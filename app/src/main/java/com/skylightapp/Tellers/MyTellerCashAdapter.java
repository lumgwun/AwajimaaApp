package com.skylightapp.Tellers;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.Adapters.TellerCashAdapter;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.List;

public class MyTellerCashAdapter extends RecyclerView.Adapter<MyTellerCashAdapter.RecyclerViewHolder>{
    private Context mContext;
    private List<TellerCash> tellerCashList;
    private OnItemClickListener mListener;

    public MyTellerCashAdapter(Context context, List<TellerCash> tellerCashes) {
        mContext = context;
        tellerCashList = tellerCashes;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.my_tc_row, parent, false);
        return new MyTellerCashAdapter.RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        TellerCash tellerCash = tellerCashList.get(position);
        holder.nameTextView.setText(tellerCash.getTellerCashItemName());
        holder.amountTextView.setText(MessageFormat.format("Package Amount: N{0}", tellerCash.getTellerCashAmount()));
        holder.dateTextView.setText(MessageFormat.format("Amount Date{0}", tellerCash.getTellerCashDate()));
        holder.packageIdTextView.setText(MessageFormat.format("Package ID:{0}", tellerCash.getTellerCashPackageID()));
        holder.nameOfTellerTextView.setText(MessageFormat.format("Item Name{0}", tellerCash.getTellerCashTellerName()));
        holder.branchNameTextView.setText(MessageFormat.format("Branch{0}", tellerCash.getTellerBranchOffice()));
        holder.statusTextView.setText(MessageFormat.format("Status{0}", tellerCash.getTellerCashStatus()));


        Glide.with(holder.iconImageView).load(R.drawable.ic__category)
                .into(holder.iconImageView);

    }

    @Override
    public int getItemCount() {
        return (null != tellerCashList ? tellerCashList.size() : 0);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {


        public TextView nameTextView,packageIdTextView,statusTextView,nameOfTellerTextView,codeTextView,branchNameTextView, amountTextView,dateTextView;
        public AppCompatImageView iconImageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nameTextView =itemView.findViewById ( R.id.tc_ItemName111 );
            amountTextView = itemView.findViewById(R.id.cashTeller55Amount111);
            packageIdTextView = itemView.findViewById(R.id.tellerCashPID111);
            statusTextView = itemView.findViewById(R.id.tcStatus111);
            nameOfTellerTextView = itemView.findViewById(R.id.tellerCashTeller111);
            branchNameTextView = itemView.findViewById(R.id.tc_Branch111);
            dateTextView = itemView.findViewById(R.id.teller33CashDate111);
            iconImageView = itemView.findViewById(R.id.tc5_icon111);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add( Menu.NONE, 1, 1, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
