package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;

import java.util.ArrayList;

import static com.skylightapp.Inventory.StockTransfer.T_STOCKS_TABLE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_ACCEPTER;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_CODE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_DATE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_FROM;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_ID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_ITEM_NAME;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_QTY;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_RECIPIENT_ID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_STATUS;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_STOCKID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_TRANSFERER;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_TRANSFERER_ID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_To;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTANCE_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTER;
import static com.skylightapp.Inventory.Stocks.STOCK_CODE;
import static com.skylightapp.Inventory.Stocks.STOCK_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_ITEM_NAME;
import static com.skylightapp.Inventory.Stocks.STOCK_OFFICE;
import static com.skylightapp.Inventory.Stocks.STOCK_PROFILE_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_QTY;
import static com.skylightapp.Inventory.Stocks.STOCK_STATUS;
import static com.skylightapp.Inventory.Stocks.STOCK_TYPE;
import static java.lang.String.valueOf;

public class StockTransferDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = T_STOCK_TRANSFERER_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public StockTransferDAO(Context context) {
        super(context);
    }
    public void deleteStocksTransfer(int stockTransferID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockTransferID)};
        db.delete(T_STOCKS_TABLE,
                selection, selectionArgs);


    }
    public void updateStocksQty(int stockID,int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockID)};
        stocksUpdateValues.put(STOCK_QTY, qty);
        db.update(STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);


    }
    public void updateStocksTransferWithCode(int stockTransferID,long code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = T_STOCK_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(stockTransferID)};
        stocksUpdateValues.put(T_STOCK_CODE, code);
        db.update(T_STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
        db.close();


    }
    public ArrayList<StockTransfer> getAllStocksTransfers() {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {T_STOCK_ID,T_STOCK_ITEM_NAME,T_STOCK_QTY,T_STOCK_DATE,T_STOCK_CODE,T_STOCK_TRANSFERER,T_STOCK_ACCEPTER,T_STOCK_STATUS};
        Cursor cursor = db.query(T_STOCKS_TABLE, columns, null, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferWithDate(String date) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {T_STOCK_ID,T_STOCK_ITEM_NAME,T_STOCK_QTY,T_STOCK_DATE,T_STOCK_CODE,T_STOCK_TRANSFERER,T_STOCK_ACCEPTER,T_STOCK_STATUS};

        String selection = T_STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(T_STOCKS_TABLE, columns, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferForSender(int profileID) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_TRANSFERER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;

    }
    public ArrayList<StockTransfer> getStocksTransferForReciever(int receiverProfileID) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_TRANSFERER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(receiverProfileID)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferFromTeller(String Teller) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_FROM + "=?";
        String[] selectionArgs = new String[]{valueOf(Teller)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferFromTellerWithDate(String Teller,String date) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_FROM + "=? AND " + T_STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(Teller), valueOf(date)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferFromBranchWithDate(String Branch,String date) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_FROM + "=? AND " + T_STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(Branch), valueOf(date)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }

    public ArrayList<StockTransfer> getStocksTransferFromSkylightWithDate(String Skylight,String date) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_FROM + "=? AND " + T_STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(Skylight), valueOf(date)};
        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();

        return stockTransferArrayList;

    }
    public ArrayList<StockTransfer> getStocksTransferFromBranch(String Branch) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_FROM + "=?";
        String[] selectionArgs = new String[]{valueOf(Branch)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferFromAwajima(String Skylight) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_FROM + "=?";
        String[] selectionArgs = new String[]{valueOf(Skylight)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksTransferAtDate(String date) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();

        return stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStocksToCustomer(String Customer) {
        ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = T_STOCK_To + "=?";
        String[] selectionArgs = new String[]{valueOf(Customer)};
        Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, T_STOCK_DATE,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stockTransferArrayList;

    }
    private void getStocksTransferWithCodeCursor(ArrayList<StockTransfer> stockTransfers, Cursor cursor) {
        while (cursor.moveToNext()) {

            long stockTransferID = cursor.getLong(0);
            long stockTCode = cursor.getLong(10);
            String tItemName = cursor.getString(4);
            String recipient = cursor.getString(8);
            int qty = cursor.getInt(5);
            String transferer = cursor.getString(7);
            String date = cursor.getString(6);
            String from = cursor.getString(12);
            String to = cursor.getString(13);
            String status = cursor.getString(11);
            stockTransfers.add(new StockTransfer(stockTransferID, tItemName,transferer,qty,recipient,stockTCode,date,from,to,status));
        }
    }
    private void getStocksTransferWithoutCodeCursor(ArrayList<StockTransfer> stockTransfers, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                long stockTransferID = cursor.getLong(0);
                String tItemName = cursor.getString(4);
                String recipient = cursor.getString(8);
                int qty = cursor.getInt(5);
                String transferer = cursor.getString(7);
                String date = cursor.getString(6);
                String status = cursor.getString(11);
                stockTransfers.add(new StockTransfer(stockTransferID, tItemName,transferer,qty,recipient,date,status));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void getStocksTransferersCursor(ArrayList<String> strings, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                String itemName = cursor.getString(7);
                strings.add(itemName);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllStockTransferersName(String name) {
        ArrayList<String> strings = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {T_STOCK_TRANSFERER};
        String selection = T_STOCK_TRANSFERER + "=?";
        String[] selectionArgs = new String[]{valueOf(name)};
        Cursor cursor = db.query(T_STOCKS_TABLE, columns, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferersCursor(strings, cursor);
                cursor.close();
            }

        db.close();

        return strings;
    }
    public ArrayList<String> getAllStockTransferersNameWithDate(String date) {
        ArrayList<String> strings = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {T_STOCK_TRANSFERER};
        String selection = T_STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        Cursor cursor = db.query(T_STOCKS_TABLE, columns, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksTransferersCursor(strings, cursor);
                cursor.close();
            }

        db.close();
        return strings;

    }



    public int getAllStockTransferCountForDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = new String[]{date};
            String selection = T_STOCK_DATE + "=?";
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + T_STOCKS_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getAllStockCountForDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[]{date};
        String selection = STOCK_DATE + "=?";


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
    private void getStocksNameSpnCursor(ArrayList<String> stringArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            String stocksName = cursor.getString(1);
            stringArrayList.add(stocksName);
        }

    }
    public Stocks getRecipientAndStock(int recipientID, String stockItems) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;
        Stocks stocks = null;
        String selection = STOCK_PROFILE_ID + "=? AND " + STOCK_ITEM_NAME + "=?";
        String[] selectionArgs = new String[]{valueOf(recipientID), valueOf(stockItems)};
        String[] projection = {STOCK_ID, STOCK_ITEM_NAME,STOCK_TYPE,STOCK_OFFICE, STOCK_QTY,STOCK_DATE,STOCK_ACCEPTER,STOCK_ACCEPTANCE_DATE,STOCK_STATUS,STOCK_CODE};

        cursor = db.query(STOCKS_TABLE, projection, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            stocks= new Stocks();
            stocks.setStockID(cursor.getInt(0));
            stocks.setStockName(cursor.getString(1));
            stocks.setStockType(cursor.getString(2));
            stocks.setStockLocation(cursor.getString(6));
            stocks.setStockItemQty(cursor.getInt(7));
            stocks.setStockDate(cursor.getString(8));
            stocks.setStockAccepter(cursor.getString(15));
            stocks.setStockAcceptanceDate(cursor.getString(16));
            stocks.setStockStatus(cursor.getString(18));
            stocks.setStockCode(cursor.getLong(20));


        }
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.close();
            }
        db.close();

        return stocks;

    }


    public ArrayList<String> getStocksName() {
        ArrayList<String> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {STOCK_ITEM_NAME};
        Cursor cursor = db.query(STOCKS_TABLE, columns, STOCK_ITEM_NAME, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksNameSpnCursor(stocksArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stocksArrayList;
    }
    private void getStocksNameStockCursor(ArrayList<Stocks> stringArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int stockID=cursor.getInt(0);
            String stocksName = cursor.getString(1);
            int qty=cursor.getInt(7);
            stringArrayList.add(new Stocks(stockID,stocksName,qty));
        }

    }

    public ArrayList<Stocks> getStocksNameStock() {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {STOCK_ITEM_NAME};
        Cursor cursor = db.query(STOCKS_TABLE, columns, STOCK_ITEM_NAME, null, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksNameStockCursor(stocksArrayList, cursor);
                cursor.close();
            }

        db.close();
        return stocksArrayList;
    }
    public ArrayList<Stocks> getAllStockForProfile(int profileID) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getStocksNameStockCursor(stocksArrayList, cursor);
                cursor.close();
            }

        db.close();

        return stocksArrayList;
    }


    public long saveNewStocksTransfer(int stID, int stockID,int senderID, int receiverID, String itemName,int qty, String sender,String receiver, String from,String to,String date, long code, String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_STOCK_ID, stID);
        contentValues.put(T_STOCK_STOCKID, stockID);
        contentValues.put(T_STOCK_TRANSFERER_ID, senderID);
        contentValues.put(T_STOCK_RECIPIENT_ID, receiverID);
        contentValues.put(T_STOCK_ITEM_NAME, itemName);
        contentValues.put(T_STOCK_QTY, qty);
        contentValues.put(T_STOCK_DATE, date);
        contentValues.put(T_STOCK_TRANSFERER, sender);
        contentValues.put(T_STOCK_ACCEPTER, receiver);
        contentValues.put(T_STOCK_FROM, from);
        contentValues.put(T_STOCK_To, to);
        contentValues.put(T_STOCK_CODE, code);
        contentValues.put(T_STOCK_STATUS, status);
        db.insert(T_STOCKS_TABLE,null,contentValues);
        //db.close();
        return stID;
    }
}
