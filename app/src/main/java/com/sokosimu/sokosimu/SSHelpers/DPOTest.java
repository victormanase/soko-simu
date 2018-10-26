package com.sokosimu.sokosimu.SSHelpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.sokosimu.sokosimu.SSClasses.SSToast;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static com.android.volley.VolleyLog.TAG;



public class DPOTest {

    private Context ctx;
    public void test(Context context){
        //String url = "URL......";

        String url  = "https://secure1.sandbox.directpay.online/API/v6/";

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        ctx = context;

        com.sokosimu.sokosimu.SSHelpers.RequestQueue requestQueu = new com.sokosimu.sokosimu.SSHelpers.RequestQueue(context);

        RequestQueue requestQueue = requestQueu.getRequestQueue();


    }

    public void toast(String text){
        SSToast ss = new SSToast(text,ctx);
    }
}
