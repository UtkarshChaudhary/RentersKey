package com.example.lenovo.renterskey.Networking;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lenovo on 08-12-2017.
 */

public class ClientService {

    private static Retrofit retrofit;
    private static ApiInterface apiInterface;
    public static ApiInterface createService(){

         if(retrofit==null) {

             OkHttpClient.Builder okhttpBuilder=new OkHttpClient.Builder();
             okhttpBuilder.addInterceptor(new Interceptor() {
                 @Override
                 public Response intercept(Chain chain) throws IOException {

                     Request request=chain.request();
                     Request newRequest=newRequest=request.newBuilder()
                             .addHeader("Accept", "application/json")
                             .addHeader("Content-Type","application/json")
                             .build();

                     return chain.proceed(newRequest);
                 }
             });
           retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpBuilder.build())
                    .build();
          apiInterface =retrofit.create(ApiInterface.class);
          }

        return apiInterface;
    }
}
