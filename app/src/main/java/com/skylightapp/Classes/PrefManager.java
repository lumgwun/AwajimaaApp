package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.blongho.country_data.Currency;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.model.QBUser;
import com.skylightapp.LoginActivity;
import com.skylightapp.MapAndLoc.Region;
import com.skylightapp.MarketClasses.MarketBizPackModel;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.SignTabMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.skylightapp.Bookings.BookingConstant.MANUAL;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROF_REF_LINK;


public class PrefManager {
    SharedPreferences pref;
    private static final String TAG = PrefManager.class.getSimpleName();
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "awajima";
    private static final String PREF_PARAM_IS_PROFILE_CREATED = "isProfileCreated";
    private static final String PREF_PARAM_IS_POSTS_WAS_LOADED_AT_LEAST_ONCE = "isPostsWasLoadedAtLeastOnce";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_ADMIN = "IsAdmin";
    private static final String SHARED_PREFERENCE_KEY = "BirthdayReminderPreferences";
    private static final String KEY_USING_FIREBASE = "USING_FIREBASE";
    private static final String SHOULD_DISPLAY_WELCOME_SCREEN = "DISPLAY_WELCOME_SCREEN";
    private static final String HAS_MIGRATED_JSON_DATA = "MIGRATED_JSON_DATA";
    public static final String FAVORITES = "_Favorite";

    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String QB_USER_ID = "qb_user_id";
    private static final String QB_USER_LOGIN = "qb_user_login";
    private static final String QB_USER_PASSWORD = "qb_user_password";
    private static final String QB_USER_FULL_NAME = "qb_user_full_name";
    private static final String QB_USER_TAGS = "qb_user_tags";

    private static PrefManager instance;

    private SharedPreferences sharedPreferences;

    public static final String KEY_NAME = "Name";

    public static final String KEY_EMAIL = "Email";

    public static final String KEY_PASSWORD = "txtPassword";
    boolean isEmailEmpty,isPasswordEmpty;

