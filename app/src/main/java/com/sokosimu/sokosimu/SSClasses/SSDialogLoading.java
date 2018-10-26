package com.sokosimu.sokosimu.SSClasses;

import android.app.ProgressDialog;
import android.content.Context;

import com.sokosimu.sokosimu.MainActivity;

public class SSDialogLoading {
    private ProgressDialog progressDialog;
    public SSDialogLoading(String message){
        progressDialog = new ProgressDialog(MainActivity.mainActivityContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
    }

    public SSDialogLoading(String message, Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
    }

    public void show(){
        progressDialog.show();
    }

    public void hide(){
        progressDialog.hide();
    }
}
