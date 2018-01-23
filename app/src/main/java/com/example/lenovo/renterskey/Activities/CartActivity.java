package com.example.lenovo.renterskey.Activities;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.SharedPreferencesConstant;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.vo.CartDetails;
import com.example.lenovo.renterskey.vo.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
  List<Products> cartProducts;
  CartAdapter cartAdapter;
  RecyclerView recyclerView;
    private BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cartProducts=new ArrayList<>();
        fetchCartDetails();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar=findViewById(R.id.toolbar_cart);
        toolbar.setTitle("Cart");
        toolbar.setTitleTextColor(getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getDrawable(R.drawable.arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);
        TextView textView=(TextView)bottomNavigationView.findViewById(R.id.place_order).findViewById(R.id.largeLabel);
        textView.setTextSize(24);
        textView.setPadding(0,0,0,32);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void fetchCartDetails(){
        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
        String userId=sharedPreferences.getString(SharedPreferencesConstant.USERID,null);
      // userId="5a3aaa74072eb528b41c5ecc";
        if(userId!=null){
            Call<CartDetails>  call= ClientService.createService().getCartOfUser(userId);
            call.enqueue(new Callback<CartDetails>() {
                @Override
                public void onResponse(Call<CartDetails> call, Response<CartDetails> response) {
                   // Log.e("abcdef","onResponse"+response.isSuccessful()+response.body());
                    if(response.isSuccessful()&&response.body()!=null){
                          //  Log.e("abcdef","on"+response.body().toString());
                       cartAdapter=new CartAdapter();
                       cartProducts.clear();
                       cartProducts.addAll(response.body().productDetails);
                       if(cartProducts.isEmpty()){
                           cartProducts.add(new Products("Your Cart do no contain any Product"));
                       }
                      recyclerView.setAdapter(cartAdapter);
                    }
                }

                @Override
                public void onFailure(Call<CartDetails> call, Throwable t) {
                   // Log.e("abcdef","onResponse"+t.getMessage().toString());
                    cartAdapter=new CartAdapter();
                    cartProducts.clear();
                    if(cartProducts.isEmpty()){
                        cartProducts.add(new Products("Your Cart do no contain any Product"));
                    }
                    recyclerView.setAdapter(cartAdapter);
                }
            });
        }
    }

    /*--------------------------------------------Adapter BoilerPlate --------------------------------------------------- */

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(cartProducts.get(position).img1!=null) {
                Glide.with(CartActivity.this).load(cartProducts.get(position).imageUrl + cartProducts.get(position).img1)
                        .into(holder.p_img);
            }
            int choice=cartProducts.get(position).choice;
            if(cartProducts.get(position).price!=null){
                int price=Integer.parseInt(cartProducts.get(position).price);
                holder.p_price.setText("Price :₹"+price+" X "+choice);
                price*=choice;
                holder.totalItemPrice.setText("₹"+price);
                holder.priceTotal.setText("Total Price :₹"+price);
            }else{
                holder.p_price.setText("");
                holder.totalItemPrice.setText("");
                holder.priceTotal.setText("");
            }
            if(cartProducts.get(position).rent!=null) {
                int rent = Integer.parseInt(cartProducts.get(position).rent);
                holder.p_rent.setText("Rent :₹" + rent + " X " + choice);
                rent *= choice;
                holder.totalItemRent.setText("₹"+rent);
                holder.rentTotal.setText("Total Rent :₹"+rent);
            }else{
                holder.p_rent.setText("");
                holder.totalItemRent.setText("");
                holder.rentTotal.setText("");
            }
            if(cartProducts.get(position).productName!=null) {
                holder.p_name.setText(cartProducts.get(position).productName);
            }else{
                holder.p_name.setText("");
            }
        }

        @Override
        public int getItemCount() {
            return cartProducts==null?0:cartProducts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
          TextView p_name;
          TextView p_price;
          TextView totalItemPrice;
          TextView totalItemRent;
          TextView p_rent;
          TextView rentTotal;
          TextView priceTotal;
          ImageView p_img;
          ImageButton deleteProduct;

             public ViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
                super(layoutInflater.inflate(R.layout.cart_item,parent,false));
                p_name=(TextView)itemView.findViewById(R.id.product_name_cart);
                p_price=(TextView)itemView.findViewById(R.id.price_cart);
                totalItemPrice=(TextView)itemView.findViewById(R.id.total_price_item_cart);
                totalItemRent=(TextView)itemView.findViewById(R.id.total_rent_item_cart);
                p_rent=(TextView)itemView.findViewById(R.id.rent_cart);
                rentTotal=(TextView)itemView.findViewById(R.id.total_rent_cart);
                priceTotal=(TextView)itemView.findViewById(R.id.total_price_cart);
                p_img=(ImageView)itemView.findViewById(R.id.product_img_cart);
                deleteProduct=(ImageButton)itemView.findViewById(R.id.cross_button);
                deleteProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //to do
                        //delete Product From Cart
                    }
                });

             }
        }
    }
}
