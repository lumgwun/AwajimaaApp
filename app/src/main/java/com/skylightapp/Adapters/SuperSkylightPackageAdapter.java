package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
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

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Admins.PackageUpdateAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Interfaces.SkylightPackageListener;
import com.skylightapp.R;
import com.skylightapp.SkyLightPackageActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
public class SuperSkylightPackageAdapter extends RecyclerView.Adapter<SuperSkylightPackageAdapter.ViewHolder> implements ListAdapter,View.OnClickListener, Filterable {

    private Context context;
    private int resource;
    ArrayList<SkyLightPackage> skyLightPackages;
    private ArrayList<SkyLightPackage> packageList;
    private OnItemsClickListener listener;
    private SkylightPackageListener mListener;
    private List<SkyLightPackage> skyLightPackageList;
    private List<SkyLightPackage> getSkyLightPackageList;
    private SkyLightPackage skyLightPackage;


    public SuperSkylightPackageAdapter(Context context, int resource, ArrayList<SkyLightPackage> skyLightPackages) {
        super();
        this.context = context;
        this.skyLightPackages = skyLightPackages;
        this.resource = resource;
    }
    public SuperSkylightPackageAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    public SuperSkylightPackageAdapter(ArrayList<SkyLightPackage> recyclerDataArrayList, Context applicationContext) {
        this.packageList = recyclerDataArrayList;
        this.context = applicationContext;

    }

    public SuperSkylightPackageAdapter(SkyLightPackageActivity skyLightPackageActivity, ArrayList<SkyLightPackage> skyLightPackagesA) {
        this.packageList = skyLightPackagesA;
    }

