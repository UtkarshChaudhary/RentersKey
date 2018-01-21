package com.example.lenovo.renterskey.di;

import com.example.lenovo.renterskey.Activities.ShowProducts;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by lenovo on 28-12-2017.
 */

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class )
    abstract ShowProducts contributeShowProducts();
}
