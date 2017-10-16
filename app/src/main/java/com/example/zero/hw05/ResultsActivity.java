package com.example.zero.hw05;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<Music> adapter= new ArrayAdapter<Music>(this,android.R.layout.simple_list_item_2,Music.results);
        ListView list=(ListView)findViewById(R.id.resultsListView);
        list.setAdapter(adapter);


    }

}
