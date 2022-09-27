package com.skylightapp.Classes;

public class AppConstants {
    public static final String FIRST_TIME = "FirstTime";
    public static final String WHATS_NEW_LAST_SHOWN = "whats_new_last_shown";
    // Audio prefernces
    public static final String VIEW_PAGER_ANIME = "PagerAnime";
    // Help Preference
    public static final String SUBMIT_LOGS = "CrashLogs";
    public static final String DD_MM_YY = "DD/MONTH/YEAR";
    public static int CURRENT_CATEGORY = 0;

    public static final String FILENAME = "birthdays.json";
    public static final String AWAJIMA_PRIVACY_POLICIES = "https://docs.google.com/document/d/1G8L2qti36yVtxGFJOQR0NV1RA0eQiK2DZFpZQj_Metw/edit?usp=sharing";

    //public static final String TWILLO_ACCOUNT_SID = "AC5e05dc0a793a29dc1da2eabdebd6c28d";
    //public static final String TWILLO_AUTH_TOKEN = "@39410e8b813c131da386f3d7bb7f94f7";
    //public static final String TWILLO_PHONE_NO = "+15703251701";
    public final static String ACCOUNT_TYPE = "account_type";
    public final static byte NEW_DAILY_ACCOUNT = 0;
    public final static byte NEW_JOURNEY_ACCOUNT = 1;

    public final static String JOURNEY_ID = "journey_id";
    public final static byte NONE_JOURNEY = -99;

    public final static String CACHE_DATA = "user_preference";
    public final static String PRIMARY_CURRENCY = "primary_currency";
    public final static String SECONDARY_CURRENCY = "secondary_currency";

    public static int DEFAULT_YEAR_OF_BIRTH = 1990;
    public static long DAY_IN_MILLIS = 86400000L; // / 86,400,000 milliseconds in a day
    public static long HOUR_IN_MILLIS = 3600000L; // Amount of milliseconds in an hour

    public static int INTENT_FROM_NOTIFICATION = 30;
    public static int CONTACT_PERMISSION_CODE = 3;
    public static String INTENT_FROM_KEY = "intent_from_key";
    public static String GOOGLE_SIGN_IN_KEY = "1072707269724-3v8qbbu86kmfs252eu44amna8cpqqj9c.apps.googleusercontent.com";

    /**
     * Firebase constants
     */
    public static String TABLE_REPORTS = "birthdays";
}
