package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;


import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;

import java.util.List;

//@androidx.room.Dao
public interface StandingOrderDao {
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StandingOrder standingOrder);


    //@Update
    void update(StandingOrder standingOrder);

    //@Delete
    void delete(StandingOrder standingOrder);

    //@Query("DELETE FROM StandingOrder.standingOrderTable")
    void deleteAllStandingOrders();


    //@Query("SELECT * FROM StandingOrder.standingOrderTable")
    LiveData<List<StandingOrder>> getAllStandingOrders();
}
