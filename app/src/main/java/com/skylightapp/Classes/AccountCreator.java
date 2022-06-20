package com.skylightapp.Classes;

import android.content.Context;

import java.math.BigDecimal;

public class AccountCreator {
    public static Account makeAccount(String bank,String name, int dbID, long accountNo, BigDecimal accountBalance,  String type,Context context)
            throws ConnectionFailedException {
        if (type.equals("ITEM PURCHASE")) {
            return new AccountItemPurchase(bank,name, dbID, accountNo, accountBalance,  type, context);
        } else if (type.equals("SAVINGS")) {
            return new AccountSavings(bank,name, dbID, accountNo, accountBalance,  type, context);
        } else if (type.equals("PROMO")) {
            return new AccountPromo(bank,name, dbID, accountNo, accountBalance,  type, context);
        } else if (type.equals("INVESTMENT")) {
            return new AccountInvestment(bank,name, dbID, accountNo, accountBalance,  type, context);
        } else {
            return null;
        }
    }


}
