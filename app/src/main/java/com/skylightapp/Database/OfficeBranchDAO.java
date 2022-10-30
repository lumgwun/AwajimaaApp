package com.skylightapp.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Inventory.Stocks;

import java.util.ArrayList;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ADDRESS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_APPROVER;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_BIZ_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_DATE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_NAME;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ROLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_STATUS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_SUPERADMIN_ID;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_10_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_20_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_40_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_5_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTANCE_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTER;
import static com.skylightapp.Inventory.Stocks.STOCK_BIZ_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_DEFECTIVE;
import static com.skylightapp.Inventory.Stocks.STOCK_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_MANAGER;
import static com.skylightapp.Inventory.Stocks.STOCK_QTY;
import static java.lang.String.valueOf;

public class OfficeBranchDAO extends DBHelperDAO {
    private static final String WHERE_ID_EQUALS = OFFICE_BRANCH_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public OfficeBranchDAO(Context context) {
        super(context);
    }

    public long insertOfficeBranch(int officeBranchId, int superAdminID, long bizID, String branchName, String branchRegDate, String branchAddress, String branchApprover, String branchStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OFFICE_BRANCH_ID, officeBranchId);
        values.put(OFFICE_SUPERADMIN_ID, superAdminID);
        values.put(OFFICE_BRANCH_NAME, branchName);
        values.put(OFFICE_BRANCH_DATE, String.valueOf(branchRegDate));
        values.put(OFFICE_BRANCH_ADDRESS, branchAddress);
        values.put(OFFICE_BRANCH_APPROVER, branchApprover);
        values.put(OFFICE_BRANCH_STATUS, branchStatus);
        values.put(OFFICE_BRANCH_BIZ_ID, bizID);
        db.insert(OFFICE_BRANCH_TABLE, null, values);
        return officeBranchId;
    }

    private void getOfficeBranchCursor(ArrayList<OfficeBranch> officeBranchArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int officeID = cursor.getInt(0);
            String officeName = cursor.getString(2);
            String officeStatus = cursor.getString(6);
            officeBranchArrayList.add(new OfficeBranch(officeID, officeName, officeStatus));
        }
    }
    public ArrayList<OfficeBranch> getAllOfficeBranches() {
        try {
            ArrayList<OfficeBranch> officeBranches = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(OFFICE_BRANCH_TABLE, null, null, null, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getOfficeBranchCursor(officeBranches, cursor);

                }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return officeBranches;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<OfficeBranch> getOfficesForBusiness(long bizID) {
        ArrayList<OfficeBranch> officeBranches = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = OFFICE_BRANCH_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};

        Cursor cursor = db.query(OFFICE_BRANCH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getOfficeBranchCursor(officeBranches, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return officeBranches;
    }
    public ArrayList<OfficeBranch> getAllOfficeBranches(String awajima) {
        ArrayList<OfficeBranch> officeBranches = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = OFFICE_BRANCH_ROLE + "=?";
        String[] selectionArgs = new String[]{valueOf(awajima)};

        Cursor cursor = db.query(OFFICE_BRANCH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getOfficeBranchCursor(officeBranches, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return officeBranches;
    }
    public void updateOfficeBranch(long bizID,String name,String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OFFICE_BRANCH_NAME, name);
        contentValues.put(OFFICE_BRANCH_ADDRESS, address);
        String selection = OFFICE_BRANCH_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};
        db.update(OFFICE_BRANCH_TABLE,
                contentValues, selection, selectionArgs);
        db.close();

    }



}
