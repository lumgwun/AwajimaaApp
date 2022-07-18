package com.skylightapp.Inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.SuperAllSTList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class StockTransferAdapter extends RecyclerView.Adapter<StockTransferAdapter.RecyclerViewHolder> implements SpinnerAdapter,View.OnClickListener, Filterable {


    private ArrayList<StockTransfer> stocksArrayList;
    private Context mcontext;
    int resources;
    private List<StockTransfer> list = new ArrayList<>();
    private List<StockTransfer> stockTransferList;
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static AdminUser adminUser;
    private  StockTransfer stockTransfer;
    private Context context;
    private Context Mcontext;
    int position;
    private OnItemsClickListener listener;

    public StockTransferAdapter(ArrayList<StockTransfer> stocksArrayList, Context mcontext) {
        this.stocksArrayList = stocksArrayList;
        this.mcontext = mcontext;
    }
    public StockTransferAdapter(OnItemsClickListener onItemsClickListener, Context mcontext) {
        this.listener = onItemsClickListener;
        this.mcontext = mcontext;
    }
    public StockTransferAdapter(OnItemsClickListener onItemsClickListener) {
        this.listener = onItemsClickListener;
    }
    public StockTransferAdapter(View itemView, final Callback callback) {
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

    public StockTransferAdapter(Context context, int resources, ArrayList<StockTransfer> stocksArrayList) {
        this.stocksArrayList = stocksArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public StockTransferAdapter(Context context, ArrayList<StockTransfer> arrayList) {
        this.stocksArrayList = arrayList;
        this.mcontext = context;

    }

    public StockTransferAdapter(SuperAllSTList superAllSTList, ArrayList<StockTransfer> tellerSTArrayList) {
        this.stocksArrayList = tellerSTArrayList;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public StockTransferAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.st_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        StockTransfer stockTransfer = this.stocksArrayList.get(position);
        holder.stockID.setText(MessageFormat.format("ST. ID:{0}", String.valueOf(stockTransfer.getStockTransferID())));
        holder.stock_Code.setText(MessageFormat.format("Code:{0}", stockTransfer.getStockTransferCode()));
        holder.stocksName.setText(MessageFormat.format("Stock Item:{0}", stockTransfer.getStockName()));
        holder.stocksRecipient.setText(MessageFormat.format("Recipient:{0}", stockTransfer.getStockAccepter()));
        holder.stocksQty.setText(MessageFormat.format("Qty:{0}", stockTransfer.getStockItemQty()));
        holder.officeDate.setText(MessageFormat.format("Date : {0}", stockTransfer.getStockTransferDate()));
        holder.status.setText(MessageFormat.format("Status:{0}", stockTransfer.getStockStatus()));
        holder.fromTo.setText(MessageFormat.format("Type:{0}", stockTransfer.getStockFrom()+""+"to"+stockTransfer.getStockTo()));
        holder.stockTransferer.setText(MessageFormat.format("Transferer:{0}", stockTransfer.getStockTransferer()));


        /*if (stocks.getStockStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.stocksName.setTextColor(Color.MAGENTA);
        } else if (stocks.getStockStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.stocksName.setTextColor(Color.RED);
        } else if (stocks.getStockStatus().equalsIgnoreCase("Unverified"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.stocksName.setTextColor(Color.RED);
        }*/


    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(stockTransfer);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    list = stockTransferList;
                } else {
                    List<StockTransfer> filteredList = new ArrayList<>();
                    for (StockTransfer stockTransfer : stockTransferList) {
                        if (stockTransfer.getStockName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(stockTransfer);
                        }
                    }
                    list = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<StockTransfer>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView stocksName, stocksDate, status, stockTransferer, reportAmountTendered;
        public AppCompatImageView icon;
        private TextView officeDate, stock_Code, stocksRecipient, stocksQty, stockID ,fromTo;
        public AppCompatButton btnMore;
        private MyInventAdapter.OnItemsClickListener listener;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.st_icon);
            stockID = convertView.findViewById(R.id.stID);
            stocksName = convertView.findViewById(R.id.sT_Item);
            stocksDate = convertView.findViewById(R.id.stDate);
            stock_Code = convertView.findViewById(R.id.st_Code);
            stockTransferer = convertView.findViewById(R.id.st_transferer);
            stocksRecipient = convertView.findViewById(R.id.st_receiver);
            stocksQty = convertView.findViewById(R.id.st_qty);
            status = convertView.findViewById(R.id.st_Status);
            fromTo = convertView.findViewById(R.id.st_from_to);

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

        void onSTClick(long tcId, View view);
    }


    public StockTransfer getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<StockTransfer> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(StockTransfer stockTransfer);
        void onItemClick(int adapterPosition);
    }
}
