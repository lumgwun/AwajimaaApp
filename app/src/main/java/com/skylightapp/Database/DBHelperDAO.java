package com.skylightapp.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHelperDAO {
    protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    public DBHelperDAO(Context context) {
        this.mContext = context;
        dbHelper = DBHelper.getHelper(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DBHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }


    public SQLiteDatabase getWritableDatabase() {
        return writableDatabase;
    }

    public void setWritableDatabase(SQLiteDatabase writableDatabase) {
        this.writableDatabase = writableDatabase;
    }
    public SQLiteDatabase getReadableDatabase() {
        return readableDatabase;
    }

    public void setReadableDatabase(SQLiteDatabase readableDatabase) {
        this.readableDatabase = readableDatabase;
    }
}
