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

public class MBDonorArrayA extends ArrayAdapter<MarketBizDonor> {

    private Context context;
    private int resource;
    private Uri donorLogo;
    private int partnerID;
    private ArrayList<MarketBizDonor> marketBizDonors;

    private String donorName;

    public MBDonorArrayA(Context context, int resource, ArrayList<MarketBizDonor> marketBizDonors) {
        super(context, resource, marketBizDonors);
        this.context = context;
        this.resource = resource;
        this.marketBizDonors = marketBizDonors;
    }

    public MBDonorArrayA(FragmentActivity activity, int biz_donor_item, ArrayList<MarketBizDonor> bizPartners) {
        super(activity, biz_donor_item, bizPartners);
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        MarketBizDonor marketBizPartner = getItem(position);
        if(marketBizPartner !=null){
            donorLogo =marketBizPartner.getmBDonorLogo();
            donorName =marketBizPartner.getmBDonorName();
        }

        TextView txtBizDonorName = convertView.findViewById(R.id.donor_name);
        CircleImageView bizLogo = convertView.findViewById(R.id.donor_img);



        assert marketBizPartner != null;
        txtBizDonorName.setText(MessageFormat.format("{0}", donorName));

        Glide.with(context)
                .load(donorLogo)
                .placeholder(R.drawable.lsgroup)
                .error(R.drawable.alertbox_cross_icon_credit)
                .into(bizLogo);

        return convertView;
    }
}
