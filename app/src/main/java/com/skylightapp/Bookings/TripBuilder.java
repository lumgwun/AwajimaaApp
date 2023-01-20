package com.skylightapp.Bookings;

public class TripBuilder {
    private String trackingId;
    private  PushData pushData;
    private TripOptions tripOptions;
    public TripBuilder(String trackingId1) {
        this.trackingId = trackingId1;

    }
    public TripBuilder() {
        super();
    }

    public void withUserPushObject(PushData pushData1) {
        this.pushData = pushData1;

    }

    public PushData getPushData() {
        return pushData;
    }

    public void setPushData(PushData pushData) {
        this.pushData = pushData;
    }

    public TripOptions build() {
        return tripOptions;
    }

    public TripOptions getTripOptions() {
        return tripOptions;
    }

    public void setTripOptions(TripOptions tripOptions) {
        this.tripOptions = tripOptions;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }
}
