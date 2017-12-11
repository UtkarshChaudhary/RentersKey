package com.example.lenovo.renterskey.NetworkingPostRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by lenovo on 01-12-2017.
 */

public interface ApiInterfacePostRequest {

@Headers({"Accept:application/json","Content-Type:application/json"})
 @POST("signup")
 Call<SimplePostRequest> sendSimplePostRequest(@Body UserDetails userInfo);


 @Headers({"Accept:application/json","Content-Type:application/json"})
 @POST("signup/verify")
 Call<UserVerificationResponse> userVerificationPostRequest(@Body UserDetailVerification userDetailVerification);

 @Headers({"Accept:application/json","Content-Type:application/json"})
 @POST("upload/getproductid")
 Call<ResponseGetProductId> getProductId();

 @Headers({"Accept:application/json","Content-Type:application/json"})
 @POST("upload/data")
 Call<UserVerificationResponse> uploadDetailsOfProduct(@Body PostAddFormData data);

 @Headers({"Accept:application/json","Content-Type:application/json"})
 @POST("upload")
 Call<UserVerificationResponse> uploadImageOfProduct(@Body UserDetailVerification userDetailVerification);


}
