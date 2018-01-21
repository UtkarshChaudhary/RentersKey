package com.example.lenovo.renterskey.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.lenovo.renterskey.viewModel.ProductsViewModel;
import com.example.lenovo.renterskey.viewModel.RentersKeyViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by lenovo on 28-12-2017.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel.class)
    abstract ViewModel bindProductsViewModel(ProductsViewModel productsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(RentersKeyViewModelFactory factory);

}
