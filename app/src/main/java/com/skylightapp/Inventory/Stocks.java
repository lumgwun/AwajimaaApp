package com.skylightapp.Inventory;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;

public class Stocks implements Parcelable, Serializable {
    public static final String STOCKS_TABLE = "Stock_Table";
    public static final String STOCK_ID = "Stock_ID";
    public static final String STOCK_ITEM_NAME = "Stock_Name";
    public static final String STOCK_TYPE = "Stock_Type";
    public static final String STOCK_MODEL = "Stock_Model";
    public static final String STOCK_COLOR = "Stock_Color";
    public static final String STOCK_SIZE = "Stock_Size";
    public static final String STOCK_OFFICE = "Stock_Office_Location";
    public static final String STOCK_QTY = "Stock_Qty";
    public static final String STOCK_DATE = "Stock_Date";
    public static final String STOCK_40_DATE = "Stock_40_Date";
    public static final String STOCK_20_DATE = "Stock_20_Date";
    public static final String STOCK_10_DATE = "Stock_10_Date";
    public static final String STOCK_5_DATE = "Stock_5_Date";
    public static final String STOCK_OUT_DATE = "Stock_Out_Date";
    public static final String STOCK_MANAGER = "Stock_Manager";
    public static final String STOCK_DEFECTIVE = "Stock_Defective";
    public static final String STOCK_ACCEPTER = "Stock_Accepter";
    public static final String STOCK_STATUS = "Stock_Status";
    public static final String STOCK_CODE = "Stock_Code";
    public static final String STOCK_PROFILE_ID = "Stock_ProfileID";
    public static final String STOCK_BRANCH_ID = "Stock_BranchID";
    public static final String STOCK_ACCEPTANCE_DATE = "Stock_Acceptance_Date";

    public static final String CREATE_STOCK_TABLE = "CREATE TABLE " + STOCKS_TABLE + " (" + STOCK_ID + " INTEGER PRIMARY KEY, " + STOCK_ITEM_NAME + " TEXT, " +
            STOCK_TYPE + " TEXT, " + STOCK_MODEL + " TEXT, "+ STOCK_COLOR + " TEXT, " +  STOCK_SIZE + " TEXT, " + STOCK_OFFICE + " TEXT, " +STOCK_QTY + " TEXT, " + STOCK_DATE + " TEXT, " +
            STOCK_40_DATE + " TEXT, " + STOCK_20_DATE + " TEXT, " + STOCK_10_DATE + " TEXT, " + STOCK_5_DATE + " TEXT, " + STOCK_DEFECTIVE + " TEXT, " +STOCK_OUT_DATE + " TEXT, " + STOCK_ACCEPTER + " TEXT, " + STOCK_ACCEPTANCE_DATE + " TEXT, " + STOCK_MANAGER + " TEXT,"+ STOCK_STATUS + " TEXT,"+ STOCK_PROFILE_ID + " INTEGER,"+ STOCK_CODE + " LONG,"+ STOCK_BRANCH_ID + " INTEGER," + "FOREIGN KEY(" + STOCK_BRANCH_ID + ") REFERENCES " + OFFICE_BRANCH_TABLE + "(" + OFFICE_BRANCH_ID + "),"+"FOREIGN KEY(" + STOCK_PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + STOCK_PROFILE_ID + "))";


    private long stockCode;
    private String stockName,stockType,stockDate;
    private String stockColor;
    protected String stockModel;
    private String stockSize;
    private int stockItemQty,stockID,branchID,stockProfileID;
    private String stockAcceptanceDate;
    private double stockPrice;
    private String stock40percentDate;
    private String stock20percentDate;
    private String stock10percentDate;
    private String stock5percentDate;
    private String stockOutDate;
    private String stockLocation;
    private String stockManager;
    private int stockDefective;
    private String stockAccepter;
    private String stockStatus;


