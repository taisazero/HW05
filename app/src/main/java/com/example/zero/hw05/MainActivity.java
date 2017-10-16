package com.example.zero.hw05;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;


public class MainActivity extends AppCompatActivity {
    Button search;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        setHandlers();




    }


    public void setHandlers(){
        search=(Button)findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=(EditText)findViewById(R.id.editTrackSearch);

                if(isConnected()) {
                    RequestParams r = new RequestParams("GET", "http://ws.audioscrobbler.com/2.0/?format=json");
                    r.addParam("method", "track.search");
                    r.addParam("track", text.getText().toString() );

                    r.addParam("api_key", "426392c61e4a15c55916cd91b1bf857d");
                    r.addParam("limit", "20");
                    new LoadData().execute(r);
                    Intent i =new Intent(MainActivity.this,ResultsActivity.class);
                    startActivity(i);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_home:
                Toast.makeText(this, "You are already home!",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.action_exit:
                Toast.makeText(this, "Exited!",
                       Toast.LENGTH_SHORT).show();


                    finish();




                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
