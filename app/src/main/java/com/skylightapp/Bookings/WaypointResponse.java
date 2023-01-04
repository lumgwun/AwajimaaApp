package com.skylightapp.Bookings;

import androidx.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

public class WaypointResponse {
    @SerializedName("location")
    private Location location;

    @SerializedName("waypointType")
    private String waypointType;

    public Location getLocation() {
        return location;
    }

    @VisibleForTesting
    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Location {
        @SerializedName(value = "point")
        private Point point;

        public Point getPoint() {
            return point;
        }

        @VisibleForTesting
        public void setPoint(Point point) {
            this.point = point;
        }
    }

    public static class Point {
        @SerializedName(value = "latitude")
        private double latitude;

        @SerializedName(value = "longitude")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        @VisibleForTesting
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @VisibleForTesting
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
