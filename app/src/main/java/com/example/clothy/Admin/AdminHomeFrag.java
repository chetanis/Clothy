 package com.example.clothy.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clothy.Models.Order;
import com.example.clothy.R;
import com.example.clothy.adapter.OrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


 public class AdminHomeFrag extends Fragment {
    RecyclerView admin_orderlist;
     DatabaseReference orderref;
    View mview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.fragment_admin_home, container, false);
        elem();
        getorders();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        admin_orderlist.setLayoutManager(manager);

        return mview;

    }


    private void elem(){
        admin_orderlist = mview.findViewById(R.id.admin_orderlist);
        orderref = FirebaseDatabase.getInstance(getContext().getString(R.string.DB)).getReference()
                .child("orders");
    }


     private void getorders(){
         ArrayList<Order> orders = new ArrayList<>();
         orderref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 orders.clear();
                 for (DataSnapshot oneSnapShot : snapshot.getChildren()) {
                     if (oneSnapShot.child("state").getValue().toString().equals("0")){
                        Order order = oneSnapShot.getValue(Order.class);
                        orders.add(order);
                     }
                 }
                 OrderAdapter adapter = new OrderAdapter(getContext(), orders,true);
                 admin_orderlist.setAdapter(adapter);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
     }
}