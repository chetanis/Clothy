package com.example.clothy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clothy.Admin.HomeActivity;
import com.example.clothy.User.UserHome;
import com.example.clothy.data.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
//        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC);
//        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
//            @Override
//            public void onSuccess(String s) {
//                DatabaseReference userref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("Users")
//                        .child(auth.getCurrentUser().getUid()).child("token");
//                userref.setValue(s);
//            }
//        });

        //adding splash screen for 0.7 second
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    sleep(700);
                }catch (Exception e){

                }finally {
                    if (auth.getCurrentUser() != null){
                        Intent i = new Intent(MainActivity.this, UserHome.class);
                        startActivity(i);
                        finish();
                    }else {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        };
        thread.start();

    }
}