package com.example.rozkladjazdy;

import com.example.tabsswipe.adapter.TabsTimeTablePageAdapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TimeTableActivity extends FragmentActivity implements
ActionBar.TabListener {

	 private String[] tabs = { "Dni powszednie", "Soboty", "Niedziele" };
	 private ViewPager viewPager;
	    private TabsTimeTablePageAdapter mAdapter;
	    private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_table);
		
		 	//cuda
		 viewPager = (ViewPager) findViewById(R.id.pager);
	        actionBar = getActionBar();
	        mAdapter = new TabsTimeTablePageAdapter(getSupportFragmentManager());
	 
	        viewPager.setAdapter(mAdapter);
	        //actionBar.setHomeButtonEnabled(TRUE);
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
		 	//cuda
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_table, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	
		switch (item.getItemId()) {
        case R.id.action_settings:
           //cos
            return true;
        case R.id.action_favorite:
        	
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
