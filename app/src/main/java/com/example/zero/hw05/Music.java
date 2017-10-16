package com.example.zero.hw05;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * @author Josiah Laivins
 * @author Erfan Al-Hossami
 *
 * @version 10/16/2017
 */
public class Music {
    public static ArrayList<Music> results;
    public ArrayList<Music> similars;
    public static ArrayList<Music> favorites = new ArrayList<>();
    private String name;
    private String artist;
    private String url;
    private String smallURL;
    private String largeURL;
    private boolean isFavorate;
    private Bitmap smallPic;
    private Bitmap largePic;
    public ArrayList<Music>getSimilars(){return this.similars;}
    public void setSimilars(ArrayList<Music> list){
        this.similars=list;
    }
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

    public boolean isFavorate() {
        return isFavorate;
    }

    public void setFavorate(boolean favorate) {
        isFavorate = favorate;
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
                ", isFavorate=" + isFavorate +
                ", smallPic=" + smallPic +
                ", largePic=" + largePic +
                '}';
    }

    public Music(String n, String a , String url, String small, String large){
        this.name=n;
        this.artist=a;
        this.url=url;
        this.smallURL=small;
        this.largeURL=large;
        this.isFavorate = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Music music = (Music) o;

        if (isFavorate != music.isFavorate) return false;
        if (!name.equals(music.name)) return false;
        if (!artist.equals(music.artist)) return false;
        if (!url.equals(music.url)) return false;
        if (!smallURL.equals(music.smallURL)) return false;
        return largeURL.equals(music.largeURL);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + smallURL.hashCode();
        result = 31 * result + largeURL.hashCode();
        result = 31 * result + (isFavorate ? 1 : 0);
        return result;
    }
}
