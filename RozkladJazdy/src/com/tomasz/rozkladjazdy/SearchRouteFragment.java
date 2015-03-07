package com.tomasz.rozkladjazdy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.rozkladjazdy.R;
import com.tomasz.model.FoundRoute;

import android.app.ActionBar.LayoutParams;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class SearchRouteFragment extends Fragment {
	static final int BUS_STOP_REQUEST = 1; // The request code
	private DataBaseHelper dbHelper;
	private TextView tv;
	private TextView lineNumber;
	private TextView lineNumberChangeLine;
	private TextView startBusStopName;
	private TextView startTimeOnChangeBusStop;
	private TextView stopBusStopName;
	private TextView changeBusStopName;
	private TextView changeBusStopName1;
	private TextView startTime;
	private TextView stopTime;
	private TextView stopTimeonChangeLineBusStop;
	private boolean change;
	private String minutesToDep="";
	String sourceId;
	String destId;
	String sourceName;
	String destName;
	String s = "";
	StringBuffer sB = new StringBuffer(s);
	String departureTime = "brak";
	String departureTimeOnChangeBusStop = "brak";
	String arriveTime;
	private LinearLayout parentLayout;
	private List<FoundRoute> list = new ArrayList<FoundRoute>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		dbHelper = new DataBaseHelper(getActivity());

		dbHelper.openDataBase();
		Bundle bundle = this.getArguments();
		sourceId = bundle.getString("sourceId");
		destId = bundle.getString("destId");
		sourceName = bundle.getString("sourceName");
		destName = bundle.getString("destName");
		change=bundle.getBoolean("change",true);
		minutesToDep=bundle.getString("minutesToDep");

		View rootView = inflater.inflate(R.layout.search_route_fragment,
				container, false);

		parentLayout = (LinearLayout) rootView.findViewById(R.id.search_result);
		tv = (TextView) rootView.findViewById(R.id.notFound);
		tv.setVisibility(View.GONE);
		Button goBack = (Button) rootView.findViewById(R.id.button_go_back);
		goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				android.support.v4.app.FragmentManager fm = getFragmentManager();
				if (fm.getBackStackEntryCount() > 0) {

					fm.popBackStack();
				} else {

				}

			}
		});

		Context cont = getActivity();
		new LongOperation(cont, rootView).execute();

		return rootView;

	}

	public void setList(List<FoundRoute> list) {
		this.list = list;
	}

	private class LongOperation extends AsyncTask<Void, Void, List<FoundRoute>> {
		List<FoundRoute> found;
		private Context mContext;
		private View rootView;
		ProgressDialog dialog = new ProgressDialog(getActivity());

		public LongOperation(Context context, View rootView) {
			this.mContext = context;
			this.rootView = rootView;
		}

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Wyszukiwanie. Proszê czekaæ...");
			this.dialog.show();

		}

		@Override
		protected void onPostExecute(List<FoundRoute> found) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			TextView tv = (TextView) rootView.findViewById(R.id.notFound);

			parentLayout = (LinearLayout) rootView
					.findViewById(R.id.search_result);
			ScrollView scroll = new ScrollView(getActivity());
			scroll.setBackgroundColor(android.R.color.transparent);
			scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			LinearLayout searchResult = new LinearLayout(getActivity());
			searchResult.setBackgroundColor(android.R.color.transparent);
			searchResult.setOrientation(LinearLayout.VERTICAL);
			scroll.addView(searchResult);
			parentLayout.addView(scroll);
			if (found.isEmpty()) {
				tv.setVisibility(View.VISIBLE);
			} else {
				for (FoundRoute temp : found) {
					// ***
					LinearLayout LL = new LinearLayout(getActivity());
					LL.setBackgroundColor(R.color.white);
					LL.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.MATCH_PARENT);
					layoutParams.setMargins(0, 0, 0, 10);

					// **
					LinearLayout informations = new LinearLayout(getActivity());
					LinearLayout lines = new LinearLayout(getActivity());
					LinearLayout informations1 = new LinearLayout(getActivity());
					LinearLayout joinInformations = new LinearLayout(getActivity());
					lines.setOrientation(LinearLayout.VERTICAL);
					joinInformations.setOrientation(LinearLayout.VERTICAL);
					informations1.setOrientation(LinearLayout.VERTICAL);
					// informations
					// .setBackgroundColor(android.R.color.transparent);
					informations.setOrientation(LinearLayout.VERTICAL);
					
					LinearLayout.LayoutParams startbusStopAndTimelayoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.MATCH_PARENT);

					LinearLayout startbusStopAndTime = new LinearLayout(
							getActivity());
					startbusStopAndTime
							.setBackgroundColor(android.R.color.transparent);
					startbusStopAndTime.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout startbusStopAndTime1 = new LinearLayout(
							getActivity());
					startbusStopAndTime1
							.setBackgroundColor(android.R.color.transparent);
					startbusStopAndTime1.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout.LayoutParams informationslayoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.MATCH_PARENT);

					LinearLayout stopbusStopAndTime = new LinearLayout(
							getActivity());
					stopbusStopAndTime
							.setBackgroundColor(android.R.color.transparent);
					stopbusStopAndTime.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout stopbusStopAndTime1 = new LinearLayout(
							getActivity());
					stopbusStopAndTime1
							.setBackgroundColor(android.R.color.transparent);
					stopbusStopAndTime1.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout.LayoutParams stopbusStopAndTimelayoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.MATCH_PARENT);

					/*
					 * lineNumberChangeLine = new TextView(getActivity());
					 * lineNumberChangeLine.setTextSize(34);
					 * lineNumberChangeLine.setTextColor(R.color.myBlue1);
					 * lineNumberChangeLine.setText(temp.getNumberChangeLine());
					 * lineNumberChangeLine.setPadding(0, 0, 15, 0);
					 */

					lineNumber = new TextView(getActivity());
					lineNumber.setTextSize(34);
					lineNumber.setTextColor(R.color.myBlue1);
					lineNumber.setText(temp.getNumberLine());
					lineNumber.setPadding(0, 0, 15, 0);
					if (temp.getNumberChangeLine() != null) {
						lineNumberChangeLine = new TextView(getActivity());
						lineNumberChangeLine.setTextSize(34);
						lineNumberChangeLine.setTextColor(R.color.myBlue1);
						lineNumberChangeLine
								.setText(temp.getNumberChangeLine());
						lineNumberChangeLine.setPadding(0, 0, 15, 0);
					}
					lines.addView(lineNumber);
					if (temp.getNumberChangeLine() != null) {
						lines.addView(lineNumberChangeLine);
						stopBusStopName = new TextView(getActivity());
						stopBusStopName.setTextSize(18);
						stopBusStopName.setText(" " + destName);
						//
						changeBusStopName = new TextView(getActivity());
						changeBusStopName.setTextSize(18);
						changeBusStopName
								.setText(" " + temp.getChangeBusStop());
						changeBusStopName1 = new TextView(getActivity());
						changeBusStopName1.setTextSize(18);
						changeBusStopName1
								.setText(" " + temp.getChangeBusStop());
						stopBusStopName = new TextView(getActivity());
						stopBusStopName.setTextSize(18);
						stopBusStopName.setText(" " + destName);
						startTime = new TextView(getActivity());
						startTime.setTextSize(18);
						startTime.setText(temp.getDepTime());
						stopTime = new TextView(getActivity());
						stopTime.setTextSize(18);
						stopTime.setText(temp.getArriveTime());
					}

					startBusStopName = new TextView(getActivity());
					startBusStopName.setTextSize(18);
					startBusStopName.setText(" " + sourceName);
					stopBusStopName = new TextView(getActivity());
					stopBusStopName.setTextSize(18);
					stopBusStopName.setText(" " + destName);
					startTime = new TextView(getActivity());
					startTime.setTextSize(18);
					startTime.setText(temp.getDepTime());
					stopTime = new TextView(getActivity());
					stopTime.setTextSize(18);
					stopTime.setText(temp.getArriveTime());
					
					startTimeOnChangeBusStop = new TextView(getActivity());
					startTimeOnChangeBusStop.setTextSize(18);
					startTimeOnChangeBusStop.setText(temp.getDepTimeOnChangeBusStop());
					stopTimeonChangeLineBusStop = new TextView(getActivity());
					stopTimeonChangeLineBusStop.setTextSize(18);
					stopTimeonChangeLineBusStop.setText(temp.getArriveTimeOnDestSecLine());
					LL.addView(lines);

				//	LL.addView(lineNumberChangeLine);

					startbusStopAndTime.addView(startTime);
					startbusStopAndTime.addView(startBusStopName);
					stopbusStopAndTime.addView(stopTime);
					if (temp.getNumberChangeLine() != null) {
					startbusStopAndTime1.addView(startTimeOnChangeBusStop);
					startbusStopAndTime1.addView(changeBusStopName1);
					stopbusStopAndTime1.addView(stopTimeonChangeLineBusStop);
					stopbusStopAndTime1.addView(stopBusStopName);
					}
					if (temp.getNumberChangeLine() != null) {
						stopbusStopAndTime.addView(changeBusStopName);
					} else {	stopbusStopAndTime.addView(stopBusStopName);}
				
					informations.addView(startbusStopAndTime);
					informations.addView(stopbusStopAndTime);
				
					//
					informations.addView(startbusStopAndTime1);
					informations.addView(stopbusStopAndTime1);
					// */
					LL.addView(informations);
					if (temp.getNumberChangeLine() != null) {
					//LL.addView(informations1);
					}
					searchResult.addView(LL, layoutParams);

					// ***
					
					
				}
			}
		}

		@Override
		protected List<FoundRoute> doInBackground(Void... arg0) {

			return search(sourceId, destId);
		}

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	List<FoundRoute> search(String idSource, String idDestination) {
		// fetchBusStopByLine
		List<FoundRoute> foundRoutes = new ArrayList<FoundRoute>();
		Cursor source = dbHelper.fetchLinesByBus(idSource);
		Cursor destination = dbHelper.fetchLinesByBus(idDestination);
		String result = "";
		boolean czyZnal = false;
		boolean czyTaSamaLinia = false;
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
	
		SimpleDateFormat sdf = new SimpleDateFormat("Hmm");
		int currentTime;
		String s = "linie:";
		StringBuffer sB = new StringBuffer(s);
		Calendar c = Calendar.getInstance();
		if(!minutesToDep.equals("")){
		c.setTime(new Date());
		c.add(Calendar.MINUTE, Integer.parseInt(minutesToDep));
		 currentTime = Integer.parseInt(sdf.format(c.getTime()));
		}
		else
		 currentTime = Integer.parseInt(sdf.format(new Date()));
		int i = 0;
		String prevSourceLine = source.getString(1);
		while (!source.isAfterLast()) {
			boolean lineWasAlready = false;
			String sourceLineId = source.getString(0);
			if (i > 0) {
				if (prevSourceLine.equals(source.getString(1))) {
					lineWasAlready = true;
					prevSourceLine = source.getString(1);
				}
				prevSourceLine = source.getString(1);
			}

			i++;
			destination.moveToFirst();
			while (!destination.isAfterLast() && !lineWasAlready) {

				String destLineId = destination.getString(0);

				if (sourceLineId.equals(destLineId)) {
					Cursor routeBusStopIdSource = dbHelper.fetchRouteBusStopId(
							sourceId, sourceLineId);
					Cursor routeBusStopIdDest = dbHelper.fetchRouteBusStopId(
							destId, sourceLineId);
					int orderOfTheRouteSource = routeBusStopIdSource.getInt(1);
					int orderOfTheRouteDest = routeBusStopIdDest.getInt(1);
					int prevdDepTime = 0;
					int tripTime = 0;
					Cursor timeTable=null;
					if(day==Calendar.SUNDAY)
					{
						 timeTable = dbHelper
							.fetchTimeTableSunday(sourceId,sourceLineId);
					}
					else if(day==Calendar.SATURDAY){
						 timeTable = dbHelper
								.fetchTimeTableSaturday(sourceId,sourceLineId);
					}
					else
					{
					 timeTable = dbHelper
							.fetchTimeTableWeekDayNew(routeBusStopIdSource
							.getString(0));
					}

					boolean flag = false;
					while (!timeTable.isAfterLast() && !flag) {
						int hour = Integer.parseInt(timeTable.getString(0)
								.replaceAll("\\D+", ""));
						if (currentTime <= hour) {
							flag = true;
							prevdDepTime = hour;
							departureTime = arriveTime = timeTable.getString(0)
									.replaceAll("[^0-9:]", "");
						}
						timeTable.moveToNext();
					}
					timeTable.close();
					Cursor StopsOntheWay = dbHelper.fetchSpecificBusStops(
							orderOfTheRouteSource, orderOfTheRouteDest,
							sourceLineId);

					while (!StopsOntheWay.isAfterLast()) {
						Cursor schedule=null;
						if(day==Calendar.SUNDAY)
						{
							schedule=dbHelper
							.fetchTimeTableSundayNew(StopsOntheWay
									.getString(0));
						}
						else if(day==Calendar.SATURDAY){
							schedule = dbHelper
									.fetchTimeTableSaturdayNew(StopsOntheWay
											.getString(0));
						}
						else
						{
							 schedule = dbHelper
									.fetchTimeTableWeekDayNew(StopsOntheWay
											.getString(0));
						}
						
						boolean fl = false;
						while (!schedule.isAfterLast() && !fl && flag) {
							int hour = Integer.parseInt(schedule.getString(0)
									.replaceAll("\\D+", ""));

							if (prevdDepTime <= hour) {
								if (hour - prevdDepTime > 20) {
									tripTime += hour - prevdDepTime - 40;
									prevdDepTime = hour;
								} else {
									tripTime += hour - prevdDepTime;
									prevdDepTime = hour;
								}
								fl = true;
							}
							schedule.moveToNext();
						}
						schedule.close();
						StopsOntheWay.moveToNext();
					}
					StopsOntheWay.close();
					if (flag &&  tripTime>0) {
						SimpleDateFormat df = new SimpleDateFormat("HH:mm");
						Date d = null;
						try {
							d = df.parse(arriveTime);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						cal.add(Calendar.MINUTE, tripTime);
						FoundRoute foundRoute = new FoundRoute();
						foundRoute.setDepTime(departureTime);
						foundRoute.setArriveTime(df.format(cal.getTime()));
						foundRoute.setNumberLine(source.getString(1));
						foundRoutes.add(foundRoute);
					}
				}
				destination.moveToNext();
			}
			source.moveToNext();

		}
		// ****
		// dla 2 tras
		if(change)
		{
		source.moveToFirst();
		destination.moveToFirst();
		i = 0;
		prevSourceLine = source.getString(1);
		while (!source.isAfterLast()&& !(source.getString(1).equals("N1")|| source.getString(1).equals("N2") || source.getString(1).equals("N3"))) {
			boolean sourceLineWasAlready = false;
			// String sourceLineId = source.getString(0);
			if (i > 0) {
				if (prevSourceLine.equals(source.getString(1))) {
					sourceLineWasAlready = true;
					prevSourceLine = source.getString(1);
				}
				prevSourceLine = source.getString(1);
			}

			i++;
			destination.moveToFirst();
			int j = 0;
			String prevDestLine = destination.getString(1);
			while (!destination.isAfterLast() && !sourceLineWasAlready && !(destination.getString(1).equals("N1")||destination.getString(1).equals("N2")|| destination.getString(1).equals("N1"))) {
				boolean destLineWasAlready = false;
				// String sourceLineId = source.getString(0);
				if (j > 0) {
					if (prevDestLine.equals(destination.getString(1))) {
						destLineWasAlready = true;
						prevDestLine = destination.getString(1);
					}
					prevDestLine = destination.getString(1);
				}

				j++;
				// ****
				String sourceLineId = source.getString(0);
				String destLineId = destination.getString(0);
				Cursor busStopsOrigin = dbHelper
						.fetchBusStopByLine(sourceLineId);
				Cursor busStopsDestination = dbHelper
						.fetchBusStopByLine(destLineId);

				czyZnal = false;
				while (!busStopsOrigin.isAfterLast()
						&& (!czyZnal)
						&& !(source.getString(1).equals(destination
								.getString(1))) && !destLineWasAlready) {
					busStopsDestination.moveToFirst();
					while (!busStopsDestination.isAfterLast() && (!czyZnal)) {
						// porownanie
						if (busStopsOrigin.getInt(0) == busStopsDestination
								.getInt(0)) {
							Cursor routeBusStopIdSource = dbHelper.fetchRouteBusStopId(
									sourceId, sourceLineId);
							
							Cursor routeBusStopIdChange = dbHelper.fetchRouteBusStopId(
									Integer.toString(busStopsDestination
											.getInt(0)), sourceLineId);
							Cursor routeBusStopIdChangeSecLine = dbHelper.fetchRouteBusStopId(
									Integer.toString(busStopsDestination
											.getInt(0)), destLineId);
							Cursor routeBusStopIdSecLineDest = dbHelper.fetchRouteBusStopId(
									destId, destLineId);
							Cursor routeBusStopIdSecLineSource= dbHelper.fetchRouteBusStopId(
									Integer.toString(busStopsDestination
											.getInt(0)), destLineId);
							int orderOfTheRouteSource = routeBusStopIdSource.getInt(1);
							int orderOfTheRouteDest = routeBusStopIdChange.getInt(1);
							int prevdDepTime = 0;
							int tripTime = 0;
							
							Cursor timeTable=null;
							if(day==Calendar.SUNDAY)
							{
								timeTable=dbHelper
								.fetchTimeTableSundayNew(routeBusStopIdSource
										.getString(0));
							}
							else if(day==Calendar.SATURDAY){
								timeTable = dbHelper
										.fetchTimeTableSaturdayNew(routeBusStopIdSource
												.getString(0));
							}
							else
							{
								timeTable = dbHelper
										 .fetchTimeTableWeekDayNew(routeBusStopIdSource
													.getString(0));
							}

							boolean flag = false;
							while (!timeTable.isAfterLast() && !flag) {
								int hour = Integer.parseInt(timeTable.getString(0)
										.replaceAll("[^0-9]",""));
								if (currentTime <= hour) {
									flag = true;
									prevdDepTime = hour;
									departureTime = arriveTime = timeTable.getString(0)
											.replaceAll("[^0-9:]", "");
								}
								timeTable.moveToNext();
							}
							timeTable.close();
							Cursor StopsOntheWay = dbHelper.fetchSpecificBusStops(
									orderOfTheRouteSource, orderOfTheRouteDest,
									sourceLineId);

							while (!StopsOntheWay.isAfterLast()) {
								
								Cursor schedule=null;
								if(day==Calendar.SUNDAY)
								{
									schedule= dbHelper
											.fetchTimeTableSundayNew(StopsOntheWay
													.getString(0));
								}
								else if(day==Calendar.SATURDAY){
									schedule=dbHelper
										.fetchTimeTableSaturdayNew(StopsOntheWay
												.getString(0));
								}
								else
								{
									schedule=  dbHelper
											.fetchTimeTableWeekDayNew(StopsOntheWay
													.getString(0));
								}
								
								boolean fl = false;
								while (!schedule.isAfterLast() && !fl && flag) {
									int hour = Integer.parseInt(schedule.getString(0)
											.replaceAll("[^0-9]",""));

									if (prevdDepTime <= hour) {
										if (hour - prevdDepTime > 20) {
											tripTime += hour - prevdDepTime - 40;
											prevdDepTime = hour;
										} else {
											tripTime += hour - prevdDepTime;
											prevdDepTime = hour;
										}
										fl = true;
									}
									schedule.moveToNext();
								}
								schedule.close();
								StopsOntheWay.moveToNext();
							}
							StopsOntheWay.close();
							SimpleDateFormat df = new SimpleDateFormat("HH:mm");
							Date d = null;
							try {
								d = df.parse(arriveTime);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Calendar cal = Calendar.getInstance();
							cal.setTime(d);
							cal.add(Calendar.MINUTE, tripTime);
							
							//proba wyznaczenia czasu dla przesiadkowej lini
							int prevdDepTimeSecLine = 0;
							int tripTimeSecLine = 0;
							
							Cursor timeTableSecondLine=null;
							if(day==Calendar.SUNDAY)
							{
								timeTableSecondLine= dbHelper
										.fetchTimeTableSundayNew(routeBusStopIdChangeSecLine
												.getString(0));
							}
							else if(day==Calendar.SATURDAY){
								timeTableSecondLine=dbHelper
										.fetchTimeTableSaturdayNew(routeBusStopIdChangeSecLine
												.getString(0));
							}
							else
							{
								timeTableSecondLine=  dbHelper
										.fetchTimeTableWeekDayNew(routeBusStopIdChangeSecLine
												.getString(0));
							}
							String arriveTimeOnChangeBusStop=df.format(cal.getTime());
							
							int arrivetimeint=Integer.parseInt(arriveTimeOnChangeBusStop.replaceAll(":", ""));
							boolean flag1 = false;
							while (!timeTableSecondLine.isAfterLast() && !flag1) {
								int hour = Integer.parseInt(timeTableSecondLine.getString(0).
										replaceAll("[^0-9]",""));
								
								
								if (arrivetimeint <= hour) {
									flag1 = true;
									prevdDepTime = hour;
									departureTimeOnChangeBusStop = timeTableSecondLine.getString(0)
											.replaceAll("[^0-9:]", "");
									departureTimeOnChangeBusStop.replace("a", "");
								}
								timeTableSecondLine.moveToNext();
							}
							if(flag1 && flag && tripTime>0){
							////proba wyznaczenia czasu dla przesiadkowej lini
							//wyznaczenie czasu podrozy na przesiadkowej lini
							 tripTime=0;
							int orderOfTheRouteSourceSecLine = routeBusStopIdSecLineSource.getInt(1);
							int orderOfTheRouteDestSecLine =  routeBusStopIdSecLineDest.getInt(1); //routeBusStopIdChangeSecLine
							Cursor StopsOntheWay1 = dbHelper.fetchSpecificBusStops(
									orderOfTheRouteSourceSecLine, orderOfTheRouteDestSecLine,
									destLineId);

							while (!StopsOntheWay1.isAfterLast()) {
							
								Cursor schedule=null;
								if(day==Calendar.SUNDAY)
								{
									schedule= dbHelper
											.fetchTimeTableSundayNew(StopsOntheWay1
													.getString(0));
								}
								else if(day==Calendar.SATURDAY){
									schedule=dbHelper
											.fetchTimeTableSaturdayNew(StopsOntheWay1
													.getString(0));
								}
								else
								{
									schedule=  dbHelper
											.fetchTimeTableWeekDayNew(StopsOntheWay1
													.getString(0));
								}
								boolean fl = false;
								while (!schedule.isAfterLast() && !fl && flag) {
									int hour = Integer.parseInt(schedule.getString(0)
											.replaceAll("\\D+", ""));

									if (prevdDepTime <= hour) {
										if (hour - prevdDepTime > 20) {
											tripTime += hour - prevdDepTime - 40;
											prevdDepTime = hour;
										} else {
											tripTime += hour - prevdDepTime;
											prevdDepTime = hour;
										}
										fl = true;
									}
									schedule.moveToNext();
								}
								schedule.close();
								StopsOntheWay1.moveToNext();
							}
							
							if(tripTime>0)
							{
							SimpleDateFormat dfa = new SimpleDateFormat("HH:mm");
							
							Date da = null;
							try {
								da = df.parse(departureTimeOnChangeBusStop);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Calendar cale = Calendar.getInstance();
							cale.setTime(da);
							cale.add(Calendar.MINUTE,tripTime);
							//wyzn czasu podr na przes linie
							
							
							FoundRoute foundRoute = new FoundRoute();
							foundRoute.setDepTime(departureTime);
							foundRoute.setArriveTime(df.format(cal.getTime()));
							foundRoute.setDepTimeOnChangeBusStop(departureTimeOnChangeBusStop);
							foundRoute.setArriveTimeOnDestSecLine(dfa.format(cale.getTime()));
							foundRoute.setNumberChangeLine(destination
									.getString(1));
							foundRoute.setChangeBusStop(busStopsDestination
									.getString(1));
							foundRoute.setNumberLine(source.getString(1));
							foundRoutes.add(foundRoute);
							}
							}	
							czyZnal = true;

						}

						busStopsDestination.moveToNext();

					}

					busStopsOrigin.moveToNext();
				}
				busStopsDestination.close();
				busStopsOrigin.close();
				destination.moveToNext();
			}
			source.moveToNext();

		}
		// dla 2 tras
		}
		source.close();
		destination.close();

		return foundRoutes;

	}

}