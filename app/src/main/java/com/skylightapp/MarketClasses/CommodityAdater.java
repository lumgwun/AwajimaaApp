package com.skylightapp.MarketClasses;

import android.content.Context;
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
import com.google.android.material.card.MaterialCardView;
import com.skylightapp.Markets.MarketDetailsAct;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommodityAdater extends RecyclerView.Adapter<CommodityAdater.MyViewHolder> implements View.OnClickListener, Filterable {


    private ArrayList<MarketCommodity> data;
    private OnCommodityClickListener listener;
    private List<MarketCommodity> theSlideItemsModelClassList = new ArrayList<>();
    private List<MarketCommodity> itemsListFilter = new ArrayList<>();
    String comName;
    String comType;
    String comDes;
    int comQty;
    private Context context;
    private int comImg;
    private MarketCommodity marketCommodity;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f );
    protected AlphaAnimation fadeIn = new AlphaAnimation( 0.0f , 1.0f );

    public CommodityAdater(MarketDetailsAct marketDetailsAct, ArrayList<MarketCommodity> marketCommodities) {
        this.context = marketDetailsAct;
        this.data = marketCommodities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mComodityName;
        private TextView mType;
        private TextView mDescript;
        private TextView mQty;
        private ImageView comImg;
        MaterialCardView materialCardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mComodityName = itemView.findViewById(R.id.com_name);
            mType = itemView.findViewById(R.id.com_type);
            mDescript = itemView.findViewById(R.id.com_des);
            mQty = itemView.findViewById(R.id.com_qty);
            comImg = itemView.findViewById(R.id.img_acom);
            materialCardView = itemView.findViewById(R.id.cardViewC);
        }
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemsListFilter.clear();
        if (charText.length() == 0) {
            itemsListFilter.addAll(data);
        } else {
            for (MarketCommodity wp : data) {
                if (wp.getCommodityName().toLowerCase(Locale.getDefault()).contains(charText)) {
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
                    itemsListFilter = data;
                } else {
                    List<MarketCommodity> filteredList = new ArrayList<>();
                    for (MarketCommodity marketCommodity : data) {
                        if (marketCommodity.getCommodityName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(marketCommodity);
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
                itemsListFilter = (ArrayList<MarketCommodity>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }



    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onCommodityClick(marketCommodity);
        }

    }
    public CommodityAdater(Context context, ArrayList<MarketCommodity> marketCommodityArrayList, OnCommodityClickListener callback) {
        this.context = context;
        this.data = marketCommodityArrayList;
        this.listener = callback;


    }

    public void updateItems(ArrayList<MarketCommodity> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public CommodityAdater(ArrayList<MarketCommodity> data) {
        this.data = data;
    }
    public void renewItems(ArrayList<MarketCommodity> sliderItems) {
        this.data = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.data.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(MarketCommodity sliderItem) {
        this.data.add(sliderItem);
        notifyDataSetChanged();
    }
    public void setCallback(OnCommodityClickListener callback) {
        this.listener = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MarketCommodity marketCommodity = data.get(position);
        if(marketCommodity !=null){
            comName= marketCommodity.getCommodityName();
            comType= marketCommodity.getCommodityType();
            comDes= marketCommodity.getCommodityDes();
            comQty= marketCommodity.getCommodityQty();
            comImg= marketCommodity.getCommodityImage();
        }

        holder.mComodityName.setText(MessageFormat.format("Name:{0}", comName));
        holder.mType.setText(MessageFormat.format("Type:{0}", comType));
        holder.mDescript.setText(MessageFormat.format("Desc:{0}", comDes));
        holder.mQty.setText(MessageFormat.format("Qty: NGN{0}", comQty));
        try {
            Glide.with(context).load(comImg).into(holder.comImg);

            /*Glide.with(context).asBitmap().load(comImg).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(holder.comImg);*/
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }
    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }
    public void setWhenClickListener(OnCommodityClickListener listener){
        this.listener = listener;
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void restoreItem(MarketCommodity item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public ArrayList<MarketCommodity> getData() {
        return data;
    }
    public interface OnCommodityClickListener {
        void onCommodityClick(MarketCommodity marketCommodity);
    }
}
