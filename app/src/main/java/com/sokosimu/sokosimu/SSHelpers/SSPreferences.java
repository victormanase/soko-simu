package com.sokosimu.sokosimu.SSHelpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.sokosimu.sokosimu.MainActivity;



public class SSPreferences {
    SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;
    private static final String PREFERENCES_FILE = "SSPreferencesFile";
    private static final int PREFERENCES_MODE_PRIVATE = 0;

    public SSPreferences(Context context){
        preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }

    public void storeString(String Key, String Value){
        preferencesEditor.putString(Key,Value);
        preferencesEditor.commit();
    }

    public String getString(String Key, String DefaultVAlue){
        return preferences.getString(Key,DefaultVAlue);
    }

    public void storeInt(String Key, int Value){
        preferencesEditor.putInt(Key,Value);
        preferencesEditor.commit();
    }

    public int getInt(String Key, int DefaultVaule){
        return preferences.getInt(Key,DefaultVaule);
    }

    public void storeBoolean(String Key, Boolean Value){
        preferencesEditor.putBoolean(Key, Value);
        preferencesEditor.commit();
    }

    public Boolean getBoolean(String Key, Boolean DefaultVaule){
        return preferences.getBoolean(Key,DefaultVaule);
    }
    public void storeFloat(String Key, Float Value){
        preferencesEditor.putFloat(Key,Value);
        preferencesEditor.commit();
    }

    public Float getFloat(String Key, Float DefaultView){
        return preferences.getFloat(Key, DefaultView);
    }

    public void storeLong(String Key, Long Value){
        preferencesEditor.putLong(Key, Value);
        preferencesEditor.commit();
    }


    public Long getLong(String Key, Long DefaultView){
        return preferences.getLong(Key, DefaultView);
    }

    public void clearPreference(String key){
        preferencesEditor.remove(key);
        preferencesEditor.commit();
    }
}
