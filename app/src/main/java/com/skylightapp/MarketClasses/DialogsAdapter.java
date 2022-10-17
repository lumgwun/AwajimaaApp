package com.skylightapp.MarketClasses;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.quickblox.chat.model.QBChatDialog;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@SuppressWarnings("deprecation")
public class DialogsAdapter extends BaseAdapter {
    private static final String MAX_MESSAGES_TEXT = "99+";
    private static final int MAX_MESSAGES = 99;

    private Context context;

    private List<QBChatDialog> dialogs;
    private boolean isSelectMode = false;
    private List<QBChatDialog> selectedItems = new ArrayList<>();
    private boolean hasStableIds;

    public DialogsAdapter(Context context, List<QBChatDialog> dialogs) {
        this.context = context;
        this.dialogs = dialogs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {


            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_dialog_new, parent, false);
            holder = new ViewHolder();
            holder.rootLayout = convertView.findViewById(R.id.rootAll);
            holder.dialogImageView = convertView.findViewById(R.id.image_dialog33_icon);
            holder.nameTextView = convertView.findViewById(R.id.text_dialog234_name);

            holder.lastMessageTextView = convertView.findViewById(R.id.text_dialog987_last_message);
            holder.unreadCounterTextView = convertView.findViewById(R.id.text_unread_counter456);

            holder.lastMessageTimeTextView = convertView.findViewById(R.id.text_last_msg345_time);
            holder.dialogAvatarTitle = convertView.findViewById(R.id.text_dialog345_avatar_title);
            holder.checkboxDialog = convertView.findViewById(R.id.check234box_dialogs);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QBChatDialog dialog = getItem(position);
        String nameWithoutSpaces = dialog.getName().replace(" ", "");
        StringTokenizer stringTokenizer = new StringTokenizer(nameWithoutSpaces, ",");
        String firstLetter = String.valueOf(stringTokenizer.nextToken().charAt(0));
        String avatarTitle = firstLetter;
        if (stringTokenizer.hasMoreTokens()) {
            String secondLetter = String.valueOf(stringTokenizer.nextToken().charAt(0));
            avatarTitle = firstLetter + secondLetter;
        }

        holder.dialogAvatarTitle.setText(avatarTitle);
        holder.dialogImageView.setImageDrawable(UiUtils.getColorCircleDrawable(position));
        holder.nameTextView.setText(QbDialogUtils.getDialogName(dialog));
        holder.lastMessageTextView.setText(prepareTextLastMessage(dialog));
        holder.lastMessageTimeTextView.setText(getDialogLastMessageTime(dialog.getLastMessageDateSent()));

        int unreadMessagesCount = getUnreadMsgCount(dialog);
        if (isSelectMode) {
            holder.checkboxDialog.setVisibility(View.VISIBLE);
            holder.lastMessageTimeTextView.setVisibility(View.INVISIBLE);
            holder.unreadCounterTextView.setVisibility(View.INVISIBLE);
        } else if (unreadMessagesCount == 0) {
            holder.checkboxDialog.setVisibility(View.INVISIBLE);
            holder.lastMessageTimeTextView.setVisibility(View.VISIBLE);
            holder.unreadCounterTextView.setVisibility(View.INVISIBLE);
        } else {
            holder.checkboxDialog.setVisibility(View.INVISIBLE);
            holder.lastMessageTimeTextView.setVisibility(View.VISIBLE);
            holder.unreadCounterTextView.setVisibility(View.VISIBLE);
            String messageCount = (unreadMessagesCount > MAX_MESSAGES) ? MAX_MESSAGES_TEXT : String.valueOf(unreadMessagesCount);
            holder.unreadCounterTextView.setText(messageCount);
        }

        int backgroundColor;
        if (isItemSelected(position)) {
            holder.checkboxDialog.setChecked(true);
            backgroundColor = context.getResources().getColor(R.color.selected_list_item_color);
        } else {
            holder.checkboxDialog.setChecked(false);
            backgroundColor = context.getResources().getColor(android.R.color.transparent);
        }
        holder.rootLayout.setBackgroundColor(backgroundColor);

        return convertView;
    }

