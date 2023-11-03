package com.example.clothy.User;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.clothy.R;
import com.example.clothy.data.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserHome extends AppCompatActivity {
    ImageView home,cart,profile,order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        elem();
        getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container,new UserHomeFrag()).commit();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container,new UserHomeFrag()).commit();
                home.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                order.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container,new CartFrag()).commit();
                home.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                order.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container,new ProfileFrag()).commit();
                home.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                order.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container,new OrderFrag()).commit();
                home.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                order.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });

    }



    private void elem(){
        home = findViewById(R.id.userhome_btn);
        cart = findViewById(R.id.cart_btn);
        profile=findViewById(R.id.profile_btn);
        order = findViewById(R.id.order_btn);

    }
}