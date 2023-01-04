package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SubHistoryAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sub_history);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }


    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }
}