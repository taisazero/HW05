package com.example.zero.hw05;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josiah Laivins
 * @author Erfan Al-Hossami
 *
 * @version 10/16/2017
 */
public class MainActivity extends AppCompatActivity {
    Button search;
    EditText text;//ji

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MusicHandler", MODE_PRIVATE);
        Log.d("MainActivity", "does pref contain: " + pref.contains("MusicSet"));
        if (pref.contains("MusicSet")) {
            Gson gson = new Gson();
            Set<String> json = pref.getStringSet("MusicSet", null);
            if (json != null) {
                String[] musicArray = new String[json.size()];
                int i = 0;
                for (String s : json) {
                    musicArray[i++] = s;
                }
                for (String s : musicArray) {
                    Music.addFavorite(gson.fromJson(s, Music.class));
                }

                Log.d("MainActivity", "added favorites");
            }
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        setHandlers();

        generateList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateList();
    }


    public void generateList() {

        ArrayList<Music> favorites = Music.getFavorites();

        MusicAdapter adapter = new MusicAdapter(this, favorites);

        ListView list = (ListView) findViewById(R.id.mainListView);
        list.setAdapter(adapter);
        list.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ResultsActivity::", "List clicked");
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("position", position);
                startActivity(i);
                finish();
            }
        });

    }


    public void setHandlers() {
        search = (Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (EditText) findViewById(R.id.editTrackSearch);

                if (isConnected()) {
                    RequestParams r = new RequestParams("GET", "http://ws.audioscrobbler.com/2.0/?format=json");
                    r.addParam("method", "track.search");
                    r.addParam("track", text.getText().toString());

                    r.addParam("api_key", "426392c61e4a15c55916cd91b1bf857d");
                    r.addParam("limit", "20");

                    Intent i = new Intent(MainActivity.this, ResultsActivity.class);
                    new LoadData(MainActivity.this, i).execute(r);
                }
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
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

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_home:
                Toast.makeText(this, "You are already home!",
                        Toast.LENGTH_LONG).show();
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
