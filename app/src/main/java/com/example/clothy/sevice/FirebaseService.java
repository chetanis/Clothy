package com.example.clothy.sevice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.clothy.MainActivity;
import com.example.clothy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {
    public static String CHANNEL_ID = "My channel";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Intent i = new Intent(this, MainActivity.class);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt();
        createNotificationChannel(notificationManager);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new Notification.Builder(this,CHANNEL_ID)
                                    .setContentTitle(message.getData().get("title"))
                                    .setContentText(message.getData().get("message"))
                                    .setSmallIcon(R.mipmap.ic_launcher_round).setAutoCancel(true)
                                    .setContentIntent(pendingIntent).build();
        notificationManager.notify(notificationID,notification);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void createNotificationChannel(NotificationManager manager){
        String channel_name = "channelName";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channel_name,NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("description");
        channel.enableLights(true);
        manager.createNotificationChannel(channel);
    }
}
