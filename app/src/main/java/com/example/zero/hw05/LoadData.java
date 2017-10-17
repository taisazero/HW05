package com.example.zero.hw05;

import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;
import android.widget.Toast;

//import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * @author Josiah Laivins
 * @author Erfan Al-Hossami
 *
 * @version 10/16/2017
 */
public class LoadData extends AsyncTask<RequestParams,Void,String> {
    Intent i;
    MainActivity m;
    public LoadData(MainActivity m,Intent i){
        this.i=i;
        this.m=m;
    }
    @Override
    protected String doInBackground(RequestParams... requests) {
        String data="";
        StringBuilder sb=null;
        if (Music.results != null) {
            ArrayList<Music> temp = new ArrayList<>();
            for (Music result : Music.results) {
                if (result.isFavorate()) {
                    temp.add(result);
                }
            }
            for (Music music : temp) {
                if (!Music.addFavorite(music)) {
                    Toast.makeText(m, "You have reached your favorite limit!!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        Music.results = new ArrayList<>();
        try {
            HttpURLConnection connection=requests[0].setUpConnection();
            connection.connect();

            if(connection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                // data= IOUtils.toString(connection.getInputStream(),"UTF-8");
                BufferedReader br= new BufferedReader (new InputStreamReader(connection.getInputStream()));
               sb=new StringBuilder();
                while((data=br.readLine())!=null){
                    sb.append(data);
                    Log.d("data",data);
                }
                br.close();
                JSONObject root=new JSONObject(sb.toString());
                JSONObject r=root.getJSONObject("results");





                JSONObject results=r.getJSONObject("trackmatches");
                JSONArray tracks=results.getJSONArray("track");
                Log.d("parsing 1","found "+tracks.length()+"track");
                for(int i =0; i<tracks.length();i++){
                    JSONObject t=tracks.getJSONObject(i);
                    Music m=new Music();
                    m.setName(t.getString("name"));
                    m.setArtist(t.getString("artist"));
                    m.setUrl(t.getString("url"));
                    JSONArray images=t.getJSONArray("image");
                    JSONObject smallPic=images.getJSONObject(0);
                    JSONObject largePic=images.getJSONObject(3);
                    m.setSmallURL(smallPic.getString("#text"));
                    m.setLargeURL(largePic.getString("#text"));
                    Music.results.add(m);
                }

            }
            else {
                Log.d("Connection", "Connection Failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    @Override
    protected void onPostExecute(String s) {

        for (Music m : Music.results) {
            Log.d("Musics",m.toString() );
        }
        m.startActivity(i);
        //m.finish();

    }
}

