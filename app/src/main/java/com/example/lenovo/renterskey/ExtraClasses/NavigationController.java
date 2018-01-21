package com.example.lenovo.renterskey.ExtraClasses;

import android.support.v4.app.FragmentManager;

import com.example.lenovo.renterskey.Activities.ShowProducts;
import com.example.lenovo.renterskey.Fragments.ShowProductsFragment;
import com.example.lenovo.renterskey.R;

import javax.inject.Inject;

/**
 * Created by lenovo on 02-01-2018.
 */

public class NavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(ShowProducts showProducts){
        //error might occur
        this.containerId= R.id.container_show_products;
        this.fragmentManager=showProducts.getSupportFragmentManager();
    }

    public void navigateToShowProducts(String category){
        ShowProductsFragment showProductsFragment= ShowProductsFragment.create(category);
        fragmentManager.beginTransaction()
                .replace(containerId,showProductsFragment)
               .addToBackStack(null)
                .commitAllowingStateLoss();
    }


}
