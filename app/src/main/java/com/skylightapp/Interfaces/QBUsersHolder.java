package com.skylightapp.Interfaces;

import com.quickblox.users.model.QBUser;

import java.util.List;

public interface QBUsersHolder {
    void putUser(QBUser user);

    void putUsers(List<QBUser> users);

    com.skylightapp.MarketClasses.QBUser getUserById(int userID);

    List<QBUser> getUsersByIds(List<Integer> usersIDs);

    boolean hasAllUsers(List<Integer> usersIDs);
}
