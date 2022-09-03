package com.skylightapp.Adapters;

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
import com.skylightapp.Classes.AppCash;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MySkylightCashA extends RecyclerView.Adapter<MySkylightCashA.RecyclerViewHolder>{


    private ArrayList<AppCash> appCashArrayList;
    private Context mcontext;
    int resources;
    private List<AppCash> list = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static AdminUser adminUser;
    private Context context;
    private Context Mcontext;
    int position;
    private OnItemsClickListener listener;

    public MySkylightCashA(ArrayList<AppCash> reports, Context mcontext) {
        this.appCashArrayList = reports;
        this.mcontext = mcontext;
    }
    public MySkylightCashA(OnItemsClickListener onItemsClickListener, Context mcontext) {
        this.listener = onItemsClickListener;
        this.mcontext = mcontext;
    }
    public MySkylightCashA(OnItemsClickListener onItemsClickListener) {
        this.listener = onItemsClickListener;
    }
    public MySkylightCashA(View itemView, final Callback callback) {
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

    public MySkylightCashA(Context context, int resources, ArrayList<AppCash> appCashes) {
        this.appCashArrayList = appCashes;
        this.mcontext = context;
        this.resources = resources;

    }

    public MySkylightCashA(Context context, ArrayList<AppCash> deposits) {
        this.appCashArrayList = deposits;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skylight_sc_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        AppCash appCash = this.appCashArrayList.get(position);
        holder.skylightCashID.setText(MessageFormat.format("Skylight Cash ID:{0}", String.valueOf(appCash.getSkylightCashID())));
        holder.skylightCashDate.setText(MessageFormat.format("Collection date:{0}", appCash.getSkylightCashDate()));
        holder.cashAmount.setText(MessageFormat.format("Amount : NGN{0}", String.format("%.2f", appCash.getSkylightCashAmount())));
        holder.status.setText(MessageFormat.format("Status:{0}", appCash.getSkylightCashStatus()));
        holder.payer.setText(MessageFormat.format("Cash Collector:{0}", appCash.getSCPayer()));
        holder.payee.setText(MessageFormat.format("Payee:{0}",  appCash.getSCPayee()));
        holder.tc_Code.setText(MessageFormat.format("Code:{0}", appCash.getSkylightCashCode()));
        holder.from.setText(MessageFormat.format("From:{0}", "Me"));
        holder.to.setText(MessageFormat.format("To:{0}", appCash.getSkylightCashTo()));

        if (appCash.getSkylightCashStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.payer.setTextColor(Color.MAGENTA);
        } else if (appCash.getSkylightCashStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.payer.setTextColor(Color.RED);
        } else if (appCash.getSkylightCashStatus().equalsIgnoreCase("Unverified"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.payer.setTextColor(Color.RED);
        }


    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView payer, skylightCashDate, status, payee, reportAmountTendered;
        public AppCompatImageView icon;
        private TextView cashAmount, tc_Code,from,to, skylightCashID;
        public AppCompatButton btnMore;
        private OnItemsClickListener listener;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.teller_icon);
            skylightCashID = convertView.findViewById(R.id.iDtellerCash);
            payer = convertView.findViewById(R.id.tellerCashTeller);
            skylightCashDate = convertView.findViewById(R.id.tellerCDate);
            tc_Code = convertView.findViewById(R.id.tc_Code);
            cashAmount = convertView.findViewById(R.id.tellerCashAmount);
            payee = convertView.findViewById(R.id.tCPayee);
            from = convertView.findViewById(R.id.sc33From);
            to = convertView.findViewById(R.id.sc33To);
            status = convertView.findViewById(R.id.cashTcStatus);
            convertView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());

        }
    }


    @Override
    public int getItemCount() {
        return (null != appCashArrayList ? appCashArrayList.size() : 0);
    }

    public interface Callback {
        void onLongItemClick(View view, int position);

        void onTellerCashClick(long tcId, View view);
    }


    public AppCash getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<AppCash> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(AppCash appCash);
        void onItemClick(int adapterPosition);
    }
}
