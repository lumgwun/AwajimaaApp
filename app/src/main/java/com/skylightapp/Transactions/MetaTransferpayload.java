package com.skylightapp.Transactions;

public class MetaTransferpayload {
    private String AccountNumber;
    private String RoutingNumber;
    private String SwiftCode;
    private String BankName;
    private String BeneficiaryName;
    private String BeneficiaryAddress;
    private String BeneficiaryCountry;
    public String getAccountNumber() {
        return AccountNumber;
    }


    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }


    public String getRoutingNumber() {
        return RoutingNumber;
    }


    public void setRoutingNumber(String RoutingNumber) {
        this.RoutingNumber = RoutingNumber;
    }

    public String getSwiftCode() {
        return SwiftCode;
    }


    public void setSwiftCode(String SwiftCode) {
        this.SwiftCode = SwiftCode;
    }


    public String getBankName() {
        return BankName;
    }


    public void setBankName(String BankName) {
        this.BankName = BankName;
    }


    public String getBeneficiaryName() {
        return BeneficiaryName;
    }


    public void setBeneficiaryName(String BeneficiaryName) {
        this.BeneficiaryName = BeneficiaryName;
    }


    public String getBeneficiaryAddress() {
        return BeneficiaryAddress;
    }


    public void setBeneficiaryAddress(String BeneficiaryAddress) {
        this.BeneficiaryAddress = BeneficiaryAddress;
    }


    public String getBeneficiaryCountry() {
        return BeneficiaryCountry;
    }

    public void setBeneficiaryCountry(String BeneficiaryCountry) {
        this.BeneficiaryCountry = BeneficiaryCountry;
    }

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


}
