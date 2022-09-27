package com.skylightapp.MarketClasses;

import android.graphics.Bitmap;

import com.skylightapp.R;

import java.io.ByteArrayOutputStream;

public class MarketStock {


    public static final String MARKET_STOCK_TABLE = "Market_Stocks_Table";
    public static final String KEY_PROD_NAME = "prod_name";
    public static final String KEY_PROD_STOCK = "prod_stock";
    public static final String KEY_PROD_COST = "prod_cost";
    public static final String KEY_PROD_PRICE = "prod_price";
    public static final String KEY_PROD_BOUGHT = "prod_bought";
    public static final String KEY_PROD_PHOTO = "prod_photo";
    public static final String KEY_PROD_BENEFIT = "prod_benefit";
    public static final String KEY_PROD_SALES = "prod_sales";

    public static final int PROD_PHOTO_COL = 5;

    public static final String[] KEYS_PROD = { KEY_PROD_NAME, KEY_PROD_STOCK,
            KEY_PROD_COST, KEY_PROD_PRICE, KEY_PROD_BOUGHT, KEY_PROD_PHOTO,
            "(("+KEY_PROD_BOUGHT + " - " + KEY_PROD_STOCK + ")" 	// Sold units
                    +" * ("+KEY_PROD_PRICE + " - " + KEY_PROD_COST + ")"	// by benefits per unit
                    +" - " + KEY_PROD_STOCK + " * " + KEY_PROD_COST 		// minus cost of stock
                    + ") AS " + KEY_PROD_BENEFIT,							//  AS benefits
            KEY_PROD_BOUGHT + " - " + KEY_PROD_STOCK + " AS " + KEY_PROD_SALES};
    public static final int PROD_NAME_COL = 0;
    public static final int PROD_STOCK_COL = 1;
    public static final int PROD_COST_COL = 2;
    public static final int PROD_PRICE_COL = 3;
    public static final int PROD_BOUGHT_COL = 4;

    public static final String CREATE_MARKET_STOCK_TABLE = "CREATE TABLE " + MARKET_STOCK_TABLE +
            " USING fts3"+
            " ("+KEY_PROD_NAME+" TEXT NOT NULL, "+
            KEY_PROD_STOCK+ " INTEGER NOT NULL, "+
            KEY_PROD_COST+ " REAL NOT NULL, "+
            KEY_PROD_PRICE+ " REAL NOT NULL, "+
            KEY_PROD_BOUGHT+ " INTEGER NOT NULL, "+
            KEY_PROD_PHOTO+ " BLOB);";



    private String name;		// Nombre descriptivo del producto
    private int stock;			// Cantidad de unidades en almacen
    private double cost;		// Coste de fabricación/adquisición de cada unidad
    private double price;		// Precio de venta de cada unidad
    private int boughtUnits;	// Unidades fabricadas/adquiridas
    private Bitmap photo;		// Foto del producto

    public MarketStock() {
        this("");
    }

    public MarketStock(MarketStock other) {
        this.name = other.name;
        this.stock = other.stock;
        this.cost = other.cost;
        this.price = other.price;
        this.photo = other.photo;
        this.boughtUnits = other.boughtUnits;
    }

    public MarketStock(String name) {
        this.name = name;
        this.stock = 0;
        this.cost = 0;
        this.price = 0;
        this.photo = null;
        this.boughtUnits = 0;
    }

    public MarketStock(String name, int stock, double cost, double price, Bitmap photo) throws StockAttrException {
        this(name, stock, cost, price, photo, stock);
    }

    public MarketStock(String name, int stock, double cost, double price, Bitmap photo, int boughtUnits) throws StockAttrException {
        if (stock < 0)
            throw new StockAttrException(R.string.error_invalid_stock_or_too_large);

        if (cost < 0)
            throw new StockAttrException(R.string.error_invalid_cost);

        if (price < 0)
            throw new StockAttrException(R.string.error_invalid_price);

        if (name == null || name.equals(""))
            throw new StockAttrException(R.string.error_name_empty);

        this.name = name;
        this.stock = stock;
        this.boughtUnits = boughtUnits;
        this.cost = cost;
        this.price = price;
        this.photo = photo;
    }

    public void addStock(int units) throws StockAttrException {
        if (((stock+units) < 0) || ((boughtUnits + units) < 0))
            throw new StockAttrException(R.string.error_invalid_stock_or_too_large);

        this.stock += units;
        this.boughtUnits += units;
    }

    public String getBoughtUnits() {
        return Integer.toString(boughtUnits);
    }

    public String getCost() {
        return Double.toString(cost);
    }

    public String getName() {
        return name;
    }

    /**
     * @return photo of this Product, or null if there's no photo.
     */
    public Bitmap getPhoto() {
        return photo;
    }

    public byte[] getPhotoAsByteArray() {
        if (photo != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 0, outputStream); // PNG: lossless compression
            return outputStream.toByteArray();
        }

        return new byte[0];
    }

    public String getPrice() {
        return Double.toString(price);
    }

    public int getSoldUnits() {
        return boughtUnits - stock;
    }

    public String getStock() {
        return Integer.toString(stock);
    }

    public void sellUnits(int units) throws StockAttrException {
        if ((stock-units) < 0)
            throw new StockAttrException(R.string.error_invalid_stock_or_too_large);

        this.stock -= units;
    }

    public void setCost(double cost) throws StockAttrException {
        if (cost < 0)
            throw new StockAttrException(R.string.error_invalid_cost);

        this.cost = cost;
    }

    public void setName(String name) throws StockAttrException {
        if (name == null || name.equals(""))
            throw new StockAttrException(R.string.error_name_empty);

        this.name = name;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setPrice(double price) throws StockAttrException {
        if (price < 0)
            throw new StockAttrException(R.string.error_invalid_price);

        this.price = price;
    }

    public void setStock(int stock) throws StockAttrException {
        if (stock < 0)
            throw new StockAttrException(R.string.error_invalid_stock_or_too_large);

        this.stock = stock;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public double getBenefits() {
        Double benefitPerUnit = (this.price - this.cost);
        return Math.round(100*(getSoldUnits() * benefitPerUnit - this.stock*this.cost)) / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof MarketStock))
            return false;
        if(o == this)
            return true;

        MarketStock other = (MarketStock) o;

        if(other.boughtUnits != this.boughtUnits)
            return false;
        if(other.cost != this.cost)
            return false;
        if(!other.name.equals(this.name))
            return false;
        if(!other.photo.equals(this.photo))
            return false;
        if(other.price != this.price)
            return false;
        if(other.stock != this.stock)
            return false;

        return true;
    }
}
