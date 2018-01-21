package com.example.lenovo.renterskey.Networking;

import android.arch.lifecycle.LiveData;

import com.example.lenovo.renterskey.vo.CartDetails;
import com.example.lenovo.renterskey.vo.HelperJson;
import com.example.lenovo.renterskey.vo.LoginBody;
import com.example.lenovo.renterskey.vo.ProductDetail;
import com.example.lenovo.renterskey.vo.Products;
import com.example.lenovo.renterskey.vo.ResponseGetProductId;
import com.example.lenovo.renterskey.vo.UserDetails;
import com.example.lenovo.renterskey.vo.UserLoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
 Call<UserLoginResponse> uploadDetailsOfProduct(@Body ProductDetail data);

 @Multipart
 @POST("product/upload")
 Call<UserLoginResponse> uploadImageOfProduct(@Part MultipartBody.Part image,
                                              @Part("productId") RequestBody id);

 @POST("login")
 Call<UserLoginResponse> performLogin(@Body LoginBody body);

// fetch/category?category=electronics'
 @GET("fetch/category")
 LiveData<ApiResponse<ProductsOfCategory>> getProductsViaCategory(@Query("category") String category);

// @GET("fetch/category?category={cate}")
// LiveData<ApiResponse<ProductsOfCategory>> getProductsViaCategory2(@Path("cate") String category);
//
 @GET("fetch/category")
 Call<ProductsOfCategory> getProductsViaCategory2(@Query("category") String category);

 @GET("product/description")
 Call<HelperJson<Products,Object>> getProductDetails(@Query("productId") String productId);

 @GET("search")
 Call<HelperJson<Object,List<Products>>> getProductsForSearch();

@GET("cart/getcart")
Call<CartDetails> getCartOfUser(@Query("userId") String userId);
}