    public Stocks(Parcel in) {
        stockID = in.readInt();
        stockName = in.readString();
        stockType = in.readString();
        stockDate = in.readString();
        stockColor = in.readString();
        stockModel = in.readString();
        stockSize = in.readString();
        stockItemQty = in.readInt();
        stockPrice = in.readDouble();
        stock40percentDate = in.readString();
        stock20percentDate = in.readString();
        stock10percentDate = in.readString();
        stock5percentDate = in.readString();
        stockOutDate = in.readString();
        stockLocation = in.readString();
        stockManager = in.readString();
    }

    public Stocks(int stockID, String itemName, String office, int qty, String date, String date20percent, String date5percent, int defectiveNo, String stockOutDate, String accepter, String accepterDate, String manager, String status) {
        this.stockID = stockID;
        this.stockName = itemName;
        this.stockLocation = office;
        this.stockItemQty = qty;
        this.stockDate = date;
        this.stock20percentDate = date20percent;
        this.stock5percentDate = date5percent;
        this.stockDefective = defectiveNo;
        this.stockOutDate = stockOutDate;
        this.stockAccepter = accepter;
        this.stockAcceptanceDate = accepterDate;
        this.stockManager = manager;
        this.stockStatus = status;
    }

    public Stocks(int stockID, String itemName, String itemType, int qty, String status) {
        this.stockID = stockID;
        this.stockName = itemName;
        this.stockType = itemType;
        this.stockItemQty = qty;
        this.stockStatus = status;

    }

    public Stocks(int stockID, String itemName, String itemType, String model, String color, String size, String office, int qty, String date, int defectiveNo, String status) {
        this.stockID = stockID;
        this.stockName = itemName;
        this.stockType = itemType;
        this.stockModel = model;
        this.stockColor = color;
        this.stockSize = size;
        this.stockLocation = office;
        this.stockItemQty = qty;
        this.stockDate = date;
        this.stockDefective = defectiveNo;
        this.stockStatus = status;
    }

    public Stocks(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        this.stockID = stocksID;
        this.stockName = selectedStockPackage;
        this.stockType = uStockType;
        this.stockModel = uStockModel;
        this.stockColor = uStockColor;
        this.stockSize = uStockSize;
        this.stockLocation = selectedOfficeBranch;
        this.stockItemQty = uStockQuantity;
        this.stockPrice = uStockPricePerUnit;
        this.stockDate = stockDate;
    }

    public Stocks(int stockID, String itemName, String type, int qty, String date, long code, String office, String admin, String status) {
        this.stockID = stockID;
        this.stockName = itemName;
        this.stockType = type;
        this.stockStatus = status;
        this.stockManager = admin;
        this.stockCode = code;
        this.stockLocation = office;
        this.stockItemQty = qty;
        this.stockDate = date;

    }

    public Stocks() {
        super();
    }

    public Stocks(int stockID, String itemName, String itemType, int qty, String color,long code, String status) {
        this.stockID = stockID;
        this.stockName = itemName;
        this.stockType = itemType;
        this.stockStatus = status;
        this.stockItemQty = qty;
        this.stockColor = color;
        this.stockCode = code;

    }

    public Stocks(String itemName, String itemType, int qty) {
        this.stockName = itemName;
        this.stockType = itemType;
        this.stockItemQty = qty;

    }

    public Stocks(int stockID, String stocksName, int qty) {
        this.stockName = stocksName;
        this.stockID = stockID;
        this.stockItemQty = qty;

    }

    public Stocks(int stocksID, String stocksName, String uStockType, int uStockQuantity, long stockCode, String stockDate, String status) {

        this.stockType = uStockType;
        this.stockStatus = status;
        this.stockDate = stockDate;
        this.stockCode = stockCode;
        this.stockName = stocksName;
        this.stockID = stocksID;
        this.stockItemQty = uStockQuantity;
    }

