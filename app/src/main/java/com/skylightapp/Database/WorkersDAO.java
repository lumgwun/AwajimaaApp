package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.skylightapp.Classes.CustomerManager.WORKER;
import static com.skylightapp.Classes.CustomerManager.WORKER_ID;
import static com.skylightapp.Classes.CustomerManager.WORKER_TABLE;

public class WorkersDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = WORKER_ID
            + " =?";
    public WorkersDAO(Context context) {
        super(context);
    }

    public void saveNewWorker(int workerID,  String worker) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORKER_ID, workerID);
        contentValues.put(WORKER, worker);
        db.insert(WORKER_TABLE, null, contentValues);
        db.close();



    }
    private void getWorkersCursor(ArrayList<String> workersArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                String worker = cursor.getString(1);
                workersArrayList.add(worker);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<String> getAllWorkers() {
        ArrayList<String> workersArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {WORKER};
        Cursor cursor = db.query(WORKER_TABLE, columns, WORKER, null, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getWorkersCursor(workersArrayList, cursor);
                cursor.close();
            }

        db.close();
        return workersArrayList;
    }
}
