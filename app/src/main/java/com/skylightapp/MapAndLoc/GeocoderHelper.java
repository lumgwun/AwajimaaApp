package com.skylightapp.MapAndLoc;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.R;

import javax.inject.Inject;

public class GeocoderHelper {
    private Context context;
    private Geocoder geocoder;

    @Inject
    public GeocoderHelper(Geocoder geocoder,  Context context) {
        this.geocoder = geocoder;
        this.context = context;
    }

    public String getNameFromLocation(LatLng latLng) {
        String result = context.getString(R.string.unknown_location);
        try {
            Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            if (address != null) {
                result = address.getSubLocality() + ", " + address.getThoroughfare() + ", " + address.getSubThoroughfare();
                return result.replace(", null", "");
            } else {
                return result;
            }
        } catch (Exception e) {
            return result;
        }
    }
}
