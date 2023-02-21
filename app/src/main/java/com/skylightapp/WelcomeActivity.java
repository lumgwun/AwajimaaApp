package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.SliderData;
import com.skylightapp.Classes.WelcomeSliderAd;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private AppCompatTextView tv2_We;
    private Button btnSkip, btnNext,buttonSwitchNext;
    private PrefManager prefManager;
    private AppCompatTextView txtWel2;
    private ArrayList<SliderData> sliderDataArrayList ;
    private WelcomeSliderAd welcomeSliderAd;
    private SliderView sliderView;
    private boolean isDoArrayGet=false;
    SliderData sliderBus,sliderJet,sliderBoat,sliderTour,sliderTrain,sliderTaxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        setTitle("Awajima Reception Area");
        sliderDataArrayList = new ArrayList<SliderData>();
        final int[] currentIndex = {-1};
        sliderView = findViewById(R.id.slider_Welcome);
        sliderBus= new SliderData();
        sliderJet= new SliderData();
        sliderBoat= new SliderData();
        sliderTour= new SliderData();
        sliderTrain= new SliderData();
        sliderTaxi= new SliderData();

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        sliderBus.setTittle("Transport Companies");
        sliderBus.setImgUrl(R.drawable.ifesinachi);
        sliderJet.setTittle("Jet Charter");
        sliderJet.setImgUrl(R.drawable.jet_private);
        sliderBoat.setTittle("Boat Booking");
        sliderBoat.setImgUrl(R.drawable.boat33);
        sliderTour.setTittle("Tour Bookings");
        sliderTour.setImgUrl(R.drawable.dubai_tour);
        sliderTrain.setTittle("Train Booking");
        sliderTrain.setImgUrl(R.drawable.train);
        sliderTaxi.setTittle("Taxi Booking");
        sliderTaxi.setImgUrl(R.drawable.taxi2);


        getGoogleVersionNo();
        sliderDataArrayList.add(sliderBus);
        sliderDataArrayList.add(sliderJet);
        sliderDataArrayList.add(sliderBoat);
        sliderDataArrayList.add(sliderTour);
        sliderDataArrayList.add(sliderTrain);
        sliderDataArrayList.add(sliderTaxi);


        //Animation bounce = AnimationUtils.loadAnimation(this, android.R.anim.bounce_interpolator);
        try {
            if(isDoArrayGet){
                welcomeSliderAd = new WelcomeSliderAd(this, sliderDataArrayList);

                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
                sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION);
                sliderView.setSliderAdapter(welcomeSliderAd);

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        txtWel2 = findViewById(R.id.tv2_Welcome);
        //tv2_We = findViewById(R.id.tv2_We);


        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        btnNext.setAnimation(in);
        btnSkip.setAnimation(out);
        Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
        try {

            txtWel2.startAnimation(marquee);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //textSwitcher2.setCurrentText("click on next button to switch text");

        /*btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                currentIndex[0]++;
                if (currentIndex[0] == messageCount)
                    currentIndex[0] = 0;
                textSwitcher2.setText(strings[currentIndex[0]]); // set Text in TextSwitcher
            }
        });*/


        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2
                //R.layout.welcome_slider3
                //R.layout.welcome_slider4

        };

        addBottomDots(0);

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page if true launch MainActivity
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void getGoogleVersionNo() {
       /* tv2_We = findViewById(R.id.tv2_We);
        Resources res = getResources();
        int gpsVersion = res.getInteger(R.integer.google_play_services_version);
        if(gpsVersion>0){
            tv2_We.setText("Core Version No:"+gpsVersion);
        }*/
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml(".."));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        //prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, ConfirmationActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText("Go!");
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    // Making notification bar transparent

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}