package com.skylightapp.MarketClasses;

import com.google.gson.annotations.SerializedName;

public class BaseUserRequest {
    @SerializedName("user_id")
    private Long userId;

    public BaseUserRequest(Long userId) {
        this.userId = userId;
    }
}

