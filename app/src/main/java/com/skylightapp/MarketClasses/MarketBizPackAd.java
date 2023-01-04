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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.blongho.country_data.Currency;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MarketBizPackAd extends ArrayAdapter<MarketBizPackage> {

    private Context context;
    private int resource;
    private String packType;
    private Uri packageImage;
    private List<MarketBizPackage> bizPackages;

    public MarketBizPackAd(Context context, int resource, ArrayList<MarketBizPackage> bizPackages) {
        super(context, resource, bizPackages);
        this.context = context;
        this.resource = resource;
        this.bizPackages = bizPackages;
    }

    public MarketBizPackAd(FragmentActivity activity, int package_array_item, ArrayList<MarketBizPackage> packages) {
        super(activity, package_array_item, packages);
        this.context = activity;
        this.resource = package_array_item;
        this.bizPackages = packages;
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        MarketBizPackage marketBizPackage = getItem(position);
        if(marketBizPackage !=null){
            packType=marketBizPackage.getPackageType();
            packageImage= marketBizPackage.getPackageImage();
        }

        MaterialCardView cardView = convertView.findViewById(R.id.card_l1);
        AppCompatTextView txtPackType = convertView.findViewById(R.id.package_adap);
        CircularImageView packImg = convertView.findViewById(R.id.package_img);
        Glide.with(context).load(packImg).placeholder(R.drawable.ic_admin_panel)
                .centerCrop().into(packImg);


        txtPackType.setText(MessageFormat.format("Package Type{0}", packType));

        return convertView;
    }
}
