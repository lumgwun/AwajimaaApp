package com.skylightapp.MapAndLoc;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {
    @SerializedName("paths")
    @Expose
    public List<Path> paths;
    @SerializedName("optimizedWaypoints")
    @Expose
    public List<Integer> optimizedWaypoints;
    @SerializedName("bounds")
    @Expose
    public LatLngBounds bounds;
    @SerializedName("hasRestrictedRoad")
    @Expose
    public Integer hasRestrictedRoad;
    @SerializedName("dstInRestrictedArea")
    @Expose
    public Integer dstInRestrictedArea;
    @SerializedName("crossCountry")
    @Expose
    public Integer crossCountry;
    @SerializedName("crossMultiCountries")
    @Expose
    public Integer crossMultiCountries;
    @SerializedName("hasRoughRoad")
    @Expose
    public Integer hasRoughRoad;
    @SerializedName("dstInDiffTimeZone")
    @Expose
    public Integer dstInDiffTimeZone;
    @SerializedName("hasFerry")
    @Expose
    public Integer hasFerry;
    @SerializedName("hasTrafficLight")
    @Expose
    public Integer hasTrafficLight;
    @SerializedName("hasTolls")
    @Expose
    public Integer hasTolls;
    @SerializedName("trafficLightNum") @Expose public Integer trafficLightNum;
}
