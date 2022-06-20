package com.skylightapp.Customers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.skylightapp.R;

public class NewLoanOptions extends AppCompatActivity {
    private Bundle loanBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_loan_options);
        loanBundle= new Bundle();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Loan Option");
        builder.setIcon(R.drawable.transaction);
        builder.setItems(new CharSequence[]
                        {"From Ewallet Savings", "From Standing Order Savings"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                loanBundle.putString("loanType","EWallet");
                                Intent ewalletIntent = new Intent(NewLoanOptions.this, CusLoanAct.class);
                                Toast.makeText(NewLoanOptions.this, "Borrow From the Ewallet Acct", Toast.LENGTH_SHORT).show();
                                ewalletIntent.putExtras(loanBundle);
                                startActivity(ewalletIntent);
                                break;
                            case 1:
                                loanBundle.putString("loanType","StandingOrderAcct");
                                Intent soIntent = new Intent(NewLoanOptions.this, CusLoanAct.class);
                                Toast.makeText(NewLoanOptions.this, "Borrow from Standing Acct", Toast.LENGTH_SHORT).show();
                                soIntent.putExtras(loanBundle);
                                startActivity(soIntent);

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