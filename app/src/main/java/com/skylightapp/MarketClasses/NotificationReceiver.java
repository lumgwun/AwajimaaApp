package com.skylightapp.MarketClasses;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.skylightapp.Bookings.MapApp;

import io.teliver.sdk.core.Teliver;
import io.teliver.sdk.core.TrackingListener;
import io.teliver.sdk.models.MarkerOption;
import io.teliver.sdk.models.TLocation;
import io.teliver.sdk.models.TrackingBuilder;


public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        TrackingBuilder builder = new TrackingBuilder(new MarkerOption(intent.getStringExtra(MapApp.TRACKING_ID)))
                .withListener(new TrackingListener() {
                    @Override
                    public void onTrackingStarted(String trackingId) {
                        Log.d("TELIVER::", "onTrackingStarted: " + trackingId);
                    }

                    @Override
                    public void onLocationUpdate(String trackingId, TLocation location) {
                        Log.d("TELIVER::", "onLocationUpdate: " + location.getLatitude() + location.getLongitude());
                    }

                    @Override
                    public void onTrackingEnded(String trackingId) {
                        Log.d("TELIVER::", "onTrackingEnded: " + trackingId);
                    }

                    @Override
                    public void onTrackingError(String reason) {
                        Log.d("TELIVER::", "onTrackingError: " + reason);
                    }
                });
        Teliver.startTracking(builder.build());
    }
}