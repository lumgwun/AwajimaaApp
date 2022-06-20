package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;

import java.util.List;

@androidx.room.Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);


    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllTransactions();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<Transaction>> getAllTransaction();
}
