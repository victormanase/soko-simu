package com.sokosimu.sokosimu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sokosimu.sokosimu.SSClasses.SSDialogLoading;
import com.sokosimu.sokosimu.SSHelpers.EmailValidator;

import org.json.JSONArray;
import org.json.JSONException;

public class Register extends AppCompatActivity {
    private SSDialogLoading ssDialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ssDialogLoading = new SSDialogLoading("Registering",this);
        final EditText name = findViewById(R.id.nameEditText);
        final EditText email = findViewById(R.id.emailEditText);
        final EditText password = findViewById(R.id.passwordEditText);
        final EditText cPassword = findViewById(R.id.cPasswordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    request(name.getText().toString(),email.getText().toString(),password.getText().toString(),cPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void request(String name,String email, String password,String cPassword) throws JSONException {

        EmailValidator emailValidator = new EmailValidator();
        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(password.equals(cPassword)){
                if(emailValidator.isEmailValid(email)) {
                    RequestQueue queue = Volley.newRequestQueue(this);

                    String url = "http://172.104.170.154/api/register";

                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(name);
                    jsonArray.put(email);
                    jsonArray.put(password);


                    JsonArrayRequest jsArrRequest = new JsonArrayRequest
                            (Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {

                                @Override
                                public void onResponse(JSONArray response) {
                                    if (response.toString().contains("204")) {
                                        Toast.makeText(getApplication().getBaseContext(), "Email already taken!", Toast.LENGTH_SHORT).show();
                                    } else if (response.toString().contains("205")) {
                                        finishRegistration();
                                        ssDialogLoading.hide();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(getApplication().getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsArrRequest);
                    ssDialogLoading.show();
                }
                else{
                    Toast.makeText(getApplication().getBaseContext(),"Invalid email",Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(getApplication().getBaseContext(),"passwords do not match!",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplication().getBaseContext(),"please fill all fields",Toast.LENGTH_SHORT).show();
        }
    }

    private void finishRegistration() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Registration Successful, please login with your credentials");
        alertDialog.setTitle("Complete");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnToLogin();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.create().show();
    }

    private void returnToLogin(){
        Intent intt = new Intent(getApplication().getBaseContext(),Login.class);
        startActivity(intt);
        finish();
    }
}
