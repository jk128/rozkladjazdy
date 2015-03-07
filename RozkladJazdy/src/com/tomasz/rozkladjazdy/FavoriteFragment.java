package com.tomasz.rozkladjazdy;


import java.util.List;
import java.util.zip.Inflater;

import com.example.rozkladjazdy.R;
import com.tomasz.adapter.FavoriteTimeTableAdapter;
import com.tomasz.model.FavoriteTimeTable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FavoriteFragment extends Fragment {
	private DataBaseHelper dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private ListView listView;
	private boolean exclamation_mark=false;
	 ImageView iv;
	ArrayAdapter<FavoriteTimeTable> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_favorite, container,
				false);
		dbHelper = new DataBaseHelper(getActivity());
		dbHelper.openDataBase();
		final Cursor c = dbHelper.fetchAllFavoriteTimeTables();
		
		 List<FavoriteTimeTable> values = dbHelper.getAllFavoriteTimeTables();
		 final ArrayAdapter<FavoriteTimeTable> adapter = new ArrayAdapter<FavoriteTimeTable>(this.getActivity(),
			        android.R.layout.simple_list_item_1, values);
		 ImageView iv=(ImageView) rootView.findViewById(R.id.exclamation_mark);
		 TextView tv=(TextView) rootView.findViewById(R.id.favorite_explanation);
		 LinearLayout LL= (LinearLayout) rootView.findViewById(R.id.LinearlayoutFav);
		if( (values.size()==0)) {
			
			iv.setVisibility(View.VISIBLE);
			tv.setVisibility(View.VISIBLE);
			LL.setVisibility(View.INVISIBLE);
		}
		else  
		{
			iv.setVisibility(View.INVISIBLE);
			tv.setVisibility(View.INVISIBLE);
			 LL.setVisibility(View.VISIBLE);
		}
		//cuda
		
		 
		 FavoriteTimeTableAdapter adapterr = new FavoriteTimeTableAdapter(this.getActivity(),R.layout.listview_item_row, values);
		
		 //koniec cudow
		// Assign adapter to ListView
		
		listView = (ListView) rootView.findViewById(R.id.favoriteBusStops);
		listView.setAdapter(adapterr);
		
		((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				c.moveToPosition(arg2);
				final int id;
				id = arg2;
				
			
				DeleteFavoriteTimeTableDialog dialog = new DeleteFavoriteTimeTableDialog(new UpdateListListener() {
					
					@Override
					public void onListUpdate() {
						
						 FavoriteTimeTable favorite = null;
						 favorite=(FavoriteTimeTable) adapter.getItem(id);
						 dbHelper.DeleteFavoriteTimeTable(favorite.getId());
						 adapter.remove(favorite);
						 if(adapter.getCount()==0) exclamation_mark=true; else exclamation_mark=false;
						
						dataAdapter.notifyDataSetChanged();
					}
				});
				Bundle args = new Bundle();
				args.putInt("id", id);
				
				return false;
				
			}

		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int id;
				id = arg2;
				 FavoriteTimeTable favorite = null;
				 favorite=(FavoriteTimeTable) adapter.getItem(id);
				Intent i = new Intent(getActivity(), TimeTableActivity.class);
				Bundle args = new Bundle();
				args.putString("busStopId", favorite.getBusStopId());
				args.putString("lineId", favorite.getLineId());

				i.putExtras(args);
				startActivity(i);

			}

		});
		// }

		return rootView;

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume(){
		 List<FavoriteTimeTable> values = dbHelper.getAllFavoriteTimeTables();
		 ImageView iv=(ImageView)  getActivity().findViewById(R.id.exclamation_mark);
		 TextView tv=(TextView) getActivity().findViewById(R.id.favorite_explanation);
		 LinearLayout LL= (LinearLayout) getActivity().findViewById(R.id.LinearlayoutFav);
		 if (values.isEmpty()){ 
			 iv.setVisibility(View.VISIBLE);
			 tv.setVisibility(View.VISIBLE);
			 LL.setVisibility(View.INVISIBLE);
		 } else  
		 {
			 iv.setVisibility(View.INVISIBLE);
			 tv.setVisibility(View.INVISIBLE);
			 LL.setVisibility(View.VISIBLE);
		 }
		
		
		 
		 View header = (View)getActivity().getLayoutInflater().inflate(R.layout.listview_header_row, null);
		 final FavoriteTimeTableAdapter adapterr = new FavoriteTimeTableAdapter(this.getActivity(),R.layout.listview_item_row, values);
		 final ArrayAdapter<FavoriteTimeTable> adapter = new ArrayAdapter<FavoriteTimeTable>(this.getActivity(),
			        android.R.layout.simple_list_item_1, values);
		 listView = (ListView) getActivity().findViewById(R.id.favoriteBusStops);
			//listView.setAdapter(adapter); z tym dziala
		//test
			//listView.addHeaderView(header);
		       
	        listView.setAdapter(adapterr);
			//test
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					final int id;
					id = (int)arg2;
					
				
					DeleteFavoriteTimeTableDialog dialog = new DeleteFavoriteTimeTableDialog(new UpdateListListener() {
						
						@Override
						public void onListUpdate() {
							
							 FavoriteTimeTable favorite = null;
							 favorite=(FavoriteTimeTable) adapterr.getItem(id);
							 dbHelper.DeleteFavoriteTimeTable(favorite.getId());
							 adapterr.remove(favorite);	
							 ImageView iv=(ImageView)  getActivity().findViewById(R.id.exclamation_mark);
							 TextView tv=(TextView) getActivity().findViewById(R.id.favorite_explanation);
							 LinearLayout LL= (LinearLayout) getActivity().findViewById(R.id.LinearlayoutFav);
							 if(adapter.getCount()==0){
							 iv.setVisibility(View.VISIBLE);
							 tv.setVisibility(View.VISIBLE);
							 LL.setVisibility(View.INVISIBLE);
							 
							 } else  {iv.setVisibility(View.INVISIBLE);tv.setVisibility(View.INVISIBLE); LL.setVisibility(View.VISIBLE);}
							
							
						}
					});
					Bundle args = new Bundle();
					args.putInt("id", id);
					dialog.setArguments(args);
					dialog.show(getFragmentManager(), "mTag");
					return false;
					
				}

			});
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					final int id;
					id = arg2;
					 FavoriteTimeTable favorite = null;
					 favorite=(FavoriteTimeTable) adapter.getItem(id);
					Intent i = new Intent(getActivity(), TimeTableActivity.class);
					Bundle args = new Bundle();
					args.putString("busStopId", favorite.getBusStopId());
					args.putString("lineId", favorite.getLineId());

					i.putExtras(args);
					startActivity(i);
				}

			});
	    super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

}