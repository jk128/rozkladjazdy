package com.tomasz.rozkladjazdy;

import java.io.IOException;



import com.example.rozkladjazdy.About;
import com.example.rozkladjazdy.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tomasz.adapter.TabsPagerAdapter;
import com.tomasz.rozkladjazdy.DataBaseHelper;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private boolean SearchFrg=false;
	// Tab titles
	private String[] tabs = { "Ulubione", "Menu", "Trasa" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DataBaseHelper myDbHelper = new DataBaseHelper(this);
		myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}
		myDbHelper.openDataBase();
		myDbHelper.createFavoriteTable();

		// ***********
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);

		// actionBar.setHomeButtonEnabled(TRUE);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
 @Override
 public void onStart(){
	 super.onStart();

 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_info) {
			Intent intent = new Intent(this, About.class);
			   
		    startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
