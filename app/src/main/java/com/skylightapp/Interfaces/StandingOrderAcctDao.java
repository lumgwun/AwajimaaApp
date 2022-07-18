package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;

import java.util.List;

@androidx.room.Dao
public interface StandingOrderAcctDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSO(StandingOrderAcct standingOrderAcct);

    @Update
    void updateSO(StandingOrderAcct standingOrderAcct);

    @Delete
    void deleteSO(StandingOrderAcct standingOrderAcct);

    @Query("DELETE FROM StandingOrderAcct.SOAcctTable")
    void deleteAllSO();

    @Query("SELECT * FROM StandingOrderAcct.SOAcctTable")
    LiveData<List<StandingOrderAcct>> getAllSO();
}
