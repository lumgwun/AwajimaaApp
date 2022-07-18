package com.skylightapp.Classes;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import androidx.core.app.NotificationManagerCompat;

import com.skylightapp.R;


@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] smsExtra = (Object[]) intent.getExtras().get("pdus");
        StringBuilder body = new StringBuilder();

        for (Object o : smsExtra) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) o);
            body.append(sms.getMessageBody());
        }

        Notification notification = new Notification.Builder(context)
                .setContentText(body.toString())
                .setContentTitle("New Skylight Message")
                .setSmallIcon(R.drawable.ic_alert)
                .setStyle(new Notification.BigTextStyle().bigText(body.toString()))
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}