package com.skylightapp.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.R;
import com.skylightapp.Tellers.TellerCash;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TellerCashAdapter extends RecyclerView.Adapter<TellerCashAdapter.RecyclerViewHolder>{
    private Context mContext;
    private List<TellerCash> tellerCashList;
    private OnItemClickListener mListener;

    public TellerCashAdapter(Context context, List<TellerCash> tellerCashes) {
        mContext = context;
        tellerCashList = tellerCashes;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.tc_row, parent, false);
        return new RecyclerViewHolder(v);
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
        holder.codeTextView.setText(MessageFormat.format("Code{0}", tellerCash.getTellerCashCode()));
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
            nameTextView =itemView.findViewById ( R.id.tc_ItemName );
            amountTextView = itemView.findViewById(R.id.cashTeller55Amount);
            packageIdTextView = itemView.findViewById(R.id.tellerCashPID);
            statusTextView = itemView.findViewById(R.id.tcStatus);
            nameOfTellerTextView = itemView.findViewById(R.id.tellerCashTeller);
            codeTextView = itemView.findViewById(R.id.tc555Code);
            branchNameTextView = itemView.findViewById(R.id.tc_Branch);
            dateTextView = itemView.findViewById(R.id.teller33CashDate);
            iconImageView = itemView.findViewById(R.id.tc5_icon);

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
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
}
