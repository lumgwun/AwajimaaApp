package com.skylightapp.Classes;

public class TwilloMessage {
    private String mobileNumber;
    private String smsText;

    public TwilloMessage() {
        super();
    }

    public TwilloMessage(String mobileNumber, String smsText) {
        super();
        this.mobileNumber = mobileNumber;
        this.smsText = smsText;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }
}
