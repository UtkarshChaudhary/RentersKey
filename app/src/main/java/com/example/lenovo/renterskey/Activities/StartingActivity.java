package com.example.lenovo.renterskey.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.SharedPreferencesConstant;
import com.example.lenovo.renterskey.Networking.ApiInterface;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.vo.LoginBody;
import com.example.lenovo.renterskey.vo.UserLoginResponse;
import com.facebook.CallbackManager;
import com.google.android.gms.common.SignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StartingActivity extends AppCompatActivity  {

    CallbackManager callbackManager;

    // Id to identity READ_CONTACTS permission request.
    private static final int REQUEST_READ_CONTACTS = 0;

    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button registerbutton;
    private Button forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
        String userId=sharedPreferences.getString(SharedPreferencesConstant.USERID,null);
        if(userId!=null&&!userId.isEmpty()){
            Intent i=new Intent(StartingActivity.this,MainActivity.class);
            startActivityForResult(i,IntentConstant.STARTINGACTIVITY_MAINACTIVITY);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        mEmailView=findViewById(R.id.email);
        mPasswordView=findViewById(R.id.password);
        forgetPassword=findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(StartingActivity.this,MainActivity.class);
//                startActivity(i);
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });



//        //from firebase video
//        FirebaseAuth auth=FirebaseAuth.getInstance();
//        startActivityForResult(AuthUI.getInstance()
//        .createSignInIntentBuilder().setProviders(
//                AuthUI.FACEBOOK_PROVIDER,
//                AuthUI.EMAIL_PROVIDER,
//                AuthUI.GOOGLE_PROVIDER)
//        .build(),1);

        //facebook login
//        callbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = (LoginButton)findViewById(R.category.facebook_login_button);
//        loginButton.setReadPermissions("email");
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
                // App code
//                Set<String> grantedPermision=loginResult.getRecentlyGrantedPermissions();
//                Log.i("abcd",grantedPermision.toString()); //we get [public_profile, email]
//                String[] arr= (String[]) grantedPermision.toArray();
//                Log.i("abcd",arr[1]);
//                AccessToken accessToken=loginResult.getAccessToken();
//                Log.i("abcd-accessToken",accessToken.toString()); //we get {AccessToken token:ACCESS_TOKEN_REMOVED permissions:[public_profile, email]}
//                Set<String> granted=loginResult.getRecentlyDeniedPermissions();
//                Log.i("abcd-denied",granted.toString()); //we get []

//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.i("LoginActivity", response.toString());
//
//                                // Application code
//
//                                String email = null;
//                                try {
//                                    email = object.getString("email");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                Log.i("LoginActivity",email);
//
//
////                                String birthday = object.getString("birthday"); // 01/31/1980 format
//                            }
//
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "category,name,email,gender,birthday");
//                request.setParameters(parameters);
//                request.executeAsync();

//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });

        SignInButton signInButton=(SignInButton)findViewById(R.id.google_sign_in_button);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Sign in with Google");

        registerbutton=findViewById(R.id.register);
        registerbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StartingActivity.this,EnterDetails.class);
                startActivityForResult(i, IntentConstant.REGISTER_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode== IntentConstant.REGISTER_USER) {
            if(resultCode==RESULT_OK) {
                SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
//               Log.d("abcdefg","startingActivity userid "+sharedPreferences.getString(SharedPreferencesConstant.USERID,null));
//                Log.d("abcdefg","startingActivity email "+sharedPreferences.getString(SharedPreferencesConstant.EMAIL,null));
               String userId=sharedPreferences.getString(SharedPreferencesConstant.USERID,null);
               if(userId!=null&&!userId.isEmpty()) {
                   Intent i = new Intent(StartingActivity.this, MainActivity.class);
                   startActivityForResult(i,IntentConstant.STARTINGACTIVITY_MAINACTIVITY);
               }
            }
        }else if(requestCode==IntentConstant.STARTINGACTIVITY_MAINACTIVITY){
            onBackPressed();
            finish();
        }
    }


//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }


    //Callback received when a permissions request has been completed.

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if (email.isEmpty()) {
            mEmailView.requestFocus();
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            mPasswordView.requestFocus();
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }


        showProgress(true);

        sendLoginRequest(email,password);

    }

    private void sendLoginRequest(String email,String password){

        LoginBody loginBody=new LoginBody(email,password);
        ApiInterface apiInterface= ClientService.createService();
        Call<UserLoginResponse> call=apiInterface.performLogin(loginBody);

        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                Log.d("abcdefg","inside Response respnse= "+response.toString());
                Log.d("abcdefg","success "+response.body().isSuccess());
                if(response.body()!=null){
                    Log.d("abcdefg","success "+response.body().isSuccess());
                    Log.d("abcdefg","success "+response.body().firstname+"bla bla");
            //        Log.d("abcdefg",response.body().getUserid());
                    showProgress(false);
                    if(response.body().firstname!=null&&response.body().getUserid()!=null) {
                        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(SharedPreferencesConstant.FIRSTNAME, response.body().firstname);
                        editor.putString(SharedPreferencesConstant.USERID, response.body().getUserid());
                        editor.putString(SharedPreferencesConstant.EMAIL, loginBody.getEmail());
                        editor.commit();
                        Intent i = new Intent(StartingActivity.this, MainActivity.class);
                        startActivityForResult(i,IntentConstant.STARTINGACTIVITY_MAINACTIVITY);
                    }
                }else{
                    Log.d("abcdefg","response body null");
                    showProgress(false);
                    mPasswordView.setText(null);
                    Toast.makeText(StartingActivity.this,"Email Password pair doesnot match",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("abcdefg","inside failure");
                showProgress(false);
                mEmailView.setText(null);
                mPasswordView.setText(null);
                Toast.makeText(StartingActivity.this,"Getting problem while login please retry",Toast.LENGTH_SHORT).show();
            }
        });
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

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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



}

