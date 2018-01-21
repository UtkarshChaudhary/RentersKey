package com.example.lenovo.renterskey.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by lenovo on 27-12-2017.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
