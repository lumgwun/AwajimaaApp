package com.skylightapp.MapAndLoc;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class CircularFence extends Fence {
    private  int cfEmergID;
    private String  cfEmergType;
    private String  cfEmergTown;
    private String  cfEmergSuburb;
    private String  cfEmergDate;
    private String  cfEmergStatus;
    LatLng centre;
    float radius;
    private EmergencyReport cfReport;
    private EmergReportNext cfReportNext;
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

    public int getCfEmergID() {
        return cfEmergID;
    }

    public void setCfEmergID(int cfEmergID) {
        this.cfEmergID = cfEmergID;
    }

    public String getCfEmergType() {
        return cfEmergType;
    }

    public void setCfEmergType(String cfEmergType) {
        this.cfEmergType = cfEmergType;
    }

    public String getCfEmergTown() {
        return cfEmergTown;
    }

    public void setCfEmergTown(String cfEmergTown) {
        this.cfEmergTown = cfEmergTown;
    }

    public String getCfEmergSuburb() {
        return cfEmergSuburb;
    }

    public void setCfEmergSuburb(String cfEmergSuburb) {
        this.cfEmergSuburb = cfEmergSuburb;
    }

    public String getCfEmergDate() {
        return cfEmergDate;
    }

    public void setCfEmergDate(String cfEmergDate) {
        this.cfEmergDate = cfEmergDate;
    }

    public String getCfEmergStatus() {
        return cfEmergStatus;
    }

    public void setCfEmergStatus(String cfEmergStatus) {
        this.cfEmergStatus = cfEmergStatus;
    }

    public EmergencyReport getCfReport() {
        return cfReport;
    }

    public void setCfReport(EmergencyReport cfReport) {
        this.cfReport = cfReport;
    }

    public EmergReportNext getCfReportNext() {
        return cfReportNext;
    }

    public void setCfReportNext(EmergReportNext cfReportNext) {
        this.cfReportNext = cfReportNext;
    }
}
