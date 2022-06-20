package com.skylightapp.Classes;

import android.content.Context;

public class UserCreator {
    public static User makeUser(String userSurname, String firstName1, String phoneNumber, String email, String dob1, String selectedGender, String address1, String selectedState, String selectedOffice, String userName, String password, String s, String s1, String profilePicture, Context role)
            throws ConnectionFailedException {
        if (role.equals("ADMIN")) {
            return new AdminUser();
        } else if (role.equals("Customer Manager")) {
            return new CustomerManager();
        }
        return null;
    }
}
