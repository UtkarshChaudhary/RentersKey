package com.example.lenovo.renterskey.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.renterskey.IntentAndSharedPreferences.SharedPreferencesConstant;
import com.example.lenovo.renterskey.NetworkingPostRequest.ApiInterfacePostRequest;
import com.example.lenovo.renterskey.NetworkingPostRequest.ClientServicePostRequest;
import com.example.lenovo.renterskey.NetworkingPostRequest.PostAddFormData;
import com.example.lenovo.renterskey.NetworkingPostRequest.ResponseGetProductId;
import com.example.lenovo.renterskey.NetworkingPostRequest.UserVerificationResponse;
import com.example.lenovo.renterskey.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdd extends AppCompatActivity {

    private String productId="";
    private View mLoginFormView;
    private EditText[] editTexts;
    private Spinner spinner_type;
    private Spinner spinner_category;
    private List<String> typelist;
    private List<String> categorylist;
    private String type_selected="Type";
    private String category_selected="Category";
    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        Toolbar toolbar=findViewById(R.id.toolbar_post_add);
        setSupportActionBar(toolbar);

        //to create cross icon on toolbar
        toolbar.setNavigationIcon(R.drawable.cross);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        editTexts=new EditText[]{
                findViewById(R.id.product_name_postadd),
                findViewById(R.id.rent_price_postadd),
                findViewById(R.id.sell_price_postadd),
                findViewById(R.id.description_postadd)

        };
        mLoginFormView=findViewById(R.id.post_add_details_view);

        spinner_category=findViewById(R.id.category_spinner_postadd);
        spinner_type=findViewById(R.id.type_spinner_postadd);
        Resources resources=this.getResources();
        String[] cat=resources.getStringArray(R.array.products_type);
        String[] category=new String[cat.length+1];
        category[0]="Category";
        for(int i=0;i<cat.length;i++){
            category[i+1]=cat[i];
        }
        String[] type=new String[]{
             "Type",
              "falana",
               "dikda",
                "tushar",
                "sudhanshu"
        };
        typelist=new ArrayList<>(Arrays.asList(type));
        categorylist=new ArrayList<>(Arrays.asList(category));
        initializeAdapter();
        spinner_type.setAdapter(typeAdapter);
        spinner_category.setAdapter(categoryAdapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem=(String)adapterView.getItemAtPosition(i);
                if(i>0){
                    category_selected=selectedItem;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem=(String)adapterView.getItemAtPosition(i);
                if(i>0){
                    type_selected=selectedItem;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BottomNavigationView navigation =  findViewById(R.id.bottom_navigation_post_add);
        Button Cancel=navigation.findViewById(R.id.cancel_button_post_add);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button Continue= navigation.findViewById(R.id.continue_post_add);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr=new String[]{
                        editTexts[0].getText().toString(),
                        editTexts[1].getText().toString(),
                        editTexts[2].getText().toString(),
                        editTexts[3].getText().toString()
                };
                View view1=null;
                for(int i=0;i<4;i++){
                    if(arr[i]==null||arr[i].equals("")){
                        view1=editTexts[i];
                        break;
                    }
                }

                if(view1!=null){
                    view1.requestFocus();
                    Toast.makeText(PostAdd.this, "This field can't be empty", Toast.LENGTH_LONG).show();
                }else if(category_selected.equals("Category")){
                    Toast.makeText(PostAdd.this,"please select Category",Toast.LENGTH_LONG).show();
                }else if(type_selected.equals("Type")){
                    Toast.makeText(PostAdd.this,"please select type",Toast.LENGTH_LONG).show();

                }else {
                    uploadProductDetails(arr);
                }
            }
        });

        showProgress(true);
        ApiInterfacePostRequest apiInterfacePostRequest= ClientServicePostRequest.createService();
        retrofit2.Call<ResponseGetProductId> call=apiInterfacePostRequest.getProductId();

        call.enqueue(new Callback<ResponseGetProductId>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseGetProductId> call, Response<ResponseGetProductId> response) {
                if(response.body()!=null){
                    productId=response.body().getProductId();
                    Log.d("abcdefg","success productId ="+productId);
                }else {
                    Log.d("abcdefg", "responseBody is null" + response.toString());
                }
                showProgress(false);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseGetProductId> call, Throwable t) {
                Log.d("abcdefg","failure");
                showProgress(false);
            }
        });

    }
    //to hide keyboard on clicking elsewhere on screen
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void initializeAdapter(){
        typeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typelist){

            @Override
            public boolean isEnabled(int position) {
                if(position==0){
                    return false;
                }else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view=super.getDropDownView(position, convertView, parent);
                TextView tv=(TextView) view;
                if(position==0){
                    tv.setTextColor(Color.GRAY);
                }else{
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorylist){

            @Override
            public boolean isEnabled(int position) {
                if(position==0){
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view=super.getDropDownView(position,convertView,parent);
                TextView tv=(TextView)view;
                if(position==0){
                    tv.setTextColor(Color.GRAY);
                }else{
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void uploadProductDetails(String[] arr){
        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
       String userid=sharedPreferences.getString(SharedPreferencesConstant.USERID,null);

       if(userid==null){
            userid="136";
        }

        PostAddFormData data=new PostAddFormData(category_selected,type_selected,arr[1],arr[2],
                arr[3],productId,userid,arr[0]);

        ApiInterfacePostRequest apiInterfacePostRequest=ClientServicePostRequest.createService();

        Call<UserVerificationResponse> responseCall=apiInterfacePostRequest.uploadDetailsOfProduct(data);

        responseCall.enqueue(new Callback<UserVerificationResponse>() {
            @Override
            public void onResponse(Call<UserVerificationResponse> call, Response<UserVerificationResponse> response) {
                Log.d("abcdefg","inside Response respnse= "+response.toString());
                if(response.body()!=null){
                    Log.d("abcdefg","success "+response.body().isSuccess());
                }else{
                    Log.d("abcdefg","response body null");
                }
            }

            @Override
            public void onFailure(Call<UserVerificationResponse> call, Throwable t) {
                Log.d("abcdefg","inside failure");
            }
        });

    }
}
