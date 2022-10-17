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

import com.bumptech.glide.Glide;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MBPartnerArrayA extends ArrayAdapter<MarketBizPartner> {

    private Context context;
    private int resource;
    private Uri partnerLogo;
    private int partnerID;
    private ArrayList<MarketBizPartner> businesses;

    private String partnerName;

    public MBPartnerArrayA(Context context, int resource, ArrayList<MarketBizPartner> businessArrayList) {
        super(context, resource, businessArrayList);
        this.context = context;
        this.resource = resource;
        this.businesses = businessArrayList;
    }

    public MBPartnerArrayA(FragmentActivity activity, int biz_partner_item, ArrayList<MarketBizPartner> bizPartners) {
        super(activity, biz_partner_item, bizPartners);
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        MarketBizPartner marketBizPartner = getItem(position);
        if(marketBizPartner !=null){
            partnerLogo =marketBizPartner.getBizPartnerLogo();
            partnerName =marketBizPartner.getBizPartnerName();
        }

        TextView txtBizPartnerName = convertView.findViewById(R.id.partner_name);
        CircleImageView bizLogo = convertView.findViewById(R.id.logo_partner);



        assert marketBizPartner != null;
        txtBizPartnerName.setText(MessageFormat.format("{0}", partnerName));

        Glide.with(context)
                .load(partnerLogo)
                .placeholder(R.drawable.lsgroup)
                .error(R.drawable.ic_error_black_24dp)
                .into(bizLogo);

        return convertView;
    }

}
