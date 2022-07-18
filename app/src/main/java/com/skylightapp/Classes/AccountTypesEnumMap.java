package com.skylightapp.Classes;

import android.content.Context;

import com.skylightapp.Database.DBHelper;

import java.util.EnumMap;


@SuppressWarnings("ConstantConditions")
public class AccountTypesEnumMap {
    final private static EnumMap<AccountTypes, Integer> accountsMap = new EnumMap<>(AccountTypes.class);
    final private DBHelper selector;

    public AccountTypesEnumMap(Context context) {
        selector = new DBHelper(context);
        //this.update();
    }



    public int getAccountId(String accountName) {
        return accountsMap.get(AccountTypes.valueOf(accountName));
    }
}
