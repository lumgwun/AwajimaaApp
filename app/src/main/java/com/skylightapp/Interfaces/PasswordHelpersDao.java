package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;

import java.util.List;

@androidx.room.Dao
public interface PasswordHelpersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPassword(int profileID,int cusID,String password);

    @Update
    void updatePassword(int profileID,int cusID,String password);

    @Delete
    void deletePassword(int profileID,int cusID,String password);

}
