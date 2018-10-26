package com.sokosimu.sokosimu.SSClasses;

import android.support.v4.app.FragmentManager;

import com.sokosimu.sokosimu.SSAdapters.SSBottomBarAdapter;
import com.sokosimu.sokosimu.SSFragments.Cart.RootFragmentCart;
import com.sokosimu.sokosimu.SSFragments.Categories.RootFragmentCategory;
import com.sokosimu.sokosimu.SSFragments.Home.RootFragmentHome;
import com.sokosimu.sokosimu.SSFragments.Profile.RootFragmentProfile;
import com.sokosimu.sokosimu.SSViews.NoSwipePager;



public class SSViewPager {
    private final FragmentManager fragmentManager;
    private NoSwipePager noSwipePager;
    private SSBottomBarAdapter pagerAdapter;

    public SSViewPager(NoSwipePager nsp, FragmentManager fm){
        noSwipePager = nsp;
        fragmentManager = fm;
        buildViewPager();
    }

    private void buildViewPager() {
        noSwipePager.setPagingEnabled(false);

        pagerAdapter = new SSBottomBarAdapter(fragmentManager);

        RootFragmentHome hf = new RootFragmentHome();
        RootFragmentCategory cf = new RootFragmentCategory();
        RootFragmentCart crf = new RootFragmentCart();
        RootFragmentProfile pf = new RootFragmentProfile();

        pagerAdapter.addFragements(hf);
        pagerAdapter.addFragements(cf);
        pagerAdapter.addFragements(crf);
        pagerAdapter.addFragements(pf);

        noSwipePager.setAdapter(pagerAdapter);
    }
}
