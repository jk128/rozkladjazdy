package com.tomasz.rozkladjazdy;

import com.example.rozkladjazdy.R;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public class DeleteFavoriteTimeTableDialog extends DialogFragment {
	private DataBaseHelper dbHelper;
	UpdateListListener listener;

	public DeleteFavoriteTimeTableDialog(UpdateListListener listener) {
		this.listener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.dialog_delete_favorite_time_table)
				.setPositiveButton(R.string.delete,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								listener.onListUpdate();

							}
						})
				.setNegativeButton(R.string.cencel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}