package com.tomasz.rozkladjazdy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;

public class ShowBusStopDialog extends DialogFragment {
	String lineId;
	String lineName;
	String busStopId;
	private DataBaseHelper dbHelper;
	private SimpleCursorAdapter dataAdapter;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	dbHelper = new DataBaseHelper(getActivity());
		dbHelper.openDataBase();
		lineName=getArguments().getString("nazwa");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Cursor lineC=dbHelper.fetchLinesByName(lineName);
        lineC.moveToFirst();
        lineId=lineC.getString(0);
    	final Cursor c = dbHelper.fetchLineVariant(lineName);
		dataAdapter = new SimpleCursorAdapter(getActivity(), 
		        android.R.layout.simple_list_item_1, c,new String[] { "NAZWA" },   
		        new int[] { android.R.id.text1 });
		builder.setCursor(c,new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				c.moveToPosition( which);
				lineId=c.getString(0);
				 Intent i= new Intent(getActivity(),TimeTableActivity.class);
				Bundle args = new Bundle();
			    args.putString("busStopId", busStopId);
			    args.putString("lineId", lineId);
			    i.putExtras(args);
			   startActivity(i);
			   
				
			}}
		
		, "NAZWA");
		
        // Create the AlertDialog object and return it
        return builder.create();
    }
	

		 


}
