package com.example.rozkladjazdy;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchBusStopActivity extends ActionBarActivity {
	private DataBaseHelper dbHelper;
	 private SimpleCursorAdapter dataAdapter;
	public int  mGroupIdColumnIndex;
	ShowLineDialog showDialog =new ShowLineDialog();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_bus_stop);
		dbHelper = new DataBaseHelper(this);
		dbHelper.openDataBase();
		displayListView();
		//fillData();
		//db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
	private void displayListView() {
		Cursor c = dbHelper.fetchAllBusStops();
		dataAdapter = new SimpleCursorAdapter(this, 
		        android.R.layout.simple_list_item_1, c,new String[] { "NAZWA" }, 
		        new int[] { android.R.id.text1 });
		  ListView listView = (ListView) findViewById(R.id.listView1);
		  // Assign adapter to ListView
		  listView.setAdapter(dataAdapter);
		  listView.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> listView, View view,
			     int position, long id) {
			   // Get the cursor, positioned to the corresponding row in the result set
			   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
			 
			   // Get the state's capital from this row in the database.
			   String busStopId =cursor.getString(cursor.getColumnIndexOrThrow("_id"));
			   String nazwa=cursor.getString(cursor.getColumnIndexOrThrow("NAZWA"));
		       Bundle args = new Bundle();
		       args.putString("id", busStopId);
		       args.putString("nazwa",nazwa);
		       showDialog.setArguments(args);
		       // startActivity(i);
		       showDialog.show(getSupportFragmentManager(), "");
			   }
			  });
		  EditText myFilter = (EditText) findViewById(R.id.busStopName);
		  myFilter.addTextChangedListener(new TextWatcher() {
		 
		   public void afterTextChanged(Editable s) {
		   }
		 
		   public void beforeTextChanged(CharSequence s, int start,
		     int count, int after) {
		   }
		 
		   public void onTextChanged(CharSequence s, int start,
		     int before, int count) {
			   dataAdapter.getFilter().filter(s.toString());;
		   }
		  });
		   
		  dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
		         public Cursor runQuery(CharSequence constraint) {
		             return dbHelper.fetchBuStopsByName(constraint.toString());
		         }
		     });
		 
		 }

}
