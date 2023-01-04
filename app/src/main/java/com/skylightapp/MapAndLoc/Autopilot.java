package com.skylightapp.MapAndLoc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.skylightapp.BuildConfig;
import com.skylightapp.R;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.messagecenter.MessageCenter;

public class Autopilot extends com.urbanairship.Autopilot implements UAirship.OnReadyCallback {

    private static final String NO_BACKUP_PREFERENCES = "com.urbanairship.sample.no_backup";

    private static final String FIRST_RUN_KEY = "first_run";
    private static final String PREF_NAME = "awajima";
    SharedPreferences userPreferences;

    @Override
    public void onAirshipReady(@NonNull UAirship airship) {
        SharedPreferences preferences = UAirship.getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        boolean isFirstRun = preferences.getBoolean(FIRST_RUN_KEY, true);
        if (isFirstRun) {
            preferences.edit().putBoolean(FIRST_RUN_KEY, false).apply();

            // Enable user notifications on first run
            airship.getPushManager().setUserNotificationsEnabled(true);
        }

        MessageCenter.shared().setOnShowMessageCenterListener(messageId -> {

            Uri uri;
            if (messageId != null) {
                uri = Uri.parse("vnd.urbanairship.sample://deepLink/inbox/message/" + messageId);
            } else {
                uri = Uri.parse("vnd.urbanairship.sample://deepLink/inbox");
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, uri)
                    .setPackage(UAirship.getPackageName())
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            UAirship.getApplicationContext().startActivity(intent);
            return true;
        });

        AirshipListener airshipListener = new AirshipListener();
        airship.getPushManager().addPushListener(airshipListener);
        airship.getPushManager().addPushTokenListener(airshipListener);
        airship.getPushManager().setNotificationListener(airshipListener);
        airship.getChannel().addChannelListener(airshipListener);
    }

    @Nullable
    @Override
    public AirshipConfigOptions createAirshipConfigOptions(@NonNull Context context) {


             /*AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                    .setInProduction(!BuildConfig.DEBUG)
                    .setDevelopmentAppKey("N9ChLEviSGOpOURNZcvaBA")
                    .setDevelopmentAppSecret("O7XqkUw2StiWfYlYK4XEZQ")
                    .setProductionAppKey("")
                    .setProductionAppSecret("")
                    .setNotificationAccentColor(ContextCompat.getColor(context, R.color.color_accent))
                    .setNotificationIcon(R.drawable.awajima_logo)
                    .build();
            return options;*/


        return super.createAirshipConfigOptions(context);
    }
}
