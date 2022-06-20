package com.skylightapp.Classes;

import android.content.Context;

import java.math.BigDecimal;

public class AccountSavings extends Account {

    public AccountSavings(String bank,String name, int dbID, long accountNo, BigDecimal accountBalance, String type, Context context) {
        this.enumMap = new AccountTypesEnumMap(context);
        this.setAcctID(dbID);
        this.setType_BigDecimal(this.enumMap.getAccountId("SAVING"));
        this.setName(name);
        this.setBalance(accountBalance);
    }
    public AccountSavings(){

    }
}
