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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Customer;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Interfaces.SkylightPackageListener;
import com.skylightapp.R;
import com.skylightapp.AwajimaPackAct;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;


@SuppressWarnings("deprecation")
public class SuperSkylightPackageAdapter extends RecyclerView.Adapter<SuperSkylightPackageAdapter.ViewHolder> implements ListAdapter,View.OnClickListener, Filterable {

    private Context context;
    private int resource;
    ArrayList<MarketBizPackage> marketBizPackages;
    private ArrayList<MarketBizPackage> packageList;
    private OnItemsClickListener listener;
    private SkylightPackageListener mListener;
    private List<MarketBizPackage> marketBizPackageList;
    private List<MarketBizPackage> getMarketBizPackageList;
    private MarketBizPackage marketBizPackage;


    public SuperSkylightPackageAdapter(Context context, int resource, ArrayList<MarketBizPackage> marketBizPackages) {
        super();
        this.context = context;
        this.marketBizPackages = marketBizPackages;
        this.resource = resource;
    }
    public SuperSkylightPackageAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    public SuperSkylightPackageAdapter(ArrayList<MarketBizPackage> recyclerDataArrayList, Context applicationContext) {
        this.packageList = recyclerDataArrayList;
        this.context = applicationContext;

    }

    public SuperSkylightPackageAdapter(AwajimaPackAct awajimaPackAct, ArrayList<MarketBizPackage> marketBizPackagesA) {
        this.packageList = marketBizPackagesA;
    }

    public SuperSkylightPackageAdapter(Context context, ArrayList<MarketBizPackage> marketBizPackages) {
        this.packageList = marketBizPackages;
        this.context=context;
    }



    public void setWhenClickListener(OnItemsClickListener listener){
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
        return packageList == null? 0: packageList.size();
    }

