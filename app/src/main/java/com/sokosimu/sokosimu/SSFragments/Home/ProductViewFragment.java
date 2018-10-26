package com.sokosimu.sokosimu.SSFragments.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSClasses.CartHandler;
import com.sokosimu.sokosimu.SSClasses.SSDialog;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSFragments.Cart.CartFragment;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.SSValues.Urls;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductViewFragment extends Fragment {

    private List<String> images;
    private CarouselView carousel;
    private TextView productName,productDescription,productPrice;
    private Bundle bundle;
    Button addToCart,removeFromCart;
    private Product product;

    public ProductViewFragment() {
        // Required empty public constructor
    }

    public void loadContent(){
            images = new ArrayList<>();
            productDescription.setText(getArguments().getString("description"));
            productName.setText(getArguments().getString("name"));
            productPrice.setText(getArguments().getString("price"));
            product = new Product();
            product.productId = getArguments().getInt("id");
            product.productName =  getArguments().getString("name");
            product.productTime =  getArguments().getString("time");
            product.productPrice =  getArguments().getString("price");
            product.productDescription =  getArguments().getString("description");

            for (int i = 0; i< getArguments().getInt("imgCount"); i++){
                images.add(getArguments().getString("image"+i));
                int index = i+1;
                switch (index){
                    case 1:
                        product.productImage = getArguments().getString("image"+i);
                        break;
                    case 2:
                        product.productImage2 = getArguments().getString("image"+i);
                        break;
                    case 3:
                        product.productImage3 = getArguments().getString("image"+i);
                        break;
                    case 4:
                        product.productImage4 = getArguments().getString("image"+i);
                        break;
                    case 5:
                        product.productImage5 = getArguments().getString("image"+i);
                        break;
                }
            }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = savedInstanceState;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_view, container, false);

        carousel = view.findViewById(R.id.carouselProductView);
        productName = view.findViewById(R.id.product_name_view);
        productDescription = view.findViewById(R.id.product_description_view);
        productPrice = view.findViewById(R.id.product_price_view);
        addToCart= view.findViewById(R.id.add_to_cart);

        loadContent();
        fillCarouselView(carousel);
        addListeners();
        return view;
    }

    private void addListeners() {
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartHandler cartHandler = new CartHandler();

                String result = cartHandler.addToCart(product);

                CartFragment.cartProductsLoader.GetProducts();
                SSToast ssssss = new SSToast(result);
            }
        });
    }

    private void fillCarouselView(CarouselView cv) {

        cv.setPageCount(images.size());
        cv.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(MainActivity.mainActivityContext)
                        .load(Urls.IMAGES_LINK+images.get(position))
                        .apply(new RequestOptions()
                                    .placeholder(R.drawable.loading)
                                    .centerInside())
                        .into(imageView);

                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        });
    }
}