    @Override
    public QBChatDialog getItem(int position) {
        return dialogs.get(position);
    }

    @Override
    public long getItemId(int id) {
        return (long) id;
    }

    @Override
    public int getCount() {
        return dialogs != null ? dialogs.size() : 0;
    }



    private int getUnreadMsgCount(QBChatDialog chatDialog) {
        Integer unreadMessageCount = chatDialog.getUnreadMessageCount();
        if (unreadMessageCount == null) {
            unreadMessageCount = 0;
        }
        return unreadMessageCount;
    }


    private String prepareTextLastMessage(QBChatDialog chatDialog) {
        if (isLastMessageAttachment(chatDialog)) {
            return context.getString(R.string.chat_attachment);
        } else {
            return chatDialog.getLastMessage();
        }
    }


    public void updateList(List<QBChatDialog> dialogs) {
        this.dialogs = dialogs;
        notifyDataSetChanged();
    }

    public void selectItem(QBChatDialog item) {
        if (selectedItems.contains(item)) {
            return;
        }
        selectedItems.add(item);
        notifyDataSetChanged();
    }

    public void toggleSelection(QBChatDialog item) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item);
        } else {
            selectedItems.add(item);
        }
        notifyDataSetChanged();
    }

    public List<QBChatDialog> getSelectedItems() {
        return selectedItems;
    }
    private boolean isItemSelected(Integer position) {
        return !selectedItems.isEmpty() && selectedItems.contains(getItem(position));

    }



    private boolean isLastMessageAttachment(QBChatDialog dialog) {
        String lastMessage = dialog.getLastMessage();
        Integer lastMessageSenderId = dialog.getLastMessageUserId();
        return TextUtils.isEmpty(lastMessage) && lastMessageSenderId != null;
    }


    public void prepareToSelect() {
        isSelectMode = true;
        notifyDataSetChanged();
    }

    public void clearSelection() {
        isSelectMode = false;
        selectedItems.clear();
        notifyDataSetChanged();
    }


    private String getDialogLastMessageTime(long seconds) {
        long timeInMillis = seconds * 1000;
        Calendar msgTime = Calendar.getInstance();
        msgTime.setTimeInMillis(timeInMillis);

        if (timeInMillis == 0) {
            return "";
        }

        Calendar now = Calendar.getInstance();
        SimpleDateFormat timeFormatToday = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat dateFormatThisYear = new SimpleDateFormat("d MMM", Locale.ENGLISH);
        SimpleDateFormat lastYearFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);

        if (now.get(Calendar.DATE) == msgTime.get(Calendar.DATE) && now.get(Calendar.YEAR) == msgTime.get(Calendar.YEAR)) {
            return timeFormatToday.format(new Date(timeInMillis));
        } else if (now.get(Calendar.DAY_OF_YEAR) - msgTime.get(Calendar.DAY_OF_YEAR) == 1 && now.get(Calendar.YEAR) == msgTime.get(Calendar.YEAR)) {
            return context.getString(R.string.yesterday);
        } else if (now.get(Calendar.YEAR) == msgTime.get(Calendar.YEAR)) {
            return dateFormatThisYear.format(new Date(timeInMillis));
        } else {
            return lastYearFormat.format(new Date(timeInMillis));
        }
    }

    public void setHasStableIds(boolean hasStableIds) {
        this.hasStableIds = hasStableIds;
    }

    public boolean getHasStableIds() {
        return hasStableIds;
    }

    private static class ViewHolder {
        ViewGroup rootLayout;
        ImageView dialogImageView;
        TextView nameTextView;
        TextView lastMessageTextView;
        TextView unreadCounterTextView;
        TextView lastMessageTimeTextView;
        TextView dialogAvatarTitle;
        CheckBox checkboxDialog;
    }
}