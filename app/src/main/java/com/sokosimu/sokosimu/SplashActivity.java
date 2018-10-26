package com.sokosimu.sokosimu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sokosimu.sokosimu.SSHelpers.DPOTest;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean IsLogin = false;

       // DPOTest dpoTest = new DPOTest();
       // dpoTest.test(this);

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            boolean Islogin = prefs.getBoolean("Islogin", false);

            if(Islogin)
            {   // condition true means user is already login
                launchHome();
            }
            else
            {
                Login.state = getResources().getInteger(R.integer.logged_out);
                launchLogin();
                // condition false take it user on login form
            }
        } catch (Exception e) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putBoolean("Islogin", IsLogin).apply();
            Login.state = getResources().getInteger(R.integer.Installed);
            launchLogin();
        }
    }


    public void launchLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
