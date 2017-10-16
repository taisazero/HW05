package com.example.zero.hw05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * @author Josiah Laivins
 * @author Erfan Al-Hossami
 *
 * @version 10/16/2017
 */
public class LoadImage extends AsyncTask<String,Integer,Bitmap> {
    ImageView view;
    //ProgressBar pb;
    public LoadImage(ImageView v){
        this.view=v;
        //this.pb=p;

    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap pic=null;
        try {
            URL link=new URL(params[0]);
           HttpURLConnection con= (HttpURLConnection) link.openConnection();
            con.connect();
            if(con.getResponseCode()==HttpURLConnection.HTTP_OK){
                publishProgress(3);
                pic=BitmapFactory.decodeStream(con.getInputStream());
            }
            publishProgress(100);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pic;
    }

   // @Override
  //  protected void onPreExecute() {
   //     pb.setMax(100);
   //     pb.setVisibility(View.VISIBLE);
   // }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

   // @Override
   // protected void onProgressUpdate(Integer... values) {
    //    pb.setProgress(values[0]);
    //    for(int i=values[0];i<99;i++){
     //       pb.setProgress(i);
      //      Thread t=new Thread();
      //      try {
        //        t.sleep(50);
       //     } catch (InterruptedException e) {
          //      e.printStackTrace();
        //    }
       // }
   // }
}
