package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.R;

import java.util.Date;

public class UserVerifnAct extends AppCompatActivity {
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;
    private Profile profile;
    private DBHelper dbHelper;
    String currentDate;
    private TimeLineClassDAO timeLineClassDAO;
    private Animation translater, translER;
    private Date date;
    private AppCompatButton proceed_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_verifn);
        gson = new Gson();
        profile= new Profile();
        timeLineClassDAO= new TimeLineClassDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
    }
}