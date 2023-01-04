package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.ProfileMarkerType.DROPOFF_POINT;
import static com.skylightapp.Bookings.ProfileMarkerType.INTERMEDIATE_DESTINATION_POINT;
import static com.skylightapp.Bookings.ProfileMarkerType.PICKUP_POINT;
import static com.skylightapp.Bookings.ProfileMarkerType.PREVIOUS_TRIP_PENDING_POINT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import com.skylightapp.R;

public final class ProfMarkerUtils {
    private static final float ANCHOR_VALUE = 0.5f;


    public static MarkerOptions getConsumerMarkerOptions(
            Context context, @ProfileMarkerType int consumerMarkerType) {
        switch (consumerMarkerType) {
            case PICKUP_POINT:
                return new MarkerOptions()
                        .anchor(ANCHOR_VALUE, ANCHOR_VALUE)
                        .icon(toBitmapDescriptor(context, R.drawable.pickup));
            case DROPOFF_POINT:
                return new MarkerOptions()
                        .anchor(ANCHOR_VALUE, ANCHOR_VALUE)
                        .icon(toBitmapDescriptor(context, R.drawable.dropoff));
            case INTERMEDIATE_DESTINATION_POINT:
                return new MarkerOptions()
                        .anchor(ANCHOR_VALUE, ANCHOR_VALUE)
                        .icon(toBitmapDescriptor(context, R.drawable.waypoint));
            case PREVIOUS_TRIP_PENDING_POINT:
                return new MarkerOptions()
                        .anchor(ANCHOR_VALUE, ANCHOR_VALUE)
                        .icon(toBitmapDescriptor(context, R.drawable.dest));
            default:
                throw new IllegalArgumentException(
                        String.format("Marker type: %d not supported", consumerMarkerType));
        }
    }

    public static BitmapDescriptor toBitmapDescriptor(Context context, @DrawableRes int resourceId) {
        Drawable vectorDrawable = AppCompatResources.getDrawable(context, resourceId);
        Bitmap bitmap =
                Bitmap.createBitmap(
                        vectorDrawable.getIntrinsicWidth(),
                        vectorDrawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static void setCustomMarkers(ConsumerController mConsumerController, Context context) {
        ConsumerMapStyle mapStyle = mConsumerController.getConsumerMapStyle();

        MarkerOptions pickupMarkerStyleOptions =
                new MarkerOptions().icon(toBitmapDescriptor(context, R.drawable.pickup));
        mapStyle.setMarkerStyleOptions(MarkerType.TRIP_PICKUP_POINT, pickupMarkerStyleOptions);

        MarkerOptions dropoffMarkerStyleOptions =
                new MarkerOptions()
                        .icon(toBitmapDescriptor(context, R.drawable.dest));
        mapStyle.setMarkerStyleOptions(MarkerType.TRIP_DROPOFF_POINT, dropoffMarkerStyleOptions);

        MarkerOptions intermediateMarkerStyleOptions =
                new MarkerOptions().icon(toBitmapDescriptor(context, R.drawable.waypoint));
        mapStyle.setMarkerStyleOptions(
                MarkerType.TRIP_INTERMEDIATE_DESTINATION, intermediateMarkerStyleOptions);
    }
}
