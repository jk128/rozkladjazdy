package com.tomasz.rozkladjazdy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rozkladjazdy.R;

public class SearchFragment extends Fragment {
	static final int BUS_STOP_REQUEST = 1; // The request code
	private String sourceNazwa;
	private String sourceId = null;
	private String destNazwa;
	private String destId = null;
	private EditText sourceBusStop;
	private EditText destBusStop;
	private EditText minutesToDep;
	private TextView min;
	private boolean addMinutes=false;
	private ToggleButton tb;
	private DataBaseHelper dbHelper;
	public static final int PLEASE_WAIT_DIALOG = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);
		Button search = (Button) rootView.findViewById(R.id.search_button);

		dbHelper = new DataBaseHelper(getActivity());
		dbHelper.openDataBase();
		ImageView change = (ImageView) rootView
				.findViewById(R.id.changeSequence);

		minutesToDep = (EditText) rootView
				.findViewById(R.id.editText1MinToDep);
		minutesToDep.setFilters(new InputFilter[] { new MinMaxInputFilter("1",
				"60") });
		min= (TextView) rootView
				.findViewById(R.id.textView2);
		minutesToDep.setVisibility(View.INVISIBLE);
		minutesToDep.setEnabled(false);
		min.setVisibility(View.INVISIBLE);
		tb = (ToggleButton) rootView
				.findViewById(R.id.toggleButton1);

		tb.setChecked(true);
		tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {

				} else {

				}

			}
		});
		RadioGroup radioGroup = (RadioGroup) rootView
				.findViewById(R.id.radioGroup1);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.radio0)
				{
					minutesToDep.setVisibility(View.INVISIBLE);
					minutesToDep.setEnabled(false);
					min.setVisibility(View.INVISIBLE);
				}
				else if(checkedId==R.id.radio1){
					minutesToDep.setVisibility(View.VISIBLE);
					minutesToDep.setEnabled(true);
					min.setVisibility(View.VISIBLE);
				}
			}

		});

		sourceBusStop = (EditText) rootView.findViewById(R.id.source);
		destBusStop = (EditText) rootView.findViewById(R.id.destination);
		sourceBusStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),
						SearchBusStopActivity.class);
				Bundle args = new Bundle();
				args.putBoolean("SrchFragDest", true);
				args.putBoolean("source", true);
				i.putExtras(args);
				startActivityForResult(i, BUS_STOP_REQUEST);

			}

		});
		destBusStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),
						SearchBusStopActivity.class);
				Bundle args = new Bundle();
				args.putBoolean("SrchFragDest", true);
				args.putBoolean("source", false);
				i.putExtras(args);
				startActivityForResult(i, BUS_STOP_REQUEST);

			}

		});
		change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tmpN = sourceBusStop.getText().toString();
				String tmpId = sourceId;
				sourceBusStop.setText(destBusStop.getText().toString());
				destBusStop.setText(tmpN);
				sourceId = destId;
				destId = tmpId;

			}

		});
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if ((sourceId == null) || (destId == null)) {
					Toast.makeText(getActivity(), "Wybierz Przystanki!!!!!",
							Toast.LENGTH_SHORT).show();
				} else {
					SearchRouteFragment newFragment = new SearchRouteFragment();

					Bundle args = new Bundle();
					args.putString("sourceId", sourceId);
					args.putString("destId", destId);
					args.putString("sourceName", sourceNazwa);
					args.putString("destName", destNazwa);
					args.putBoolean("change", tb.isChecked());
					if(minutesToDep.isEnabled()){
						args.putString("minutesToDep", minutesToDep.getText().toString());
					}
					else args.putString("minutesToDep","");
					newFragment.setArguments(args);

					FragmentTransaction transaction = getFragmentManager()
							.beginTransaction();

					// Replace whatever is in the fragment_container view with
					// this fragment,
					// and add the transaction to the back stack so the user can
					// navigate back
					transaction.replace(R.id.layout, newFragment);
					transaction.addToBackStack(null);

					// Commit the transaction
					transaction.commit();
					// ***
				}
			}

		});
		/*
		 * showOnMapSource.setOnClickListener(new OnClickListener(){
		 * 
		 * @Override public void onClick(View arg0) { Intent i = new
		 * Intent(getActivity(), MapsActivity.class); Bundle args = new
		 * Bundle(); args.putBoolean("SrchFragDest", true);
		 * args.putBoolean("source", true); i.putExtras(args);
		 * startActivityForResult(i, BUS_STOP_REQUEST);
		 * 
		 * }});
		 */
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);

			if (requestCode == BUS_STOP_REQUEST) {
				boolean dir = data.getBooleanExtra("source", false);
				if (dir) {
					sourceNazwa = data.getStringExtra("nazwa");
					sourceId = data.getStringExtra("id");
					sourceBusStop.setText(sourceNazwa);

				}
				if (!dir) {
					destNazwa = data.getStringExtra("nazwa");
					destId = data.getStringExtra("id");
					destBusStop.setText(destNazwa);
				}

			}
		} catch (Exception ex) {
			/*
			 * Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT)
			 * .show();
			 */
		}

	}

}