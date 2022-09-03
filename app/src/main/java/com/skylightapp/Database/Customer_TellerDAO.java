package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.ConnectionFailedException;
import com.skylightapp.Classes.CustomerManager;

import java.util.ArrayList;

import static com.skylightapp.Classes.Bookings.BOOKING_ID;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_ID;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_OFFICE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_TABLE;
import static java.lang.String.valueOf;

public class Customer_TellerDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CUSTOMER_TELLER_ID
            + " =?";
    public Customer_TellerDAO(Context context) {
        super(context);
    }
    public void deleteTeller(int tellerID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CUSTOMER_TELLER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID)};

        db.delete(CUSTOMER_TELLER_TABLE,
                selection, selectionArgs);

    }
    public Cursor getSpinnerTellersFromCursor(ArrayList<CustomerManager> customerManagerArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int tellerID = cursor.getInt(0);
            String surname = cursor.getString(2);
            String firstName = cursor.getString(3);
            customerManagerArrayList.add(new CustomerManager(tellerID, surname, firstName));

        }
        return cursor;
    }

    public ArrayList<CustomerManager> getAllTellersSpinner() {
        ArrayList<CustomerManager> customerManagers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TELLER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSpinnerTellersFromCursor(customerManagers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customerManagers;

    }

    public Cursor getAllCustomerManagersFromCursor(ArrayList<CustomerManager> customerManagers, Cursor cursor) {
        while (cursor.moveToNext()) {

            int tellerID = cursor.getInt(0);
            String surname = cursor.getString(2);
            String firstName = cursor.getString(3);
            String phoneNumber = cursor.getString(4);
            String emailAddress = cursor.getString(5);
            String userName = cursor.getString(6);
            String dob = cursor.getString(7);
            String gender = cursor.getString(8);
            String address = cursor.getString(9);
            String office = cursor.getString(10);
            String dateJoined = cursor.getString(11);
            String password = cursor.getString(12);
            String nin = cursor.getString(13);
            Uri pix = Uri.parse(cursor.getString(14));
            String status = cursor.getString(15);

            try {
                customerManagers.add(new CustomerManager(tellerID, surname, firstName, phoneNumber, emailAddress, nin,dob, gender, address, userName, password, office, dateJoined,pix,status));
            } catch (ConnectionFailedException e) {
                e.printStackTrace();
            }
        }
        return cursor;
    }



    public ArrayList<CustomerManager> getAllCustomersManagers() {
        ArrayList<CustomerManager> customerManagers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TELLER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAllCustomerManagersFromCursor(customerManagers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customerManagers;
    }



    public ArrayList<CustomerManager> getAllTellerBranchSpinner(String branchOffice) {
        ArrayList<CustomerManager> customerManagerArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CUSTOMER_TELLER_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchOffice)};

        @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TELLER_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSpinnerTellersFromCursor(customerManagerArrayList, cursor);
                cursor.close();
            }

        db.close();

        return customerManagerArrayList;
    }
}
