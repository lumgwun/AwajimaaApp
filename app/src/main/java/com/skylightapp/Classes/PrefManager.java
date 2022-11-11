package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.model.QBUser;
import com.skylightapp.LoginActivity;
import com.skylightapp.SignTabMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    private static final String PREF_NAME = "skylight";
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

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        //sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsAdmin(boolean isAdmin) {
        editor.putBoolean(IS_ADMIN, isAdmin);
        editor.commit();
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
        editor.putString("Email", email);
        editor.putString("Password", password);
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
    public void createUserLoginSession(long profileID,String uName, String uPassword,String email,String machine){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(PROFILE_USERNAME, uName);
        editor.putString(PROFILE_PASSWORD,  uPassword);
        editor.putString(KEY_EMAIL,  email);
        editor.putLong(PROFILE_ID,  profileID);
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

        user.put(PROFILE_USERNAME, pref.getString(PROFILE_USERNAME, null));
        user.put(PROFILE_PASSWORD, pref.getString(PROFILE_PASSWORD, null));
        user.put(PROFILE_EMAIL, pref.getString(PROFILE_EMAIL, null));
        user.put(PROFILE_ID, pref.getString(PROFILE_ID, null));
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
        SharedPreferences sharedPreferences = _context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
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
    public void saveBankAccountDetails(Account account) {
        String bankName = account.getBankName();
        String userName=account.getAccountName();
        int accountNumber =account.getAwajimaAcctNo();
        AccountTypes accountType = (AccountTypes) account.getType();
        String accountBalance = String.valueOf(account.getAccountBalance());
        SharedPreferences sharedPreferences = _context.getSharedPreferences("BankAccountDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Bank Name", bankName);
        editor.putString("Customer Name", userName);
        editor.putString("Account Number", String.valueOf(accountNumber));
        editor.putString("Account Balance", accountBalance);
        editor.putString("Account Type", String.valueOf(accountType));
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

        SharedPreferences sharedPreferences = _context.getSharedPreferences("MyProfileDetails", Context.MODE_PRIVATE);
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
    public void savePackages(Context context, List<SkyLightPackage> skyLightPackages) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonPackages = gson.toJson(skyLightPackages);

        editor.putString("My Packages", jsonPackages);

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

    public void saveFavoritePackage(Context context, List<SkyLightPackModel> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
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

        editor.commit();
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

    public void removeFavorite(Context context, SkyLightPackModel packageListModel) {
        ArrayList<SkyLightPackModel> favorites = getFavoritePackage(context);
        if (favorites != null) {
            favorites.remove(packageListModel);
            saveFavoritePackage(context, favorites);
        }
    }

    public ArrayList<SkyLightPackModel> getFavoritePackage(Context context) {
        SharedPreferences settings;
        List<SkyLightPackModel> favorites;

        settings = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            SkyLightPackModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    SkyLightPackModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<SkyLightPackModel>(favorites);
        } else
            return null;

        return (ArrayList<SkyLightPackModel>) favorites;
    }

    public boolean isUserLoggedOut() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = Objects.requireNonNull(sharedPreferences.getString("Email", "")).isEmpty();
        boolean isPasswordEmpty = Objects.requireNonNull(sharedPreferences.getString("Password", "")).isEmpty();
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
        sharedPreferences = AppController.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
}
