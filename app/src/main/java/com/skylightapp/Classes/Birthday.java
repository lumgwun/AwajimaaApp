package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;


import com.skylightapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static java.sql.Date.valueOf;

@SuppressWarnings("deprecation")
//@Entity(tableName = Birthday.BIRTHDAY_TABLE)
public class Birthday implements Serializable, Parcelable {
    public static final String BIRTHDAY_STATUS = "bbirthday_status";
    public static final String BIRTHDAY_ID = "bbirth_id";
    public static final String BIRTHDAY_DAYS_REMAINING = "bdays_remaining";
    public static final String BIRTHDAY_DAYS_BTWN = "bdays_between";

    public static final String BIRTHDAY_TABLE = "birthday_table";
    private static final String JSON_NAME = "name";
    public static final String B_FIRSTNAME = "b_FirstName";
    public static final String B_SURNAME = "b_Surname";
    public static final String B_EMAIL = "b_Email";
    public static final String B_PHONE = "b_Phone";
    public static final String B_DOB = "b_DOb";
    public static final String B_PROF_ID = "b_Prof_ID";
    private static final String JSON_DATE = "bdate";
    private static final String JSON_YEAR = "byear";
    private static final String JSON_REMIND = "bremind";
    private static final String JSON_UID = "buid";
    private static final String JSON_SHOW_YEAR = "bshow_year";
    public static final String B_DB_ID = "b_DB_ID";


