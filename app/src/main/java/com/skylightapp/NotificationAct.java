package com.skylightapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.skylightapp.Admins.SODueDateListAct;
import com.skylightapp.Classes.AlertReceiver;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.SkyLightPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class NotificationAct extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    public static final String SAVINGS_CHANNEL_ID = "10002" ;
    public static final String SAVINGS_CHANNEL_Name = "New Savings" ;
    public static final String NEW_PACK_CHANNEL_ID = "10003" ;
    public static final String NEW_PACK_CHANNEL_Name = "New Package" ;
    public static final String NEW_CUS_CHANNEL_ID = "10004" ;
    public static final String NEW_CUS_CHANNEL_Name = "New Customer" ;
    public static final String NEW_TELLER_CHANNEL_ID = "10005" ;
    public static final String NEW_TELLER_CHANNEL_Name = "New Teller" ;
    public static final String LOAN_CHANNEL_ID = "10006" ;
    public static final String LOAN_CHANNEL_Name = "New Loan" ;
    public static final String TRANSACTION_CHANNEL_ID = "10007" ;
    public static final String TRANSACTION_CHANNEL_Name = "New Transaction" ;
    public static final String TELLER_REPORT_CHANNEL_ID = "10008" ;
    public static final String TELLER_REPORT_CHANNEL_Name = "New Teller Report" ;
    private final static String default_notification_channel_id = "default" ;
    public static final String CHANNEL_ID = "NotificationAct.CHANNEL_ID";

    public static final String NEW_ADMIN_CHANNEL_ID = "New Admin" ;
    public static final String NEW_ADMIN_CHANNEL_NAME = "10009" ;

    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "Add transactions reminder";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "Due and overdue bills";
    private NotificationManager mManager;
    TextView txtNot;
    static int notificationCount = 0 ;
    private CustomerDailyReport customerDailyReport;
    private SkyLightPackage skyLightPackage;
    String endDate;
    Bundle bundle;
    int delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notification);
        txtNot = findViewById(R.id. notID ) ;
        skyLightPackage= new SkyLightPackage();
        bundle=new Bundle();
        Intent snoozeIntent = new Intent(NotificationAct. this, NotificationAct. class ) ;
        snoozeIntent.putExtra( "fromNotification" , true ) ;
        snoozeIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        snoozeIntent.setAction( "ACTION_SNOOZE" ) ;
        snoozeIntent.putExtra( "EXTRA_NOTIFICATION_ID" , 0 ) ;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(snoozeIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        bundle=getIntent().getExtras();
        if(bundle !=null){
            skyLightPackage=bundle.getParcelable("Package");
            endDate=skyLightPackage.getDateEnded();

        }
        long futureInMillis = SystemClock. elapsedRealtime () + 30000 ;
        PendingIntent snoozePendingIntent = PendingIntent. getBroadcast (NotificationAct. this, 0 , snoozeIntent , 0 ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotificationAct. this, default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Skylight Message" ) ;
        mBuilder.setContentText( "Info" ) ;
        mBuilder.setTicker( "Pay small small, and achieve big things" ) ;
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        mBuilder.addAction(R.drawable. ic_launcher_foreground , "Snooze" , snoozePendingIntent) ;
        mBuilder.setAutoCancel( true ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , snoozePendingIntent) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        notificationCount ++ ;
        txtNot .setText(String. valueOf ( notificationCount )) ;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel savingsChannel = new NotificationChannel(SAVINGS_CHANNEL_ID, SAVINGS_CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        savingsChannel.enableLights(true);
        savingsChannel.enableVibration(true);
        savingsChannel.setLightColor(R.color.colorPrimary);
        savingsChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(savingsChannel);

        NotificationChannel packageChannel = new NotificationChannel(NEW_PACK_CHANNEL_ID, NEW_PACK_CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        packageChannel.enableLights(true);
        packageChannel.enableVibration(true);
        packageChannel.setLightColor(R.color.colorPrimary);
        packageChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(packageChannel);


        NotificationChannel transactionChannel = new NotificationChannel(TRANSACTION_CHANNEL_ID, TRANSACTION_CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        transactionChannel.enableLights(true);
        transactionChannel.enableVibration(true);
        transactionChannel.setLightColor(R.color.colorPrimary);
        transactionChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(transactionChannel);



        NotificationChannel loanChannel = new NotificationChannel(LOAN_CHANNEL_ID, LOAN_CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        loanChannel.enableLights(true);
        loanChannel.enableVibration(true);
        loanChannel.setLightColor(R.color.colorPrimary);
        loanChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(loanChannel);




        NotificationChannel newCustomerChannel = new NotificationChannel(NEW_CUS_CHANNEL_ID, NEW_CUS_CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        newCustomerChannel.enableLights(true);
        newCustomerChannel.enableVibration(true);
        newCustomerChannel.setLightColor(R.color.colorPrimary);
        newCustomerChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(newCustomerChannel);


        NotificationChannel newTellerChannel = new NotificationChannel(NEW_TELLER_CHANNEL_ID, NEW_TELLER_CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        newTellerChannel.enableLights(true);
        newTellerChannel.enableVibration(true);
        newTellerChannel.setLightColor(R.color.colorPrimary);
        newTellerChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(newTellerChannel);



        NotificationChannel newAdminChannel = new NotificationChannel(NEW_ADMIN_CHANNEL_ID, NEW_ADMIN_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        newAdminChannel.enableLights(true);
        newAdminChannel.enableVibration(true);
        newAdminChannel.setLightColor(R.color.colorPrimary);
        newAdminChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(newAdminChannel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, SAVINGS_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

    public NotificationCompat.Builder getChannel2Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NEW_PACK_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
    public NotificationCompat.Builder getChannel3Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NEW_CUS_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
    public NotificationCompat.Builder getChannel4Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NEW_TELLER_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
    public NotificationCompat.Builder getChannel5Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NEW_ADMIN_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
    public NotificationCompat.Builder getChannel6Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, TELLER_REPORT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
    public NotificationCompat.Builder getChannel7Notification(String title, String message) {
        Intent intent = new Intent(this, SODueDateListAct.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, LOAN_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    private void setNotifications(int dueDayOfYear) {
        LocalDate dueDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dueDate = LocalDate.ofYearDay(LocalDate.now().getYear(), dueDayOfYear);
        }
        LocalDateTime prevDay = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prevDay = dueDate.minusDays(1).atStartOfDay().plusHours(14).plusMinutes(7);
        }
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        int reqCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, prevDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), pendingIntent);
        }
    }

}