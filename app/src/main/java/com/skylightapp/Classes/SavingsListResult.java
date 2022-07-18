package com.skylightapp.Classes;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SavingsListResult extends CustomerDailyReport {
    boolean isMoreDataAvailable;
    List<CustomerDailyReport> dailyReports = new ArrayList<>();
    long lastItemCreatedDate;

    public boolean isMoreDataAvailable() {
        return isMoreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public List<CustomerDailyReport> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(List<CustomerDailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }

    public long getLastItemCreatedDate() {
        return lastItemCreatedDate;
    }

    public void setLastItemCreatedDate(long lastItemCreatedDate) {
        this.lastItemCreatedDate = lastItemCreatedDate;
    }

    @Override
    public void setNotificationUris(@NonNull ContentResolver cr, @NonNull List<Uri> uris) {

    }
}