    public static final String CREATE_BIRTHDAY_TABLE = "CREATE TABLE IF NOT EXISTS " + BIRTHDAY_TABLE + " (" + B_PROF_ID + " INTEGER , " + BIRTHDAY_ID + " INTEGER   , " +
            B_FIRSTNAME + " TEXT, " + B_SURNAME + " TEXT, " + B_EMAIL + " TEXT, " + B_PHONE + " TEXT, " +
            B_DOB + " TEXT, " + BIRTHDAY_DAYS_REMAINING + " TEXT, " + BIRTHDAY_DAYS_BTWN + " TEXT, " + BIRTHDAY_STATUS + " TEXT, " + B_DB_ID + " INTEGER PRIMARY KEY, " + "FOREIGN KEY(" + B_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    @SuppressLint("ConstantLocale")
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());


    private static final int DAY_IN_MILLIS = 86400000;

    // References to data
    public String bFirstName;
    private String bSurName;
    public String bGender;
    //@PrimaryKey(autoGenerate = true)
    private int birthdayID=12;
    public int bProfileID;
    public String bPhoneNumber;
    private String bEmail;
    public String bAddress;
    public String bStatus;
    private String bDate;
    private Date date1;
    private boolean bRemind;
    private  int bYearOfBirth =0;
    private  boolean bShowYear =false;
    private String uID;
    private Profile bProfile;
    Context bContext;
    public String bDaysRemaining;
    public int bDaysBtwn;
    public Birthday() {
        super();


    }

    public Birthday(String bFirstName, Date dateOfBirthday, boolean notifyUserOfBirthday, boolean includeYear) {

        super();
        this.bFirstName = bFirstName.toUpperCase();
        this.bRemind = notifyUserOfBirthday;
        this.date1 = dateOfBirthday;
        this.bYearOfBirth = dateOfBirthday.getYear();
        this.bShowYear = includeYear;

    }
    public Birthday(int bProfileID, int birthdayID, String bFirstName, String bPhoneNumber, String bEmail, String bGender, String bAddress, String bDate, int daysBTWN, String bDaysRemaining, String bStatus) {

        super();
        this.bFirstName = bFirstName.toUpperCase();
        this.bProfileID = bProfileID;
        this.birthdayID = birthdayID;
        this.bPhoneNumber = bPhoneNumber;
        this.bEmail = bEmail;
        this.bGender = bGender;
        this.bAddress = bAddress;
        this.bDate = String.valueOf(bDate);
        this.bStatus = bStatus;
        this.bDaysBtwn =daysBTWN;
        this.bDaysRemaining = bDaysRemaining;
    }
    public Birthday(int bProfileID, int birthdayID, String names, String phoneNo, String dob, String bDaysRemaining, int bDaysBtwn) {
        super();
        this.bFirstName = names.toUpperCase();
        this.bProfileID = bProfileID;
        this.birthdayID = birthdayID;
        this.bPhoneNumber = phoneNo;
        this.bEmail = bEmail;
        this.bGender = bGender;
        this.bAddress = bAddress;
        this.bDate = String.valueOf(dob);
        this.bDaysBtwn = bDaysBtwn;
        this.bDaysRemaining = bDaysRemaining;
    }

   /* public Birthday(long parseLong, long parseLong1, String string, String string1, String string2, String string3, String string4, String string5, String string6, String string7) {
        this.name = name.toUpperCase();
        this.profileID = profileID;
        this.birthdayID = birthdayID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.date = String.valueOf(date);
        this.status = status;
    }*/

    public Birthday(JSONObject json) throws JSONException {

        // Find String name of person attached to this birthday.
        super();
        if (json.has(JSON_NAME)) {
            bFirstName = json.getString(JSON_NAME);
        }

        bRemind = !json.has(JSON_REMIND) || json.getBoolean(JSON_REMIND );

        if (json.has(JSON_DATE)) {
            bDate = String.valueOf(new Date());
            Objects.equals(bDate, json.getLong(JSON_DATE));
        }
        // year
        if (json.has(JSON_YEAR)) {
            bYearOfBirth = json.getInt(JSON_YEAR);
        } else {
            bYearOfBirth = AppConstants.DEFAULT_YEAR_OF_BIRTH;
        }



        bShowYear = json.has(JSON_SHOW_YEAR) && json.getBoolean(JSON_SHOW_YEAR);
    }

    public Birthday(int bProfileID, int profileID1, String phoneNo, String bEmail, String bGender, String bAddress, String dob, int daysBTWN, int bDaysRemaining, String bFirstName, String bStatus) {
        super();
        this.bFirstName = bFirstName.toUpperCase();
        this.bProfileID = bProfileID;
        this.birthdayID = birthdayID;
        this.bPhoneNumber = phoneNo;
        this.bEmail = bEmail;
        this.bGender = bGender;
        this.bAddress = bAddress;
        this.bDate = String.valueOf(dob);
        this.bDaysBtwn = bDaysBtwn;
        this.bDaysRemaining = String.valueOf(bDaysRemaining);
    }


    protected Birthday(Parcel in) {
        bFirstName = in.readString();
        bSurName = in.readString();
        bGender = in.readString();
        birthdayID = in.readInt();
        bProfileID = in.readInt();
        bPhoneNumber = in.readString();
        bEmail = in.readString();
        bAddress = in.readString();
        bStatus = in.readString();
        bDate = in.readString();
        bRemind = in.readByte() != 0;
        bYearOfBirth = in.readInt();
        bShowYear = in.readByte() != 0;
        bProfile = in.readParcelable(Profile.class.getClassLoader());
        bDaysRemaining = in.readString();
        bDaysBtwn = in.readInt();
    }

    public static final Creator<Birthday> CREATOR = new Creator<Birthday>() {
        @Override
        public Birthday createFromParcel(Parcel in) {
            return new Birthday(in);
        }

        @Override
        public Birthday[] newArray(int size) {
            return new Birthday[size];
        }
    };

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, this.getbFirstName());
        json.put(JSON_DATE, this.getbDate().getTime());
        json.put(JSON_YEAR, this.getbYearOfBirth());
        json.put(JSON_REMIND, this.getbRemind());
        json.put(JSON_UID, this.getBirthdayID());
        json.put(JSON_SHOW_YEAR, this.shouldIncludeYear());
        return json;
    }

    public boolean shouldIncludeYear() {
        return bShowYear;
    }

    public int getbYearOfBirth() {
        return bYearOfBirth;
    }

    public Date getbDate() {
        return valueOf(bDate);
    }

    public String getbFirstName() {
        return bFirstName;
    }

    public void setYearOfDate(int year) {

    }
    /*public int getDaysBtwn() {
        return daysBtwn;
    }
    public String getDaysRemaining() {
        return daysRemaining;
    }
    public void setDaysBtwn(int daysBtwn) {
        this.daysBtwn = daysBtwn;
    }
    public void setDaysRemaining(String daysRemaining) {
        this.daysRemaining = daysRemaining;
    }*/

    public int getBirthdayID() {
        return birthdayID;
    }

    public void setBirthdayID(int birthdayID) {
        this.birthdayID = birthdayID;
    }
    public int getbProfileID() {
        return bProfileID;
    }

    public void setbProfileID(int bProfileID) {
        this.bProfileID = bProfileID;
    }



    public boolean getbRemind() {
        return bRemind;
    }

    /*public String getReminderString() {
        if (remind) {
            return AppController.getInstance().getResources().getString(R.string.reminder_set);
        } else {
            return AppController.getInstance().getResources().getString(R.string.reminder_canceled);
        }
    }*/


    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getRemindAlarmDrawable() {
        if (bRemind) {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_mes);
        } else {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_mes);
        }
    }

    public boolean toggleReminder() {
        bRemind = !bRemind;
        return bRemind;
    }

    // Refactoring
    public String getBirthMonth() {
        return (String) bDate;

    }

    public String getBirthDay() {
        return "" + bProfile.getProfileDob() + Utils.stringToDate(bProfile.getProfileDob());
        //return Utils.stringToDate(profile.getDob());

    }

    public String getFormattedDaysRemainingString(String currentDate,String dateOfBirth) {
        int i = getDaysInBetween(currentDate,dateOfBirth);


        if (i == 0) {
            return (bContext.getString(R.string.date_today) + "!");
        } else if (i == 1) {
            return (bContext.getString(R.string.date_tomorrow) + "!");
        } else if (i == -1) {
            return bContext.getString(R.string.date_yesterday);
        } else if (i > 1 && i <= 6) {
            Date newDate = new Date();
            newDate.setTime(getbDate().getTime() - DAY_IN_MILLIS);
            return (String) DateFormat.format("EEEE", newDate);
        } else if (i == 7) {
            return (bContext.getString(R.string.date_week));
        } else if (i < 9) {
            return " " + String.valueOf(i) + " " + bContext.getString(R.string.date_days);
        } else if (i > 99) {
            return "  " + String.valueOf(i) + " " + bContext.getString(R.string.date_days);
        } else {
            return "" + i + " " + bContext.getString(R.string.date_days);
        }
    }
    public int getDaysInBetween( String currentDate,String dateOfBirth) {
        int daysBetween = (int) getDayCount(currentDate, dateOfBirth);

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

    public static int getYearOfNextBirthday(Date date) {

        int year = 2014;
        date.setYear(year);

        boolean nowAhead = dateInFuture(date);

        // While date instance is in the past, increase by a year and check again
        while (nowAhead) {

            year += 1;
            date.setYear(year);

            nowAhead = dateInFuture(date);
        }
        return year;
    }


    private static boolean dateInFuture(Date queryDate) {

        Calendar nowCal = Calendar.getInstance();

        Date now = new Date();
        now.setYear(nowCal.get(Calendar.YEAR));
        now.setMonth(nowCal.get(Calendar.MONTH));
        now.setDate(nowCal.get(Calendar.DATE));

        long millisNow = now.getTime();
        long millisBDAY = queryDate.getTime();

        return millisNow > millisBDAY + DAY_IN_MILLIS;
    }


   /* public static String getFormattedStringDay(Birthday b, Context c) {

        String dayFormatted = "";

        int daysFromNotiUntilDay = getDaysBeforeReminderPref(c);

        if (daysFromNotiUntilDay == 0) {
            dayFormatted += c.getResources().getString(R.string.date_today);
        } else if (daysFromNotiUntilDay == 1) {
            dayFormatted += c.getResources().getString(R.string.date_tomorrow);
        } else if (daysFromNotiUntilDay == 7) {
            dayFormatted += c.getResources().getString(R.string.date_week);
        } else if (daysFromNotiUntilDay == 14) {
            dayFormatted += c.getResources().getString(R.string.date_2_week);
        } else {
            Date newDate = new Date();
            newDate.setTime(b.getDate().getTime() - DAY_IN_MILLIS);
            return c.getResources().getString(R.string.date_this) + " " + DateFormat.format("EEEE", newDate);
        }
        dayFormatted += "!";

        return dayFormatted;
    }

    public static int getDaysBeforeReminderPref(Context c) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        return Integer.valueOf(sharedPref.getString(c.getString(R.string.pref_days_before_key), "1"));
    }*/


    public String getAge() {
        Date birthDate = getbDate();

        int year = this.getbYearOfBirth();

        Calendar calendarBirthday = Calendar.getInstance();
        calendarBirthday.set(year, birthDate.getMonth(), birthDate.getDate());

        Calendar nextBirthdate = Calendar.getInstance();
        nextBirthdate.set(getYearOfNextBirthday(birthDate), birthDate.getMonth(), birthDate.getDate());

        int age = nextBirthdate.get(Calendar.YEAR) - calendarBirthday.get(Calendar.YEAR);

        if (nextBirthdate.get(Calendar.MONTH) > calendarBirthday.get(Calendar.MONTH) ||
                (nextBirthdate.get(Calendar.MONTH) == calendarBirthday.get(Calendar.MONTH) &&
                        nextBirthdate.get(Calendar.DATE) > calendarBirthday.get(Calendar.DATE))) {
            age--;
        }

        if (age < 0) {
            return "N/A";
        } else {
            return String.valueOf(age);
        }
    }

    public static Birthday fromFB(FirebaseImportantDay fb) {
        Date date = new Date();
        date.setYear(fb.dateYear);
        date.setMonth(fb.dateMonth);
        date.setDate(fb.dateDay);
        Birthday birthday = new Birthday(fb.name, date, fb.remind, fb.showYear);
        return birthday;
    }


    public String getbSurName() {
        return bSurName;
    }

    public void setbSurName(String bSurName) {
        this.bSurName = bSurName;
    }
    public void setBFirstName(String bFirstName) {
        this.bFirstName = bFirstName;
    }


    public String getbStatus() {
        return bStatus;
    }
    public Profile getBProfile() {
        return bProfile;
    }
    public void setBProfile(Profile bProfile) {
        this.bProfile = bProfile;
    }



    public void setbStatus(String bStatus) {
        this.bStatus = bStatus;
    }

    public String getBEmail() {
        return bEmail;
    }

    public void setBEmail(String bEmail) {
        this.bEmail = bEmail;
    }
    public String getbPhoneNumber() {
        return bPhoneNumber;
    }

    public void setbPhoneNumber(String bPhoneNumber) {
        this.bPhoneNumber = bPhoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bFirstName);
        parcel.writeString(bSurName);
        parcel.writeString(bGender);
        parcel.writeInt(birthdayID);
        parcel.writeInt(bProfileID);
        parcel.writeString(bPhoneNumber);
        parcel.writeString(bEmail);
        parcel.writeString(bAddress);
        parcel.writeString(bStatus);
        parcel.writeString(bDate);
        parcel.writeByte((byte) (bRemind ? 1 : 0));
        parcel.writeInt(bYearOfBirth);
        parcel.writeByte((byte) (bShowYear ? 1 : 0));
        parcel.writeParcelable(bProfile, i);
        parcel.writeString(bDaysRemaining);
        parcel.writeInt(bDaysBtwn);
    }
}