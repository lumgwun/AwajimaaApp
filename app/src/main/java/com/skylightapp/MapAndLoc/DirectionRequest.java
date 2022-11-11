package com.skylightapp.MapAndLoc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DirectionRequest {
    @SerializedName("origin") @Expose
    public Coordinate origin;

    @SerializedName("destination") @Expose public Coordinate destination;

    public DirectionRequest(Coordinate origin, Coordinate destination) {
        this.origin = origin;
        this.destination = destination;
    }
}
