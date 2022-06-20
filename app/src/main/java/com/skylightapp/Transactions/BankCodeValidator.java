package com.skylightapp.Transactions;

import java.util.regex.Pattern;

public class BankCodeValidator {
    //@Inject
    public BankCodeValidator() {
    }

    public boolean isBankCodeValid(String bankCode) {

        if (bankCode == null) {
            return false;
        } else {
            return Pattern.matches("\\d{3}", bankCode);
        }
    }
}
