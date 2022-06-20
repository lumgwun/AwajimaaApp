package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class SkylightPackageSliderAdapter extends SliderViewAdapter<SkylightPackageSliderAdapter.SliderAdapterVH> implements View.OnClickListener, Filterable {

    private Context context;

    private List<SkyLightPackModel> mSliderItems = new ArrayList<>();
    private Context Mcontext;
    private  SkyLightPackModel skyLightPackModel;

    private List<SkyLightPackModel> theSlideItemsModelClassList = new ArrayList<>();
    private List<SkyLightPackModel> itemsListFilter = new ArrayList<>();
    private OnItemsClickListener listener;

    public SkylightPackageSliderAdapter(Context context) {
        this.context = context;
    }


    public SkylightPackageSliderAdapter(Context context, ArrayList<SkyLightPackModel> sliderDataArrayList) {
        this.context = context;
        this.mSliderItems = sliderDataArrayList;

    }

    public void renewItems(List<SkyLightPackModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SkyLightPackModel sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SkyLightPackModel sliderItem = mSliderItems.get(position);

        viewHolder.tittle.setText(MessageFormat.format("Desc.:{0}", sliderItem.getItemName()));
        viewHolder.textViewDescription.setText(MessageFormat.format("Desc.:{0}", sliderItem.getDescription()));
        viewHolder.price.setText(MessageFormat.format("Price.:N{0}", sliderItem.getPrice()));
        viewHolder.duration.setText(MessageFormat.format("Duration.:{0}", sliderItem.getDuration()));
        //viewHolder.itemView
        Glide.with(viewHolder.imageItem)
                .load(sliderItem.getItemImage())
                .fitCenter()
                .into(viewHolder.imageItem);

        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle paymentBundle = new Bundle();
                if(sliderItem !=null){
                    String packageName =sliderItem.getName();
                    String paymentDate =sliderItem.getDate();
                    double paymentAmount =sliderItem.getAmount();
                    double totalAmount =sliderItem.getTotalAmount();
                    int numberOfDailyPayments =sliderItem.getNumberOfDays();
                    String packageId = String.valueOf(sliderItem.getPackageId());
                    totalAmount = paymentAmount* numberOfDailyPayments;

                    String surName = userProfile.getLastName();
                    String firstName = userProfile.getFirstName();
                    String email = userProfile.getEmailAddress();
                    String address = String.valueOf(userProfile.getAddress());
                    String phoneNumber = userProfile.getPhoneNumber();
                    String status = skyLightPackage.getStatus();
                    String userNames =surName +""+ firstName;
                    paymentBundle.putString("Package Name",packageName);
                    paymentBundle.putString("Package Id",packageId);
                    paymentBundle.putDouble("Amount",paymentAmount);
                    paymentBundle.putString("Date",paymentDate);
                    paymentBundle.putString("Names",userNames);
                    paymentBundle.putString("Phone Number",phoneNumber);
                    paymentBundle.putString("Email Address",email);
                    paymentBundle.putString("Address",address);


                }



                /*Bundle paymentBundle = new Bundle();
                paymentBundle.putString("Package Id", String.valueOf(packageId));
                paymentBundle.putString("PACKAGE_KEY", packageName);
                paymentBundle.putString("Date", paymentDate);
                paymentBundle.putString("Report ID", String.valueOf(KEY_EXTRA_CONTACT_ID));
                paymentBundle.putString("Number of Days", String.valueOf(numberOfDailyPayments));
                paymentBundle.putString(PACKAGE_DURATION, String.valueOf(durationInDays));
                paymentBundle.putString("Grand Total", String.valueOf(grandTotal));
                paymentBundle.putString(PACKAGE_TYPE, String.valueOf(packageType));
                paymentBundle.putString("Amount", String.valueOf(savingsAmount));
                paymentBundle.putString("Amount So Far", String.valueOf(totalAmountSum));
                paymentBundle.putString("Package Count", "1");
                paymentBundle.putString("Customer Name", customerNames);
                paymentBundle.putString("Customer Email", emailAddress);
                paymentBundle.putString("Customer Phone Number", phoneNumber1);
                paymentBundle.putString("Day Remaining", String.valueOf(daysRemaining));
                paymentBundle.putString("Amount Remaining", String.valueOf(amountRemaining));
                paymentBundle.putString("Account ID", String.valueOf(acctID));
                paymentBundle.putString("Total", String.valueOf(totalAmountSum));

            }
        });*/

    }

    @Override
    public int getCount() {
        return (null != mSliderItems ? mSliderItems.size() : 0);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsListFilter = mSliderItems;
                } else {
                    List<SkyLightPackModel> filteredList = new ArrayList<>();
                    for (SkyLightPackModel skyLightPackModel : mSliderItems) {
                        if (skyLightPackModel.getItemName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(skyLightPackModel);
                        }
                    }
                    itemsListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsListFilter = (ArrayList<SkyLightPackModel>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }



    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(skyLightPackModel);
        }

    }

    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder implements View.OnClickListener {

        View itemView;
        TextView tittle;
        ImageView imageItem;
        ImageView imageGifContainer;
        TextView textViewDescription;
        TextView price;
        TextView duration;
        OnItemsClickListener listener;
        SkyLightPackModel skyLightPackModel;

        public SliderAdapterVH(View itemView) {
            super(itemView);

            imageItem = itemView.findViewById(R.id.iv_auto_image_slider);
            //imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            tittle = itemView.findViewById(R.id.tittle4);
            price = itemView.findViewById(R.id.image_slider_price);
            duration = itemView.findViewById(R.id.duration_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(skyLightPackModel);
            }

        }
    }
    public interface OnItemsClickListener{
        void onItemClick(SkyLightPackModel lightPackage);
    }
}