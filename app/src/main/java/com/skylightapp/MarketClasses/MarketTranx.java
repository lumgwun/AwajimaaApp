package com.skylightapp.MarketClasses;

public class MarketTranx {
    private int marketTID;
    private int marketTBizID;
    private  int marketTMarketID;
    private int marketTProfID;
    private int marketTFromProfID;
    private int marketTToProfID;
    private int marketTFromAcctID;
    private int marketTToAcctID;
    private double marketTAmountTotal;
    private String marketTTittle;
    private String marketTFrom;
    private String marketTTo;
    private String marketTDate;
    private int marketTCode;
    private String marketTStatus;
    private String marketTType;
    private String marketTOffice;
    private String marketTCurrency;
    private String marketTMethod;
    private String marketTApprover;

    public MarketTranx () {
        super();

    }


    public MarketTranx(int id, String title, double amount, String from, String to, int code, String date, String status) {
        this.marketTID=id;
        this.marketTTittle=title;
        this.marketTFrom=from;
        this.marketTAmountTotal=amount;
        this.marketTTo=to;
        this.marketTCode=code;
        this.marketTDate=date;
        this.marketTStatus=status;
    }

    public MarketTranx(int profID, int bizID, int tranxID,int marketID, String tittle,String modeOfPayment, String payer, String payee, String tranxDate, double amount, String currencyCode, String approver, String tranxType,String office, String status) {
        this.marketTID=tranxID;
        this.marketTTittle=tittle;
        this.marketTFrom=payer;
        this.marketTAmountTotal=amount;
        this.marketTTo=payee;
        this.marketTCode=profID;
        this.marketTCode=bizID;
        this.marketTMethod=modeOfPayment;
        this.marketTCurrency=currencyCode;
        this.marketTApprover=approver;
        this.marketTType=tranxType;
        this.marketTOffice=office;
        this.marketTDate=tranxDate;
        this.marketTStatus=status;
        this.marketTMarketID=marketID;
    }

    public int getMarketTID() {
        return marketTID;
    }

    public void setMarketTID(int marketTID) {
        this.marketTID = marketTID;
    }

    public int getMarketTBizID() {
        return marketTBizID;
    }

    public void setMarketTBizID(int marketTBizID) {
        this.marketTBizID = marketTBizID;
    }

    public int getMarketTMarketID() {
        return marketTMarketID;
    }

    public void setMarketTMarketID(int marketTMarketID) {
        this.marketTMarketID = marketTMarketID;
    }

    public int getMarketTProfID() {
        return marketTProfID;
    }

    public void setMarketTProfID(int marketTProfID) {
        this.marketTProfID = marketTProfID;
    }

    public int getMarketTFromProfID() {
        return marketTFromProfID;
    }

    public void setMarketTFromProfID(int marketTFromProfID) {
        this.marketTFromProfID = marketTFromProfID;
    }

    public int getMarketTToProfID() {
        return marketTToProfID;
    }

    public void setMarketTToProfID(int marketTToProfID) {
        this.marketTToProfID = marketTToProfID;
    }

    public int getMarketTFromAcctID() {
        return marketTFromAcctID;
    }

    public void setMarketTFromAcctID(int marketTFromAcctID) {
        this.marketTFromAcctID = marketTFromAcctID;
    }

    public int getMarketTToAcctID() {
        return marketTToAcctID;
    }

    public void setMarketTToAcctID(int marketTToAcctID) {
        this.marketTToAcctID = marketTToAcctID;
    }

    public double getMarketTAmountTotal() {
        return marketTAmountTotal;
    }

    public void setMarketTAmountTotal(double marketTAmountTotal) {
        this.marketTAmountTotal = marketTAmountTotal;
    }

    public String getMarketTFrom() {
        return marketTFrom;
    }

    public void setMarketTFrom(String marketTFrom) {
        this.marketTFrom = marketTFrom;
    }

    public String getMarketTTo() {
        return marketTTo;
    }

    public void setMarketTTo(String marketTTo) {
        this.marketTTo = marketTTo;
    }

    public String getMarketTStatus() {
        return marketTStatus;
    }

    public void setMarketTStatus(String marketTStatus) {
        this.marketTStatus = marketTStatus;
    }

    public String getMarketTTittle() {
        return marketTTittle;
    }

    public void setMarketTTittle(String marketTTittle) {
        this.marketTTittle = marketTTittle;
    }

    public String getMarketTDate() {
        return marketTDate;
    }

    public void setMarketTDate(String marketTDate) {
        this.marketTDate = marketTDate;
    }

    public int getMarketTCode() {
        return marketTCode;
    }

    public void setMarketTCode(int marketTCode) {
        this.marketTCode = marketTCode;
    }


    public String getMarketTType() {
        return marketTType;
    }

    public void setMarketTType(String marketTType) {
        this.marketTType = marketTType;
    }

    public String getMarketTOffice() {
        return marketTOffice;
    }

    public void setMarketTOffice(String marketTOffice) {
        this.marketTOffice = marketTOffice;
    }

    public String getMarketTCurrency() {
        return marketTCurrency;
    }

    public void setMarketTCurrency(String marketTCurrency) {
        this.marketTCurrency = marketTCurrency;
    }

    public String getMarketTMethod() {
        return marketTMethod;
    }

    public void setMarketTMethod(String marketTMethod) {
        this.marketTMethod = marketTMethod;
    }

    public String getMarketTApprover() {
        return marketTApprover;
    }

    public void setMarketTApprover(String marketTApprover) {
        this.marketTApprover = marketTApprover;
    }
}
