package com.skylightapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.PayNowActivity;
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
    private  SkyLightPackage skyLightPackage;

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
        String tittle=sliderItem.getpMItemName();
        String type=sliderItem.getpMType();
        int duration=sliderItem.getpMDuration();
        double amount=sliderItem.getpMPrice();
        double grandTotal=duration*amount;
        int id=sliderItem.getpModeID();

        viewHolder.tittle.setText(MessageFormat.format("Item Name.:{0}", sliderItem.getpMItemName()));
        viewHolder.textViewDescription.setText(MessageFormat.format("Desc.:{0}", sliderItem.getpMdesc()));
        viewHolder.price.setText(MessageFormat.format("Price.:N{0}", sliderItem.getpMPrice()));
        viewHolder.duration.setText(MessageFormat.format("Duration.:{0}", sliderItem.getpMDuration()));
        Glide.with(viewHolder.imageItem).load(sliderItem.getpMItemImage()).fitCenter().into(viewHolder.imageItem);

        final SkyLightPackage skyLightPackage = new SkyLightPackage(id,0,tittle,amount,grandTotal,type,duration);
        skyLightPackModel.setSkyLightPackage(skyLightPackage);
        viewHolder.motherLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(skyLightPackModel);
                }
            }
        });

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
                        if (skyLightPackModel.getpMItemName().toLowerCase().contains(charString.toLowerCase())) {
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
        public LinearLayout motherLayoutView;

        public SliderAdapterVH(View itemView) {
            super(itemView);

            imageItem = itemView.findViewById(R.id.iv_auto_image_slider);
            motherLayoutView = itemView.findViewById(R.id.productLayout);
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
        void onItemClick(SkyLightPackModel skyLightPackModel);
    }
}