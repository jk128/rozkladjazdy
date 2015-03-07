package com.tomasz.adapter;

import java.util.List;

import com.tomasz.model.FavoriteTimeTable;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rozkladjazdy.R;
public class FavoriteTimeTableAdapter extends ArrayAdapter<FavoriteTimeTable>{

    Context context;
    int layoutResourceId;   
    List<FavoriteTimeTable> data = null;
   
    public FavoriteTimeTableAdapter(Context context, int layoutResourceId, List<FavoriteTimeTable> data) {
    	super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
           
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
       
        FavoriteTimeTable favorite = data.get(position);
        holder.txtTitle.setText(favorite.getNazwa());
        holder.imgIcon.setImageResource(R.drawable.calendar_month);
       
        return row;
    }
   
    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}