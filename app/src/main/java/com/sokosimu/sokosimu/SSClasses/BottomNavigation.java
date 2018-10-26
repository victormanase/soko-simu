package com.sokosimu.sokosimu.SSClasses;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.sokosimu.sokosimu.R;



public class BottomNavigation {
    private AHBottomNavigation bottomNavigation;
    private Context context;
    public Boolean notificationVisible;
    public BottomNavigation(AHBottomNavigation bN, Context ct){
        bottomNavigation = bN;
        context = ct;
        buildNavigation();
    }

    private void buildNavigation() {
        AHBottomNavigationItem item;
        item = new AHBottomNavigationItem("Home", R.drawable.ic_home);
        bottomNavigation.addItem(item);
        item = new AHBottomNavigationItem("Categories", R.drawable.ic_categories);
        bottomNavigation.addItem(item);
        item = new AHBottomNavigationItem("Cart", R.drawable.ic_cart);
        bottomNavigation.addItem(item);
        item = new AHBottomNavigationItem("Profile", R.drawable.ic_profile);
        bottomNavigation.addItem(item);

        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setDefaultBackgroundColor(color(R.color.colorWhite));
        bottomNavigation.setAccentColor(color(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(color(R.color.colorBlack));

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(false);
    }

    private int color(@ColorRes int color){
        return ContextCompat.getColor(context,color);
    }

    public void setNotification(String count){
        AHNotification notification = new AHNotification.Builder()
                .setText(count)
                .setBackgroundColor(color(R.color.colorPrimary))
                .setTextColor(color(R.color.colorWhite))
                .build();

        bottomNavigation.setNotification(notification,bottomNavigation.getItemsCount()-2);
        notificationVisible = true;
    }
}