    @Override
    public MarketBizPackage getItem(int i) {
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
        return packageList == null? 0: packageList.size();
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
            listener.onItemClick(marketBizPackage);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    marketBizPackageList = getMarketBizPackageList;
                } else {
                    List<MarketBizPackage> filteredList = new ArrayList<>();
                    for (MarketBizPackage marketBizPackage : getMarketBizPackageList) {
                        if (marketBizPackage.getPackageName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(marketBizPackage);
                        }
                    }
                    marketBizPackageList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = marketBizPackageList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                marketBizPackageList = (ArrayList<MarketBizPackage>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_list_row, parent, false);
        return new ViewHolder(view);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView singleItemCardView;
        public View mView;
        public Object mItem;
        public TextView packageID;
        public TextView type;
        public TextView cusName;
        public TextView packageAmount;
        public TextView packageTotal;
        public TextView packageAmtRem;
        public TextView packageDaysRem;
        public TextView amountSaved;
        public TextView startDate;
        public TextView endDate;
        public TextView status;
        public TextView manager;
        public TextView duration1;
        public ImageView itemImage;
        MarketBizPackage marketBizPackage;
        Customer customer= new Customer();
        Profile userProfile= new Profile();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            singleItemCardView = itemView.findViewById(R.id.card_view_package);
            cusName = itemView.findViewById(R.id.package_customer);
            packageAmount = itemView.findViewById(R.id.package_amount1);
            packageTotal = itemView.findViewById(R.id.package_total);
            amountSaved = itemView.findViewById(R.id.package_saved);
            packageAmtRem = itemView.findViewById(R.id.package_amount_rem1);
            duration1 = itemView.findViewById(R.id.package_duration1);
            packageDaysRem = itemView.findViewById(R.id.package_days_remaining1);
            type = itemView.findViewById(R.id.package_type3);
            startDate = itemView.findViewById(R.id.package_start_date1);
            endDate = itemView.findViewById(R.id.endDate3);
            status = itemView.findViewById(R.id.package_status_3);
            manager = itemView.findViewById(R.id.package_manager);
            itemImage = itemView.findViewById(R.id.thumbnail_tx);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(marketBizPackage);
                }


            });

        }
        @SuppressLint("DefaultLocale")
        public void setData(MarketBizPackage marketBizPackage) {
            this.marketBizPackage = marketBizPackage;
            this.itemImage.setImageResource(R.drawable.ic_icon2);
            cusName.setText(MessageFormat.format("{0}", marketBizPackage.getPackageCustomerName()));
            startDate.setText(MessageFormat.format("Start date:{0}", marketBizPackage.getPackageDateStarted()));
            packageAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageDailyAmount())));
            packageAmtRem.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageAmtRem())));
            packageTotal.setText(MessageFormat.format("Grand Total:{0}", marketBizPackage.getPackageTotalAmount()));
            status.setText(MessageFormat.format("Status:{0}", marketBizPackage.getPackageStatus()));
            endDate.setText(MessageFormat.format("End date:{0}", marketBizPackage.getPackageDateEnded()));
            packageDaysRem.setText(MessageFormat.format("Days Rem:{0}", marketBizPackage.getPackageDaysRem()));
            duration1.setText(MessageFormat.format("Duration:{0}", marketBizPackage.getPackageDuration()));

            amountSaved.setText(MessageFormat.format("Status:{0}", marketBizPackage.getPackageAmount_collected()));
            type.setText(MessageFormat.format("End date:{0}", marketBizPackage.getPackageDateEnded()));
            manager.setText(MessageFormat.format("Days Rem:{0}", marketBizPackage.getPackageTellerName()));

            if (marketBizPackage.getPackageType().equalsIgnoreCase("Savings")) {
                itemImage.setImageResource(R.drawable.shape_rect_1);
            } else if (marketBizPackage.getPackageType().equalsIgnoreCase("Item Purchase")) {
                itemImage.setImageResource(R.drawable.ic_icon2);
                packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
            } else if (marketBizPackage.getPackageType().equalsIgnoreCase("Investment")) {
                itemImage.setImageResource(R.drawable.ic__category);
                packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }else if (marketBizPackage.getPackageType().equalsIgnoreCase("Promo")) {
                itemImage.setImageResource(R.drawable.ic_expand_more);
                packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }

        }

        @Override
        public void onClick(View view) {

            if (mListener != null) {
                mListener.onItemClick(marketBizPackage);
            }

        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        marketBizPackage = packageList.get(position);
        holder.cusName.setText(MessageFormat.format("{0}", marketBizPackage.getPackageCustomerName()));
        holder.startDate.setText(MessageFormat.format("Start date:{0}", marketBizPackage.getPackageDateStarted()));
        holder.packageAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageDailyAmount())));
        holder.packageAmtRem.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageAmtRem())));
        holder.packageTotal.setText(MessageFormat.format("Grand Total:{0}", marketBizPackage.getPackageTotalAmount()));
        holder.status.setText(MessageFormat.format("Status:{0}", marketBizPackage.getPackageStatus()));
        holder.endDate.setText(MessageFormat.format("End date:{0}", marketBizPackage.getPackageDateEnded()));
        holder.packageDaysRem.setText(MessageFormat.format("Days Rem:{0}", marketBizPackage.getPackageDaysRem()));
        holder.duration1.setText(MessageFormat.format("Duration:{0}", marketBizPackage.getPackageDuration()));
        holder.amountSaved.setText(MessageFormat.format("Amt collected:{0}", marketBizPackage.getPackageAmount_collected()));
        holder.type.setText(MessageFormat.format("End date:{0}", marketBizPackage.getPackageType()));
        holder.manager.setText(MessageFormat.format("Days Rem:{0}", marketBizPackage.getPackageTellerName()));

        if (marketBizPackage.getPackageType().equalsIgnoreCase("Savings")) {
            holder.itemImage.setImageResource(R.drawable.shape_rect_1);
        } else if (marketBizPackage.getPackageType().equalsIgnoreCase("Item Purchase")) {
            holder.itemImage.setImageResource(R.drawable.ic_icon2);
            holder.packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else if (marketBizPackage.getPackageType().equalsIgnoreCase("Investment")) {
            holder.itemImage.setImageResource(R.drawable.ic__category);
            holder.packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }else if (marketBizPackage.getPackageType().equalsIgnoreCase("Promo")) {
            holder.itemImage.setImageResource(R.drawable.ic_expand_more);
            holder.packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }


        holder.singleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(marketBizPackage);
                }
            }
        });
    }


    public interface OnItemsClickListener{
        void onItemClick(MarketBizPackage lightPackage);
    }



    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }
        MarketBizPackage marketBizPackage = getItem(position);
        /*ImageView imgTransactionIcon = convertView.findViewById(R.id.thumbnail_p);
        TextView txtTransactionTitle = convertView.findViewById(R.id.txt_transaction_type_id);
        TextView txtTransactionInfo = convertView.findViewById(R.id.txt_transaction_type_id);
        TextView date = convertView.findViewById(R.id.txt_transaction_timestamp);
        TextView duration = convertView.findViewById(R.id.txt_transaction_timestamp);
        TextView amount = convertView.findViewById(R.id.txt_transaction_timestamp);
        TextView startDate = convertView.findViewById(R.id.txt_transaction_timestamp);
        TextView endDate = convertView.findViewById(R.id.txt_transaction_info);
        endDate.setVisibility(View.VISIBLE);
        TextView txtTransactionAmount = convertView.findViewById(R.id.txt_transaction_amount);
        TextView daysRemaining = convertView.findViewById(R.id.txt_transaction_amount);
        TextView amountRemaining = convertView.findViewById(R.id.txt_transaction_amount);
        TextView status = convertView.findViewById(R.id.txt_transaction_amount);

        txtTransactionTitle.setText(MessageFormat.format("{0} - {1}", marketBizPackage.getPackageType().toString(), marketBizPackage.getPackageId()));
        startDate.setText(MessageFormat.format("Start date:{0}", marketBizPackage.getDateStarted()));
        amount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", marketBizPackage.getAmount())));
        amountRemaining.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", marketBizPackage.getAmountRemaining())));
        date.setText(MessageFormat.format("Date:{0}", marketBizPackage.getDate()));
        status.setText(MessageFormat.format("Status:{0}", marketBizPackage.getPackageStatus()));
        endDate.setText(MessageFormat.format("End date:{0}", marketBizPackage.getDateEnded()));
        daysRemaining.setText(MessageFormat.format("Days Rem:{0}", marketBizPackage.getRemainingDays()));
        duration.setText(MessageFormat.format("Duration:{0}", marketBizPackage.getPackageDuration()));

        if (marketBizPackage.getSkylightPackage() == MarketBizPackage.SkylightPackage_Type.SAVINGS) {
            imgTransactionIcon.setImageResource(R.drawable.shape_rect_1);
            txtTransactionInfo.setText(MessageFormat.format("Savings: {0}", marketBizPackage.getSavings()));

            txtTransactionAmount.setTextColor(Color.RED);
        } else if (marketBizPackage.getSkylightPackage() == MarketBizPackage.SkylightPackage_Type.ITEM_PURCHASE) {
            imgTransactionIcon.setImageResource(R.drawable.shape_rect_4);
            txtTransactionInfo.setText(MessageFormat.format("Item Purchase: {0}", marketBizPackage.getItem_purchases()));
            txtTransactionAmount.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else if (marketBizPackage.getSkylightPackage() == MarketBizPackage.SkylightPackage_Type.BORROWING) {
            imgTransactionIcon.setImageResource(R.drawable.shape_rect_7);
            txtTransactionInfo.setVisibility(View.GONE);
            txtTransactionAmount.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }else if (marketBizPackage.getSkylightPackage() == MarketBizPackage.SkylightPackage_Type.PROMO) {
            imgTransactionIcon.setImageResource(R.drawable.shape_layer_3);
            txtTransactionInfo.setVisibility(View.GONE);
            txtTransactionAmount.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }*/

        return convertView;
    }
}
