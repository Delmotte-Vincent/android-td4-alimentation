package com.main.exercice2.androidproject;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import java.util.Objects;

public class Notification extends Application {

    public static final String CHANNEL_ID ="channel1";
    private static NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel("channel de notification","Ce cannal sert à envoyer les notifications", NotificationManager.IMPORTANCE_HIGH);
    }

    private void createNotificationChannel(String name,String description, int importanceDefault) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importanceDefault);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

}
