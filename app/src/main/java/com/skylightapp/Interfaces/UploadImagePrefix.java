package com.skylightapp.Interfaces;

public enum UploadImagePrefix {
    PROFILE("profile_"), SAVINGS("savings"),PACKAGE("packages"),LOAN("loan"),DATA("data"),AIRTIME("airtime");

    String prefix;

    UploadImagePrefix(String prefix) {
        this.prefix = prefix;
    }

    private String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return getPrefix();
    }
}
