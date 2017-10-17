package com.example.zero.hw05;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * @author Josiah Laivins
 * @author Erfan Al-Hossami
 *
 * @version 10/16/2017
 */
public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MusicAdapter adapter= new MusicAdapter(this,Music.results);

        ListView list=(ListView)findViewById(R.id.resultsListView);
        list.setAdapter(adapter);
        list.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ResultsActivity::", "List clicked");
                Intent i = new Intent(ResultsActivity.this,DetailsActivity.class);
                i.putExtra("position",position);
                startActivity(i);
                finish();
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
                Intent i = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.action_exit:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MusicHandler", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Music.getFavorites());
                edit.putString("MusicArrayList", json);
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
