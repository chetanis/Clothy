package com.example.clothy.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothy.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public ImageView order_image;
    public TextView order_number,order_willaya,order_state,order_adress;
    public Button accept_btn,decline_btn;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        order_adress = itemView.findViewById(R.id.order_adress);
        decline_btn = itemView.findViewById(R.id.decline_btn);
        accept_btn = itemView.findViewById(R.id.accept_btn);
        order_image = itemView.findViewById(R.id.order_image);
        order_state = itemView.findViewById(R.id.order_state);
        order_willaya = itemView.findViewById(R.id.order_willaya);
        order_number = itemView.findViewById(R.id.order_number);
    }
}
