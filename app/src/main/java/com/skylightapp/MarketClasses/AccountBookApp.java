package com.skylightapp.MarketClasses;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDexApplication;

public class AccountBookApp extends MultiDexApplication {

    private static final String TAG = AccountBookApp.class.getSimpleName();

    private static Context context;
    private static String primaryCurrency;
    private static String secondaryCurrency;

    public static double USD_NGN = 6.9;
    public static double GBP_NGN = 0.8;

    @Override
    public void onCreate() {
        super.onCreate();

        AccountBookApp.context = getApplicationContext();
        loadData();
        queryRate();
    }

    public static Context getContext() {
        return AccountBookApp.context;
    }

    public static String getPrimaryCurrency(){
        return AccountBookApp.primaryCurrency;
    }

    public static String getSecondaryCurrency(){
        return AccountBookApp.secondaryCurrency;
    }

    public static void setPrimaryCurrency(String primary){
        AccountBookApp.primaryCurrency = primary;
    }

    public static void setSecondaryCurrency(String secondary){
        AccountBookApp.secondaryCurrency = secondary;
    }

    public void loadData(){
        SharedPreferences preferences = getSharedPreferences("CACHE_DATA", MODE_PRIVATE);
        primaryCurrency = preferences.getString("PRIMARY_CURRENCY", "");
        secondaryCurrency = preferences.getString("SECONDARY_CURRENCY", "");
    }

    public void queryRate(){
        RateUtil.setListener((USD_RMB1, HKD_RMB1) -> {
            AccountBookApp.USD_NGN = USD_NGN / 100;
            AccountBookApp.GBP_NGN = GBP_NGN / 100;
        });
        RateUtil.queryRate();
    }
}
