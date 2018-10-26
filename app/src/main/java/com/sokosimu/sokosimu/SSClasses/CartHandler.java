package com.sokosimu.sokosimu.SSClasses;

import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;

import java.util.ArrayList;
import java.util.List;



public class CartHandler {

    private SSPreferences ssPreferences;
    private int count;


    public CartHandler(){
        ssPreferences  = new SSPreferences(MainActivity.mainActivityContext);
        count = ssPreferences.getInt(SharedPrefKeys.CART_COUNT, SharedPrefDefaults.CART_COUNT);
    }

    public String addToCart(Product product){
        int id = product.productId;
        int index = count+1;
        if(count<=10){
            if(ifProductExistsInCart(product)){
                return "Product exists";
            }
            else{
                ssPreferences.storeInt(SharedPrefKeys.PRODUCT_ID+index,id);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_NAME+index,product.productName);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_PRICE+index,product.productPrice);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_IMAGE+index,product.productImage);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_IMAGE2+index,product.productImage2);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_IMAGE3+index,product.productImage3);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_IMAGE4+index,product.productImage4);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_IMAGE5+index,product.productImage5);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_DESCRIPTION+index,product.productDescription);
                ssPreferences.storeString(SharedPrefKeys.PRODUCT_TIME+index,product.productTime);

                ssPreferences.storeInt(SharedPrefKeys.CART_COUNT,index);
                return "Added to cart";
            }
        }else{
            return "Cart Full";
        }
    }

    public List<Product> getCartItems(){
        ssPreferences  = new SSPreferences(MainActivity.mainActivityContext);
        count = ssPreferences.getInt(SharedPrefKeys.CART_COUNT, SharedPrefDefaults.CART_COUNT);

        List<Product> artProducts = new ArrayList<>();
        for(int i=1;i<=count;i++){
            Product product = new Product();
            product.productId = ssPreferences.getInt(SharedPrefKeys.PRODUCT_ID+i,SharedPrefDefaults.PRODUCT_ID);
            product.productName = ssPreferences.getString(SharedPrefKeys.PRODUCT_NAME+i,SharedPrefDefaults.PRODUCT_NAME);
            product.productPrice = ssPreferences.getString(SharedPrefKeys.PRODUCT_PRICE+i,SharedPrefDefaults.PRODUCT_PRICE);
            product.productImage = ssPreferences.getString(SharedPrefKeys.PRODUCT_IMAGE+i,SharedPrefDefaults.PRODUCT_IMAGE);
            product.productImage2 = ssPreferences.getString(SharedPrefKeys.PRODUCT_IMAGE2+i,SharedPrefDefaults.PRODUCT_IMAGE2);
            product.productImage3 = ssPreferences.getString(SharedPrefKeys.PRODUCT_IMAGE3+i,SharedPrefDefaults.PRODUCT_IMAGE3);
            product.productImage4 = ssPreferences.getString(SharedPrefKeys.PRODUCT_IMAGE4+i,SharedPrefDefaults.PRODUCT_IMAGE4);
            product.productImage5 = ssPreferences.getString(SharedPrefKeys.PRODUCT_IMAGE5+i,SharedPrefDefaults.PRODUCT_IMAGE5);
            product.productDescription = ssPreferences.getString(SharedPrefKeys.PRODUCT_DESCRIPTION+i,SharedPrefDefaults.PRODUCT_DESCRIPTION);
            product.productTime = ssPreferences.getString(SharedPrefKeys.PRODUCT_TIME+i,SharedPrefDefaults.PRODUCT_TIME);
            artProducts.add(product);
        }

        return artProducts;
    }

    public String clearCart(){
        for(int i=1;i<=count;i++){
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_ID+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_NAME+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_PRICE+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_IMAGE+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_IMAGE2+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_IMAGE3+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_IMAGE4+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_IMAGE5+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_DESCRIPTION+i);
            ssPreferences.clearPreference(SharedPrefKeys.PRODUCT_TIME+i);
        }
        ssPreferences.clearPreference(SharedPrefKeys.CART_COUNT);
        count = ssPreferences.getInt(SharedPrefKeys.CART_COUNT, SharedPrefDefaults.CART_COUNT);
        return "cleared";
    }

    public boolean ifProductExistsInCart(Product product){
        boolean val = false;
        for(int i=1;i<=count;i++){
            if(product.productId==ssPreferences.getInt(SharedPrefKeys.PRODUCT_ID+i,SharedPrefDefaults.PRODUCT_ID)){
                val = true;
                break;
            }
        }
        return val;
    }
}
