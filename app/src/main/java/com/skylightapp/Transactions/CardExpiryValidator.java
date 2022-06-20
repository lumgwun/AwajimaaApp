package com.skylightapp.Transactions;

import java.util.regex.Pattern;

public class CardExpiryValidator {
   // @Inject
    public CardExpiryValidator() {
    }

    public boolean isCardExpiryValid(String cardExpiry) {
        if (cardExpiry != null) {

            return Pattern.matches("\\d\\d/\\d\\d", cardExpiry);
        } else {
            return false;
        }

    }
}
