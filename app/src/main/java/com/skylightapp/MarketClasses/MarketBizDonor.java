package com.skylightapp.MarketClasses;

import android.net.Uri;

import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Profile;

public class MarketBizDonor {
    private QBUser marketBizDQBUser;
    private Uri mBDonorLogo;
    private String mBDonorName;
    private Profile marketBizDProf;

    public QBUser getMarketBizDQBUser() {
        return marketBizDQBUser;
    }

    public void setMarketBizDQBUser(QBUser marketBizDQBUser) {
        this.marketBizDQBUser = marketBizDQBUser;
    }

    public Uri getmBDonorLogo() {
        return mBDonorLogo;
    }

    public void setmBDonorLogo(Uri mBDonorLogo) {
        this.mBDonorLogo = mBDonorLogo;
    }

    public String getmBDonorName() {
        return mBDonorName;
    }

    public void setmBDonorName(String mBDonorName) {
        this.mBDonorName = mBDonorName;
    }

    public Profile getMarketBizDProf() {
        return marketBizDProf;
    }

    public void setMarketBizDProf(Profile marketBizDProf) {
        this.marketBizDProf = marketBizDProf;
    }
}
