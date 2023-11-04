package com.example.clothy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothy.Admin.HomeActivity;
import com.example.clothy.Models.User;
import com.example.clothy.User.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText name,email,password;
    AppCompatButton signup;
    TextView goto_login;
    FirebaseAuth auth;
    DatabaseReference userref;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        elem();
        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test()){
                    loading.show();
                    CreateAnAccount();
                }
            }
        });

    }



    private void elem(){
        goto_login=findViewById(R.id.goto_login);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        signup = findViewById(R.id.signup_button);
        auth = FirebaseAuth.getInstance();
        userref = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("Users");
        loading = new ProgressDialog(this);
        loading.setMessage("Please wait");
    }


    private boolean test(){
        if (name.getText().toString().isEmpty()){
            name.setError("please fill the name");
            return false;
        }else if (email.getText().toString().isEmpty()){
            email.setError("please fill the name");
            return false;
        }else if (password.getText().toString().isEmpty()){
            password.setError("please fill the name");
            return false;
        }
        return true;
    }


    private void CreateAnAccount(){
        auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(email.getText().toString(),name.getText().toString());
                            saveUserDataInDB(user);
                        }else {
                            loading.dismiss();
                            Toast.makeText(SignupActivity.this,"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    private void  saveUserDataInDB(User user){
        userref.child(auth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loading.dismiss();
                    Intent i = new Intent(SignupActivity.this, UserHome.class);
                    startActivity(i);
                    finish();
                }else {
                    loading.dismiss();
                    Toast.makeText(SignupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}