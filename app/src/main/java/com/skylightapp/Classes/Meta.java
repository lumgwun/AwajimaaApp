package com.skylightapp.Classes;

public class Meta {
    public Meta(String metaname, String metavalue) {
        this.metaname = metaname;
        this.metavalue = metavalue;
    }

    String metavalue;

    public String getMetavalue() {
        return metavalue;
    }

    public void setMetavalue(String metavalue) {
        this.metavalue = metavalue;
    }

    public String getMetaname() {
        return metaname;
    }

    public void setMetaname(String metaname) {
        this.metaname = metaname;
    }

    String metaname;
    public String getAccountNumber() {
        return AccountNumber;
    }

    /**
     * @param AccountNumber the AccountNumber to set
     */
    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    /**
     * @return the RoutingNumber
     */
    public String getRoutingNumber() {
        return RoutingNumber;
    }

    /**
     * @param RoutingNumber the RoutingNumber to set
     */
    public void setRoutingNumber(String RoutingNumber) {
        this.RoutingNumber = RoutingNumber;
    }

    /**
     * @return the SwiftCode
     */
    public String getSwiftCode() {
        return SwiftCode;
    }

    /**
     * @param SwiftCode the SwiftCode to set
     */
    public void setSwiftCode(String SwiftCode) {
        this.SwiftCode = SwiftCode;
    }

    /**
     * @return the BankName
     */
    public String getBankName() {
        return BankName;
    }

    /**
     * @param BankName the BankName to set
     */
    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    /**
     * @return the BeneficiaryName
     */
    public String getBeneficiaryName() {
        return BeneficiaryName;
    }

    /**
     * @param BeneficiaryName the BeneficiaryName to set
     */
    public void setBeneficiaryName(String BeneficiaryName) {
        this.BeneficiaryName = BeneficiaryName;
    }

    /**
     * @return the BeneficiaryAddress
     */
    public String getBeneficiaryAddress() {
        return BeneficiaryAddress;
    }

    /**
     * @param BeneficiaryAddress the BeneficiaryAddress to set
     */
    public void setBeneficiaryAddress(String BeneficiaryAddress) {
        this.BeneficiaryAddress = BeneficiaryAddress;
    }

    /**
     * @return the BeneficiaryCountry
     */
    public String getBeneficiaryCountry() {
        return BeneficiaryCountry;
    }

    /**
     * @param BeneficiaryCountry the BeneficiaryCountry to set
     */
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

    private String AccountNumber;
    private String RoutingNumber;
    private String SwiftCode;
    private String BankName;
    private String BeneficiaryName;
    private String BeneficiaryAddress;
    private String BeneficiaryCountry;
}
