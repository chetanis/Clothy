package com.example.clothy.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.adapter.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProductFrag extends Fragment {
    View mview;
    FloatingActionButton add;
    RecyclerView Product_list;
    DatabaseReference productref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_product, container, false);
        add = mview.findViewById(R.id.add_btn);
        Product_list = mview.findViewById(R.id.Product_list);
        productref = FirebaseDatabase.getInstance(getContext().getString(R.string.DB)).getReference().child("products");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddProductActivity.class);
                startActivity(i);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        Product_list.setLayoutManager(gridLayoutManager);
        initAdapter();

        return mview;
    }

    private void initAdapter(){
        ArrayList<Product> products = new ArrayList<>();
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    products.add(product);
                }
                ProductAdapter adapter = new ProductAdapter(getContext(),products);
                Product_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}