package com.skylightapp.MapAndLoc;

import com.directions.route.Route;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionResponse {
    @SerializedName("returnCode") @Expose String returnCode;
    @SerializedName("returnDesc")
    @Expose
    String returnDesc;
    @SerializedName("routes")
    public @Expose
    List<Route> routes;
}
