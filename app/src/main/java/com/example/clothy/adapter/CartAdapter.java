package com.example.clothy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.viewholder.CartViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    Context context;
    ArrayList<Product> products;
    DatabaseReference productref;
    FirebaseAuth mauth = FirebaseAuth.getInstance();

    public CartAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        if (context!=null){
            productref = FirebaseDatabase.getInstance(context.getString(R.string.DB)).getReference()
                        .child("cart").child(mauth.getCurrentUser().getUid());
        }
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cart_title.setText(products.get(position).getProductName());
        holder.cart_price.setText(String.valueOf(products.get(position).getProductPrice())+" DZD");
        Picasso.get().load(products.get(position).getProductImage()).into(holder.cart_image);
        holder.cart_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productref.child(products.get(position).getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "item removed", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
