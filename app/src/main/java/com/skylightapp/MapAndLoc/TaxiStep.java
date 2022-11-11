package com.skylightapp.MapAndLoc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxiStep {
    @SerializedName("distance") public @Expose Double distance;
    @SerializedName("distanceText") public @Expose String distanceText;
    @SerializedName("duration") public @Expose Double duration;
    @SerializedName("durationText") public @Expose String durationText;
    @SerializedName("startLocation") public @Expose Coordinate startLocation;
    @SerializedName("endLocation") public @Expose Coordinate endLocation;
    @SerializedName("action")
    public @Expose
    String action;
    @SerializedName("polyline")
    public @Expose
    List<Coordinate> polyline;
    @SerializedName("roadName")
    public @Expose
    String roadName;
    @SerializedName("orientation") public @Expose Integer orientation;
    @SerializedName("instruction") public @Expose
    String instruction;
}
