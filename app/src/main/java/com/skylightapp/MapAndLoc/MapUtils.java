package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.AppController.TAG;
import static java.lang.Math.abs;
import static java.lang.Math.atan;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MapUtils {
    float getRotation(LatLng start, LatLng end) {
        double latDifference = abs(start.latitude - end.latitude);
        double lngDifference= abs(start.longitude - end.longitude);
        float rotation = -1F;
        if(start.latitude < end.latitude && start.longitude < end.longitude ){
            rotation = (float) Math.toDegrees(atan(lngDifference / latDifference));

        }
        if(start.latitude >= end.latitude && start.longitude < end.longitude ){
            rotation = (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 90);


        }
        if(start.latitude >= end.latitude && start.longitude >= end.longitude ){
            rotation = (float) (Math.toDegrees(atan(lngDifference / latDifference)) + 180);

        }
        if(start.latitude < end.latitude && start.longitude >= end.longitude ){
            rotation = (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270);

        }
        if(start.latitude < end.latitude && start.longitude < end.longitude ){
            rotation = (float) Math.toDegrees(atan(lngDifference / latDifference));

        }
        Log.d(TAG, "getRotation: $rotation");
        return rotation;
    }
}
