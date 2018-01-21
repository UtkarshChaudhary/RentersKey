package com.example.lenovo.renterskey.ExtraClasses;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.renterskey.Activities.ProductDetails;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.vo.Products;

import java.util.List;

/**
 * Created by lenovo on 07-01-2018.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    private final List<Products> productsList;
    public final Context context;
    private final ParticularProductClicked productClicked;

    public ProductListAdapter(List<Products> productsList, Context context,ParticularProductClicked productClicked) {
        this.productsList = productsList;
        this.context = context;
        this.productClicked=productClicked;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()),parent,context,productClicked);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Glide.with(context).load(productsList.get(position).imageUrl+productsList.get(position).img1).into(holder.productImage);
        holder.productTitle.setText(productsList.get(position).productName);
        holder.productDescription.setText("Description :"+productsList.get(position).type+productsList.get(position).category);
        holder.productPrice.setText("Price :"+productsList.get(position).price);
        holder.productRent.setText("Rent :"+productsList.get(position).rent);

    }

    @Override
    public int getItemCount() {
        return productsList==null?0:productsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

       public ImageView productImage;
       public TextView productTitle;
       public TextView productDescription;
        public TextView productPrice;
        public TextView productRent;
        Context context;
        ParticularProductClicked productClicked;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent,Context context,ParticularProductClicked productClicked) {
            super(inflater.inflate(R.layout.item_product,parent,false));
            this.context=context;
            this.productClicked=productClicked;
            productImage=(ImageView)itemView.findViewById(R.id.product_image_item_product);
            productTitle=(TextView) itemView.findViewById(R.id.product_title_item_product);
            productDescription=(TextView) itemView.findViewById(R.id.product_description_item_product);
            productPrice=(TextView) itemView.findViewById(R.id.product_price_item_product);
            productRent=(TextView) itemView.findViewById(R.id.product_rent_item_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     productClicked.onProductClicked(view,getAdapterPosition());
                }
            });
        }
    }


    public interface ParticularProductClicked{
        void onProductClicked(View view,int pos);
    }
}
