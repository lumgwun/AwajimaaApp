package com.skylightapp.Database;

import static com.skylightapp.Classes.Worker.WORKER_NAME;
import static com.skylightapp.Classes.Worker.WORKER_BIZ_ID;
import static com.skylightapp.Classes.Worker.WORKER_ID;
import static com.skylightapp.Classes.Worker.WORKER_OFFICE_NAME;
import static com.skylightapp.Classes.Worker.WORKER_PROFILE_ID;
import static com.skylightapp.Classes.Worker.WORKER_ROLE;
import static com.skylightapp.Classes.Worker.WORKER_SIGNED_UP_DATE;
import static com.skylightapp.Classes.Worker.WORKER_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class WorkersDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = WORKER_ID
            + " =?";
    public WorkersDAO(Context context) {
        super(context);
    }

    public void saveNewWorker(int workerID, int profID, long bizID, String officeName,String worker,String role,String dateSignedUp) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORKER_ID, workerID);
        contentValues.put(WORKER_PROFILE_ID, profID);
        contentValues.put(WORKER_BIZ_ID, bizID);
        contentValues.put(WORKER_ROLE, role);
        contentValues.put(WORKER_OFFICE_NAME, officeName);
        contentValues.put(WORKER_NAME, worker);
        contentValues.put(WORKER_SIGNED_UP_DATE, dateSignedUp);
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
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {WORKER_NAME};
        Cursor cursor = db.query(WORKER_TABLE, columns, WORKER_NAME, null, null,
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
