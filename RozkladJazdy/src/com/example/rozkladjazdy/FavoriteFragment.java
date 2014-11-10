package com.example.rozkladjazdy;


import java.util.List;
import java.util.zip.Inflater;

import com.example.rozkladjazdy.R;

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
import android.widget.ListView;
import android.widget.Toast;

public class FavoriteFragment extends Fragment {
	private DataBaseHelper dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private ListView listView;
	ArrayAdapter<FavoriteTimeTable> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_favorite, container,
				false);
		dbHelper = new DataBaseHelper(getActivity());
		dbHelper.openDataBase();
		final Cursor c = dbHelper.fetchAllFavoriteTimeTables();
		 List<FavoriteTimeTable> values = dbHelper.getAllComments();
		 final ArrayAdapter<FavoriteTimeTable> adapter = new ArrayAdapter<FavoriteTimeTable>(this.getActivity(),
			        android.R.layout.simple_list_item_1, values);
		 //ArrayAdapter<FavoriteTimeTable> adapterr = new ArrayAdapter<FavoriteTimeTable>();
		// if(c.isBeforeFirst()){
		dataAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, c,
				new String[] { "NAZWA" }, new int[] { android.R.id.text1 });
		// Assign adapter to ListView

		listView = (ListView) rootView.findViewById(R.id.favoriteBusStops);
		listView.setAdapter(adapter);
		
		((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				c.moveToPosition(arg2);
				final int id;
				id = (int)arg2;
				
			
				DeleteFavoriteTimeTableDialog dialog = new DeleteFavoriteTimeTableDialog(new UpdateListListener() {
					
					@Override
					public void onListUpdate() {
						
						 FavoriteTimeTable favorite = null;
						 favorite=(FavoriteTimeTable) adapter.getItem(id);
						 dbHelper.DeleteFavoriteTimeTable(favorite.getId());
						 adapter.remove(favorite);
						 Toast.makeText(getActivity(),
									"ID "+id, Toast.LENGTH_LONG)
									.show();
						dataAdapter.notifyDataSetChanged();
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
				c.moveToPosition(arg2);
				String busStopId;
				String lineId;
				busStopId = c.getString(1);
				lineId = c.getString(2);
				Intent i = new Intent(getActivity(), TimeTableActivity.class);
				Bundle args = new Bundle();
				args.putString("busStopId", busStopId);
				args.putString("lineId", lineId);

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
		 List<FavoriteTimeTable> values = dbHelper.getAllComments();
		 final ArrayAdapter<FavoriteTimeTable> adapter = new ArrayAdapter<FavoriteTimeTable>(this.getActivity(),
			        android.R.layout.simple_list_item_1, values);
		 listView = (ListView) getActivity().findViewById(R.id.favoriteBusStops);
			listView.setAdapter(adapter);
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
							 favorite=(FavoriteTimeTable) adapter.getItem(id);
							 dbHelper.DeleteFavoriteTimeTable(favorite.getId());
							 adapter.remove(favorite);
							 Toast.makeText(getActivity(),
										"ID "+id, Toast.LENGTH_LONG)
										.show();
							dataAdapter.notifyDataSetChanged();
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
					
				}

			});
	    super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

}