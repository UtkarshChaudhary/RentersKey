package com.example.lenovo.renterskey.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.SharedPreferencesConstant;
import com.example.lenovo.renterskey.NetworkingPostRequest.ApiInterfacePostRequest;
import com.example.lenovo.renterskey.NetworkingPostRequest.ClientServicePostRequest;
import com.example.lenovo.renterskey.NetworkingPostRequest.SimplePostRequest;
import com.example.lenovo.renterskey.NetworkingPostRequest.UserDetailVerification;
import com.example.lenovo.renterskey.NetworkingPostRequest.UserDetails;
import com.example.lenovo.renterskey.NetworkingPostRequest.UserVerificationResponse;
import com.example.lenovo.renterskey.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EnterDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int ARRAY_LENGTH=10;
    private EditText[] editTexts;
    private ArrayAdapter<String> adapter;
    private List<String> stateList;
    private Spinner spinner;
    private View mLoginFormView;
    private String state="state";

   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           int id=item.getItemId();
           String[] stringArray=new String[ARRAY_LENGTH];
           if(id== R.id.sign_up_enter_details){
               Log.d("abcdefgh","entry successfull");
              View focusView=null;
               for(int i=0;i<ARRAY_LENGTH;i++){
                   stringArray[i]=editTexts[i].getText().toString();
                   //to check empty entries (except in address line 2)
                   if(i!=6&&stringArray[i].equals("")){
                       focusView=editTexts[i];
                       break;
                   }
               }
               if(focusView!=null){
                   focusView.requestFocus();
                   Toast.makeText(EnterDetails.this,"Field can't be empty",Toast.LENGTH_LONG).show();

               }else if(state.equals("state")){
                   focusView=spinner;
                   focusView.requestFocus();
                   Toast.makeText(EnterDetails.this,"please select state",Toast.LENGTH_LONG).show();
               }else if(!stringArray[4].equals(stringArray[9])){
                   focusView=editTexts[4];
                   focusView.requestFocus();
                   editTexts[4].setText(null);
                   editTexts[4].setHint("Password");
                   editTexts[9].setText(null);
                   editTexts[9].setHint("Confirm password");
                   Toast.makeText(EnterDetails.this,"Re-enter Password",Toast.LENGTH_LONG).show();
               }else if(!isEmailValid(stringArray[2])){
                   focusView=editTexts[2];
                   focusView.requestFocus();
                   editTexts[2].setText(null);
                   editTexts[2].setHint("Email");
                   Toast.makeText(EnterDetails.this,"Enter valid email",Toast.LENGTH_LONG).show();
               }else if(!isPasswordValid(stringArray[4])){
                   focusView=editTexts[4];
                   focusView.requestFocus();
                   editTexts[4].setText(null);
                   editTexts[4].setHint("Password");
                   editTexts[9].setText(null);
                   editTexts[9].setHint("Confirm password");
                   Toast.makeText(EnterDetails.this,"Password length is too short",Toast.LENGTH_LONG).show();
               }else{
                   final UserDetails userDetails=new UserDetails(stringArray[0],stringArray[1],stringArray[2],stringArray[3],
                           stringArray[4],stringArray[5],stringArray[6],stringArray[7],state,stringArray[8]);


                  ApiInterfacePostRequest apiInterfacePostRequest = ClientServicePostRequest.createService();

                   retrofit2.Call<SimplePostRequest> call  =  apiInterfacePostRequest.sendSimplePostRequest(userDetails);
                   call.enqueue(new Callback<SimplePostRequest>() {
                       @Override
                       public void onResponse(retrofit2.Call<SimplePostRequest> call, Response<SimplePostRequest> response) {
                         //  Log.d("abcdefg",response.toString());
                          Log.d("abcdefg", "" + response.body());
                          SimplePostRequest request=response.body();
                          if(request.success) {
                              SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
                              SharedPreferences.Editor editor= sharedPreferences.edit();
                              editor.putString(SharedPreferencesConstant.EMAIL,userDetails.getEmail());
                              editor.putString(SharedPreferencesConstant.USERID,request.getUserid());
                              editor.commit();
                             verifyUser(request);

                          }else{
                             showErrorMessager(request.error);
                          }

                       }
                      @Override
                       public void onFailure(retrofit2.Call<SimplePostRequest> call, Throwable t) {
                           Log.d("abcdefg","error On failure"+t.getMessage().toString());

                       }
                   });
                   showProgress(true);
               }

               return true;
           }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);
        Toolbar toolbar=findViewById(R.id.toolbar_enter_details);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sign up");
        toolbar.setTitleTextColor(getColor(R.color.white));

        //to create back arrow icon on toolbar
        toolbar.setNavigationIcon(getDrawable(R.drawable.arrow_back));
        mLoginFormView=findViewById(R.id.signup_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editTexts = new EditText[] {
                 findViewById(R.id.firstname),
                 findViewById(R.id.lastname),
                 findViewById(R.id.email),
                 findViewById(R.id.contact_number),
                 findViewById(R.id.password),
                 findViewById(R.id.addressline1),
                 findViewById(R.id.addressline2),
                 findViewById(R.id.city),
                 findViewById(R.id.pin),
                 findViewById(R.id.confirmPassword) };

        spinner=findViewById(R.id.statelist_spinner);
        String[] state=new String[]{
        "State",
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chandigarh",
        "Chhattisgarh",
        "Dadra and Nagar Haveli",
        "Daman and Diu",
        "Delhi",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jammu and Kashmir",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Lakshadweep",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Puducherry",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Nadu",
        "Telangana",
        "Tripura",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal"
        };

        stateList=new ArrayList<>(Arrays.asList(state));
        initializeAdapter();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_enter_details);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        TextView textView=(TextView)navigation.findViewById(R.id.sign_up_enter_details).findViewById(R.id.largeLabel);
        textView.setTextSize(20);
        textView.setPadding(0,0,0,35);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItemText=(String)adapterView.getItemAtPosition(i);
        if(i>0){
          state=selectedItemText;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_enter_details,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
       setResult(RESULT_CANCELED);
       super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.clear_all_fields){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Reset all entries");
            builder.setMessage("Do you want to clear all entries and want to reset them again" );
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.dismiss();
                }
            });
           builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    clearAllFields();
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
            return true;
        }

        return false;
    }

    private  void clearAllFields(){

        for(int i=0;i<ARRAY_LENGTH;i++){
            editTexts[i].setText(null);
        }

        editTexts[0].setHint("First name");
        editTexts[1].setHint("Last name");
        editTexts[2].setHint("Email");
        editTexts[3].setHint("Contact number");
        editTexts[4].setHint("Password");
        editTexts[5].setHint("Address line one");
        editTexts[6].setHint("Address line two");
        editTexts[7].setHint("City");
        editTexts[8].setHint("Pin");
        editTexts[9].setHint("Confirm password");

        spinner.setAdapter(adapter);
    }

    private void initializeAdapter(){
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stateList){
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
                View view=super.getDropDownView(position,convertView,parent);
                TextView tv=(TextView) view;
                if(position==0){
                    tv.setTextColor(Color.GRAY);
                }else{
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String password){
        return password.length()>4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
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

    private void verifyUser(SimplePostRequest res){

        showProgress(false);
        View v= getLayoutInflater().inflate(R.layout.dialogbox_verify_user,null);
        TextView textView=v.findViewById(R.id.dialogbox_verify_user_textView);
        textView.setText("Enter verification code from your email id mentioned in signup page");
        final EditText editText=v.findViewById(R.id.dialogbox_verify_user_editText);

        final AlertDialog dialog = new AlertDialog.Builder(EnterDetails.this)
                .setView(v)
                .setTitle("Verfication")
                .setCancelable(false)
                .setPositiveButton("Cancel", null) //Set to null. We override the onclick
                .setNegativeButton("OK", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                        //Dismiss once everything is OK.

                    }
                });

                Button button1=((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                button1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        String vc=editText.getText().toString();
                        ApiInterfacePostRequest apiInterfacePostRequest = ClientServicePostRequest.createService();
                        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
                        final String email=sharedPreferences.getString(SharedPreferencesConstant.EMAIL,null);
                        UserDetailVerification user=new UserDetailVerification(email,vc);
                        Call<UserVerificationResponse> call= apiInterfacePostRequest.userVerificationPostRequest(user);

                        call.enqueue(new Callback<UserVerificationResponse>() {
                            @Override
                            public void onResponse(Call<UserVerificationResponse> call, Response<UserVerificationResponse> response) {
                                boolean res=response.body().isSuccess();
                                if(res){
                                    Toast.makeText(EnterDetails.this,"You are successfully verified",
                                            Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                   setResult(RESULT_OK);
                                    finish();
                                }else{
                                    Toast.makeText(EnterDetails.this,"Entered verification code is incorrect",
                                            Toast.LENGTH_LONG).show();
                                    editText.setText(null);
                                    editText.requestFocus();
                                }

                            }

                            @Override
                            public void onFailure(Call<UserVerificationResponse> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });
        dialog.show();
    }

    private void showErrorMessager(String error){
       showProgress(false);
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setCancelable(true);
       builder.setTitle("Registration failed");
       builder.setMessage(error);
       builder.create().show();
    }
}
