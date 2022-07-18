package com.skylightapp.Classes;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PackageListResult extends SkyLightPackage {
    boolean isMoreDataAvailable;
    List<SkyLightPackage> skyLightPackageList = new ArrayList<>();
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

    public List<SkyLightPackage> getSkyLightPackageList() {
        return skyLightPackageList;
    }

    public void setSkyLightPackageList(List<SkyLightPackage> skyLightPackageList) {
        this.skyLightPackageList = skyLightPackageList;
    }

    public long getLastItemCreatedDate() {
        return lastItemCreatedDate;
    }

    public void setLastItemCreatedDate(long lastItemCreatedDate) {
        this.lastItemCreatedDate = lastItemCreatedDate;
    }

}
