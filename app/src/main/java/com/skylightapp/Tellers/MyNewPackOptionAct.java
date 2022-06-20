package com.skylightapp.Tellers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.skylightapp.AllCustNewPackAct;
import com.skylightapp.AllNewPackOptionAct;
import com.skylightapp.R;

public class MyNewPackOptionAct extends AppCompatActivity {
    String packageType;
    private AppCompatSpinner spnTypeSelection;
    Button btnContinue,btnCancel;
    String selectedType;
    private Bundle optionBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_new_pack_option);
        optionBundle=new Bundle();
        spnTypeSelection = findViewById(R.id.spnPackType22);
        optionBundle = new Bundle();
        btnContinue = findViewById(R.id.spnContinue);
        btnCancel = findViewById(R.id.btnCancelType);
        spnTypeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedType = spnTypeSelection.getSelectedItem().toString();
                selectedType = (String) parent.getSelectedItem().toString();
                Toast.makeText(MyNewPackOptionAct.this, "New Package Type: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnContinue.setOnClickListener(this::continueType44);
        btnCancel.setOnClickListener(this::cancelType44);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionBundle.putString("PackageType", packageType);
                Toast.makeText(MyNewPackOptionAct.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
                Intent savingsIntent = new Intent(MyNewPackOptionAct.this, MyCusNewPackAct.class);
                savingsIntent.putExtras(optionBundle);
                //savingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(savingsIntent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void cancelType44(View view) {
    }

    public void continueType44(View view) {
    }
}