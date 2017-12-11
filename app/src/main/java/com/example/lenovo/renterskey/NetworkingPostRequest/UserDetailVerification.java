package com.example.lenovo.renterskey.NetworkingPostRequest;

/**
 * Created by lenovo on 10-12-2017.
 */

public class UserDetailVerification {

    private String email;
    private String verificationcode;

    public UserDetailVerification(String email, String verificationcode) {
        this.email = email;
        this.verificationcode = verificationcode;
    }
}