    AppController appController;
    private SharedPreferences app_prefs;
    private final String USER_ID = "user_id";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String DEVICE_TOKEN = "device_token";
    private final String SESSION_TOKEN = "session_token";
    private final String REQUEST_ID = "request_id";
    private final String REQUEST_TIME = "request_time";
    private final String REQUEST_LATITUDE = "request_latitude";
    private final String REQUEST_LONGITUDE = "request_longitude";
    private final String LOGIN_BY = "login_by";
    private final String SOCIAL_ID = "social_id";
    private final String PAYMENT_MODE = "payment_mode";
    private final String DEFAULT_CARD = "default_card";
    private final String DEFAULT_CARD_NO = "default_card_no";
    private final String DEFAULT_CARD_TYPE = "default_card_type";
    private final String BASE_PRICE = "base_cost";
    private final String DISTANCE_PRICE = "distance_cost";
    private final String TIME_PRICE = "time_cost";
    private final String IS_TRIP_STARTED = "is_trip_started";
    private final String HOME_ADDRESS = "home_address";
    private final String WORK_ADDRESS = "work_address";
    private final String DEST_LAT = "dest_lat";
    private final String DEST_LNG = "dest_lng";
    private final String REFEREE = "is_referee";
    private final String PROMO_CODE = "promo_code";
    private String password;

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        //sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public void putBasePrice(float price) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putFloat(BASE_PRICE, price);
        edit.apply();
    }

    public float getBasePrice() {
        return app_prefs.getFloat(BASE_PRICE, 0f);
    }

    public void putDistancePrice(float price) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putFloat(DISTANCE_PRICE, price);
        edit.apply();
    }

    public float getDistancePrice() {
        return app_prefs.getFloat(DISTANCE_PRICE, 0f);
    }

    public void putTimePrice(float price) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putFloat(TIME_PRICE, price);
        edit.apply();
    }
    public void putEmail(String emailAddress) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PROFILE_EMAIL, emailAddress);
        edit.apply();

    }

    public float getTimePrice() {
        return app_prefs.getFloat(TIME_PRICE, 0f);
    }
    public void putDeviceToken(String deviceToken) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DEVICE_TOKEN, deviceToken);
        edit.apply();
    }

    public String getDeviceToken() {
        return app_prefs.getString(DEVICE_TOKEN, null);

    }

    public void putSessionToken(String sessionToken) {

        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(SESSION_TOKEN, sessionToken);
        edit.apply();
    }

    public String getSessionToken() {
        return app_prefs.getString(SESSION_TOKEN, null);

    }

    public void putRequestId(int requestId) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putInt(REQUEST_ID, requestId);
        edit.apply();
    }

    public int getRequestId() {
        return app_prefs.getInt(REQUEST_ID, AppConstants.NO_REQUEST);
    }

    public void putLoginBy(String loginBy) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(LOGIN_BY, loginBy);
        edit.apply();
    }

    public String getLoginBy() {
        return app_prefs.getString(LOGIN_BY, MANUAL);
    }

    public void putRequestTime(long time) {

        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putLong(REQUEST_TIME, time);
        edit.apply();
    }

    public long getRequestTime() {
        return app_prefs.getLong(REQUEST_TIME, AppConstants.NO_TIME);
    }

    public void putPaymentMode(int mode) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putInt(PAYMENT_MODE, mode);
        edit.apply();
    }

    public int getPaymentMode() {
        return app_prefs.getInt(PAYMENT_MODE, AppConstants.CASH);
    }

    public void putDefaultCard(int cardId) {

        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putInt(DEFAULT_CARD, cardId);
        edit.apply();
    }
    public void putUserId(int userId) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putInt(USER_ID, userId);
        edit.apply();
    }
    public int getUserId() {
        return app_prefs.getInt(USER_ID, 0);

    }

    public int getDefaultCard() {
        return app_prefs.getInt(DEFAULT_CARD, 0);
    }

    public void putDefaultCardNo(String cardNo) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DEFAULT_CARD_NO, cardNo);
        edit.apply();
    }

    public String getDefaultCardNo() {
        return app_prefs.getString(DEFAULT_CARD_NO, "");
    }

    public void putDefaultCardType(String cardType) {

        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DEFAULT_CARD_TYPE, cardType);
        edit.apply();
    }

    public String getDefaultCardType() {
        return app_prefs.getString(DEFAULT_CARD_TYPE, "");
    }

    public boolean getIsTripStarted() {
        return app_prefs.getBoolean(IS_TRIP_STARTED, false);
    }

    public void putIsTripStarted(boolean started) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(IS_TRIP_STARTED, started);
        edit.apply();
    }

    public String getHomeAddress() {
        return app_prefs.getString(HOME_ADDRESS, null);
    }

    public void putHomeAddress(String homeAddress) {

        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(HOME_ADDRESS, homeAddress);
        edit.apply();
    }

    public String getWorkAddress() {
        return app_prefs.getString(WORK_ADDRESS, null);
    }

    public void putWorkAddress(String homeAddress) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(WORK_ADDRESS, homeAddress);
        edit.apply();
    }

    public void putRequestLocation(LatLng latLang) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(REQUEST_LATITUDE, String.valueOf(latLang.latitude));
        edit.putString(REQUEST_LONGITUDE, String.valueOf(latLang.longitude));
        edit.apply();
    }

    public LatLng getRequestLocation() {
        LatLng latLng = new LatLng(0.0, 0.0);
        try {
            latLng = new LatLng(Double.parseDouble(app_prefs.getString(
                    REQUEST_LATITUDE, "0.0")), Double.parseDouble(app_prefs
                    .getString(REQUEST_LONGITUDE, "0.0")));
        } catch (NumberFormatException nfe) {
            latLng = new LatLng(0.0, 0.0);
        }
        return latLng;
    }
    public Region getRegion(int regionID) {
        Region region = new Region();
        boolean isTheRegion=false;
        try {
            region = new Region(app_prefs.getInt("regionID",0), Double.parseDouble(app_prefs.getString("lat","")) , Double.parseDouble(app_prefs.getString("lng","")),app_prefs.getLong("time",0), app_prefs.getBoolean("isEnter",false), Double.parseDouble(app_prefs.getString("radius", "")),app_prefs.getBoolean("isCurrentlyInside",false) ,app_prefs.getInt("distance",0), app_prefs.getString("distanceText",""), app_prefs.getInt("duration",0),app_prefs.getString("durationText",""), app_prefs.getString("type",""), app_prefs.getFloat("speed",0), app_prefs.getString("paramType",""), app_prefs.getString("status",""));

            if(app_prefs.getInt("regionID",0)==regionID){
                isTheRegion=true;

                return region;

            }
        } catch (Exception nfe) {
            //region = new Region();
        }
        return region;
    }
    public Region getRegion() {
        Region region = new Region();
        boolean isTheRegion=false;
        try {
            region = new Region(app_prefs.getInt("regionID",0), Double.parseDouble(app_prefs.getString("lat","")) , Double.parseDouble(app_prefs.getString("lng","")),app_prefs.getLong("time",0), app_prefs.getBoolean("isEnter",false), Double.parseDouble(app_prefs.getString("radius", "")),app_prefs.getBoolean("isCurrentlyInside",false) ,app_prefs.getInt("distance",0), app_prefs.getString("distanceText",""), app_prefs.getInt("duration",0),app_prefs.getString("durationText",""), app_prefs.getString("type",""), app_prefs.getFloat("speed",0), app_prefs.getString("paramType",""), app_prefs.getString("status",""));

            if(app_prefs.getInt("regionID",0)>0){
                isTheRegion=true;

                return region;

            }
        } catch (Exception nfe) {
            //region = new Region();
        }
        return region;
    }

    public void putClientDestination(LatLng destination) {

        SharedPreferences.Editor edit = app_prefs.edit();
        if (destination == null) {
            edit.putString(DEST_LAT, null);
            edit.putString(DEST_LNG, null);
        } else {
            edit.putString(DEST_LAT, String.valueOf(destination.latitude));
            edit.putString(DEST_LNG, String.valueOf(destination.longitude));
        }
        edit.apply();
    }

    public LatLng getClientDestination() {
        try {
            if (app_prefs.getString(DEST_LAT, null) != null) {
                return new LatLng(Double.parseDouble(app_prefs.getString(
                        DEST_LAT, "0.0")), Double.parseDouble(app_prefs
                        .getString(DEST_LNG, "0.0")));
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void putReferee(int is_referee) {

        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putInt(REFEREE, is_referee);
        edit.apply();
    }

    public int getReferee() {
        return app_prefs.getInt(REFEREE, 0);
    }

    public void putPromoCode(String promoCode) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PROMO_CODE, promoCode);
        edit.apply();
    }

    public String getPromoCode() {
        return app_prefs.getString(PROMO_CODE, null);
    }

    public void clearRequestData() {
        putRequestId(AppConstants.NO_REQUEST);
        putRequestTime(AppConstants.NO_TIME);
        putRequestLocation(new LatLng(0.0, 0.0));
        putIsTripStarted(false);
        putClientDestination(null);
        putPromoCode(null);
        // new DBHelper(context).deleteAllLocations();
    }

    @SuppressLint("CommitPrefEdits")
    public void Logout() {
        clearRequestData();
        putUserId(0);
        putSessionToken(null);
        putClientDestination(null);
        putLoginBy(MANUAL);
        app_prefs.edit().clear();
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }
    public void storeBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }
    public void saveTrackingId(String tracking_id, String trackingID) {
        editor.putString("TRACKING_ID", trackingID);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public void storeString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }


    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsAdmin(boolean isAdmin) {
        editor.putBoolean(IS_ADMIN, isAdmin);
        editor.apply();
    }

    public boolean setIsAdmin() {
        return pref.getBoolean(IS_ADMIN, true);
    }


    /*public void saveLoginDetails(String email, String password) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.apply();
    }*/
    public void saveLoginDetails(String email, String password) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PROFILE_EMAIL", email);
        editor.putString("PROFILE_PASSWORD", password);
        editor.apply();
    }
    public void saveAppReferrer(String referrer,String strName,String refCode) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AwajimaReferrer", referrer);
        editor.putString("AwajimaRefCode", refCode);
        editor.putString("AwajimaRefUser", strName);
        editor.apply();
    }
    public void saveAppReferrer(Uri refLink) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROF_REF_LINK, String.valueOf(refLink));
        editor.apply();
    }
    public void createUserLoginSession(int profileID,String uName, String uPassword,String email,String machine){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString("PROFILE_USERNAME", uName);
        editor.putString("PROFILE_PASSWORD",  uPassword);
        editor.putString("PROFILE_EMAIL",  email);
        editor.putLong("PROFILE_ID",  profileID);
        editor.putString("machine",  machine);
        editor.commit();
    }
    public void createUserLoginSession(int profileID,int qbUserID,String uName, String uPassword,String email,String machine){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString("PROFILE_USERNAME", uName);
        editor.putString("PROFILE_PASSWORD",  uPassword);
        editor.putString("PROFILE_EMAIL",  email);
        editor.putLong("PROFILE_ID",  profileID);
        editor.putLong("qbUserID",  qbUserID);
        editor.putString("machine",  machine);
        editor.commit();
    }


    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){


            Intent i = new Intent(_context, LoginActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            return true;
        }
        return false;
    }



    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();

        user.put("PROFILE_USERNAME", pref.getString("PROFILE_USERNAME", null));
        user.put("PROFILE_PASSWORD", pref.getString("PROFILE_PASSWORD", null));
        user.put("PROFILE_EMAIL", pref.getString("PROFILE_EMAIL", null));
        user.put("PROFILE_ID", pref.getString("PROFILE_ID", null));
        return user;
    }
    public void remove(String key) {
        SharedPreferences.Editor mEditor = pref.edit();
        mEditor.remove(key);
        mEditor.apply();
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, SignTabMainActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }


    public String getEmail() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("PROFILE_EMAIL", "");
    }
    public String getPassword() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("PROFILE_PASSWORD", "");
    }
    public void setPassword(String password) {
        editor.putString("PROFILE_PASSWORD", password);
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static Boolean isProfileCreated(Context context) {
        return getSharedPreferences(context).getBoolean(PREF_PARAM_IS_PROFILE_CREATED, false);
    }

    public static Boolean isPostWasLoadedAtLeastOnce(Context context) {
        return getSharedPreferences(context).getBoolean(PREF_PARAM_IS_POSTS_WAS_LOADED_AT_LEAST_ONCE, false);
    }

    public static void setProfileCreated(Context context, Boolean isProfileCreated) {
        getSharedPreferences(context).edit().putBoolean(PREF_PARAM_IS_PROFILE_CREATED, isProfileCreated).apply();
    }

    public static void setPostWasLoadedAtLeastOnce(Context context, Boolean isPostWasLoadedAtLeastOnce) {
        getSharedPreferences(context).edit().putBoolean(PREF_PARAM_IS_POSTS_WAS_LOADED_AT_LEAST_ONCE, isPostWasLoadedAtLeastOnce).apply();
    }

    public static void clearPreferences(Context context){
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
    public void saveAccountDetails(Account account) {
        String bankName=null;
        String userName=null;
        int accountNumber=0;
        AccountTypes accountType=null;
        double accountBalance=0;
        int awajimaAcctNo=0;
        Currency currency=null;
        String currencyCode=null;
        if(account !=null){
            currency=account.getCurrency();
            bankName = account.getBankName();
            userName=account.getAccountName();
            accountNumber =account.getAwajimaAcctNo();
            accountType = (AccountTypes) account.getType();
            accountBalance = account.getAccountBalance();
            awajimaAcctNo=account.getAwajimaAcctNo();
        }
        if(currency !=null){
            currencyCode=currency.getCode();
        }

        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ACCOUNT_NO", awajimaAcctNo);
        editor.putString("ACCOUNT_BANK", bankName);
        editor.putString("ACCOUNT_CURRENCY", currencyCode);
        editor.putString("ACCOUNT_NAME", userName);
        editor.putString("BANK_ACCT_NO", String.valueOf(accountNumber));
        editor.putFloat("ACCOUNT_BALANCE", (float) accountBalance);
        editor.putString("ACCOUNT_TYPE", String.valueOf(accountType));
        editor.apply();
    }
    public void saveProfileDetails(Profile profile) {
        String firstName = profile.getProfileFirstName();
        String lastName=profile.getProfileLastName();
        String phoneNumber =profile.getProfilePhoneNumber();
        String userName = profile.getProfileUserName();
        String profileEmail=profile.getProfileEmail();
        String userState =profile.getProfileState();
        String userOffice = profile.getProfOfficeName();
        String otp=profile.getProfile_AuthenticationKey();
        String dateJoined =profile.getProfileDateJoined();
        String DOB = profile.getProfileDob();
        String userGender = profile.getProfileGender();
        User.User_Type userType=profile.getProfileType();
        String userPin =profile.getProfilePin();
        String nextOfKin = profile.getNextOfKin();
        Uri profilePicture = profile.getProfilePicture();

        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("First Name", firstName);
        editor.putString("Surname", lastName);
        editor.putString("Email Address", profileEmail);
        editor.putString("Phone Number", phoneNumber);
        editor.putString("User Name", userName);
        editor.putString("State", userState);
        editor.putString("Nearest Office", userOffice);
        editor.putString("Secret Key", otp);
        editor.putString("User Gender", userGender);
        editor.putString("User Type", String.valueOf(userType));
        editor.putString("User Pin", userPin);
        editor.putString("Next of Kin", nextOfKin);
        editor.putString("Profile Picture", String.valueOf(profilePicture));
        editor.apply();
    }
    public void saveSaving(Context context, List<CustomerDailyReport> dailyReports) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonSavings = gson.toJson(dailyReports);

        editor.putString("My Savings", jsonSavings);

        editor.apply();
    }
    public void savePackages(Context context, List<MarketBizPackage> marketBizPackages) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonPackages = gson.toJson(marketBizPackages);

        editor.putString("My Packages", jsonPackages);

        editor.apply();
    }
    public void saveAccounts(Context context, ArrayList<Account> accountList) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String accountJson = gson.toJson(accountList);

        editor.putString("My Accounts", accountJson);

        editor.apply();
    }
    public void saveLoan(Context context, List<Loan> loans) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonSavings = gson.toJson(loans);

        editor.putString("My Savings", jsonSavings);

        editor.apply();
    }

    public void saveFavoritePackage(Context context, List<MarketBizPackModel> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }
    public void saveFavoriteCustomer(Context context, List<Customer> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String favoritesCustomer = gson.toJson(favorites);

        editor.putString(FAVORITES, favoritesCustomer);

        editor.apply();
    }

    public void addFavoriteCustomer(Context context, Customer customer) {
        List<Customer> favoriteCustomers = getFavoriteCustomers(context);
        if (favoriteCustomers == null)
            favoriteCustomers = new ArrayList<Customer>();
        favoriteCustomers.add(customer);
        saveFavoriteCustomer(context, favoriteCustomers);
    }
    public ArrayList<Customer> getFavoriteCustomers(Context context) {
        SharedPreferences settings;
        List<Customer> favorites;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Customer[] favoriteItems = gson.fromJson(jsonFavorites,
                    Customer[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Customer>(favorites);
        } else
            return null;

        return (ArrayList<Customer>) favorites;
    }

    public void removeFavorite(Context context, MarketBizPackModel packageListModel) {
        ArrayList<MarketBizPackModel> favorites = getFavoritePackage(context);
        if (favorites != null) {
            favorites.remove(packageListModel);
            saveFavoritePackage(context, favorites);
        }
    }

    public ArrayList<MarketBizPackModel> getFavoritePackage(Context context) {
        SharedPreferences settings;
        List<MarketBizPackModel> favorites;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MarketBizPackModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    MarketBizPackModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<MarketBizPackModel>(favorites);
        } else
            return null;

        return (ArrayList<MarketBizPackModel>) favorites;
    }

    public boolean isUserLoggedOut() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences22 = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences !=null){
            sharedPreferences=sharedPreferences22;
            isEmailEmpty = Objects.requireNonNull(sharedPreferences.getString(PROFILE_EMAIL, "")).isEmpty();
            isPasswordEmpty = Objects.requireNonNull(sharedPreferences.getString(PROFILE_PASSWORD, "")).isEmpty();


        }

        return isEmailEmpty || isPasswordEmpty;
    }


    public static synchronized void setIsUsingFirebase(Context context, boolean usingFirebase) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_USING_FIREBASE, usingFirebase);
        editor.apply();
    }

    public static synchronized boolean isUsingFirebase(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
        return prefs.getBoolean(KEY_USING_FIREBASE, false);
    }

    public static synchronized void setShouldShowWelcomeScreen(Context context, boolean shouldShowWelcomeScreen) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SHOULD_DISPLAY_WELCOME_SCREEN, shouldShowWelcomeScreen);
        editor.apply();
    }

    public static synchronized boolean shouldShowWelcomeScreen(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
        return prefs.getBoolean(SHOULD_DISPLAY_WELCOME_SCREEN, true);
    }
    public void storeBooleanInPref(String step_one_running, boolean b) {
        SharedPreferences prefs = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("STEP_ONE_RUNNING", b);
        editor.putBoolean("STEP_TWO_RUNNING", b);
        editor.putBoolean("STEP_THREE_RUNNING", b);
        editor.apply();
    }
    public boolean getBooleanInPef(String step_string) {
        if(step_string !=null){
            if (step_string.equalsIgnoreCase("STEP_ONE")) {
                return pref.getBoolean("STEP_ONE", false);
            } else if (step_string.equalsIgnoreCase("STEP_TWO")) {
                return pref.getBoolean("STEP_ONE", false);
            } else if (step_string.equalsIgnoreCase("STEP_THREE")) {
                return pref.getBoolean("STEP_ONE", false);
            } else if (step_string.equalsIgnoreCase("STEP_FOUR")) {
                return pref.getBoolean("STEP_ONE", false);
            }

        }

        editor.commit();

        return false;
    }


    public static synchronized void setHasMigratedjsonData(Context context, boolean hasMigrated) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(HAS_MIGRATED_JSON_DATA, hasMigrated);
        editor.apply();
    }

    public static synchronized boolean hasMigratedjsonData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
        return prefs.getBoolean(HAS_MIGRATED_JSON_DATA, false);
    }




    public static synchronized PrefManager getInstance() {
        if (instance == null) {
            instance = new PrefManager();
        }

        return instance;
    }


    public PrefManager() {
        instance = this;
        //appController= new AppController();
        try {

            sharedPreferences = AppController.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            getEditor().remove(key).commit();
        }
    }

    public void save(String key, Object value) {
        SharedPreferences.Editor editor = getEditor();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-supported preference");
        }

        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }



    public void saveQbUser(QBUser qbUser) {
        save(QB_USER_ID, qbUser.getId());
        save(QB_USER_LOGIN, qbUser.getLogin());
        save(QB_USER_PASSWORD, qbUser.getPassword());
        save(QB_USER_FULL_NAME, qbUser.getFullName());
        save(QB_USER_TAGS, qbUser.getTags().getItemsAsString());
    }

    public void removeQbUser() {
        delete(QB_USER_ID);
        delete(QB_USER_LOGIN);
        delete(QB_USER_PASSWORD);
        delete(QB_USER_FULL_NAME);
        delete(QB_USER_TAGS);
    }

    public QBUser getQbUser() {
        if (hasQbUser()) {
            Integer id = get(QB_USER_ID);
            String login = get(QB_USER_LOGIN);
            String password = get(QB_USER_PASSWORD);
            String fullName = get(QB_USER_FULL_NAME);
            String tagsInString = get(QB_USER_TAGS);

            StringifyArrayList<String> tags = null;

            if (tagsInString != null) {
                tags = new StringifyArrayList<>();
                tags.add(tagsInString.split(","));
            }

            QBUser user = new QBUser(login, password);
            user.setId(id);
            user.setFullName(fullName);
            user.setTags(tags);
            return user;
        } else {
            return null;
        }
    }

    public boolean hasQbUser() {
        return has(QB_USER_LOGIN) && has(QB_USER_PASSWORD);
    }

    public void clearAllData() {
        SharedPreferences.Editor editor = getEditor();
        editor.clear().commit();
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void deletePreference() {
        SharedPreferences.Editor editor = getEditor();
        editor.clear().commit();
    }



}
