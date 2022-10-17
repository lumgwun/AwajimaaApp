package com.skylightapp.MarketClasses;

import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Profile;

public class MarketAdmin {
    private  int marketAdminID;
    private  int marketAdminMarketID;
    private QBUser marketAdminQbUser;
    private Profile marketAdminProf;
    private Market marketAdminMarket;
    private String marketAdminName;

    public MarketAdmin () {
        super();

    }
    public Profile getMarketAdminProf() {
        return marketAdminProf;
    }

    public void setMarketAdminProf(Profile marketAdminProf) {
        this.marketAdminProf = marketAdminProf;
    }

    public int getMarketAdminID() {
        return marketAdminID;
    }

    public void setMarketAdminID(int marketAdminID) {
        this.marketAdminID = marketAdminID;
    }

    public QBUser getMarketAdminQbUser() {
        return marketAdminQbUser;
    }

    public void setMarketAdminQbUser(QBUser marketAdminQbUser) {
        this.marketAdminQbUser = marketAdminQbUser;
    }

    public int getMarketAdminMarketID() {
        return marketAdminMarketID;
    }

    public void setMarketAdminMarketID(int marketAdminMarketID) {
        this.marketAdminMarketID = marketAdminMarketID;
    }

    public Market getMarketAdminMarket() {
        return marketAdminMarket;
    }

    public void setMarketAdminMarket(Market marketAdminMarket) {
        this.marketAdminMarket = marketAdminMarket;
    }

    public String getMarketAdminName() {
        return marketAdminName;
    }

    public void setMarketAdminName(String marketAdminName) {
        this.marketAdminName = marketAdminName;
    }
}
