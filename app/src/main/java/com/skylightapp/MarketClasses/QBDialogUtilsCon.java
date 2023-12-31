package com.skylightapp.MarketClasses;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QBDialogUtilsCon {
    private static final String TAG = QBDialogUtilsCon.class.getSimpleName();


    public static QBChatDialog createDialog(List<QBUser> users, String chatName) {
        List<Integer> userIDs = new ArrayList<>();

        for (QBUser user : users) {
            userIDs.add(user.getId());
        }
        QBChatDialog dialog = new QBChatDialog();
        dialog.setName(chatName);
        dialog.setType(QBDialogType.GROUP);
        dialog.setOccupantsIds(userIDs);

        return dialog;
    }

    private static boolean isPrivateChat(List<QBUser> users) {
        return users.size() == 2;
    }

    public static List<com.quickblox.users.model.QBUser> getAddedUsers(Context context, QBUser currentUser, QBChatDialog dialog, List<QBUser> currentUsers) {
        return getAddedUsers(currentUser, getQbUsersFromQbDialog(context, dialog), currentUsers);
    }

    private static List<com.quickblox.users.model.QBUser> getAddedUsers(QBUser currentUser, List<QBUser> previousUsers, List<com.quickblox.users.model.QBUser> currentUsers) {
        List<com.quickblox.users.model.QBUser> addedUsers = new ArrayList<>();
        for (com.quickblox.users.model.QBUser user : currentUsers) {
            boolean wasInChatBefore = false;
            for (QBUser previousUser : previousUsers) {
                if (user.getId().equals(previousUser.getId())) {
                    wasInChatBefore = true;
                    break;
                }
            }
            if (!wasInChatBefore) {
                addedUsers.add(user);
            }
        }
        addedUsers.remove(currentUser);

        return addedUsers;
    }

    public static List<com.quickblox.users.model.QBUser> getRemovedUsers(Context context, QBUser currentUser, QBChatDialog dialog, List<QBUser> currentUsers) {
        return getRemovedUsers(currentUser, getQbUsersFromQbDialog(context, dialog), currentUsers);
    }

    private static List<com.quickblox.users.model.QBUser> getRemovedUsers(QBUser currentUser, List<QBUser> previousUsers, List<com.quickblox.users.model.QBUser> currentUsers) {
        List<com.quickblox.users.model.QBUser> removedUsers = new ArrayList<>();
        for (QBUser previousUser : previousUsers) {
            boolean isUserStillPresented = false;
            for (com.quickblox.users.model.QBUser user : currentUsers) {
                if (previousUser.getId().equals(user.getId())) {
                    isUserStillPresented = true;
                    break;
                }
            }
            if (!isUserStillPresented) {
                removedUsers.add(previousUser);
            }
        }
        removedUsers.remove(currentUser);

        return removedUsers;
    }

    public static void logDialogUsers(Context context, QBChatDialog qbDialog) {
        Log.v(TAG, "Dialog " + getDialogName(context, qbDialog));
        logUsersByIds(context, qbDialog.getOccupants());
    }

    public static void logUsers(List<com.quickblox.users.model.QBUser> users) {
        for (com.quickblox.users.model.QBUser user : users) {
            Log.i(TAG, user.getId() + " " + user.getFullName());
        }
    }

    private static void logUsersByIds(Context context, List<Integer> users) {
        for (Integer id : users) {
            QBUser user = ((AppConference) context).getQBUsersHolder().getUserById(id);
            Log.i(TAG, ((user != null && user.getId() != null) ? (user.getId() + " " + user.getLogin()) : "noId"));
        }
    }

    public static String getDialogName(Context context, QBChatDialog dialog) {
        if (dialog.getType().equals(QBDialogType.GROUP)) {
            return dialog.getName();
        } else {
            // It's a private dialog, let's use opponent's name as chat name
            Integer opponentId = dialog.getRecipientId();
            QBUser user = ((AppConference) context).getQBUsersHolder().getUserById(opponentId);
            if (user != null) {
                return TextUtils.isEmpty(user.getFullName()) ? user.getLogin() : user.getFullName();
            } else {
                return dialog.getName();
            }
        }
    }

    private static List<QBUser> getQbUsersFromQbDialog(Context context, QBChatDialog dialog) {
        List<QBUser> previousDialogUsers = new ArrayList<>();
        for (Integer id : dialog.getOccupants()) {
            QBUser user = ((AppConference) context).getQBUsersHolder().getUserById(id);
            if (user != null) {
                previousDialogUsers.add(user);
            }
        }
        return previousDialogUsers;
    }

    public static String getOccupantsIdsStringFromList(Collection<Integer> occupantIdsList) {
        return TextUtils.join(",", occupantIdsList);
    }

    public static String getOccupantsNamesStringFromList(Collection<QBUser> appServerUsers) {
        ArrayList<String> userNameList = new ArrayList<>();
        for (QBUser user : appServerUsers) {
            if (TextUtils.isEmpty(user.getFullName())) {
                userNameList.add(user.getLogin());
            } else {
                userNameList.add(user.getFullName());
            }
        }
        return TextUtils.join(", ", userNameList);
    }

    public static QBChatDialog buildPrivateChatDialog(String dialogId, Integer recipientId) {
        QBChatDialog chatDialog = DialogUtils.buildPrivateDialog(recipientId);
        chatDialog.setDialogId(dialogId);

        return chatDialog;
    }
}
