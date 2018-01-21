package com.example.lenovo.renterskey.di;

import com.example.lenovo.renterskey.Fragments.ShowProductsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by lenovo on 28-12-2017.
 */

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ShowProductsFragment contributeShowProductsFragment();
}
