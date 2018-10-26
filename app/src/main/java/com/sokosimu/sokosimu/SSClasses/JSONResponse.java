package com.sokosimu.sokosimu.SSClasses;


import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import org.json.JSONArray;



public class JSONResponse implements Response.Listener<JSONArray>,Response.ErrorListener {

    public JSONArray response;
    public VolleyError volleyError;
    @Override
    public void onResponse(JSONArray rspns) {
        response = rspns;

        SSToast t = new SSToast(Integer.toString(rspns.length()));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        volleyError = error;
    }
}
