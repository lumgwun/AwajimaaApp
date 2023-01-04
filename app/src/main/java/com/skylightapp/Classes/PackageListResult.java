package com.skylightapp.Classes;

import com.skylightapp.MarketClasses.MarketBizPackage;

import java.util.ArrayList;
import java.util.List;

public class PackageListResult extends MarketBizPackage {
    boolean isMoreDataAvailable;
    List<MarketBizPackage> marketBizPackageList = new ArrayList<>();
    long lastItemCreatedDate;

    public PackageListResult() {
        super();
    }

    public boolean isMoreDataAvailable() {
        return isMoreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public List<MarketBizPackage> getSkyLightPackageList() {
        return marketBizPackageList;
    }

    public void setSkyLightPackageList(List<MarketBizPackage> marketBizPackageList) {
        this.marketBizPackageList = marketBizPackageList;
    }

    public long getLastItemCreatedDate() {
        return lastItemCreatedDate;
    }

    public void setLastItemCreatedDate(long lastItemCreatedDate) {
        this.lastItemCreatedDate = lastItemCreatedDate;
    }

}
