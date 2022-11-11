package com.skylightapp.MapAndLoc;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class FenceMap {
    private GoogleMap map;

    public FenceMap (GoogleMap gmap) { map = gmap; }

    FenceMap showCircularFence (CircularFence fence) {
        showCircularMarker ( fence.getName (), fence.getCentre (),
                fence.getRadius (), Color.GREEN );
        return this;
    }


    public FenceMap showFences (List<Fence> fences)
    {
        for (Fence fence : fences)
        { fence.showOnMap (this); }
        return this;
    }


    public FenceMap showLocation (String name, Location location){

        LatLng position =
                new LatLng (location.getLatitude (), location.getLongitude ());
        showCircularMarker (name, position, location.getAccuracy (), Color.BLACK);
        return this;
    }

    private void
    showCircularMarker (String name, LatLng position, float radius, int colour)
    {

        MarkerOptions mOptions =
                new MarkerOptions ().position (position).title (name);
        map.addMarker (mOptions);

        if (radius > 1f)
        {
            CircleOptions cOptions = new CircleOptions ()
                    .center (position)
                    .radius (radius)
                    .strokeColor (colour);
            map.addCircle (cOptions);
        }
    }
}
