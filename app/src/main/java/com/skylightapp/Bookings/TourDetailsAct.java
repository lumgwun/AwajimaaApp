package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.FRAGMENT_FULLDAY;
import static com.skylightapp.Bookings.BookingConstant.FRAGMENT_HALFDAY;
import static com.skylightapp.Bookings.BookingConstant.FRAGMENT_TOUR_DETAILS;
import static com.skylightapp.Bookings.BookingConstant.FULL_DAY_TOUR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import java.util.ArrayList;

public class TourDetailsAct extends ActionBarBaseAct implements View.OnClickListener {
    public PrefManager pHelper;
    public ParseContent pContent;
    SharedPreferences userPreferences;
    private AppCompatTextView tvTourDesc,tvTourFullDay,tvTourHalfDay;
    private Tour tour;
    private Bundle detailBundle;
    private Bundle fullDayBundle;
    private ArrayList<PTour> pTourList;
    private ArrayList<PTour> fullDayPriceList = new ArrayList<PTour>();
    private ArrayList<PTour> halfDayPriceList = new ArrayList<PTour>();
    private PTour pTour;
    private Bundle halfDayBundle;
    public AppCompatButton btnBookTour;
    private Bundle bundle;
    private QBUser qbUser;
    private Profile profile;
    private Awajima awajima;
    Gson gson, gson1,gson3;
    String json, json1, json3,name;
    private static final String PREF_NAME = "awajima";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tour_details);
        setTitle("Tour Details");
        bundle= new Bundle();
        awajima= new Awajima();
        qbUser= new QBUser();
        profile= new Profile();
        pTour= new PTour();
        tour= new Tour();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            tour=bundle.getParcelable("Tour");

        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvTitle
                .getLayoutParams();
        params.setMargins(
                (int) getResources().getDimension(R.dimen._8sdp), 0,
                0, 0);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tvTitle.setLayoutParams(params);
        setTitle(tour.getTourName());
        pHelper = new PrefManager(this);
        pContent = new ParseContent(this);
        tvTourDesc =  findViewById(R.id.tvTourDesc);
        tvTourFullDay =  findViewById(R.id.tvTourFullDay);
        tvTourHalfDay = findViewById(R.id.tvTourHalfDay);
        btnBookTour = findViewById(R.id.btnBookTour);

        tvTourDesc.setOnClickListener(this);
        tvTourFullDay.setOnClickListener(this);
        tvTourHalfDay.setOnClickListener(this);
        btnBookTour.setOnClickListener(this);

        tvTourDesc.setSelected(true);

        detailBundle = new Bundle();
        detailBundle.putString(BookingConstant.Params.TOUR_DESC, tour.getTourDesc());
        detailBundle.putStringArrayList(BookingConstant.Params.TOUR_IMAGES,
                tour.getImgLst());

        fullDayBundle = new Bundle();
        fullDayBundle.putString(BookingConstant.Params.FULL_DAY_HIGHLIGHT,
                tour.getFullDayTour());
        fullDayBundle.putDouble(BookingConstant.Params.FULL_DAY_COMMON_PRICE,
                tour.getFullDayPrice());
        fullDayBundle.putString(BookingConstant.Params.FULL_DAY_DTIME,
                tour.getfDayDTime());
        fullDayBundle.putString(BookingConstant.Params.FULL_DAY_RTIME,
                tour.getfDayRTime());

        halfDayBundle = new Bundle();
        halfDayBundle.putString(BookingConstant.Params.HALF_DAY_HIGHLIGHT,
                tour.getHalfDayTour());
        halfDayBundle.putDouble(BookingConstant.Params.HALF_DAY_COMMON_PRICE,
                tour.getHalfDayPrice());
        halfDayBundle.putString(BookingConstant.Params.HALF_DAY_MORNING_DTIME,
                tour.getMorningDTime());
        halfDayBundle.putString(BookingConstant.Params.HALF_DAY_MORNING_RTIME,
                tour.getMorningRTime());
        halfDayBundle.putString(BookingConstant.Params.HALF_DAY_AFTER_DTIME,
                tour.getAfterDTime());
        halfDayBundle.putString(BookingConstant.Params.HALF_DAY_AFTER_RTIME,
                tour.getAfterRTime());

        // Filter Private tours list
        pTourList = tour.getpTour();
        for (int i = 0; i < pTourList.size(); i++) {
            pTour = pTourList.get(i);
            if (pTour.getpTourType() == FULL_DAY_TOUR)
                fullDayPriceList.add(pTour);
            else
                halfDayPriceList.add(pTour);
        }
        fullDayBundle.putSerializable(BookingConstant.Params.FULL_DAY_PRIVATE_PRICE,
                fullDayPriceList);
        halfDayBundle.putSerializable(BookingConstant.Params.HALF_DAY_PRIVATE_PRICE,
                halfDayPriceList);

        gotoTourDetailsFragment();
    }

    public void gotoTourDetailsFragment() {
        btnBookTour.setVisibility(View.GONE);
        TourDetailsFragment frag = new TourDetailsFragment();
        frag.setArguments(detailBundle);
        addFragment(frag, false, false, FRAGMENT_TOUR_DETAILS);
    }

    public void gotoFullDayFragment() {
        btnBookTour.setText(getResources().getString(R.string.book_full_day));
        FullDayFragment frag = new FullDayFragment();
        frag.setArguments(fullDayBundle);
        addFragment(frag, false, false, FRAGMENT_FULLDAY);
    }

    public void gotoHalfDayFragment() {
        btnBookTour.setText(getResources().getString(R.string.book_half_day));
        /*HalfDayFragment frag = new HalfDayFragment();
        frag.setArguments(halfDayBundle);
        addFragment(frag, false, false, FRAGMENT_HALFDAY);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v == tvTourDesc || v == tvTourFullDay || v == tvTourHalfDay) {
            tvTourDesc.setSelected(false);
            tvTourFullDay.setSelected(false);
            tvTourHalfDay.setSelected(false);
            v.setSelected(true);
        }
        switch (v.getId()) {
            case R.id.tvTourDesc:
                gotoTourDetailsFragment();
                break;
            case R.id.tvTourFullDay:
                gotoFullDayFragment();
                break;
            case R.id.tvTourHalfDay:
                gotoHalfDayFragment();
                break;
            case R.id.btnBookTour:
                Intent intent = new Intent(TourDetailsAct.this, TourBookingAct.class);
                intent.putExtra(BookingConstant.Params.HALF_DAY, halfDayBundle);
                intent.putExtra(BookingConstant.Params.FULL_DAY, fullDayBundle);
                intent.putExtra(BookingConstant.Params.TOUR_ID, tour.getTourId());
                intent.putExtra(BookingConstant.Params.TOUR_NAME, tour.getTourName());
                startActivity(intent);
                break;
            case R.id.btnActionNotification:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}