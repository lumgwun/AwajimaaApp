package com.skylightapp.MapAndLoc;

public enum Gender {
    UNKNOWN, MALE, FEMALE;

    public static String getGenderText(int value) {
        switch (value) {
            case 0: {
                return "Unknown";
            }
            case 1: {
                return "Male";
            }
            case 2: {
                return "Female";
            }
            default: return "Unknown";
        }
    }
}
