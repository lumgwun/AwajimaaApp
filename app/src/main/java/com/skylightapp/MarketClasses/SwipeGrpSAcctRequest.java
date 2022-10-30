package com.skylightapp.MarketClasses;

import com.google.gson.annotations.SerializedName;

public class SwipeGrpSAcctRequest extends BaseGSARRequest {
    @SerializedName("swiped_id")
    private int swipedId;
    @SerializedName("wannaSave")
    private int wannaSave;
    private String name;
    @SerializedName("match_value")
    private double matchValue;

    public SwipeGrpSAcctRequest(int userId, int swipedId, int wannaSave, String name, double matchValue) {
        super(userId);
        this.swipedId = swipedId;
        this.wannaSave = wannaSave;
        this.name = name;
        this.matchValue = matchValue;
    }
}
