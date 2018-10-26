package com.sokosimu.sokosimu.SSFragments.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.SSAdapters.HomeProductsAdapter;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private View view;
    private List<Product> products;
    private CarouselView cv;
    private int[] carouselImages = {R.drawable.image2,R.drawable.image1};
    public static HomeProductsLoader homeProductsLoader;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        cv = view.findViewById(R.id.carouselView);

        fillRecyclerView(view);
        fillCarouselView(cv);
        return view;
    }

    private void fillCarouselView(CarouselView cv) {
        cv.setPageCount(carouselImages.length);
        cv.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(carouselImages[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
    }

    public void fillRecyclerView(View view){
        products = new ArrayList<>();
        RecyclerView rv = view.findViewById(R.id.homeRecyclerView);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        HomeProductsAdapter adapter = new HomeProductsAdapter(products,R.id.home_root_fl);

        ProgressBar progressBar = view.findViewById(R.id.progressbar_home);

        homeProductsLoader = new HomeProductsLoader(adapter,products, MainActivity.mainActivityContext,progressBar);
        homeProductsLoader.GetProducts();

        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);
        adapter.notifyDataSetChanged();

    }
}
