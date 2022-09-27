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

public class DialogsAdapterCon extends BaseAdapter {
    private static final String MAX_MESSAGES_TEXT = "99+";
    private static final int MAX_MESSAGES = 99;

    private Context context;
    private List<QBChatDialog> dialogs;
    private boolean isSelectMode = false;
    private List<QBChatDialog> selectedItems = new ArrayList<>();

    public DialogsAdapterCon(Context context, List<QBChatDialog> dialogs) {
        this.context = context;
        this.dialogs = dialogs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_d_con, parent, false);
            holder = new ViewHolder();
            holder.rootLayout = convertView.findViewById(R.id.rootDialog);
            holder.dialogImageView = convertView.findViewById(R.id.image_dia_conf);
            holder.checkboxDialog = convertView.findViewById(R.id.check_dialogsCon);
            holder.nameTextView = convertView.findViewById(R.id.text_d_nameCon);
            holder.dialogAvatarTitle = convertView.findViewById(R.id.text_d_title_con);
            holder.lastMessageTextView = convertView.findViewById(R.id.text_d_last_mesC);
            holder.unreadCounterTextView = convertView.findViewById(R.id.unread_con);
            holder.lastMessageTimeTextView = convertView.findViewById(R.id.last_msg_time);


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
        holder.dialogImageView.setImageDrawable(UiUtils.getColorCircleDrawable(context, position));
        holder.nameTextView.setText(QBDialogUtilsCon.getDialogName(context, dialog));
        holder.lastMessageTextView.setText(prepareTextLastMessage(dialog));
        holder.lastMessageTimeTextView.setText(getDialogLastMessageTime(dialog.getLastMessageDateSent(), dialog.getName()));

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

    private boolean isItemSelected(Integer position) {
        return !selectedItems.isEmpty() && selectedItems.contains(getItem(position));
    }

    private int getUnreadMsgCount(QBChatDialog chatDialog) {
        Integer unreadMessageCount = chatDialog.getUnreadMessageCount();
        if (unreadMessageCount == null) {
            unreadMessageCount = 0;
        }
        return unreadMessageCount;
    }

    private boolean isLastMessageAttachment(QBChatDialog dialog) {
        String lastMessage = dialog.getLastMessage();
        Integer lastMessageSenderId = dialog.getLastMessageUserId();
        return TextUtils.isEmpty(lastMessage) && lastMessageSenderId != null;
    }

    private String prepareTextLastMessage(QBChatDialog chatDialog) {
        if (isLastMessageAttachment(chatDialog)) {
            return context.getString(R.string.chat_attachment);
        } else {
            return chatDialog.getLastMessage();
        }
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

    private String getDialogLastMessageTime(long seconds, String dialogName) {
        String result = "";
        long timeInMillis = seconds * 1000;
        Calendar msgTime = Calendar.getInstance();
        msgTime.setTimeInMillis(timeInMillis);

        if (timeInMillis == 0) {
            return result;
        }

        Calendar now = Calendar.getInstance();
        SimpleDateFormat timeFormatToday = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat dateFormatThisYear = new SimpleDateFormat("d MMM", Locale.ENGLISH);
        SimpleDateFormat lastYearFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);

        boolean sameYear = now.get(Calendar.YEAR) == msgTime.get(Calendar.YEAR);
        boolean sameMonth = now.get(Calendar.MONTH) == msgTime.get(Calendar.MONTH);
        boolean sameDay = now.get(Calendar.DATE) == msgTime.get(Calendar.DATE);
        boolean today = sameDay && sameMonth && sameYear;
        boolean yesterday = now.get(Calendar.DAY_OF_YEAR) - msgTime.get(Calendar.DAY_OF_YEAR) == 1 && sameYear;

        if (today) {
            result = timeFormatToday.format(new Date(timeInMillis));
        } else if (yesterday) {
            result = context.getString(R.string.yesterday);
        } else if (sameYear) {
            result = dateFormatThisYear.format(new Date(timeInMillis));
        } else {
            result = lastYearFormat.format(new Date(timeInMillis));
        }

        return result;
    }

    private class ViewHolder {
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
