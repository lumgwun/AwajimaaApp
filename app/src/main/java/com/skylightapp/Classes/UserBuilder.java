package com.skylightapp.Classes;

import android.content.Context;

public class UserBuilder {
    private String userType;
    private USER_TYPE user_Type;
    private Profile userProfile;

    public UserBuilder() {
        super();

    }
    public UserBuilder(String userType) {
        this.userType = userType;

    }
    public UserBuilder(USER_TYPE userType) {
        this.user_Type = userType;

    }

    public USER_TYPE getUser_Type() {
        return user_Type;
    }

    public Profile setUser_Type(USER_TYPE user_Type) {
        this.user_Type = user_Type;
        return userProfile;
    }

    public enum USER_TYPE {
        CUSTOMER,DRIVER,OPERATOR,VIRTUAL_ASSISTANT
    }



    public static User makeUser(String userSurname, String firstName1, String phoneNumber, String email, String dob1, String selectedGender, String address1, String selectedState, String selectedOffice, String userName, String password, String s, String s1, String profilePicture, Context role)
            throws ConnectionFailedException {
        if (role.equals("ADMIN")) {
            //return new AdminUser();
        } else if (role.equals("Customer Manager")) {
            //return new CustomerManager();
        }
        return null;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
