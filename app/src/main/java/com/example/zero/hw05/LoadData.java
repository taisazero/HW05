package com.example.zero.hw05;

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
 * Created by Zero on 10/14/2017.
 */

public class LoadData extends AsyncTask<RequestParams,Void,String> {

    @Override
    protected String doInBackground(RequestParams... requests) {
        String data="";
        try {
            HttpURLConnection connection=requests[0].setUpConnection();
            connection.connect();

            if(connection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                // data= IOUtils.toString(connection.getInputStream(),"UTF-8");
                BufferedReader br= new BufferedReader (new InputStreamReader(connection.getInputStream()));
                while(br.readLine()!=null){
                    data+=br.readLine();
                    Log.d("data",data);
                }
                br.close();
                JSONObject root=new JSONObject(data);
                JSONObject results=root.getJSONObject("trackmatches");
                JSONArray tracks=results.getJSONArray("tracks");
                Log.d("parsing 1","found "+tracks.length()+"tracks");
                for(int i =0; i<tracks.length();i++){
                    JSONObject t=tracks.getJSONObject(i);
                    Music m=new Music();
                    m.setName(t.getString("name"));
                    m.setArtist(t.getString("artist"));
                    m.setUrl(t.getString("url"));
                    JSONArray images=t.getJSONArray("image");
                    JSONObject smallPic=images.getJSONObject(0);
                    JSONObject largePic=images.getJSONObject(2);
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
        return data;
    }



    @Override
    protected void onPostExecute(String s) {
        Log.d("parsing",s);
    }
}

