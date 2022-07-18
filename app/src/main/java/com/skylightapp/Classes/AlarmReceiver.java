package com.skylightapp.Classes;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import com.skylightapp.Admins.SODueDateListAct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.Tellers.PackageAllCusAct;

import java.util.Calendar;


import static com.skylightapp.Admins.SODueDateListAct.IMPORTANT_LIST_ID;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.StandingOrder.SO_ID;

@SuppressWarnings("ALL")
public class AlarmReceiver extends BroadcastReceiver {
    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        String mReceivedID = intent.getStringExtra(PACKAGE_ID);
        String birthdayReceivedID = intent.getStringExtra(BIRTHDAY_ID);
        String soID = intent.getStringExtra(SO_ID);
        String savingsID = intent.getStringExtra(REPORT_ID);

        DBHelper dbHelper = new DBHelper(context);
        Birthday birthday = dbHelper.getBirthdayReminder(birthdayReceivedID);
        SkyLightPackage skyLightPackage = dbHelper.getPackageReminder(mReceivedID);
        ImportantDates reminder = dbHelper.getReminder(mReceivedID);
        String mTitle = reminder.getTitle();

        Intent birthDayIntent = new Intent(context, SODueDateListAct.class);
        birthDayIntent.putExtra(IMPORTANT_LIST_ID, birthdayReceivedID);

        Intent packageIntent = new Intent(context, PackageAllCusAct.class);
        packageIntent.putExtra(PACKAGE_ID, mReceivedID);

        PendingIntent mClick = PendingIntent.getActivity(context, Integer.valueOf(mReceivedID), packageIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification
        PendingIntent mClick2 = PendingIntent.getActivity(context, Integer.valueOf(birthdayReceivedID), birthDayIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.skylight_2))
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setTicker(mTitle)
                .setContentText(mTitle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(mClick)
                .setContentIntent(mClick2)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(Integer.parseInt(mReceivedID), mBuilder.build());

         nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(Integer.parseInt(String.valueOf(birthdayReceivedID)), mBuilder.build());
    }

    public void setAlarm(Context context, Calendar calendar, int ID) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(PACKAGE_ID, Integer.toString(ID));
        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification time
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using notification time
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                mPendingIntent);

        // Restart alarm if device is rebooted
        ComponentName receiver = new ComponentName(context, BootBroadcastReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(PACKAGE_ID, Integer.toString(ID));
        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification timein
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using initial notification time and repeat interval time
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                RepeatTime , mPendingIntent);

        // Restart alarm if device is rebooted
        ComponentName receiver = new ComponentName(context, BootBroadcastReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context, int ID) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Cancel Alarm using Reminder ID
        mPendingIntent = PendingIntent.getBroadcast(context, ID, new Intent(context, AlarmReceiver.class), 0);
        mAlarmManager.cancel(mPendingIntent);

        // Disable alarm
        ComponentName receiver = new ComponentName(context, BootBroadcastReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

    }
}