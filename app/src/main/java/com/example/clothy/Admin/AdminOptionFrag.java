package com.example.clothy.Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.clothy.Models.NotificationData;
import com.example.clothy.Models.PushNotification;
import com.example.clothy.R;
import com.example.clothy.User.UserHome;
import com.example.clothy.data.Constants;
import com.example.clothy.data.NotificationApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminOptionFrag extends Fragment {

    View mview;
    Button goto_user,send_notif;
    ProgressDialog loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview =inflater.inflate(R.layout.fragment_admin_option, container, false);
        goto_user = mview.findViewById(R.id.goto_user);
        send_notif = mview.findViewById(R.id.send_notif);
        loading = new ProgressDialog(getContext());
        loading.setTitle("Sending notification");
        loading.setMessage("Please wait we are sending the message");

        goto_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UserHome.class);
                startActivity(i);
            }
        });
        send_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Send notification to user");
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                EditText notification_title = new EditText(getContext());
                notification_title.setHint("Notification title");
                EditText notification_message = new EditText(getContext());
                notification_message.setHint("notification message");
                linearLayout.addView(notification_title);
                linearLayout.addView(notification_message);
                builder.setView(linearLayout);
                builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loading.show();
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        NotificationApi notificationApi = retrofit.create(NotificationApi.class);
                        NotificationData data = new NotificationData(notification_title.getText().toString(),
                                                    notification_message.getText().toString());
                        PushNotification pushNotification = new PushNotification(data,Constants.TOPIC);

                        Call<ResponseBody> response= notificationApi.postNotification(pushNotification);
                        response.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                            }
                        });
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });


        return mview;
    }
}