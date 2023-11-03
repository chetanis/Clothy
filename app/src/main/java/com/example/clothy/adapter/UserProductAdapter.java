package com.example.clothy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.User.ProductDetailsActivity;
import com.example.clothy.viewholder.UserProductViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductViewHolder> {
    Context context;
    ArrayList<Product> products;
    Boolean home;

    public UserProductAdapter(Context context, ArrayList<Product> products, Boolean home) {
        this.context = context;
        this.products = products;
        this.home = home;
    }

    @NonNull
    @Override
    public UserProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_userproduct,parent,false);
        return new UserProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.price.setText(String.valueOf(products.get(position).getProductPrice()+" DZD"));
    holder.title.setText(products.get(position).getProductName());
    Picasso.get().load(products.get(position).getProductImage()).into(holder.image);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ProductDetailsActivity.class);
            i.putExtra("productID",products.get(position).getID());
            context.startActivity(i);
        }
    });
    }

    @Override
    public int getItemCount() {
        if (home){
            if (products.size()<5){
                return products.size();
            }else {
            return 5;}
        }
        return products.size();
    }
}
