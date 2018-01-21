package com.example.lenovo.renterskey.vo;

import java.util.Random;

/**
 * Created by lenovo on 01-12-2017.
 */

public class UserDetails {

    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String password;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String pin;
    private String verificationcode;


    public UserDetails(String firstname, String lastname, String email, String contact, String password, String address1, String address2, String city, String state, String pin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.pin = pin;
        Random rd=new Random();
        int vc=rd.nextInt(9999);
        if(vc<=999){
            vc+=1000;
        }
        this.verificationcode =""+vc;
    }

    public UserDetails(String email, String verificationcode) {
        this.email = email;
        this.verificationcode = verificationcode;
    }


    public String getEmail(){
        return email;
    }

}
