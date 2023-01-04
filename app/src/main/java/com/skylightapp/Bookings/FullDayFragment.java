package com.skylightapp.Bookings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;

import com.skylightapp.R;

import java.util.ArrayList;

public class FullDayFragment extends TourBaseFragment {

    private View view;
    private Bundle args;
    private AppCompatTextView tvFDayHightLight,tvFdayCommonPrice,tvPrivatePrice,tvFullDayRTime,tvPrivatePerson,tvFullDayDTime;
    private TableLayout tblFdayPrivateTours;
    private ArrayList<PTour> fullDayPriceList = new ArrayList<PTour>();
    private PTour pTour;
    private TableRow tblRow;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_fullday, container, false);
        args = getArguments();
        fullDayPriceList = (ArrayList<PTour>) args
                .getSerializable(BookingConstant.Params.FULL_DAY_PRIVATE_PRICE);
        if (fullDayPriceList.size() == 0) {
            view.findViewById(R.id.ivEmptyView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.layoutFullDay).setVisibility(View.GONE);
            activity.btnBookTour.setVisibility(View.GONE);
            return view;
        }
        activity.btnBookTour.setVisibility(View.VISIBLE);
        tvFDayHightLight = view.findViewById(R.id.tvFDayHightLight);
        tvFdayCommonPrice =  view
                .findViewById(R.id.tvFdayCommonPrice);
        tblFdayPrivateTours = (TableLayout) view
                .findViewById(R.id.tblFdayPrivateTours);
        tvFullDayDTime =  view.findViewById(R.id.tvFullDayDTime);
        tvFullDayRTime =  view.findViewById(R.id.tvFullDayRTime);
        tvFullDayDTime.setText(args.getString(BookingConstant.Params.FULL_DAY_DTIME));
        tvFullDayRTime.setText(args.getString(BookingConstant.Params.FULL_DAY_RTIME));
        tvFDayHightLight.setText("- " + args.getString(BookingConstant.Params.FULL_DAY_HIGHLIGHT).replace(",",
                "\n- "));
        tvFdayCommonPrice.setText("NGN"
                + args.getDouble(BookingConstant.Params.FULL_DAY_COMMON_PRICE));

        for (int i = 0; i < fullDayPriceList.size(); i++) {
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tvParams.setMargins(
                    (int) getResources().getDimension(R.dimen._8sdp),
                    0, 0,
                    (int) getResources().getDimension(R.dimen._8sdp));

            tblRow = new TableRow(getActivity());
            pTour = fullDayPriceList.get(i);
            tvPrivatePrice = new AppCompatTextView(getActivity());
            tvPrivatePrice.setBackground(getResources().getDrawable(R.drawable.taxi2));
            tvPrivatePrice.setTextColor(getResources().getColor(
                    R.color.red_brown_dark));
            tvPrivatePrice.setTextSize(16);
            tvPrivatePrice.setGravity(Gravity.CENTER);
            tvPrivatePrice.setText("R" + pTour.getpTourPrice());

            tvPrivatePerson = new AppCompatTextView(getActivity());
            tvPrivatePerson.setBackground(getResources().getDrawable(R.drawable.taxi2));
            tvPrivatePerson.setTextColor(getResources().getColor(
                    R.color.red_brown_dark));
            tvPrivatePerson.setTextSize(16);
            tvPrivatePerson.setGravity(Gravity.CENTER);
            tvPrivatePerson.setText(pTour.getpTourPerson() + " Person");
            tvPrivatePerson.setLayoutParams(tvParams);

            tblRow.addView(tvPrivatePrice);
            tblRow.addView(tvPrivatePerson);
            tblFdayPrivateTours.addView(tblRow);
        }
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
