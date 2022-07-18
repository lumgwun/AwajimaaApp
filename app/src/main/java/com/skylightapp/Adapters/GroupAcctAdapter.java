package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Admins.GrpSavingsAct;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class GroupAcctAdapter  extends RecyclerView.Adapter<GroupAcctAdapter.ViewHolder>{
    private ArrayList<GroupAccount> groupAccountArrayList;
    private Context context;
    private ItemListener listener;
    int resources;
    public GroupAcctAdapter(Context context, ArrayList<GroupAccount> groupAccountArrayList, ItemListener listener) {
        this.context = context;
        this.groupAccountArrayList = groupAccountArrayList;
        this.listener = listener;
    }

    public GroupAcctAdapter(ArrayList<GroupAccount> groupAccountArrayList, int grp_savings_row, ItemListener listener) {
        this.context = context;
        this.groupAccountArrayList = groupAccountArrayList;
        this.listener = listener;
        this.resources = grp_savings_row;
    }

    public GroupAcctAdapter(GrpSavingsAct grpSavingsAct, int grp_acct_row, ArrayList<GroupAccount> groupAccountArrayList) {
        this.context = grpSavingsAct;
        this.groupAccountArrayList = groupAccountArrayList;
        this.resources = grp_acct_row;
    }

    public void setListener(ItemListener listener) {
        listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grp_acct_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GroupAccount model = groupAccountArrayList.get(position);
        ViewHolder mainHolder = (ViewHolder) holder;// holder
        mainHolder.txtID.setText(MessageFormat.format("Savings ID:{0}", model.getGrpAcctNo()));
        mainHolder.txtTittle.setText(MessageFormat.format("Group Title:N{0}", model.getGrpTittle()));
        mainHolder.txtPurpose.setText(MessageFormat.format("Purpose:{0}", model.getGrpPurpose()));
        mainHolder.txtNames.setText(MessageFormat.format("Name:{0}", model.getGrpLastName()+","+model.getGrpFirstName()));
        mainHolder.txtStartDate.setText(MessageFormat.format("Start Date:{0}", model.getGrpStartDate()));
        mainHolder.txtEndDate.setText(MessageFormat.format("End Date:{0}", model.getGrpEndDate()));
        mainHolder.txtBalance.setText(MessageFormat.format("Group Balance:N{0}", model.getGrpAcctBalance()));

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatImageView imageView;
        public TextView txtID, txtTittle, txtPurpose, txtNames,txtStartDate,txtEndDate, txtBalance;
        public GroupAccount item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.grpAcct0);
            txtID = (TextView) itemView.findViewById(R.id.grp_Acct_ID);
            txtTittle = (TextView) itemView.findViewById(R.id.grp_Acct_Tittle);
            txtPurpose = (TextView) itemView.findViewById(R.id.grp_Acct_Purpose);
            txtNames = (TextView) itemView.findViewById(R.id.grpAcctNames);
            txtStartDate = (TextView) itemView.findViewById(R.id.grpAcctSD);
            txtEndDate = (TextView) itemView.findViewById(R.id.grpAcctED);
            txtBalance = (TextView) itemView.findViewById(R.id.grpAcctBalance);
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(GroupAccount groupAccount);
    }

    @Override
    public int getItemCount() {
        return (null != groupAccountArrayList ? groupAccountArrayList.size() : 0);
    }

}
