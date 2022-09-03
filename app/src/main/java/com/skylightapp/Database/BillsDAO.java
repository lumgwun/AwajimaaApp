package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Transactions.BillModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_PHONE_NUMBER;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PIC_ID;
import static com.skylightapp.Database.DBHelper.BILL_ID_WITH_PREFIX;
import static com.skylightapp.Transactions.BillModel.BILLER_CODE;
import static com.skylightapp.Transactions.BillModel.BILL_ACCT_ID;
import static com.skylightapp.Transactions.BillModel.BILL_AMOUNT;
import static com.skylightapp.Transactions.BillModel.BILL_COUNTRY;
import static com.skylightapp.Transactions.BillModel.BILL_CURRENCY;
import static com.skylightapp.Transactions.BillModel.BILL_CUSTOMER_ID;
import static com.skylightapp.Transactions.BillModel.BILL_DATE;
import static com.skylightapp.Transactions.BillModel.BILL_ID;
import static com.skylightapp.Transactions.BillModel.BILL_ITEM_CODE;
import static com.skylightapp.Transactions.BillModel.BILL_NAME;
import static com.skylightapp.Transactions.BillModel.BILL_PROF_ID;
import static com.skylightapp.Transactions.BillModel.BILL_RECURRING_TYPE;
import static com.skylightapp.Transactions.BillModel.BILL_REF;
import static com.skylightapp.Transactions.BillModel.BILL_STATUS;
import static com.skylightapp.Transactions.BillModel.BILL_TABLE;
import static java.lang.String.valueOf;

public class BillsDAO extends DBHelperDAO{
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private static final String WHERE_ID_EQUALS = PROFILE_PIC_ID
            + " =?";
    public BillsDAO(Context context) {
        super(context);
    }

