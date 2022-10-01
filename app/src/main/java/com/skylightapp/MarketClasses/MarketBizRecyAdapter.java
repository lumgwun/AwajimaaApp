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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.Currency;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Customers.CusPackageList;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MarketBizRecyAdapter extends RecyclerView.Adapter<MarketBizRecyAdapter.RecyclerViewHolder> {


    private ArrayList<MarketBusiness> businessArrayList;
    private List<MarketBusiness> businessList;
    private Context mcontext;
    int resources;
    private OnItemsClickListener listener;
    private Profile profile;
    private String curCode, bizStatus;
    private MarketBusiness marketBusiness;
    private Currency currency;

    public MarketBizRecyAdapter(ArrayList<MarketBusiness> recyclerDataArrayList, Context mcontext) {
        this.businessArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public MarketBizRecyAdapter(Context context, int resources, ArrayList<MarketBusiness> businessArrayList) {
        this.businessArrayList = businessArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public MarketBizRecyAdapter(Context context, List<MarketBusiness> businesses) {
        this.businessList = businesses;
        this.mcontext = context;
    }

    public MarketBizRecyAdapter(CusPackageList cusPackageList, ArrayList<MarketBusiness> businessArrayList) {
        this.businessList = businessArrayList;
        this.mcontext = cusPackageList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_biz_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView desc, brandName, bizMarketID,packageAmount, bizLogo,customerID;
        public final BezelImageView ImageIcon;
        private TextView bizDateJoined, bizState,profileManager, bizRevenueAmt,grandTotalAmount, bizType,duration,packageID;
        public AppCompatButton btnMore;
        LinearLayoutCompat p_01,p_41;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            ImageIcon = convertView.findViewById(R.id.thumbnail_mb);
            brandName = convertView.findViewById(R.id.mb_name);
            bizType = convertView.findViewById(R.id.type_mb);
            desc = convertView.findViewById(R.id.mb_desc);
            bizLogo = convertView.findViewById(R.id.thumbnail_);
            bizState = convertView.findViewById(R.id.mb_state);
            bizDateJoined = convertView.findViewById(R.id.mb_date_joined);
            bizRevenueAmt = convertView.findViewById(R.id.mb_revenue);
            bizMarketID = convertView.findViewById(R.id.mb_market_ID);

        }
    }

    @SuppressLint({"ResourceAsColor", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MarketBusiness marketBusiness = businessArrayList.get(position);
        if(marketBusiness !=null){
            profile = marketBusiness.getmBusOwner();
            currency=marketBusiness.getMarketBizCurrency();
            bizStatus =marketBusiness.getBizStatus();

        }
        if(currency !=null){
            curCode =currency.getCode();
        }

        if (marketBusiness != null) {
            holder.bizType.setText(MessageFormat.format("Type:{0}", marketBusiness.getBizType()));
            holder.brandName.setText(MessageFormat.format("Brand Name:", marketBusiness.getBizBrandname()));
            holder.desc.setText(MessageFormat.format("Desc:{0}", marketBusiness.getBizDescription()));
            holder.bizLogo.setText(MessageFormat.format("Biz Logo:{0},{1}", marketBusiness.bizPicture));
            holder.bizState.setText(MessageFormat.format("State:{0}", marketBusiness.getBizState()));
            holder.bizDateJoined.setText(MessageFormat.format("Joined date:{0}", marketBusiness.getDateOfJoin()));
            holder.bizRevenueAmt.setText(MessageFormat.format("Revenue Amount: curCode{0}", String.format("%.2f", marketBusiness.getMarketBizRevenue())));
            holder.bizMarketID.setText(MessageFormat.format("Market ID:{0}", String.valueOf(marketBusiness.getBizMarketID())));

            if (bizStatus.equalsIgnoreCase("Verified")) {
                holder.ImageIcon.setImageResource(R.drawable.verified3);
                holder.brandName.setTextColor(Color.BLACK);
            } else if (bizStatus .equalsIgnoreCase("unVerified")) {
                holder.ImageIcon.setImageResource(R.drawable.unverified);
                holder.brandName.setTextColor(Color.RED);
            } else if (bizStatus.equalsIgnoreCase("")) {
                holder.ImageIcon.setImageResource(R.drawable.ic_add_business);
                holder.brandName.setTextColor(android.R.color.holo_blue_dark);
            }

        }


    }
    @Override
    public int getItemCount() {
        return (null != businessArrayList ? businessArrayList.size() : 0);
    }
    private String removeNewLinesDividers(String text) {
        int decoratedTextLength = Math.min(text.length(), Utils.SkyLightPackage.MAX_TEXT_LENGTH_IN_LIST);
        return text.substring(0, decoratedTextLength).replaceAll("\n", " ").trim();
    }

    public interface OnClickListener {
        void onItemClick(int position);

    }
    public interface OnItemsClickListener{
        void onItemClick(MarketBusiness marketBusiness);
    }
}
