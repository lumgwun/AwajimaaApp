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
public interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(Profile profile);

    @Update
    void updateProfile(Profile profile);

    @Delete
    void deleteProfile(Profile profile);

    @Query("DELETE FROM RoomProfileTable")
    void deleteAllProfiles();


    @Query("SELECT * FROM RoomProfileTable")
    LiveData<List<Profile>> getAllProfiles();

    @Query("SELECT count() AS " + "profile_count" + " FROM RoomProfileTable")
    long getProfileCount();
}

