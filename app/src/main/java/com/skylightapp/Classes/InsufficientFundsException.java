package com.skylightapp.Classes;

public class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L;


    public InsufficientFundsException() {
        super("");
    }


    public InsufficientFundsException(String message) {
        super(message);
    }

}
