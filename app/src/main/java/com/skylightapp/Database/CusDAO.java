package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.Classes.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.skylightapp.Classes.Customer.CUSTOMER_ADDRESS;
import static com.skylightapp.Classes.Customer.CUSTOMER_DATE_JOINED;
import static com.skylightapp.Classes.Customer.CUSTOMER_EMAIL_ADDRESS;
import static com.skylightapp.Classes.Customer.CUSTOMER_FIRST_NAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_GENDER;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_OFFICE;
import static com.skylightapp.Classes.Customer.CUSTOMER_PASSWORD;
import static com.skylightapp.Classes.Customer.CUSTOMER_PHONE_NUMBER;
import static com.skylightapp.Classes.Customer.CUSTOMER_PROF_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_SURNAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_USER_NAME;
import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_PROF_ID_FK;
import static com.skylightapp.Classes.Loan.LOAN_PROF_ID;
import static com.skylightapp.Classes.Loan.LOAN_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_PROF_ID;
import static com.skylightapp.Classes.Message.MESSAGE_TABLE;
import static com.skylightapp.Classes.PaymentCode.CODE_PROFILE_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROF_ID_FOREIGN_KEY_PASSWORD;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_CUSTOMER_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_PROFILE_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TYPE;
import static com.skylightapp.Classes.StandingOrder.SO_CUS_ID;
import static com.skylightapp.Classes.StandingOrder.STANDING_ORDER_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PROF_ID;
import static java.lang.String.valueOf;

