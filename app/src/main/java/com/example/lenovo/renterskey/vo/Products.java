package com.example.lenovo.renterskey.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 27-12-2017.
 */


@Entity
public class Products {

   @NonNull
   @PrimaryKey
   public String productId;


   public String img1;
   public String category;
   public String price;
   public String productName;
   public String rent;
   public String type;
   public String img2;
   public String img3;
   public String img4;
   public String description;
   public int choice;
   public static String imageUrl="http://10.0.2.2:8080/images/";

    public Products(String description,String productId, String img1, String img2, String img3, String img4, String category, String price, String productName, String rent, String type,int choice) {
        this.productId = productId;
        this.description=description;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.category = category;
        this.price = price;
        this.productName = productName;
        this.rent = rent;
        this.type = type;
        this.choice=choice;
        imageUrl="http://10.0.2.2:8080/images/"+img1;

    }

    @Ignore
    public Products(String productName) {
        this.productName = productName;
    }
}
