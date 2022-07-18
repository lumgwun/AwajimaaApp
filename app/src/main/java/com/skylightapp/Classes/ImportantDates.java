
package com.skylightapp.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

import com.skylightapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;


@SuppressWarnings("deprecation")
public class ImportantDates implements Serializable, Parcelable {
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_REPEAT = "repeat";
    public static final String KEY_REPEAT_NO = "repeat_no";
    public static final String KEY_REPEAT_TYPE = "repeat_type";
    public static final String KEY_ACTIVE = "active";

    public static final String IMPORTANT_DATE_TABLE = "dates_table";
    public static final String IMPORTANT_DATE_ID = "id";
    public static final String IMPORTANT_DATE_TITLE = "title";
    public static final String IMPORTANT_DATE = "date";


    private static final String JSON_NAME = "name";
    private static final String JSON_DATE = "date";
    private static final String JSON_YEAR = "year";
    private static final String JSON_REMIND = "remind";
    private static final String JSON_UID = "uid";
    private static final String JSON_SHOW_YEAR = "show_year";
    public static final String TABLE_REMINDERS = "ReminderTable";

    public static final String CREATE_REMINDER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDERS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PACKAGE_ID + " INTEGER,"+ KEY_TITLE + " TEXT," + KEY_DATE + " TEXT," + KEY_TIME + " INTEGER,"
            + KEY_REPEAT + " BOOLEAN," + KEY_REPEAT_NO + " INTEGER," + KEY_REPEAT_TYPE + " TEXT," + KEY_ACTIVE + " BOOLEAN, " + "FOREIGN KEY(" + PACKAGE_ID + ") REFERENCES " + PACKAGE_TABLE + "(" + PACKAGE_ID  + "))";


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault());

    private static final int DAY_IN_MILLIS = 86400000;

    // References to data
    public String name;
    private Date date;
    private boolean remind;
    private int yearOfBirth;
    private boolean showYear;
    private String uID;
    private int mID;
    private String mTitle;
    private String mDate;
    private String mTime;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;


    /**
     * Constructor for creating new birthday.
     *
     */
    public ImportantDates(String name, Date dateImportant, boolean notifyUserOfDayImportant, boolean includeYear) {
        this.name = name.toUpperCase();
        this.remind = notifyUserOfDayImportant;
        this.date = dateImportant;
        this.yearOfBirth = dateImportant.getYear();
        this.showYear = includeYear;
        this.uID = UUID.randomUUID().toString();
    }
    public ImportantDates(int ID, String Title, String Date, String Time, String Repeat, String RepeatNo, String RepeatType, String Active){
        mID = ID;
        mTitle = Title;
        mDate = Date;
        mTime = Time;
        mRepeat = Repeat;
        mRepeatNo = RepeatNo;
        mRepeatType = RepeatType;
        mActive = Active;
    }

    public ImportantDates(){
        super();

    }
    public ImportantDates(String Title, String Date, String Time, String Repeat, String RepeatNo, String RepeatType, String Active){
        mTitle = Title;
        mDate = Date;
        mTime = Time;
        mRepeat = Repeat;
        mRepeatNo = RepeatNo;
        mRepeatType = RepeatType;
        mActive = Active;
    }


    public ImportantDates(JSONObject json) throws JSONException {

        // Find String name of person attached to this birthday.
        if (json.has(JSON_NAME)) {
            name = json.getString(JSON_NAME);
        }

        remind = !json.has(JSON_REMIND) || json.getBoolean(JSON_REMIND );

        if (json.has(JSON_DATE)) {
            date = new Date();
            date.setTime(json.getLong(JSON_DATE));
        }
        // year
        if (json.has(JSON_YEAR)) {
            yearOfBirth = json.getInt(JSON_YEAR);
        } else {
            yearOfBirth = AppConstants.DEFAULT_YEAR_OF_BIRTH;
        }

        // UID
        if (json.has(JSON_UID)) {
            uID = json.getString(JSON_UID);
        }

        // Should use age?
        showYear = json.has(JSON_SHOW_YEAR) && json.getBoolean(JSON_SHOW_YEAR);
    }

    public ImportantDates(String names, String dateOfBirth, String s, String s1, String s2, String s3) {
        mTitle = names;
        mDate = dateOfBirth;
        mTime = s;
        mRepeat = s1;
        mRepeatNo = s2;
        mRepeatType = s3;
    }


    protected ImportantDates(Parcel in) {
        name = in.readString();
        remind = in.readByte() != 0;
        yearOfBirth = in.readInt();
        showYear = in.readByte() != 0;
        uID = in.readString();
        mID = in.readInt();
        mTitle = in.readString();
        mDate = in.readString();
        mTime = in.readString();
        mRepeat = in.readString();
        mRepeatNo = in.readString();
        mRepeatType = in.readString();
        mActive = in.readString();
    }

    public static final Creator<ImportantDates> CREATOR = new Creator<ImportantDates>() {
        @Override
        public ImportantDates createFromParcel(Parcel in) {
            return new ImportantDates(in);
        }

        @Override
        public ImportantDates[] newArray(int size) {
            return new ImportantDates[size];
        }
    };

    /**
     * Convert current Birthday to JSON format and return.
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, this.getName());
        json.put(JSON_DATE, this.getDate().getTime());
        json.put(JSON_YEAR, this.getYear());
        json.put(JSON_REMIND, this.getRemind());
        json.put(JSON_UID, this.getUID());
        json.put(JSON_SHOW_YEAR, this.shouldIncludeYear());
        return json;
    }
    public int getID() {
        return Integer.parseInt(uID);
    }

    public void setID(int uID) {
        uID = uID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date1) {
        date = date1;
    }
    public Date getDate1() {
        return date;
    }

    public void setDate(String date) {
        mDate = date;
    }


    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getRepeatType() {
        return mRepeatType;
    }

    public void setRepeatType(String repeatType) {
        mRepeatType = repeatType;
    }

    public String getRepeatNo() {
        return mRepeatNo;
    }

    public void setRepeatNo(String repeatNo) {
        mRepeatNo = repeatNo;
    }

    public String getRepeat() {
        return mRepeat;
    }

    public void setRepeat(String repeat) {
        mRepeat = repeat;
    }

    public String getActive() {
        return mActive;
    }

    public void setActive(String active) {
        mActive = active;
    }

    /**
     * Getters & setters for variables
     */
    public boolean shouldIncludeYear() {
        return showYear;
    }

    public int getYear() {
        return yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setYearOfDate(int year) {
        this.date.setYear(year);
    }

    public String getUID() {
        String uid = this.uID;
        if (uid == null ||uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
            setUID(uid);
        }
        return uID;
    }

    public void setUID(String uID) {
        this.uID = uID;
    }

    public boolean getRemind() {
        return remind;
    }

    public String getReminderString() {
        if (remind) {
            return AppController.getInstance().getResources().getString(R.string.reminder_set);
        } else {
            return AppController.getInstance().getResources().getString(R.string.reminder_canceled);
        }
    }

    public Drawable getRemindAlarmDrawable() {
        if (remind) {
            return AppController.getInstance().getResources().getDrawable(R.drawable.notification);
        } else {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_notifications_black_24dp);
        }
    }

    public boolean toggleReminder() {
        remind = !remind;
        return remind;
    }

    // Refactoring
    public String getDateMonth() {
        return (String) DateFormat.format("MMM", date);
    }

    public String getDateDay() {
        return "" + date.getDate() + Utils.getDateSuffix(date.getDate());
    }

    public String getFormattedDaysRemainingString() {
        int i = getDaysBetween();

        Context context = AppController.getInstance();

        if (i == 0) {
            return (context.getString(R.string.date_today) + "!").toUpperCase();
        } else if (i == 1) {
            return (context.getString(R.string.date_tomorrow) + "!");
        } else if (i == -1) {
            return context.getString(R.string.date_yesterday);
        } else if (i > 1 && i <= 6) {
            Date newDate = new Date();
            newDate.setTime(getDate().getTime() - DAY_IN_MILLIS);
            return (String) DateFormat.format("EEEE", newDate);
        } else if (i == 7) {
            return context.getString(R.string.date_week).toUpperCase();
        } else if (i < 9) {
            return " " + String.valueOf(i) + " " + context.getString(R.string.date_days);
        } else if (i > 99) {
            return "  " + String.valueOf(i) + " " + context.getString(R.string.date_days);
        } else {
            return "" + i + " " + context.getString(R.string.date_days);
        }
    }

    public int getDaysBetween() {

        Date dateBirthday = getDate();
        String importantDay = String.valueOf(dateBirthday.getDate()) + "."
                + String.valueOf(dateBirthday.getMonth() + 1) + "."
                + String.valueOf(getYearOfNextImportantDay(dateBirthday));

        Date dateNow = new Date();
        String today = String.valueOf((dateNow.getDate()) + "."
                + String.valueOf(dateNow.getMonth() + 1) + "."
                + String.valueOf(dateNow.getYear() + 1900));

        // use below method to calculate days until next birthday occurance
        int daysBetween = (int) getDayCount(today, importantDay);

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
            dayCount = Math.round((dateEnd.getTime() - dateStart.getTime())
                    / (double) DAY_IN_MILLIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayCount;
    }


    public static int getYearOfNextImportantDay(Date date) {

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

        // Set date to desired time
        Date now = new Date();
        now.setYear(nowCal.get(Calendar.YEAR));
        now.setMonth(nowCal.get(Calendar.MONTH));
        now.setDate(nowCal.get(Calendar.DATE));

        // Get dates in form of milliseconds
        long millisNow = now.getTime();
        long millisBDAY = queryDate.getTime();

        // use this to ensure a birthday
        return millisNow > millisBDAY + DAY_IN_MILLIS;
    }


    public static String getFormattedStringDay(ImportantDates b, Context c) {

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
            return c.getResources().getString(R.string.date) + " " + DateFormat.format("EEEE", newDate);
        }
        dayFormatted += "!";

        return dayFormatted;
    }

    public static int getDaysBeforeReminderPref(Context c) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        return Integer.valueOf(sharedPref.getString(c.getString(R.string.pref_days_before_key), "1"));
    }


    public String getAge() {
        Date birthDate = getDate();

        int year = this.getYear();

        Calendar calendarImportantDay = Calendar.getInstance();
        calendarImportantDay.set(year, birthDate.getMonth(), birthDate.getDate());

        Calendar nextImportantdate = Calendar.getInstance();
        nextImportantdate.set(getYearOfNextImportantDay(birthDate), birthDate.getMonth(), birthDate.getDate());

        int age = nextImportantdate.get(Calendar.YEAR) - calendarImportantDay.get(Calendar.YEAR);

        if (nextImportantdate.get(Calendar.MONTH) > calendarImportantDay.get(Calendar.MONTH) ||
                (nextImportantdate.get(Calendar.MONTH) == calendarImportantDay.get(Calendar.MONTH) &&
                        nextImportantdate.get(Calendar.DATE) > calendarImportantDay.get(Calendar.DATE))) {
            age--;
        }

        if (age < 0) {
            return "N/A";
        } else {
            return String.valueOf(age);
        }
    }

    public static ImportantDates fromFB(FirebaseImportantDay fb) {
        Date date = new Date();
        date.setYear(fb.dateYear);
        date.setMonth(fb.dateMonth);
        date.setDate(fb.dateDay);
        ImportantDates importantDates = new ImportantDates(fb.name, date, fb.remind, fb.showYear);
        importantDates.setUID(fb.uID);
        return importantDates;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (remind ? 1 : 0));
        parcel.writeInt(yearOfBirth);
        parcel.writeByte((byte) (showYear ? 1 : 0));
        parcel.writeString(uID);
        parcel.writeInt(mID);
        parcel.writeString(mTitle);
        parcel.writeString(mDate);
        parcel.writeString(mTime);
        parcel.writeString(mRepeat);
        parcel.writeString(mRepeatNo);
        parcel.writeString(mRepeatType);
        parcel.writeString(mActive);
    }
}