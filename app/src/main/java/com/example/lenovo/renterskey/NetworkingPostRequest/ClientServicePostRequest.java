package com.example.lenovo.renterskey.NetworkingPostRequest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 08-12-2017.
 */

public class ClientServicePostRequest {

    private static Retrofit retrofit;
    public static ApiInterfacePostRequest createService(){

         if(retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

          }
        return retrofit.create(ApiInterfacePostRequest.class);
    }
}
