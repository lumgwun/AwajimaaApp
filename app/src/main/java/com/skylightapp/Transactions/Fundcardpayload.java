package com.skylightapp.Transactions;

public class Fundcardpayload {
    public String getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(String test) {
        this.test = test;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the debit_currency
     */
    public String getDebit_currency() {
        return debit_currency;
    }

    /**
     * @param debit_currency the debit_currency to set
     */
    public void setDebit_currency(String debit_currency) {
        this.debit_currency = debit_currency;
    }

    /**
     * @return the secret_key
     */
    public String getSecret_key() {
        return secret_key;
    }

    /**
     * @param secret_key the secret_key to set
     */
    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

//    {
//	"id": "660bae3b-333c-410f-b283-2d181587247f",
//	"amount": "20",
//	"debit_currency": "USD",
//	"secret_key": "FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X"
//}

    private String id;
    private String amount;
    private String debit_currency;
    private String secret_key;
    private String test;
}
