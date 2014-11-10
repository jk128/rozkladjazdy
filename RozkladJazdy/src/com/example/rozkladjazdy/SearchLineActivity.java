package com.example.rozkladjazdy;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SearchLineActivity extends ActionBarActivity {
	GridView gridView;
	ShowBusStopDialog showDialog =new ShowBusStopDialog();
	static final String[] numbers = new String[] { 
		"0A", "0B", "1", "2", "3",
		"4", "5", "6", "7", "8",
		"9", "10", "11", "12", "13",
		"14", "15", "16", "17", "18",
		"19", "20", "21", "22", "23",
		"24", "25", "26", "27", "28",
		"29", "30", "31", "32", "33",
		"34", "35", "36", "37", "38",
		"39", "40", "42", "44", "46",
		"49", "59", "51", "53", "54",
		"N1", "N2", "N3"};
	String nazwa=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_line);
		gridView = (GridView) findViewById(R.id.gridView1);
		 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, numbers);
 
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   Toast.makeText(getApplicationContext(),
				((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			   nazwa=((TextView) v).getText().toString();
			  
		     //  showDialog.setArguments(args);
		       Intent i= new Intent(SearchLineActivity.this,ShowLineVariant.class);
		       i.putExtra("nazwa", nazwa);
		        startActivity(i);
		      // showDialog.show(getSupportFragmentManager(), "bla");
			}
		});

	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_line, menu);
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
}
