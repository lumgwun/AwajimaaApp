package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Profile;

import java.util.List;

@androidx.room.Dao
public interface AdminBankDepositDao {
    @Insert
    void insert(AdminBankDeposit adminBankDeposit);


    @Update
    void update(AdminBankDeposit adminBankDeposit);

    @Delete
    void delete(AdminBankDeposit adminBankDeposit);

    @Query("DELETE FROM AdminBankDeposit.DEPOSIT_TABLE")
    void deleteAllAdminBankDeposit();


    @Query("SELECT * FROM AdminBankDeposit.DEPOSIT_TABLE")
    LiveData<List<AdminBankDeposit>> getAllAdminBankDeposit();
}
