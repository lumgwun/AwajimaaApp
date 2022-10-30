package com.skylightapp.MarketClasses;

import com.google.gson.annotations.SerializedName;

public class BaseGSARRequest {
    @SerializedName("user_id")
    private int userId;

    public BaseGSARRequest(int userId) {
        this.userId = userId;
    }
}
