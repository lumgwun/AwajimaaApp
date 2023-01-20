package com.skylightapp.Bookings;

public class PushData {
    private String payload;
    private String message;

    public PushData(String[] split) {

    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
