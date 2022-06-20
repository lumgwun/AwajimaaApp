package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.skylightapp.Classes.PaymentDoc;

import java.util.List;

@androidx.room.Dao
public interface PaymentDocDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPaymentDoc(PaymentDoc paymentDoc);


    @Update
    void updatePaymentDoc(PaymentDoc paymentDoc);

    @Delete
    void deletePaymentDoc(PaymentDoc paymentDoc);

    @Query("DELETE FROM PaymentDoc.Doc_table")
    void deleteAllPaymentDoc();


    @Query("SELECT * FROM PaymentDoc.Doc_table")
    LiveData<List<PaymentDoc>> getAllPaymentDoc();
}
