package com.example.lenovo.renterskey.di;

/**
 * Created by lenovo on 28-12-2017.
 */

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.lenovo.renterskey.RentersKeyApp;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Helper class to automatically inject fragments if they implement {@link Injectable}.
 */

public class AppInjector {

    private AppInjector(){};
    public static void init(RentersKeyApp rentersKeyApp){
        DaggerAppComponent.builder().application(rentersKeyApp)
                .build().inject(rentersKeyApp);

        rentersKeyApp.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity){
        if(activity instanceof HasSupportFragmentInjector){
            AndroidInjection.inject(activity);
        }
        if(activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                            if(f instanceof Injectable){
                                AndroidSupportInjection.inject(f);
                            }
                        }
                    },true);

        }
    }

}
