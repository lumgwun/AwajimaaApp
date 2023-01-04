package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SecureAppAct extends AppCompatActivity {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppLockAct.REQUEST_CODE) {
            try {
                AppLockAct.handleLockResponse(this, resultCode);
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            AppLockAct.protectWithLock(this, true);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            AppLockAct.protectWithLock(this, false);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

    }
}