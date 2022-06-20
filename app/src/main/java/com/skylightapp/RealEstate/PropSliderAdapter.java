package com.skylightapp.RealEstate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PropSliderAdapter extends SliderViewAdapter<PropSliderAdapter.SliderAdapterViewHolder> {

    private final List<PropertyImage> mSliderItems;


    public PropSliderAdapter(Context context, ArrayList<PropertyImage> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_slider, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final PropertyImage sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUri())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }


    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        BezelImageView imageViewBackground;
        BezelImageView propertyTittle;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myPropImage);
            propertyTittle = itemView.findViewById(R.id.myPropertyTittle);
            this.itemView = itemView;
        }
    }
}
