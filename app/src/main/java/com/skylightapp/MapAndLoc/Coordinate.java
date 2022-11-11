package com.skylightapp.MapAndLoc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinate {
    @SerializedName("lat") public @Expose
    Double lat;
    @SerializedName("lng") public @Expose Double lng;

    public Coordinate(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
