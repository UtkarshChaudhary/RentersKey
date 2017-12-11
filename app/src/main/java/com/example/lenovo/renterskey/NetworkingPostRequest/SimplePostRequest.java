package com.example.lenovo.renterskey.NetworkingPostRequest;

/**
 * Created by lenovo on 08-12-2017.
 */

public class SimplePostRequest {
   public boolean success;
   private String userid;
    public String error;

    public SimplePostRequest(boolean success, String userid, String error) {
        this.success = success;
        this.userid = userid;
        this.error = error;
    }

    public String getUserid() {
        return userid;
    }
}
