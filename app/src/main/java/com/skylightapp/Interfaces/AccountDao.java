package com.skylightapp.Interfaces;

import androidx.lifecycle.LiveData;

import com.skylightapp.Classes.Account;

import java.util.List;

//@androidx.room.Dao
public interface AccountDao {
    //@Insert
    void insert(Account account);


    //@Update
    void update(Account account);

    //@Delete
    void delete(Account account);

    //@Query("DELETE FROM Account.accounts")
    void deleteAllAccount();


    //@Query("SELECT * FROM Account.accounts")
    LiveData<List<Account>> getAllAccount();
}
