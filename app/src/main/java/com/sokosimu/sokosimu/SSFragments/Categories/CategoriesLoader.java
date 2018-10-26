package com.sokosimu.sokosimu.SSFragments.Categories;

import android.content.Context;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sokosimu.sokosimu.SSAdapters.CategoryAdapter;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSModels.Category;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSValues.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



public class CategoriesLoader implements Response.Listener<JSONArray>, Response.ErrorListener {

    private CategoryAdapter categoryAdapter;
    private Context context;
    private SSPreferences ssPreferences;
    private List<Category> categories;
    private Category category;
    private ProgressBar progressBar;

    public  CategoriesLoader(CategoryAdapter ct, List<Category> cts, Context ctx, ProgressBar prg){
        categories = cts;
        context = ctx;
        ssPreferences = new SSPreferences(ctx);
        categoryAdapter = ct;
        progressBar =prg;
        GetCategories();
    }

    private void GetCategories(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();
        String url = Urls.CATEGORIES + "?api_token=" + ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,jsonArray, this,this);

        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onResponse(JSONArray response) {
        parseCategories(response);
    }

    private void parseCategories(JSONArray response) {
        int count = response.length();
        categories.clear();

        for(int i=0; i<count;i++){
            try {
                category = new Category();
                JSONObject j = response.getJSONObject(i);

                category.Id = j.getInt("id");
                category.Name = j.getString("name");
                category.Image = j.getString("logo");

                categories.add(category);

            }catch (JSONException ex){

            }
        }
        categoryAdapter.notifyDataSetChanged();
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
