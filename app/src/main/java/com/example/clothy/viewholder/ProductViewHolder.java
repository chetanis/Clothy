package com.example.clothy.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.clothy.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView image_product,delete_product;
    public TextView name_product,desc_product,price_product,category_product;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        image_product = itemView.findViewById(R.id.image_product);
        delete_product = itemView.findViewById(R.id.delete_product);
        name_product = itemView.findViewById(R.id.name_product);
        desc_product = itemView.findViewById(R.id.desc_product);
        price_product = itemView.findViewById(R.id.price_product);
        category_product = itemView.findViewById(R.id.category_product);
    }
}
