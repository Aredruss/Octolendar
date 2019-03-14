package com.redbox.octolendar.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

import com.redbox.octolendar.R;
import com.redbox.octolendar.database.model.Event;

public class NotificationUtils extends ContextWrapper {
    private NotificationManager manager;
    public static final String EVENT_CHANNEL_ID = "com.redbox.octolendar.EVENT";
    public static final String EVENT_CHANNEL_NAME = "EVENT CHANNEL";

    public NotificationUtils(Context base){
        super(base);
        createChannels();
    }

    public void createChannels(){
        NotificationChannel notificationChannel = new NotificationChannel(EVENT_CHANNEL_ID,
                EVENT_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(notificationChannel);

    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public Notification.Builder getEventChannelNotification(String title){


        return new Notification.Builder(getApplicationContext(), EVENT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText("Don't forget about that")
                .setAutoCancel(true);
    }
}