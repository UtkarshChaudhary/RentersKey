package com.example.lenovo.renterskey.vo;

import java.io.File;

/**
 * Created by lenovo on 12-12-2017.
 */

public class uploadImageRequest {

   private File img1;
   private String productId;

    public uploadImageRequest(File img1, String productId) {
        this.img1 = img1;
        this.productId = productId;
    }
}
