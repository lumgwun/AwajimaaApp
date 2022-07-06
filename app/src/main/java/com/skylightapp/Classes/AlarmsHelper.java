package com.skylightapp.Classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;



public class AlarmsHelper {
    public static void setAllNotificationAlarms(Context context) {

        Intent serviceIntent = new Intent(context, SetAlarmsService.class);
        context.startService(serviceIntent);
    }

    public static void cancelAllReportAlarm(Context context, ArrayList<CustomerDailyReport> dailyReports) {
        for (CustomerDailyReport b: dailyReports) {
            cancelAlarm(context, b.getRecordCustomerName().hashCode());
        }
    }
    /*public static void cancelAllLoanAlarm(Context context, ArrayList<Loan> loans) {
        for (Loan b: loans) {
            cancelAlarm(context, b.getLoanId().hashCode());
        }
    }
    public static void cancelAllPackageAlarm(Context context, ArrayList<SkyLightPackage> skyLightPackages) {
        for (SkyLightPackage b: skyLightPackages) {
            //cancelAlarm(context, b.getPackageId().hashCode());
        }
    }*/
    public static void cancelAllPayeeAlarm(Context context, ArrayList<Payee> payees) {
        for (Payee b: payees) {
           // cancelAlarm(context, b.getPayeeID().hashCode());
        }
    }
    /*public static void cancelAllTransactionAlarm(Context context, ArrayList<Transaction> transactions) {
        for (Transaction b: transactions) {
            cancelAlarm(context, b.getTransactionID().hashCode());
        }
    }*/
   /* public static void cancelAllProfileAlarm(Context context, ArrayList<Profile> profiles) {
        for (Profile b: profiles) {


            cancelAlarm(context, b.getuID().hashCode());
        }
    }*/
    public static void cancelAllPaymentAlarm(Context context, ArrayList<Payment> payments) {
        for (Payment b: payments) {
            //cancelAlarm(context, b.getAmount().hashCode());
        }
    }
    public static void cancelAllCustomerAlarm(Context context, ArrayList<Customer> customers) {
        for (Customer b: customers) {
           // cancelAlarm(context, b.getuID().hashCode());
        }
    }

    public static void cancelAlarm(Context context, int id) {

        Intent mNotificationReceiverIntent = new Intent(context,
                NotificationBuilderReceiver.class);

        PendingIntent mNotificationReceiverPendingIntent = PendingIntent
                .getBroadcast(context.getApplicationContext(),
                        id,
                        mNotificationReceiverIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager mAlarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (mAlarmManager != null) {
            mAlarmManager.cancel(mNotificationReceiverPendingIntent);
        }
    }
}
