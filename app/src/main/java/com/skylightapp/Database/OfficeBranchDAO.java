package com.skylightapp.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.OfficeBranch;

import java.util.ArrayList;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ADDRESS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_APPROVER;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_DATE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_NAME;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_STATUS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_SUPERADMIN_ID;

public class OfficeBranchDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = OFFICE_BRANCH_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public OfficeBranchDAO(Context context) {
        super(context);
    }
    public long insertOfficeBranch(int officeBranchId, int superAdminID, String branchName, String branchRegDate, String branchAddress, String branchApprover, String branchStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OFFICE_BRANCH_ID, officeBranchId);
        values.put(OFFICE_SUPERADMIN_ID, superAdminID);
        values.put(OFFICE_BRANCH_NAME, branchName);
        values.put(OFFICE_BRANCH_DATE, String.valueOf(branchRegDate));
        values.put(OFFICE_BRANCH_ADDRESS, branchAddress);
        values.put(OFFICE_BRANCH_APPROVER, branchApprover);
        values.put(OFFICE_BRANCH_STATUS, branchStatus);
        db.insert(OFFICE_BRANCH_TABLE,null,values);
        return officeBranchId;
    }

    private void getOfficeBranchCursor(ArrayList<OfficeBranch> officeBranchArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int officeID = cursor.getInt(0);
            String officeName = cursor.getString(2);
            String officeStatus = cursor.getString(6);
            officeBranchArrayList.add(new OfficeBranch(officeID, officeName,officeStatus));
        }
    }

}
