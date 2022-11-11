package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PasswordHelpers;
import com.skylightapp.Classes.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.Profile.CUS_ID_PIX_KEY;
import static com.skylightapp.Classes.Profile.PASSWORD;
import static com.skylightapp.Classes.Profile.PASSWORD_TABLE;
import static com.skylightapp.Classes.Profile.PICTURE_TABLE;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFID_FOREIGN_KEY_PIX;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ADDRESS;
import static com.skylightapp.Classes.Profile.PROFILE_CUS_ID_KEY;
import static com.skylightapp.Classes.Profile.PROFILE_DATE_JOINED;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_NEXT_OF_KIN;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_PIC_ID;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROFILE_SPONSOR_ID;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_STATUS;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;
import static com.skylightapp.Classes.Profile.PROF_BUSINESS_ID;
import static com.skylightapp.Classes.Profile.PROF_ID_FOREIGN_KEY_PASSWORD;
import static com.skylightapp.Classes.Profile.PROF_REF_LINK;
import static com.skylightapp.Classes.Profile.SPONSOR_REFERER;
import static com.skylightapp.Classes.Profile.SPONSOR_REF_COUNT;
import static com.skylightapp.Classes.Profile.SPONSOR_REF_REWARD_COUNT21;
import static com.skylightapp.Classes.Profile.SPONSOR_TABLE;
import static com.skylightapp.Classes.Profile.USER_TYPE_BIZ_ID;
import static com.skylightapp.Classes.Profile.USER_TYPE_CUS_ID;
import static com.skylightapp.Classes.Profile.USER_TYPE_MARKET_ID;
import static com.skylightapp.Classes.Profile.USER_TYPE_PROF_ID;
import static com.skylightapp.Classes.Profile.USER_TYPE_STRING;
import static com.skylightapp.Classes.Profile.USER_TYPE_TABLE;
import static java.lang.String.valueOf;

