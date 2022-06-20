package com.skylightapp.Admins;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.UserLocatorAct;
import com.skylightapp.PackageDetailsActivity;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Objects;

public class PackageUpdateAct extends AppCompatActivity {
    Bundle bundle;
    String packageName,startDate,newStartDate,endDate,manager,surName,userNames,status,firstName,type,email,address,customerOfficeBranch,phoneNumber;
    int packageId;
    double paymentAmount,totalAmount,savedAmount,remAmount;
    int duration;
    LatLng location;
    DBHelper dbHelper;
    double aDouble;
    LinearLayoutCompat updateLayout;
    TextViewCompat txtpackageCount;
    AppCompatButton btnSubmit;
    AppCompatEditText edtEndDate,edtBalance;
    Spinner spnStatus;
    AppCompatTextView txtName,txtType,txtPackID;
    String finalStatus,finalEndDate;
    DatePickerDialog picker;
    SkyLightPackage skyLightPackage;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    int customerID;
    ActivityResultLauncher<Intent> locStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        finish();
                    }
                }
            });

    ActivityResultLauncher<Intent> packDetailsStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        finish();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_package_update);

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        txtName = findViewById(R.id.updateName);
        txtPackID = findViewById(R.id.updateId2);
        txtType = findViewById(R.id.updateType);
        spnStatus = findViewById(R.id.updateStatus);
        edtEndDate = findViewById(R.id.updateEndDate);
        edtBalance = findViewById(R.id.updateBalance);
        btnSubmit = findViewById(R.id.recyclerViewCustomSO);
        skyLightPackage= new SkyLightPackage();
        bundle=getIntent().getExtras();
        dbHelper=new DBHelper(this);
        if(bundle !=null){
            location= (LatLng) bundle.getParcelable(String.valueOf(location));
            skyLightPackage=bundle.getParcelable("SkyLightPackage");
            duration=bundle.getInt(String.valueOf(duration));
            remAmount=bundle.getDouble(String.valueOf(remAmount));
            savedAmount=bundle.getDouble(String.valueOf(savedAmount));
            totalAmount=bundle.getDouble(String.valueOf(totalAmount));
            paymentAmount=bundle.getDouble(String.valueOf(paymentAmount));
            packageId=bundle.getInt(String.valueOf(packageId));
            packageName=bundle.getString(packageName);
            startDate=bundle.getString(startDate);
            endDate=bundle.getString(endDate);
            manager=bundle.getString(manager);
            surName=bundle.getString(surName);
            userNames=bundle.getString(userNames);
            status=bundle.getString(status);
            firstName=bundle.getString(firstName);
            type=bundle.getString(type);
            email=bundle.getString(email);
            phoneNumber=bundle.getString(phoneNumber);
            customerOfficeBranch=bundle.getString(customerOfficeBranch);
            address=bundle.getString(address);
            LayoutInflater li = LayoutInflater.from(this);
            updateLayout = (LinearLayoutCompat)li.inflate(R.layout.package_update, null);

            edtEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH +1);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(PackageUpdateAct.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    edtEndDate.setText(MessageFormat.format("{0}/{1}/{2}", dayOfMonth, monthOfYear + 1, year));


                                }
                            }, year, month, day);
                    picker.show();
                }
            });
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalStatus=spnStatus.getSelectedItem().toString().trim();
                    newStartDate= Objects.requireNonNull(edtEndDate.getText()).toString();
                    aDouble=Double.parseDouble(Objects.requireNonNull(edtBalance.getText()).toString());
                    dbHelper.updatePackage(customerID,packageId,aDouble,finalStatus);


                }
            });


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Package Type");
            builder.setIcon(R.drawable.ic_icon2);
            builder.setItems(new CharSequence[]
                            {"Update Package Status", "Delete Package","Check Package Count","Package Details","Package Location"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Toast.makeText(PackageUpdateAct.this, "You have selected to update this"+type+""+"Package", Toast.LENGTH_SHORT).show();
                                    builder.setView(updateLayout);
                                    break;
                                case 1:

                                    Toast.makeText(PackageUpdateAct.this, "You have selected Delete", Toast.LENGTH_SHORT).show();
                                    deletePackage();

                                    break;


                                case 2:
                                    Toast.makeText(PackageUpdateAct.this, " Package Count, selected", Toast.LENGTH_SHORT).show();
                                    dbHelper.getSavingsCount(packageId);
                                    break;
                                case 3:

                                    Toast.makeText(PackageUpdateAct.this, "package Details would be shown now", Toast.LENGTH_SHORT).show();
                                    packDetailsStartForResult.launch(new Intent(PackageUpdateAct.this, PackageDetailsActivity.class));
                                    //Intent packageIntent = new Intent(PackageUpdateAct.this, PackageDetailsActivity.class);
                                    break;
                                case 4:
                                    Toast.makeText(PackageUpdateAct.this, "Package Location opening in map, now", Toast.LENGTH_SHORT).show();
                                    locStartForResult.launch(new Intent(PackageUpdateAct.this, UserLocatorAct.class));
                                    //Intent locationIntent = new Intent(PackageUpdateAct.this, PackLocAct.class);
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
        if(skyLightPackage !=null){
            packageId=skyLightPackage.getPackageId();
            customerID=skyLightPackage.getCustomer().getCusUID();
        }
    }
    public void deletePackage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure that you want to delete?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setItems(new CharSequence[]
                        {"Yes", "No"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dbHelper.deletePackage(packageId);
                                break;
                            case 1:
                                finish();
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