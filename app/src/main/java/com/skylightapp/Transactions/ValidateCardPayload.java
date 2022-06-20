package com.skylightapp.Transactions;

public class ValidateCardPayload {
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
     * @return the PBFPubKey
     */
    public String getPBFPubKey() {
        return PBFPubKey;
    }

    /**
     * @param PBFPubKey the PBFPubKey to set
     */
    public void setPBFPubKey(String PBFPubKey) {
        this.PBFPubKey = PBFPubKey;
    }

    /**
     * @return the transaction_reference
     */
    public String getTransaction_reference() {
        return transaction_reference;
    }


    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }


    public String getOtp() {
        return otp;
    }


    public void setOtp(String otp) {
        this.otp = otp;
    }

    private String PBFPubKey;
    private String transaction_reference;
    private String otp;
    private String test;

}
