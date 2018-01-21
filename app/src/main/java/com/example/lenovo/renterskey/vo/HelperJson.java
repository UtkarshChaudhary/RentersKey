package com.example.lenovo.renterskey.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 13-01-2018.
 */

public class HelperJson<T,V> {

    @SerializedName("product")
    public T data;

    @SerializedName("products")
    public V data2;
}
