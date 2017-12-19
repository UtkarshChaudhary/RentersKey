package com.example.lenovo.renterskey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TesterClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester_class);

//        ApiInterface apiInterface= ClientService.createService();
//        UserDetailVerification user=new UserDetailVerification("jaat@s","9391");
//        Call<UserVerificationResponse> call=apiInterface.userVerificationPostRequest(user);
//        call.enqueue(new Callback<UserVerificationResponse>() {
//            @Override
//            public void onResponse(Call<UserVerificationResponse> call, Response<UserVerificationResponse> response) {
//                Log.d("abcdefg","tester call on Response"+response +"abc "+"response.body()="+response.body());
//                if(response.body()!=null&&response.body().isSuccess()){
//                    Log.d("abcdefg","success");
//                }else{
//                    Log.d("abcdefg","failure");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserVerificationResponse> call, Throwable t) {
//                Log.d("abcdefg","on Failure"+t.getMessage().toString());
//            }
//        });
   }
}
