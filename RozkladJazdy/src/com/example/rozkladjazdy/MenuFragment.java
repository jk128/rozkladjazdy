package com.example.rozkladjazdy;

import com.example.rozkladjazdy.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
 
public class MenuFragment extends Fragment {
 
	final String[] items = new String[] { "Rozk³ad Przystanku", "Rozk³ad Linii", "Wyszukiwanie po³¹czeñ",
	        "Mapa", "Menu"};
	final Integer[] imageId=  {
	R.drawable.abc_ic_go_search_api_holo_light,
	R.drawable.abc_ic_go_search_api_holo_light,
	R.drawable.abc_ic_go_search_api_holo_light,
	R.drawable.abc_ic_go_search_api_holo_light,
	R.drawable.abc_ic_go_search_api_holo_light
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

	    View view = inflater.inflate(R.layout.fragment_menu, container, false);

	    ListView list = (ListView)view.findViewById(R.id.listView1);
	    CustomAdapter cus = new CustomAdapter(getActivity(),items,imageId);   
	    list.setAdapter(cus);
	   
	    list.setOnItemClickListener(new OnItemClickListener() {

	    	 @Override
	    	   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
	    		if (position==0){ Intent busStop = new Intent(getActivity(), SearchBusStopActivity.class);
	                startActivity(busStop);
	    						}
	              if (position==1){ Intent line = new Intent(getActivity(), SearchLineActivity.class);
	                startActivity(line);
	    		}
	    	   } 
	    	});


	    return view;

	   }
	  }