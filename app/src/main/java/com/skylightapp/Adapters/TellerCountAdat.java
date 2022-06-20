package com.skylightapp.Adapters;

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
import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.List;

public class TellerCountAdat extends RecyclerView.Adapter<TellerCountAdat.RecyclerViewHolder>{
    private Context mContext;
    private List<TellerCountData> countDataList;
    private OnItemClickListener mListener;

    public TellerCountAdat(Context context, List<TellerCountData> countData) {
        mContext = context;
        countDataList = countData;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.teller_count_row, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        TellerCountData countData = countDataList.get(position);
        holder.nameTextView.setText(countData.getTellerName());
        holder.totalText.setText(MessageFormat.format("Total: N{0}", countData.getCountData()));
        holder.durationText.setText(MessageFormat.format("Duration{0}", countData.getCountDuration()));
        holder.tellerIdText.setText(MessageFormat.format("Teller ID:{0}", countData.getTellerID()));


        Glide.with(holder.iconImageView).load(R.drawable.ic__category)
                .into(holder.iconImageView);

    }

    @Override
    public int getItemCount() {
        return (null != countDataList ? countDataList.size() : 0);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {


        public TextView nameTextView, tellerIdText,statusTextView,codeTextView,branchNameTextView, totalText, durationText;
        public AppCompatImageView iconImageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nameTextView =itemView.findViewById ( R.id.tellerCName );
            totalText = itemView.findViewById(R.id.tc_DTotal);
            tellerIdText = itemView.findViewById(R.id.tellerCID);
            durationText = itemView.findViewById(R.id.tellerDuration);
            iconImageView = itemView.findViewById(R.id.tc_ic);

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
