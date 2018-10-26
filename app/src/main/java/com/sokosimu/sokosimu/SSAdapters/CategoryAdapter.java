package com.sokosimu.sokosimu.SSAdapters;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sokosimu.sokosimu.SSFragments.Categories.ProductsFragment;
import com.sokosimu.sokosimu.SSModels.Category;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSClasses.SSFragmentHandler;

import java.util.List;



public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder> {

    public class categoryViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;
        public ImageView categoryImage;
        LinearLayout categoryContainer;
        public categoryViewHolder(View itemView) {
            super(itemView);
            categoryImage =  itemView.findViewById(R.id.categoryImage);

            categoryName  =  itemView.findViewById(R.id.categoryName);

            categoryContainer = itemView.findViewById(R.id.category_container);
        }
    }

    private List<Category> categories;
    public CategoryAdapter(List<Category> c){
        this.categories = c;
    }

    @Override
    public CategoryAdapter.categoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false);
        return new CategoryAdapter.categoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.categoryViewHolder holder, int position) {
        final Category category = categories.get(position);
        holder.categoryName.setText(category.Name);
        holder.categoryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsFragment productsFragment = new ProductsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", category.Id);
                productsFragment.setArguments(bundle);
                SSFragmentHandler ssFragmentHandler = new SSFragmentHandler();
                ssFragmentHandler.openFragment(productsFragment,R.id.category_root_fl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
