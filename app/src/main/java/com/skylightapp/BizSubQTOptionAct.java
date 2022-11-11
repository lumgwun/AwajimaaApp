package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class BizSubQTOptionAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_sub_option);
        AlertDialog.Builder builder= new AlertDialog.Builder(BizSubQTOptionAct.this);
        builder.setTitle("Awajima Subscription Choice");
        builder.setIcon(R.drawable.awajima_logo);
        builder.setItems(new CharSequence[]{"Automatic Payment", "3 months ","6 Months","One Time, Manual Payment"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case 0:
                                Intent autoIntent = new Intent(BizSubQTOptionAct.this, PlanPaymentActivity.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                autoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                autoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(autoIntent);
                                break;
                            case 1:

                                Intent my3Intent = new Intent(BizSubQTOptionAct.this, Sub3MonthsAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                my3Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                my3Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(my3Intent);

                                break;

                            case 2:

                                Intent my6Int = new Intent(BizSubQTOptionAct.this, Sub6MonthsAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                my6Int.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                my6Int.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(my6Int);

                                break;


                            case 3:

                                Intent my1Int = new Intent(BizSubQTOptionAct.this, SubManualAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                my1Int.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                my1Int.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(my1Int);

                                break;




                        }

                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();
    }
}