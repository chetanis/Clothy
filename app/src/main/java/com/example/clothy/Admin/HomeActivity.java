package com.example.clothy.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.clothy.R;

public class HomeActivity extends AppCompatActivity {
    ImageView productBtn, homeBtn,optionbtn;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        elem();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdminHomeFrag()).commit();
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdminHomeFrag()).commit();
                homeBtn.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                productBtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                optionbtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });
        productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProductFrag()).commit();
                productBtn.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                homeBtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                optionbtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });
        optionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdminOptionFrag()).commit();
                productBtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                homeBtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                optionbtn.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);

            }
        });
    }


    private void elem(){
        productBtn = findViewById(R.id.product_btn);
        homeBtn = findViewById(R.id.home_btn);
        optionbtn = findViewById(R.id.option);
    }
}