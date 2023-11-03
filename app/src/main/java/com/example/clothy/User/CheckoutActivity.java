package com.example.clothy.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothy.Models.Order;
import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    int price;
    ArrayList<Product> products ;
    TextView pricetext;
    EditText fullname,phonenumber,willaya,address;
    AppCompatButton confirmcheckout;
    DatabaseReference orderref,cartref;
    String order_id;
    FirebaseAuth mauth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        elem();
        pricetext.setText(String.valueOf(price));
        confirmcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendorder();
            }
        });
    }


    private void  elem(){
        products = (ArrayList<Product>) getIntent().getSerializableExtra("products");
        price = getIntent().getIntExtra("price",-1);
        pricetext = findViewById(R.id.textView);
        fullname = findViewById(R.id.fullname);
        phonenumber = findViewById(R.id.phonenumber);
        willaya = findViewById(R.id.willaya);
        address = findViewById(R.id.address);
        confirmcheckout = findViewById(R.id.confirmcheckout);
        cartref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("cart").child(mauth.getCurrentUser().getUid());
        orderref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("orders");
    }

    private  void sendorder(){
        orderref = orderref.push();
        order_id = orderref.getKey();
        Order order = new Order(products,fullname.getText().toString(),phonenumber.getText().toString(),willaya.getText().toString(),
                                address.getText().toString(),mauth.getCurrentUser().getUid().toString(),order_id,price,0);
        orderref.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    cartref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                confirmcheckout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                                Toast.makeText(CheckoutActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(CheckoutActivity.this, "somthing went wrong try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}