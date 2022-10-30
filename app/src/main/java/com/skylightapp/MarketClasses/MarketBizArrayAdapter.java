package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.blongho.country_data.Currency;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarketBizArrayAdapter extends ArrayAdapter<MarketBusiness> {

    private Context context;
    private int resource;
    private Uri businessLogo;
    private long businessID;
    private Currency currency;
    private String currencyCode,currencySymbol,bizDealStatus,dealStartDate,dealTitle;
    private ArrayList<MarketBusiness> businesses;
    private double dealAmount;
    private String bizRegNo;
    private String bizBrandName;

    public MarketBizArrayAdapter(Context context, int resource, ArrayList<MarketBusiness> businessArrayList) {
        super(context, resource, businessArrayList);
        this.context = context;
        this.resource = resource;
        this.businesses = businessArrayList;
    }

    public MarketBizArrayAdapter(FragmentActivity activity, int biz_item, ArrayList<MarketBusiness> accounts) {
        super(activity, biz_item, accounts);
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            //item_market_biz
        }

        MarketBusiness marketBusiness = getItem(position);
        if(marketBusiness !=null){
            businessLogo =marketBusiness.getBizPicture();
            businessID =marketBusiness.getBusinessID();
            bizBrandName=marketBusiness.getBizBrandname();
        }

        TextView txtBizBrandName = convertView.findViewById(R.id.market_biz_names);
        TextView bizID = convertView.findViewById(R.id.mb_IZd);
        CircleImageView bizLogo = convertView.findViewById(R.id.img_BizL);



        assert marketBusiness != null;
        txtBizBrandName.setText(MessageFormat.format("{0}", bizBrandName));
        bizID.setText(MessageFormat.format("{0}", businessID));

        Glide.with(context)
                .load(businessLogo)
                .placeholder(R.drawable.lsgroup)
                .error(R.drawable.ic_error)
                .into(bizLogo);

        return convertView;
    }

}
