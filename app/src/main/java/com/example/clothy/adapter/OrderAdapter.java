package com.example.clothy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothy.Models.NotificationData;
import com.example.clothy.Models.Order;
import com.example.clothy.Models.Product;
import com.example.clothy.Models.PushNotification;
import com.example.clothy.R;
import com.example.clothy.User.ProductDetailsActivity;
import com.example.clothy.data.Constants;
import com.example.clothy.data.NotificationApi;
import com.example.clothy.viewholder.OrderViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    Context context;
    ArrayList<Order> orders;
    boolean admin = false;
    ProgressDialog loading;

    public OrderAdapter(Context context, ArrayList<Order> orders, boolean admin) {
        this.context = context;
        this.orders = orders;
        this.admin = admin;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loading = new ProgressDialog(context);
        loading.setTitle("Sending notification");
        loading.setMessage("Please wait we are sending the message");
        holder.order_willaya.setText(orders.get(position).getWillaya());
        holder.order_adress.setText(orders.get(position).getAddress());
        Picasso.get().load(orders.get(position).getProducts().get(0).getProductImage()).into(holder.order_image);
        holder.order_number.setText("Order nº"+String.valueOf(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView fullname = new TextView(context);
                fullname.setText("User name: "+orders.get(position).getFullname());
                TextView phonenumber = new TextView(context);
                phonenumber.setText("User phone number: "+orders.get(position).getPhonenumber());
                TextView willaya = new TextView(context);
                willaya.setText("Willaya: "+orders.get(position).getWillaya());
                TextView address = new TextView(context);
                address.setText("Address: "+orders.get(position).getAddress());
                linearLayout.addView(fullname);
                linearLayout.addView(phonenumber);
                linearLayout.addView(willaya);
                linearLayout.addView(address);

                for (Product product:orders.get(position).getProducts()){
                    ImageView imageView = new ImageView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500, 500);
                    params.gravity= Gravity.CENTER;
                    params.bottomMargin = 20;
                    imageView.setLayoutParams(params);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.get().load(product.getProductImage()).into(imageView);
                    imageView.requestLayout();
                    linearLayout.addView(imageView);
                }
                builder.setView(linearLayout);
                builder.show();
            }
        });

        if (orders.get(position).getState()==0){
            holder.order_state.setText("Waiting");
            holder.order_state.setTextColor(context.getColor(R.color.orange));
        }else if(orders.get(position).getState() == 1){
            holder.order_state.setText("Accepted");
            holder.order_state.setTextColor(context.getColor(R.color.green));
        }else if(orders.get(position).getState() == 2){
            holder.order_state.setText("Refused");
            holder.order_state.setTextColor(context.getColor(R.color.red));
        }else{
            holder.order_state.setText("Completed");
            holder.order_state.setTextColor(context.getColor(R.color.blue1));
        }

        if (admin){
            DatabaseReference orderref = FirebaseDatabase.getInstance(context.getString(R.string.DB)).getReference()
                                            .child("orders").child(orders.get(position).getOrder_id()).child("state");
            holder.accept_btn.setVisibility(View.VISIBLE);
            holder.decline_btn.setVisibility(View.VISIBLE);
            holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.show();
                    acceptOrder(orders.get(position).getOrder_id(),orders.get(position).getUser_id());
                }
            });
            holder.decline_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderref.setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Order Declined", Toast.LENGTH_SHORT).show();
                            send_notification(orders.get(position).getUser_id());
                        }
                    });
                }
            });
        }
        }


    @Override
    public int getItemCount() {
        return orders.size();
    }


    private void send_notification(String UserID){
        DatabaseReference userref = FirebaseDatabase.getInstance(context.getString(R.string.DB)).getReference().child("Users")
                .child(UserID).child("token");
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String token = snapshot.getValue().toString();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                NotificationApi notificationApi = retrofit.create(NotificationApi.class);
                NotificationData data = new NotificationData("Order Accepted","Your order has been accepted");
                PushNotification pushNotification = new PushNotification(data,token);
                Call<ResponseBody> response= notificationApi.postNotification(pushNotification);

                response.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }else{
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void acceptOrder(String idOrder , String userId){
        DatabaseReference OrdersRef = FirebaseDatabase.getInstance(context.getString(R.string.DB))
                .getReference().child("orders").child(idOrder);
        OrdersRef.child("state").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // send notification
                    send_notification(userId);
                }
            }
        });
    }
}
