package com.example.lenovo.renterskey.ExtraClasses;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.renterskey.R;

/**
 * Created by lenovo on 02-12-2017.
 */

public class HorizontalScrollViewAdapter extends RecyclerView.Adapter<HorizontalScrollViewAdapter.ViewHolder> {

    private static final int LENGTH=8;
    private final Drawable[] picture;
    private final String[] text;
    private final OnItemClicked onItemClicked;

    public HorizontalScrollViewAdapter(Context context,OnItemClicked onItemClicked){
        Resources resources=context.getResources();
        text=resources.getStringArray(R.array.products_type);
        TypedArray a=resources.obtainTypedArray(R.array.products_images);
        picture=new Drawable[a.length()];
        for(int i=0;i<text.length;i++){
           picture[i]=a.getDrawable(i);
        }
        a.recycle();
        this.onItemClicked=onItemClicked;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()),parent,onItemClicked);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.picture.setImageDrawable(picture[position%picture.length]);
        holder.textView.setText(text[position%text.length]);
    }

    @Override
    public int getItemCount() {
        return LENGTH;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView textView;
        public OnItemClicked onItemClicked;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent,OnItemClicked onItemClicked) {
            super(inflater.inflate(R.layout.horizontal_scrollview, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.horizontalscrollview_imageview);
            textView=(TextView)itemView.findViewById(R.id.horizontalscrollview_textview);
            this.onItemClicked=onItemClicked;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked.onClickFunction(view,getAdapterPosition());
                  //  Context context = view.getContext();
                    //start new activity corresponding to item selected
                }
            });
        }
    }

    public interface OnItemClicked{
        void onClickFunction(View view,int pos);
    }
}


