package com.skylightapp.MapAndLoc;

import static com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
import static com.google.android.gms.location.Geofence.NEVER_EXPIRE;

import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

public abstract class Fence {
    public static Fence
    buildCircularFence (String name, LatLng centre, float radius) {
        return new CircularFence (name, centre, radius); }


    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    protected Fence (@NonNull String fname) { name = fname; }

    public Geofence createGeofence ()
    {
        return createBuilder ().build ();
    }


    protected Geofence.Builder createBuilder ()
    {
        return new Geofence.Builder ()
                .setRequestId (name)
                .setExpirationDuration (NEVER_EXPIRE)
                .setTransitionTypes ( Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT | GEOFENCE_TRANSITION_DWELL )
                .setLoiteringDelay (5 * 60 * 1000);  // DWELL fires after 5 minutes
    }

    abstract void showOnMap (FenceMap map);
}
