package com.sokosimu.sokosimu.SSClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sokosimu.sokosimu.MainActivity;


public class SSFragmentHandler {
    public void openFragment(Fragment fragment, int rootframe){
        FragmentTransaction transaction = MainActivity.fragmentManager.beginTransaction();
        transaction.add(rootframe,fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void closeFragment(Fragment fragment, FragmentManager fragmentManager){
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
