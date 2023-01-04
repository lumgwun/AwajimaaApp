package com.skylightapp.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skylightapp.MarketClasses.MarketBizPackModel;
import com.skylightapp.Markets.ProductDetailsAct;
import com.skylightapp.R;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    List<MarketBizPackModel> horizontalProductScrollModelList;

    public GridProductLayoutAdapter(List<MarketBizPackModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hor_scroll_item, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent =new Intent(parent.getContext(), ProductDetailsAct.class);
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });

            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productDescription = view.findViewById(R.id.h_s_product_description);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);

            //productImage.setImageResource(horizontalProductScrollModelList.get(position).getProductImage());
            Glide.with(parent.getContext()).load(horizontalProductScrollModelList.get(position).getpMItemImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_right_tab_round)).into(productImage);
            productTitle.setText(horizontalProductScrollModelList.get(position).getpMItemName());
            productDescription.setText(horizontalProductScrollModelList.get(position).getpMdesc());
            productPrice.setText("NGN."+horizontalProductScrollModelList.get(position).getpMPrice()+"/-");



        } else {
            view = convertView;
        }
        return view;
    }
}
