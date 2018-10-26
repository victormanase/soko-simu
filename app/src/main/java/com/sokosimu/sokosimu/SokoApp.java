package com.sokosimu.sokosimu;

import android.app.Application;
import android.content.Context;



public class SokoApp extends Application {
    public static SokoApp instance;

    public static SokoApp getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate(){
        instance = this;
        super.onCreate();
    }
}
