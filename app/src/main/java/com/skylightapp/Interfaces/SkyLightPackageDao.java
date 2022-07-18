package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;

import java.util.List;

@androidx.room.Dao
public interface SkyLightPackageDao {
    @Insert
    void insert(SkyLightPackage skyLightPackage);


    @Update
    void update(SkyLightPackage skyLightPackage);

    @Delete
    void delete(SkyLightPackage skyLightPackage);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllSkyLightPackage();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<SkyLightPackage>> getAllSkyLightPackage();
}
