package com.sokosimu.sokosimu.SSFragments.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sokosimu.sokosimu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RootFragmentProfile extends Fragment {


    public RootFragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.profile_root_fl,new ProfileFragment());
        transaction.commit();
        return inflater.inflate(R.layout.fragment_root_fragment_profile, container, false);
    }

}
