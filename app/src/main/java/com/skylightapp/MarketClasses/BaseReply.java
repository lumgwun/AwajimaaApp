package com.skylightapp.MarketClasses;

public class BaseReply {
    private String status;

    public String getStatus() {
        return status;
    }

    public boolean isStatusOkay() {
        return status.equals("ok");
    }
}
