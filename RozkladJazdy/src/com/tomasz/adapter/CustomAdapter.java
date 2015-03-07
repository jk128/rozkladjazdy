package com.tomasz.adapter;

import com.example.rozkladjazdy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    String items[];
    Integer imageId[];
    LayoutInflater mInflater;

    public CustomAdapter(Context context, String[] items, Integer[] imageId) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.imageId=imageId;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView ==null)
        {
            convertView = mInflater.inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.tv.setTextSize(24);
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag(); 
        }
        holder.tv.setText(items[position]) ;
        holder.iv.setImageResource(imageId[position]);
        return convertView;
    }

    static class ViewHolder
    { 
        ImageView iv;
        TextView tv;
    }
}