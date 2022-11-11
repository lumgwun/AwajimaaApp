package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BizDealPartnerSpnA extends ArrayAdapter<BizDealPartner> {

    private Context context;
    private int resource;
    private String name,profID,country;
    private Uri profPicture;
    private List<BizDealPartner> dealPartners;
    private Profile profile;
    private Market market;

    public BizDealPartnerSpnA(Context context, int resource, ArrayList<BizDealPartner> dealPartners) {
        super(context, resource, dealPartners);
        this.context = context;
        this.resource = resource;
        this.dealPartners = dealPartners;
    }

    public BizDealPartnerSpnA(FragmentActivity activity, int deal_account, ArrayList<BizDealPartner> accounts) {
        super(activity, deal_account, accounts);
        this.context = activity;
        this.resource = deal_account;
        this.dealPartners = accounts;
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        BizDealPartner bizDealPartner = getItem(position);
        CircularImageView dealerImg = convertView.findViewById(R.id.photo_dealer);
        MaterialCardView cardView = convertView.findViewById(R.id.dealer_container);
        AppCompatTextView txtNameDealer = convertView.findViewById(R.id.name_dealer);
        AppCompatTextView txtGenderDealer = convertView.findViewById(R.id.gender_dealer);
        AppCompatTextView txtBizCACNo = convertView.findViewById(R.id.cac_biz_no);
        AppCompatTextView txtDealerMarket = convertView.findViewById(R.id.dealer_market);
        AppCompatTextView txtDealerBrandName = convertView.findViewById(R.id.address_dealer);
        AppCompatButton btnSendPartnerM = convertView.findViewById(R.id.dealer_partner_message);
        AppCompatButton btnMoreAboutPartner = convertView.findViewById(R.id.partner_moreBtn);
        if (bizDealPartner != null) {
            txtDealerMarket.setText(bizDealPartner.getPartnerMarketName());
            txtDealerBrandName.setText(bizDealPartner.getPartnerBrandName());
            txtNameDealer.setText(bizDealPartner.getPartnerName());
            txtGenderDealer.setText(bizDealPartner.getPartnerGender());
            txtBizCACNo.setText(bizDealPartner.getPartnerCACNo());
            profile=bizDealPartner.getPartnerProfile();
            market= bizDealPartner.getPartnerMarket();
            profPicture=bizDealPartner.getPartnerPix();
            //btnSendPartnerM.setText(String.format("%s %s", context.getString(R.string.account_no), bizDealPartner.getAwajimaAcctNo()));

        }
        Glide.with(context)
                .load(profPicture)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(dealerImg);
        btnSendPartnerM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMoreAboutPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
