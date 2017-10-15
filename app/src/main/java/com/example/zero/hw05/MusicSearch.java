package com.example.zero.hw05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MusicSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestParams r=new RequestParams("GET","http://ws.audioscrobbler.com/2.0/?format=json");
        r.addParam("method","track.search");
        r.addParam("track","Colors");
        r.addParam("artist","FLOW");
        r.addParam("api_key","426392c61e4a15c55916cd91b1bf857d");
        r.addParam("limit","20");
        Log.d("URL",r.getEncodedUrl());
        new LoadData().execute(r);

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

}
