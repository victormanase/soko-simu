package com.sokosimu.sokosimu.SSAdapters;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSClasses.SSFragmentHandler;
import com.sokosimu.sokosimu.SSFragments.Home.ProductViewFragment;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.SSValues.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;



public class CategoryProductsAdapter extends RecyclerView.Adapter<CategoryProductsAdapter.viewHolder> {

    private List<Product> products;
    private int rootLayoutId;

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription, productTime;
        ImageView productImage;
        public LinearLayout linearLayout;
        public viewHolder(View itemView) {
            super(itemView);
            productImage =  itemView.findViewById(R.id.productImage);

            productName  =  itemView.findViewById(R.id.productName);

            productPrice  = itemView.findViewById(R.id.productPrice);

            productDescription = itemView.findViewById(R.id.productDescription);

            productTime = itemView.findViewById(R.id.productTime);

            linearLayout = itemView.findViewById(R.id.product_container);

        }
    }

    public CategoryProductsAdapter(List<Product> p, int RootLayoutId){
        this.products = p;
        rootLayoutId = RootLayoutId;
    }

    @Override
    public CategoryProductsAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryProductsAdapter.viewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.productName.setText(product.productName);
        holder.productPrice.setText(product.productPrice);
        holder.productDescription.setText(product.productDescription);
        holder.productTime.setText(product.productTime);

        if(product.productImage!=null)
            Picasso.with(MainActivity.mainActivityContext)
                    .load( Urls.IMAGES_LINK+product.productImage)
                    .into(holder.productImage);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductViewFragment productViewFragment = new ProductViewFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id",product.productId);
                bundle.putString("name",product.productName);
                bundle.putString("price",product.productPrice);
                bundle.putString("description",product.productDescription);
                int imgCount = 0;
                if(product.productImage != null && product.productImage!="null"){
                    bundle.putString("image"+imgCount,product.productImage);
                    imgCount++;
                }

                if(product.productImage2 != null && product.productImage2!="null"){
                    bundle.putString("image"+imgCount,product.productImage2);
                    imgCount++;
                }
                if(product.productImage3 != null && product.productImage3!="null"){
                    bundle.putString("image"+imgCount,product.productImage3);
                    imgCount++;
                }
                if(product.productImage4 != null && product.productImage4!="null"){
                    bundle.putString("image"+imgCount,product.productImage4);
                    imgCount++;
                }
                if(product.productImage5 != null && product.productImage5!="null"){
                    bundle.putString("image"+imgCount,product.productImage5);
                    imgCount++;
                }

                bundle.putInt("imgCount",imgCount);

                productViewFragment.setArguments(bundle);

                SSFragmentHandler ssFragmentHandler = new SSFragmentHandler();
                ssFragmentHandler.openFragment(productViewFragment,rootLayoutId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
