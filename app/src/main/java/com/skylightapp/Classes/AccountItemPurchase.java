package com.skylightapp.Classes;

import android.content.Context;

import java.math.BigDecimal;

public class AccountItemPurchase extends Account {

    public AccountItemPurchase(String bank,String name, int dbID, long accountNo, BigDecimal accountBalance, String type, Context context) {
        this.enumMap = new AccountTypesEnumMap(context);
        this.setSkyLightAcctNo(dbID);
        this.setType_BigDecimal(this.enumMap.getAccountId("ITEM PURCHASE"));;
        this.setItemsAcctName(name);
        this.setItemAcctBalance(accountBalance);
    }

    private void setItemsAcctName(String name) {

    }

    public AccountItemPurchase(){

    }


}
