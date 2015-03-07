package com.example.rozkladjazdy;

import java.util.ArrayList;
import java.util.List;

import com.example.rozkladjazdy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemInfoWindowClickListener;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.tomasz.model.MyItem;
import com.tomasz.rozkladjazdy.DataBaseHelper;
import com.tomasz.rozkladjazdy.ShowLineDialog;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MapsActivity extends ActionBarActivity implements ClusterManager.OnClusterClickListener<MyItem>, ClusterManager.OnClusterInfoWindowClickListener<MyItem>, ClusterManager.OnClusterItemClickListener<MyItem>, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {
	private DataBaseHelper dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private Cursor busStops;
	ShowLineDialog showDialog =new ShowLineDialog();
	List< MyItem> list;
	static final LatLng RZESZOW = new LatLng(50.049, 21.999);
	// Google Map
	private GoogleMap googleMap;
	 private ClusterManager<MyItem> mClusterManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		dbHelper = new DataBaseHelper(this);
		dbHelper.openDataBase();
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RZESZOW, 10));
		googleMap.addMarker(new MarkerOptions().position(
				RZESZOW).title("Rzeszów"));
		new LongOperation().execute();
		try {
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private class  LongOperation extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(MapsActivity.this);
	
		public LongOperation() {
		
		}
	
		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Proszê czekaæ...");
			this.dialog.show();

		}
		@Override
		protected String doInBackground(Void... arg0) {
			getData();
			
			return null;
		}
		@Override
		protected void onPostExecute(String found) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			setUpClusterer();
			
		}
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_info) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			
			googleMap.setMyLocationEnabled(true);
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(50.049, 21.999)).zoom(12).build();
			Marker hamburg = googleMap.addMarker(new MarkerOptions().position(
					RZESZOW).title("Rzeszów"));
			Cursor c = dbHelper.fetchAllBusStops();
			c.moveToFirst();
			 while (!c.isAfterLast()) {
					
				 Double longitude=c.getDouble(2);
				 Double latitude=c.getDouble(3);
				 String name= c.getString(1);
				if((longitude!= null) && (latitude!=null) )
				{
			    String linie=dbHelper.fetchLinesNumberByBusStop(c.getString(0));
				 googleMap.addMarker(new MarkerOptions()
			        .position(new LatLng(latitude, longitude))
			        .title(name).snippet(linie));
				}

				c.moveToNext();
			 }
			 c.close();
			// Move the camera instantly to Rzeszow with a zoom of 12.
			googleMap
					.moveCamera(CameraUpdateFactory.newLatLngZoom(RZESZOW, 12));
			googleMap.addMarker(new MarkerOptions()
	        .position(new LatLng(50.038937, 21.997654))
	        .title("Ciepliñskiego park 02 "));
			// Zoom in, animating the camera.
			// googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000,
			// null);
			// googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	
	private void setUpClusterer() {
	    // Declare a variable for the cluster manager.
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		googleMap.setMyLocationEnabled(true);
	    // Position the map.
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RZESZOW, 10));

	    // Initialize the manager with the context and the map.
	    // (Activity extends context, so we can pass 'this' in the constructor.)
	    mClusterManager = new ClusterManager<MyItem>(this, googleMap);
	    mClusterManager.setRenderer(new ItemRenderer());
	    googleMap.setOnCameraChangeListener(mClusterManager);
	    googleMap.setOnMarkerClickListener(mClusterManager);
	    googleMap.setOnInfoWindowClickListener(mClusterManager);
	    mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager.cluster();
	    // Point the map's listeners at the listeners implemented by the cluster
	    // manager.
	    
	    
	    // Add cluster items (markers) to the cluster manager.
	    addItems();
	}

	private void addItems() {
			
		for(MyItem item : list){
			// MyItem offsetItem = new MyItem(item.getPosition().latitude, item.getPosition().longitude,item.getName(),item.getLines(),item.getPictureResource()); 
			mClusterManager.addItem(item);
			
			}
			        
		    
	
	    // Set some lat/lng coordinates to start with.
	
	    // Add ten cluster items in close proximity, for purposes of this example.
	
	}
	private void getData(){
		
		list = new ArrayList< MyItem>();
		busStops = dbHelper.fetchAllBusStops();
		
		 while (!busStops.isAfterLast()) {
				
			 Double longitude=busStops.getDouble(2);
			 Double latitude=busStops.getDouble(3);
			 String name= busStops.getString(1);
			if((longitude!= null) && (latitude!=null) )
			{
				String linie=dbHelper.fetchLinesNumberByBusStop(busStops.getString(0));
				 MyItem offsetItem = new MyItem(latitude, longitude,name,linie,R.drawable.bus_station_icon,busStops.getString(0)); 
			        list.add(offsetItem);
		    
			
			}

			busStops.moveToNext();
		 }
		 busStops.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//initilizeMap();
	}

	@Override
	public boolean onClusterItemClick(MyItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClusterItemInfoWindowClick(MyItem item) {
		// TODO Auto-generated method stub
		 Bundle args = new Bundle();
	       args.putString("id", item.getId());
	       args.putString("nazwa",item.name);
	      
	     
	       showDialog.setArguments(args);
	       showDialog.show(getSupportFragmentManager(), "");
		
	}

	@Override
	public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {
		//Toast.makeText(this,  " (including )", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public boolean onClusterClick(Cluster<MyItem> cluster) {
		// TODO Auto-generated method stub
		return false;
	}
	//cuda swiata
	private class ItemRenderer extends DefaultClusterRenderer<MyItem> {
		 private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
	        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
	        private final ImageView mImageView;
	        private final ImageView mClusterImageView;
	        private final int mDimension;

     

        public ItemRenderer() {
            super(getApplicationContext(), googleMap, mClusterManager);
            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new ImageView(getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
           
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem person, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.
        	 mImageView.setImageResource(person.pictureResource);
             Bitmap icon = mIconGenerator.makeIcon();
            
            markerOptions.title(person.name);
            markerOptions.snippet(person.lines);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;
            Drawable drawable=null;
            for (MyItem p : cluster.getItems()) {
                // Draw 1 at most.
                if (profilePhotos.size() == 1) break;
                drawable = getResources().getDrawable(p.pictureResource);
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
           

            mClusterImageView.setImageDrawable(drawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 2;
        }
    }

}
