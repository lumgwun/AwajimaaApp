package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class AwajimaSlider extends RecyclerView.Adapter<AwajimaSlider.SliderAdapterViewHolder> {

    private List<SkyLightPackModel> mSliderItems;
    private Context mContext;
    private  int layout;

    // Constructor
    public AwajimaSlider(Context context, int slider_announce, ArrayList<SkyLightPackModel> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.mContext = context;
        this.layout = slider_announce;
    }
    public AwajimaSlider(Context context, ArrayList<SkyLightPackModel> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.mContext = context;

    }


    @NonNull
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_product, parent, false);
        return new SliderAdapterViewHolder(view);

    }


    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(List<SkyLightPackModel> mSliderItems) {
        this.mSliderItems = mSliderItems;
        notifyDataSetChanged();
    }




    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final SkyLightPackModel sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getpModeID())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }

    static class SliderAdapterViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        private AppCompatImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.prod_img_slider);
            this.itemView = itemView;
        }
    }
}
