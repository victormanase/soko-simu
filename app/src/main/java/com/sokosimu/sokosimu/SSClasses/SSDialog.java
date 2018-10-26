package com.sokosimu.sokosimu.SSClasses;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.sokosimu.sokosimu.MainActivity;



public class SSDialog {

    public SSDialog(String message, String title){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.mainActivityContext);
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);

        alertDialog.setCancelable(true);
        alertDialog.create().show();
    }
}
