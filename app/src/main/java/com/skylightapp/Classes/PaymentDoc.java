package com.skylightapp.Classes;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = PaymentDoc.DOCUMENT_TABLE)
public class PaymentDoc extends CustomerDailyReport{

    @PrimaryKey(autoGenerate = true)
    private int documentId=211;
    private String docTittle;
    private String docStatus;
    private int docCustomerID;
    private int docReportID;
    private String paymentMethod;
    private Uri documentLink;
    Customer docCustomer;


    public static final String DOCUMENT_ID = "doc_id";
    public static final String DOCUMENT_TITLE = "doc_tittle";
    public static final String DOCUMENT_URI = "doc_uri";
    public static final String DOCUMENT_STATUS = "doc_status";
    public static final String DOCUMENT_PAYMENT_METHOD = "doc_payment_method";
    public static final String DOCUMENT_MANAGER = "doc_manager";
    public static final String DOCUMENT_TABLE = "Doc_table";
    public static final String DOCUMENT_PROF_ID = "Doc_Prof_ID";
    public static final String DOCUMENT_CUS_ID = "Doc_CUS_ID";
    public static final String DOCUMENT_REPORT_NO = "Doc_Rep_NO";

    public static final String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + DOCUMENT_TABLE + " (" + DOCUMENT_PROF_ID + " INTEGER , " + DOCUMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ DOCUMENT_REPORT_NO + " INTEGER , " +
            DOCUMENT_TITLE + " TEXT , " + DOCUMENT_PAYMENT_METHOD + " TEXT , "+ DOCUMENT_URI + " BLOB , " + DOCUMENT_STATUS + " TEXT , " + DOCUMENT_MANAGER + " TEXT , "+ DOCUMENT_CUS_ID + " INTEGER , "  + "FOREIGN KEY(" + DOCUMENT_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," + "FOREIGN KEY(" + DOCUMENT_REPORT_NO + ") REFERENCES " + DAILY_REPORT_TABLE + "(" + REPORT_ID + ")," +"FOREIGN KEY(" + DOCUMENT_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    public PaymentDoc(int id, String title, int docCustomerID, int docReportID, Uri documentLink, String docStatus) {
        this.documentId = id;
        this.docTittle = title;
        this.docCustomerID = docCustomerID;
        this.docReportID = docReportID;
        this.documentLink = documentLink;
        this.docStatus = docStatus;

    }

    public PaymentDoc() {
        super();
    }

    public String getDocStatus() { return docStatus; }
    public void setDocStatus(String docStatus) { this.docStatus = docStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDocTittle() { return docTittle; }
    public void setDocTittle(String docTittle) { this.docTittle = docTittle; }
    public Uri getDocumentLink() { return documentLink; }
    public void setDocumentLink(Uri documentLink) { this.documentLink = documentLink; }

    public int getDocumentId()
    { return documentId; }
    public void setDocumentId(int documentId) { this.documentId = documentId;
    }
    public Customer getDocCustomer()
    { return docCustomer; }
    public void setDocCustomer(Customer customer1) { this.docCustomer = customer1;
    }

    public PaymentDoc(int id, String title, int docCustomerID, int docReportID, Uri documentLink) {
        this.documentId = id;
        this.docTittle = title;
        this.docCustomerID = docCustomerID;
        this.docReportID = docReportID;
        this.documentLink = documentLink;
    }

    public PaymentDoc(int id, String title, String paymentMethod, int docCustomerID, int docReportID, Uri documentLink, String docStatus) {
        this.documentId = id;
        this.docTittle = title;
        this.docCustomerID = docCustomerID;
        this.docReportID = docReportID;
        this.paymentMethod = paymentMethod;
        this.documentLink = documentLink;
        this.docStatus = docStatus;
    }

    public int getDocCusID() {
        return docCustomerID;
    }

    public void setDocCusID(int customerID) {
        this.docCustomerID = customerID;
    }

    public int getDocReportID() {
        return docReportID;
    }

    public void setDocReportID(int reportID) {
        this.docReportID = reportID;
    }
}
