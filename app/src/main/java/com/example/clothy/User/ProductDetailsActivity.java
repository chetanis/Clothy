package com.example.clothy.User;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView titel,price,desc;
    ImageView image,back_btn;
    String id;
    DatabaseReference productref,cartref;
    FirebaseAuth mauth;
    Product product;
    AppCompatButton addtocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        id = getIntent().getStringExtra("productID");
        elem();
        GetProduct();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToCart();
                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });
    }
    private void GetProduct(){
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.getValue(Product.class);
                if (product!= null){
                    titel.setText(product.getProductName());
                    price.setText(String.valueOf(product.getProductPrice()));
                    desc.setText(product.getProductDesc());
                    Picasso.get().load(product.getProductImage()).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void elem(){
        titel = findViewById(R.id.details_title);
        price = findViewById(R.id.details_price);
        desc = findViewById(R.id.details_desc);
        image = findViewById(R.id.details_image);
        back_btn= findViewById(R.id.back_btn);
        addtocart=findViewById(R.id.addtocart);
        mauth = FirebaseAuth.getInstance();
        cartref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("cart").child(mauth.getCurrentUser().getUid());
        productref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("products")
                .child(id);
    }

    private void SendToCart(){
        if (product!=null){
            cartref.child(id).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ProductDetailsActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ProductDetailsActivity.this, "something went wrong try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}