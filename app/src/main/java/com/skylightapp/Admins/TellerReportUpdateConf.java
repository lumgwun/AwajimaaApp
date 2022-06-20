package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.skylightapp.R;

public class TellerReportUpdateConf extends AppCompatActivity {
    Bundle bundle;
    String passCode,enteredCode;
    AppCompatEditText edtCode;
    private AppCompatButton UpdatesCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_report_con);
        edtCode = findViewById(R.id.SuperCode);
        bundle= new Bundle();
        UpdatesCodeButton = findViewById(R.id.UpdatesCodeButton);
        if(bundle !=null){
            passCode=bundle.getString("Code");
        }
    }

    public void confirmUpdateCode(View view) {
        bundle= new Bundle();
        try {

            enteredCode = edtCode.getText().toString().trim();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        if(bundle !=null){
            passCode=bundle.getString("Code");

            if(enteredCode.equalsIgnoreCase(passCode)){
                setResult(Activity.RESULT_OK);
                setResult(Activity.RESULT_OK, new Intent());

            }
        }
    }
}