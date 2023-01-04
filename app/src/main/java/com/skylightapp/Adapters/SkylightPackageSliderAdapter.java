package com.skylightapp.Adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.skylightapp.MarketClasses.MarketBizPackModel;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.MarketClasses.AwajimaSlider;
import com.skylightapp.R;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SkylightPackageSliderAdapter extends RecyclerView.Adapter<SkylightPackageSliderAdapter.SliderAdapterVH> implements View.OnClickListener, Filterable {

    private Context context;

    private ArrayList<MarketBizPackModel> mSliderItems = new ArrayList<>();
    private Context Mcontext;
    private MarketBizPackModel skyLightPackModel;
    private MarketBizPackage marketBizPackage;
    private String tittle;
    private String type;
    private double durationPrice;
    private double grandTotal;
    private int id,imageLink,duration,position;
    private GestureDetector gestureDetector;
    private List<MarketBizPackModel> theSlideItemsModelClassList = new ArrayList<>();
    private List<MarketBizPackModel> itemsListFilter = new ArrayList<>();
    private OnItemsClickListener callback;
    TouchInterface touchInterface;
    SliderAdapterVH viewHolder;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f );
    protected AlphaAnimation fadeIn = new AlphaAnimation( 0.0f , 1.0f );

    public SkylightPackageSliderAdapter(Context context) {
        this.context = context;
    }


    public SkylightPackageSliderAdapter(Context context, ArrayList<MarketBizPackModel> sliderDataArrayList, OnItemsClickListener callback) {
        this.context = context;
        this.mSliderItems = sliderDataArrayList;
        this.callback = callback;


    }

    public SkylightPackageSliderAdapter(List<MarketBizPackModel> horizontalProductScrollModelList) {
        this.itemsListFilter = horizontalProductScrollModelList;
    }

    public void renewItems(ArrayList<MarketBizPackModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }
    public void updateItems(ArrayList<MarketBizPackModel> list) {
        this.mSliderItems = list;


        notifyDataSetChanged();
    }


    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(MarketBizPackModel sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }
    public void setCallback(OnItemsClickListener callback) {
        this.callback = callback;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemsListFilter.clear();
        if (charText.length() == 0) {
            itemsListFilter.addAll(mSliderItems);
        } else {
            for (MarketBizPackModel wp : mSliderItems) {
                if (wp.getpMItemName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemsListFilter.add(wp);
                }
            }
        }
        notifyDataSetChanged();
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
                    List<MarketBizPackModel> filteredList = new ArrayList<>();
                    for (MarketBizPackModel skyLightPackModel : mSliderItems) {
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
                itemsListFilter = (ArrayList<MarketBizPackModel>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }



    @Override
    public void onClick(View view) {
        if (callback != null) {
            //callback.onItemClick(itemsListFilter.get(getAdapterPosition()));
            callback.onItemClick(position);
        }

    }

    @NonNull
    @Override
    public SliderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapterVH viewHolder, int position) {
        MarketBizPackModel sliderItem = mSliderItems.get(position);
        if(sliderItem !=null){
            tittle=sliderItem.getpMItemName();
            type=sliderItem.getpMType();
            duration=sliderItem.getpMDuration();
            durationPrice=sliderItem.getpMPrice();
            grandTotal=duration*durationPrice;
            id=sliderItem.getpModeID();
            imageLink=sliderItem.getpMItemImage();

        }
        context = viewHolder.itemView.getContext();
        //context = viewHolder.imageItem.getContext();

        viewHolder.tittle.setText(MessageFormat.format("Item Name.:{0}", sliderItem.getpMItemName()));
        viewHolder.textViewDescription.setText(MessageFormat.format("Desc.:{0}", sliderItem.getpMdesc()));
        viewHolder.price.setText(MessageFormat.format("Price.:N{0}", sliderItem.getpMPrice()));
        viewHolder.duration.setText(MessageFormat.format("Duration.:{0}", sliderItem.getpMDuration()));
        //Glide.with(context).load(imageLink).fitCenter().into(viewHolder.imageItem);

        final MarketBizPackage marketBizPackage = new MarketBizPackage(id,0,tittle,durationPrice,grandTotal,type,duration);
        sliderItem.setSkyLightPackage(marketBizPackage);
        viewHolder.motherLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onItemClick(position);
                }
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.iv_auto_image_slider) {
                    if (callback != null) {

                        callback.onItemClick(position);
                    }
                    if (callback != null) {
                        callback.onItemClick(position);
                    }
                }

                if (id == R.id.tittle4) {
                    if (callback != null) {
                        callback.onItemClick(position);
                    }
                }

                if (id == R.id.image_slider_price) {
                    if (callback != null) {
                        callback.onItemClick(position);
                    }
                }
                if (id == R.id.duration_image_slider) {
                    if (callback != null) {
                        callback.onItemClick(position);
                    }
                }
                if (id == R.id.tv_auto_image_slider) {
                    if (callback != null) {
                        callback.onItemClick(position);
                    }
                }
            }
        };


    }

    @Override
    public int getItemCount() {
        return (null != mSliderItems ? mSliderItems.size() : 0);
    }

    class SliderAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnContextClickListener,View.OnLongClickListener,View.OnTouchListener {

        View itemView;
        TextView tittle;
        TextView textViewDescription;
        TextView price;
        TextView duration;
        OnItemsClickListener listener;
        MarketBizPackModel skyLightPackModel;
        public LinearLayout motherLayoutView;
        ImageSlider sliderView;
        AwajimaSlider awajimaSlider;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            sliderView = itemView.findViewById(R.id.iv_auto_image_slider);
            motherLayoutView = itemView.findViewById(R.id.productLayout);
            tittle = itemView.findViewById(R.id.tittle4);
            price = itemView.findViewById(R.id.image_slider_price);
            duration = itemView.findViewById(R.id.duration_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                //listener.onItemClick(itemsListFilter.get(getAdapterPosition()));
                listener.onItemClick(position);
            }

        }
        SliderAdapterVH(View itemView, OnItemsClickListener listener) {
            super(itemView);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != null) {
                listener.onItemClick(position);
            }
            return true;
        }

        @Override
        public boolean onContextClick(View view) {
            if (listener != null) {
                listener.onItemClick(position);
            }
            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (listener != null) {
                listener.onItemClick(position);
            }
            return false;
        }
    }

    public interface OnItemsClickListener{
        void onItemClick(int position);
        void onItemClick(View view, int position);
    }
    public interface TouchInterface {
        void touch(View.OnTouchListener onTouchListener);}

    public void setOnTouchListener(View.OnTouchListener touchListener){
        this.touchInterface = (TouchInterface) touchListener;
        viewHolder.motherLayoutView.setOnTouchListener(touchListener);
    }

}