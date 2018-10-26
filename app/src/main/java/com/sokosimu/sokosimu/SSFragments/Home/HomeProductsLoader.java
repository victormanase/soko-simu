package com.sokosimu.sokosimu.SSFragments.Home;

import android.content.Context;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sokosimu.sokosimu.SSAdapters.HomeProductsAdapter;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSModels.Product;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSValues.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;




public class HomeProductsLoader implements Response.Listener<JSONArray>, Response.ErrorListener {
    private HomeProductsAdapter homeProductsAdapter;
    private Context context;
    private SSPreferences ssPreferences;
    private List<Product> products;
    private Product product;
    private ProgressBar progressBar;

    public HomeProductsLoader(HomeProductsAdapter hpa, List<Product> p, Context ctx, ProgressBar prg){
        homeProductsAdapter = hpa;
        context = ctx;
        ssPreferences = new SSPreferences(ctx);
        products = p;
        progressBar = prg;
    }

    public void GetProducts(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();
        String url = Urls.PRODUCTS + "?api_token="+ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
        JsonArrayRequest jsArrRequest = new JsonArrayRequest (Request.Method.GET, url , jsonArray, this,this );

        jsArrRequest.setShouldCache(false);
        queue.add(jsArrRequest);
    }

    @Override
    public void onResponse(JSONArray response) {
        parseProducts(response);
    }

    private void parseProducts(JSONArray response) {
        int count = response.length();
        products.clear();

        for(int i=0; i<count;i++){
            try {
                product = new Product();
                JSONObject j = response.getJSONObject(i);

                product.productId = j.getInt("id");
                product.productName = j.getString("name");
                product.productDescription = j.getString("description");
                product.productPrice = "Tsh. "+ j.getString("price");
                product.productImage =j.getString("featuredImage1");
                product.productImage2 =j.getString("featuredImage2");
                product.productImage3 =j.getString("featuredImage3");
                product.productImage4 =j.getString("featuredImage4");
                product.productImage5 =j.getString("featuredImage5");
                product.productTime = j.getString("created_at");

                products.add(product);
            }catch (JSONException ex){}
        }
        progressBar.setVisibility(ProgressBar.GONE);
        homeProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
