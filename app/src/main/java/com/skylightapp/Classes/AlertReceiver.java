package com.skylightapp.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.skylightapp.NotificationAct;
import com.skylightapp.R;

public class AlertReceiver extends BroadcastReceiver {
    public static final String TAG = "AlertReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationAct notificationHelper = new NotificationAct();
        int id = (int) System.currentTimeMillis();


        NotificationCompat.Builder nb1 = notificationHelper.getChannel1Notification(
                notificationHelper.getString(R.string.savings_alert), notificationHelper.getString(R.string.savings_msg)
        );
        notificationHelper.getManager().notify(1, nb1.build());

        NotificationCompat.Builder nb2 = notificationHelper.getChannel2Notification(
                notificationHelper.getString(R.string.new_package), notificationHelper.getString(R.string.package_msg)
        );
        notificationHelper.getManager().notify(2, nb2.build());


        NotificationCompat.Builder nb3 = notificationHelper.getChannel3Notification(
                notificationHelper.getString(R.string.newCus), notificationHelper.getString(R.string.cus_msg)
        );
        notificationHelper.getManager().notify(3, nb3.build());



        NotificationCompat.Builder nb4 = notificationHelper.getChannel4Notification(
                notificationHelper.getString(R.string.teller_Alert), notificationHelper.getString(R.string.teller_msg)
        );
        notificationHelper.getManager().notify(4, nb4.build());



        NotificationCompat.Builder nb5 = notificationHelper.getChannel5Notification(
                notificationHelper.getString(R.string.admin_signup), notificationHelper.getString(R.string.admin_msg)
        );
        notificationHelper.getManager().notify(5, nb5.build());


        NotificationCompat.Builder nb6 = notificationHelper.getChannel6Notification(
                notificationHelper.getString(R.string.tellerReportAlert), notificationHelper.getString(R.string.tellerReport)
        );
        notificationHelper.getManager().notify(6, nb2.build());

        NotificationCompat.Builder nb7 = notificationHelper.getChannel7Notification(
                notificationHelper.getString(R.string.loanAlert), notificationHelper.getString(R.string.loanAlertDetails)
        );
        notificationHelper.getManager().notify(7, nb2.build());

    }
}