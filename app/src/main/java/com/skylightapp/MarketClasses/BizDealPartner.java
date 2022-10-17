package com.skylightapp.MarketClasses;

import com.quickblox.users.model.QBUser;

public class BizDealPartner {
    private QBUser user;
    private boolean isSelected = false;




    public BizDealPartner(QBUser user, boolean isSelected) {
        this.user = user;
        this.isSelected = isSelected;
    }

    public BizDealPartner() {
        super();
    }

    public QBUser getUser() {
        return user;
    }

    public void setUser(QBUser user) {
        this.user = user;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
