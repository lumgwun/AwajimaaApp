package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Admins.CusPackHistoryAct;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final ArrayList<Message> mValues;
    Context context;
    String statusSwitch1;
    private OnItemsClickListener listener;


    public MessageAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.mValues = messageArrayList;
    }
    public MessageAdapter(CusPackHistoryAct cusPackHistoryAct, ArrayList<Message> messageArrayList) {
        this.context = cusPackHistoryAct;
        this.mValues = messageArrayList;
    }


    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.message_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        Profile userProfile = new Profile();
        final Message message = mValues.get(position);
        holder.sender.setText(message.getMessageSender());
        holder.id.setText(message.getMessageID());
        holder.message.setText(message.getMessageDetails());
        holder.timesStamp.setText(message.getMessageTime());
        holder.profilePic.setImageURI(userProfile.getProfilePicture());
        holder.switchCompat.setText("");
        if (holder.switchCompat.isChecked())
            holder.switchCompat.setText("Read");
        else
            holder.switchCompat.setText("Not yet Read");
    }

    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView sender;
        public final TextView message;
        public final TextView id;
        public final TextView timesStamp;
        public final BezelImageView profilePic;
        public final SwitchCompat switchCompat;

        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sender = (TextView) view.findViewById(R.id.sender2);
            timesStamp = (TextView) view.findViewById(R.id.dateOfMessage2);
            message = (TextView) view.findViewById(R.id.message3);
            id = (TextView) view.findViewById(R.id.location);
            profilePic = (BezelImageView) view.findViewById(R.id.profile_pix);
            switchCompat = (SwitchCompat) view.findViewById(R.id.message_switch);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + message.getText() + "'";
        }
    }

    public void setUserList(ArrayList<? extends Message> users) {
        mValues.clear();
        mValues.addAll(users);
        notifyDataSetChanged();
    }

    public void addLast(Message message) {
        mValues.add(message);
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(Message message);
    }
}
