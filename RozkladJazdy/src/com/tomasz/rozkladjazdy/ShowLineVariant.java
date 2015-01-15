package com.example.rozkladjazdy;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

public class ShowLineVariant extends ActionBarActivity {
	private DataBaseHelper dbHelper;
	String nazwaLinii;
	String lineId;
	String busStopId;
	Cursor childCursor;
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_line_variant, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {

		super.onPause();
		

	}

	
	@Override
	protected void onStop(){
		super.onStop();
	
	}
	@Override
	protected void onStart()
	{
		super.onStart();
		setContentView(R.layout.activity_show_line_variant);
		dbHelper = new DataBaseHelper(ShowLineVariant.this);
		dbHelper.openDataBase();
		Intent intent = getIntent();
		nazwaLinii = intent.getStringExtra("nazwa");
		fillData();

	}
	@Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        setContentView(R.layout.activity_show_line_variant);
		dbHelper = new DataBaseHelper(ShowLineVariant.this);
		dbHelper.openDataBase();
		Intent intent = getIntent();
		nazwaLinii = intent.getStringExtra("nazwa");
		fillData();
    }
	@Override
	protected void onResume(){
		super.onResume();
	}

	public class ExpandableListAdapter extends SimpleCursorTreeAdapter {

		public ExpandableListAdapter(Cursor cursor, Context context,
				int groupLayout, int childLayout, String[] groupFrom,
				int[] groupTo, String[] childrenFrom, int[] childrenTo) {
			super(context, cursor, groupLayout, groupFrom, groupTo,
					childLayout, childrenFrom, childrenTo);
		}

		@Override
		protected Cursor getChildrenCursor(Cursor groupCursor) {
			lineId = groupCursor.getString(groupCursor.getColumnIndex("_id"));
			childCursor = dbHelper.fetchBusStopByLine(lineId);
			childCursor.moveToFirst();
			return childCursor;
		}
	}

	private void fillData() {
		final Cursor mGroupsCursor = dbHelper.fetchLineVariant(nazwaLinii);
		ShowLineVariant.this.startManagingCursor(mGroupsCursor);
		mGroupsCursor.moveToFirst();
		ExpandableListAdapter mAdapter;

		ExpandableListView elv = (ExpandableListView) findViewById(R.id.expandableListView1);

		mAdapter = new ExpandableListAdapter(mGroupsCursor,
				ShowLineVariant.this,
				R.layout.list_group, // group layout
																
				android.R.layout.simple_list_item_1, // Your row layout for a
														// child
				new String[] { "NAZWA" }, // Field(s) to use from group cursor
				new int[] { R.id.lblListHeader }, // Widget ids to put group
													// data into
				new String[] { "NAZWA" }, // Field(s) to use from child cursors
				new int[] { android.R.id.text1 });
		elv.setAdapter(mAdapter); // set the list adapter.
		elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// Your child click code here
				childCursor.moveToPosition(childPosition);
				busStopId = childCursor.getString(0);
				// Toast toast = Toast.makeText(ShowLineVariant.this, busStopId,
				// Toast.LENGTH_LONG);
				// toast.show();
				Intent i = new Intent(ShowLineVariant.this,
						TimeTableActivity.class);
				Bundle args = new Bundle();
				args.putString("busStopId", busStopId);
				args.putString("lineId", lineId);
				i.putExtras(args);
				startActivity(i);
				return true;
			}
		});
	}
}
