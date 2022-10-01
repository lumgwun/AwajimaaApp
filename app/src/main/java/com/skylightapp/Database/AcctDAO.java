package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_BALANCE;
import static com.skylightapp.Classes.Account.ACCOUNT_BANK;
import static com.skylightapp.Classes.Account.ACCOUNT_CUS_ID;
import static com.skylightapp.Classes.Account.ACCOUNT_NAME;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Account.ACCOUNT_PROF_ID;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPES_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE_ID;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE_INTEREST;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE_NAME;
import static com.skylightapp.Classes.Account.BANK_ACCT_BALANCE;
import static com.skylightapp.Classes.Account.BANK_ACCT_NO;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_ID;
import static java.lang.String.valueOf;

public class AcctDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = DOCUMENT_ID
            + " =?";
    public AcctDAO(Context context) {
        super(context);
    }
    public long insertAccount(Account account, int profileId, int customerId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String accountName = account.getAccountName();
        String accountBank = account.getBankName();
        int accountNumber = account.getAwajimaAcctNo();
        String accountBalance = valueOf(account.getAccountBalance());
        contentValues.put(ACCOUNT_PROF_ID, profileId);
        contentValues.put(ACCOUNT_NAME, accountName);
        contentValues.put(ACCOUNT_CUS_ID, customerId);
        contentValues.put(ACCOUNT_BANK, accountBank);
        contentValues.put(ACCOUNT_NO, accountNumber);
        contentValues.put(ACCOUNT_BALANCE, accountBalance);
        sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);

        return accountNumber;
    }
    protected Cursor getAccountDetails(int accountId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(accountId)};
        return sqLiteDatabase.rawQuery("SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + selection, selectionArgs);


    }

    protected BigDecimal getBalance(int accountId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        BigDecimal result = null;
        String selection = ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(accountId)};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT BALANCE FROM " + ACCOUNTS_TABLE + " WHERE " + selection, selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = new BigDecimal(cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();
        return result;
    }


    protected Cursor getAccountTypesId() {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT ID FROM ACCOUNT_TYPES_TABLE", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public boolean updateAccountName(String name, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_NAME, name);
        String selection = ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        return sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                selectionArgs) > 0;
    }
    public void updateAccBalance(int eWalletID, double newBalance) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_BALANCE, newBalance);
        String selection = ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(eWalletID)};
        sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                selectionArgs);


    }

    public boolean updateAccountBalance(BigDecimal balance, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_BALANCE, balance.toPlainString());
        String selection = ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        return sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                selectionArgs) > 0;
    }

    public boolean updateAccountType(String typeId, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_TYPE, typeId);
        String selection = ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        return sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                selectionArgs) > 0;
    }
    public boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_TYPE_INTEREST, interestRate.toPlainString());
        String selection = ACCOUNT_TYPE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        return sqLiteDatabase.update(ACCOUNT_TYPES_TABLE, contentValues, selection,
                selectionArgs)>0;
    }

    public ArrayList<Account> getAccountsFromCurrentCustomer(int customerID) {
        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(ACCOUNTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getAccountsFromCursor(accounts, cursor);

        cursor.close();
        //db.close();

        return accounts;
    }
    public ArrayList<Account> getAccountsFromCurrentProfile(int profileID) {
        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ACCOUNT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        @SuppressLint("Recycle") Cursor cursor = db.query(ACCOUNTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getAccountsFromCursor(accounts, cursor);

        cursor.close();
        db.close();

        return accounts;

    }

    public Cursor getAccountIds(int cusId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String selection = ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(cusId)};
        return sqLiteDatabase.rawQuery("SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + selection, selectionArgs);


    }

    private void getAccountsFromCursor(ArrayList<Account> accounts, Cursor cursor) {
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String accountNo = cursor.getString(1);
            String accountType = cursor.getString(5);
            String accountBank = cursor.getString(6);
            String accountName = cursor.getString(7);
            double accountBalance = cursor.getDouble(8);
            accounts.add(new Account(id, accountBank, accountName, accountNo, accountBalance, AccountTypes.valueOf(accountType)));
        }


    }
    public ArrayList<Account> getAccountsForCustomer(int customerID) {
        Customer customer= new Customer();
        if(customer !=null){
            customerID=customer.getCusUID();
        }

        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(ACCOUNTS_TABLE, null, selection,selectionArgs, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAccountsFromCurrentCustomer(customerID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return accounts;
    }


    public void overwriteAccount(Profile profile, Customer customer, Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
        cv.put(BANK_ACCT_BALANCE, account.getBankAccountBalance());
        String selection = ACCOUNT_PROF_ID + "=? AND " + ACCOUNT_NO + "=? AND " + ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profile.getPID()), valueOf(account.getAwajimaAcctNo()),valueOf(customer.getCusUID())};
        db.update(ACCOUNTS_TABLE, cv, selection,
                selectionArgs);
        db.close();

    }

    public void overwriteAccount(Profile userProfile, Account account,int cusID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Customer customer = new Customer();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_NAME, account.getAccountName());
        cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
        cv.put(BANK_ACCT_BALANCE, account.getBankAccountBalance());
        String selection = ACCOUNT_PROF_ID + "=? AND " + ACCOUNT_NO + "=? AND " + ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(userProfile.getPID()), valueOf(account.getAwajimaAcctNo()),valueOf(cusID)};

        db.update(ACCOUNTS_TABLE, cv, selection,
                selectionArgs);
        db.close();


    }
    public void overwriteAccount1(int acctID,int cusID,double balance,double bankAcctBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        Customer customer = new Customer();
        Account account = customer.getCusAccount();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_NO, acctID);
        cv.put(ACCOUNT_BALANCE, balance);
        cv.put(BANK_ACCT_BALANCE, bankAcctBalance);
        String selection = ACCOUNT_CUS_ID + "=? AND " + ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(cusID), valueOf(acctID)};

        db.update(ACCOUNTS_TABLE, cv, selection,
                selectionArgs);
        //db.close();



    }
    public long saveNewAccount1(String accountBank, String accountName, int accountNumber, double accountBalance, AccountTypes accountTypes) {


        SQLiteDatabase db = this.getWritableDatabase();
        Profile profile = new Profile();

        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_PROF_ID, profile.getPID());
        cv.put(ACCOUNT_BANK, accountBank);
        cv.put(ACCOUNT_NO, accountNumber);
        cv.put(ACCOUNT_NAME, accountName);
        cv.put(ACCOUNT_TYPE_NAME, String.valueOf(accountTypes));
        cv.put(ACCOUNT_BALANCE, accountBalance);
        db.insert(ACCOUNTS_TABLE, null, cv);


        return accountNumber;
    }
    public long insertAccountType(long number, AccountTypes name, BigDecimal interestRate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_TYPE_ID, number);
        contentValues.put(ACCOUNT_TYPE_NAME, String.valueOf(name));
        contentValues.put(ACCOUNT_TYPE_INTEREST, interestRate.toPlainString());
        return sqLiteDatabase.insert(ACCOUNT_TYPES_TABLE, null, contentValues);

    }
    public long insertAccount(int profileID2, int customerID1, String skylightMFb, String accountName, long accountNumber, double accountBalance, AccountTypes accountTypeStr) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_PROF_ID, profileID2);
        contentValues.put(ACCOUNT_CUS_ID, customerID1);
        contentValues.put(ACCOUNT_NAME, accountName);
        contentValues.put(ACCOUNT_NO, accountNumber);
        contentValues.put(ACCOUNT_BANK, skylightMFb);
        contentValues.put(BANK_ACCT_NO, "");
        contentValues.put(ACCOUNT_BALANCE, accountBalance);
        contentValues.put(ACCOUNT_TYPE_NAME, String.valueOf(accountTypeStr));
        return  sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);

    }

    protected long insertUserAccount(int profID, int accountId,int cusID,String bank,double skylightBalance,double bankBalance,String bankAcctNo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_PROF_ID, profID);
        contentValues.put(ACCOUNT_CUS_ID, cusID);
        contentValues.put(ACCOUNT_NAME, accountId);
        contentValues.put(ACCOUNT_BANK, bank);
        contentValues.put(BANK_ACCT_NO, bankAcctNo);
        contentValues.put(BANK_ACCT_BALANCE, bankBalance);
        contentValues.put(ACCOUNT_NO, accountId);
        contentValues.put(ACCOUNT_BALANCE, skylightBalance);
        return sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);

    }
    public void UpdateAccountBalanceStatus(String balance, int accountID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            cVals.put(ACCOUNT_BALANCE, balance);
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(accountID)};
            long result = db.update(ACCOUNTS_TABLE, cVals, selection, selectionArgs);
            Log.d("Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }





    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Profile profile = new Profile();
        long profileID = profile.getPID();
        Cursor cursor = db.query(ACCOUNTS_TABLE, null, null, null, null,
                null, null);
        getAccountsFromCursor(accounts, cursor);
        cursor.close();
        //db.close();

        return accounts;
    }


}
