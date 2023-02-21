package com.skylightapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skylightapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class WelcomeSliderAd extends SliderViewAdapter<WelcomeSliderAd.SliderAdapterViewHolder> {

    private final List<SliderData> mSliderItems;
    private SliderData sliderItem;
    private String tittle;
    private Context mContext;

    public WelcomeSliderAd(Context context, ArrayList<SliderData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.mContext = context;
    }


    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_intro, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        sliderItem = mSliderItems.get(position);
        if(sliderItem !=null){
            tittle= sliderItem.getTittle();

            Glide.with(mContext)
                    .load(sliderItem.getImgUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);

            if(sliderItem.getImgUrl()==0){
                Glide.with(mContext)
                        .asBitmap()
                        .load(R.drawable.ifesinachi)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.ic_alert)
                        //.listener(listener)
                        .skipMemoryCache(true)
                        .fitCenter()
                        .centerCrop()
                        .into(viewHolder.imageViewBackground);
            }
        }

        viewHolder.txtTittle.setText(tittle);


    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        AppCompatImageView imageViewBackground;
        AppCompatTextView txtTittle;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.my_intro_image);
            txtTittle = itemView.findViewById(R.id.welcome_tittle);
            this.itemView = itemView;
        }
    }
}
