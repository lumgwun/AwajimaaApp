package com.skylightapp.Bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;
;

import com.skylightapp.R;
import com.viewpagerindicator.CirclePageIndicator;

public class TourDetailsFragment extends TourBaseFragment {
    private View view;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;

    private CirclePageIndicator imageIndicator;
    private AppCompatTextView tvTourDetails;
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_tour_details, container,
                false);

        args = getArguments();
        tvTourDetails = (AppCompatTextView) view.findViewById(R.id.tvTourDetails);

        tvTourDetails.setText(args.getString(BookingConstant.Params.TOUR_DESC, ""));
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity(),
                args.getStringArrayList(BookingConstant.Params.TOUR_IMAGES));
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        imageIndicator = (CirclePageIndicator) view
                .findViewById(R.id.indicator);
        //imageIndicator.setViewPager(mViewPager);

        return view;
    }

    @Override
    public void onClick(View arg0) {

    }

    @Override
    protected boolean isValidate() {
        return false;
    }
}
