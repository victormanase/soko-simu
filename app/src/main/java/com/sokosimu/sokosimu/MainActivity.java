package com.sokosimu.sokosimu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.sokosimu.sokosimu.SSFragments.Cart.CartFragment;
import com.sokosimu.sokosimu.SSFragments.Home.HomeFragment;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSViews.NoSwipePager;
import com.sokosimu.sokosimu.SSClasses.BottomNavigation;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSClasses.SSViewPager;

public class MainActivity extends AppCompatActivity {
    public static AHBottomNavigation bottomNavigation;
    private NoSwipePager nsp;
    public static BottomNavigation bN;
    public static Context mainActivityContext;
    public static FragmentManager fragmentManager;
    public static boolean newpost = false;
    public static String api_token;
    SSPreferences ssPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityContext = this;
        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_main);
        ssPreferences = new SSPreferences(this);
        api_token = ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bN = new BottomNavigation(bottomNavigation,this);

        nsp = findViewById(R.id.viewPager);
        SSViewPager ssvp = new SSViewPager(nsp,getSupportFragmentManager());
        nsp.setOffscreenPageLimit(5);
        setListener();

        ImageView addButton = findViewById(R.id.new_post_toolbar);
        ImageView searchButton = findViewById(R.id.search_toolbar);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(getApplication().getApplicationContext(),NewProductActivity.class);
                startActivity(intt);
                overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSToast t = new SSToast("search");
            }
        });
    }

    private void setListener() {
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                int cartPosition = bottomNavigation.getItemsCount() - 2;

                if (!wasSelected) {
                    nsp.setCurrentItem(position,false);

//                    if (bN.notificationVisible && position == cartPosition) {
//                        bottomNavigation.setNotification(new AHNotification(), cartPosition);
//                        bN.notificationVisible = false;
//                    }
                }
                else if(position == 0){
                    HomeFragment.homeProductsLoader.GetProducts();
                }
                return true;

            }
        });
    }

}
