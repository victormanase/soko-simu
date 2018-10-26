package com.sokosimu.sokosimu.SSClasses;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSValues.Urls;

import org.json.JSONArray;



public class Requests {


    private Context context;
    private SSPreferences ssPreferences;
    public Requests(Context ctx){
        context = ctx;
        ssPreferences = new SSPreferences(ctx);
    }

    public void GetProducts() {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();
        JSONResponse jsonResponse = new JSONResponse();
        String url = Urls.PRODUCTS + "?api_token="+ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
        JsonArrayRequest jsArrRequest = new JsonArrayRequest (Request.Method.GET, url , jsonArray, jsonResponse,jsonResponse );
        queue.add(jsArrRequest);
    }

    public void GetCategories(){
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();
        JSONResponse jsonResponse = new JSONResponse();
        String url = Urls.CATEGORIES + "?api_token="+ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
        JsonArrayRequest jsArrRequest = new JsonArrayRequest (Request.Method.GET, url , jsonArray, jsonResponse,jsonResponse );
        queue.add(jsArrRequest);
    }
}
