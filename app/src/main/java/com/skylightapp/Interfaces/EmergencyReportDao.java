package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;


import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.Classes.Profile;

import java.util.List;

//@androidx.room.Dao
public interface EmergencyReportDao {
    //@Insert
    void insert(EmergencyReport emergencyReport);


    //@Update
    void update(EmergencyReport emergencyReport);

    //@Delete
    void delete(EmergencyReport emergencyReport);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllEmergencyReport();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<EmergencyReport>> getAllEmergencyReport();
}
