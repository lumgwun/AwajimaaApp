package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;


import com.skylightapp.Classes.PaymentDoc;

import java.util.List;

public interface PaymentDocDao {
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPaymentDoc(PaymentDoc paymentDoc);


    //@Update
    void updatePaymentDoc(PaymentDoc paymentDoc);

    //@Delete
    void deletePaymentDoc(PaymentDoc paymentDoc);

    //@Query("DELETE FROM PaymentDoc.Doc_table")
    void deleteAllPaymentDoc();


    //@Query("SELECT * FROM PaymentDoc.Doc_table")
    LiveData<List<PaymentDoc>> getAllPaymentDoc();
}
