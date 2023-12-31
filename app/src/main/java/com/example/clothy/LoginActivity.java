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
import com.example.clothy.User.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    AppCompatButton login;
    ProgressDialog loading;
    FirebaseAuth auth;
    TextView goto_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize UI elements
        elem();
        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the SignupActivity when the "goto_signup" TextView is clicked
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test()){
                    // If validation passes, show a progress dialog and perform login
                    loading.show();
                    Login();
                }
            }
        });
    }

    // Function to perform input validation
    private boolean test(){
        if (email.getText().toString().isEmpty()){
            email.setError("fill the email");
            return  false;
        }else if (password.getText().toString().isEmpty()){
            password.setError("fill the password");
            return  false;
        }
        return true;
    }

    // Function to initialize UI elements and Firebase authentication
    private void elem(){
        goto_signup = findViewById(R.id.goto_signup);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        auth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(this);
        loading.setMessage("Please wait");
    }

    // Function to perform user login using Firebase authentication
    private void Login(){
        auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loading.dismiss();
                    Intent i = new Intent(LoginActivity.this, UserHome.class);
                    startActivity(i);
                    finish();
                }else{
                    loading.dismiss();
                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}