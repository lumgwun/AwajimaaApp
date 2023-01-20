package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.Currency;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Customers.CusPackageList;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MBizRecyA extends RecyclerView.Adapter<MBizRecyA.RecyclerViewHolder> {


    private ArrayList<MarketBusiness> businessArrayList;
    private List<MarketBusiness> businessList;
    private Context mcontext;
    int resources;
    private OnItemsClickListener listener;
    private Profile profile;
    private String bizLogo, bizStatus,type,name;
    private MarketBusiness marketBusiness;
    private Currency currency;

    public MBizRecyA(ArrayList<MarketBusiness> recyclerDataArrayList, Context mcontext) {
        this.businessArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public MBizRecyA(Context context, int resources, ArrayList<MarketBusiness> businessArrayList) {
        this.businessArrayList = businessArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public MBizRecyA(Context context, List<MarketBusiness> businesses) {
        this.businessList = businesses;
        this.mcontext = context;
    }

    public MBizRecyA(CusPackageList cusPackageList, ArrayList<MarketBusiness> businessArrayList) {
        this.businessList = businessArrayList;
        this.mcontext = cusPackageList;
    }
    public void updateItems(ArrayList<MarketBusiness> list) {
        this.businessArrayList = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void deleteItem(int position) {
        this.businessArrayList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(MarketBusiness sliderItem) {
        this.businessArrayList.add(sliderItem);
        notifyDataSetChanged();
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MBizRecyA.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.biz_row, parent, false);
        return new MBizRecyA.RecyclerViewHolder(view);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView brandName,customerID;
        public final BezelImageView bizLogo;
        private AppCompatTextView  bizType;
        public AppCompatButton btnMore;
        LinearLayoutCompat p_01,p_41;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            brandName = convertView.findViewById(R.id.biz_n);
            bizLogo = convertView.findViewById(R.id.b_icon);
            bizType = convertView.findViewById(R.id.type_biz);

        }
    }

    @SuppressLint({"ResourceAsColor", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MarketBusiness marketBusiness = businessArrayList.get(position);
        if(marketBusiness !=null){
            bizStatus =marketBusiness.getBizStatus();
            bizLogo=marketBusiness.getBizDrivePix();
            type =marketBusiness.getBizStatus();
            name =marketBusiness.getBizStatus();

        }

        if (marketBusiness != null) {
            holder.bizType.setText(MessageFormat.format("Type:{0}", type));
            holder.brandName.setText(MessageFormat.format("Brand Name:", name));
            Glide.with(mcontext)
                    .asBitmap()
                    .load(R.drawable.ifesinachi)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    //.listener(listener)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(holder.bizLogo);


        }


    }
    @Override
    public int getItemCount() {
        return (null != businessArrayList ? businessArrayList.size() : 0);
    }
    private String removeNewLinesDividers(String text) {
        int decoratedTextLength = Math.min(text.length(), Utils.SkyLightPackage33.MAX_TEXT_LENGTH_IN_LIST);
        return text.substring(0, decoratedTextLength).replaceAll("\n", " ").trim();
    }

    public interface OnClickListener {
        void onItemClick(int position);

    }
    public interface OnItemsClickListener{
        void onBizClick(MarketBusiness marketBusiness);
    }
}