public class ProfDAO extends DBHelperDAO{
    public static String  password;
    private static final String WHERE_ID_EQUALS = PROFILE_ID
            + " =?";
    public ProfDAO(Context context) {
        super(context);
    }
    public String getUserPassword(String phoneNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_PHONE + "=?";
        String password=null;
        String[] selectionArgs = new String[]{valueOf(phoneNo)};
        Cursor cursor = db.query(PROFILES_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    password=cursor.getColumnName(19);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return password;
    }
    public long addUserTypes(ArrayList<String> userTypeList,int profID,int cusID,long bizID,int marketID) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        for (int i = 0; i < userTypeList.size(); i++) {
            String userType = userTypeList.get(i);
            ContentValues values = new ContentValues();
            values.put(USER_TYPE_STRING, userType);
            values.put(USER_TYPE_PROF_ID, profID);
            values.put(USER_TYPE_CUS_ID, cusID);
            values.put(USER_TYPE_BIZ_ID, bizID);
            values.put(USER_TYPE_MARKET_ID, marketID);
            long id = db.insert(USER_TYPE_TABLE, null, values);
            if (id != -1)
                count += 10;
        }
        return count;

    }
    public long addUserType(String userType,int profID,int cusID,long bizID,int marketID) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_TYPE_STRING, userType);
        values.put(USER_TYPE_PROF_ID, profID);
        values.put(USER_TYPE_CUS_ID, cusID);
        values.put(USER_TYPE_BIZ_ID, bizID);
        values.put(USER_TYPE_MARKET_ID, marketID);
        return db.insert(USER_TYPE_TABLE, null, values);

    }
    public int getReferrerCount(String refLink) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SPONSOR_REFERER + "=?";
        int refCount=0;
        String[] selectionArgs = new String[]{valueOf(refLink)};
        Cursor cursor = db.query(SPONSOR_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return 0;
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    refCount=cursor.getColumnCount();
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return refCount;
    }
    public void updateRewardCount(String refLink,int newUserCount) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SPONSOR_REF_REWARD_COUNT21, newUserCount);
        String selection = SPONSOR_REFERER + "=?";
        String[] selectionArgs = new String[]{refLink};
        sqLiteDatabase.update(SPONSOR_TABLE, contentValues, selection, selectionArgs);


    }
    public int getReferrerRewardCount(String refLink) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SPONSOR_REFERER + "=?";
        int refCount=0;
        String[] selectionArgs = new String[]{valueOf(refLink)};
        Cursor cursor = db.query(SPONSOR_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return 0;
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    refCount=cursor.getInt(9);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return refCount;
    }
    public void updateRefCount(String refLink,int newUserCount) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SPONSOR_REF_COUNT, newUserCount);
        String selection = SPONSOR_REFERER + "=?";
        String[] selectionArgs = new String[]{refLink};
        sqLiteDatabase.update(SPONSOR_TABLE, contentValues, selection, selectionArgs);


    }
    Cursor check_ProfPhone_No_exist(String user_phone_no, String status){
        String query = "SELECT * FROM "+PROFILES_TABLE+" WHERE "+PROFILE_PHONE+" ="+user_phone_no+ " AND "+PROFILE_STATUS+" = "+status;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            cursor = db.rawQuery(query,null);
            return cursor;
        }
        else{
            return cursor;
        }
    }
    public String getUserName(String phoneNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_PHONE + "=?";
        String userName=null;
        String[] selectionArgs = new String[]{valueOf(phoneNo)};
        Cursor cursor = db.query(PROFILES_TABLE, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    userName=cursor.getColumnName(18);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return userName;
    }
    public String getPassword(int profileID) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {PROFILE_PASSWORD,PROFILE_ID};
        Cursor cursor =sqLiteDatabase.query(PROFILES_TABLE,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(1);
            password =cursor.getString(6);
        }

        return password;
    }
    public boolean checkUserExist(String strPhoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME, PROFILE_PHONE};

        String selection = PROFILE_PHONE + "=?";
        String[] selectionArgs = new String[]{valueOf(strPhoneNumber)};

        Cursor cursor = db.query(PROFILES_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        //close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Profile> getAllProfileUsers() {
        ArrayList<Profile> userModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + PROFILES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
        //db.close();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Profile profile = new Profile();
                Customer customer = new Customer();
                profile.setPID(Integer.parseInt(c.getString(0)));
                profile.setProfileLastName(c.getString(1));
                profile.setProfileFirstName(c.getString(2));
                profile.setProfilePhoneNumber(c.getString(3));
                profile.setProfileEmail(c.getString(4));
                profile.setProfileRole(c.getString(16));
                try {
                    profile.setProfileUserName(c.getString(17));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                profile.setProfilePassword(c.getString(18));

                userModelArrayList.add(profile);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }
    public ArrayList<Profile> getTellersFromMachineAndBiz(String Teller,long bizID) {
        ArrayList<Profile> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(Teller)};
        Cursor cursor = db.query(PROFILES_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleProfileFromCursor(profileArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return profileArrayList;

    }
    public ArrayList<Profile> getBizProfiles(long bizID) {
        ArrayList<Profile> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROF_BUSINESS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};
        Cursor cursor = db.query(PROFILES_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if (cursor == null){
            return null;
        }else {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSimpleProfileFromCursor(profileArrayList, cursor);
                cursor.close();
            }

            db.close();

        }
        return profileArrayList;

        //Cursor cursor=null;
        //String selection = PROF_BUSINESS_ID + "=? AND " + PROFILE_PASSWORD + "=?";
        //String[] selectionArgs = new String[]{valueOf(bizID), valueOf(branch)};

        /*try {
            cursor = db.rawQuery("Select PROFILE_ROLE from PROFILES_TABLE INNER JOIN WORKER_TABLE ON PROFILE_ID=WORKER_TELLER_PROF_ID WHERE PROF_BUSINESS_ID='" + WORKER_BIZ_ID + "'", null);




        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();*/




    }
    public Cursor getSimpleProfileFromCursor(ArrayList<Profile> profileArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            String surname = cursor.getString(3);
            String firstName = cursor.getString(4);
            profileArrayList.add(new Profile(surname,firstName));
        }
        return cursor;
    }
    public String getUserRole(int userId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String result=null;
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};

        Cursor cursor = sqLiteDatabase.query(PROFILES_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(16);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return result;

    }

    public Cursor getUsersDetails() {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getUserDetails(int userId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE WHERE PROFILE_ID = ?",
                new String[]{valueOf(userId)});
    }

    public boolean updateRole(String role, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ROLE, role);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateProfileUserName(String name, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_USERNAME, name);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                > 0;

    }
    public void updateProfile(int id,String lastName, String firstName,String phoneNo,String email,String gender,String address,String nextOfKin,String userName,String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_SURNAME, lastName);
        contentValues.put(PROFILE_FIRSTNAME, firstName);
        contentValues.put(PROFILE_USERNAME, userName);
        contentValues.put(PROFILE_PHONE, phoneNo);
        contentValues.put(PROFILE_EMAIL, email);
        contentValues.put(PROFILE_ADDRESS, address);
        contentValues.put(PROFILE_PASSWORD, password);
        contentValues.put(PROFILE_GENDER, gender);
        contentValues.put(PROFILE_NEXT_OF_KIN, nextOfKin);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs);


    }

    public boolean updatePhoneNumber(String phoneNumber, int profileID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_PHONE, phoneNumber);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileID)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                > 0;
    }

    public boolean updateUserRole(String role, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_ROLE, role);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                > 0;

    }

    public boolean updateUserAddress(String address, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ADDRESS, address);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateProfile2(int profileId, int customerId, String lastName, String firstName, String phoneNumber, String email, String dob, String gender, String address, String state, String nextOfKin, String dateJoined, String userName, String password, String profilePicture, String status, int roleId) {

        SQLiteDatabase db = getWritableDatabase();
        Profile profile = new Profile();
        Customer customer = new Customer();
        customerId = customer.getCusUID();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_USERNAME, customerId);
        contentValues.put(PROFILE_PHONE, phoneNumber);
        contentValues.put(PROFILE_ADDRESS, address);
        contentValues.put(PROFILE_EMAIL, email);
        contentValues.put(PROFILE_PASSWORD, password);
        contentValues.put(PROFILE_FIRSTNAME, firstName);
        contentValues.put(PROFILE_SURNAME, lastName);
        contentValues.put(PROFILE_STATUS, status);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileId)};
        db.update(PROFILES_TABLE, contentValues, selection, selectionArgs);
        return true;
    }

    public boolean updateProfile3(Profile profile,int id) {
        SQLiteDatabase db = getWritableDatabase();
        Customer customer = new Customer();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_ID, profile.getPID());
        contentValues.put(PROFILE_SURNAME, profile.getProfileLastName());
        contentValues.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        contentValues.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        contentValues.put(PROFILE_EMAIL, profile.getProfileEmail());
        contentValues.put(PROFILE_GENDER, profile.getProfileGender());
        contentValues.put(PROFILE_ADDRESS, valueOf(profile.getProfileAddress()));
        contentValues.put(PROFILE_STATE, profile.getProfileState());
        contentValues.put(PROFILE_USERNAME, profile.getProfileUserName());
        contentValues.put(PROFILE_PASSWORD, profile.getProfilePassword());
        contentValues.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
        contentValues.put(PROFILE_STATUS, profile.getProfileStatus());
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        db.update(PROFILES_TABLE, contentValues, selection, selectionArgs);
        return true;
    }


    public void overwriteUser(int PROFILE_ID,Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_SURNAME, profile.getProfileLastName());
        cv.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        cv.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        cv.put(PROFILE_EMAIL, profile.getProfileEmail());
        cv.put(PROFILE_DOB, valueOf(profile.getProfileDob()));
        cv.put(PROFILE_ADDRESS, valueOf(profile.getProfileAddress()));
        cv.put(PROFILE_OFFICE, profile.getProfOfficeName());
        cv.put(PROFILE_NEXT_OF_KIN, profile.getNextOfKin());
        cv.put(PROFILE_USERNAME, profile.getProfileUserName());
        cv.put(PROFILE_PASSWORD, profile.getProfilePassword());
        cv.put(PROFILE_ROLE, profile.getProfileRole());
        cv.put(PROFILE_STATUS, profile.getProfileStatus());
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(PROFILE_ID)};


        db.update(PROFILES_TABLE, cv, selection,selectionArgs);
        db.close();

    }
    public void UpdateUserStatus(String status, int profileID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        Profile profile = new Profile();
        profileID = profile.getPID();
        cVals.put(PROFILE_STATUS, status);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        long result = db.update(PROFILES_TABLE, cVals, selection, selectionArgs);
        //Log.d("Update Result:", "=" + result);

    }
    public int getUsersCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + PROFILES_TABLE;
        Cursor cursor=null;
        try {
            cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }


        return 0;
    }
    public int getProfileCount(int profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileId)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PROFILES_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(PROFILE_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public ArrayList<Profile> getAllProfiles() {
        ArrayList<Profile> profiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PROFILES_TABLE, null, null, null, null,
                null, null);
        getProfilesFromCursor(profiles, cursor);

        cursor.close();
        //db.close();

        return profiles;
    }
    public ArrayList<String> getProfileName(String machine) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{machine};
        String[] colums = {PROFILE_FIRSTNAME, PROFILE_SURNAME};

        try (Cursor res = db.query(PROFILES_TABLE, colums, selection, selectionArgs, null,
                null, null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(16));
                res.moveToNext();
            }
        }
        return array_list;
    }
    public void blockCustomer(int cusID, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_ROLE, status);
        String selection = PROFILE_CUS_ID_KEY + "=?";
        String[] selectionArgs = new String[]{valueOf(cusID)};
        db.update(PROFILES_TABLE, values, selection,
                selectionArgs);

    }
    public void blockProfile(int profID, String blocked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_ROLE, blocked);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profID)};
        db.update(PROFILES_TABLE, values, selection,
                selectionArgs);

    }
    public Cursor getUserDetailsFromUserName(String userName) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE WHERE USERNAME = ?",
                new String[]{valueOf(userName)});
    }
    @SuppressLint("Recycle")
    public Profile getUserDetailsFromPhoneNumber(String phoneNumber) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return (Profile) sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE WHERE USER_PHONE = ?",
                new String[]{valueOf(phoneNumber)});
    }
    public String getProfileRoleByEmailAndPassword(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String role=null;

        @SuppressLint("Recycle") Cursor pictureCursor = db.query(PROFILES_TABLE,
                null,
                PROFILE_EMAIL + "=?",
                new String[]{String.valueOf(email)},
                null,
                null,
                null);
        pictureCursor.moveToNext();

        role = pictureCursor.getString(16);

        return (role);

    }
    public boolean updateRole(int profileID,String role) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_ROLE, role);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection,
                selectionArgs) > 0;
    }
    private Profile getProfile(String phoneNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Profile profile = new Profile();

        @SuppressLint("Recycle") Cursor cursor = db.query(PROFILES_TABLE,
                null,
                PROFILE_PHONE + "=?",
                new String[]{String.valueOf(phoneNumber)},
                null,
                null,
                null);
        if (cursor.moveToLast()) {
            try {
                profile.setProfileUserName(cursor.getString(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            profile.setProfileLastName(cursor.getString(1));
            profile.setProfileFirstName(cursor.getString(2));
            profile.setProfilePhoneNumber(cursor.getString(3));
            profile.setProfileEmail(cursor.getString(4));
            profile.setProfileGender(cursor.getString(6));
            profile.setProfOfficeName(cursor.getString(14));
            profile.setProfileDob(cursor.getString(4));
            profile.setProfileAddress(cursor.getString(7));
            return profile;

        }else {
            Log.e("error! not found", "We could not find that User ");
            return profile;

        }
    }

    public String getRole(int profileID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT NAME FROM PROFILES_TABLE WHERE PROFILE_ID = ?",
                new String[]{valueOf(profileID)});
        cursor.moveToFirst();

        String value = cursor.getString(16);
        cursor.close();
        return value;

    }
    public Bitmap getProfilePicture(int profId) {

        try {
            Uri picturePath = getPicturePath(profId);
            if (picturePath == null )
                return (null);

            return (BitmapFactory.decodeFile(String.valueOf(picturePath)));

        }catch (NullPointerException exception)
        {
            exception.printStackTrace();
        }

        return null;
    }
    public Uri getPicturePath(int profileId) {

        try {
            SQLiteDatabase db = getReadableDatabase();
            Uri picturePath;
            try (Cursor reportCursor = db.query(PICTURE_TABLE,
                    null,
                    PROFILE_PIC_ID + "=?",
                    new String[]{String.valueOf(profileId)},
                    null,
                    null,
                    null)) {
                reportCursor.moveToNext();
                int column_index = reportCursor.getColumnIndexOrThrow(PICTURE_URI);
                return Uri.parse(reportCursor.getString(column_index));

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        //return (picturePath);
        return null;
    }

    private Cursor getProfilesFromCursor(ArrayList<Profile> profiles, Cursor cursor) {
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String lastName = cursor.getString(1);
            String firstName = cursor.getString(2);
            String state = cursor.getString(12);
            String username = cursor.getString(17);
            //String password = cursor.getString(18);
            profiles.add(new Profile(id,firstName, lastName, state, username ));
        }return  cursor;
    }
    public ArrayList<Profile> getAllGrpAcctProfiles(long GRPA_ID) {

        ArrayList<Profile> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GRP_PROFILE_TABLE, null, GRPA_ID + "=?",
                new String[]{String.valueOf(GRPA_ID)}, null, null,
                null, null);
        getProfileAcctFromCursor(profileArrayList, cursor);
        cursor.close();
        //db.close();

        return profileArrayList;
    }
    private void getProfileAcctFromCursor(ArrayList<Profile> profileArrayList , Cursor cursor) {
        while (cursor.moveToNext()) {

            int profileID = (cursor.getInt(1));
            String surname = cursor.getString(4);
            String firstName = cursor.getString(5);
            String dateJoined = cursor.getString(6);
            String status = cursor.getString(7);
            Uri pix = Uri.parse(cursor.getString(3));
            profileArrayList.add(new Profile(profileID, surname,firstName,dateJoined,status, pix));

        }
    }


    public List<String> getAllUsers(){
        List<String> listUsers = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME};
        Cursor c=null;

        try {
            c = db.rawQuery("SELECT * FROM " + PROFILES_TABLE , columns);
            if(c == null) return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(1);
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return listUsers;
    }
    public int getAllProfileCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = null;
        String[] selectionArgs = null;
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PROFILES_TABLE + " WHERE " +  selection,
                selectionArgs);
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PROFILE_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public long insertProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, profile.getPID());
        values.put(PROFILE_SURNAME, profile.getProfileLastName());
        values.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        values.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        values.put(PROFILE_EMAIL, profile.getProfileEmail());
        values.put(PROFILE_DOB, profile.getProfileDob());
        values.put(PROFILE_GENDER, profile.getProfileGender());
        values.put(PROFILE_ADDRESS, profile.getProfileAddress());
        values.put(PROFILE_STATE, profile.getProfileState());
        values.put(PROFILE_OFFICE, profile.getProfOfficeName());
        values.put(PROFILE_DATE_JOINED, profile.getProfileDateJoined());
        values.put(PROFILE_ROLE, profile.getProfileRole());
        values.put(PROFILE_USERNAME, profile.getProfileUserName());
        values.put(PROFILE_PASSWORD, profile.getProfilePassword());
        values.put(PROFILE_SPONSOR_ID, profile.getProfileSponsorID());
        values.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
        db.insert(PROFILES_TABLE,null,values);
        return profile.getPID();
    }
    public String getProfileRoleByUserNameAndPassword(String userName,String password) {
        SQLiteDatabase db = getReadableDatabase();
        String role = null;
        String[] columns = {PROFILE_ROLE};
        String selection = PROFILE_USERNAME + "=? AND " + PROFILE_PASSWORD + "=?";
        String[] selectionArgs = new String[]{valueOf(userName), valueOf(password)};

        @SuppressLint("Recycle") Cursor profileCursor = db.query(PROFILES_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (profileCursor != null && profileCursor.getCount() > 0) {
            profileCursor.moveToFirst();
            role = profileCursor.getString((16));
            profileCursor.close();
        }

            /*if(profileCursor != null && profileCursor.moveToFirst()){
                //role = profileCursor.getString(16);
                role = profileCursor.getString(profileCursor.getColumnIndex(PROFILE_ROLE));
                //profileCursor.close();
            }*/

        return (role);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    public int getProfileIDByUserNameAndPassword(String userName, String password) {
        int profileID=0;
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {PROFILE_ID};
        String selection = PROFILE_USERNAME + "=? AND " + PROFILE_PASSWORD + "=?";
        String[] selectionArgs = new String[]{userName, password};

        @SuppressLint("Recycle") Cursor profileCursor = db.query(PROFILES_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while (profileCursor.moveToNext()) {
            profileID = profileCursor.getInt(0);
        }
        if (profileCursor != null)
            if (profileCursor.getCount() > 0) {
                profileCursor.close();
            }


        return profileID;

    }


    public long getProfileIDByEmailAndPassword(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = PROFILE_EMAIL + "=?";
        String[] selectionArgs = new String[]{valueOf(email)};

        @SuppressLint("Recycle") Cursor pictureCursor = db.query(PROFILES_TABLE,
                null,
                selection, selectionArgs,
                null,
                null,
                null);
        pictureCursor.moveToNext();

        String picturePath = pictureCursor.getString(0);

        return Long.parseLong((picturePath));
    }
    public Profile getProfileFromUserNameAndPassword(String userName,String password) {
        //long rv = -1;
        Profile profile = null;
        Cursor csr = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selection = PROFILE_USERNAME + "=? AND " + PROFILE_PASSWORD + "=?";
        String[] selectionArgs = new String[]{userName, password};
        String[] columns = {PROFILE_ID, PROFILE_FIRSTNAME, PROFILE_OFFICE,PROFILE_PHONE,PROFILE_ROLE,PROFILE_SURNAME};
        csr = sqLiteDatabase.query(PROFILES_TABLE,columns,selection,selectionArgs,null,null,null);
        if (csr.moveToFirst()) {
            profile = new Profile();

            profile.setPID(csr.getInt(0));
            profile.setProfileEmail(csr.getString(2));
            profile.setProfOfficeName(csr.getString(6));
            profile.setProfileFirstName(csr.getString(7));
            profile.setProfileLastName(csr.getString(8));
            profile.setProfileRole(csr.getString(15));
            profile.setProfileDateJoined(csr.getString(16));

        }
        return profile;


    }

    public void delete_User_byID(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};

            db.delete(PROFILE_ID, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};

        return db.rawQuery(
                "SELECT * FROM " + PROFILES_TABLE + " WHERE " + selection,
                selectionArgs
        );
    }
    public Cursor getProfileCursor(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        return db.rawQuery(
                "SELECT * FROM " + PROFILES_TABLE + " WHERE " + selection,
                selectionArgs
        );
    }
    public boolean updateProfilePassword(String password, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_PASSWORD, password);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                > 0;

    }

    public void updateProfile(int profileId, String phoneNo, String nextOfKin, String userEmail, String userAddress, String userName, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_PASSWORD, password);
        contentValues.put(PROFILE_USERNAME, userName);
        contentValues.put(PROFILE_ADDRESS, userAddress);
        contentValues.put(PROFILE_EMAIL, userEmail);
        contentValues.put(PROFILE_NEXT_OF_KIN, nextOfKin);
        contentValues.put(PROFILE_PHONE, phoneNo);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileId)};
        sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs);

    }
    public boolean updateProfileStatus(String status, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_STATUS, status);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(PROFILE_ID)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                > 0;
    }
    public long insertPassword(String password, int profileID) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        password = PasswordHelpers.passwordHash(password);

        contentValues.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID);
        contentValues.put(PASSWORD, password);
        return sqLiteDatabase.insert(PASSWORD_TABLE, null, contentValues);

    }
    protected boolean updateUserPassword(String password, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, password);
        String selection = PROF_ID_FOREIGN_KEY_PASSWORD + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};

        return sqLiteDatabase.update(PASSWORD_TABLE, contentValues, selection, selectionArgs) > 0;

    }
    public void updateReportPicture(int userId, Bitmap picture) {
        String picturePath = "";
        File internalStorage= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/skylightImage.jpg/");
        try {
            FileInputStream fis = new FileInputStream(internalStorage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File reportFilePath = new File(internalStorage, userId + ".png");
        picturePath = reportFilePath.toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(reportFilePath);
            picture.compress(Bitmap.CompressFormat.PNG, 100 /*quality*/, fos);
            fos.close();
        }
        catch (Exception ex) {
            Log.i("SKYLIGHT DATABASE", "Problem updating picture", ex);
            picturePath = "";
        }

        SQLiteDatabase db = getWritableDatabase();
        String selection = PROFID_FOREIGN_KEY_PIX + "=?";
        String[] selectionArgs = new String[]{valueOf(userId)};

        ContentValues newPictureValue = new ContentValues();
        newPictureValue.put(PICTURE_URI,
                picturePath);

        db.update(PICTURE_TABLE,
                newPictureValue,
                selection, selectionArgs);


    }
    public boolean updateUserPicture(Uri profilePicture, int PROFILE_ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PICTURE_URI, valueOf(profilePicture));
        String selection = PROFID_FOREIGN_KEY_PIX + "=?";
        String[] selectionArgs = new String[]{String.valueOf(PROFILE_ID)};
        return sqLiteDatabase.update(PICTURE_TABLE, contentValues, selection,
                selectionArgs) > 0;
    }

    public long insertProfilePicture(int profileID,int cusID,Uri profilePix) {
        int id=0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFID_FOREIGN_KEY_PIX, profileID);
        values.put(CUS_ID_PIX_KEY, cusID);
        values.put(PICTURE_URI, String.valueOf(profilePix));
        db.insert(PICTURE_TABLE,null,values);
        return id;
    }


    public boolean updateProfilePix(int profileID, int customerID, Uri photoUri) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String selection = PROFID_FOREIGN_KEY_PIX + "=? AND " + CUS_ID_PIX_KEY + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileID), String.valueOf(customerID)};
        contentValues.put(PICTURE_URI, valueOf(photoUri));
        return sqLiteDatabase.update(PICTURE_TABLE, contentValues, selection,
                selectionArgs) > 0;
    }

    public long addProfRefLink(int sharedPrefProfileID, String mInvitationUrl) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(sharedPrefProfileID)};
        contentValues.put(PROF_REF_LINK, valueOf(mInvitationUrl));
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection,
                selectionArgs);

    }
}
