package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.skylightapp.Classes.SliderData;
import com.skylightapp.Classes.SliderModel;
import com.skylightapp.R;


import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderAdapterViewHolder> {

    private  List<SliderData> mSliderItems;
    private  List<ImageSlider> productList;
    private ArrayList<SlideModel> models;

    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
    }

    @NonNull
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapterViewHolder viewHolder, int position) {
        final SliderData sliderItem = mSliderItems.get(position);
        if(sliderItem !=null){
            //productList=sliderItem.getImgLink();
            models=sliderItem.getSlideModelList();
        }
        viewHolder.sliderView.setImageList(models);

        /*Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.sliderView);*/

    }

    @Override
    public int getItemCount() {
        return (null != mSliderItems ? mSliderItems.size() : 0);
    }


    static class SliderAdapterViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        //AppCompatImageView imageViewBackground;
        ImageSlider sliderView;


        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            sliderView = itemView.findViewById(R.id.iv_auto_image_slider);
            //imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;
        }
    }
}
