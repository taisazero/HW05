package com.example.zero.hw05;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {
    TextView name;
    TextView artist;
    TextView url;
    ImageView pic;

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
        if (getIntent().getExtras() != null) {
            int p = (int) getIntent().getExtras().get("position");
            Music selection = Music.results.get(p);
            name.setText(selection.getName());
            artist.setText(selection.getArtist());
            url.setText(selection.getUrl());
            url.setClickable(true);
            new LoadImage(pic).execute(selection.getLargeURL());
            setHandlers();

        } else {
            Log.d("Details", "Intent Error");
        }


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
