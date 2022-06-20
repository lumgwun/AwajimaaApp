package com.skylightapp.Transactions;

public class CapturePayload {
    public String getRef() {
        return ref;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the SECKEY
     */
    public String getSECKEY() {
        return SECKEY;
    }

    /**
     * @param SECKEY the SECKEY to set
     */
    public void setSECKEY(String SECKEY) {
        this.SECKEY = SECKEY;
    }

    /**
     * @return the flwRef
     */
    public String getFlwRef() {
        return flwRef;
    }

    /**
     * @param flwRef the flwRef to set
     */
    public void setFlwRef(String flwRef) {
        this.flwRef = flwRef;
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

    private String SECKEY;
    private String flwRef;
    private String amount;
    private String action;
    private String ref;

}