    public boolean updateBillStatus(long billID, String billStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BILL_STATUS, billStatus);
            db.update(BILL_TABLE, contentValues, "BILL_ID = ? ", new String[]{Long.toString(billID)});
            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public ArrayList<BillModel> getBills() {
        try {
            ArrayList<BillModel> bills = new ArrayList<BillModel>();
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            SQLiteDatabase db = this.getReadableDatabase();
            queryBuilder
                    .setTables(BILL_TABLE
                            + " INNER JOIN "
                            + CUSTOMER_TABLE
                            + " ON "
                            + BILL_CUSTOMER_ID
                            + " = "
                            + (CUSTOMER_TABLE + "." + CUSTOMER_ID));

            // Get cursor

            Cursor cursor = queryBuilder.query(db, new String[] {
                            BILL_ID_WITH_PREFIX,
                            BILL_TABLE + "."
                                    + BILL_NAME,
                            BILL_AMOUNT,
                            BILL_CURRENCY,
                            BILL_DATE,
                            BILL_CUSTOMER_ID,
                            CUSTOMER_TABLE + "."
                                    + CUSTOMER_PHONE_NUMBER }, null, null, null, null,
                    null);

            while (cursor.moveToNext()) {
                BillModel bill = new BillModel();
                bill.setId(cursor.getInt(0));
                bill.setName(cursor.getString(1));
                bill.setAmount(cursor.getInt(2));
                bill.setCurrency(cursor.getString(3));
                bill.setName(cursor.getString(4));
                //bill.setBillDate(cursor.getString(5));
                bill.setBillStatus(cursor.getString(6));
                try {
                    bill.setBillDate(formatter.parse(cursor.getString(5)));
                } catch (ParseException e) {
                    bill.setBillDate((Date) null);
                }
                Customer customer = new Customer();
                customer.setCusUID(cursor.getInt(4));
                customer.setCusPhoneNumber(cursor.getString(5));
                bill.setCustomer(customer);

                bills.add(bill);
            }
            return bills;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public void deleteBill(BillModel billModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(billModel !=null){
            long billId= billModel.getId();
            db.delete(BILL_TABLE,
                    "BILL_ID = ? ",
                    new String[]{Long.toString(billId)});
        }


    }
    public long insertBills(int bill_ID,  long PROFILE_ID, long CUSTOMER_ID, long accountNo,String billName, double amount, String currency, Date billDate, String billCountry, String recurringType,  String billRef, String itemCode,String billerCode) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //double packageCount =customerDailyReport.getTotalSavingsForPackage(investment_ID)% 31;
        contentValues.put(BILL_PROF_ID, PROFILE_ID);
        contentValues.put(String.valueOf(BILL_ID), bill_ID);
        contentValues.put(BILL_CUSTOMER_ID, CUSTOMER_ID);
        contentValues.put(BILL_ACCT_ID, accountNo);
        contentValues.put(BILL_NAME, billName);
        contentValues.put(BILL_AMOUNT, amount);
        contentValues.put(BILL_CURRENCY, valueOf(currency));
        contentValues.put(BILL_DATE, String.valueOf(billDate));
        contentValues.put(String.valueOf(BILL_COUNTRY), billCountry);
        contentValues.put(String.valueOf(BILL_RECURRING_TYPE), recurringType);
        contentValues.put(String.valueOf(BILL_REF), billRef);
        contentValues.put(String.valueOf(BILL_ITEM_CODE), itemCode);
        contentValues.put(BILLER_CODE, billerCode);
        contentValues.put(BILL_STATUS, "Being processed");
        sqLiteDatabase.insert(BILL_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return bill_ID;
    }

    public int getCustomerBillCount(long customerId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + BILL_TABLE + " WHERE " + CUSTOMER_ID + "=?",
                    new String[]{valueOf(customerId)}
            );
            int count = 0;
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
            if (cursor != null) {
                cursor.close();
            }
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getProfileBillCount(long profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + BILL_TABLE + " WHERE " + PROFILE_ID + "=?",
                    new String[]{valueOf(profileID)}
            );
            int count = 0;
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
            if (cursor != null) {
                cursor.close();
            }
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public Cursor getBillsFromCustomerCursor(long customerID, ArrayList<BillModel> bills, Cursor cursor) {
        while (cursor.moveToNext()) {

            if (customerID == cursor.getLong(2)) {
                long billID = cursor.getLong(0);
                String billName = cursor.getString(4);
                Integer billAmount = cursor.getInt(5);
                String billCurrency = cursor.getString(6);
                //BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(LOAN_MONTHLY_COLUMN));
                String billDate = cursor.getString(7);
                String billFreq = cursor.getString(9);
                String billStatus = cursor.getString(13);

                bills.add(new BillModel(billID,customerID, billName, billAmount,billCurrency,billDate,billFreq,billStatus));
            }
        }return  cursor;
    }

    public void updateBill(long billID,String recurrenceType,String newStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Customer customer = new Customer();
            BillModel bill = new BillModel();
            ContentValues cv = new ContentValues();
            cv.put(BILL_ID, valueOf(billID));
            cv.put(BILL_STATUS, newStatus);
            cv.put(BILL_REF, bill.getReference());
            cv.put(BILL_RECURRING_TYPE, recurrenceType);
            String selection = BILL_ID + "=? AND " + BILL_REF + "=?";
            String[] selectionArgs = new String[]{valueOf(billID), valueOf(newStatus)};

            db.update(BILL_TABLE, cv, selection,
                    selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }



    public int getBillsCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String countQuery = "SELECT  * FROM " + BILL_TABLE;
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getBillsCountCustomer(long customerID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + BILL_TABLE + " WHERE " + selection, selectionArgs
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public Cursor getBillsFromAdminCursor(ArrayList<BillModel> billModels, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                long billID = cursor.getLong(0);
                long customerID = cursor.getLong(2);
                String billName = cursor.getString(4);
                Integer billAmount = cursor.getInt(5);
                String billCurrency = cursor.getString(6);
                //BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(LOAN_MONTHLY_COLUMN));
                String billDate = cursor.getString(7);
                String billFreq = cursor.getString(9);
                String billStatus = cursor.getString(13);

                billModels.add(new BillModel(billID,customerID, billName, billAmount,billCurrency,billDate,billFreq,billStatus));

            }return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return cursor;
    }

    public ArrayList<BillModel> getAllBillsAdmin() {
        try {
            ArrayList<BillModel> bills = new ArrayList<>();
            Profile profile = new Profile();
            long profileID = profile.getPID();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(BILL_TABLE, null, null, null, null,
                    null, null);
            getBillsFromAdminCursor(bills, cursor);

            cursor.close();
            db.close();

            return bills;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public int getBillCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String countQuery = "SELECT  * FROM " + BILL_TABLE;
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

}
