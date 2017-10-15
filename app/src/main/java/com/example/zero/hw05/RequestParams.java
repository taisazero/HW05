package com.example.zero.hw05;

/**
 * Created by Zero on 10/14/2017.
 */

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.StringTokenizer;
/**
 * Created by Zero on 10/1/2017.
 */

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.StringTokenizer;

public class RequestParams {
    String method, baseURL;
    HashMap<String,String> params=new HashMap<String,String>();
    public  RequestParams(String method,String baseURL){
        super();
        this.baseURL=baseURL;
        this.method=method;
    }
    public void addParam(String key,String value){
        params.put(key,value);
    }
    public String getEncodedParams(){
        StringBuilder sb=new StringBuilder();
        for (String key: params.keySet()){
            try {
                String value = URLEncoder.encode(params.get(key), "UTF-8");

                //StringTokenizer token = new StringTokenizer(value, "%2");
              //  String[] temp = new String[token.countTokens()];
                //for (int i = 0; token.hasMoreElements(); i++) {
               //     temp[i] = token.nextToken();


                        sb.append("&");


                        sb.append(key + "=" + value);





            }catch(UnsupportedEncodingException e){
                Log.d("URL TEST", "Encoding Exception");
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public String getEncodedUrl(){
        Log.d("URL",this.baseURL+getEncodedParams());
        return this.baseURL+getEncodedParams();

    }
    public HttpURLConnection setUpConnection() throws IOException {
        if (method.equals("GET")){
            Log.d("URL TEST","In GET");
            URL link= new URL(getEncodedUrl());
            HttpURLConnection con=(HttpURLConnection)link.openConnection();
            con.setRequestMethod("GET");
            return con;
        }
        else{
            URL link= new URL(this.baseURL);
            HttpURLConnection con=(HttpURLConnection)link.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStreamWriter w=new OutputStreamWriter(con.getOutputStream());
            w.write(getEncodedParams());
            w.flush();
            return con;
        }
    }

}

