package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skylightapp.MarketClasses.OnboardingAdapter;
import com.skylightapp.MarketClasses.OnboardingItem;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class BizOnbordingAct extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    ViewPager2 onboardingViewPager;
    TextView skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_biz_onbording);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicator);
        skip = findViewById(R.id.skip);

        setupOnboardingItems();

        onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BizOnbordingAct.this,BizRegAct.class));
                finish();
            }
        });


    }


    private void setupOnboardingItems(){

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemTime = new OnboardingItem();
        itemTime.setTitle("Time is Money");
        itemTime.setDescription("Access Everything for your Business,here.");
        itemTime.setImage(R.drawable.onboarding1);


        OnboardingItem itemFee = new OnboardingItem();
        itemFee.setTitle("Zero Fees");
        itemFee.setDescription("Manage Your Business with our digital Business Book for Customers, Inventory, Daily Income and Expenses Book keeping");
        itemFee.setImage(R.drawable.onboarding2);


        OnboardingItem itemSafe = new OnboardingItem();
        itemSafe.setTitle("Safe And Secure");
        itemSafe.setDescription("Access the African Markets with our multi-Currency cross Border Accounts. Create Business Deals, and get Funding");
        itemSafe.setImage(R.drawable.onboarding3);

        onboardingItems.add(itemTime);
        onboardingItems.add(itemFee);
        onboardingItems.add(itemSafe);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for (int i =0 ; i < indicators.length ; i++){
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.onb_ind_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for(int i = 0 ; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.onb_ind_active));
            }
            else{

                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.onb_ind_inactive));
            }
        }

    }
}