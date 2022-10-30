package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.Inventory.MyInventAdapter;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MarketTranxAdapter extends RecyclerView.Adapter<MarketTranxAdapter.RecyclerViewHolder>{


    private ArrayList<MarketTranx> tranxArrayList;
    private Context mcontext;
    int resources;
    private List<MarketTranx> list = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static AdminUser adminUser;
    private Context context;
    private Context Mcontext;
    int position;
    private OnMarketTxItemsClickListener listener;

    public MarketTranxAdapter(ArrayList<MarketTranx> reports, Context mcontext) {
        this.tranxArrayList = reports;
        this.mcontext = mcontext;
    }
    public MarketTranxAdapter(OnMarketTxItemsClickListener onItemsClickListener, Context mcontext) {
        this.listener = onItemsClickListener;
        this.mcontext = mcontext;
    }
    public MarketTranxAdapter(OnMarketTxItemsClickListener onItemsClickListener) {
        this.listener = onItemsClickListener;
    }
    public MarketTranxAdapter(View itemView, final Callback callback) {
        super();

        this.callback = callback;
        this.context = itemView.getContext();

        if (callback != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (position != RecyclerView.NO_POSITION) {
                        callback.onLongItemClick(v, position);
                        return true;
                    }

                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public MarketTranxAdapter(Context context, int resources, ArrayList<MarketTranx> tranxArrayList) {
        this.tranxArrayList = tranxArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public MarketTranxAdapter(Context context, ArrayList<MarketTranx> arrayList) {
        this.tranxArrayList = arrayList;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public MarketTranxAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_tx, parent, false);
        return new MarketTranxAdapter.RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        MarketTranx marketTranx = this.tranxArrayList.get(position);
        holder.tranxID.setText(MessageFormat.format("Tranx ID:{0}", String.valueOf(marketTranx.getMarketTID())));
        holder.txDate.setText(MessageFormat.format("Tranx date:{0}", marketTranx.getMarketTDate()));
        holder.tranx_Code.setText(MessageFormat.format("Code:{0}", marketTranx.getMarketTCode()));
        holder.tranxTittle.setText(MessageFormat.format("Tranx Name:{0}", marketTranx.getMarketTTittle()));
        holder.marketTxsType.setText(MessageFormat.format("Type:{0}", marketTranx.getMarketTType()));
        holder.marketTxAmount.setText(MessageFormat.format("Amt:{0}", marketTranx.getMarketTAmountTotal()));
        holder.officeBranch.setText(MessageFormat.format("Office : {0}", marketTranx.getMarketTOffice()));
        holder.status.setText(MessageFormat.format("Status:{0}", marketTranx.getMarketTStatus()));
        holder.txApprover.setText(MessageFormat.format("From:{0}", marketTranx.getMarketTFrom()));


        if (marketTranx.getMarketTStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.tranxTittle.setTextColor(Color.MAGENTA);
        } else if (marketTranx.getMarketTStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.tranxTittle.setTextColor(Color.RED);
        } else if (marketTranx.getMarketTStatus().equalsIgnoreCase("Unverified"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.tranxTittle.setTextColor(Color.RED);
        }


    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public AppCompatTextView tranxTittle, txDate, status, txApprover;
        public AppCompatImageView icon;
        private AppCompatTextView officeBranch, tranx_Code, marketTxsType, marketTxAmount, tranxID;
        public AppCompatButton btnMore;
        private MyInventAdapter.OnItemsClickListener listener;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.inss_icon);
            tranxID = convertView.findViewById(R.id.ins_tx_ID);
            tranxTittle = convertView.findViewById(R.id.ins_tx_Tittle);
            txDate = convertView.findViewById(R.id.date_ins_tx);
            tranx_Code = convertView.findViewById(R.id.ins_code_tx);
            officeBranch = convertView.findViewById(R.id.office_ins);
            txApprover = convertView.findViewById(R.id.approver_ins);
            marketTxsType = convertView.findViewById(R.id.ins_tx_Type);
            marketTxAmount = convertView.findViewById(R.id.ins_Amt_tx);
            status = convertView.findViewById(R.id.ins_status_tx);
            convertView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());

        }
    }


    @Override
    public int getItemCount() {
        return (null != tranxArrayList ? tranxArrayList.size() : 0);
    }

    public interface Callback {
        void onLongItemClick(View view, int position);

        void onMarketTranxClick(long tcId, View view);
    }


    public MarketTranx getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<MarketTranx> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnMarketTxItemsClickListener{
        void onMtItemClick(MarketTranx marketTranx);
        void onPosItemClick(int adapterPosition);
    }
}
