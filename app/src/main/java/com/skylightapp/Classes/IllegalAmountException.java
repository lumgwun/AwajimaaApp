package com.skylightapp.Classes;

public class IllegalAmountException extends Exception {

    private static final long serialVersionUID = 1L;

    public IllegalAmountException() {
        super("");
    }

    public IllegalAmountException(String message) {
        super(message);
    }
}
