package com.example.lenovo.renterskey.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.vo.HelperJson;
import com.example.lenovo.renterskey.vo.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {

    List<String> productImages;
    LayoutInflater layoutInflater;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ImageViewAdapter adapter;
    TextView priceTextView;
    TextView rentTextView;
    TextView typeTextView;
    TextView categoryTextView;
    TextView descriptionTextView;

    private BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productImages=new ArrayList<>();
        layoutInflater=getLayoutInflater();
        recyclerView=findViewById(R.id.recycle_view_product_detail);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        toolbar=findViewById(R.id.toolbar_product_detaills);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getDrawable(R.drawable.arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        adapter=new ImageViewAdapter();
        priceTextView=findViewById(R.id.price_product_detail);
        rentTextView=findViewById(R.id.rent_product_detail);
        typeTextView=findViewById(R.id.type_product_detail);
        categoryTextView=findViewById(R.id.category_product_detail);
        descriptionTextView=findViewById(R.id.description_product_detail);
        BottomNavigationView navigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation_product_details);
        navigationView.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

        TextView textView1=(TextView) navigationView.findViewById(R.id.particular_product_rent_it).findViewById(R.id.largeLabel);
        textView1.setTextSize(22);
        textView1.setPadding(0,0,0,32);

        TextView textView2=(TextView) navigationView.findViewById(R.id.particular_product_buy_it).findViewById(R.id.largeLabel);
        textView2.setTextSize(20);
        textView2.setPadding(0,0,0,32);
        Intent i=getIntent();
        String product_id=i.getStringExtra(IntentConstant.PRODUCT_ID);
        if(product_id!=null||!product_id.isEmpty()) {
            fetchProductDetails(product_id);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    private void fetchProductDetails(String id){

        Call<HelperJson<Products,Object>> call= ClientService.createService().getProductDetails(id);
        call.enqueue(new Callback<HelperJson<Products,Object>>() {
            @Override
            public void onResponse(Call<HelperJson<Products,Object>> call, Response<HelperJson<Products,Object>> response) {
                Log.e("abcdef","productDetail onResponse"+response.toString());
                if(response!=null&&response.body()!=null&&response.isSuccessful()){
                    Log.e("abcdef","productDetail onResponse"+response.body().toString());
                    Products products=response.body().data;

                    Log.e("abcdef",products.img1+products.category+products.price);
                  productImages.add(Products.imageUrl+products.img1);
                  productImages.add(Products.imageUrl+products.img1);
                  productImages.add(Products.imageUrl+products.img1);
                  productImages.add(Products.imageUrl+products.img1);
                  toolbar.setTitle(products.productName);
                  priceTextView.setText("Price : "+products.price);
                  rentTextView.setText("Rent : "+products.rent);
                  typeTextView.setText("Type : "+products.type);
                  categoryTextView.setText("Category : "+products.category);
                  descriptionTextView.setText(products.description);
                  recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<HelperJson<Products,Object>> call, Throwable t) {
                Log.e("abcdef","productDetail onFailure"+t.toString());
            }
        });
    }





// -------------------------------Recycler View BoilerPlate -----------------------------

    class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=layoutInflater.inflate(R.layout.recycler_view_product_detail,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(ProductDetails.this).load(productImages.get(position)).into(holder.picture);
        }

        @Override
        public int getItemCount() {
            return productImages.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView picture;

            public ViewHolder(View itemView) {
                super(itemView);
                this.picture = (ImageView) itemView.findViewById(R.id.product_detail_recycler_view_image);
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
    }

}