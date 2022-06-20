package com.skylightapp.Classes;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = PaymentDoc.DOCUMENT_TABLE)
public class PaymentDoc extends CustomerDailyReport{

    @PrimaryKey(autoGenerate = true)
    private int documentId=211;
    private String tittle;
    private String status;
    private int customerId;
    private int reportId;
    private String paymentMethod;
    private Uri documentLink;
    Customer customer;


    public static final String DOCUMENT_ID = "doc_id";
    public static final String DOCUMENT_TITLE = "doc_tittle";
    public static final String DOCUMENT_URI = "doc_uri";
    public static final String DOCUMENT_STATUS = "doc_status";
    public static final String DOCUMENT_PAYMENT_METHOD = "payment_method";
    public static final String DOCUMENT_MANAGER = "doc_manager";
    public static final String DOCUMENT_TABLE = "Doc_table";

    public static final String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + DOCUMENT_TABLE + " (" + PROFILE_ID + " INTEGER , " + DOCUMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ REPORT_NUMBER + " INTEGER , " +
            DOCUMENT_TITLE + " TEXT , " + DOCUMENT_PAYMENT_METHOD + " TEXT , "+ DOCUMENT_URI + " BLOB , " + DOCUMENT_STATUS + " TEXT , " + DOCUMENT_MANAGER + " TEXT , " + "FOREIGN KEY(" + REPORT_NUMBER + ") REFERENCES " + DAILY_REPORT_TABLE + "(" + REPORT_NUMBER + ")," +"FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    public PaymentDoc(int id, String title, int customerId, int reportId, Uri documentLink, String status) {
        this.documentId = id;
        this.tittle = title;
        this.customerId = customerId;
        this.reportId = reportId;
        this.documentLink = documentLink;
        this.status = status;

    }

    public PaymentDoc() {
        super();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTittle() { return tittle; }
    public void setTittle(String tittle) { this.tittle = tittle; }
    public Uri getDocumentLink() { return documentLink; }
    public void setDocumentLink(Uri documentLink) { this.documentLink = documentLink; }

    public int getDocumentId()
    { return documentId; }
    public void setDocumentId(int documentId) { this.documentId = documentId;
    }
    public Customer getCustomer()
    { return customer; }
    public void setCustomer(Customer customer1) { this.customer = customer1;
    }

    public PaymentDoc(int id, String title, int customerId, int reportId, Uri documentLink) {
        this.documentId = id;
        this.tittle = title;
        this.customerId = customerId;
        this.reportId = reportId;
        this.documentLink = documentLink;
    }

    public PaymentDoc(int id, String title, String paymentMethod, int customerId, int reportId, Uri documentLink, String status) {
        this.documentId = id;
        this.tittle = title;
        this.customerId = customerId;
        this.reportId = reportId;
        this.paymentMethod = paymentMethod;
        this.documentLink = documentLink;
        this.status = status;
    }

}
