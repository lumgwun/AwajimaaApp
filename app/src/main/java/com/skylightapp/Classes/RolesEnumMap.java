package com.skylightapp.Classes;

import android.content.Context;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.Enums.UserType;

import java.util.EnumMap;



public class RolesEnumMap {

    final private static EnumMap<UserType, Integer> rolesMap = new EnumMap<>(UserType.class);

    final private DBHelper selector;

    public RolesEnumMap(Context context) {
        selector = new DBHelper(context);
        //this.update();
    }


    /*public void update() {
        List<Integer> roleIds = (List<Integer>)  selector.getRoles();
        for (Integer roleId : roleIds) {
            // for all the roleIds, there are specific roles we want to add
            String role = selector.getRole(roleId);

            // add the value of the roleId into the enumMap for the specified role
            rolesMap.put(UserType.valueOf(role), roleId);
        }
    }*/


    public int getRoleId(String role) {
        return rolesMap.get(UserType.valueOf(role));
    }
}
