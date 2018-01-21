package com.example.lenovo.renterskey.vo;

import java.util.List;

/**
 * Created by lenovo on 20-01-2018.
 */

public class CartDetails {

    public String userId;
    public List<Products> productDetails;

    public CartDetails(String userId, List<Products> productDetails) {
        this.userId = userId;
        this.productDetails = productDetails;
    }
}

