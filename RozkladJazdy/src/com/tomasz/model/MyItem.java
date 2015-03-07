package com.tomasz.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    public final String name;
    public final String lines;
   private final String id;
    public final int pictureResource;
    
    public MyItem(double lat, double lng,String name,String lines, int pictureResource,String id) {
        mPosition = new LatLng(lat, lng);
        this.name = name;
        this.lines=lines;
        this.pictureResource = pictureResource;
        this.id=id;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
    public String getName(){
    	return name;
    }
    public String getLines(){
    	return lines;
    }
    public int getPictureResource(){
    	return pictureResource;
    }

	public String getId() {
		return id;
	}
    
    
}