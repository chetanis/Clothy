package com.example.clothy.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.adapter.CartAdapter;
import com.example.clothy.adapter.UserProductAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CartFrag extends Fragment {

    RecyclerView user_cartlist;
    View mview;
    AppCompatButton checkout_button;
    int price=0;
    TextView checkout_price;
    LinearLayout empty_cart,full_cart;
    DatabaseReference productref;
    FirebaseAuth mauth;
    ArrayList<Product>  products;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_cart, container, false);
        elem();
        intiadapter();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        user_cartlist.setLayoutManager(manager);
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),CheckoutActivity.class);
                i.putExtra("products",products);
                i.putExtra("price",price);
                startActivity(i);
            }
        });
        return mview;
    }
    private void elem(){
        user_cartlist = mview.findViewById(R.id.user_cartlist);
        mauth = FirebaseAuth.getInstance();
        productref= FirebaseDatabase.getInstance(getContext().getString(R.string.DB)).getReference()
                    .child("cart").child(mauth.getCurrentUser().getUid());
        products = new ArrayList<>();
        checkout_price = mview.findViewById(R.id.checkout_price);
        checkout_button = mview.findViewById(R.id.checkout_button);
        full_cart = mview.findViewById(R.id.full_cart);
        empty_cart = mview.findViewById(R.id.empty_cart);
    }


    private void intiadapter(){
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot oneSnapshot :snapshot.getChildren()){
                    Product product = oneSnapshot.getValue(Product.class);
                    products.add(product);
                }
                totalprice();
                CartAdapter adapter = new CartAdapter(getContext(),products);
                user_cartlist.setAdapter(adapter);
                if (products.isEmpty()){
                    empty_cart.setVisibility(View.VISIBLE);
                    full_cart.setVisibility(View.INVISIBLE);
                }else{
                    full_cart.setVisibility(View.VISIBLE);
                    empty_cart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void totalprice(){
        price =0;
        for (Product product : products){
            price += product.getProductPrice();
            checkout_price.setText(String.valueOf(price)+" DZD");
        }
    }
}