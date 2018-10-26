package com.sokosimu.sokosimu.SSFragments.Categories;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.SSAdapters.HomeProductsAdapter;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {


    private List<Product> products;
    ProgressBar progressBar;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

       //
        fillRecyclerView(view);
        return view;
    }

    public void fillRecyclerView(View view){
        progressBar = view.findViewById(R.id.progressbar_products);
        RecyclerView rv = view.findViewById(R.id.productsRecyclerView);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(),2);
        products = new ArrayList<>();
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        HomeProductsAdapter adapter = new HomeProductsAdapter(products,R.id.category_root_fl);

        CategoryProductsLoader categoryProductsLoader = new CategoryProductsLoader(adapter,products, MainActivity.mainActivityContext,progressBar,getArguments().getInt("id"));
        categoryProductsLoader.GetProducts();

        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);
        adapter.notifyDataSetChanged();
    }
}
