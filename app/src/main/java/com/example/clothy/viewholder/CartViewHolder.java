package com.example.clothy.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.clothy.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView cart_image,cart_close;
    public TextView cart_title, cart_price;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cart_image = itemView.findViewById(R.id.cart_image);
        cart_price = itemView.findViewById(R.id.cart_price);
        cart_title = itemView.findViewById(R.id.cart_title);
        cart_close = itemView.findViewById(R.id.cart_close);
    }

}
