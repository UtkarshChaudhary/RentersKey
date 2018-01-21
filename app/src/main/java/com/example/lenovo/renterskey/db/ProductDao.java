package com.example.lenovo.renterskey.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.lenovo.renterskey.vo.Products;

import java.util.List;


/**
 * Created by lenovo on 27-12-2017.
 */
@Dao
public abstract class ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Products... products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertProducts(List<Products> productsList);

    @Query("SELECT * FROM Products WHERE category = :cate")
    public abstract LiveData<Products> load(String cate);

    @Query("SELECT * FROM Products WHERE category = :cate")
    public abstract LiveData<List<Products>> loadProducts(String cate);

    @Query("SELECT * FROM products")
    public abstract List<Products> loadAllProducts();

    @Query("DELETE  FROM Products")
    public abstract void deleteListOfProducts();



//    public LiveData<List<Products>> loadOrdered(List<String> productIds){
//        Sparse order=new SparseArray<>();
//        int index=0;
//        for (String productId : productIds ){
//            order.put(productId,index++);
//        }
//
//        return Transformations.map(loadById(productIds), repositories -> {
//            Collections.sort(repositories, (r1, r2) -> {
//                int pos1=order.get(r1.productId);
//                int pos2=order.get(r2.productId);
//                return pos1-pos2;
//            });
//            return repositories;
//        });
//    }

//    @Query("SELECT * FROM products WHERE productId in (:productIds)")
//    protected abstract LiveData<List<Products>> loadById(List<String> productIds);

}
