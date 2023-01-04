package com.skylightapp.Classes;

import android.content.Context;

import java.math.BigDecimal;

public class AccountInvestment extends Account {

    private String acctInvName;
    private BigDecimal balance;

    public AccountInvestment(String bank, String name, int dbID, long accountNo, BigDecimal accountBalance, String type , Context context) {
        this.enumMap = new AccountTypesEnumMap(context);
        this.setAwajimaAcctNo(dbID);
        this.setType_BigDecimal(this.enumMap.getAccountId("INVESTMENT"));;
        this.setAcctInvName(name);
        this.setBalance(accountBalance);
    }
    public AccountInvestment(){

    }

    public void setAcctInvName(String acctInvName) {
        this.acctInvName = acctInvName;
    }

    public String getAcctInvName() {
        return acctInvName;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
