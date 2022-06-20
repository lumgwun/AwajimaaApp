package com.skylightapp.Transactions;

public class TransferPayload {
    private String account_bank;
    private String account_number;
    private String amount;
    private String seckey;
    private String narration;
    private String currency;
    private String reference;
    private MetaTransferpayload meta;
    private String beneficiary_name;
    private String destination_branch_code;
    public String getAccount_bank() {
        return account_bank;
    }


    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }


    public String getAmount() {
        return amount;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getSeckey() {
        return seckey;
    }


    public void setSeckey(String seckey) {
        this.seckey = seckey;
    }


    public String getNarration() {
        return narration;
    }


    public void setNarration(String narration) {
        this.narration = narration;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getReference() {
        return reference;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }



    public MetaTransferpayload getMeta() {
        return meta;
    }


    public void setMeta(MetaTransferpayload meta) {
        this.meta = meta;
    }


    public String getBeneficiary_name() {
        return beneficiary_name;
    }


    public void setBeneficiary_name(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }


    public String getDestination_branch_code() {
        return destination_branch_code;
    }


    public void setDestination_branch_code(String destination_branch_code) {
        this.destination_branch_code = destination_branch_code;
    }

//    {
//  "account_bank": "044",
//  "account_number": "0690000044",
//  "amount": 500,
//  "seckey": "FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X",
//  "narration": "New transfer",
//  "currency": "NGN",
//  "reference": "mk-902837-jk",
//  "beneficiary_name": "Kwame Adew" // only pass this for non NGN
//}
//
//    {
//  "amount": 2000,
//  "seckey": "FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X",
//  "narration": "New transfer",
//  "currency": "USD",
//  "reference": "rave-transfer-15208080003460029376",
//  "beneficiary_name": "Mark Cuban"
//  "meta": [
//    {
//      "AccountNumber": "09182972BH",
//      "RoutingNumber": "0000000002993",
//      "SwiftCode": "ABJG190",
//      "BankName": "BANK OF AMERICA, N.A., SAN FRANCISCO, CA",
//      "BeneficiaryName": "Mark Cuban",
//      "BeneficiaryAddress": "San Francisco, 4 Newton",
//      "BeneficiaryCountry": "US"
//    }
//  ]
//}

//    {
//  "account_bank": "MPS",
//  "account_number": "2540782773934",
//  "amount": 50,
//  "seckey": "FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X",
//  "narration": "New transfer",
//  "currency": "KES",
//  "reference": "mk-902837-jk",
//  "beneficiary_name": "Kwame Adew" // only pass this for non NGN
//}


}
