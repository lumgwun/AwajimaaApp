package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;

import java.util.List;

@androidx.room.Dao
public interface TellerReportDao {
    @Insert
    void insert(TellerReport tellerReport);


    @Update
    void update(TellerReport tellerReport);

    @Delete
    void delete(TellerReport tellerReport);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllProfiles();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<TellerReport>> getAllTC();
}
