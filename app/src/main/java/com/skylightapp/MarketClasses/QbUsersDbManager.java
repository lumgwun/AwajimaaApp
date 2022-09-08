package com.skylightapp.MarketClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class QbUsersDbManager {
    private static String TAG = QbUsersDbManager.class.getSimpleName();
    private static final String TABLE_STUDENTS = "students";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String CITY = "class";

    public static final String DB_TABLE_NAME = "chat_users";
    public static final String DB_COLUMN_ID = "ID";
    public static final String DB_COLUMN_USER_FULL_NAME = "userFullName";
    public static final String DB_COLUMN_USER_LOGIN = "userLogin";
    public static final String DB_COLUMN_USER_ID = "userID";
    public static final String DB_COLUMN_USER_PASSWORD = "userPass";
    public static final String DB_COLUMN_USER_TAG = "userTag";

    private static QbUsersDbManager instance;
    private Context mContext;

    private QbUsersDbManager(Context context) {
        this.mContext = context;
    }

    public static QbUsersDbManager getInstance(Context context) {
        if (instance == null) {
            instance = new QbUsersDbManager(context);
        }

        return instance;
    }

    public ArrayList<QBUser> getAllUsers() {

        ArrayList<QBUser> allUsers = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(DB_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int userIdColIndex = c.getColumnIndex(DB_COLUMN_USER_ID);
            int userLoginColIndex = c.getColumnIndex(DB_COLUMN_USER_LOGIN);
            int userPassColIndex = c.getColumnIndex(DB_COLUMN_USER_PASSWORD);
            int userFullNameColIndex = c.getColumnIndex(DB_COLUMN_USER_FULL_NAME);
            int userTagColIndex = c.getColumnIndex(DB_COLUMN_USER_TAG);

            do {
                QBUser qbUser = new QBUser();

                qbUser.setFullName(c.getString(userFullNameColIndex));
                qbUser.setLogin(c.getString(userLoginColIndex));
                qbUser.setId(c.getInt(userIdColIndex));
                qbUser.setPassword(c.getString(userPassColIndex));

                StringifyArrayList<String> tags = new StringifyArrayList<>();
                tags.add(c.getString(userTagColIndex));
                qbUser.setTags(tags);

                allUsers.add(qbUser);
            } while (c.moveToNext());
        }

        c.close();
        dbHelper.close();

        return allUsers;
    }

    public QBUser getUserById(Integer userId) {
        QBUser qbUser = null;
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(DB_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int userIdColIndex = c.getColumnIndex(DB_COLUMN_USER_ID);
            int userLoginColIndex = c.getColumnIndex(DB_COLUMN_USER_LOGIN);
            int userPassColIndex = c.getColumnIndex(DB_COLUMN_USER_PASSWORD);
            int userFullNameColIndex = c.getColumnIndex(DB_COLUMN_USER_FULL_NAME);
            int userTagColIndex = c.getColumnIndex(DB_COLUMN_USER_TAG);

            do {
                if (c.getInt(userIdColIndex) == userId) {
                    qbUser = new QBUser();
                    qbUser.setFullName(c.getString(userFullNameColIndex));
                    qbUser.setLogin(c.getString(userLoginColIndex));
                    qbUser.setId(c.getInt(userIdColIndex));
                    qbUser.setPassword(c.getString(userPassColIndex));

                    StringifyArrayList<String> tags = new StringifyArrayList<>();
                    tags.add(c.getString(userTagColIndex).split(","));
                    qbUser.setTags(tags);
                    break;
                }
            } while (c.moveToNext());
        }

        c.close();
        dbHelper.close();

        return qbUser;
    }

    public void saveAllUsers(ArrayList<QBUser> allUsers, boolean needRemoveOldData) {
        if (needRemoveOldData) {
            clearDB();
        }

        for (QBUser qbUser : allUsers) {
            saveUser(qbUser);
        }
        Log.d(TAG, "saveAllUsers");
    }

    public void saveUser(QBUser qbUser) {
        ContentValues cv = new ContentValues();
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(DB_COLUMN_USER_FULL_NAME, qbUser.getFullName());
        cv.put(DB_COLUMN_USER_LOGIN, qbUser.getLogin());
        cv.put(DB_COLUMN_USER_ID, qbUser.getId());
        cv.put(DB_COLUMN_USER_PASSWORD, qbUser.getPassword());
        cv.put(DB_COLUMN_USER_TAG, qbUser.getTags().getItemsAsString());

        db.insert(DB_TABLE_NAME, null, cv);
        dbHelper.close();
    }

    public void clearDB() {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DB_TABLE_NAME, null, null);
        dbHelper.close();
    }

    public ArrayList<QBUser> getUsersByIds(List<Integer> usersIds) {
        ArrayList<QBUser> qbUsers = new ArrayList<>();

        for (Integer userId : usersIds) {
            if (getUserById(userId) != null) {
                qbUsers.add(getUserById(userId));
            }
        }

        return qbUsers;
    }
}
