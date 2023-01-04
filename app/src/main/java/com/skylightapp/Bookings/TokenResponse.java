package com.skylightapp.Bookings;

import com.google.gson.annotations.SerializedName;


public final class TokenResponse {
    @SerializedName("jwt")
    private String token;

    @SerializedName("creationTimestamp")
    private long creationTimestampMs;

    @SerializedName("expirationTimestamp")
    private long expirationTimestampMs;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*public Instant getCreationTimestamp() {
        return new Instant(creationTimestampMs);
    }

    public Instant getExpirationTimestamp() {
        return new Instant(expirationTimestampMs);
    }*/
}
