package com.sokosimu.sokosimu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.sokosimu.sokosimu.SSClasses.SSToast;
import com.sokosimu.sokosimu.SSFragments.Home.HomeFragment;
import com.sokosimu.sokosimu.SSHelpers.SSPreferences;
import com.sokosimu.sokosimu.SSValues.SharedPrefDefaults;
import com.sokosimu.sokosimu.SSValues.SharedPrefKeys;
import com.sokosimu.sokosimu.SSValues.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import io.reactivex.functions.Consumer;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONArray> {

    private TextInputEditText productName,productDescription,productPrice;
    private String product_name, product_description, product_price;
    private SSPreferences ssPreferences;
    private String userId;
    private String url;
    private ImageView image1,image2,image3;
    private List<String> imageFiles;
    private Spinner spinner;
    List<String> categoryNamesList = new ArrayList<String>();
    List<String> categoryIdsList = new ArrayList<String>();
    String selectedCategoryId;
    ProgressDialog loadingCategories;
    String image_one = null, image_two = null, image_three = null;

    final private static int IMAGE_1_REQUEST_CODE = 101,IMAGE_2_REQUEST_CODE = 102,IMAGE_3_REQUEST_CODE = 103
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        imageFiles = new ArrayList<>();
        ssPreferences = new SSPreferences(this);
                
        userId = ssPreferences.getString(SharedPrefKeys.USER_ID, SharedPrefDefaults.USER_ID);


        Button button = findViewById(R.id.post_button);
        productName = findViewById(R.id.post_product_name);
        productDescription = findViewById(R.id.post_product_description);
        productPrice = findViewById(R.id.post_product_price);
        image1 = findViewById(R.id.post_image_1);
        image2 = findViewById(R.id.post_image_2);
        image3 = findViewById(R.id.post_image_3);
        spinner = findViewById(R.id.spinner_categories);

        button.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);

        loadCategories();
    }

    @Override
    public void onBackPressed(){
        this.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.slide_down);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post_button:
                fetchValues();
                break;
            case R.id.post_image_1:
                imageBrowse(IMAGE_1_REQUEST_CODE);
                break;
            case R.id.post_image_2:
                imageBrowse(IMAGE_2_REQUEST_CODE);
                break;
            case R.id.post_image_3:
                imageBrowse(IMAGE_3_REQUEST_CODE);
                break;
        }
    }

    private void fetchValues() {
        product_name = productName.getText().toString();
        product_price = productPrice.getText().toString();
        product_description = productDescription.getText().toString();
        if(product_name!=null&&!product_name.equals("")) {
            if (product_description != null && !product_description.equals("")) {
                if (product_price != null && !product_price.equals("")) {
                    continuePosting();
                }
                else{
                    SSToast t = new SSToast("Enter product price");
                }
            }
            else{
                SSToast t = new SSToast("Enter product description");
            }
        }
        else{
            SSToast t = new SSToast("Enter product name");
        }
            
    }

    private void continuePosting() {
        if(image_one != null && image_two != null &&image_three != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Posting...");
            progressDialog.show();

            final String api_token = ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
            url = Urls.PRODUCTS + "?api_token=" + api_token;

            final File fileToUpload1 = new File(image_one);
            final File fileToUpload2 = new File(image_two);
            final File fileToUpload3 = new File(image_three);
            Ion.with(this)
                    .load("POST", url)
                    .progressDialog(progressDialog)
                    .uploadProgressHandler(new ProgressCallback() {
                        @Override
                        public void onProgress(long uploaded, long total) {
                        }
                    })
                    .setMultipartParameter("description", product_description)
                    .setMultipartParameter("name",product_name)
                    .setMultipartParameter("price",product_price)
                    .setMultipartParameter("category_id",selectedCategoryId)
                    .setMultipartFile("image_one", "image/"+image_one.substring(image_one.lastIndexOf(".") + 1), fileToUpload1)
                    .setMultipartFile("image_two","image/"+image_one.substring(image_one.lastIndexOf(".") + 1), fileToUpload2)
                    .setMultipartFile("image_three","image/"+image_one.substring(image_one.lastIndexOf(".") + 1), fileToUpload3)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (result != null) {
                                SSToast asdf=  new SSToast("successful");
                                progressDialog.hide();
                                HomeFragment.homeProductsLoader.GetProducts();
                                close();
                            } else {
                                SSToast asdfad = new SSToast("failed"+e.getMessage());
                                progressDialog.hide();
                                //Upload Failed
                            }

                        }
                    });
        }
        else {
            SSToast t = new SSToast("please add all three images");
        }
    }

    private void close() {
        this.finish();
    }

    private void imageBrowse(final int requestCode) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {  //
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, requestCode);
            }
        });
        alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takeFromCamera(requestCode);
            }
        });
        alertDialog.show();
         // Start the Intent
       //
    }

    private void takeFromCamera(final int requestCode) {
        RxImagePicker.with(this).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
            @Override
            public void accept(Uri uri) throws Exception {
                setImageFromCamera(requestCode, uri);
            }
        });
    }

    private void setImageFromCamera(int requestCode, Uri uri) {
        switch (requestCode) {
            case IMAGE_1_REQUEST_CODE:
                image_one=getPath(uri);
                Picasso.with(this).load(uri).resize(150,150).centerInside().into(image1);
                break;
            case IMAGE_2_REQUEST_CODE:
                image_two=getPath(uri);
                Picasso.with(this).load(uri).resize(150,150).centerInside().into(image2);
                break;
            case IMAGE_3_REQUEST_CODE:
                image_three=getPath(uri);
                Picasso.with(this).load(uri).resize(150,150).centerInside().into(image3);
                break;
        }
    }


    public void onActivityResult(int requesCode,int resultCode, Intent data){
        super.onActivityResult(requesCode,resultCode,data);
        Uri uri;
        if(resultCode == RESULT_OK){
            switch (requesCode) {
                case IMAGE_1_REQUEST_CODE:
                    uri = data.getData();
                    image_one=getPath(uri);
                    Picasso.with(this).load(uri).resize(150,150).centerInside().into(image1);
                    break;
                case IMAGE_2_REQUEST_CODE:
                    uri = data.getData();
                    image_two=getPath(uri);
                    Picasso.with(this).load(uri).resize(150,150).centerInside().into(image2);
                    break;
                case IMAGE_3_REQUEST_CODE:
                    uri = data.getData();
                    image_three=getPath(uri);
                    Picasso.with(this).load(uri).resize(150,150).centerInside().into(image3);
                    break;
            }
        }
    }


    private void loadCategories(){
        loadingCategories = new ProgressDialog(this);
        loadingCategories.setIndeterminate(true);
        loadingCategories.setMessage("Loading Categories");
        loadingCategories.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONArray jsonArray = new JSONArray();
        String url = Urls.CATEGORIES + "?api_token=" + ssPreferences.getString(SharedPrefKeys.API_TOKEN, SharedPrefDefaults.API_TOKEN);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,jsonArray, this,this);

        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onResponse(JSONArray response) {
        int count = response.length();

        for(int i=0; i<count;i++){
            try {
                JSONObject j = response.getJSONObject(i);
                categoryIdsList.add(j.getString("id"));
                categoryNamesList.add(j.getString("name"));
            }catch (JSONException ex){

            }
        }

        String[] names = categoryNamesList.toArray(new String[categoryNamesList.size()]);
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item,names);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryId=categoryIdsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadingCategories.hide();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
