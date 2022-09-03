package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static com.skylightapp.Classes.Loan.LOAN_ACCT_NO;
import static com.skylightapp.Classes.Loan.LOAN_AMOUNT;
import static com.skylightapp.Classes.Loan.LOAN_BALANCE;
import static com.skylightapp.Classes.Loan.LOAN_CODE;
import static com.skylightapp.Classes.Loan.LOAN_CUS_ID;
import static com.skylightapp.Classes.Loan.LOAN_DATE;
import static com.skylightapp.Classes.Loan.LOAN_DISPOSABLE_COM;
import static com.skylightapp.Classes.Loan.LOAN_DOWN_PAYMENT;
import static com.skylightapp.Classes.Loan.LOAN_END_DATE;
import static com.skylightapp.Classes.Loan.LOAN_FIXED_PAYMENT;
import static com.skylightapp.Classes.Loan.LOAN_ID;
import static com.skylightapp.Classes.Loan.LOAN_INTEREST;
import static com.skylightapp.Classes.Loan.LOAN_PACK_ID;
import static com.skylightapp.Classes.Loan.LOAN_PROF_ID;
import static com.skylightapp.Classes.Loan.LOAN_START_DATE;
import static com.skylightapp.Classes.Loan.LOAN_STATUS;
import static com.skylightapp.Classes.Loan.LOAN_TABLE;
import static com.skylightapp.Classes.Loan.LOAN_TOTAL_INTEREST;
import static com.skylightapp.Classes.Loan.LOAN_TYPE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static java.lang.String.valueOf;

