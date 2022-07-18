package com.skylightapp.Admins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skylightapp.Adapters.AdminCusAdapter;
import com.skylightapp.BlockedUserAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.DeleteUserAct;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_PHONE_NUMBER;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class AdminCusActionAct extends AppCompatActivity {
    Bundle getIntentBundle,userBundle;
    Customer customer;
    long customerID;
    TextView txtCus;
    String names;
    AdminCusAdapter adminCusAdapter;
    ArrayAdapter<Customer> customerArrayAdapter;
    ArrayList<Customer> customers=new ArrayList<>();
    SharedPreferences sharedpreferences;
    Gson gson;
    String json,CUSTOMER_FIRST_NAME,CUSTOMER_SURNAME,USER_PHONE,CUSTOMER_EMAIL_ADDRESS;
    DBHelper dbHelper;
    LatLng CUSTOMER_LATLONG;
    private  Profile userProfile,customerProfile,theProfile;
    private static final String PREF_NAME = "skylight";
    String packages,loans,savings,transactions,so,savingsCode,doc,messages,grpSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_cus_action);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = sharedpreferences.getString("Machine", "");
        gson = new Gson();
        userBundle=new Bundle();
        userProfile= new Profile();
        customerProfile= new Profile();
        theProfile= new Profile();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        getIntentBundle=getIntent().getExtras();


        if(getIntentBundle !=null){
            customer=getIntentBundle.getParcelable("Customer");
            theProfile=getIntentBundle.getParcelable("Profile");
            customerID=customer.getCusUID();
            CUSTOMER_FIRST_NAME=customer.getCusFirstName();
            CUSTOMER_SURNAME=customer.getCusSurname();
            CUSTOMER_EMAIL_ADDRESS=customer.getCusEmailAddress();
            USER_PHONE=customer.getCusPhoneNumber();
            CUSTOMER_LATLONG=customer.getCusLocation();
            names=customer.getCusFirstName()+""+customer.getCusSurname();
            txtCus = findViewById(R.id.nameCus);
            txtCus.setText(MessageFormat.format("Cus:{0}{1}", names, customerID));

        }
        if(theProfile !=null){
            userBundle.putLong("PROFILE_ID",theProfile.getPID());
            userBundle.putLong(PROFILE_ID,theProfile.getPID());
            userBundle.putString("Machine",machine);
            userBundle.putParcelable("Profile", theProfile);
            userBundle.putString("Machine",machine);
            userBundle.putString("CUSTOMER_ID",CUSTOMER_ID);
            userBundle.putString(CUSTOMER_ID,CUSTOMER_ID);
            userBundle.putString("CUSTOMER_FIRST_NAME",CUSTOMER_FIRST_NAME);
            userBundle.putString(CUSTOMER_FIRST_NAME,CUSTOMER_FIRST_NAME);
            userBundle.putString("CUSTOMER_SURNAME",CUSTOMER_SURNAME);
            userBundle.putString(CUSTOMER_SURNAME,CUSTOMER_SURNAME);
            userBundle.putString("CUSTOMER_EMAIL_ADDRESS",CUSTOMER_EMAIL_ADDRESS);
            userBundle.putString(CUSTOMER_EMAIL_ADDRESS,CUSTOMER_EMAIL_ADDRESS);
            userBundle.putParcelable(String.valueOf(CUSTOMER_LATLONG),CUSTOMER_LATLONG);
            userBundle.putString("CUSTOMER_PHONE_NUMBER",USER_PHONE);
            userBundle.putString(CUSTOMER_PHONE_NUMBER,USER_PHONE);
            userBundle.putParcelable("PreviousLocation",CUSTOMER_LATLONG);
            userBundle.putParcelable("Customer",customer);
            userBundle.putParcelable("Profile",theProfile);
            userBundle.putParcelable("CUSTOMER_LATLONG",CUSTOMER_LATLONG);

        }



        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCusActionAct.this);
        builder.setTitle("Choose Action on Customer");
        builder.setItems(new CharSequence[]
                        {"Delete Customer", "Update Profile","Loans","Packages","Savings","Standing Orders","Transactions","Savings Codes","Payment Docs"," Location","Messages"," Send Customer message"," Block User"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(AdminCusActionAct.this, "delete option, selected ", Toast.LENGTH_SHORT).show();
                                deleteCustomer(userBundle);
                                break;
                            case 1:
                                Toast.makeText(AdminCusActionAct.this, "update customer, selected", Toast.LENGTH_SHORT).show();
                                setContentView(R.layout.update_customer);
                                break;
                            case 2:
                                Toast.makeText(AdminCusActionAct.this, "Check Loan Overview", Toast.LENGTH_SHORT).show();
                                loans(userBundle);
                                break;
                            case 3:
                                Toast.makeText(AdminCusActionAct.this, "Check package overview", Toast.LENGTH_SHORT).show();
                                doPackage(userBundle);
                                break;
                            case 4:
                                Toast.makeText(AdminCusActionAct.this, "Savings option, selected ", Toast.LENGTH_SHORT).show();
                                savings(userBundle);
                                break;
                            case 5:
                                Toast.makeText(AdminCusActionAct.this, "View standing orders option, selected ", Toast.LENGTH_SHORT).show();
                                so(userBundle);
                                break;
                            case 6:
                                Toast.makeText(AdminCusActionAct.this, "View transaction option, selected ", Toast.LENGTH_SHORT).show();
                                tx(userBundle);
                                break;
                            case 7:
                                Toast.makeText(AdminCusActionAct.this, "View savings codes option, selected ", Toast.LENGTH_SHORT).show();
                                savingsCode(userBundle);
                                break;
                            case 8:
                                Toast.makeText(AdminCusActionAct.this, "View payment documents option, selected ", Toast.LENGTH_SHORT).show();
                                doc(userBundle);
                                break;

                            case 9:
                                Toast.makeText(AdminCusActionAct.this, "View Customer's Loc.", Toast.LENGTH_SHORT).show();
                                location(userBundle);
                                break;
                            case 10:
                                Toast.makeText(AdminCusActionAct.this, "Customer messages, selected ", Toast.LENGTH_SHORT).show();
                                messages(userBundle);
                                break;


                            case 11:
                                Toast.makeText(AdminCusActionAct.this, "Send a message to the Customer", Toast.LENGTH_SHORT).show();
                                sendMessage(userBundle);
                                break;
                            case 12:
                                Toast.makeText(AdminCusActionAct.this, "Block User", Toast.LENGTH_SHORT).show();
                                blockUser(userBundle);
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
    public void doPackage(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    public void  location(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, TrackWorkersAct.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private  void deleteCustomer(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, DeleteUserAct.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }

    private  void loans(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private  void savings(Bundle userBundle){

        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private  void so(Bundle userBundle){

        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private  void tx(Bundle userBundle){

        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private  void savingsCode(Bundle userBundle){

        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private  void doc(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }

    private  void messages(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, AdminCusActionView.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }

    private  void sendMessage(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, SendUserMessageAct.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);


    }
    private  void blockUser(Bundle userBundle){
        Intent userIntent = new Intent(AdminCusActionAct.this, BlockedUserAct.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
}