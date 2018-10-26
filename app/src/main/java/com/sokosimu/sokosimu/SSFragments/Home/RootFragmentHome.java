package com.sokosimu.sokosimu.SSFragments.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sokosimu.sokosimu.R;

public class RootFragmentHome extends Fragment {


    public RootFragmentHome() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_fragment_home, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.home_root_fl,new HomeFragment());
        transaction.commit();
        return view;
    }

}
