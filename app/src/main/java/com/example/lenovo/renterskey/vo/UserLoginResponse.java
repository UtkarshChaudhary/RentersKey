package com.example.lenovo.renterskey.vo;

/**
 * Created by lenovo on 08-12-2017.
 */

public class UserLoginResponse {
   private boolean success;
   private String userid;
   public String error;
   public String firstname;

    public UserLoginResponse(boolean success, String userid, String error,String firstname) {
        this.success = success;
        this.userid = userid;
        this.error = error;
        this.firstname=firstname;
    }

    public UserLoginResponse(boolean success) {
        this.success = success;
    }

    public String getUserid() {
        return userid;
    }

    public boolean isSuccess() {
        return success;
    }
}
