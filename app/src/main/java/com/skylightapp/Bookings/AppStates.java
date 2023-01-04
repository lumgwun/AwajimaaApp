package com.skylightapp.Bookings;


import static java.lang.annotation.RetentionPolicy.SOURCE;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

@Retention(SOURCE)
@IntDef({
        AppStates.UNINITIALIZED,
        AppStates.INITIALIZED,
        AppStates.SELECTING_DROPOFF,
        AppStates.SELECTING_PICKUP,
        AppStates.CONFIRMING_TRIP,
        AppStates.JOURNEY_SHARING,
        AppStates.TRIP_CANCELED,
        AppStates.TRIP_COMPLETE
})
public @interface AppStates {
    int UNINITIALIZED = 0;
    int INITIALIZED = 1;
    int SELECTING_DROPOFF = 2;
    int SELECTING_PICKUP = 3;
    int CONFIRMING_TRIP = 4;
    int JOURNEY_SHARING = 5;
    int TRIP_CANCELED = 6;
    int TRIP_COMPLETE = 7;
}
