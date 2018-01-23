package com.example.lenovo.renterskey.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Base64;


import java.io.ByteArrayOutputStream;

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
        if(img2==null||img2.isEmpty()) {
           this.img2=null;
        }else{
            this.img2 = img2;
        }
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

    public Products(String category, String price, String productName, String rent, String type, Drawable img, String description) {
        this.category = category;
        this.price = price;
        this.productName = productName;
        this.rent = rent;
        this.type = type;
        this.description = description;
        img1=null;
        img3=null;
        img4=null;
        productId=null;
        choice=0;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        Bitmap bitmap= ((BitmapDrawable)img).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imagesBytes=baos.toByteArray();
        this.img2 = Base64.encodeToString(imagesBytes,Base64.DEFAULT);
    }

    @Ignore
    public Products(String productName) {
        this.productName = productName;
    }
}
