package com.skylightapp.Bookings;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        ProfileMarkerType.PICKUP_POINT,
        ProfileMarkerType.DROPOFF_POINT,
        ProfileMarkerType.INTERMEDIATE_DESTINATION_POINT,
        ProfileMarkerType.PREVIOUS_TRIP_PENDING_POINT,
})
public @interface ProfileMarkerType {
    int PICKUP_POINT = 1;

    int DROPOFF_POINT = 2;

    int INTERMEDIATE_DESTINATION_POINT = 3;

    int PREVIOUS_TRIP_PENDING_POINT = 4;
}
