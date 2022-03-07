package com.allinone.smartocity.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Util {


    public static void displayDailog(Context conten, String tittle, String message) {

        new AlertDialog.Builder(conten)
                .setTitle(tittle)
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                      SharedPref.deleteAllSharedPrefs();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}
