package com.example.lenovo.renterskey.Networking;

import android.support.annotation.NonNull;

import com.example.lenovo.renterskey.vo.Products;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 04-01-2018.
 */

public class ProductsOfCategory {

    @SerializedName("total_count")
    private int total;

    @SerializedName("productList")
    private List<Products> productList;

    public int getTotal(){
        return productList==null?0:productList.size();
    }

    public ProductsOfCategory(List<Products> productList) {
        this.productList = productList;
    }

    public List<Products> getProductList() {
        return productList;
    }
}
