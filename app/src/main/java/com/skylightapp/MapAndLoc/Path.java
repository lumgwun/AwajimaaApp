package com.skylightapp.MapAndLoc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Path {
    @SerializedName("steps")
    public @Expose
    List<TaxiStep> steps;
    @SerializedName("distance")
    public @Expose
    Double distance;
    @SerializedName("distanceText") public @Expose String distanceText;
    @SerializedName("duration") public @Expose Double duration;
    @SerializedName("durationInTraffic") public @Expose Double durationInTraffic;
    @SerializedName("durationInTrafficText") public @Expose String durationInTrafficText;
    @SerializedName("startLocation") public @Expose Coordinate startLocation;
    @SerializedName("startAddress") public @Expose String startAddress;
    @SerializedName("endLocation") public @Expose Coordinate endLocation;
    @SerializedName("endAddress") public @Expose String endAddress;
}
