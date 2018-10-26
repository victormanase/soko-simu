package com.sokosimu.sokosimu.SSClasses;

import android.content.Context;
import android.widget.Toast;

import com.sokosimu.sokosimu.MainActivity;



public class SSToast {
    public SSToast(String text){
        Toast.makeText(MainActivity.mainActivityContext,text,Toast.LENGTH_SHORT).show();
    }
    public SSToast(String text, Context context){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
