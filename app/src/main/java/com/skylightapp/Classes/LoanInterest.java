
package com.skylightapp.Classes;

import java.io.Serializable;

public class LoanInterest implements Serializable {

    public static final String INTEREST_ID = "int_id";
    public static final String INTEREST_RATE = "rate";
    public static final String INTEREST_TABLE = "interest_table";
    public static final String CREATE_INTEREST_TABLE = "CREATE TABLE " + INTEREST_TABLE + " (" + INTEREST_ID + " INTEGER PRIMARY KEY , " + INTEREST_RATE + " DOUBLE)";


    private long loanInterestId;
    private double interestRate;

    public long getLoanInterestId() {
        return loanInterestId;
    }

    public void setLoanInterestId(long loanInterestId) {
        this.loanInterestId = loanInterestId;
    }

    public double getInterest() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}








