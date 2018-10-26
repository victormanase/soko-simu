package com.sokosimu.sokosimu.SSFragments.Profile;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sokosimu.sokosimu.MainActivity;
import com.sokosimu.sokosimu.R;
import com.sokosimu.sokosimu.SSClasses.SSDialog;
import com.sokosimu.sokosimu.SSClasses.SSDialogLoading;
import com.sokosimu.sokosimu.SSClasses.SSFragmentHandler;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSValues.Urls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {


    private String name, email;
    private EditText nameEt, emailEt;
    private Button saveButton;
    private SSPreferences ssPreferences;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    private void loadContents(){
        try {
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            nameEt.setText(name);
            emailEt.setText(email);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ssPreferences = new SSPreferences(MainActivity.mainActivityContext);
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        nameEt = v.findViewById(R.id.fragment_edit_profile_name);
        emailEt = v.findViewById(R.id.fragment_edit_profile_email);
        saveButton = v.findViewById(R.id.fragment_edit_profile_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContents();
            }
        });
        loadContents();
        return v;
    }

    private SSDialogLoading ssDialog;

    private void saveContents(){
        email = emailEt.getText().toString();name = nameEt.getText().toString();
        if(!name.equals("")&&name.length()>4){
            if(isEmailCorrect(email)){
                ssDialog = new SSDialogLoading("Saving...");
                ssDialog.show();
                proceedEdit();
            }else{
                toast("Enter a valid email please");
            }
        }else{
            toast("Enter a valid name please");
        }
    }

    private void proceedEdit() {
        String url = Urls.PROFILE+
                ssPreferences.getString(SharedPrefKeys.USER_ID, SharedPrefDefaults.USER_ID)+
                "?api_token="+ssPreferences.getString(SharedPrefKeys.API_TOKEN,SharedPrefDefaults.API_TOKEN)+
                "&name="+name+
                "&email="+email;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.mainActivityContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ProfileFragment.profileName.setText(response.getString("name"));
                            if(isEmailCorrect(response.getString("email")))
                                ProfileFragment.profileEmail.setText(response.getString("email"));
                        } catch (JSONException e) {
                        }
                        ssDialog.hide();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.mainActivityContext);
                        alertDialog.setMessage("Profile Edited Successfully");
                        alertDialog.setTitle("Complete");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               returnBack();
                            }
                        });
                        alertDialog.setCancelable(false);
                        alertDialog.create().show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SSToast q = new SSToast("Profile_edit_error:"+error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void returnBack() {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SSFragmentHandler ssFragmentHandler = new SSFragmentHandler();
        ssFragmentHandler.closeFragment(this,fragmentManager);
    }

    private void toast(String text){
        SSToast asdfasd = new SSToast(text);
    }

    private boolean isEmailCorrect(String email){
        if (email == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
