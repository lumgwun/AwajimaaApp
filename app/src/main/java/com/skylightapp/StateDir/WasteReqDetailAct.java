package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.skylightapp.R;

import java.util.HashMap;

public class WasteReqDetailAct extends AppCompatActivity implements View.OnClickListener{
    final String TAG = "Pay";
    CardView payer,cancel;
    AppCompatTextView tv_credit,tv_email,tv_client,tv_frequency,tv_amount,tv_place,tv_residence,tv_phone,
            tv_link_credit,tv_days, tv_schedule,tv_todo;
    private ProgressDialog pd;
    String loginEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_waste_req_detail);
        payer = findViewById(R.id.cvPay);
        cancel = findViewById(R.id.cvCancel);
        tv_email = findViewById(R.id.Email);
        tv_client = findViewById(R.id.Client);
        tv_frequency = findViewById(R.id.Frequency);
        tv_place = findViewById(R.id.Place);
        tv_residence = findViewById(R.id.Residence);
        tv_phone = findViewById(R.id.Phone);
        tv_days = findViewById(R.id.Days);
        tv_todo = findViewById(R.id.Todo);
        tv_amount = findViewById(R.id.tv_amount);
        tv_credit = findViewById(R.id.tv_credit);
        tv_link_credit = findViewById(R.id.link_credit);
        tv_schedule = findViewById(R.id.tv_creneau);

        /*
            Getting logged in user email via shared preferences
         */
        SharedPreferences pref = getSharedPreferences("loginData",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        loginEmail = pref.getString("email",null);

        pd = new ProgressDialog(WasteReqDetailAct.this);
        pd.setMessage("Chargement ...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        String extraEmail = getIntent().getStringExtra("email");
        tv_email.setText(extraEmail);

        String extraClient = getIntent().getStringExtra("client");
        tv_client.setText(extraClient);

        String extraFrequency = getIntent().getStringExtra("frequency");
        tv_frequency.setText(extraFrequency);

        String extraPlace = getIntent().getStringExtra("place");
        tv_place.setText(extraPlace);

        String extraResidence = getIntent().getStringExtra("residence");
        tv_residence.setText(extraResidence);

        String extraPhone = getIntent().getStringExtra("phone");
        tv_phone.setText(extraPhone);

        String extraAmount = getIntent().getStringExtra("amount");
        tv_amount.setText(extraAmount);

        String extraCreneau = getIntent().getStringExtra("creneau");
        tv_schedule.setText(extraCreneau);

        String extraDays = getIntent().getStringExtra("days");
        tv_days.setText(extraDays);

        String extraTodo = getIntent().getStringExtra("todo");
        tv_todo.setText(extraTodo);

        /*
            Canceling order
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        payer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        final String orderAmount = tv_amount.getText().toString();

        HashMap<String,String> postBill = new HashMap<>();
        postBill.put("email",loginEmail);
        postBill.put("amount",orderAmount);

    }


    private void cancel(){

        HashMap<String,String> postCancel = new HashMap<>();
        postCancel.put("email",loginEmail);

    }
}