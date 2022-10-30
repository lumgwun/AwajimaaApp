package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.R;
import java.util.ArrayList;
import java.util.List;

public class AnnounceSliderA extends RecyclerView.Adapter<AnnounceSliderA.SliderAdapterViewHolder> {

    private List<AnnounceSData> mSliderItems;
    private Context mContext;
    private  int layout;

    // Constructor
    public AnnounceSliderA(Context context, int slider_announce,ArrayList<AnnounceSData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.mContext = context;
        this.layout = slider_announce;
    }
    public AnnounceSliderA(Context context, ArrayList<AnnounceSData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.mContext = context;

    }


    @NonNull
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_announce, parent, false);
        return new SliderAdapterViewHolder(view);

    }


    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(List<AnnounceSData> mSliderItems) {
        this.mSliderItems = mSliderItems;
        notifyDataSetChanged();
    }




    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final AnnounceSData sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }

    static class SliderAdapterViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        private AppCompatImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.announce_img);
            this.itemView = itemView;
        }
    }
}
