package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;

import java.util.List;

@androidx.room.Dao
public interface CustomerDao {
    @Insert
    void insert(Customer customer);


    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllCustomers();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<Customer>> getAllCustomers();
}
