package com.example.zero.hw05;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Zero on 10/16/2017.
 */

public class MusicAdapter extends ArrayAdapter<Music> {
    Context context;
    ArrayList<Music> list;

    public MusicAdapter(Context context, ArrayList<Music> musicArrayList) {
        super(context, R.layout.item_row_layout, musicArrayList);
        this.context = context;
        this.list = musicArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_row_layout, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.songPic);
            holder.artistName = (TextView) convertView.findViewById(R.id.aName);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            //holder.favorate = (ImageButton) convertView.findViewById(R.id.imgBtn);
            convertView.setTag(holder);

        }
        holder = (ViewHolder) convertView.getTag();
        TextView name = holder.name;
        TextView aName = holder.artistName;
        ImageView pic = holder.image;
        //final ImageButton favorate = holder.favorate;
        name.setText(list.get(position).getName());
        aName.setText(list.get(position).getArtist());
        new LoadImage(pic).execute(list.get(position).getSmallURL());
/*
        if (list.get(position).isFavorate()) {
            favorate.setImageResource(android.R.drawable.star_big_on);
        } else {
            favorate.setImageResource(android.R.drawable.star_big_off);
        }

        favorate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isFavorate()) {
                    favorate.setImageResource(android.R.drawable.star_big_off);
                    list.get(position).setFavorate(false);
                } else {
                    favorate.setImageResource(android.R.drawable.star_big_on);
                    list.get(position).setFavorate(true);
                }
            }
        });*/

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView name;
        TextView artistName;
        //ImageButton favorate;
    }

}
