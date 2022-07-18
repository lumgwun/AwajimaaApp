package com.skylightapp.Interfaces;

public interface OnSavingsExistListener<CustomerDailyReport> {
    public void onDataChanged(boolean exist);
    void onError(String errorText);
    void onObjectChanged(CustomerDailyReport obj);
}
