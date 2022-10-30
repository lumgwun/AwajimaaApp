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

public class SkylightCashAdapter extends RecyclerView.Adapter<SkylightCashAdapter.RecyclerViewHolder>{


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

    public SkylightCashAdapter(ArrayList<AppCash> reports, Context mcontext) {
        this.appCashArrayList = reports;
        this.mcontext = mcontext;
    }
    public SkylightCashAdapter(OnItemsClickListener onItemsClickListener, Context mcontext) {
        this.listener = onItemsClickListener;
        this.mcontext = mcontext;
    }
    public SkylightCashAdapter(OnItemsClickListener onItemsClickListener) {
        this.listener = onItemsClickListener;
    }
    public SkylightCashAdapter(View itemView, final Callback callback) {
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
        }
    }

    public SkylightCashAdapter(Context context, int resources, ArrayList<AppCash> appCashes) {
        this.appCashArrayList = appCashes;
        this.mcontext = context;
        this.resources = resources;

    }

    public SkylightCashAdapter(Context context, ArrayList<AppCash> deposits) {
        this.appCashArrayList = deposits;
        this.mcontext = context;

    }


    @NonNull
    @Override
    public SkylightCashAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skylight_cash_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        AppCash appCash = this.appCashArrayList.get(position);
        holder.skylightCashID.setText(MessageFormat.format("Awajima Cash ID:{0}", String.valueOf(appCash.getSkylightCashID())));
        holder.tellerCashDate.setText(MessageFormat.format("Awajima Cash date:{0}", appCash.getSkylightCashDate()));
        holder.cashAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", appCash.getSkylightCashAmount())));
        holder.status.setText(MessageFormat.format("Status:{0}", appCash.getSkylightCashStatus()));
        holder.payer.setText(MessageFormat.format("Payer :{0}", appCash.getSCPayer()));
        holder.payee.setText(MessageFormat.format("Payee:{0}",  appCash.getSCPayee()));
        holder.from.setText(MessageFormat.format("From:{0}", appCash.getSkylightCashFrom()));
        holder.to.setText(MessageFormat.format("To:{0}", appCash.getSkylightCashTo()));
        holder.code.setText(MessageFormat.format("Code:{0}", appCash.getSkylightCashCode()));


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

        public TextView payer, tellerCashDate, status, payee, reportAmountTendered;
        public AppCompatImageView icon;
        private TextView cashAmount, from,to,code, skylightCashID;
        public AppCompatButton btnMore;
        private OnItemsClickListener listener;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.tc_icon);
            skylightCashID = convertView.findViewById(R.id.tellerCashID);
            payer = convertView.findViewById(R.id.skylighCashPayer);
            tellerCashDate = convertView.findViewById(R.id.tellerCashDate);
            from = convertView.findViewById(R.id.scFrom);
            to = convertView.findViewById(R.id.scTo);
            cashAmount = convertView.findViewById(R.id.cashTellerAmount);
            payee = convertView.findViewById(R.id.cashPayee);
            status = convertView.findViewById(R.id.cashStatus);
            code = convertView.findViewById(R.id.scCode);
            convertView.setOnClickListener(this);

            //reportAmountTendered = convertView.findViewById(R.id.bankDAmount);


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
