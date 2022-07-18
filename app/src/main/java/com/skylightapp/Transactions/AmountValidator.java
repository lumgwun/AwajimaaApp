package com.skylightapp.Transactions;

public class AmountValidator {
    //@Inject
    public AmountValidator() {
    }

    public boolean isAmountValid(Double amount) {
        return amount > 0;
    }


    public boolean isAmountValid(String amount) {

        try {
            if (amount != null) {
                if (!amount.isEmpty()) {
                    return Double.parseDouble(amount) > 0;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
