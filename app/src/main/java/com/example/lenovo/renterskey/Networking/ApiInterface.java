package com.example.lenovo.renterskey.Networking;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lenovo on 01-12-2017.
 */

public interface ApiInterface {

 @POST("signup")
 Call<UserLoginResponse> sendSimplePostRequest(@Body UserDetails userInfo);


 @POST("signup/verify")
 Call<UserLoginResponse> userVerificationPostRequest(@Body UserDetails userDetail);

 @POST("product/getproductid")
 Call<ResponseGetProductId> getProductId();

 @POST("product/data")
 Call<UserLoginResponse> uploadDetailsOfProduct(@Body PostAddFormData data);

 @Multipart
 @POST("product/upload")
 Call<UserLoginResponse> uploadImageOfProduct(@Part MultipartBody.Part image,
                                                @Part("productId")RequestBody id);

 @POST("login")
 Call<UserLoginResponse> performLogin(@Body LoginBody body);



}
