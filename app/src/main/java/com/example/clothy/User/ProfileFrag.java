package com.example.clothy.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.clothy.Admin.HomeActivity;
import com.example.clothy.MainActivity;
import com.example.clothy.Models.User;
import com.example.clothy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFrag extends Fragment {

    Button logout_btn,goto_admin;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    TextView profile_email,profile_fullname;
    DatabaseReference userref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview=inflater.inflate(R.layout.fragment_profile, container, false);
        logout_btn = mview.findViewById(R.id.logout_btn);
        userref = FirebaseDatabase.getInstance(getContext().getString(R.string.DB)).getReference().child("Users")
                        .child(mauth.getCurrentUser().getUid());
        profile_email = mview.findViewById(R.id.profile_email);
        goto_admin = mview.findViewById(R.id.goto_admin);
        profile_fullname = mview.findViewById(R.id.profile_fullname);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                profile_email.setText(user.getEmail());
                profile_fullname.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        goto_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),HomeActivity.class);
                startActivity(i);
            }
        });
        return mview;
    }
}