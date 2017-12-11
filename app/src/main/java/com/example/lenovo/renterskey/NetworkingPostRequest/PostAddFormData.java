package com.example.lenovo.renterskey.NetworkingPostRequest;

/**
 * Created by lenovo on 10-12-2017.
 */

public class PostAddFormData {
    private String category;
    private String type;
    private String rent;
    private String price;
    private String description;
    private String productId;
    private String userid;
    private String productName;

    public PostAddFormData(String category, String type, String rent, String price, String description, String productId, String userid, String productName) {
        this.category = category;
        this.type = type;
        this.rent = rent;
        this.price = price;
        this.description = description;
        this.productId = productId;
        this.userid = userid;
        this.productName = productName;
       }
}