public class CusDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CUSTOMER_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public CusDAO(Context context) {
        super(context);
    }
    public ArrayList<Customer> getCustomersFromCurrentProfile(int profileID) {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(CUSTOMER_TABLE, null,  selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getCustomerFromCursor(customerArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        cursor.close();

        return customerArrayList;
    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getChartCustomers(String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"COUNT(" + CUSTOMER_ID + ") AS " + tmpcol_monthly_total, "substr(" + CUSTOMER_DATE_JOINED + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",4)";
        String orderbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",7,2)||substr(" + CUSTOMER_DATE_JOINED + ",4,2)";
        Cursor cursor = db.query(CUSTOMER_TABLE, columns, null,
                null, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return dataList;

    }



    public ArrayList<Customer> getAllCustomers11() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getCustomerFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return customers;
    }

    public int getTellerMonthCusCountNew(int profileID,String yearMonth) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String queryString="SELECT COUNT (*) FROM " + CUSTOMER_TABLE ;
        String selection = "substr(" + CUSTOMER_DATE_JOINED + ",4)" + "=? AND " + PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(yearMonth), valueOf(profileID)};

        String tmpcol_monthly_total = "Monthly_Count";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"COUNT(" + CUSTOMER_ID + ") AS " + tmpcol_monthly_total, "substr(" + CUSTOMER_DATE_JOINED + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",4)";
        String orderbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",7,2)||substr(" + CUSTOMER_DATE_JOINED + ",4,2)";
        @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(CUSTOMER_PHONE_NUMBER);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;

    }
    public int getNewCustomersCountForTodayTeller(int profileID,String today) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CUSTOMER_PROF_ID + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getNewCustomersCountForTodayOffice(String  office,String today) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CUSTOMER_OFFICE + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getCusCountTodayForTeller1(int tellerID, String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = CUSTOMER_PROF_ID + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public String getCustomerNames(int cusID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = CUSTOMER_ID + "=?";
        String name=null;
        String[] selectionArgs = new String[]{valueOf(cusID)};
        Cursor cursor = db.query(CUSTOMER_TABLE, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    name=cursor.getColumnName(4);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return name;
    }
    public Cursor getCustomersFromPackageCursor(ArrayList<Customer> customers, Cursor cursor) {
        while (cursor.moveToNext()) {
            int customerID = cursor.getInt((0));
            String surname = cursor.getString(3);
            String firstName = cursor.getString(4);
            String phoneNumber = cursor.getString(5);
            String emailAddress = cursor.getString(6);
            String nin = cursor.getString(7);
            String dob = cursor.getString(8);
            String gender = cursor.getString(9);
            String address = cursor.getString(10);
            String username = cursor.getString(11);
            String password = cursor.getString(12);
            String office = cursor.getString(13);
            String joinedDate = cursor.getString(14);
            String status = cursor.getString(15);
            String names = surname +","+firstName;





            customers.add(new Customer(customerID, names, phoneNumber, emailAddress, address, office));

        }
        return cursor;
    }
    public ArrayList<Customer> getCusForItemsPurchase(String items) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                + " WHERE " + PACKAGE_TYPE + " = " +  items;
        Cursor cursor = db.rawQuery(rawQuery,
                null);
        getCustomersFromPackageCursor(customers, cursor);
        cursor.close();
        db.close();

        return customers;


    }
    public ArrayList<Customer> getCusForSavings(String savings) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                + " WHERE " + PACKAGE_TYPE + " = " +  savings;
        Cursor cursor = db.rawQuery(rawQuery,
                null);
        getCustomersFromPackageCursor(customers, cursor);
        cursor.close();
        db.close();

        return customers;
    }
    public ArrayList<Customer> getCusWithoutPackage() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                + " WHERE " + PACKAGE_TYPE + " = ?" +  "";

        Cursor cursor = db.rawQuery(rawQuery,
                null);
        getCustomersFromPackageCursor(customers, cursor);
        cursor.close();
        db.close();

        return customers;
    }
    public ArrayList<Customer> getCusForInvestment(String investment) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                + " WHERE " + PACKAGE_TYPE + " = " +  investment;
        Cursor cursor = db.rawQuery(rawQuery,
                null);
        getCustomersFromPackageCursor(customers, cursor);
        cursor.close();
        db.close();

        return customers;
    }
    public ArrayList<Customer> getCusForPromo(String promo) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                + " WHERE " + PACKAGE_TYPE + " = " +  promo;
        Cursor cursor = db.rawQuery(rawQuery,
                null);
        getCustomersFromPackageCursor(customers, cursor);
        cursor.close();
        db.close();

        return customers;

    }
    private Customer getCustomer(String phoneNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Customer u = new Customer();

        @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TABLE,
                null,
                CUSTOMER_PHONE_NUMBER + "=?",
                new String[]{String.valueOf(phoneNumber)},
                null,
                null,
                null);
        if (cursor.moveToLast()) {
            u.setCusUserName(cursor.getString(1));
            u.setCusSurname(cursor.getString(2));
            u.setCusFirstName(cursor.getString(3));
            u.setCusPhoneNumber(cursor.getString(4));
            u.setCusEmail(cursor.getString(5));
            u.setCusGender(cursor.getString(6));
            u.setCusOffice(cursor.getString(7));
            u.setCusDob(cursor.getString(8));
            u.setCusAddress(cursor.getString(9));
            return u;

        }else {
            Log.e("error! not found", "We could not find that User ");
            return u;

        }


    }
    public int deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        return db.delete(CUSTOMER_TABLE, selection,selectionArgs);

    }
    public void deleteCustomer(String customerPhoneNumber) {
        try {
            boolean result = false;

            String selection = "customerPhoneNumber = \"" + customerPhoneNumber + "\"";

            int rowsDeleted = myCR.delete(UserContentProvider.CONTENT_URI,
                    selection, null);

            if (rowsDeleted > 0)
                result = true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }




    public int getCusCountToday(String today) {

        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return count;

    }

    public int getNewCustomersCountForToday(String today) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getAllNewCusCountForToday(String joinedDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(joinedDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        return count;
    }
    public int getTellerMonthCusCount1( int tellerID,String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;


        String selection = "substr(" + CUSTOMER_DATE_JOINED + ",4)" + "=? AND " + CUSTOMER_PROF_ID + "=?";

        String queryString="select COUNT ("+ CUSTOMER_ID +") from " + CUSTOMER_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(tellerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }




    private void getCustomerFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String lastName = cursor.getString(3);
            String firstName = cursor.getString(4);
            String phoneNumber = cursor.getString(5);
            String email = cursor.getString(6);
            String nin = cursor.getString(7);
            String dob = cursor.getString(8);
            String gender = cursor.getString(9);
            String address = cursor.getString(10);
            String username = cursor.getString(11);
            String password = cursor.getString(12);
            String office = cursor.getString(13);
            String joinedDate = cursor.getString(14);
            String status = cursor.getString(15);

            customers.add(new Customer(id, firstName, lastName, phoneNumber, email, address, nin,gender, dob, username, password, office, joinedDate));
        }

    }
    public ArrayList<Customer> getAllCustomerUsers() {
        ArrayList<Customer> userModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
        //db.close();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setCusUID(Integer.parseInt(c.getString(0)));
                customer.setCusSurname(c.getString(3));
                customer.setCusFirstName(c.getString(4));
                customer.setCusPhoneNumber(c.getString(5));
                customer.setCusEmail(c.getString(6));
                customer.setCusDateJoined(c.getString(14));
                customer.setCusUserName(c.getString(11));
                customer.setCusPin(c.getString(12));
                customer.setCusOffice(c.getString(13));
                customer.setCusGender(c.getString(9));

                userModelArrayList.add(customer);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }
    public ArrayList<Customer> getCustomerFromMachine(String Customer) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{Customer};

        Cursor cursor = db.query(PROFILES_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return customers;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAllCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;
    }
    public ArrayList<Customer> getAllCustomersNames() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;

    }
    public ArrayList<Customer> getCustomerFromCurrentProfile(int profileID) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileID)};

        Cursor cursor = db.query(
                CUSTOMER_TABLE + " , " + PROFILES_TABLE,
                Utils.concat(new String[]{CUSTOMER_TABLE, PROFILES_TABLE}),
                CUSTOMER_PROF_ID + " = " + PROFILE_ID + " AND " + PROFILE_ID + " = " +  profileID ,
                null, PROFILE_ID, null, PROFILE_ID);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;
    }
    public int getCustomersCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;

        String countQuery = "SELECT  * FROM " + CUSTOMER_TABLE;
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
    public int getCustomersCountTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
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
    public Cursor getSpinnerCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        while (cursor.moveToNext()) {

            int customerID = cursor.getInt(0);
            String surname = cursor.getString(3);
            String firstName = cursor.getString(4);
            customers.add(new Customer(customerID, surname, firstName));

        }
        return cursor;
    }

    public ArrayList<Customer> getAllCustomerSpinner() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSpinnerCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return customers;
    }

    public ArrayList<Customer> getAllCustomerBranchSpinner(String branchOffice) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CUSTOMER_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchOffice)};

        @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSpinnerCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;
    }
    public Cursor getCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        while (cursor.moveToNext()) {

            int customerID = cursor.getInt(0);
            String surname = cursor.getString(3);
            String firstName = cursor.getString(4);
            String phoneNumber = cursor.getString(5);
            String emailAddress = cursor.getString(6);
            String nin = cursor.getString(7);
            String dob = cursor.getString(8);
            String gender = cursor.getString(9);
            String address = cursor.getString(10);
            String userName = cursor.getString(11);
            String password = cursor.getString(12);
            String office = cursor.getString(13);
            String dateJoined = cursor.getString(14);

            customers.add(new Customer(customerID, surname, firstName, phoneNumber, emailAddress,nin, dob, gender, address, userName, password, office, dateJoined));

        }
        return cursor;
    }

    public ArrayList<Customer> getAllCusForSo() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT * FROM " + STANDING_ORDER_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                + " ON " + SO_CUS_ID + " = " + CUSTOMER_ID;
        Cursor cursor = db.rawQuery(rawQuery,
                null);
        getCustomerFromCursor(customers, cursor);
        cursor.close();
        db.close();

        return customers;
    }






    public Cursor getSimpleCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        while (cursor.moveToNext()) {
            String surname = cursor.getString(3);
            String firstName = cursor.getString(4);
            customers.add(new Customer(surname, firstName));
        }
        return cursor;
    }

    public ArrayList<Customer> getCustomersFromCurrentCustomer(int customerID) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(customerID)};

        Cursor cursor = db.query(CUSTOMER_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getAllCustomersFromCursor(customers, cursor);
                cursor.close();
            }

        db.close();


        return customers;

    }

    public Cursor getAllCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        while (cursor.moveToNext()) {
            int customerID = cursor.getInt(0);
            String surname = cursor.getString(3);
            String firstName = cursor.getString(4);
            String phoneNumber = cursor.getString(5);
            String emailAddress = cursor.getString(6);
            String nin = cursor.getString(7);
            String dob = cursor.getString(8);
            String gender = cursor.getString(9);
            String address = cursor.getString(10);
            String userName = cursor.getString(11);
            String password = cursor.getString(12);
            String office = cursor.getString(13);
            String dateJoined = cursor.getString(14);
            customers.add(new Customer(customerID, surname, firstName, phoneNumber, emailAddress, nin,dob, gender, address, userName, password, office, dateJoined));
        }
        return cursor;

    }
    public ArrayList<String> getCustomer() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from CUSTOMER_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(0));
                res.moveToNext();
            }
        }
        return array_list;
    }
    public ArrayList<String> getCustomerName() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from CUSTOMER_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(4));
                res.moveToNext();
            }
        }
        return array_list;
    }


    public Customer findCustomer(String phoneNumber) {
        String[] projection = {CUSTOMER_ID,
                CUSTOMER_FIRST_NAME, CUSTOMER_SURNAME, CUSTOMER_PHONE_NUMBER, CUSTOMER_USER_NAME, CUSTOMER_DATE_JOINED, CUSTOMER_ADDRESS};

        String selection = "CUSTOMER_PHONE_NUMBER = \"" + phoneNumber + "\"";

        Cursor cursor = myCR.query(UserContentProvider.CONTENT_URI,
                projection, selection, null,
                null);

        Customer customer = new Customer();

        assert cursor != null;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            customer.setCusUID(Integer.parseInt(cursor.getString(0)));
            customer.setCusFirstName(cursor.getString(1));
            customer.setCusSurname(cursor.getString(2));
            customer.setCusPhoneNumber(cursor.getString(3));
            customer.setCusUserName(cursor.getString(4));
            customer.setCusDateJoined(cursor.getString(5));
            customer.setCusAddress(cursor.getString(6));
            cursor.close();
        } else {
            customer = null;
        }
        return customer;
    }
    public boolean updateCustomerUserName(String userName, int customerID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_USER_NAME, userName);
        String selection = CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(customerID)};

        return sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, selection, selectionArgs)
                > 0;
    }
    public boolean updateCustomer(int id,String lastName, String firstName,String phoneNo,String email,String gender,String address,String office,String userName,String password,String role) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_SURNAME, lastName);
        contentValues.put(CUSTOMER_FIRST_NAME, firstName);
        contentValues.put(CUSTOMER_USER_NAME, userName);
        contentValues.put(CUSTOMER_PHONE_NUMBER, phoneNo);
        contentValues.put(CUSTOMER_EMAIL_ADDRESS, email);
        contentValues.put(CUSTOMER_ADDRESS, address);
        contentValues.put(CUSTOMER_PASSWORD, password);
        contentValues.put(CUSTOMER_GENDER, gender);
        contentValues.put(CUSTOMER_OFFICE, office);
        return sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, "CUSTOMER_ID = ?", new String[]{valueOf(id)})
                > 0;
    }
    public void updateCustomer1(int customerID,String lastName, String firstName,String phoneNo,String email,String gender,String address,String office,String userName,String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_SURNAME, lastName);
        contentValues.put(CUSTOMER_FIRST_NAME, firstName);
        contentValues.put(CUSTOMER_USER_NAME, userName);
        contentValues.put(CUSTOMER_PHONE_NUMBER, phoneNo);
        contentValues.put(CUSTOMER_EMAIL_ADDRESS, email);
        contentValues.put(CUSTOMER_ADDRESS, address);
        contentValues.put(CUSTOMER_PASSWORD, password);
        contentValues.put(CUSTOMER_GENDER, gender);
        contentValues.put(CUSTOMER_OFFICE, office);
        sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, "CUSTOMER_ID = ?", new String[]{valueOf(customerID)});


    }

    public boolean updateCustomerPassword(String password, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_PASSWORD, password);
        String selection = CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, selection, selectionArgs)
                > 0;
    }
    public String getPassword1(int profileId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String result=null;
        String selection = PROF_ID_FOREIGN_KEY_PASSWORD + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileId)};
        Cursor cursor = sqLiteDatabase.query(CUSTOMER_TABLE, null,
                selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(2);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return result;
    }



    public Cursor queueAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{CUSTOMER_ID, CUSTOMER_USER_NAME, CUSTOMER_PHONE_NUMBER, CUSTOMER_FIRST_NAME, CUSTOMER_EMAIL_ADDRESS, CUSTOMER_SURNAME};
        Cursor cursor = db.query(CUSTOMER_TABLE, columns,
                null, null, CUSTOMER_PHONE_NUMBER, null, null);

        return cursor;

    }
    public int getCustomerCount1() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = CUSTOMER_ID + "=?";
            //String[] selectionArgs = new String[]{valueOf(customerId)};
            int count = 0;
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + null,
                    null
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public ArrayList<Customer> getCustomersFromProfileWithDate(int profileID,String date) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CUSTOMER_SURNAME,CUSTOMER_FIRST_NAME,CUSTOMER_OFFICE};
        String selection = CUSTOMER_PROF_ID + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
        Cursor cursor = db.query(CUSTOMER_TABLE, columns, selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getCustomerFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return customers;
    }
    public ArrayList<Customer> getCustomersToday(String todayDate) {
        Customer customer = new Customer();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf.format(calendar.getTime().getDate());
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CUSTOMER_SURNAME,CUSTOMER_FIRST_NAME,CUSTOMER_OFFICE};
        String selection = CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{todayDate};
        Cursor cursor = db.query(CUSTOMER_TABLE, columns, selection, selectionArgs, null, null, null);
        getCustomerFromCursor(customers, cursor);
        cursor.close();
        db.close();
        return customers;
    }
    public void overwriteCustomer1(int customerID,int profileID, Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_SURNAME, customer.getCusSurname());
        cv.put(CUSTOMER_FIRST_NAME, customer.getCusFirstName());
        cv.put(CUSTOMER_PHONE_NUMBER, customer.getCusPhoneNumber());
        cv.put(CUSTOMER_EMAIL_ADDRESS, customer.getCusEmail());
        cv.put(CUSTOMER_ADDRESS, valueOf(customer.getCusAddress()));
        cv.put(CUSTOMER_OFFICE, customer.getCusOfficeBranch());
        cv.put(CUSTOMER_USER_NAME, customer.getCusUserName());
        cv.put(CUSTOMER_PASSWORD, customer.getCusPassword());
        String selection = CUSTOMER_ID + "=? AND " + CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(profileID)};
        db.update(CUSTOMER_TABLE, cv, selection, selectionArgs);
        db.close();

    }
    public void delete_Customer_byID(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};

            db.delete(CUSTOMER_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public Cursor getCustomerCursor(int customerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        return db.rawQuery(
                "SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
    }


    public Cursor queueAll_SortBy_PhoneNumber_And_Id() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{PROFILE_ID, CUSTOMER_PHONE_NUMBER, CUSTOMER_ID};

        return db.query(CUSTOMER_TABLE, columns,
                null, null, null, CUSTOMER_PHONE_NUMBER, CUSTOMER_ID);

    }

    public Cursor queueAll_SortBy_Office_And_Gender() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{PROFILE_ID, CUSTOMER_OFFICE, CUSTOMER_GENDER};
        Cursor cursor = db.query(CUSTOMER_TABLE, columns,
                null, null, null, CUSTOMER_OFFICE, CUSTOMER_OFFICE);

        return cursor;
    }
    public void updateCustomerUserNamePassword(int customerId, String userName, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_PASSWORD, password);
        contentValues.put(CUSTOMER_USER_NAME, userName);
        String selection = CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(customerId)};
        sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, selection, selectionArgs);

    }





}
