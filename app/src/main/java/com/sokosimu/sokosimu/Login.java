package com.sokosimu.sokosimu;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sokosimu.sokosimu.SSClasses.SSDialog;
import com.sokosimu.sokosimu.SSClasses.SSDialogLoading;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSHelpers.EmailValidator;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = Login.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    public static int state;
    public static String username;
    public static String email;

    private CallbackManager callbackManager;
    private Button fbButton;
    private LoginManager loginManager;

    private SSPreferences ssPreferences;
    private SSDialogLoading ssDialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ssDialogLoading = new SSDialogLoading("Logging in...",this);
        ssPreferences = new SSPreferences(this);

        //gooogle setup
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        ///fb setup
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        loginWithFacebook(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplication().getBaseContext(),exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


        //buttons
        Button btSi = findViewById(R.id.googlebutton);
        btSi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });



        fbButton = findViewById(R.id.login_button);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFacebookLogin();
            }
        });

        Button btRg  = findViewById(R.id.registerButton);
        btRg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startRegistrationActivity();
            }
        });

        Button btSignIn  = findViewById(R.id.signInButton);
        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        btSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    request(emailEditText.getText().toString(),passwordEditText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startFacebookLogin(){

        loginManager.logInWithReadPermissions(this,Arrays.asList("public_profile"));
    }

    private void loginWithFacebook(LoginResult loginResult) {
        onlineSignUpFb(loginResult.getAccessToken().getUserId());
    }

    private void onlineSignUpFb(String uid) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://172.104.170.154/api/facebook";

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(uid);

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject j = response.getJSONObject(0);
                            String id = j.getString("id");
                            String api_token = j.getString("api_token");

                            ssPreferences.storeString(SharedPrefKeys.USER_ID, id);
                            ssPreferences.storeString(SharedPrefKeys.API_TOKEN, api_token);
                            ssDialogLoading.hide();
                            goHome();
                        }
                        catch (JSONException ex) {
                            ssDialogLoading.hide();
                        }

                    }
                }, null);
        queue.add(jsArrRequest);
    }

    public void signInWithGoogle(){
        ssDialogLoading.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        ssDialogLoading.hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ssDialogLoading.show();
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            try{
                onlineSignUp(acct.getId(),acct.getDisplayName(),acct.getEmail());
            }catch (NullPointerException ex){

                ssDialogLoading.hide();
            }
        } else {
            // Signed out, show unauthenticated UI.

            ssDialogLoading.hide();
        }
    }

    private void onlineSignUp(String uid, String name, String email) {
        ssDialogLoading.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://172.104.170.154/api/google";

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(uid);

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject j = response.getJSONObject(0);
                            String id = j.getString("id");
                            String api_token = j.getString("api_token");

                            ssPreferences.storeString(SharedPrefKeys.USER_ID, id);
                            ssPreferences.storeString(SharedPrefKeys.API_TOKEN,api_token);
                            ssDialogLoading.hide();
                            goHome();
                        }
                        catch (JSONException ex) {

                            ssDialogLoading.hide();
                        }
                    }
                }, null);
        queue.add(jsArrRequest);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
    }

    private void startRegistrationActivity(){
        Intent intt = new Intent(this,Register.class);
        startActivity(intt);
    }


    private void request(String email, String password) throws JSONException {
        EmailValidator emailValidator = new EmailValidator();
        if((email!=null&&!email.isEmpty())&&(password!=null&&!password.isEmpty())){
            if(emailValidator.isEmailValid(email)){
                RequestQueue queue = Volley.newRequestQueue(this);

                String url = "http://172.104.170.154/api/login";

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(email);
                jsonArray.put(password);

                JsonArrayRequest jsArrRequest = new JsonArrayRequest
                        (Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                if (response.toString().contains("203")) {
                                    Toast.makeText(getApplication().getBaseContext(), "Wrong Password or Email", Toast.LENGTH_SHORT).show();
                                    ssDialogLoading.hide();
                                } else {
                                    try {
                                        JSONObject j = response.getJSONObject(0);
                                        String id = j.getString("id");
                                        String api_token = j.getString("api_token");

                                        ssPreferences.storeString(SharedPrefKeys.USER_ID, id);
                                        ssPreferences.storeString(SharedPrefKeys.API_TOKEN, api_token);
                                        ssDialogLoading.hide();
                                        goHome();
                                    } catch (JSONException ex) {
                                        Toast.makeText(getApplication().getBaseContext(), "failed", Toast.LENGTH_SHORT).show();
                                        ssDialogLoading.hide();
                                    }


                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                SSToast t = new SSToast("failed"+error.getMessage());
                                ssDialogLoading.hide();
                            }
                        });
                queue.add(jsArrRequest);
                ssDialogLoading.show();
            }
            else{
                Toast.makeText(getApplication().getBaseContext(),"Enter valid email",Toast.LENGTH_SHORT).show();
                ssDialogLoading.hide();
            }
        }
        else{
            Toast.makeText(getApplication().getBaseContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
            ssDialogLoading.hide();
        }

    }

    public void goHome(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getBaseContext());
        prefs.edit().putBoolean("Islogin", true).apply();
        Intent intt = new Intent(getApplication().getBaseContext(), MainActivity.class);
        startActivity(intt);
        finish();
    }
}
