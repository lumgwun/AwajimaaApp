package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.GroupSavingsAcctList;
import com.skylightapp.MyGrpSavingsList;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class GroupSavingsAdapter extends RecyclerView.Adapter<GroupSavingsAdapter.ViewHolder>{
    private ArrayList<GroupSavings> groupSavingsArrayList;
    private Context context;
    private ItemListener listener;
    int resources;
    public GroupSavingsAdapter(Context context, ArrayList<GroupSavings> groupSavingsArrayList1, ItemListener  listener) {
        this.context = context;
        this.groupSavingsArrayList = groupSavingsArrayList1;
        this.listener = listener;
    }

    public GroupSavingsAdapter(ArrayList<GroupSavings> groupSavingsArrayList, int grp_savings_row, MyGrpSavingsList listener) {
        this.context = context;
        this.groupSavingsArrayList = groupSavingsArrayList;
        this.listener = listener;
        this.resources = grp_savings_row;
    }


    public void setListener(ItemListener listener) {
        listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grp_savings_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GroupSavings model = groupSavingsArrayList.get(position);
        ViewHolder mainHolder = (ViewHolder) holder;// holder
        mainHolder.setData(groupSavingsArrayList.get(position));
        mainHolder.txtID.setText(MessageFormat.format("Savings ID:{0}", model.getGrpSavingsID()));
        mainHolder.txtAmount.setText(MessageFormat.format("Savings Amount:N{0}", model.getGrpSavingsAmount()));
        mainHolder.txtDate.setText(MessageFormat.format("Savings Date:{0}", model.getSavingsDate()));
        mainHolder.txtStatus.setText(MessageFormat.format("Savings Status:{0}", model.getStatus()));

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatImageView imageView;
        public TextView txtID,txtAmount,txtDate,txtStatus;
        public GroupSavings item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.grpS09);
            txtID = (TextView) itemView.findViewById(R.id.grp_savings_ID);
            txtAmount = (TextView) itemView.findViewById(R.id.grp_savingsAmt);
            txtDate = (TextView) itemView.findViewById(R.id.grp_savings_Date);
            txtStatus = (TextView) itemView.findViewById(R.id.grpSavingsStatus);
        }

        public void setData(GroupSavings groupSavings) {
            this.item = groupSavings;
            txtID.setText("Grp. Savings ID:"+item.getGrpSavingsID());
            txtAmount.setText("Group Savings Amount:N"+item.getGrpSavingsAmount());
            txtDate.setText("Group Savings Date:"+item.getSavingsDate());
            txtStatus.setText("Group Savings Status:"+item.getStatus());
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(GroupSavings groupSavings);
    }

    @Override
    public int getItemCount() {
        return (null != groupSavingsArrayList ? groupSavingsArrayList.size() : 0);
    }

}
