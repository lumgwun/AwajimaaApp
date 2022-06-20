package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.skylightapp.R;

public class TellerSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_success);
    }
    public void goBackToHome(View view) {
        Intent usersIntent = new Intent(this, TellerHomeChoices.class);
        Toast.makeText(this, "Going back to the Teller's Dashboard", Toast.LENGTH_SHORT).show();

    }
}