package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;


import com.skylightapp.Classes.Profile;
import com.skylightapp.Tellers.TellerCash;

import java.util.List;

public interface TellerCashDao {
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTellerCash(TellerCash tellerCash);


    //@Update
    void updateTellerCash(TellerCash tellerCash);

    //@Delete
    void deleteTellerCash(TellerCash tellerCash);

    //@Query("DELETE FROM TellerCash.tc_Table")
    void deleteAllTellerCash();


    //@Query("SELECT * FROM TellerCash.tc_Table")
    LiveData<List<TellerCash>> getAllTellerCash();
}
