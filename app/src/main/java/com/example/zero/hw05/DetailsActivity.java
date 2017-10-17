package com.example.zero.hw05;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Josiah Laivins
 * @author Erfan Al-Hossami
 *
 * @version 10/16/2017
 */
public class DetailsActivity extends AppCompatActivity {
    TextView name;
    TextView artist;
    TextView url;
    ImageView pic;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        name = (TextView) findViewById(R.id.txtResultName);
        artist = (TextView) findViewById(R.id.txtResultArtist);
        url = (TextView) findViewById(R.id.txtResultUrl);
        pic = (ImageView) findViewById(R.id.imgArtist);
        int p=0;
        Music selection;
        if (getIntent().getExtras().get("position") != null) {
            p = (int) getIntent().getExtras().get("position");
            selection = Music.results.get(p);
        }
        else {
             p = (int) getIntent().getExtras().get("positionNEW");
            selection = Music.similars.get(p);
        }

            name.setText(selection.getName());
            artist.setText(selection.getArtist());
            url.setText(selection.getUrl());
            url.setClickable(true);
            new LoadImage(pic).execute(selection.getLargeURL());
            list=(ListView)findViewById(R.id.listSimilarTracks);
            //http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=<ARTISTNAME>&track=TRACKNAME&api_key=YOUR_API_KEY&format=json &limit=10
            RequestParams link = new RequestParams("GET","http://ws.audioscrobbler.com/2.0/?format=json");
            link.addParam("method","track.getsimilar");
            link.addParam("artist",selection.getArtist());
            link.addParam("track",selection.getName());
            link.addParam("api_key","426392c61e4a15c55916cd91b1bf857d");
            link.addParam("limit","10");
            Log.d("Similar",link.getEncodedUrl());

             new LoadSimilarTracks(this,selection).execute(link);

            Log.d("Detailed Similars",new Integer(selection.getSimilars().size()).toString());

            for (Music m : selection.getSimilars()){
               Log.d("Detailed Similars", m.toString());
            }





            setHandlers();



        MusicAdapter adapter= new MusicAdapter(this,Music.similars);

        ListView list=(ListView)findViewById(R.id.listSimilarTracks);
        list.setAdapter(adapter);
        list.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ResultsActivity::", "List clicked");
                Intent i = new Intent(DetailsActivity.this,DetailsActivity.class);
                i.putExtra("positionNEW",position);

                startActivity(i);
                finish();
            }
        });
    }

    public void setHandlers() {
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url.getText().toString()));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }


    @SuppressLint("ApplySharedPref")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_home:
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.action_exit:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MusicHandler", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                Gson gson = new Gson();
                String[] arrayOfMusicStrings = new String[Music.getFavorites().size()];
                for (int i = 0; i < arrayOfMusicStrings.length; i++) {
                    arrayOfMusicStrings[i] = gson.toJson(Music.getFavorites().get(i), Music.class);
                }
                Set<String> json = new HashSet<>(Arrays.asList(arrayOfMusicStrings));
                edit.putStringSet("MusicSet", json);
                edit.commit();
                Toast.makeText(this, "Exited!",
                        Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                } else {
                    this.finishAffinity();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
