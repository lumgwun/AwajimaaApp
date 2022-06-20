package com.skylightapp.Classes;

import android.content.Context;

import java.math.BigDecimal;

public class AccountPromo extends Account {

    public AccountPromo (String bank,String name, int dbID, long accountNo, BigDecimal accountBalance, String type, Context context) {
        this.enumMap = new AccountTypesEnumMap(context);
        this.setAcctID(dbID);
        this.setType_BigDecimal(this.enumMap.getAccountId("PROMO"));;
        this.setName(name);
        this.setBalance(accountBalance);
    }
    public AccountPromo(){

    }
}
