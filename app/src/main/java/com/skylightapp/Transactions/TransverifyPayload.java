package com.skylightapp.Transactions;

public class TransverifyPayload {
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }


    public String getTxref() {
        return txref;
    }


    public void setTxref(String txref) {
        this.txref = txref;
    }


    public String getSECKEY() {
        return SECKEY;
    }

    public void setSECKEY(String SECKEY) {
        this.SECKEY = SECKEY;
    }

    private String txref;
    private String SECKEY;
    private String test;
}
