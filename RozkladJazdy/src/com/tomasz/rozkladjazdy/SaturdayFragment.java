package com.tomasz.rozkladjazdy;

import com.example.rozkladjazdy.R;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SaturdayFragment  extends Fragment {
	String busStopId;
	String lineId;
	String nazwa;
	String linia;
	private DataBaseHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	  View rootView = inflater.inflate(R.layout.time_table, container, false);
    	 dbHelper = new DataBaseHelper(getActivity());
  		dbHelper.openDataBase();
  		Bundle extras = getActivity().getIntent().getExtras();
    	  busStopId=extras.getString("busStopId");
    	  lineId=extras.getString("lineId");
    	StringBuilder tmp = new StringBuilder(); 
		Cursor mCursor = dbHelper.fetchTimeTableSaturday(busStopId, lineId);
		if(mCursor.getCount()==0){
			TextView noDeparture = (TextView) rootView.findViewById(R.id.textView_no_departure);
			noDeparture.setText("Brak odjazdów w wskazanym dniu.");
		}
    
         //
		TableLayout tablelayout1;
		tablelayout1 = (TableLayout) rootView.findViewById(R.id.tabela);
		tablelayout1.setStretchAllColumns(true);
		tablelayout1.setShrinkAllColumns(true);
		TableRow rowTitle = new TableRow(getActivity());
		rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
		// title column/row
		TextView title = new TextView(getActivity());
		ImageView favorite = new ImageView(getActivity());
		favorite.setImageResource(R.drawable.favorite);
		String[] description = new String[2];
		description = dbHelper.getDescription(lineId);
		TextView desc = new TextView(getActivity());
		title.setText(description[1]);
		linia = description[1];
		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		title.setGravity(Gravity.CENTER);
		title.setTypeface(Typeface.SERIF, Typeface.BOLD);

		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 6;

		rowTitle.addView(title, params);
		rowTitle.addView(favorite);
		tablelayout1.addView(rowTitle);
		TableRow godz0 = new TableRow(getActivity()); 
		TableRow godz1 = new TableRow(getActivity());
		TableRow godz2 = new TableRow(getActivity());
		TableRow godz3 = new TableRow(getActivity());
		TableRow godz4 = new TableRow(getActivity());
		TableRow godz5 = new TableRow(getActivity());
		TableRow godz6 = new TableRow(getActivity());
		TableRow godz7 = new TableRow(getActivity());
		TableRow godz8 = new TableRow(getActivity());
		TableRow godz9 = new TableRow(getActivity());
		TableRow godz10 = new TableRow(getActivity());
		TableRow godz11 = new TableRow(getActivity());
		TableRow godz12 = new TableRow(getActivity());
		TableRow godz13 = new TableRow(getActivity());
		TableRow godz14 = new TableRow(getActivity());
		TableRow godz15 = new TableRow(getActivity());
		TableRow godz16 = new TableRow(getActivity());
		TableRow godz17 = new TableRow(getActivity());
		TableRow godz18 = new TableRow(getActivity());
		TableRow godz19 = new TableRow(getActivity());
		TableRow godz20 = new TableRow(getActivity());
		TableRow godz21 = new TableRow(getActivity());
		TableRow godz22 = new TableRow(getActivity());
		TableRow godz23 = new TableRow(getActivity());
		TableRow legenda = new TableRow(getActivity());
		
		desc.setText(description[0]);
		favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbHelper.insertFavoriteTimeTable(busStopId, lineId, nazwa,
						linia);
				Toast.makeText(getActivity(),
						"Dodano przystanek do ulubionych", Toast.LENGTH_LONG)
						.show();
					
			}

		});
		legenda.addView(desc, params);
		int godz_0 = 0;
		int godz_1 = 0;
		int godz_2 = 0;
		int godz_3 = 0;
		int godz_4 = 0;
		int godz_5 = 0;
		int godz_6 = 0;
		int godz_7 = 0;
		int godz_8 = 0;
		int godz_9 = 0;
		int godz_10 = 0;
		int godz_11 = 0;
		int godz_12 = 0;
		int godz_13 = 0;
		int godz_14 = 0;
		int godz_15 = 0;
		int godz_16 = 0;
		int godz_17 = 0;
		int godz_18 = 0;
		int godz_19 = 0;
		int godz_20 = 0;
		int godz_21 = 0;
		int godz_22 = 0;
		int godz_23 = 0;
		boolean gray=true;
		 while (mCursor.moveToNext()) {
			
			if (mCursor.getString(0).substring(0, 3).equals("00:")) {
				if (godz_0 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray)godz0.setBackgroundColor(R.color.gray);
					tV.setText("0  ");
					tV.setTextSize(28);
					godz0.addView(tV);
					godz_0++;
				}
				TextView tV_txt1 = new TextView(getActivity());

				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				godz0.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("01:")) {
				if (godz_1 == 0) {
					TextView tV = new TextView(getActivity());
					tV.setText("1  ");
					tV.setTextSize(28);
					godz1.addView(tV);
					godz_1++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				godz1.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("02:")) {
				if (godz_2 == 0) {
					TextView tV = new TextView(getActivity());
					tV.setText("2  ");
					tV.setTextSize(28);
					godz2.addView(tV);
					godz_2++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz2.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("03:")) {
				if (godz_3 == 0) {
					TextView tV = new TextView(getActivity());
					tV.setText("3  ");
					tV.setTextSize(28);
					godz3.addView(tV);
					godz_3++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz3.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("04:")) {
				if (godz_4 == 0) {
					TextView tV = new TextView(getActivity());
					tV.setText("4  ");
					tV.setTextSize(28);
					godz4.addView(tV);
					godz_4++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);

				godz4.addView(tV_txt1);

			}

			if (mCursor.getString(0).substring(0, 3).equals("05:")) {
				if (godz_5 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz5.setBackgroundColor(R.color.myBlue1);}
					 gray=!gray;
					tV.setText("5  ");
					tV.setTextSize(28);
					godz5.addView(tV);
					godz_5++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz5.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("06:")) {

				if (godz_6 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz6.setBackgroundColor(R.color.myBlue1);}
					 gray=!gray;
					tV.setText("6  ");
					tV.setTextSize(28);
					godz6.addView(tV);
					godz_6++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz6.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("07:")) {
				if (godz_7 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz7.setBackgroundColor(R.color.myBlue1);}
					 gray=!gray;
					tV.setText("7  ");
					tV.setTextSize(28);
					godz7.addView(tV);
					godz_7++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz7.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("08:")) {
				if (godz_8 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz8.setBackgroundColor(R.color.myBlue1);}
					 gray=!gray;
					tV.setText("8  ");
					tV.setTextSize(28);
					godz8.addView(tV);
					godz_8++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz8.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("09:")) {
				if (godz_9 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz9.setBackgroundColor(R.color.myBlue1);}
					 gray=!gray;
					tV.setText("9  ");
					tV.setTextSize(28);
					godz9.addView(tV);
					godz_9++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz9.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("10:")) {
				if (godz_10 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz10.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("10  ");
					tV.setTextSize(28);
					godz10.addView(tV);
					godz_10++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz10.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("11:")) {
				if (godz_11 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz11.setBackgroundColor(R.color.myBlue1);}
					 gray=!gray;
					tV.setText("11  ");
					tV.setTextSize(28);
					godz11.addView(tV);
					godz_11++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz11.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("12:")) {
				if (godz_12 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz12.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("12  ");
					tV.setTextSize(28);
					godz12.addView(tV);
					godz_12++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				tV_txt1.setTextSize(18);
				godz12.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("13:")) {
				if (godz_13 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz13.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("13  ");
					tV.setTextSize(28);
					godz13.addView(tV);
					godz_13++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz13.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("14:")) {
				if (godz_14 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz14.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("14  ");
					tV.setTextSize(28);
					godz14.addView(tV);
					godz_14++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz14.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("15:")) {
				if (godz_15 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz15.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("15  ");
					tV.setTextSize(28);
					godz15.addView(tV);
					godz_15++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz15.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("16:")) {
				if (godz_16 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz16.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("16  ");
					tV.setTextSize(28);
					godz16.addView(tV);
					godz_16++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz16.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("17:")) {
				if (godz_17 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz17.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("17  ");
					tV.setTextSize(28);
					godz17.addView(tV);
					godz_17++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz17.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("18:")) {
				if (godz_18 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz18.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("18  ");
					tV.setTextSize(28);
					godz18.addView(tV);
					godz_18++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz18.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("19:")) {
				if (godz_19 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz19.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("19  ");
					tV.setTextSize(28);
					godz19.addView(tV);
					godz_19++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz19.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("20:")) {
				if (godz_20 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz20.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("20  ");
					tV.setTextSize(28);
					godz20.addView(tV);
					godz_20++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz20.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("21:")) {
				if (godz_21 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz21.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("21  ");
					tV.setTextSize(28);
					godz21.addView(tV);
					godz_21++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz21.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("22:")) {
				if (godz_22 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz22.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("22  ");
					tV.setTextSize(28);
					godz22.addView(tV);
					godz_22++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz22.addView(tV_txt1);

			}
			if (mCursor.getString(0).substring(0, 3).equals("23:")) {
				if (godz_23 == 0) {
					TextView tV = new TextView(getActivity());
					if(gray){godz23.setBackgroundColor(R.color.myBlue1); }
					 gray=!gray;
					tV.setText("23  ");
					tV.setTextSize(28);
					godz23.addView(tV);
					godz_23++;
				}
				TextView tV_txt1 = new TextView(getActivity());
				tV_txt1.setText(mCursor.getString(0).substring(3));
				tV_txt1.setTextSize(18);
				tV_txt1.setGravity(Gravity.CENTER_HORIZONTAL);
				godz23.addView(tV_txt1);

			}

		}
		tablelayout1.addView(godz0);
		tablelayout1.addView(godz1);
		tablelayout1.addView(godz2);
		tablelayout1.addView(godz3);
		tablelayout1.addView(godz4);
		tablelayout1.addView(godz5);
		tablelayout1.addView(godz6);
		tablelayout1.addView(godz7);
		tablelayout1.addView(godz8);
		tablelayout1.addView(godz9);
		tablelayout1.addView(godz10);
		tablelayout1.addView(godz11);
		tablelayout1.addView(godz12);
		tablelayout1.addView(godz13);
		tablelayout1.addView(godz14);
		tablelayout1.addView(godz15);
		tablelayout1.addView(godz16);
		tablelayout1.addView(godz17);
		tablelayout1.addView(godz18);
		tablelayout1.addView(godz19);
		tablelayout1.addView(godz20);
		tablelayout1.addView(godz21);
		tablelayout1.addView(godz22);
		tablelayout1.addView(godz23);
		tablelayout1.addView(legenda);
		//
         //
    
        return rootView;
   
}
}