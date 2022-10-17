package com.skylightapp.MarketClasses;

import android.net.Uri;

import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Profile;

public class MarketBizPartner {
    private QBUser marketBizPQBUser;
    private Uri bizPartnerLogo;
    private String bizPartnerName;
    private Profile bizPartnerProf;

    public MarketBizPartner () {
        super();

    }

    public QBUser getMarketBizPQBUser() {
        return marketBizPQBUser;
    }

    public void setMarketBizPQBUser(QBUser marketBizPQBUser) {
        this.marketBizPQBUser = marketBizPQBUser;
    }

    public Uri getBizPartnerLogo() {
        return bizPartnerLogo;
    }

    public void setBizPartnerLogo(Uri bizPartnerLogo) {
        this.bizPartnerLogo = bizPartnerLogo;
    }

    public String getBizPartnerName() {
        return bizPartnerName;
    }

    public void setBizPartnerName(String bizPartnerName) {
        this.bizPartnerName = bizPartnerName;
    }

    public Profile getBizPartnerProf() {
        return bizPartnerProf;
    }

    public void setBizPartnerProf(Profile bizPartnerProf) {
        this.bizPartnerProf = bizPartnerProf;
    }
}
