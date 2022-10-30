package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.channguyen.rsv.RangeSliderView;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class GSavingsMatchAct extends AppCompatActivity {
    private SeekBar SliderQ1;
    private float range;
    private SeekBar SliderQ2;
    SharedPreferences.Editor editor;
    private SharedPreferences userPreferences;
    private Profile userProfile;
    private Gson gson, gson1;
    private String json, json1, SharedPrefSuperUser;
    private static final String PREF_NAME = "awajima";
    private AppCompatSeekBar maxDurationSeeker, minDurSeek;
    private RangeSlider amtRangeSeekbar;
    private  long minDurationValue,maxMatchValue,minAmount,maxAmount, maxDuration;
    private TextView txtCountProgress,txtAmtProgress,txtMatchProgress;
    private CardView btnSavePref;


    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double barProgress = seekBar.getProgress();
            float max = (float) seekBar.getMax();
            float h = 15 + (float) ((max / range) * barProgress);
            float s = 100;
            float v = 90;
            String hexColor = hsvToRgb(h, s, v);
            //String hexColor = String.format("#%06X", (0xFFFFFF & color));
            seekBar.getProgressDrawable().setColorFilter(Color.parseColor(hexColor), PorterDuff.Mode.SRC_IN);
            seekBar.getThumb().setColorFilter(Color.parseColor(hexColor), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gsavings_match);
        range = 1000000;
        btnSavePref = findViewById(R.id.save_pref_cardview);
        txtAmtProgress = findViewById(R.id.amt_progress);
        amtRangeSeekbar = findViewById(R.id.settings_amt_range_seekbar);
        txtCountProgress = findViewById(R.id.months_count_);
        maxDurationSeeker = findViewById(R.id.max_duration_seekbar);
        txtMatchProgress = findViewById(R.id.match_progressT);
        minDurSeek = findViewById(R.id.min_duration_seekbar);
        gson = new Gson();
        gson1 = new Gson();
        userProfile= new Profile();

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        minDurSeek.setOnSeekBarChangeListener(seekBarChangeListener);
        final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {

            }
        };
        amtRangeSeekbar.setStepSize(100);

        amtRangeSeekbar.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull @NotNull RangeSlider slider) {
                minAmount = slider.getLeft();
                maxAmount=slider.getRight();
                if(slider !=null){
                    txtAmtProgress.setText("Progress"+slider.getValueFrom());

                }

            }

            @Override
            public void onStopTrackingTouch(@NonNull @NotNull RangeSlider slider) {

                if(slider !=null){
                    minAmount = slider.getLeft();
                    maxAmount=slider.getRight();
                    SharedPreferences.Editor editor=userPreferences.edit();
                    editor.putLong("minMatchAmount",minAmount);
                    editor.putLong("maxMatchAmount",maxAmount).apply();

                }

            }
        });
        maxDurationSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                maxDuration = (int) Math.ceil(progress / 1000f);
                txtCountProgress.setText("Selection"+progress);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar !=null){
                    maxDuration =seekBar.getMax();
                    SharedPreferences.Editor editor=userPreferences.edit();
                    editor.putLong("maxDuration", maxDuration).apply();
                    txtCountProgress.setText("Selection"+ maxDuration);

                }

            }
        });

        minDurSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                //matchValueTextView.setVisibility(View.VISIBLE);
                //minMatchValue = (int) Math.ceil(progress / 1000f);
                minDurationValue = progress;

                if(progress >0){
                    SharedPreferences.Editor editor=userPreferences.edit();
                    editor.putLong("minDuration",minDurationValue).apply();
                    txtMatchProgress.setText("Min Duration"+minDurationValue);

                }


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //minMatchValue=seekBar.getMax();
                if(seekBar !=null){
                    SharedPreferences.Editor editor=userPreferences.edit();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        editor.putLong("minDuration",seekBar.getMin()).apply();
                    }
                    txtMatchProgress.setText(MessageFormat.format("Progress{0}", seekBar.getProgress()));

                }
                if (seekBar != null) {
                    txtMatchProgress.setText(MessageFormat.format("Progress{0}", seekBar.getProgress()));
                }

            }
        });
        btnSavePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GSavingsMatchAct.this, GroupSavingsAcctList.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        });
        btnSavePref.setOnClickListener(this::SaveMatchPref);



    }


    public static String hsvToRgb(float H, float S, float V) {

        float R, G, B;
        H /= 360f;
        S /= 100f;
        V /= 100f;

        if (S == 0) {
            R = V * 255;
            G = V * 255;
            B = V * 255;
        } else {
            float var_h = H * 6;
            if (var_h == 6)
                var_h = 0; // H must be < 1
            int var_i = (int) Math.floor((double) var_h); // Or ... var_i =
            // floor( var_h )
            float var_1 = V * (1 - S);
            float var_2 = V * (1 - S * (var_h - var_i));
            float var_3 = V * (1 - S * (1 - (var_h - var_i)));

            float var_r;
            float var_g;
            float var_b;
            if (var_i == 0) {
                var_r = V;
                var_g = var_3;
                var_b = var_1;
            } else if (var_i == 1) {
                var_r = var_2;
                var_g = V;
                var_b = var_1;
            } else if (var_i == 2) {
                var_r = var_1;
                var_g = V;
                var_b = var_3;
            } else if (var_i == 3) {
                var_r = var_1;
                var_g = var_2;
                var_b = V;
            } else if (var_i == 4) {
                var_r = var_3;
                var_g = var_1;
                var_b = V;
            } else {
                var_r = V;
                var_g = var_1;
                var_b = var_2;
            }

            R = var_r * 255;
            G = var_g * 255;
            B = var_b * 255;
        }
        String rs = Integer.toHexString((int) (R));
        String gs = Integer.toHexString((int) (G));
        String bs = Integer.toHexString((int) (B));

        if (rs.length() == 1)
            rs = "0" + rs;
        if (gs.length() == 1)
            gs = "0" + gs;
        if (bs.length() == 1)
            bs = "0" + bs;
        return "#" + rs + gs + bs;
    }


    public void SaveMatchPref(View view) {
    }
}