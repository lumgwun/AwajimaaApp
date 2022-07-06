package com.skylightapp.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.R;
import com.skylightapp.SkyLightPackageActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;



public class SkyLightPackageShowCaseAdapter extends RecyclerView.Adapter<SkyLightPackageShowCaseAdapter.ViewHolder> implements ListAdapter {

    private final List<SkyLightPackModel> skyLightPackage_2List;
    private OnItemsClickListener listener;
    Context context;
    private ArrayList<SkyLightPackModel> packageList;
    private ArrayList<SkyLightPackage> skyLightPackages;

    public SkyLightPackageShowCaseAdapter(Context context, List<SkyLightPackModel> skyLightPackage_2List) {
        this.skyLightPackage_2List = skyLightPackage_2List;
        this.context = context;
    }
    public SkyLightPackageShowCaseAdapter(SkyLightPackageActivity skyLightPackageActivity, List<SkyLightPackModel> skyLightPackage_2List) {
        this.skyLightPackage_2List = skyLightPackage_2List;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pack_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SkyLightPackage model = packageList.get(position);
        final SkyLightPackModel model2= packageList.get(position);
        ViewHolder mainHolder = (ViewHolder) holder;
        mainHolder.description.setText(MessageFormat.format("Desc.{0}", model2.getpMdesc()));
        mainHolder.itemName.setText(MessageFormat.format("Item Name:{0}", model2.getpMItemName()));
        mainHolder.price.setText(MessageFormat.format("Price N{0}", model2.getpMPrice()));
        mainHolder.duration1.setText(MessageFormat.format("Duration:{0}", model2.getpMDuration()));
        mainHolder.itemImage.setImageResource(model2.getpMItemImage());
        holder.setData(skyLightPackage_2List.get(position).getpMItemImage(),skyLightPackage_2List.get(position).getpMItemName(),skyLightPackage_2List.get(position).getpMPrice(),skyLightPackage_2List.get(position).getpMdesc(),skyLightPackage_2List.get(position).getpMdesc());
    }
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView itemName;
        public TextView description;
        public TextView price;
        public TextView duration1;
        public ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.item_daily_price);
            duration1 = itemView.findViewById(R.id.duration1);
            description = itemView.findViewById(R.id.item_description);
            itemImage = itemView.findViewById(R.id.item_image);
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

        }
    }


    @Override
    public int getItemCount() {
        return (null != skyLightPackages ? skyLightPackages.size() : 0);
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
        void onItemClick(SkyLightPackModel lightPackage);
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }
}
