package com.skylightapp.Classes;

import android.content.Context;

import java.math.BigDecimal;

public class AccountSavings extends Account {

    public AccountSavings(String bank,String name, int dbID, long accountNo, BigDecimal accountBalance, String type, Context context) {
        this.enumMap = new AccountTypesEnumMap(context);
        this.setSkyLightAcctNo(dbID);
        this.setType_BigDecimal(this.enumMap.getAccountId("SAVING"));
        this.setSavingsAcctName(name);
        this.setSavingsAcctBalance(accountBalance);
    }

    private void setSavingsAcctName(String name) {

    }

    public AccountSavings(){

    }
}
