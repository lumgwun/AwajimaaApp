package com.skylightapp.MapAndLoc;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class CircularFence extends Fence {


    LatLng centre;


    float radius;

    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }

    public LatLng getCentre() {
        return centre;
    }
    public void setCentre(LatLng centre) {
        this.centre = centre;
    }


    CircularFence (String name, LatLng c, float r)
    {
        super (name);
        centre = c;
        radius = r;
    }

    @Override
    protected Geofence.Builder createBuilder ()
    {
        return super.createBuilder ()
                .setCircularRegion (centre.latitude, centre.longitude, radius);
    }

    @Override
    void showOnMap (FenceMap map)
    { map.showCircularFence (this); }
}
