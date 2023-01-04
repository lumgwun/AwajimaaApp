package com.skylightapp.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.MarketClasses.MarketBizPackModel;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.R;
import com.skylightapp.AwajimaPackAct;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;



public class SkyLightPackageShowCaseAdapter extends RecyclerView.Adapter<SkyLightPackageShowCaseAdapter.ViewHolder> implements ListAdapter {

    private List<MarketBizPackModel> skyLightPackage_2List;
    private OnItemsClickListener listener;
    Context context;
    private ArrayList<MarketBizPackModel> packageList;
    private ArrayList<MarketBizPackage> marketBizPackages;
    private GestureDetector gestureDetector;
    private int position;

    public SkyLightPackageShowCaseAdapter(Context context, List<MarketBizPackModel> skyLightPackage_2List) {
        this.context = context;
        this.skyLightPackage_2List = skyLightPackage_2List;

    }
    public SkyLightPackageShowCaseAdapter(Context context, ArrayList<MarketBizPackModel> sliderDataArrayList, OnItemsClickListener callback) {
        this.context = context;
        this.packageList = sliderDataArrayList;
        this.listener = callback;


    }
    public void setCallback(OnItemsClickListener callback) {
        this.listener = callback;
    }

    public SkyLightPackageShowCaseAdapter(AwajimaPackAct awajimaPackAct, List<MarketBizPackModel> skyLightPackage_2List) {
        this.context = awajimaPackAct;
        this.skyLightPackage_2List = skyLightPackage_2List;

    }
    public void updateItems(ArrayList<MarketBizPackModel> list) {
        this.packageList = list;


        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pack_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MarketBizPackage model = packageList.get(position);
        final MarketBizPackModel model2= packageList.get(position);
        ViewHolder mainHolder = (ViewHolder) holder;
        context = mainHolder.itemView.getContext();
        mainHolder.description.setText(MessageFormat.format("Desc.{0}", model2.getpMdesc()));
        mainHolder.itemName.setText(MessageFormat.format("Item Name:{0}", model2.getpMItemName()));
        mainHolder.price.setText(MessageFormat.format("Price N{0}", model2.getpMPrice()));
        mainHolder.duration1.setText(MessageFormat.format("Duration:{0}", model2.getpMDuration()));
        //mainHolder.itemImage.setImageResource(model2.getpMItemImage());
        Glide.with(context).load(model2.getpMItemImage()).fitCenter().into(mainHolder.itemImage);
        holder.setData(skyLightPackage_2List.get(position).getpMItemImage(),skyLightPackage_2List.get(position).getpMItemName(),skyLightPackage_2List.get(position).getpMPrice(),skyLightPackage_2List.get(position).getpMdesc(),skyLightPackage_2List.get(position).getpMdesc());

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(position);
                }
            }
        });
    }
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView itemName;
        public TextView description;
        public TextView price;
        public TextView duration1;
        public ImageView itemImage;
        private View itemView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.item_daily_price);
            duration1 = itemView.findViewById(R.id.duration1);
            description = itemView.findViewById(R.id.item_description);
            itemImage = itemView.findViewById(R.id.item_image);
            itemView = itemView1;
            itemView.setOnClickListener(this);
        }

        public void setData(int itemImage, String itemName, double price, String description, String duration){
            this.itemName.setText(itemName);
            this.price.setText((int) price);
            this.description.setText(description);
            this.duration1.setText( duration);
            this.itemImage.setImageResource(itemImage);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(position);
            }

        }
    }


    @Override
    public int getItemCount() {
        return (null != marketBizPackages ? marketBizPackages.size() : 0);
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
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }



    public interface OnItemsClickListener{
        void onItemClick(int position);
        void onItemClick(View view, int position);
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }
}
