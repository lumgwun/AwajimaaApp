package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.MarketClasses.MarketBizPackage;

import java.util.List;

@androidx.room.Dao
public interface SkyLightPackageDao {
    @Insert
    void insert(MarketBizPackage marketBizPackage);


    @Update
    void update(MarketBizPackage marketBizPackage);

    @Delete
    void delete(MarketBizPackage marketBizPackage);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllSkyLightPackage();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<MarketBizPackage>> getAllSkyLightPackage();
}
