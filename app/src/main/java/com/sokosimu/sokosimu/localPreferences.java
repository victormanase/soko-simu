package com.sokosimu.sokosimu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;



public class localPreferences {
    private SharedPreferences sPref;
    public localPreferences(){
        SokoApp sa = new SokoApp();
        Context context = sa.getContext();
        sPref = context.getSharedPreferences("sokoAppPreferences", Context.MODE_PRIVATE);
    }

    public void putIntPreference(int pref, String prefName){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(prefName,pref);
        ed.commit();
    }

    public int readIntPreference(int defaultValue,  String prefName){
        return sPref.getInt(prefName,defaultValue);
    }
}
