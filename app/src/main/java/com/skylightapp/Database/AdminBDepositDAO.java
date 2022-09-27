package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Admins.AdminBankDeposit;

import java.util.ArrayList;

import static com.skylightapp.Admins.AdminBankDeposit.DEPOSITOR;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_ACCOUNT;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_ACCOUNT_NAME;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_AMOUNT;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_APPROVER;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_BANK;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_BIZ_ID;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_CONFIRMATION_DATE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_DATE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_DOC;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_ID;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_OFFICE_BRANCH;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_OFFICE_ID22;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_PROFILE_ID;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_TABLE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_TRANSACTION_STATUS;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_TRANS_TYPE;
import static com.skylightapp.Classes.AdminUser.ADMIN_PROFILE_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_CUS_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_DATE;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_PACKID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_STATUS;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_TABLE;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_EXPECTED_BALANCE;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_RECEIVED_AMOUNT;
import static java.lang.String.valueOf;

public class AdminBDepositDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = DEPOSIT_ID
            + " =?";
    public AdminBDepositDAO(Context context) {
        super(context);
    }
    public void deleteAdminDeposit(int depositID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DEPOSIT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(depositID)};
        db.delete(DEPOSIT_TABLE,
                selection,
                selectionArgs);

    }
    public void updateAdminDeposit( int depositID, String Status,String approver, String approvalDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEPOSIT_ACCOUNT_NAME, Status);
        values.put(DEPOSIT_CONFIRMATION_DATE, approvalDate);
        values.put(DEPOSIT_APPROVER, approver);
        String selection = DEPOSIT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(depositID)};
        db.update(DEPOSIT_TABLE, values, selection,
                selectionArgs);

    }

    public void updateAdminDeposit( int depositID, String bank, String acctName, String acctNo, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEPOSIT_BANK, bank);
        values.put(DEPOSIT_ACCOUNT_NAME, acctName);
        values.put(DEPOSIT_ACCOUNT, acctNo);
        values.put(DEPOSIT_AMOUNT, amount);
        String selection = DEPOSIT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(depositID)};
        db.update(DEPOSIT_TABLE, values, selection,
                selectionArgs);

    }
    public void updateAdminDeposit(int adminDepositID, Uri mImageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEPOSIT_DOC, String.valueOf(mImageUri));
        String selection = DEPOSIT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(adminDepositID)};
        db.update(DEPOSIT_TABLE, values, selection,
                selectionArgs);


    }
    public ArrayList<String> getAllAdminNamesForDeposit() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {DEPOSITOR};
        Cursor res = db.query(DEPOSIT_TABLE,columns,null,null,null,null,null);
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                array_list.add(res.getString(9));
                res.moveToNext();
            }
        }
        res.close();

        return array_list;
    }
    public int getAllAdminDepositBranchCount(String branch) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_OFFICE_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(branch)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    private void getAdminBankDepositFull(ArrayList<AdminBankDeposit> adminBankDepositArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int depositID = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            double depositAmt = Double.parseDouble(cursor.getString(2));
            String officeBranch = cursor.getString(3);
            String type = cursor.getString(4);
            String bank = cursor.getString(5);
            String acctName = cursor.getString(6);
            String acctNo = cursor.getString(7);
            String date = cursor.getString(8);
            String depositor = cursor.getString(9);
            String depositApprover = cursor.getString(10);
            String confirmationDate = cursor.getString(11);
            String status = cursor.getString(12);
            adminBankDepositArrayList.add(new AdminBankDeposit(depositID, profileID,depositAmt,officeBranch,type, bank,acctName, Integer.parseInt(acctNo), date,depositor,depositApprover,confirmationDate,status));
        }

    }

    private void getAdminBankDepositImp(ArrayList<AdminBankDeposit> adminBankDepositArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            double depositAmt = Double.parseDouble(cursor.getString(2));
            String officeBranch = cursor.getString(3);
            String type = cursor.getString(4);
            String bank = cursor.getString(5);
            String acctName = cursor.getString(6);
            int acctNo = cursor.getInt(7);
            String date = cursor.getString(8);
            String depositor = cursor.getString(9);
            String status = cursor.getString(12);
            adminBankDepositArrayList.add(new AdminBankDeposit(depositAmt,officeBranch,type, bank,acctName, acctNo, date,depositor,status));
        }

    }
    public ArrayList<AdminBankDeposit> getAdminDepositsByDepositor(String depositor) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DEPOSITOR + "=?";
        String[] selectionArgs = new String[]{valueOf(depositor)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositsByProfileID(int profileID) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DEPOSIT_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositsForBranch(String officeBranch) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DEPOSIT_OFFICE_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }

    public ArrayList<AdminBankDeposit> getAdminDepositsAtDate(String date) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        //String selection = DEPOSITOR + "=? AND " + DEPOSIT_DATE + "=?";
        //String[] selectionArgs = new String[]{valueOf(depositor), valueOf(date)};
        String selection = DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, DEPOSIT_OFFICE_BRANCH,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositAtDateAndBranch(String branch,String date) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DEPOSIT_OFFICE_BRANCH + "=? AND " + DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branch), valueOf(date)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositAtDateAndDepositor(String depositor,String date) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DEPOSITOR + "=? AND " + DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(depositor), valueOf(date)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAllAdminBankDepositImp() {
        ArrayList<AdminBankDeposit> adminBankDepositArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DEPOSIT_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDepositArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return adminBankDepositArrayList;
    }
    public ArrayList<AdminBankDeposit> getAllAdminBankDeposit() {
        ArrayList<AdminBankDeposit> adminBankDepositArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DEPOSIT_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositFull(adminBankDepositArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDepositArrayList;

    }
    public int getAllAdminDepositCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + null,
                null
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllAdminDepositCountDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

    }
    public int getAllAdminDepositCountForProfile(int profileID) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllAdminDepositCountForOffice(String officeBranch) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_OFFICE_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllAdminDepositCountForAdmin(String adminOfficer) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSITOR + "=?";
        String[] selectionArgs = new String[]{valueOf(adminOfficer)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public long saveNewAdminBalance(int id, int profileId, int customerID,int packageID,double amount, String date, String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ADMIN_BALANCE_ID, id);
        contentValues.put(ADMIN_EXPECTED_BALANCE, profileId);
        contentValues.put(ADMIN_RECEIVED_AMOUNT, amount);
        contentValues.put(ADMIN_BALANCE_DATE, date);
        contentValues.put(ADMIN_PROFILE_ID, profileId);
        contentValues.put(ADMIN_BALANCE_CUS_ID, customerID);
        contentValues.put(ADMIN_BALANCE_PACKID, packageID);
        contentValues.put(ADMIN_BALANCE_STATUS, packageID);
        db.insert(ADMIN_BALANCE_TABLE,null,contentValues);
        db.close();

        return id;
    }

    public long saveNewAdminDeposit(int depositID,int profileID,String depositor, String date,String office ,String bank, double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DEPOSIT_ID, depositID);
        cv.put(DEPOSIT_PROFILE_ID, profileID);
        cv.put(DEPOSIT_AMOUNT, amount);
        cv.put(DEPOSIT_OFFICE_BRANCH, office);
        cv.put(DEPOSIT_BANK, bank);
        cv.put(DEPOSIT_DATE, date);
        cv.put(DEPOSIT_TRANSACTION_STATUS, "Unverified");
        cv.put(DEPOSITOR, depositor);
        return db.insert(DEPOSIT_TABLE, null, cv);

    }


    public void saveNewAdminDeposit(AdminBankDeposit adminBankDeposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String tranxType=null;
        String bank=null;
        String date=null;
        int profileID=0;
        int officeID=0;
        int bizID=0;
        int acctNo=0;
        String depositor=null;
        double amount=0.00;
        String officeName=null;
        if(adminBankDeposit !=null){
            tranxType=adminBankDeposit.getDepositType();
            bank=adminBankDeposit.getDepositBank();
            date=adminBankDeposit.getDepositDate();
            profileID=adminBankDeposit.getDepositProfileID();
            officeID=adminBankDeposit.getDepositOfficeID();
            bizID=adminBankDeposit.getDepositBizID();
            depositor=adminBankDeposit.getDepositor();
            amount=adminBankDeposit.getDepositAmount();
            officeName=adminBankDeposit.getDepositOfficeBranch();
            acctNo=adminBankDeposit.getDepositAcctNo();
        }
        cv.put(DEPOSIT_TRANS_TYPE, tranxType);
        cv.put(DEPOSIT_PROFILE_ID, profileID);
        cv.put(DEPOSIT_AMOUNT, amount);
        cv.put(DEPOSIT_OFFICE_BRANCH, officeName);
        cv.put(DEPOSIT_BANK, bank);
        cv.put(DEPOSIT_OFFICE_ID22, officeID);
        cv.put(DEPOSIT_DATE, date);
        cv.put(DEPOSIT_BIZ_ID, bizID);
        cv.put(DEPOSITOR, depositor);
        cv.put(DEPOSIT_ACCOUNT, acctNo);
        db.insert(DEPOSIT_TABLE, null, cv);

    }
}
