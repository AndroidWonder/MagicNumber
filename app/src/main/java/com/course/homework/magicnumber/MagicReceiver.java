package com.course.homework.magicnumber;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MagicReceiver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private Notification notifyDetails;
    private int SIMPLE_NOTFICATION_ID = 1;
    private String contentTitle = "Magic Number Found";
    private String contentText = "";
    private String tickerText = "New Alert, Click Me !!!";

    @Override
    public void onReceive(Context context, Intent intent) {

        String number = intent.getStringExtra("number");
        String threadName = intent.getStringExtra("thread");

        contentText = "MagicNumber   " + number + "   " + threadName;

        Log.e("MagicNumber", number + "   " + threadName);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyDetails = new Notification.Builder(context)

                .setContentTitle(contentTitle)    //set Notification text and icon
                .setContentText(contentText)
                .setSmallIcon(R.drawable.droid)

                .setTicker(tickerText)            //set status bar text
                .setWhen(System.currentTimeMillis())    //timestamp when event occurs
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setLights(Integer.MAX_VALUE, 500, 500)
                .build();

        mNotificationManager.notify(SIMPLE_NOTFICATION_ID, notifyDetails);


    }

}