public class LoanDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = LOAN_ID
            + " =?";
    public LoanDAO(Context context) {
        super(context);
    }
    public long saveNewLoan(int profile_ID, int cus_ID, int loan_ID,double loanInterest, double loanAmt, String loanDate, int acctNo, String typeFromAcct, long loanCode,String loanStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOAN_PROF_ID, profile_ID);
        cv.put(LOAN_CUS_ID, cus_ID);
        cv.put(LOAN_ID, loan_ID);
        cv.put(LOAN_ACCT_NO, acctNo);
        cv.put(LOAN_AMOUNT, loanAmt);
        cv.put(LOAN_TYPE, typeFromAcct);
        cv.put(LOAN_INTEREST, loanInterest);
        cv.put(LOAN_CODE, loanCode);
        cv.put(LOAN_DATE, loanDate);
        cv.put(LOAN_STATUS, loanStatus);
        return db.insert(LOAN_TABLE, null, cv);
    }
    public long insertNewLoan(int profile_ID, int cus_ID, int loan_ID,double loanInterest, double loanAmt, String loanDate, int acctNo, String typeFromAcct, long loanCode,String loanStatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOAN_PROF_ID, profile_ID);
        cv.put(LOAN_CUS_ID, cus_ID);
        cv.put(LOAN_ID, loan_ID);
        cv.put(LOAN_ACCT_NO, acctNo);
        cv.put(LOAN_AMOUNT, loanAmt);
        cv.put(LOAN_TYPE, typeFromAcct);
        cv.put(LOAN_INTEREST, loanInterest);
        cv.put(LOAN_CODE, loanCode);
        cv.put(LOAN_DATE, loanDate);
        cv.put(LOAN_STATUS, loanStatus);
        return db.insert(LOAN_TABLE, null, cv);

    }

    public long insertLoan13(int profileID, String address,String dob,int cusID, Loan loan) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOAN_PROF_ID, profileID);
        cv.put(LOAN_CUS_ID, cusID);
        cv.put(LOAN_ID, loan.getLoanId());
        cv.put(LOAN_AMOUNT, dob);
        cv.put(LOAN_INTEREST, address);
        cv.put(LOAN_FIXED_PAYMENT, valueOf(loan.getFixedPayment()));
        cv.put(LOAN_TOTAL_INTEREST, valueOf(loan.getTotalInterests()));
        cv.put(LOAN_DOWN_PAYMENT, valueOf(loan.getDownPayment()));
        cv.put(LOAN_DATE, loan.getLoan_date());
        cv.put(LOAN_START_DATE, loan.getLoan_startDate());
        cv.put(LOAN_END_DATE, loan.getLoan_endDate());
        cv.put(LOAN_STATUS, loan.getLoan_status());
        return sqLiteDatabase.insert(LOAN_TABLE, null, cv);
    }
    @SuppressLint("Recycle")
    public BigInteger getLoanSumValue() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum=0;
        //ArrayList<String> array_list = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT SUM(" + LOAN_AMOUNT + ") as Total FROM " + LOAN_TABLE, null);

        while (cursor.moveToNext()) {
            sum = cursor.getColumnIndex(LOAN_AMOUNT);
            return BigInteger.valueOf(sum);
        }
        return null;

    }
    public BigInteger getLoansSumValueForCustomer(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        //Cursor cur = db.rawQuery("SELECT SUM(" + (REPORT_TOTAL) + ") FROM " + DAILY_REPORT_TABLE, null);

        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ LOAN_AMOUNT +")) FROM " + LOAN_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(LOAN_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }
    public void UpdateLoanBalance(String amount, int loanID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            cVals.put(LOAN_BALANCE, amount);
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(loanID)};
            long result = db.update(LOAN_TABLE, cVals, selection, selectionArgs);
            Log.d("Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



    public void overwriteLoan1(Profile profile, Customer customer, Loan loan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(LOAN_PROF_ID, profile.getPID());
        cv.put(LOAN_CUS_ID, customer.getCusUID());
        cv.put(LOAN_AMOUNT, valueOf(profile.getProfileDob()));
        cv.put(LOAN_INTEREST, valueOf(profile.getProfileAddress()));
        cv.put(LOAN_FIXED_PAYMENT, valueOf(loan.getFixedPayment()));
        cv.put(LOAN_TOTAL_INTEREST, valueOf(loan.getTotalInterests()));
        cv.put(LOAN_DOWN_PAYMENT, valueOf(loan.getDownPayment()));
        cv.put(LOAN_DISPOSABLE_COM, valueOf(loan.getDisposableCommission()));
        cv.put(LOAN_BALANCE, valueOf(loan.getResiduePayment()));
        cv.put(LOAN_DATE, loan.getLoan_date());
        cv.put(LOAN_START_DATE, loan.getLoan_startDate());
        cv.put(LOAN_END_DATE, loan.getLoan_endDate());
        cv.put(LOAN_STATUS, loan.getLoan_status());
        String selection = LOAN_CUS_ID + "=? AND " + LOAN_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customer.getCusUID()), valueOf(loan.getLoanId())};

        db.update(LOAN_TABLE, cv, selection, selectionArgs);


    }


    public void delete_Loan_byID(int LOAN_ID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(LOAN_ID)};

            db.delete(LOAN_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateProfilePix(int profileID, int customerID, Uri photoUri) {


    }
    public long getLoanCode(int loanID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor=null;
        long loanCode=0;
        String selection = LOAN_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(loanID)};
        cursor = sqLiteDatabase.query(LOAN_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    loanCode=cursor.getColumnIndex(LOAN_CODE);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loanID;
    }

    public ArrayList<Loan> getAllLoansCustomer(int customerID) {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoanFromCursorAdmin(loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loanArrayList;
    }
    public ArrayList<Loan> getAllLoansProfile(int profileID) {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoanFromCursorAdmin(loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return loanArrayList;

    }
    public ArrayList<Loan> getAllLoansAdmin() {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(LOAN_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoanFromCursorAdmin(loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return loanArrayList;
    }

    private void getLoanFromCursorAdmin(ArrayList<Loan> loans, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int loanID = cursor.getInt(0);
                int customerID = cursor.getInt(1);
                int profileID = cursor.getInt(2);
                double loanAmount = cursor.getLong(5);
                double loanBalance = cursor.getLong(13);
                String loanDate = cursor.getString(14);
                String startDate = cursor.getString(15);
                String endDate = cursor.getString(16);
                String status = cursor.getString(17);
                loans.add(new Loan(profileID, customerID, loanID,loanAmount,loanDate, startDate, endDate, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<Loan> getLoansFromCurrentProfile(int profileID) {
        ArrayList<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        getLoansFromCursorCustomer(profileID);

        cursor.close();
        //db.close();

        return loans;

    }
    public ArrayList<Loan> getLoansFromCurrentCustomer(int customerID) {
        ArrayList<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoansFromCursorCustomer(customerID);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loans;
    }
    private void getLoansFromCursorCustomer(int customerID) {
        ArrayList<Loan> loans = new ArrayList<>();
        Cursor cursor = null;

        if (cursor != null) {
            while (cursor.moveToNext()) {

                if (customerID == cursor.getInt(1)) {
                    int id = cursor.getInt(1);
                    int loanId = cursor.getInt(0);
                    int loanType = cursor.getInt(4);
                    BigDecimal amount = BigDecimal.valueOf(cursor.getFloat(5));
                    BigDecimal interest = BigDecimal.valueOf(cursor.getFloat(7));
                    BigDecimal fixedPayment = BigDecimal.valueOf(cursor.getFloat(8));
                    BigDecimal totalInterests = BigDecimal.valueOf(cursor.getFloat(9));

                    BigDecimal downPayment = BigDecimal.valueOf(cursor.getFloat(10));
                    BigDecimal disposableCommission = BigDecimal.valueOf(cursor.getFloat(11));
                    BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(12));
                    BigDecimal residuePayment = BigDecimal.valueOf(cursor.getFloat(13));
                    String loanDate = valueOf(BigDecimal.valueOf(cursor.getFloat(14)));

                    String loanStartDate = cursor.getString(15);
                    String loanEndDate = cursor.getString(16);
                    String loanStatus = cursor.getString(17);


                    loans.add(new Loan(customerID, loanId, loanType, amount, interest, fixedPayment, totalInterests, downPayment, disposableCommission, monthlyCommission, residuePayment, loanDate, loanStartDate, loanEndDate, loanStatus));
                }
            }
        }


    }

    public ArrayList<Loan> getLoanFromCurrentProfile(int profileID) {
        ArrayList<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(LOAN_TABLE, null, selection,selectionArgs, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoansFromProfileCursor(profileID, loans, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loans;

    }


    private Cursor getLoansFromProfileCursor(int profileID, ArrayList<Loan> loanArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            if (profileID == cursor.getInt(2)) {
                int id = cursor.getInt(2);
                int loanId = cursor.getInt(0);
                int loanType = cursor.getInt(4);
                BigDecimal amount = BigDecimal.valueOf(cursor.getFloat(5));
                BigDecimal interest = BigDecimal.valueOf(cursor.getFloat(7));
                BigDecimal fixedPayment = BigDecimal.valueOf(cursor.getFloat(8));
                BigDecimal totalInterests = BigDecimal.valueOf(cursor.getFloat(9));

                BigDecimal downPayment = BigDecimal.valueOf(cursor.getFloat(10));
                BigDecimal disposableCommission = BigDecimal.valueOf(cursor.getFloat(11));
                BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(12));
                BigDecimal residuePayment = BigDecimal.valueOf(cursor.getFloat(13));
                String loanDate = valueOf(BigDecimal.valueOf(cursor.getFloat(14)));

                String loanStartDate = cursor.getString(15);
                String loanEndDate = cursor.getString(16);
                String loanStatus = cursor.getString(17);


                loanArrayList.add(new Loan(profileID, loanId, loanType, amount, interest, fixedPayment, totalInterests, downPayment, disposableCommission, monthlyCommission, residuePayment, loanDate, loanStartDate, loanEndDate, loanStatus));
            }
        }return cursor;

    }
    public int getLoansCountTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + LOAN_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public int getCustomerLoanCount(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerId)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + LOAN_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(LOAN_CUS_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }

    public int getLoansCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + LOAN_TABLE;
        Cursor cursor = null;

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


    public Cursor getLoanCursor(int loanID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LOAN_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(loanID)};

        return db.rawQuery(
                "SELECT * FROM " + LOAN_TABLE + " WHERE " + selection,
                selectionArgs
        );
    }
    public Cursor getProfileLoanCursor(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        return db.rawQuery(
                "SELECT * FROM " + LOAN_TABLE + " WHERE " + selection,
                selectionArgs
        );

    }
    public long insertNewLoan(int profileID, int customerID, int borrowingID, double amountToBorrow, String borrowingDate, String bankName, String bankAcctNo, String borrower, int accountNo, String loanAcctType, int loanOTP, double v, String pending) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOAN_PROF_ID, profileID);
        contentValues.put(LOAN_CUS_ID, customerID);
        contentValues.put(LOAN_ID, borrowingID);
        contentValues.put(LOAN_ACCT_NO, accountNo);
        contentValues.put(LOAN_AMOUNT, amountToBorrow);
        contentValues.put(LOAN_DATE, borrowingDate);
        contentValues.put(LOAN_CODE, loanOTP);
        contentValues.put(LOAN_INTEREST, 0.00);
        contentValues.put(LOAN_STATUS, pending);
        return sqLiteDatabase.insert(LOAN_TABLE, null, contentValues);
    }
    public ArrayList<Loan> getAllLoansFromProfile1(int profileID) {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoansFromProfileCursor(profileID, loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return loanArrayList;
    }

    public ArrayList<Loan> getLoansFromCustomer(int customerID) {
        Customer customer= new Customer();
        if(customer !=null){
            customerID=customer.getCusUID();
        }
        ArrayList<Loan> loans1 = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(LOAN_TABLE, null, selection,selectionArgs, null, null,
                null, null);
        getLoansFromCursorCustomer(customerID);

        cursor.close();
        db.close();

        return loans1;
    }


    protected Cursor getCusAllLoans(int userId) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(userId)};
        Cursor cursor = sqLiteDatabase.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);
        return sqLiteDatabase.rawQuery("SELECT * FROM LOAN_TABLE WHERE CUSTOMER_ID = ?",
                new String[]{valueOf(userId)});
    }
    public void overwriteLoan(int profileID,int loanID,int cusID,double amount,double loanBalance,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOAN_AMOUNT, amount);
        cv.put(LOAN_STATUS, status);
        cv.put(LOAN_BALANCE, loanBalance);
        String selection = LOAN_PROF_ID + "=? AND " + LOAN_ACCT_NO + "=? AND " + LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(loanID),valueOf(cusID)};
        db.update(LOAN_TABLE, cv, selection, selectionArgs);
        db.close();


    }

    public boolean updateLoan(String status, int loanId,double loanBalance) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOAN_BALANCE, loanBalance);
        contentValues.put(LOAN_STATUS, status);
        String selection = LOAN_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(loanId)};
        return sqLiteDatabase.update(LOAN_TABLE, contentValues, selection, selectionArgs)
                > 0;
    }

    private Loan getSpecificLoan(int loanId) {
        SQLiteDatabase db = getReadableDatabase();
        Loan loan = new Loan();
        String selection = LOAN_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(loanId)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if (cursor.moveToLast()) {
            if (cursor.getCount() > 0) {

                try {
                    loan.setLoanId(cursor.getInt(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loan.setLoanAcctName(cursor.getString(18));
                loan.setLoanBankAcctNo(cursor.getString(21));
                loan.setLoanBalance(cursor.getDouble(13));
                loan.setLoanAcctID(cursor.getInt(3));
                loan.setLoan_startDate(cursor.getString(15));
                loan.setLoan_endDate(cursor.getString(16));
                loan.setAmount1(cursor.getDouble(5));
                loan.setLoanDuration(cursor.getInt(6));
                loan.setInterest(BigDecimal.valueOf(cursor.getFloat(7)));
                cursor.close();
            }

            return loan;

        }else {
            Log.e("error! not found", "We could not find that Loan ");
            return loan;

        }
    }
    public Cursor getPackageLoans(int packageID) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String selection = LOAN_PACK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(packageID)};
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + LOAN_TABLE + " WHERE " +  selection,
                selectionArgs
        );

    }


}


