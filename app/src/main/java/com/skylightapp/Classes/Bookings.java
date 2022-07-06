package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;

import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class Bookings {

    @SuppressLint("ConstantLocale")
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd/MM/yyyy", Locale.getDefault());

    public static final String BOOKING_TABLE = "booking_table";

    public static final String BOOKING_STATUS = "booking_status";
    public static final String BOOKING_ID = "booking_id";
    public static final String BOOKING_TITTLE = "booking_tittle";
    public static final String BOOKING_CLIENT_NAME = "booking_name";
    public static final String BOOKING_DATE = "booking_date";
    public static final String BOOKING_LOCATION = "booking_location";
    public static final String BOOKING_PROF_ID = "booking_Prof_ID";
    public static final String BOOKING_CUS_ID = "booking_Cus_ID";
    public static final String BOOKING_OCCURENCE_NO = "booking_occuring_No";
    public static final Boolean ITISRECCURRING = false;


    public static final String CREATE_BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOKING_TABLE + " (" + BOOKING_PROF_ID + " INTEGER NOT NULL, " + BOOKING_CUS_ID + " INTEGER   , " + BOOKING_ID + " INTEGER    , " +
            BOOKING_TITTLE + " TEXT, " + BOOKING_CLIENT_NAME + " TEXT, " + BOOKING_DATE + " DATE, " + BOOKING_LOCATION + " TEXT, " +
            BOOKING_OCCURENCE_NO + " INTEGER, " + BOOKING_STATUS + " TEXT, " + ITISRECCURRING + " BOOLEAN, " +
            "PRIMARY KEY(" + BOOKING_ID  + "), " + "FOREIGN KEY(" + BOOKING_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";



    Context  context;
    public long bookingID;
    public long profileID;
    public long customerID;
    public String tittle;
    public String ClientName;
    public Date bookingDate;
    public String status;
    public String location;
    boolean isRecurring;
    int occurrenceNo;
    public int daysBtwn;
    public int daysRemaining;
    Profile profile;



    public Bookings() {

    }
    public Bookings(int bookingID, String tittle, String ClientName, Date bookingDate, String location, Boolean isRecurring, String status) {
        this.bookingID=bookingID;
        this.tittle=tittle;
        this.ClientName=ClientName;
        this.bookingDate=bookingDate;
        this.location=location;
        this.isRecurring= isRecurring;
        this.status=status;


    }

    public boolean isRecurring() {
        return isRecurring;
    }
    public Boolean getIsRecurring() {
        return false;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public long getProfileID() {
        return profileID;
    }
    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }
    public long getCustomerID() {
        return customerID;
    }
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }


    public String getTittle() {
        return tittle;
    }
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }


    public String getClientName() {
        return ClientName;
    }
    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getOccurrenceNo() {
        return occurrenceNo;
    }

    public long getBookingID() {
        long uid = this.bookingID;
        if (uid == 0) {
            uid = UUID.randomUUID().getMostSignificantBits();
            setBookingID(uid);
        }
        return bookingID;
    }

    public void setBookingID(long bookingID) {
        this.bookingID = bookingID;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    public Date getDateOfBooking() {
        return bookingDate;
    }


    public String getFormattedDaysRemainingString(String currentDate,String dateOfBooking) {
        int i = getDaysInBetween(currentDate,dateOfBooking);


        if (i == 0) {

            return (context.getString(R.string.date_today) + "!");
        } else if (i == 1) {
            return (context.getString(R.string.date_tomorrow) + "!");
        } else if (i == -1) {
            return context.getString(R.string.date_yesterday);
        } else if (i > 1 && i <= 6) {
            Date newDate = new Date();
            newDate.setTime(getDateOfBooking().getTime() - DAY_IN_MILLIS);
            return (String) DateFormat.format("EEEE", newDate);
        } else if (i == 7) {
            return (context.getString(R.string.date_week));
        } else if (i < 9) {
            return " " + String.valueOf(i) + " " + context.getString(R.string.date_days);
        } else if (i > 99) {
            return "  " + String.valueOf(i) + " " + context.getString(R.string.date_days);
        } else {
            return "" + i + " " + context.getString(R.string.date_days);
        }
    }
    public int getDaysInBetween( String currentDate,String dateOfBooking) {
        int daysBetween = (int) getDayCount(currentDate, dateOfBooking);

        if (daysBetween == 366) {
            daysBetween = 0;
        }
        return daysBetween;
    }
    public static long getDayCount(String start, String end) {

        long dayCount = 0;
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);
            if (dateStart != null) {
                if (dateEnd != null) {
                    dayCount = Math.round((dateEnd.getTime() - dateStart.getTime())
                            / (double) DAY_IN_MILLIS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayCount;
    }


    public void isRecurring(String string) {

    }
}
