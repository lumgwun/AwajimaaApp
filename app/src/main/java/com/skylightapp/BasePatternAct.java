package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import me.zhanghai.android.patternlock.PatternView;

public class BasePatternAct extends AppCompatActivity {
    private static final int CLEAR_PATTERN_DELAY_MILLI = 2000;

    protected AppCompatTextView mMessageText;
    protected PatternView mPatternView;
    protected LinearLayout mButtonContainer;
    protected Button mLeftButton;
    protected Button mRightButton;

    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            mPatternView.clearPattern();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base_pattern);
        mMessageText = (AppCompatTextView) findViewById(R.id.pl_message_text);
        mPatternView = (PatternView) findViewById(R.id.pl_pattern);
        mButtonContainer = (LinearLayout) findViewById(R.id.pl_button_container);
        mLeftButton = (Button) findViewById(R.id.pl_left_button);
        mRightButton = (Button) findViewById(R.id.pl_right_button);
    }

    protected void removeClearPatternRunnable() {
        mPatternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        mPatternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);

    }
}