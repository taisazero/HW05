package com.example.zero.hw05;

import android.os.AsyncTask;
import android.util.Log;

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
public class LoadSimilarTracks extends AsyncTask<RequestParams,Void,ArrayList<Music>> {
    Music z;
    DetailsActivity a;

    public LoadSimilarTracks(DetailsActivity a,Music l){
        this.z=l;
        z.setSimilars(new ArrayList<Music>());
        this.a=a;
    }
    protected ArrayList<Music> doInBackground(RequestParams... requests) {
        String data="";
        StringBuilder sb=null;
        ArrayList <Music>temp=new ArrayList<Music>();

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
                JSONObject r=root.getJSONObject("similartracks");


                JSONArray tracks=r.getJSONArray("track");
                Log.d("parsing Similars","found "+tracks.length()+" track");


                for(int i =0; i<tracks.length();i++){
                    JSONObject t=tracks.getJSONObject(i);
                    Music m=new Music();
                    m.setName(t.getString("name"));
                    m.setArtist(t.getJSONObject("artist").getString("name"));
                    m.setUrl(t.getString("url"));
                    JSONArray images=t.getJSONArray("image");
                    JSONObject smallPic=images.getJSONObject(0);
                    JSONObject largePic=images.getJSONObject(2);
                    m.setSmallURL(smallPic.getString("#text"));
                    m.setLargeURL(largePic.getString("#text"));
                    temp.add(m);
                  //  Log.d("Adding", i+" "+m.toString());

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
        return temp;
    }



    @Override
    protected void onPostExecute(ArrayList<Music> temp) {


        z.setSimilars(temp);
        MusicAdapter md= new MusicAdapter(a,z.getSimilars());
        for (Music m : z.getSimilars()){
            Log.d("Debugging",m.toString());
        }
        a.list.setAdapter(md);




    }
}
