package com.skylightapp.Interfaces;


import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Loan;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.StandingOrder;

public interface OnObjectChangedListener<Profile> {
    void onObjectChanged(Profile obj);
    void onSavingsChanged(CustomerDailyReport obj);
    void onPackageChanged(MarketBizPackage obj);
    void onLoanChanged(Loan obj);
    void onManagerChanged(AdminUser obj);
    void onPhoneNumberChanged(int phoneNo);
    void onStandingOrgerChanged(StandingOrder obj);
    void onError(String errorText);
}
