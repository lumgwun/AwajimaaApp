package com.skylightapp.MarketClasses;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.MyViewHolder> implements View.OnClickListener, Filterable {


    private ArrayList<Farm> data;
    private OnItemsClickListener listener;
    private List<Farm> theSlideItemsModelClassList = new ArrayList<>();
    private List<Farm> itemsListFilter = new ArrayList<>();
    String farmName;
    String comType;
    String farmType,farmLGA,farmStatus,commodityName,farmStartDate,farmState,farmCountry;
    int farmID;
    private Location farmLoc;
    private Context context;
    private int farmLogo;
    private double farmRev;
    private FarmCommodity farmCommodity;
    private Farm farm;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f );
    protected AlphaAnimation fadeIn = new AlphaAnimation( 0.0f , 1.0f );


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemsListFilter.clear();
        if (charText.length() == 0) {
            itemsListFilter.addAll(data);
        } else {
            for (Farm farm1 : data) {
                if (farm1.getFarmName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemsListFilter.add(farm1);
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
                    itemsListFilter = data;
                } else {
                    List<Farm> filteredList = new ArrayList<>();
                    for (Farm farm : data) {
                        if (farm.getFarmName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(farm);
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
                itemsListFilter = (ArrayList<Farm>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }



    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(farm);
        }

    }

    public void updateItems(ArrayList<Farm> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public FarmAdapter(ArrayList<Farm> data) {
        this.data = data;
    }
    public void renewItems(ArrayList<Farm> sliderItems) {
        this.data = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.data.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Farm sliderItem) {
        this.data.add(sliderItem);
        notifyDataSetChanged();
    }
    public void setCallback(OnItemsClickListener callback) {
        this.listener = callback;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mFarmName;
        private TextView mType;
        private TextView mState,lga,status,startDate, farmComName;
        private TextView mRevenue;
        private ImageView farmImgLogo;
        MaterialCardView materialCardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mFarmName = itemView.findViewById(R.id.farm_name);
            mType = itemView.findViewById(R.id.farm_type);
            mState = itemView.findViewById(R.id.farm_state);
            mRevenue = itemView.findViewById(R.id.farm_Rev);
            farmComName = itemView.findViewById(R.id.farm_com);
            farmImgLogo = itemView.findViewById(R.id.farm_img);
            materialCardView = itemView.findViewById(R.id.farm_cardView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.farm_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Farm farm = data.get(position);
        if(farm !=null){
            farmName = farm.getFarmName();
            farmType= farm.getFarmType();
            farmLGA= farm.getFarmLGA();
            farmCountry= farm.getFarmCountry();
            farmLoc= farm.getFarmLocation();
            farmID= farm.getFarmMarketID();
            farmState= farm.getFarmState();
            farmRev= farm.getFarmRevenue();
            farmLogo= farm.getFarmLogo();
            farmStartDate= farm.getFarmStartDate();
            farmStatus= farm.getFarmStatus();
            farmCommodity=farm.getFarmCommodity();
        }
        if(farmCommodity !=null){
            commodityName =farmCommodity.getFarmComName();

        }

        holder.mFarmName.setText(MessageFormat.format("Name:{0}", farmName));
        holder.mType.setText(MessageFormat.format("Type:{0}", farmType));
        holder.mState.setText(MessageFormat.format("State:{0}", farmState));
        holder.mRevenue.setText(MessageFormat.format("Revenue: NGN{0}", farmRev));
        holder.farmComName.setText(MessageFormat.format("Product: 0}", commodityName));
        holder.mRevenue.setText(MessageFormat.format("Revenue: NGN{0}", farmRev));


        Glide.with(context)
                .asBitmap()
                .load(farmLogo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(holder.farmImgLogo);
    }
    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void restoreItem(Farm item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public ArrayList<Farm> getData() {
        return data;
    }
    public interface OnItemsClickListener{
        void onItemClick(Farm farm);
    }
}
