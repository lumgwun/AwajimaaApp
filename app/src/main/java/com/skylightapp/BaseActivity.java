package com.skylightapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.skylightapp.Admins.AdminHomeChoices;
import com.skylightapp.Classes.BasePresenter;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Enums.ProfileStatus;
import com.skylightapp.Enums.UserType;
import com.skylightapp.Interfaces.BaseView;
import com.skylightapp.Interfaces.LoginView;
import com.skylightapp.Tellers.TellerHomeChoices;

public class BaseActivity <L extends BaseView, L1 extends BasePresenter<LoginView>> extends AppCompatActivity {
    public static final String BASE_ACTIVE_ID_EXTRA_KEY = "BaseActivity.BASE_ACTIVE_ID_EXTRA_KEY";



    public ProgressDialog progressDialog;
    public ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base);
    }
    public void doAuthorization(ProfileStatus status, UserType userType) {
        if (status.equals(ProfileStatus.NOT_AUTHORIZED)) {
            startLoginSignUpActivity();
        }
        if (status.equals(ProfileStatus.AUTHORIZED) || userType.equals(UserType.ADMIN)) {
            startAdminActivity();
        }
        if (status.equals(ProfileStatus.AUTHORIZED) || userType.equals(UserType.CUSTOMER)) {
            startCustomerActivity();
        }
        if (status.equals(ProfileStatus.AUTHORIZED) || userType.equals(UserType.CUSTOMER_MANAGER)) {
            startTellerActivity();
        }
        if (status.equals(ProfileStatus.NOT_AUTHORIZED) || userType.equals(UserType.ADMIN)) {
            startLoginSignUpActivity_2();
        }
        if (status.equals(ProfileStatus.AUTHORIZED) || status.equals(ProfileStatus.NO_PROFILE)|| userType.equals(UserType.ADMIN)) {
            startSpecialSignUpActivity();
        }
    }
    private void startSpecialSignUpActivity() {
        Intent intent = new Intent(this, UpdateProfileAct.class);
        startActivity(intent);
    }
    private void startLoginSignUpActivity() {
        Intent intent = new Intent(this, SignTabMainActivity.class);
        startActivity(intent);
    }
    private void startLoginSignUpActivity_2() {
        Intent intent = new Intent(this, SignTabMainActivity.class);
        startActivity(intent);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void startCustomerActivity() {
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        startActivity(intent);
    }
    private void startTellerActivity() {
        Intent intent = new Intent(this, TellerHomeChoices.class);
        startActivity(intent);
    }
    private void startAdminActivity() {
        Intent intent = new Intent(this, AdminHomeChoices.class);
        startActivity(intent);
    }

    public void showProgress() {
        showProgress(R.string.loading1);
    }

    public void showProgress(int message) {
        hideProgress();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(message));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showSnackBar(int messageId) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                messageId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showSnackBar(View view, int messageId) {
        Snackbar snackbar = Snackbar.make(view, messageId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

   /* public void showWarningDialog(int messageId) {
        AlertDialog.Builder builder = new  AlertDialog().Builder(this);
        builder.setMessage(messageId);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }

    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog().Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }*/

    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            Toast.makeText(this, "Internet connection is poor", Toast.LENGTH_SHORT).show();

            //showWarningDialog(R.string.internet_connection_failed);
        }

        return hasInternetConnection;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}