package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class BizDPartnerSelectAd extends RecyclerView.Adapter<BizDPartnerSelectAd.ListUserViewHolder> {
    private Context mContext;
    private ArrayList<BizDealPartner> mList;
    List<BizDealPartner> selectedUsers;
    public OnPartnerClickListener listener;
    private BizDealPartner bizDealPartner;
    private QBUser qbUser;
    private String name;
    private int profileID;

    public BizDPartnerSelectAd(Context mContext, ArrayList<BizDealPartner> mList, OnPartnerClickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }
    private boolean isUserMe(BizDealPartner user) {
        return bizDealPartner != null && bizDealPartner.getPartnerName().equals(user.getPartnerName());
    }

    protected boolean isAvailableForSelection(BizDealPartner user) {
        return bizDealPartner == null || !bizDealPartner.getPartnerName().equals(user.getPartnerName());
    }
    public void addNewList(List<BizDealPartner> users) {
        mList.clear();
        mList.addAll(users);
        for (BizDealPartner user : users) {
            if (isUserMe(user)) {
                mList.remove(user);
            }
        }
        notifyDataSetChanged();
    }

    public void addUsers(List<BizDealPartner> users) {
        for (BizDealPartner user : users) {
            if (!mList.contains(user)) {
                mList.add(user);
            }
        }
        notifyDataSetChanged();
    }


    public void removeUsers(List<BizDealPartner> users) {
        for (BizDealPartner user : users) {
            mList.remove(user);
        }
        notifyDataSetChanged();
    }

    public void clearList() {
        mList.clear();
        notifyDataSetChanged();
    }
    public List<BizDealPartner> getSelectedUsers() {
        return mList;
    }

    public void setSelectedItemsCountsChangedListener(OnPartnerClickListener selectedItemsCountChanged) {
        if (selectedItemsCountChanged != null) {
            this.listener = selectedItemsCountChanged;
        }
    }
    public void updateUsersList(ArrayList<BizDealPartner> usersList) {
        this.mList = usersList;
        notifyDataSetChanged();
    }

    private void toggleSelection(BizDealPartner qbUser) {
        if (selectedUsers.contains(qbUser)){
            selectedUsers.remove(qbUser);
        } else {
            selectedUsers.add(qbUser);
        }
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ListUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.deal_user_layout,parent,false);
        return new ListUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListUserViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        bizDealPartner = mList.get(position);
        if(bizDealPartner !=null){
            qbUser=bizDealPartner.getUser();
        }
        if(qbUser !=null){
            profileID =qbUser.getFileId();
            name=qbUser.getFullName();
        }

        holder.txtName.setText(name);

        if(profileID >0) {
            QBContent.getFile(profileID).performAsync(new QBEntityCallback<QBFile>() {
                @Override
                public void onSuccess(QBFile qbFile, Bundle bundle) {
                    String url = qbFile.getPublicUrl();
                    Glide.with(mContext).load(url).apply(RequestOptions.circleCropTransform()).
                            placeholder(R.drawable.user_best).into(holder.imageView);
                }

                @Override
                public void onError(QBResponseException e) {
                }
            });
        }
        if (selectedUsers.contains(bizDealPartner)) {
            holder.layout.setBackgroundResource(R.color.background_color_selected_user_item);
            holder.imageView.setBackgroundDrawable(
                    UiUtils.getColoredCircleDrawable(mContext.getResources().getColor(R.color.icon_background_color_selected_user)));
            holder.imageView.setImageResource(R.drawable.ic_all_cus);
        } else {
            holder.layout.setBackgroundResource(R.color.background_color_normal_user_item);
            holder.imageView.setBackgroundDrawable(UiUtils.getColorCircleDrawable(bizDealPartner.getPartnerName().hashCode()));
            holder.imageView.setImageResource(R.drawable.ic_admin_user);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSelection(bizDealPartner);
                listener.onCountSelectedItemsChanged(selectedUsers.size());
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSelection(bizDealPartner);
                listener.onCountSelectedItemsChanged(selectedUsers.size());
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
        String userName = TextUtils.isEmpty(bizDealPartner.getPartnerName()) ? bizDealPartner.getPartnerBrandName() : bizDealPartner.getPartnerName();

        if (isUserMe(bizDealPartner)) {
            holder.txtName.setText(mContext.getString(R.string.placeholder_username_you, userName));
            holder.userCheckBox.setVisibility(View.GONE);
        } else {
            holder.txtName.setText(userName);
            //holder.userCheckBox.setVisibility(View.VISIBLE);
        }

        if (isAvailableForSelection(bizDealPartner)) {
            holder.txtName.setTextColor(ResourceUtils.getColor(R.color.text_color_black));
            holder.userCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.txtName.setTextColor(ResourceUtils.getColor(R.color.text_color_medium_grey));
            holder.userCheckBox.setVisibility(View.GONE);

        }
        boolean containsUser = selectedUsers.contains(bizDealPartner);
        holder.userCheckBox.setChecked(containsUser);

        if (containsUser) {
            holder.layout.setBackgroundColor(ResourceUtils.getColor(R.color.selected_list_item_color));
        } else {
            holder.layout.setBackgroundColor(ResourceUtils.getColor(android.R.color.transparent));
        }




        if (!TextUtils.isEmpty(bizDealPartner.getPartnerName())) {
            String avatarTitle = String.valueOf(bizDealPartner.getPartnerName().charAt(0)).toUpperCase();
            holder.txtName.setText(avatarTitle);
        }
    }

    @Override
    public int getItemCount() {
        Log.e("Adapter", "getItemCount: " +mList.size() );
        return mList.size();
    }

    static class ListUserViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView txtName;
        RelativeLayout layout;
        CircularImageView imageView,userSelected;
        private final AppCompatCheckBox userCheckBox;
        public ListUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.listPartnerName);
            layout = itemView.findViewById(R.id.deal_item_layout);
            imageView  = itemView.findViewById(R.id.listPartnerPix);
            userSelected = itemView.findViewById(R.id.partner_SelectedForGroup);
            userCheckBox = itemView.findViewById(R.id.checkbox_partner);
        }
    }

    public interface OnPartnerClickListener {
        void UserSelected(BizDealPartner dealPartner,int position);
        void UserSelected(BizDealPartner bizDealPartner);
        void onCountSelectedItemsChanged(Integer count);
    }
}
