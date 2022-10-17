package com.skylightapp.MarketClasses;

import android.net.Uri;

import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Profile;

public class BizAdmin {
    private int bizAdminID;
    private QBUser bizAdminQBUser;
    private Uri bizAdminLogo;
    private Profile bizAdminProf;
    public BizAdmin () {
        super();

    }

    public int getBizAdminID() {
        return bizAdminID;
    }

    public void setBizAdminID(int bizAdminID) {
        this.bizAdminID = bizAdminID;
    }

    public QBUser getBizAdminQBUser() {
        return bizAdminQBUser;
    }

    public void setBizAdminQBUser(QBUser bizAdminQBUser) {
        this.bizAdminQBUser = bizAdminQBUser;
    }

    public Uri getBizAdminLogo() {
        return bizAdminLogo;
    }

    public void setBizAdminLogo(Uri bizAdminLogo) {
        this.bizAdminLogo = bizAdminLogo;
    }

    public Profile getBizAdminProf() {
        return bizAdminProf;
    }

    public void setBizAdminProf(Profile bizAdminProf) {
        this.bizAdminProf = bizAdminProf;
    }
}


