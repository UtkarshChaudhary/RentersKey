package com.example.lenovo.renterskey.vo;

/**
 * Created by lenovo on 13-12-2017.
 */

public class LoginBody {

    String email;
    String password;

    public LoginBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
}
