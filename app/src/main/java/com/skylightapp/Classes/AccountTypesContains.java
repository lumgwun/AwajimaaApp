package com.skylightapp.Classes;

public class AccountTypesContains {
    public static boolean contains(String account) {
        if (account != null) {
            for (AccountTypes accountType : AccountTypes.values()) {
                if (accountType.name().equals(account.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
