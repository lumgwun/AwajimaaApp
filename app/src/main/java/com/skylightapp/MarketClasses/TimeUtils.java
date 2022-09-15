package com.skylightapp.MarketClasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private TimeUtils() {
    }


    public static String now(){
        return new SimpleDateFormat(DATE_FORMAT, Locale.CHINA).format(new Date());
    }

    public static String today(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
    }

    public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static void main(String[] args){
        String a = now();
        System.out.print(a + "\n");
        System.out.print(a.substring(0, 10) + "\n");
        System.out.print(getCurrentMonth() + "\n");
        System.out.print(getCurrentYear() + "\n");
    }

    public static String getTime(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static long getDateAsHeaderId(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return Long.parseLong(dateFormat.format(new Date(milliseconds)));
    }

}
