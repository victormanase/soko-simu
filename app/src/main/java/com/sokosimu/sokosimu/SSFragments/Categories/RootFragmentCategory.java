package com.sokosimu.sokosimu.SSFragments.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sokosimu.sokosimu.R;

public class RootFragmentCategory extends Fragment {
    public RootFragmentCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_fragment_category, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.category_root_fl,new CategoryFragment());
        transaction.commit();
        return view;
    }

}
