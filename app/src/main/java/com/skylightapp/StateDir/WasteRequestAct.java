package com.skylightapp.StateDir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WasteRequestAct extends AppCompatActivity implements View.OnClickListener{
    final String TAG = "Facture";
    Context context;
    Spinner SP_client,SP_frequency, SP_Schedule,spnState;
    private CardView cvConfirm,cvDays;
    String loginEmail = "";
    AppCompatTextView Amount,link_credit,Collects,tvDays,tvDays2;
    private ProgressDialog pd;
    AppCompatEditText residence,place,phone;
    TextInputLayout inputResidence,inputPlace,inputPhone;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefUserName,userPhoneNo;
    int SharedPrefProfileID;
    private Customer customer;
    private Bundle bundle;
    String client,frequency,niche,place2,residence2,phone2,days,amount,collects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_waste_request);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        customer= new Customer();
        bundle= new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        loginEmail=userPreferences.getString("PROFILE_EMAIL", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        SP_client = findViewById(R.id.client);
        SP_frequency = findViewById(R.id.frequency);
        SP_Schedule = findViewById(R.id.schedule);
        spnState = findViewById(R.id.pickup_state);
        if(userProfile !=null){
            userPhoneNo=userProfile.getProfilePhoneNumber();
        }

        residence = findViewById(R.id.et_residence2);
        place = findViewById(R.id.et_place2);
        phone = findViewById(R.id.et_phone2);

        residence.addTextChangedListener(new WasteRequestAct.MyTextWatcher(residence));
        place.addTextChangedListener(new WasteRequestAct.MyTextWatcher(place));
        phone.addTextChangedListener(new WasteRequestAct.MyTextWatcher(phone));

        inputResidence = findViewById(R.id.inputResidence);
        inputPlace = findViewById(R.id.inputPlace);
        inputPhone = findViewById(R.id.inputPhone);

        tvDays = findViewById(R.id.tv_days);
        tvDays2 = findViewById(R.id.tvDays2);
        if(userPhoneNo !=null){
            phone.setText("userPhoneNo");

        }

        pd = new ProgressDialog(WasteRequestAct.this);
        pd.setMessage("Chargement ...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        cvConfirm = findViewById(R.id.cvConfirm);
        cvDays = findViewById(R.id.cvDays);
        Amount = findViewById(R.id.tv_amount);
        Collects = findViewById(R.id.tv_collects);
        link_credit = findViewById(R.id.link_credit);

        ArrayAdapter<String> P_adapter = new ArrayAdapter<>(WasteRequestAct.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.client));
        P_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_client.setAdapter(P_adapter);

        ArrayAdapter<String> F_adapter = new ArrayAdapter<>(WasteRequestAct.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.frequency));
        F_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_frequency.setAdapter(F_adapter);

        ArrayAdapter<String> C_adapter = new ArrayAdapter<>(WasteRequestAct.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.schedule));
        F_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_Schedule.setAdapter(C_adapter);

        cvDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Build an AlertDialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(WasteRequestAct.this);

                // String array for alert dialog multi choice items
                String[] days = new String[]{
                        "MONDAY",
                        "TUESDAY",
                        "WEDNESDAY",
                        "THURSDAY",
                        "FRIDAY",
                        "SATURDAY",
                        "SUNDAY"
                };

                // Boolean array for initial selected items
                final boolean[] checkedDays = new boolean[]{
                        true, // MONDAY
                        false, // TUESDAY
                        false, // WEDNESDAY
                        false, // THURSDAY
                        false, // FRIDAY
                        false, // SATURDAY
                        false // SUNDAY
                };

                // Convert the days array to list
                final List<String> daysList = Arrays.asList(days);

                builder.setMultiChoiceItems(days, checkedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        // Update the current focused item's checked status
                        checkedDays[i] = b;
                    }
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                builder.setTitle("Choose pick-up day(s)");

                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        tvDays2.setText("");
                        for (int j = 0; j< checkedDays.length; j++) {

                            boolean checked = checkedDays[j];

                            if (checked) {
                                tvDays2.setText(MessageFormat.format("{0}{1} - ", tvDays2.getText(), daysList.get(j)));
                            }
                        }
                    }

                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvDays2.setText(R.string.plan);
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

        cvConfirm.setOnClickListener(this);

        link_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WasteRequestAct.this, PayNowActivity.class));
            }
        });
    }
    @Override
    public void onClick(View view) {

        client = SP_client.getSelectedItem().toString();
        frequency = SP_frequency.getSelectedItem().toString();
        if(client !=null){
            if(frequency !=null){

                if (client.equalsIgnoreCase("Particular")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }
                } else if (client.equalsIgnoreCase("Fast Food")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                } else if (client.equalsIgnoreCase("Church")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                }else if (client.equalsIgnoreCase("School")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                }else if (client.equalsIgnoreCase("Market")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                }else if (client.equalsIgnoreCase("Local Waste Point")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                }else if (client.equalsIgnoreCase("Occasion")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                }else if (client.equalsIgnoreCase("Bar")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }

                }else if (client.equalsIgnoreCase("Grocery store") || client.equalsIgnoreCase("Restaurant")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }
                } else if (client.equalsIgnoreCase("Hotel")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }
                }else if (client.equalsIgnoreCase("Party")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }
                } else if (client.equalsIgnoreCase("Marriage") || client.equalsIgnoreCase("Ceremony")) {
                    if (frequency.equalsIgnoreCase("One Shot")) {
                        Amount.setText("500");
                    } else if (frequency.equalsIgnoreCase("1* par week")) {
                        Amount.setText("1000");
                    } else if (frequency.equalsIgnoreCase("2* par week")) {
                        Amount.setText("2000");
                    } else if (frequency.equalsIgnoreCase("3* par week")) {
                        Amount.setText("3000");
                    } else if (frequency.equalsIgnoreCase("4* par week")) {
                        Amount.setText("4000");
                    } else if (frequency.equalsIgnoreCase("5* par week")) {
                        Amount.setText("5000");
                    }
                }

                if (frequency.equalsIgnoreCase("One Shot")) {
                    Collects.setText("1");
                } else if (frequency.equalsIgnoreCase("1* par week")) {
                    Collects.setText("4* per month");
                } else if (frequency.equalsIgnoreCase("2* par week")) {
                    Collects.setText("8* per month");
                } else if (frequency.equalsIgnoreCase("3* par week")) {
                    Collects.setText("12* per month");
                } else if (frequency.equalsIgnoreCase("4* par week")) {
                    Collects.setText("16* per month");
                } else if (frequency.equalsIgnoreCase("5* par week")) {
                    Collects.setText("20* per month");
                }

            }

        }




        if (!emptyResidence(residence)){

            if (!emptyPlace(place)){

                if (!emptyPhone(phone)){

                    if (phoneValidate()){



                        client = SP_client.getSelectedItem().toString();
                        frequency = SP_frequency.getSelectedItem().toString();
                        niche = SP_Schedule.getSelectedItem().toString();
                         place2 = place.getText().toString();
                        residence2 = residence.getText().toString();
                         phone2 = phone.getText().toString();
                         days = tvDays2.getText().toString();
                         amount = Amount.getText().toString();
                         collects = Collects.getText().toString();

                        HashMap<String, String> postData = new HashMap<>();

                        postData.put("email", loginEmail);
                        postData.put("client", client);
                        postData.put("frequency", frequency);
                        postData.put("place", place2);
                        postData.put("residence", residence2);
                        postData.put("phone", phone2);
                        postData.put("amount", amount);
                        postData.put("Niche", niche);
                        postData.put("days", days);
                        postData.put("todo", collects);
                        bundle.putParcelable("Profile",userProfile);
                        bundle.putString("client",client);
                        bundle.putString("frequency",frequency);
                        bundle.putString("place2",place2);
                        bundle.putString("residence2",residence2);
                        bundle.putString("phone2",phone2);
                        bundle.putString("collects",collects);
                        bundle.putString("amount",amount);
                        bundle.putString("days",days);

                        Intent intent = new Intent(WasteRequestAct.this, WasteReqDetailAct.class);
                        intent.putExtra("email", loginEmail);
                        intent.putExtra("client", client);
                        intent.putExtra("frequency", frequency);
                        intent.putExtra("place", place2);
                        intent.putExtra("residence", residence2);
                        intent.putExtra("phone", phone2);
                        intent.putExtra("amount", amount);
                        intent.putExtra("schedule", niche);
                        intent.putExtra("days", days);
                        intent.putExtra("todo", collects);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        /*PostResponseAsyncTask task1 = new PostResponseAsyncTask(this,
                                postData, new AsyncResponse() {
                            @Override
                            public void processFinish(String output) {

                                Log.d(TAG, output);
                                if (output.contains("ErrorInsert")) {
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    Toast.makeText(WasteRequestAct.this,
                                            "Try again",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(WasteRequestAct.this, WasteReqDetailAct.class);
                                    intent.putExtra("email", loginEmail);
                                    intent.putExtra("client", client);
                                    intent.putExtra("frequency", frequency);
                                    intent.putExtra("place", place2);
                                    intent.putExtra("residence", residence2);
                                    intent.putExtra("phone", phone2);
                                    intent.putExtra("amount", amount);
                                    intent.putExtra("schedule", niche);
                                    intent.putExtra("days", days);
                                    intent.putExtra("todo", collects);
                                    startActivity(intent);
                                }
                                WasteRequestAct.this.finish();
                            }
                        });
                        task1.execute("http://www.wasteappsolutions.com/WasteFacture.php");*/
                    }//phone Validate
                }else{// empty Phone
                    inputPhone.setError(getString(R.string.err_msg_phone));
                    requestFocus(inputPhone);
                }
            }else{// empty place
                inputPlace.setError(getString(R.string.err_msg_place));
                requestFocus(inputPlace);
            }
        }else{// empty residence
            inputResidence.setError(getString(R.string.err_msg_residence));
            requestFocus(inputResidence);
        }
    }
    private boolean emptyResidence(EditText residence) {

        String Residence = residence.getText().toString();
        return (Residence.isEmpty());
    }

    private boolean emptyPlace(EditText place) {

        String Place = place.getText().toString();
        return (Place.isEmpty());
    }


    private boolean emptyPhone(EditText phone) {

        String Phone = phone.getText().toString();
        return (Phone.isEmpty());
    }

    private boolean phoneValidate(){

        final int length = phone.length();

        if (length < 11){
            inputPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(inputPhone);
            return false;
        }
        return true;
    }

    // validate Residence
    private boolean validateResidence() {
        if (residence.getText().toString().trim().isEmpty()) {
            inputResidence.setError(getString(R.string.err_msg_residence));
            requestFocus(inputResidence);
            return false;

        } else {
            inputResidence.setErrorEnabled(false);
        }
        return true;
    }

    // validate Place
    private boolean validatePlace() {
        if (place.getText().toString().isEmpty()) {

            inputPlace.setError(getString(R.string.error));
            requestFocus(inputPlace);
            return false;

        } else {
            inputPlace.setErrorEnabled(false);
        }
        return true;
    }

    // validate Phone
    private boolean validatePhone() {
        if (phone.getText().toString().isEmpty()) {

            final int length = phone.length();

            if (length < 8){
                inputPhone.setError(getString(R.string.err_msg_phone));
                requestFocus(inputPhone);
            }
            return false;

        } else {
            inputPhone.setErrorEnabled(false);
        }
        return true;
    }

    // Request Focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //My Text watcher
    class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.et_phone2:
                    validatePhone();
                    break;
                case R.id.et_residence2:
                    validateResidence();
                    break;
                case R.id.et_place2:
                    validatePlace();
                    break;
            }
        }
    }
}