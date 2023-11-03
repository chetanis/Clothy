package com.example.clothy.Models;

public class PushNotification {
    NotificationData data;
    String to;

    public PushNotification(NotificationData data, String to) {
        this.data = data;
        this.to = to;
    }
}
