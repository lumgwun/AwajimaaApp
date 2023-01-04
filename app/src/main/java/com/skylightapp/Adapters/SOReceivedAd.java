package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SOReceived;
import com.skylightapp.Classes.Utils;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SOReceivedAd extends RecyclerView.Adapter<SOReceivedAd.ViewHolder> implements ListAdapter, View.OnClickListener, Filterable {

    private Context context;
    private int resource;
    ArrayList<SOReceived> soReceiveds;
    private ArrayList<SOReceived> receivedArrayList;
    private OnReceivedClickListener listener;
    private OnReceivedClickListener mListener;
    private List<SOReceived> soReceivedList;
    private List<SOReceived> list;
    private SOReceived received;


    public SOReceivedAd(Context context, int resource, ArrayList<SOReceived> soReceiveds) {
        super();
        this.context = context;
        this.soReceiveds = soReceiveds;
        this.resource = resource;
    }
    public SOReceivedAd(Context applicationContext) {
        this.context = applicationContext;
    }

    public SOReceivedAd(ArrayList<SOReceived> recyclerDataArrayList, Context applicationContext) {
        this.receivedArrayList = recyclerDataArrayList;
        this.context = applicationContext;

    }


    public SOReceivedAd(Context context, ArrayList<SOReceived> soReceiveds) {
        this.receivedArrayList = soReceiveds;
        this.context=context;
    }



    public void setWhenClickListener(OnReceivedClickListener listener){
        this.listener = listener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return receivedArrayList == null? 0: receivedArrayList.size();
    }

    @Override
    public SOReceived getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
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
    public int getItemCount() {
        //return packageList.size();
        return receivedArrayList == null? 0: receivedArrayList.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onReceivedClick(received);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    soReceivedList = list;
                } else {
                    List<SOReceived> filteredList = new ArrayList<>();
                    for (SOReceived received1 : list) {
                        if (received1.getSorManagerName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(received1);
                        }
                    }
                    soReceivedList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = soReceivedList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                soReceivedList = (ArrayList<SOReceived>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.so_received_item, parent, false);
        return new ViewHolder(view);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView singleItemCardView;
        public View mView;
        public Object mItem;
        public AppCompatTextView packageID, platformType,cusName, paymentRef, managerName, soID, officeID, amount, date;

        public AppCompatTextView comments,status, sorID, amtRemaining;
        public AppCompatImageView icon,shareIcon;
        SOReceived mSoReceived;
        Customer customer= new Customer();
        Profile userProfile= new Profile();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon = itemView.findViewById(R.id.thumbnail_sor);
            singleItemCardView = itemView.findViewById(R.id.sor_card_view);
            cusName = itemView.findViewById(R.id.sor_cusName);
            shareIcon = itemView.findViewById(R.id.sor_share);
            platformType = itemView.findViewById(R.id.sor_type);
            paymentRef = itemView.findViewById(R.id.payment_REF);
            amount = itemView.findViewById(R.id.payment_amount);
            date = itemView.findViewById(R.id.payment_date);
            soID = itemView.findViewById(R.id.sor_SO_id);
            managerName = itemView.findViewById(R.id.payment_manager);
            officeID = itemView.findViewById(R.id.sor_officeID);
            comments = itemView.findViewById(R.id.sor_comments);
            amtRemaining = itemView.findViewById(R.id.sor_amt_Rem);
            status = itemView.findViewById(R.id.sor_statusID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(mSoReceived);
                }


            });

        }
        @SuppressLint("DefaultLocale")
        public void setData(SOReceived soReceived) {
            this.mSoReceived = soReceived;
            this.icon.setImageResource(R.drawable.ic_icon2);
            cusName.setText(MessageFormat.format("{0}", soReceived.getSorCusName()));
            platformType.setText(MessageFormat.format("Type:{0}", soReceived.getSorPlatformType()));
            amount.setText(MessageFormat.format("Amount:NGN{0}", Utils.awajimaAmountFormat(soReceived.getSorAmount())));
            date.setText(MessageFormat.format("date:{0}", soReceived.getSorTranxDate()));
            paymentRef.setText(MessageFormat.format("Ref:{0}", soReceived.getSorTranxRef()));
            soID.setText(MessageFormat.format("Standing Order ID: {0}", soReceived.getSorSOID()));
            managerName.setText(MessageFormat.format("Manager:{0}", soReceived.getSorManagerName()));
            status.setText(MessageFormat.format("Status:{0}", soReceived.getSorStatus()+"/"+soReceived.getSorID()));
            comments.setText(soReceived.getSorComment());
            officeID.setText( soReceived.getSorOffice());
            amtRemaining.setText(MessageFormat.format("Amt Rem:{0}", Utils.awajimaAmountFormat(soReceived.getSorAmountRem())));


            if (soReceived.getSorStatus().equalsIgnoreCase("Unverified")) {
                icon.setImageResource(R.drawable.unverified);
                soID.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            } else if (soReceived.getSorStatus().equalsIgnoreCase("Verified")) {
                icon.setImageResource(R.drawable.verified3);
                soID.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }

        }

        @Override
        public void onClick(View view) {

            if (mListener != null) {
                mListener.onReceivedClick(mSoReceived);
            }

        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        received = receivedArrayList.get(position);
        holder.cusName.setText(MessageFormat.format("{0}", received.getSorCusName()));
        holder.platformType.setText(MessageFormat.format("Type:{0}", received.getSorPlatformType()));
        holder.amount.setText(MessageFormat.format("Amount:NGN{0}", Utils.awajimaAmountFormat(received.getSorAmount())));
        holder.date.setText(MessageFormat.format("date:{0}", received.getSorTranxDate()));
        holder.paymentRef.setText(MessageFormat.format("Ref:{0}", received.getSorTranxRef()));
        holder.soID.setText(MessageFormat.format("Standing Order ID: {0}", received.getSorSOID()));
        holder.managerName.setText(MessageFormat.format("Manager:{0}", received.getSorManagerName()));
        holder.status.setText(MessageFormat.format("Status:{0}", received.getSorStatus()+"/"+received.getSorID()));
        holder.comments.setText(received.getSorComment());
        holder.officeID.setText( received.getSorOffice());
        holder.amtRemaining.setText(MessageFormat.format("Amt Rem:{0}", Utils.awajimaAmountFormat(received.getSorAmountRem())));


        if (received.getSorStatus().equalsIgnoreCase("Unverified")) {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.soID.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else if (received.getSorStatus().equalsIgnoreCase("Verified")) {
            holder.icon.setImageResource(R.drawable.verified3);
            holder.soID.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }

        holder.singleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onReceivedClick(received);
                }
            }
        });
    }


    public interface OnReceivedClickListener {
        void onReceivedClick(SOReceived received);
    }



    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }
        SOReceived marketBizPackage = getItem(position);

        return convertView;
    }
}
