package com.skylightapp.Transactions;

public class Pay_load {
    private String PBFPubKey;
    private String client;
    private String alg;
    public String getPBFPubKey() {
        return PBFPubKey;
    }


    public void setPBFPubKey(String PBFPubKey) {
        this.PBFPubKey = PBFPubKey;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the alg
     */
    public String getAlg() {
        return alg;
    }

    /**
     * @param alg the alg to set
     */
    public void setAlg(String alg) {
        this.alg = alg;
    }


}