    public Stocks(int stocksID, String stocksName, String uStockType, int uStockQuantity, long stockCode, String selectedOfficeBranch, String stockDate, String status) {
        this.stockType = uStockType;
        this.stockLocation = selectedOfficeBranch;
        this.stockStatus = status;
        this.stockDate = stockDate;
        this.stockCode = stockCode;
        this.stockName = stocksName;
        this.stockID = stocksID;
        this.stockItemQty = uStockQuantity;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(stockID);
        dest.writeString(stockName);
        dest.writeString(stockType);
        dest.writeString(stockDate);
        dest.writeString(stockColor);
        dest.writeString(stockModel);
        dest.writeString(stockSize);
        dest.writeInt(stockItemQty);
        dest.writeDouble(stockPrice);
        dest.writeString(stock40percentDate);
        dest.writeString(stock20percentDate);
        dest.writeString(stock10percentDate);
        dest.writeString(stock5percentDate);
        dest.writeString(stockOutDate);
        dest.writeString(stockLocation);
        dest.writeString(stockManager);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stocks> CREATOR = new Creator<Stocks>() {
        @Override
        public Stocks createFromParcel(Parcel in) {
            return new Stocks(in);
        }

        @Override
        public Stocks[] newArray(int size) {
            return new Stocks[size];
        }
    };

    public int getStockID() { return stockID; }
    public void setStockID(int stockID) { this.stockID = stockID; }

    public long getStockCode() { return stockCode; }
    public void setStockCode(long stockCode) { this.stockCode = stockCode; }


    public long getStockBranchID() { return branchID; }
    public void setStockBranchID(int branchID) { this.branchID = branchID; }


    public int getStockProfileID() { return stockProfileID; }
    public void setStockProfileID(int stockProfileID) { this.stockProfileID = stockProfileID; }



    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }

    public String getStockAccepter() { return stockAccepter; }
    public void setStockAccepter(String stockAccepter) { this.stockAccepter = stockAccepter; }


    public String getStockAcceptanceDate() { return stockAcceptanceDate; }
    public void setStockAcceptanceDate(String stockAcceptanceDate) { this.stockAcceptanceDate = stockAcceptanceDate; }


    public int getStockDefective() { return stockDefective; }
    public void setStockDefective(int stockDefective) { this.stockDefective = stockDefective; }

    public String getStock5percentDate() { return stock5percentDate; }
    public void setStock5percentDate(String stock5percentDate) { this.stock5percentDate = stock5percentDate; }

    public String getStockManager() { return stockManager; }
    public void setStockManager(String stockManager) { this.stockManager = stockManager; }


    public String getStockOutDate() { return stockOutDate; }
    public void setStockOutDate(String stockOutDate) { this.stockOutDate = stockOutDate; }
    public String getStockLocation() { return stockLocation; }
    public void setStockLocation(String stockLocation) { this.stockLocation = stockLocation; }
    public String getStockColor() { return stockColor; }
    public void setStockColor(String stockColor) { this.stockColor = stockColor; }
    public String getStockType() { return stockType; }
    public void setStockType(String stockType) { this.stockType = stockType; }

    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public String getStockDate() {
        return stockDate;
    }
    public void setStockDate(String stockDate) { this.stockDate = stockDate; }
    public String getStock40percentDate() {
        return stock40percentDate;
    }
    public void setStock40percentDate(String stock40percentDate) { this.stock40percentDate = stock40percentDate; }

    public String getStockSize() {
        return stockSize;
    }
    public void setStockPrice(double stockPrice) { this.stockPrice = stockPrice; }
    public double getStockPrice() {
        return stockPrice;
    }
    public void setStockItemQty(int stockItemQty) { this.stockItemQty = stockItemQty; }
    public int getStockItemQty() { return stockItemQty; }
    public void setStockSize(String stockSize) { this.stockSize = stockSize; }
    public void setStockModel(String stockModel) { this.stockModel = stockModel; }

    public void setStock20percentDate(String stock20percentDate)
    { this.stock20percentDate = stock20percentDate; }
    public String getStock20percentDate() { return stock20percentDate; }
    public void setRole(String stock10percentDate) { this.stock10percentDate = stock10percentDate;

    }
    public String getStock10percentDate() { return stock10percentDate; }


}
