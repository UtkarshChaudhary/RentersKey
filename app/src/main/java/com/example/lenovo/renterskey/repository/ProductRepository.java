package com.example.lenovo.renterskey.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.lenovo.renterskey.AppExecutors;
import com.example.lenovo.renterskey.Networking.ApiInterface;
import com.example.lenovo.renterskey.Networking.ApiResponse;
import com.example.lenovo.renterskey.Networking.ProductsOfCategory;
import com.example.lenovo.renterskey.db.ProductDao;
import com.example.lenovo.renterskey.db.RentersKeyDb;
import com.example.lenovo.renterskey.util.AbsentLiveData;
import com.example.lenovo.renterskey.util.RateLimiter;
import com.example.lenovo.renterskey.vo.Products;
import com.example.lenovo.renterskey.vo.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lenovo on 27-12-2017.
 */

@Singleton
public class ProductRepository {

    private final RentersKeyDb db;
    private final ProductDao productDao;
    private final ApiInterface apiInterface;
    private final AppExecutors appExecutors;

    private RateLimiter<String> productListRateLimit =new RateLimiter<>(10, TimeUnit.MINUTES);;

    @Inject
    public ProductRepository(AppExecutors appExecutor, RentersKeyDb db, ProductDao productDao, ApiInterface apiInterface) {
        this.db = db;
        this.productDao = productDao;
        this.apiInterface = apiInterface;
        this.appExecutors=appExecutor;
    }

    public LiveData<Resource<List<Products>>> loadProducts(String category){
     return new NetworkBoundResource<List<Products>, ProductsOfCategory>(appExecutors) { //
         @NonNull
         @Override
         protected LiveData<List<Products>> loadFromDb() {
              return productDao.loadProducts(category);   //

         }

         @Override
         protected void saveCallResult(@NonNull ProductsOfCategory item) {
            productDao.insertProducts(item.getProductList());  //
         }

         @Override
         protected boolean shouldFetch(@Nullable List<Products> data) {
             return data==null||data.isEmpty();  //
         }

         @NonNull
         @Override
         protected LiveData<ApiResponse<ProductsOfCategory>> createCall() {
             return apiInterface.getProductsViaCategory(category);  //
         }

         @Override
         protected void onFetchFailed() {
             productListRateLimit.reset(category);
         }

         //         @Override
//         protected ProductsOfCategory processResponse(ApiResponse<ProductsOfCategory> response) {
//             ProductsOfCategory body=response.body;
//             return body;
//         }
     }.asLiveData();

    }


}
