package com.skylightapp.Transactions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.skylightapp.R;


public class OurDepositMoneyAct extends AppCompatActivity {
    public final static String DEPOSIT_ID = "KEY_EXTRA_DEPOSIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_our_deposit_money);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Our Coop. App Deposit Money Options");
        builder.setItems(new CharSequence[]
                        {"Through Direct Bank Payment", "Through Card","Through Bank Transfer","Through Mobile Money","Through UK Bank","Through Google Pay","Through QuickTeller"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "Bank transfer option selected", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SkylightBTransferAct.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Card Option selected", Toast.LENGTH_SHORT).show();
                                Intent intentCard = new Intent(getApplicationContext(), CardDepositActivity.class);
                                intentCard.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentCard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentCard);
                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
