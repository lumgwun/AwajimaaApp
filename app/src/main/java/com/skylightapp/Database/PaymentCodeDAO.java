package com.skylightapp.Database;

import android.content.Context;

import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_ID;

public class PaymentCodeDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CUSTOMER_TELLER_ID
            + " =?";
    public PaymentCodeDAO(Context context) {
        super(context);
    }
}
