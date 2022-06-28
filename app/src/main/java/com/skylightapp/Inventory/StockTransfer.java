package com.skylightapp.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_ID;

public class StockTransfer implements Parcelable, Serializable {
    private long stockTransferID, stockTransfererID, stockTransferCode, recipientID;
    private int stockItemQty;
    private String stockAcceptanceDate,stockName;
    private String stockAccepter;
    private String stockTransferer;
    private String stockStatus;
    private String stockFrom;
    private String stockTo;
    private String stockTransferDate;

    public static final String T_STOCKS_TABLE = "T_Stock_Table";
    public static final String T_STOCK_ID = "T_Stock_ID";
    public static final String T_STOCK_CODE = "T_Stock_Code";
    public static final String T_STOCK_TRANSFERER_ID = "T_Stock_TransfererID";
    public static final String T_STOCK_RECIPIENT_ID = "T_Stock_RecipientID";
    public static final String T_STOCK_ITEM_NAME = "T_Stock_Name";
    public static final String T_STOCK_QTY = "T_Stock_Qty";
    public static final String T_STOCK_ACCEPTANCE_DATE = "T_Stock_Acceptance_Date";
    public static final String T_STOCK_ACCEPTER = "T_Stock_Accepter";
    public static final String T_STOCK_TRANSFERER = "T_Stock_Transferer";
    public static final String T_STOCK_FROM = "T_Stock_From";
    public static final String T_STOCK_To = "T_Stock_To";

    public static final String T_STOCK_STATUS = "T_Stock_Status";
    public static final String T_STOCK_DATE = "T_Stock_Date";
    public static final String T_STOCK_STOCKID = "T_Stock_Stock_ID";

    public static final String CREATE_T_STOCKS_TABLE = "CREATE TABLE IF NOT EXISTS " + T_STOCKS_TABLE + " (" + T_STOCK_ID + " INTEGER, " + T_STOCK_STOCKID + " INTEGER , " + T_STOCK_TRANSFERER_ID + " INTEGER , " + T_STOCK_RECIPIENT_ID + " INTEGER , " + T_STOCK_ITEM_NAME + " TEXT, " + T_STOCK_QTY + " INTEGER, " +
            T_STOCK_DATE + " TEXT, " + T_STOCK_TRANSFERER + " TEXT, " + T_STOCK_ACCEPTER + " TEXT, " + T_STOCK_ACCEPTANCE_DATE + " TEXT, " + T_STOCK_CODE + " TEXT, " +
              T_STOCK_STATUS + " TEXT, " +T_STOCK_FROM + " TEXT, " +T_STOCK_To + " TEXT, " + "PRIMARY KEY(" +T_STOCK_ID + "), " + "FOREIGN KEY(" + T_STOCK_STOCKID  + ") REFERENCES " + STOCKS_TABLE + "(" + STOCK_ID + "),"+ "FOREIGN KEY(" + T_STOCK_TRANSFERER_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + T_STOCK_RECIPIENT_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";



    public StockTransfer(Parcel in) {
        stockTransferID = in.readLong();
        stockTransfererID = in.readLong();
        stockTransferCode = in.readLong();
        recipientID = in.readLong();
        stockItemQty = in.readInt();
        stockAcceptanceDate = in.readString();
        stockName = in.readString();
        stockAccepter = in.readString();
        stockStatus = in.readString();
        stockTransferDate = in.readString();
    }

    public static final Creator<StockTransfer> CREATOR = new Creator<StockTransfer>() {
        @Override
        public StockTransfer createFromParcel(Parcel in) {
            return new StockTransfer(in);
        }

        @Override
        public StockTransfer[] newArray(int size) {
            return new StockTransfer[size];
        }
    };
    public StockTransfer() {
        super();

    }
    public StockTransfer(long stockTransferID, String tItemName, String transferer, int qty, String recipient,long code,String date,String from,String to, String status) {
        this.stockTransferID = stockTransferID;
        this.stockName = tItemName;
        this.stockTransferer = transferer;
        this.stockAccepter = recipient;
        this.stockTransferDate = date;
        this.stockItemQty = qty;
        this.stockStatus = status;
        this.stockTransferCode = code;
        this.stockFrom = from;
        this.stockTo = to;
    }
    public StockTransfer(long stockTransferID, String tItemName, String transferer, int qty, String recipient,String date, String status) {
        this.stockTransferID = stockTransferID;
        this.stockName = tItemName;
        this.stockTransferer = transferer;
        this.stockAccepter = recipient;
        this.stockTransferDate = date;
        this.stockItemQty = qty;
        this.stockStatus = status;
    }
    public StockTransfer(long stockTransferID, String tItemName, String transferer, int qty, String recipient, long stockTCode, String date, String status) {
        this.stockTransferID = stockTransferID;
        this.stockName = tItemName;
        this.stockTransferer = transferer;
        this.stockAccepter = recipient;
        this.stockTransferCode = stockTCode;
        this.stockTransferDate = date;
        this.stockItemQty = qty;
        this.stockStatus = status;
    }

    public String getStockTransferer() {
        return stockTransferer;
    }
    public void setStockTransferer(String stockTransferer) { this.stockTransferer = stockTransferer; }

    public String getStockFrom() {
        return stockFrom;
    }
    public void setStockFrom(String stockName) { this.stockFrom = stockFrom; }

    public String getStockTo() {
        return stockTo;
    }
    public void setStockTo(String stockTo) { this.stockTo = stockTo; }

    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) { this.stockName = stockName; }

    public long getStockTransferID() { return stockTransferID; }
    public void setStockTransferID(long stockTransferID) { this.stockTransferID = stockTransferID; }

    public long getStockTransferCode() { return stockTransferCode; }
    public void setStockTransferCode(long stockTransferCode) { this.stockTransferCode = stockTransferCode; }

    public long getStockRecipientID() { return recipientID; }
    public void setStockRecipientID(long recipientID) { this.recipientID = recipientID; }


    public long getStockTransfererID() { return stockTransfererID; }
    public void setStockTransfererID(long stockTransfererID) { this.stockTransfererID = stockTransfererID; }

    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }

    public String getStockAccepter() { return stockAccepter; }
    public void setStockAccepter(String stockAccepter) { this.stockAccepter = stockAccepter; }

    public void setStockItemQty(int stockItemQty) { this.stockItemQty = stockItemQty; }
    public int getStockItemQty() { return stockItemQty; }


    public String getStockAcceptanceDate() { return stockAcceptanceDate; }
    public void setStockAcceptanceDate(String stockAcceptanceDate) { this.stockAcceptanceDate = stockAcceptanceDate;
    }
    public String getStockTransferDate() { return stockTransferDate; }
    public void setStockTransferDate(String stockTransferDate) { this.stockTransferDate = stockTransferDate; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(stockTransferID);
        parcel.writeLong(stockTransfererID);
        parcel.writeLong(stockTransferCode);
        parcel.writeLong(recipientID);
        parcel.writeInt(stockItemQty);
        parcel.writeString(stockAcceptanceDate);
        parcel.writeString(stockName);
        parcel.writeString(stockAccepter);
        parcel.writeString(stockStatus);
        parcel.writeString(stockTransferDate);
    }
}
