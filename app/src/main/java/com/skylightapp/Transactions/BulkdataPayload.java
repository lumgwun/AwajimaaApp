package com.skylightapp.Transactions;

public class BulkdataPayload {
    private String Bank;
    private String Account_number;
    private String Amount;
    private String Currency;
    private String Narration;
    private String reference;
    public String getBank() {
        return Bank;
    }


    public void setBank(String Bank) {
        this.Bank = Bank;
    }


    public String getAccount_number() {
        return Account_number;
    }


    public void setAccount_number(String Account_number) {
        this.Account_number = Account_number;
    }


    public String getAmount() {
        return Amount;
    }


    public void setAmount(String Amount) {
        this.Amount = Amount;
    }


    public String getCurrency() {
        return Currency;
    }


    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }


    public String getNarration() {
        return Narration;
    }


    public void setNarration(String Narration) {
        this.Narration = Narration;
    }


    public String getReference() {
        return reference;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }

//    "bulk_data":[
//  	{
//        "Bank":"044",
//        "Account Number": "0690000032",
//        "Amount":500,
//        "Currency":"NGN",
//        "Narration":"Bulk transfer 1",
//        "reference": "mk-82973029"
//    },


}
