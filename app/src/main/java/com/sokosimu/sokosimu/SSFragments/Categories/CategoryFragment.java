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
import com.sokosimu.sokosimu.SSAdapters.CategoryAdapter;
import com.sokosimu.sokosimu.SSModels.Category;
import com.sokosimu.sokosimu.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    private View view;
    private ArrayList<Category> categories;
    private CategoriesLoader categoriesLoader;
    private ProgressBar progressBar;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        fillRecyclerView(view);
        return view;
    }


    public void fillRecyclerView(View view){
        categories = new ArrayList<>();

        progressBar = view.findViewById(R.id.progressbar_category);
        RecyclerView rv = view.findViewById(R.id.categoryRecyclerView);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(),3);
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        CategoryAdapter adapter = new CategoryAdapter(categories);

        categoriesLoader = new CategoriesLoader(adapter,categories, MainActivity.mainActivityContext,progressBar);

        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);
        adapter.notifyDataSetChanged();
    }
}
