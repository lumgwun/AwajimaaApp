package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.skylightapp.Classes.PaymentDoc;

import java.util.ArrayList;

import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_CUS_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_PAYMENT_METHOD;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_PROF_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_REPORT_NO;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_STATUS;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_TABLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_TITLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_URI;
import static com.skylightapp.Classes.TimeLine.TIMELINE_ID;
import static java.lang.String.valueOf;

public class PaymDocDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = DOCUMENT_ID
            + " =?";
    public PaymDocDAO(Context context) {
        super(context);
    }
    public long saveNewDocument1(PaymentDoc paymentDoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues documentValues = new ContentValues();
        documentValues.put(DOCUMENT_ID, valueOf(paymentDoc.getDocumentId()));
        documentValues.put(DOCUMENT_REPORT_NO, valueOf(paymentDoc.getRecordID()));
        documentValues.put(DOCUMENT_CUS_ID, valueOf(paymentDoc.getDocCusID()));
        documentValues.put(DOCUMENT_TITLE, valueOf(paymentDoc.getDocTittle()));
        documentValues.put(DOCUMENT_URI, valueOf(paymentDoc.getDocumentLink()));
        documentValues.put(DOCUMENT_STATUS, valueOf(paymentDoc.getDocStatus()));
        return db.insert(DOCUMENT_TABLE, null, documentValues);
        //db.close();
    }
    public void updateDocumentStatus(int documentId,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues documentUpdateValues = new ContentValues();
        documentUpdateValues.put(DOCUMENT_STATUS, status);
        String selection = DOCUMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(documentId)};
        db.update(DOCUMENT_TABLE, documentUpdateValues, selection, selectionArgs);
        //db.close();


    }
    public ArrayList<PaymentDoc> getDocumentsFromCurrentProfile1(int profileID) {
        ArrayList<PaymentDoc> documentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_REPORT_NO,DOCUMENT_CUS_ID,DOCUMENT_STATUS};

        String selection = DOCUMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(DOCUMENT_TABLE, columns,  selection, selectionArgs, null, null, null);


        getDocumentsFromCursor(documentArrayList, cursor);
        cursor.close();
        //db.close();

        return documentArrayList;
    }
    public ArrayList<PaymentDoc> getDocumentsFromCurrentCustomer(int customerID) {
        ArrayList<PaymentDoc> documentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_REPORT_NO,DOCUMENT_STATUS};

        String selection = DOCUMENT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(DOCUMENT_TABLE, columns,  selection, selectionArgs, null, null, null);

        getDocumentsFromCursor(documentArrayList, cursor);
        cursor.close();
        //db.close();

        return documentArrayList;
    }

    public Bitmap getDocPicture(int savingsId) {

        try {
            Uri picturePath = getDocPicturePath(savingsId);
            if (picturePath == null )
                return (null);

            return (BitmapFactory.decodeFile(String.valueOf(picturePath)));

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public PaymentDoc getDocumentFromSavings(int reportNo) {
        PaymentDoc paymentDoc = new PaymentDoc();
        Uri docUri = paymentDoc.getDocumentLink();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DOCUMENT_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(reportNo)};
        try (Cursor cursor = db.query(DOCUMENT_TABLE, new String[]{DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_STATUS, REPORT_ID,
                DOCUMENT_URI}, selection, selectionArgs, null, null, null, null)) {
            if (cursor != null)
                cursor.moveToFirst();

            return paymentDoc;
        }
    }
    public int getSavingsDocCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + DOCUMENT_TABLE;
        Cursor cursor=null;

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

    public Uri getDocPicturePath(int savingsId) {

        try {
            SQLiteDatabase db = getReadableDatabase();
            Uri picturePath;
            try (Cursor reportCursor = db.query(DOCUMENT_TABLE,
                    null,
                    DOCUMENT_REPORT_NO + "=?",
                    new String[]{String.valueOf(savingsId)},
                    null,
                    null,
                    null)) {
                reportCursor.moveToNext();
                int column_index = reportCursor.getColumnIndexOrThrow(DOCUMENT_URI);
                return Uri.parse(reportCursor.getString(column_index));

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        //return (picturePath);
        return null;
    }


    public PaymentDoc getDocumentsFromSavings(int savingsNo) {
        PaymentDoc paymentDoc = new PaymentDoc();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_REPORT_NO,DOCUMENT_CUS_ID,DOCUMENT_STATUS};
        String selection = DOCUMENT_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(savingsNo)};

        Cursor cursor = db.query(DOCUMENT_TABLE, columns,  selection, selectionArgs, null, null, null);
        getDocFromCursor(paymentDoc, cursor);
        cursor.close();
        return paymentDoc;
    }



    private void getDocumentsFromCursor(ArrayList<PaymentDoc> paymentDocs, Cursor cursor) {
        while (cursor.moveToNext()) {

            int id = (cursor.getInt(1));
            int report = cursor.getInt(2);
            String title = cursor.getString(3);
            Uri documentLink = Uri.parse(cursor.getString(5));
            String status = cursor.getString(6);
            int customerId = cursor.getInt(8);

            paymentDocs.add(new PaymentDoc(id, title, customerId, report, documentLink,status));
        }


    }

    private void getDocFromCursor(PaymentDoc paymentDoc, Cursor cursor) {
        while (cursor.moveToNext()) {

            int id = (cursor.getInt(1));
            int report = cursor.getInt(2);
            String title = cursor.getString(3);
            Uri documentLink = Uri.parse(cursor.getString(5));
            String status = cursor.getString(6);
            int customerId = cursor.getInt(8);
            paymentDoc =new PaymentDoc(id, title, customerId, report, documentLink,status);
        }
    }

    public ArrayList<PaymentDoc> getAllDocuments() {

        ArrayList<PaymentDoc> paymentDocs = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DOCUMENT_TABLE, null, null, null, null,
                null, null);
        getDocumentsFromCursor(paymentDocs, cursor);
        cursor.close();
        //db.close();

        return paymentDocs;
    }
    public void deletePaymentDoc(int docID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DOCUMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(docID)};


        db.delete(DOCUMENT_TABLE,
                selection, selectionArgs);

    }


    public Uri getDocumentFromSavings9(int reportNo) {
        Uri docBitmap = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DOCUMENT_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(reportNo)};

        Cursor cursor = db.query(DOCUMENT_TABLE, new String[]{DOCUMENT_URI}, selection, selectionArgs, null, null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                docBitmap= Uri.parse(cursor.getColumnName(5));

                cursor.close();
            }

        db.close();


        return docBitmap;
    }
    public void deleteDocument(int docID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = DOCUMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(docID)};

        int count =sqLiteDatabase.delete(DOCUMENT_TABLE ,selection,selectionArgs);

    }
    public ArrayList<PaymentDoc> getSavingsDocsCustomer(int customerID) {
        ArrayList<PaymentDoc> documents = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DOCUMENT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(DOCUMENT_TABLE, null, selection, selectionArgs, null,
                null, null);

        getAllDocuments();

        cursor.close();
        //db.close();

        return documents;
    }
    public void getSavingsDocsProfile(int profileID) {
        ArrayList<PaymentDoc> documents = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DOCUMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        String[] column = new String[]{DOCUMENT_ID,DOCUMENT_PAYMENT_METHOD,DOCUMENT_TITLE,DOCUMENT_URI};
        @SuppressLint("Recycle") Cursor cursor = db.query(DOCUMENT_TABLE, column, selection, selectionArgs, null,
                null, null);

        getAllDocuments();

        cursor.close();


    }


    public Uri getDocumentBitMap(int reportNo) {
        Uri docBitmap = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DOCUMENT_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(reportNo)};

        Cursor cursor = db.query(DOCUMENT_TABLE, new String[]{DOCUMENT_URI}, selection, selectionArgs, null, null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                docBitmap= Uri.parse(cursor.getColumnName(5));

                cursor.close();
            }

        db.close();


        return docBitmap;

    }

}
