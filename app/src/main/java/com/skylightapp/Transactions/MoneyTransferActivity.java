package com.skylightapp.Transactions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;


public class MoneyTransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Skylight Money Transfer");
        builder.setItems(new CharSequence[]
                        {"To Bank", "To Mobile Money","Back Home"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(MoneyTransferActivity.this, "Bank transfer option selected", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MoneyTransferActivity.this, SkylightBTransferAct.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(MoneyTransferActivity.this, "Mobile Money Option selected", Toast.LENGTH_SHORT).show();
                                Intent intentDeposit = new Intent(MoneyTransferActivity.this, MobileMoneyTransfer.class);
                                intentDeposit.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentDeposit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentDeposit.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentDeposit);
                                break;
                            case 2:
                                Toast.makeText(MoneyTransferActivity.this, "Home option selected", Toast.LENGTH_SHORT).show();
                                Intent intentHomeWards = new Intent(MoneyTransferActivity.this, LoginDirectorActivity.class);
                                intentHomeWards.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentHomeWards.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentHomeWards.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentHomeWards);
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
                        finish();
                        /*Intent intent = new Intent(getApplicationContext(), LoginDirectorActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/

                    }
                });
        builder.create().show();

    }
}