package com.sokosimu.sokosimu.SSFragments.Cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSAdapters.CartProductsAdapter;
import com.sokosimu.sokosimu.SSAdapters.HomeProductsAdapter;
import com.sokosimu.sokosimu.SSClasses.CartHandler;
import com.sokosimu.sokosimu.SSClasses.SSDialog;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSFragments.Home.HomeProductsLoader;
import com.sokosimu.sokosimu.SSHelpers.RequestQueue;
import com.sokosimu.sokosimu.SSHelpers.XMLParser;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.SSValues.DPOnline;
import com.sokosimu.sokosimu.SSValues.Urls;
import com.sokosimu.sokosimu.SSValues.XMLResponseValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class CartFragment extends Fragment implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    private Button purchaseButton,clearButton;
    private TextView frTotal;
    private RecyclerView rv;
    private CartHandler cartHandler;
    private List<Product> products;
    private LinearLayout no_items;
    private ProgressDialog progressDialog;
    private String order_id;
    public static CartProductsLoader cartProductsLoader;
    private float total;
    private boolean mAlreadyLoaded = false;

    public CartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =    inflater.inflate(R.layout.fragment_cart, container, false);

        cartHandler = new CartHandler();
        purchaseButton = v.findViewById(R.id.fragment_cart_purchase);
        clearButton = v.findViewById(R.id.fragment_cart_clear);
        rv = v.findViewById(R.id.fragment_cart_recyclerview);
        frTotal = v.findViewById(R.id.fragment_cart_total);
        no_items = v.findViewById(R.id.fragment_cart_cart_mpty);

        cartProductsLoader = new CartProductsLoader(products,frTotal,rv,no_items);
        fillCart();
        purchaseButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


        return v;

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == purchaseButton.getId()) {
            purchase();
        }else if(i==clearButton.getId()){
            clear();
        }
    }

    private void clear() {
        cartHandler.clearCart();
        fillCart();
    }

    private void purchase() {
        String url = Urls.ORDERS+ "?api_token=" + Urls.API_TOKEN;
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.mainActivityContext);

        int size = cartProductsLoader.products.size();

        url = url + "&0=" + size;
        for(int i=0;i<cartProductsLoader.products.size();i++){
            int id = cartProductsLoader.products.get(i).productId;
            int index = i+1;
            url = url + "&" + index + "=" + id;
        }
        //SSToast c = new SSToast(url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject j1 = response.getJSONObject("order");
                            String date = j1.getString("created_at");
                            String order_id = j1.getString("id");
                            finishPurchase(date,order_id);

                        }catch (JSONException J){
                            SSToast vwer = new SSToast(J.getMessage());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SSToast q = new SSToast("purchase_error:"+error.getMessage());
            }

        });

        requestQueue.add(jsonObjectRequest);
        progressDialog = ProgressDialog.show(MainActivity.mainActivityContext,null,"Loading, please wait..",true,false);

    }

    private void finishPurchase(String date, String id){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String mydate = simpleDateFormat.format(calendar.getTime())+"";
        DPOnline dpOnline = new DPOnline(MainActivity.mainActivityContext);

        total = cartProductsLoader.getSum();
        dpOnline.createToken(total,"trial service", date, Integer.parseInt(id), this,this);

        order_id = id;
     }

    private void launchBrowser(String createToken){
        Uri uri = Uri.parse(Urls.PAYMENT_PAGE_URL+createToken);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(launchBrowser);
        clear();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        SSToast ss = new SSToast(error.getMessage());
    }

    @Override
    public void onResponse(String response) {
        progressDialog.hide();
        XMLParser parser = new XMLParser();
        final XMLResponseValues xmlResponseValues = parser.parse(response);
        if(xmlResponseValues.TransToken!=null){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.mainActivityContext);
            alertDialog.setMessage("Please complete the purchase, go to the payment page");
            alertDialog.setTitle("Complete");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    launchBrowser(xmlResponseValues.TransToken);
                    sendToken(xmlResponseValues.TransToken);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setCancelable(false);
            alertDialog.create().show();
        }
        else{
            SSToast werqwer = new SSToast(xmlResponseValues.ResultExplanation);
        }
    }

    private void sendToken(String transToken) {
       com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.mainActivityContext);
        String url = Urls.SEND_TOKEN_ORDERS + order_id + "?api_token=" + Urls.API_TOKEN +
                "&create_token="+transToken;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //SSToast sssss = new SSToast("sendToken:"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SSToast asdfasdfasdf = new SSToast("sendToken: "+ error.getMessage()+ error.networkResponse.statusCode);
            }
        });
        requestQueue.add(stringRequest);
    }

    public  void fillCart(){
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(),1);
        cartProductsLoader = new CartProductsLoader(products,frTotal,rv,no_items);
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        cartProductsLoader.GetProducts();
    }

    public void toast(String text){
        SSToast fasdfasdf= new SSToast(text);
    }
}
