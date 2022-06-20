package com.skylightapp.Interfaces;

public interface OnTellerReportChangeListener<CustomerDailyReport> {
    public void onSavingsChanged(CustomerDailyReport obj);

    public void onError(String errorText);
}
