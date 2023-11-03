package com.example.clothy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.viewholder.ProductViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    Context context;
    ArrayList<Product> products;
    DatabaseReference  productref;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        productref = FirebaseDatabase.getInstance(context.getString(R.string.DB)).getReference().child("products");
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(products.get(position).getProductImage()).into(holder.image_product);
        holder.name_product.setText(products.get(position).getProductName());
        holder.desc_product.setText(products.get(position).getProductDesc());
        holder.price_product.setText(String.valueOf(products.get(position).getProductPrice())+" DA");
        holder.category_product.setText(products.get(position).getProductCategory());
        holder.delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mdialog = new AlertDialog.Builder(context);
                mdialog.setTitle("Delete "+ products.get(position).getProductName());
                mdialog.setMessage("Are you sure you want to delete "+ products.get(position).getProductName()+" ?");
                mdialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        productref.child(products.get(position).getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error, check your internet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                mdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mdialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
