package com.skylightapp.Inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MyInventAdapter extends RecyclerView.Adapter<MyInventAdapter.RecyclerViewHolder>{


    private ArrayList<Stocks> stocksArrayList;
    private Context mcontext;
    int resources;
    private List<Stocks> list = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static AdminUser adminUser;
    private Context context;
    private Context Mcontext;
    int position;
    private OnItemsClickListener listener;

    public MyInventAdapter(ArrayList<Stocks> reports, Context mcontext) {
        this.stocksArrayList = reports;
        this.mcontext = mcontext;
    }
    public MyInventAdapter(OnItemsClickListener onItemsClickListener, Context mcontext) {
        this.listener = onItemsClickListener;
        this.mcontext = mcontext;
    }
    public MyInventAdapter(OnItemsClickListener onItemsClickListener) {
        this.listener = onItemsClickListener;
    }
    public MyInventAdapter(View itemView, final Callback callback) {
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

    public MyInventAdapter(Context context, int resources, ArrayList<Stocks> stocksArrayList) {
        this.stocksArrayList = stocksArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public MyInventAdapter(Context context, ArrayList<Stocks> arrayList) {
        this.stocksArrayList = arrayList;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public MyInventAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inv_row, parent, false);
        return new MyInventAdapter.RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyInventAdapter.RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        Stocks stocks = this.stocksArrayList.get(position);
        holder.stockID.setText(MessageFormat.format("Stocks ID:{0}", String.valueOf(stocks.getStockID())));
        holder.stocksDate.setText(MessageFormat.format("Collection date:{0}", stocks.getStockDate()));
        holder.stock_Code.setText(MessageFormat.format("Code:{0}", stocks.getStockCode()));
        holder.stocksName.setText(MessageFormat.format("Stock Name:{0}", stocks.getStockName()));
        holder.stocksType.setText(MessageFormat.format("Type:{0}", stocks.getStockType()));
        holder.stocksQty.setText(MessageFormat.format("Qty:{0}", stocks.getStockItemQty()));
        holder.officeBranch.setText(MessageFormat.format("Office : {0}", stocks.getStockLocation()));
        holder.status.setText(MessageFormat.format("Status:{0}", stocks.getStockStatus()));
        holder.stockAdmin.setText(MessageFormat.format("Status:{0}", stocks.getStockAccepter()));


        if (stocks.getStockStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.stocksName.setTextColor(Color.MAGENTA);
        } else if (stocks.getStockStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.stocksName.setTextColor(Color.RED);
        } else if (stocks.getStockStatus().equalsIgnoreCase("Unverified"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.stocksName.setTextColor(Color.RED);
        }


    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView stocksName, stocksDate, status, stockAdmin, reportAmountTendered;
        public AppCompatImageView icon;
        private TextView officeBranch, stock_Code, stocksType, stocksQty, stockID;
        public AppCompatButton btnMore;
        private OnItemsClickListener listener;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.stocks_icon);
            stockID = convertView.findViewById(R.id.stocksID);
            stocksName = convertView.findViewById(R.id.stocksNames);
            stocksDate = convertView.findViewById(R.id.stocksDate);
            stock_Code = convertView.findViewById(R.id.stocks_Code);
            officeBranch = convertView.findViewById(R.id.stocksOffice);
            stockAdmin = convertView.findViewById(R.id.stockAdmin);
            stocksType = convertView.findViewById(R.id.stocksType);
            stocksQty = convertView.findViewById(R.id.stocksQty);
            status = convertView.findViewById(R.id.stocksStatus);
            convertView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());

        }
    }


    @Override
    public int getItemCount() {
        return (null != stocksArrayList ? stocksArrayList.size() : 0);
    }

    public interface Callback {
        void onLongItemClick(View view, int position);

        void onTellerCashClick(long tcId, View view);
    }


    public Stocks getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<Stocks> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(Stocks stocks);
        void onItemClick(int adapterPosition);
    }
}
