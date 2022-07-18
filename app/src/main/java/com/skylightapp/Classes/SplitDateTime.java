package com.skylightapp.Classes;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplitDateTime {
    private Date origDateTime; // Example "yyyy-MM-dd HH:mm"
    private DateFormat inputFormat;

    public SplitDateTime(Date origDatetime, DateFormat origDateFormat) {
        this.origDateTime = origDatetime;
        this.inputFormat = origDateFormat;
    }

    public String getHour() {
        @SuppressLint("SimpleDateFormat") DateFormat finalFormat = new SimpleDateFormat("HH");
        return finalFormat.format(origDateTime);
    }

    public String getMinute() {
        @SuppressLint("SimpleDateFormat") DateFormat finalFormat = new SimpleDateFormat("mm");
        return finalFormat.format(origDateTime);
    }

    public String getYear() {
        @SuppressLint("SimpleDateFormat") DateFormat finalFormat = new SimpleDateFormat("yyyy");
        return finalFormat.format(origDateTime);
    }

    public String getMonth() {
        @SuppressLint("SimpleDateFormat") DateFormat finalFormat = new SimpleDateFormat("MM");
        return finalFormat.format(origDateTime);
    }

    public String getDay() {
        @SuppressLint("SimpleDateFormat") DateFormat finalFormat = new SimpleDateFormat("dd");
        return finalFormat.format(origDateTime);
    }
}
