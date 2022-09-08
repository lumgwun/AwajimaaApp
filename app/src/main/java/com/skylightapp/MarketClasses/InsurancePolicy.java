package com.skylightapp.MarketClasses;

public class InsurancePolicy {
    private int insurID;
    private String insurTittle;
    private double insurAmt;
    private String insurDuration;
    private  String insurStartDate;
    private  String insurEndDate;
    private  String insurStatus;
    private InsuranceCompany insurInsurCom;

    public String getInsurTittle() {
        return insurTittle;
    }

    public void setInsurTittle(String insurTittle) {
        this.insurTittle = insurTittle;
    }

    public double getInsurAmt() {
        return insurAmt;
    }

    public void setInsurAmt(double insurAmt) {
        this.insurAmt = insurAmt;
    }

    public String getInsurDuration() {
        return insurDuration;
    }

    public void setInsurDuration(String insurDuration) {
        this.insurDuration = insurDuration;
    }

    public String getInsurStartDate() {
        return insurStartDate;
    }

    public void setInsurStartDate(String insurStartDate) {
        this.insurStartDate = insurStartDate;
    }

    public String getInsurEndDate() {
        return insurEndDate;
    }

    public void setInsurEndDate(String insurEndDate) {
        this.insurEndDate = insurEndDate;
    }

    public String getInsurStatus() {
        return insurStatus;
    }

    public void setInsurStatus(String insurStatus) {
        this.insurStatus = insurStatus;
    }

    public InsuranceCompany getInsurInsurCom() {
        return insurInsurCom;
    }

    public void setInsurInsurCom(InsuranceCompany insurInsurCom) {
        this.insurInsurCom = insurInsurCom;
    }

    public int getInsurID() {
        return insurID;
    }

    public void setInsurID(int insurID) {
        this.insurID = insurID;
    }
}
