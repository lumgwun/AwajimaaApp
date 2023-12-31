package com.skylightapp.MarketClasses;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Markets.ChatInfoActCon;
import com.skylightapp.Markets.MessageInfoActCon;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapterCon extends BaseAdapter {

    List<com.quickblox.users.model.QBUser> userList;
    ArrayList<com.quickblox.users.model.QBUser> qbUserArrayList;
    protected com.quickblox.users.model.QBUser currentUser;

    private Context context;
    protected QBUser currentUserAwajima;

    public UsersAdapterCon(Context context, List<com.quickblox.users.model.QBUser> users) {
        this.context = context;
        currentUser = QBChatService.getInstance().getUser();
        userList = users;
    }

    public UsersAdapterCon(MessageInfoActCon context, ArrayList<com.quickblox.users.model.QBUser> qbUsers) {
        this.context = context;
        currentUser = QBChatService.getInstance().getUser();
        qbUserArrayList = qbUsers;
    }

    public UsersAdapterCon(ChatInfoActCon chatInfoActCon, List<com.quickblox.users.model.QBUser> users) {
        this.context = context;
        currentUser = QBChatService.getInstance().getUser();
        userList = users;
    }

    public void addNewList(List<QBUser> users) {
        userList.clear();
        userList.addAll(users);
        for (QBUser user : users) {
            if (isUserMe(user)) {
                userList.remove(user);
            }
        }
        notifyDataSetChanged();
    }

    public void addUsersQ(List<com.quickblox.users.model.QBUser> users) {
        for (com.quickblox.users.model.QBUser user : users) {
            if (!userList.contains(user)) {
                userList.add(user);
            }
        }
        notifyDataSetChanged();

    }

    public void addUsersAwajima(List<QBUser> users) {
        for (QBUser currentUserAwajima : users) {
            if (!userList.contains(currentUserAwajima)) {
                userList.add(currentUserAwajima);
            }
        }
        notifyDataSetChanged();
    }

    public void removeUsers(List<QBUser> users) {
        for (QBUser user : users) {
            userList.remove(user);
        }
        notifyDataSetChanged();
    }

    public void clearList() {
        userList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        com.quickblox.users.model.QBUser user = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
            holder = new ViewHolder();
            holder.rootLayout = convertView.findViewById(R.id.item_root_layout);
            holder.userImageView = convertView.findViewById(R.id.image_user);
            holder.loginTextView = convertView.findViewById(R.id.text_user_login);
            holder.userCheckBox = convertView.findViewById(R.id.checkbox_user);
            holder.userAvatarTitle = convertView.findViewById(R.id.text_user_avatar_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String userName = TextUtils.isEmpty(user.getFullName()) ? user.getLogin() : user.getFullName();

        if (isUserMe(user)) {
            holder.loginTextView.setText(context.getString(R.string.placeholder_username_you, userName));
            holder.loginTextView.setTextColor(context.getResources().getColor(R.color.text_color_medium_grey));
        } else {
            holder.loginTextView.setText(userName);
            holder.loginTextView.setTextColor(context.getResources().getColor(R.color.text_color_black));
        }

        holder.userImageView.setBackground(UiUtils.getColorCircleDrawable(context, user.getId().hashCode()));
        holder.userCheckBox.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(user.getFullName())) {
            String avatarTitle = String.valueOf(user.getFullName().charAt(0)).toUpperCase();
            holder.userAvatarTitle.setText(avatarTitle);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public com.quickblox.users.model.QBUser getItem(int position) {
        return userList.get(position);
    }

    boolean isUserMe(com.quickblox.users.model.QBUser user) {
        return currentUser != null && currentUser.getId().equals(user.getId());
    }

    public void addUsers(List<com.quickblox.users.model.QBUser> users) {

    }

    static class ViewHolder {
        LinearLayout rootLayout;
        ImageView userImageView;
        TextView loginTextView;
        CheckBox userCheckBox;
        TextView userAvatarTitle;
    }
}
