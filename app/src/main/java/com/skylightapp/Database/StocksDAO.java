package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Inventory.Stocks;

import java.util.ArrayList;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_10_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_20_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_40_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_5_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTANCE_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTER;
import static com.skylightapp.Inventory.Stocks.STOCK_BRANCH_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_CODE;
import static com.skylightapp.Inventory.Stocks.STOCK_COLOR;
import static com.skylightapp.Inventory.Stocks.STOCK_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_DEFECTIVE;
import static com.skylightapp.Inventory.Stocks.STOCK_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_ITEM_NAME;
import static com.skylightapp.Inventory.Stocks.STOCK_MANAGER;
import static com.skylightapp.Inventory.Stocks.STOCK_MODEL;
import static com.skylightapp.Inventory.Stocks.STOCK_OFFICE;
import static com.skylightapp.Inventory.Stocks.STOCK_PROFILE_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_QTY;
import static com.skylightapp.Inventory.Stocks.STOCK_SIZE;
import static com.skylightapp.Inventory.Stocks.STOCK_STATUS;
import static com.skylightapp.Inventory.Stocks.STOCK_TYPE;
import static java.lang.String.valueOf;

public class StocksDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = OFFICE_BRANCH_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public StocksDAO(Context context) {
        super(context);
    }
    public void updateBranchStockCount(String officeBranch,String itemName,int newItemCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = STOCK_OFFICE + "=? AND " + STOCK_ITEM_NAME + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(itemName)};
        stocksUpdateValues.put(STOCK_QTY, newItemCount);
        db.update(STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
        //db.close();




    }
    public int getStockItemCountForBranch(String packageName, String officeBranch) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = STOCK_ITEM_NAME + "=? AND " + STOCK_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(packageName), valueOf(officeBranch)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getStocksTotalForBranch(int branchID) {
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT SUM(" + STOCK_QTY + ") as Total FROM " + STOCKS_TABLE, new String[]{" WHERE STOCK_BRANCH_ID=?",String.valueOf(branchID)})){

            if (cursor.moveToFirst()) {

                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }

        return 0;
    }

    public double getTotalStocksTodayForBranch1(int branchID, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0;
        String selection = STOCK_BRANCH_ID + "=? AND " + STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getDouble(7);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return count;


        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        //return 0;
    }
    public ArrayList<Stocks> getAllStocksForBranch(int branchID) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_BRANCH_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(branchID)};
        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksNameFromCursor3(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return stocksArrayList;
    }

    public ArrayList<Stocks> getStocksForBranchName(String branchName) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksNameFromCursor3(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return stocksArrayList;

        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;*/
    }
    public ArrayList<Stocks> getStocksForBranchAtDate(int branchID, String date) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_BRANCH_ID + "=? AND " + STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchID), valueOf(date)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksFromCursorProfile(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return stocksArrayList;

    }

    public int getStockCountAtDateForBranch(String officeBranch,String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = STOCK_OFFICE + "=? AND " + STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public void deleteStocks(int stockID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockID)};

        db.delete(STOCKS_TABLE,
                selection,
                selectionArgs);


    }


    public int getStockItemCount(String stockItemName,String model) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = STOCK_ITEM_NAME + "=? AND " + STOCK_MODEL + "=?";
        String[] selectionArgs = new String[]{valueOf(stockItemName), valueOf(model)};
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

    }

    public void updateStocksWithCode(String office,int profileID,long code,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = STOCK_OFFICE + "=? AND " + STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(profileID)};
        stocksUpdateValues.put(STOCK_CODE, code);
        stocksUpdateValues.put(STOCK_STATUS, status);
        db.update(STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
        //db.close();


    }



    public long insertStock(int stockID, int profileID,String stockName,String stockType, String stockModel, String color,String size,int qty,String office,String date,String manager) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCK_ID, stockID);
        values.put(STOCK_PROFILE_ID, profileID);
        values.put(STOCK_ITEM_NAME, stockName);
        values.put(STOCK_TYPE, stockType);
        values.put(STOCK_MODEL, stockModel);
        values.put(STOCK_COLOR, color);
        values.put(STOCK_SIZE, size);
        values.put(STOCK_OFFICE, office);
        values.put(STOCK_QTY, qty);
        values.put(STOCK_DATE, date);
        values.put(STOCK_MANAGER, manager);
        return db.insert(STOCKS_TABLE,null,values);

    }

    public void updateStock(int stockID,String qty,String date40th,String date20th,String date10th,String date5th,int defective,String accepter,String accepterDate,String manager) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(STOCK_QTY, qty);
        paymentUpdateValues.put(STOCK_40_DATE, date40th);
        paymentUpdateValues.put(STOCK_20_DATE, date20th);
        paymentUpdateValues.put(STOCK_10_DATE, date10th);
        paymentUpdateValues.put(STOCK_5_DATE, date5th);
        paymentUpdateValues.put(STOCK_DEFECTIVE, defective);
        paymentUpdateValues.put(STOCK_ACCEPTER, accepter);
        paymentUpdateValues.put(STOCK_ACCEPTANCE_DATE, accepterDate);
        paymentUpdateValues.put(STOCK_MANAGER, manager);
        String selection = STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockID)};
        db.update(STOCKS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();

    }

    public void updateStock5th(int stockID,String office,int qty,String date5th) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(STOCK_OFFICE, office);
        paymentUpdateValues.put(STOCK_QTY, qty);
        paymentUpdateValues.put(STOCK_5_DATE, date5th);
        String selection = STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockID)};
        db.update(STOCKS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();
    }
    public void updateStock20th(int stockID,String office,int qty,String date20th) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(STOCK_OFFICE, office);
        paymentUpdateValues.put(STOCK_QTY, qty);
        paymentUpdateValues.put(STOCK_20_DATE, date20th);
        String selection = STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockID)};
        db.update(STOCKS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();

    }
    public void updateStock4oth(int stockID,String office,int qty,String date40th) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(STOCK_OFFICE, office);
        paymentUpdateValues.put(STOCK_QTY, qty);
        paymentUpdateValues.put(STOCK_40_DATE, date40th);
        String selection = STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockID)};
        db.update(STOCKS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        //db.close();

    }
    private void getStocksFromCursorSuper(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int stockID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                String office = cursor.getString(6);
                int qty = cursor.getInt(7);
                String date = cursor.getString(8);
                String date20percent = cursor.getString(10);
                String date5percent = cursor.getString(12);
                int defectiveNo = cursor.getInt(13);
                String stockOutDate = cursor.getString(14);
                String accepter = cursor.getString(15);
                String accepterDate = cursor.getString(16);
                String manager = cursor.getString(17);
                String status = cursor.getString(18);
                //Date date=formatter.parse(cursor.getString(6));

                stocksArrayList.add(new Stocks(stockID, itemName, office,qty,date,date20percent,date5percent,defectiveNo, stockOutDate,accepter,accepterDate,manager,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<Stocks> getAllStocksForDate(String date) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksFromCursorSuper(stocksArrayList, cursor);

                }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Stocks> getAllStocksForToday(String today) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksFromCursorSuper(stocksArrayList, cursor);

            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return stocksArrayList;
    }

    public double getTotalStocksForDate(String date) {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        cursor = db.rawQuery(
                "select Total ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getTotalStocks() {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(
                "select Total ("+ STOCK_QTY +") from " + STOCKS_TABLE ,null,
                null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getTotalStocksForToday(String today) {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        cursor = db.rawQuery(
                "select Total ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }

    public double getTotalStocksTodayForProfile(int profileID, String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0;
        Cursor cursor=null;
        String selection = STOCK_PROFILE_ID + "=? AND " + STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        cursor = db.rawQuery(
                "select sum ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

    }
    public ArrayList<Stocks> getStocksForProfile(int profileID) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksFromCursorProfile(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return stocksArrayList;
    }
    public ArrayList<Stocks> getStocksForProfileAtDate(int profileID, String date) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=? AND " + STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksFromCursorProfile(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return stocksArrayList;
    }
    public ArrayList<Stocks> getAllStocksForProfile(int profileID) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksFromCursorProfile(stocksArrayList, cursor);

            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return stocksArrayList;
    }
    private void getStocksFromCursorProfile(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int stockID = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String office = cursor.getString(6);
            String type = cursor.getString(2);
            int qty = cursor.getInt(7);
            String date = cursor.getString(8);
            String admin = cursor.getString(18);
            long code = cursor.getLong(20);
            String status = cursor.getString(18);
            stocksArrayList.add(new Stocks(stockID, itemName,type, qty,date,code,office,admin,status));
        }


    }
    public double getStocksTotalForProfile(int profileID) {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        cursor = db.rawQuery(
                "select sum ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }







    private void getStocksNameFromCursor3(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(1);
            String itemType = cursor.getString(2);
            int qty = cursor.getInt(7);
            stocksArrayList.add(new Stocks(itemName,itemType,qty));
        }

    }

    public ArrayList<Stocks> getStocksForTeller3(int profileID) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksNameFromCursor3(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return stocksArrayList;
    }
    private void getStocksNameAndMoreCursor(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int stockID = cursor.getInt(0);
            long stockCode = cursor.getLong(20);
            String itemName = cursor.getString(1);
            String itemType = cursor.getString(2);
            int qty = cursor.getInt(7);
            String color = cursor.getString(4);
            String status = cursor.getString(18);
            stocksArrayList.add(new Stocks(stockID, itemName,itemType,qty,color,stockCode,status));
        }
    }

    private void getStocksNameFromCursor(ArrayList<String> strings, Cursor cursor) {
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(1);
            strings.add(itemName);
        }

    }

    public ArrayList<String> getAllStocksName() {
        ArrayList<String> stockNameArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {STOCK_ITEM_NAME};
        Cursor cursor = db.query(STOCKS_TABLE, columns, STOCK_ITEM_NAME, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksNameFromCursor(stockNameArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return stockNameArrayList;
    }





    private void getStocksFromCursorFew(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int stockID = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String itemType = cursor.getString(2);
            int qty = cursor.getInt(7);
            String status = cursor.getString(18);
            stocksArrayList.add(new Stocks(stockID, itemName,itemType,qty,status));
        }

    }
    private void getStocksFromCursor(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int stockID = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String itemType = cursor.getString(2);
            String model = cursor.getString(3);
            String color = cursor.getString(4);
            String size = cursor.getString(5);
            String office = cursor.getString(6);
            int qty = cursor.getInt(7);
            String date = cursor.getString(8);
            int defectiveNo = cursor.getInt(13);
            String status = cursor.getString(18);
            //Date date=formatter.parse(cursor.getString(6));

            stocksArrayList.add(new Stocks(stockID, itemName,itemType, model,color,size,office,qty,date,defectiveNo,status));
        }


    }

    public ArrayList<Stocks> getALLStocksSuper() {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {STOCK_ID,STOCK_ITEM_NAME,STOCK_QTY,STOCK_OFFICE,STOCK_DATE,STOCK_20_DATE,STOCK_STATUS};
        Cursor cursor = db.query(STOCKS_TABLE, columns, null, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksFromCursorSuper(stocksArrayList, cursor);
                cursor.close();

            }
        db.close();

        return stocksArrayList;
    }
}
