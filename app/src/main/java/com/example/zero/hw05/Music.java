package com.example.zero.hw05;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Zero on 10/14/2017.
 */

public class Music {
    public static ArrayList<Music> results;
    public static ArrayList<Music> favorites;
    private String name;
    private String artist;
    private String url;
    private String smallURL;
    private String largeURL;
    private Bitmap smallPic;
    private Bitmap largePic;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmallURL() {
        return smallURL;
    }

    public void setSmallURL(String smallURL) {
        this.smallURL = smallURL;
    }

    public String getLargeURL() {
        return largeURL;
    }

    public void setLargeURL(String largeURL) {
        this.largeURL = largeURL;
    }



    public Music (){

    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", smallURL='" + smallURL + '\'' +
                ", largeURL='" + largeURL + '\'' +
                '}';
    }

    public Music(String n, String a , String url, String small, String large){
        this.name=n;
        this.artist=a;
        this.url=url;
        this.smallURL=small;
        this.largeURL=large;
    }

}