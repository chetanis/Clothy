package com.example.clothy.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.adapter.UserProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllProductsActivity extends AppCompatActivity {
    TextView cat;
    RecyclerView clothe_list;
    String category;
    DatabaseReference productref;
    ArrayList<Product> products;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_products);
        category = getIntent().getExtras().getString("cat");
        elem();
        cat.setText(category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        clothe_list.setLayoutManager(gridLayoutManager);
        getproduct();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    private void elem(){
        cat = findViewById(R.id.allproduct_cat);
        clothe_list = findViewById(R.id.clothe_list);
        productref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference()
                    .child("products");
        back_btn = findViewById(R.id.back_btn2);
    }

    private void getproduct(){
        products = new ArrayList<>();
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()){
                    if (oneSnapshot.child("productCategory").getValue().equals(category) || category.equals("Just Arrived")){
                        Product product = oneSnapshot.getValue(Product.class);
                        products.add(product);
                    }
                }
                UserProductAdapter adapter = new UserProductAdapter(ViewAllProductsActivity.this,products,false);
                clothe_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}