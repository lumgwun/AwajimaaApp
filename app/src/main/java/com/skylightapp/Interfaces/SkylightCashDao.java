package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.Classes.AppCash;

import java.util.List;

@androidx.room.Dao
public interface SkylightCashDao {
    @Insert
    void insert(AppCash appCash);


    @Update
    void update(AppCash appCash);

    @Delete
    void delete(AppCash appCash);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllSkylightCash();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<AppCash>> getAllSkylightCash();
}
