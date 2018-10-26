package com.sokosimu.sokosimu.SSFragments.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSAdapters.CartProductsAdapter;
import com.sokosimu.sokosimu.SSClasses.CartHandler;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSModels.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class CartProductsLoader {
    private CartHandler cartHandler;
    public List<Product> products;
    private TextView frTotal;
    private RecyclerView rv;
    private LinearLayout no_items;
    private float sum = 0;

    CartProductsLoader(List<Product> pr, TextView tx, RecyclerView r, LinearLayout l){
        frTotal = tx;
        products = pr;
        rv = r;
        no_items = l;
    }

    public void GetProducts(){
        products = new ArrayList<>();
        cartHandler = new CartHandler();
        products = cartHandler.getCartItems();
        if(products!=null&&products.size()>0){
            MainActivity.bN.setNotification(""+products.size());
            CartProductsAdapter adapter = new CartProductsAdapter(products, R.id.fragment_cart_recyclerview);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            sum = 0;
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            for (int i = 0;i<products.size();i++){
                String price = products.get(i).productPrice;
                price = price.replace("Tsh. ","");
                price = price.replace(',',' ');
                price = price.replace(" ","");
                float total =  Float.parseFloat(price);
                sum = sum + total;
            }
            String summ = "Ths. "+formatter.format(sum);
            frTotal.setText(summ);
            no_items.setVisibility(View.GONE);
        }
        else{
            CartProductsAdapter adapter = new CartProductsAdapter(products, R.id.fragment_cart_recyclerview);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
           // String s = "empty cart";
            //SSToast sm = new SSToast(s);
            String total = "Tshs. 0";
            frTotal.setText(total);
            MainActivity.bottomNavigation.setNotification(new AHNotification(),2);
            MainActivity.bN.notificationVisible = false;
            no_items.setVisibility(View.VISIBLE);
        }
    }

    public float getSum(){
        return sum;
    }
}
