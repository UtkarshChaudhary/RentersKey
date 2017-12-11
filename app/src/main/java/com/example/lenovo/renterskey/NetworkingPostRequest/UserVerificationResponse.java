package com.example.lenovo.renterskey.NetworkingPostRequest;

/**
 * Created by lenovo on 10-12-2017.
 */

public class UserVerificationResponse {
    private boolean success;

    public UserVerificationResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
