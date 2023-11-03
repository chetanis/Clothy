package com.example.clothy.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothy.R;


public class UserProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title,price;
    public UserProductViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        image = itemView.findViewById(R.id.image);

    }
}
