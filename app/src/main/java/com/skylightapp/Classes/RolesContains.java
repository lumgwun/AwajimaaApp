package com.skylightapp.Classes;


import com.skylightapp.Enums.Roles;

public class RolesContains {
    public static boolean contains(String role) {
        if (role != null) {

            for (Roles roleName : Roles.values()) {
                if (roleName.name().equals(role.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
