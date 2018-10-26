package com.sokosimu.sokosimu.SSFragments.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sokosimu.sokosimu.Login;
import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSClasses.SSFragmentHandler;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSValues.Urls;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    SSPreferences ssPreferences;
    public static TextView profileName, profileEmail,profilePhone,profileCount;
    LinearLayout wishlists, history, my_items;
    String api_token,name,email;
    private Button signOut,edit;
    SwipeRefreshLayout swipeRefreshLayout;

    public ProfileFragment() {
        // Required empty public constructor
        ssPreferences = new SSPreferences(MainActivity.mainActivityContext);
        api_token = ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
    }

    @Override
    public void onResume(){
        super.onResume();
        getProfile();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        signOut = view.findViewById(R.id.profile_signout_button);
        edit = view.findViewById(R.id.profile_edit_button);
        profileName = view.findViewById(R.id.user_name);
        profileEmail = view.findViewById(R.id.user_email);
        profilePhone = view.findViewById(R.id.user_phone);
        profileCount = view.findViewById(R.id.sell_count_textview);

        wishlists = view.findViewById(R.id.my_wishlist_click);
        history = view.findViewById(R.id.buy_history_click);
        my_items = view.findViewById(R.id.my_items_click);
        swipeRefreshLayout = view.findViewById(R.id.refreshProfile);


        setListeners();
        getProfile();


        return view;
    }

    private void setListeners() {
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("email",email);
                editProfileFragment.setArguments(bundle);
                SSFragmentHandler ssFragmentHandler = new SSFragmentHandler();
                ssFragmentHandler.openFragment(editProfileFragment,R.id.profile_root_fl);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSToast te = new SSToast("View my history");
            }
        });

        wishlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSToast te = new SSToast("View my wishlists");
            }
        });

        my_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSToast te = new SSToast("View my items");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfile();
            }
        });
    }

    private void getProfile() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.mainActivityContext);
        String url = Urls.PROFILE + "?api_token=" + api_token;
        //SSToast sf = new SSToast(api_token);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseAndFillData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    SSToast sf = new SSToast("failed"+error.getCause());
            }
        });
        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseAndFillData(JSONObject response) {
            try {
                JSONObject j = response.getJSONObject("customer");
                name = j.getString("name");
                email = j.getString("email");
                String emptyName = "Name";
                String emptyEmail = "Email";
                if(name!=null&&!name.equals("null")&&!name.equals(""))
                    profileName.setText(j.getString("name"));
                else{
                    profileName.setText(emptyName);
                    name = emptyName;
                }

                if(email!=null&&!email.equals("null")&&!email.equals(""))
                    profileEmail.setText(j.getString("email"));
                else{
                    profileEmail.setText(emptyEmail);
                    email = emptyEmail;
                }

                profileCount.setText(response.getString("productCount"));
                swipeRefreshLayout.setRefreshing(false);
            }catch (JSONException ex){
                swipeRefreshLayout.setRefreshing(false);
            }
    }

    private void logout(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainActivityContext);
        prefs.edit().putBoolean("Islogin", false).apply();
        Intent intent = new Intent(MainActivity.mainActivityContext, Login.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toast(String text){
        SSToast asdfa = new SSToast(text);
    }
}
