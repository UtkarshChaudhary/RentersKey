package com.example.lenovo.renterskey.Fragments;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lenovo.renterskey.Activities.ProductDetails;
import com.example.lenovo.renterskey.ExtraClasses.NavigationController;
import com.example.lenovo.renterskey.ExtraClasses.ProductListAdapter;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.Networking.ApiInterface;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.Networking.ProductsOfCategory;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.di.Injectable;
import com.example.lenovo.renterskey.vo.Products;


import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 28-12-2017.
 */

public class ShowProductsFragment extends Fragment implements Injectable {

    private static final String CATEGORY = "category";
    private List<Products> productsList;
    private ProductListAdapter productListAdapter;
    private RecyclerView recyclerView;

    @Inject
    NavigationController navigationController;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(CATEGORY)) {
            ApiInterface apiInterface=ClientService.createService();
            Call<ProductsOfCategory> call= apiInterface.getProductsViaCategory2(args.getString(CATEGORY));
           call.enqueue(new Callback<ProductsOfCategory>() {
               @Override
               public void onResponse(Call<ProductsOfCategory> call, Response<ProductsOfCategory> response) {
                   Log.e("tag",response.body().toString()+" "+response.isSuccessful());
                   Log.e("tag",response.message()+" "+response.body().getTotal());
                   if(response.body().getProductList()==null){
                       Log.e("tag","come here");
                   }
                   productsList=response.body().getProductList();
                   productListAdapter= new ProductListAdapter(productsList, getContext(), new ProductListAdapter.ParticularProductClicked() {
                       @Override
                       public void onProductClicked(View view, int pos) {
                          String id=productsList.get(pos).productId;
                           Intent i=new Intent(getActivity(), ProductDetails.class);
                           i.putExtra(IntentConstant.PRODUCT_ID,id);
                           startActivity(i);
                       }

                    });
                   recyclerView.setAdapter(productListAdapter);
               }

               @Override
               public void onFailure(Call<ProductsOfCategory> call, Throwable t) {
               //on failure
               }
           });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.show_products_fragment,container,false);
       recyclerView=view.findViewById(R.id.recycle_view_show_product_fragment);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static ShowProductsFragment create(String category) {
        ShowProductsFragment showProductsFragment = new ShowProductsFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        showProductsFragment.setArguments(args);
        return showProductsFragment;
    }
}