    public SuperSkylightPackageAdapter(Context context, ArrayList<SkyLightPackage> skyLightPackages) {
        this.packageList = skyLightPackages;
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
    public SkyLightPackage getItem(int i) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SkyLightPackage modelItems = packageList.get(position);

        holder.singleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(modelItems);
                }
            }
        });
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
            listener.onItemClick(skyLightPackage);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    skyLightPackageList = getSkyLightPackageList;
                } else {
                    List<SkyLightPackage> filteredList = new ArrayList<>();
                    for (SkyLightPackage skyLightPackage : getSkyLightPackageList) {
                        if (skyLightPackage.getPackageName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(skyLightPackage);
                        }
                    }
                    skyLightPackageList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = skyLightPackageList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                skyLightPackageList = (ArrayList<SkyLightPackage>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView singleItemCardView;
        public TextView singleItemTextView;
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
        SkyLightPackage skyLightPackage;
        Customer customer= new Customer();
        Profile userProfile= new Profile();

        // Link up the Main-List items layout components with their respective id
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            singleItemCardView = itemView.findViewById(R.id.singleItemCardView);
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
                    Bundle updateBundle = new Bundle();
                    if(skyLightPackage !=null){
                        String packageName =skyLightPackage.getPackageName();
                        String startDate =skyLightPackage.getDateStarted();
                        String endDate =skyLightPackage.getDateEnded();
                        double paymentAmount =skyLightPackage.getAmount();
                        double totalAmount =skyLightPackage.getPackageTotalAmount();
                        double savedAmount =skyLightPackage.getAmount_collected();
                        double remAmount =skyLightPackage.getAmountRemaining();
                        int duration =skyLightPackage.getPackageDuration();
                        long packageId = skyLightPackage.getPackageId();
                        customer=skyLightPackage.getCustomer();
                        userProfile=skyLightPackage.getProfile();
                        String manager = userProfile.getProfileFirstName();
                        String surName = customer.getCusSurname();
                        String firstName = customer.getCusFirstName();
                        String email = customer.getCusEmailAddress();
                        String address = String.valueOf(customer.getCusAddress());
                        String phoneNumber = customer.getCusPhoneNumber();
                        LatLng location = customer.getCusLocation();
                        //String cusLat= customer.getLatitude();
                        //String cusLongitude=customer.getLongitude();
                        String customerOfficeBranch = customer.getCusOfficeBranch();
                        String type = String.valueOf(skyLightPackage.getPackageType());
                        String status = skyLightPackage.getStatus();
                        String userNames =surName +","+ firstName;
                        updateBundle.putString("Package Name",packageName);
                        updateBundle.putString("StartDate",startDate);
                        updateBundle.putString("EndDate",endDate);
                        updateBundle.putString("Package Id", String.valueOf(packageId));
                        updateBundle.putDouble("Amount",paymentAmount);
                        updateBundle.putDouble("GrandTotal",totalAmount);
                        updateBundle.putString("Names",userNames);
                        updateBundle.putString("PackageType",type);
                        updateBundle.putString("SavedAmount", String.valueOf(savedAmount));
                        updateBundle.putString("AmtRem", String.valueOf(remAmount));
                        updateBundle.putString("Duration", String.valueOf(duration));
                        updateBundle.putString("Phone Number",phoneNumber);
                        updateBundle.putString("Email Address",email);
                        updateBundle.putString("Address",address);
                        updateBundle.putString("Manager",manager);
                        updateBundle.putString("Status",status);
                        updateBundle.putString("Location", String.valueOf(location));
                        updateBundle.putString("Office",customerOfficeBranch);
                        Intent packageIntent = new Intent(context,
                                PackageUpdateAct.class);
                        packageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        packageIntent.putExtras(updateBundle);


                    }

                }


            });

        }
        @SuppressLint("DefaultLocale")
        public void setData(SkyLightPackage skyLightPackage) {
            this.skyLightPackage = skyLightPackage;
            this.itemImage.setImageResource(R.drawable.ic_icon2);
            cusName.setText(MessageFormat.format("{0} - {1}", skyLightPackage.getPackageType().toString(), skyLightPackage.getPackageId()));
            startDate.setText(MessageFormat.format("Start date:{0}", skyLightPackage.getDateStarted()));
            packageAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", skyLightPackage.getAmount())));
            packageAmtRem.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", skyLightPackage.getAmountRemaining())));
            packageTotal.setText(MessageFormat.format("Grand Total:{0}", skyLightPackage.getGrandTotal()));
            status.setText(MessageFormat.format("Status:{0}", skyLightPackage.getPackageStatus()));
            endDate.setText(MessageFormat.format("End date:{0}", skyLightPackage.getDateEnded()));
            packageDaysRem.setText(MessageFormat.format("Days Rem:{0}", skyLightPackage.getRemainingDays()));
            duration1.setText(MessageFormat.format("Duration:{0}", skyLightPackage.getPackageDuration()));

            amountSaved.setText(MessageFormat.format("Status:{0}", skyLightPackage.getPackageStatus()));
            type.setText(MessageFormat.format("End date:{0}", skyLightPackage.getDateEnded()));
            manager.setText(MessageFormat.format("Days Rem:{0}", skyLightPackage.getRemainingDays()));

            if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.SAVINGS) {
                itemImage.setImageResource(R.drawable.shape_rect_1);
            } else if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.ITEM_PURCHASE) {
                itemImage.setImageResource(R.drawable.ic_icon2);
                packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
            } else if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.BORROWING) {
                itemImage.setImageResource(R.drawable.ic__category);
                packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }else if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.PROMO) {
                itemImage.setImageResource(R.drawable.ic_expand_more);
                packageAmtRem.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }

        }

        @Override
        public void onClick(View view) {

            if (mListener != null) {
                mListener.onItemClick(skyLightPackage);
            }

        }
    }

    public interface OnItemsClickListener{
        void onItemClick(SkyLightPackage lightPackage);
    }



    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }
        SkyLightPackage skyLightPackage = getItem(position);
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

        txtTransactionTitle.setText(MessageFormat.format("{0} - {1}", skyLightPackage.getPackageType().toString(), skyLightPackage.getPackageId()));
        startDate.setText(MessageFormat.format("Start date:{0}", skyLightPackage.getDateStarted()));
        amount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", skyLightPackage.getAmount())));
        amountRemaining.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", skyLightPackage.getAmountRemaining())));
        date.setText(MessageFormat.format("Date:{0}", skyLightPackage.getDate()));
        status.setText(MessageFormat.format("Status:{0}", skyLightPackage.getPackageStatus()));
        endDate.setText(MessageFormat.format("End date:{0}", skyLightPackage.getDateEnded()));
        daysRemaining.setText(MessageFormat.format("Days Rem:{0}", skyLightPackage.getRemainingDays()));
        duration.setText(MessageFormat.format("Duration:{0}", skyLightPackage.getPackageDuration()));

        if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.SAVINGS) {
            imgTransactionIcon.setImageResource(R.drawable.shape_rect_1);
            txtTransactionInfo.setText(MessageFormat.format("Savings: {0}", skyLightPackage.getSavings()));

            txtTransactionAmount.setTextColor(Color.RED);
        } else if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.ITEM_PURCHASE) {
            imgTransactionIcon.setImageResource(R.drawable.shape_rect_4);
            txtTransactionInfo.setText(MessageFormat.format("Item Purchase: {0}", skyLightPackage.getItem_purchases()));
            txtTransactionAmount.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.BORROWING) {
            imgTransactionIcon.setImageResource(R.drawable.shape_rect_7);
            txtTransactionInfo.setVisibility(View.GONE);
            txtTransactionAmount.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }else if (skyLightPackage.getSkylightPackage() == SkyLightPackage.SkylightPackage_Type.PROMO) {
            imgTransactionIcon.setImageResource(R.drawable.shape_layer_3);
            txtTransactionInfo.setVisibility(View.GONE);
            txtTransactionAmount.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }*/

        return convertView;
    }
}
