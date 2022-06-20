package com.skylightapp;

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

public class AllNewPackOptionAct extends AppCompatActivity {
    String packageType;
    private AppCompatSpinner spnTypeSelection;
    Button btnContinue,btnCancel;
    String selectedType;
    private Bundle optionBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_new_pack_option);
        spnTypeSelection = findViewById(R.id.spnPackType);
        optionBundle = new Bundle();
        btnContinue = findViewById(R.id.continueee);
        btnCancel = findViewById(R.id.cancelTypeSelection);
        spnTypeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedType = spnTypeSelection.getSelectedItem().toString();
                selectedType = (String) parent.getSelectedItem();
                Toast.makeText(AllNewPackOptionAct.this, "New Package Type: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnContinue.setOnClickListener(this::continueType);
        btnCancel.setOnClickListener(this::cancelType);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionBundle.putString("PackageType", packageType);
                Toast.makeText(AllNewPackOptionAct.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
                Intent savingsIntent = new Intent(AllNewPackOptionAct.this, AllCustNewPackAct.class);
                savingsIntent.putExtras(optionBundle);
                savingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void cancelType(View view) {
    }

    public void continueType(View view) {
    }
}