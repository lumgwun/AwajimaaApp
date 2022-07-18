package com.skylightapp.Transactions;

public class SplitaddPayload {
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTransaction_split_ratio() {
        return transaction_split_ratio;
    }


    public void setTransaction_split_ratio(String transaction_split_ratio) {
        this.transaction_split_ratio = transaction_split_ratio;
    }

    private String id;
    private String transaction_split_ratio;
}
