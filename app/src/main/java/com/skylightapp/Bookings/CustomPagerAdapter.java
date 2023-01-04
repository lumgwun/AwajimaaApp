package com.skylightapp.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.skylightapp.R;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<String> mResources;

    private AQuery aQuery;
    private ImageOptions imageOptions;

    public CustomPagerAdapter(Context context, ArrayList<String> mResources) {
        mContext = context;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aQuery = new AQuery(context);
        imageOptions = new ImageOptions();
        imageOptions.fileCache = true;
        imageOptions.memCache = true;
        imageOptions.fallback = R.mipmap.logo_awa_round;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container,
                false);
        AppCompatImageView imageView = (AppCompatImageView) itemView.findViewById(R.id.imageView);
        if (TextUtils.isEmpty(mResources.get(position))) {
            aQuery.id(imageView).image(R.mipmap.logo_awa_round);
        } else {
            aQuery.id(imageView).image(mResources.get(position));
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
