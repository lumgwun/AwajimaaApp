package com.skylightapp.Interfaces;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;

import java.util.List;

@androidx.room.Dao
public interface TimeLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTimeLine(TimeLine timeLine);


    @Update
    void updateTimeLine(TimeLine timeLine);

    @Delete
    void deleteTimeLine(TimeLine timeLine);

    @Query("DELETE FROM TimeLine.timelineTable")
    void deleteAllTimeLine();

    @Query("SELECT * FROM TimeLine.timelineTable")
    LiveData<List<TimeLine>> getAllTimeLine();

}
