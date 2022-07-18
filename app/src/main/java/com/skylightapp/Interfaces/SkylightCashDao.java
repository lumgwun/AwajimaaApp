package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkylightCash;

import java.util.List;

@androidx.room.Dao
public interface SkylightCashDao {
    @Insert
    void insert(SkylightCash skylightCash);


    @Update
    void update(SkylightCash skylightCash);

    @Delete
    void delete(SkylightCash skylightCash);

    //@Query("DELETE FROM PROFILE_TABLE")
    void deleteAllSkylightCash();


    //@Query("SELECT * FROM PROFILE_TABLE ORDER BY courseName ASC")
    LiveData<List<SkylightCash>> getAllSkylightCash();
}
