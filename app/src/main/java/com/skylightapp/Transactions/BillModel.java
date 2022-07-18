package com.skylightapp.Transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Customer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;



public class BillModel implements Parcelable, Serializable {
    public static final String BILL_TABLE = "Transactions";
    public static final String BILL_ID = "b_id";

    public static final String BILL_DATE = "Timestamp";
    public static final String BILL_NAME = "billName";
    public static final String BILL_AMOUNT = "billAmount";
    public static final String BILL_COUNTRY = "billCountry";
    public static final String BILL_REF = "billRef";
    public static final String BILL_RECURRING_TYPE = "bill_Recurring_type";
    public static final String BILLER_CODE = "billerCode";
    public static final String BILL_ITEM_CODE = "billerItemCode";
    public static final String BILL_CURRENCY = "billCurrency";
    public static final String BILL_STATUS = "billStatus";


    public static final String CREATE_BILLS_TABLE = "CREATE TABLE " + BILL_TABLE + " (" + BILL_ID + " LONG NOT NULL, " + PROFILE_ID + " LONG , " +
            CUSTOMER_ID + " LONG , " + ACCOUNT_NO + " LONG , " + BILL_NAME + " TEXT, " + BILL_AMOUNT + " DOUBLE, " +BILL_CURRENCY + " TEXT,"+
            BILL_DATE + " DATE, " + BILL_COUNTRY + " TEXT, " + BILL_RECURRING_TYPE + " TEXT,"+ BILL_REF + " TEXT, " +
            BILL_ITEM_CODE + " TEXT, " + BILLER_CODE + " TEXT, " + BILL_STATUS + " TEXT, " +"PRIMARY KEY(" +CUSTOMER_ID+"," + PROFILE_ID + "," + ACCOUNT_NO + "), " +"FOREIGN KEY(" + ACCOUNT_NO  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    public BillModel(Parcel in) {
        id = in.readInt();
        biller_code = in.readString();
        name = in.readString();
        secret_key = in.readString();
        default_commission = in.readString();
        biller_name = in.readString();
        item_code = in.readString();
        fee = in.readString();
        label_name = in.readString();
        service = in.readString();
        service_method = in.readString();
        service_version = in.readString();
        service_channel = in.readString();
        Country = in.readString();
        CustomerId = in.readString();
        Reference = in.readString();
        Amount = in.readInt();
        RecurringType = in.readString();
        IsAirtime = in.readByte() != 0;
        test = in.readString();
    }

    public static final Creator<BillModel> CREATOR = new Creator<BillModel>() {
        @Override
        public BillModel createFromParcel(Parcel in) {
            return new BillModel(in);
        }

        @Override
        public BillModel[] newArray(int size) {
            return new BillModel[size];
        }
    };

    public BillModel() {

    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(biller_code);
        parcel.writeString(name);
        parcel.writeString(secret_key);
        parcel.writeString(default_commission);
        parcel.writeString(biller_name);
        parcel.writeString(item_code);
        parcel.writeString(fee);
        parcel.writeString(label_name);
        parcel.writeString(service);
        parcel.writeString(service_method);
        parcel.writeString(service_version);
        parcel.writeString(service_channel);
        parcel.writeString(Country);
        parcel.writeString(CustomerId);
        parcel.writeString(Reference);
        parcel.writeInt(Amount);
        parcel.writeString(RecurringType);
        parcel.writeByte((byte) (IsAirtime ? 1 : 0));
        parcel.writeString(test);
    }

    public void setCustomer(Customer customer) {

    }

    public void setBillDate(String string) {

    }

    public enum BILL_TYPE {
        GOTV, DSTV, DATA,
        AIRTIME,
        POWER,WATER,SMILES,
        OTHERS
    }
    private long id;
    Customer customer;
    long customerID;
    private String biller_code;
    private String name;
    private String secret_key;
    private String default_commission;
    private String biller_name;
    private String item_code;
    private String fee;
    private Date billDate;
    private String label_name;
    private String service;
    private String service_method;
    private String service_version;
    private String service_channel;
    private String Country;
    private String CustomerId;
    private String Reference;
    private String Currency;
    private String status;
    private int Amount;
    private String RecurringType;
    private boolean IsAirtime=false;
    private String test;


    String myDate ;
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy", Locale.getDefault());
    Date date = new Date();

    /*{
        try {
            date = dateFormat.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/

    public String getTest() {
        return test;
    }

    public BillModel(long billID,long customerID, String billName, Integer billAmount, String billCurrency, String billDate, String billFreq, String billStatus) {
      this.id=billID;
      this.customerID=customerID;
        this.name=billName;
        this.Amount=billAmount;
        this.Currency=billCurrency;
        try {
            this.billDate=dateFormat.parse(billDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.RecurringType=billFreq;
        this.status=billStatus;

    }


    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }
    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
    public String getBillStatus() {
        return status;
    }

    public void setBillStatus(String status) {
        this.status = status;
    }



    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }


    public void setBiller_code(String biller_code) {
        this.biller_code = biller_code;
    }


    public String getBiller_code() {
        return biller_code;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setDefault_commission(String default_commission) {
        this.default_commission = default_commission;
    }


    public String getDefault_commission() {
        return default_commission;
    }

    public void setBiller_name(String biller_name) {
        this.biller_name = biller_name;
    }


    public String getBiller_name() {
        return biller_name;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }


    public String getItem_code() {
        return item_code;
    }

    public void setTest(String test) {
        this.test = test;
    }


    public String getSecret_key() {
        return secret_key;
    }



    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }


    public String getService() {
        return service;
    }


    public void setService(String service) {
        this.service = service;
    }


    public String getService_method() {
        return service_method;
    }


    public void setService_method(String service_method) {
        this.service_method = service_method;
    }


    public String getService_version() {
        return service_version;
    }


    public void setService_version(String service_version) {
        this.service_version = service_version;
    }


    public String getService_channel() {
        return service_channel;
    }


    public void setService_channel(String service_channel) {
        this.service_channel = service_channel;
    }


    public String getCountry() {
        return Country;
    }


    public void setCountry(String Country) {
        this.Country = Country;
    }


    public String getCustomerId() {
        return CustomerId;
    }


    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }


    public String getReference() {
        return Reference;
    }


    public void setReference(String Reference) {
        this.Reference = Reference;
    }


    public int getAmount() {
        return Amount;
    }


    public void setAmount(int Amount) {
        this.Amount = Amount;
    }


    public String getRecurringType() {
        return RecurringType;
    }


    public void setRecurringType(String RecurringType) {
        this.RecurringType = RecurringType;
    }


    public boolean isIsAirtime() {
        return IsAirtime;
    }


    public void setIsAirtime(boolean IsAirtime) {
        this.IsAirtime = IsAirtime;
    }


}
