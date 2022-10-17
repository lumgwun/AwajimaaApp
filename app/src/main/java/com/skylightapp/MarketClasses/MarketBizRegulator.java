package com.skylightapp.MarketClasses;

import android.net.Uri;

import com.skylightapp.Classes.Profile;

public class MarketBizRegulator {
    private Profile regulatorProf;
    private int regulatorID;
    private String reguName;
    private Uri regulatorLogo;

    public MarketBizRegulator () {
        super();

    }

    public Profile getRegulatorProf() {
        return regulatorProf;
    }

    public void setRegulatorProf(Profile regulatorProf) {
        this.regulatorProf = regulatorProf;
    }

    public int getRegulatorID() {
        return regulatorID;
    }

    public void setRegulatorID(int regulatorID) {
        this.regulatorID = regulatorID;
    }

    public String getReguName() {
        return reguName;
    }

    public void setReguName(String reguName) {
        this.reguName = reguName;
    }

    public Uri getRegulatorLogo() {
        return regulatorLogo;
    }

    public void setRegulatorLogo(Uri regulatorLogo) {
        this.regulatorLogo = regulatorLogo;
    }
}
