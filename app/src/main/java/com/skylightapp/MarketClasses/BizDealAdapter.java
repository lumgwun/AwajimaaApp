package com.skylightapp.MarketClasses;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Markets.MyBizDealListAct;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class BizDealAdapter extends RecyclerView.Adapter<BizDealAdapter.ListUserViewHolder> {
    private Context mContext;
    private ArrayList<BusinessDeal> mList;
    List<BusinessDeal> selectedDeals;
    public OnBizDealClickListener listener;
    private BusinessDeal businessDeal;
    private BizDealAccount bizDealAccount;
    private String tittle;
    private long createdBy;
    private String createdAt;
    private String currency;
    private int profileID;
    ArrayList<String> dealPartners;
    ArrayList<BizDealMileStone> bizDealMileStones;
    ArrayList<BizDealPartner> bizDealPartners;
    ArrayList<BizDealRemittance> bizDealRemittances;
    ArrayList<BizDealTimeLine> bizDealTimeLines;
    ArrayList<BusinessDealSub> businessDealSubs;
    ArrayList<BusinessDealDoc> businessDealDocs;
    ArrayList<BusinessDealLoan> businessDealLoans;
    ArrayList<BizDealChatMessage> bizDealChatMessages;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private Profile bizProfile;
    private  boolean iAmTheSender;
    private long marketBizID,dealSenderMarketBizID,dealReceiverMarketBizID;
    String status;
    PrefManager prefManager;
    private MarketBusiness marketBusiness;

    public BizDealAdapter(Context mContext, ArrayList<BusinessDeal> mList, OnBizDealClickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    public BizDealAdapter(MyBizDealListAct mContext, ArrayList<BusinessDeal> businessDealsNew) {
        this.mContext = mContext;
        this.mList = businessDealsNew;
    }

    private boolean isSenderMe(BusinessDeal businessDeal) {
        return this.businessDeal != null && this.businessDeal.getDealTittle().equals(businessDeal.getDealTittle());
    }

    protected boolean isAvailableForSelection(BusinessDeal deal) {
        return businessDeal == null || !businessDeal.getDealTittle().equals(deal.getDealTittle());
    }
    public void addNewList(List<BusinessDeal> businessDealList) {
        mList.clear();
        mList.addAll(businessDealList);
        for (BusinessDeal deal : businessDealList) {
            if (isSenderMe(deal)) {
                mList.remove(deal);
            }
        }
        notifyDataSetChanged();
    }

    public void addUsers(List<BusinessDeal> businessDealList) {
        for (BusinessDeal businessDeal : businessDealList) {
            if (!mList.contains(businessDeal)) {
                mList.add(businessDeal);
            }
        }
        notifyDataSetChanged();
    }


    public void removeUsers(List<BusinessDeal> dealList) {
        for (BusinessDeal businessDeal : dealList) {
            mList.remove(businessDeal);
        }
        notifyDataSetChanged();
    }

    public void clearList() {
        mList.clear();
        notifyDataSetChanged();
    }
    public List<BusinessDeal> getSelectedDeals() {
        return mList;
    }

    public void setSelectedItemsCountsChangedListener(OnBizDealClickListener selectedItemsCountChanged) {
        if (selectedItemsCountChanged != null) {
            this.listener = selectedItemsCountChanged;
        }
    }
    public void updateUsersList(ArrayList<BusinessDeal> usersList) {
        this.mList = usersList;
        notifyDataSetChanged();
    }

    private void toggleSelection(BusinessDeal businessDeal) {
        if (selectedDeals.contains(businessDeal)){
            selectedDeals.remove(businessDeal);
        } else {
            selectedDeals.add(businessDeal);
        }
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public BizDealAdapter.ListUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.deal_biz_layout,parent,false);
        return new BizDealAdapter.ListUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BizDealAdapter.ListUserViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        marketBusiness= new MarketBusiness();
        businessDeal = mList.get(position);
        if(businessDeal !=null){
            bizDealAccount = businessDeal.getBizDealBDAccount();
            tittle=businessDeal.getDealTittle();
            createdBy=businessDeal.getDealFromBizID();
            createdAt=businessDeal.getDealDateCreated();
            currency=businessDeal.getDealCurrency();
            dealSenderMarketBizID=businessDeal.getDealFromBizID();
            dealReceiverMarketBizID=businessDeal.getDealToBizID();
            status= businessDeal.getDealStatus();
        }
        //marketBizID,dealSenderMarketBizID,dealReceiverMarketBizID;

        userPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        bizProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        if(marketBusiness !=null){
            marketBizID=marketBusiness.getBusinessID();

        }

        holder.txtDealTittle.setText(tittle);
        holder.txtCreatedBy.setText("From: "+createdBy);
        holder.txtCreatedAt.setText("Create at :"+createdAt);
        holder.txtDealCurrency.setText("Currency:"+currency);
        if(dealSenderMarketBizID==marketBizID){
            holder.btnRequest.setVisibility(View.GONE);

        }
        if(status.equalsIgnoreCase("Accepted")){
            holder.imageView.setImageResource(R.drawable.verified3);

        }
        if(status.equalsIgnoreCase("New")){
            holder.imageView.setImageResource(R.drawable.unverified);

        }
        if(dealSenderMarketBizID !=marketBizID){
            if(status !=null){
                if(status.equalsIgnoreCase("New")){
                    holder.btnRequest.setVisibility(View.VISIBLE);
                    holder.imageView.setImageResource(R.drawable.unverified);

                }
                if(status.equalsIgnoreCase("Accepted")){
                    holder.btnRequest.setVisibility(View.GONE);
                    holder.imageView.setImageResource(R.drawable.verified3);

                }
            }
            holder.btnRequest.setVisibility(View.GONE);

        }

        if (selectedDeals.contains(businessDeal)) {
            holder.layout.setBackgroundResource(R.color.background_color_selected_user_item);
            holder.imageView.setBackgroundDrawable(
                    UiUtils.getColoredCircleDrawable(mContext.getResources().getColor(R.color.icon_background_color_selected_user)));
            holder.imageView.setImageResource(R.drawable.ic_all_cus);
        } else {
            holder.layout.setBackgroundResource(R.color.background_color_normal_user_item);
            //holder.imageView.setBackgroundDrawable(UiUtils.getColorCircleDrawable(businessDeal.getPartnerName().hashCode()));
            //holder.imageView.setImageResource(R.drawable.ic_admin_user);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSelection(businessDeal);
                listener.onCountSelectedItemsChanged(selectedDeals.size());
            }
        });

        /*holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSelection(businessDeal);
                listener.onCountSelectedItemsChanged(selectedDeals.size());
                if(mList.get(position).isSelected()) {
                    holder.userSelected.setVisibility(View.INVISIBLE);
                    mList.get(position).setSelected(false);
                }
                else
                {
                    holder.userSelected.setVisibility(View.VISIBLE);
                    mList.get(position).setSelected(true);
                }
                listener.UserSelected(mList.get(position));
            }
        });
        String userName = TextUtils.isEmpty(businessDeal.getPartnerName()) ? businessDeal.getPartnerBrandName() : businessDeal.getPartnerName();

        if (isSenderMe(businessDeal)) {
            holder.txtName.setText(mContext.getString(R.string.placeholder_username_you, userName));
            holder.userCheckBox.setVisibility(View.GONE);
        } else {
            holder.txtName.setText(userName);
            //holder.userCheckBox.setVisibility(View.VISIBLE);
        }*/

        if (isAvailableForSelection(businessDeal)) {
            holder.txtDealTittle.setTextColor(ResourceUtils.getColor(R.color.text_color_black));
            holder.userCheckBox.setVisibility(View.VISIBLE);


        } else {
            holder.txtDealTittle.setTextColor(ResourceUtils.getColor(R.color.text_color_medium_grey));
            holder.userCheckBox.setVisibility(View.GONE);

        }
        boolean containsUser = selectedDeals.contains(businessDeal);
        holder.userCheckBox.setChecked(containsUser);

        if (containsUser) {
            holder.layout.setBackgroundColor(ResourceUtils.getColor(R.color.selected_list_item_color));
        } else {
            holder.layout.setBackgroundColor(ResourceUtils.getColor(android.R.color.transparent));
        }




       /* if (!TextUtils.isEmpty(businessDeal.getPartnerName())) {
            String avatarTitle = String.valueOf(businessDeal.getPartnerName().charAt(0)).toUpperCase();
            holder.txtName.setText(avatarTitle);
        }*/
    }

    @Override
    public int getItemCount() {
        Log.e("Adapter", "getItemCount: " +mList.size() );
        return mList.size();
    }

    static class ListUserViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView txtDealTittle,txtCreatedBy,txtCreatedAt,txtDealCurrency;
        RelativeLayout layout;
        CircularImageView imageView,userSelected;
        private AppCompatCheckBox userCheckBox;
        private AppCompatButton btnRequest;
        public ListUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDealTittle = itemView.findViewById(R.id.dealTittle);
            txtCreatedBy = itemView.findViewById(R.id.deal_created_By);
            layout = itemView.findViewById(R.id.deal_biz_layout);
            txtCreatedAt = itemView.findViewById(R.id.deal_created_Date);
            btnRequest = itemView.findViewById(R.id.deal_requestBtn);
            txtDealCurrency = itemView.findViewById(R.id.deal_Acct_Cur);
            imageView  = itemView.findViewById(R.id.deal_statusS);
            userSelected = itemView.findViewById(R.id.partner_SelectedForGroup);
            userCheckBox = itemView.findViewById(R.id.checkbox_bDeal);
        }
    }

    public interface OnBizDealClickListener {
        void DealSelected(BusinessDeal businessDeal,int position);
        void DealSelected(BusinessDeal businessDeal);
        void onCountSelectedItemsChanged(Integer count);
    }
}
