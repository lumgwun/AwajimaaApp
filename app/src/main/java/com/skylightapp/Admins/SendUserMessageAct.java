package com.skylightapp.Admins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;
import com.twilio.Twilio;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class SendUserMessageAct extends AppCompatActivity {
    Bundle bundle,userBundle;
    int iD;
    int userProfID;
    String cusPhoneNo,firstName,surName,cusEmail,customerFirstName,cusSurname,cusLoc,profilePhone,email,location;
    AppCompatButton btnSend;
    AppCompatSpinner spnSendAdmin,spnSendTeller,spnSendCustomer;
    AppCompatTextView texName,txtCusID, txtProfileID,txtPhoneNo;
    LinearLayoutCompat layoutCus,layoutTeller,layoutAdmin,layoutOffice;
    DBHelper dbHelper;
    ArrayList<Customer>customers;
    private ArrayAdapter<Customer> customersAdapter;
    ArrayList<String>profiles;
    SharedPreferences userPref;
    PreferenceManager preferenceManager;
    boolean isInvisible;
    String messageDetails,name;
    AppCompatEditText editMessage;
    com.twilio.rest.api.v2010.account.Message message;
    private static boolean isPersistenceEnabled = false;
    int selectedCustomerIndex;
    private Customer customer;
    SharedPreferences sharedpreferences;
    Gson gson0,gson,gson1,gson2;
    String json0,json,json1,json2;
    private Calendar calendar;
    SecureRandom random;
    int messageID;
    long profileID;
    String machine,userName,officeBranch,sender,sendee,selectedOffice;
    Profile userProfile;
    AdminUser adminUser;
    UserSuperAdmin superAdmin;
    CustomerManager customerManager;
    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayList<Customer> customersN;
    private ArrayList<AdminUser> adminUsers;
    private ArrayList<CustomerManager> customerManagerArrayList;
    private ArrayAdapter<CustomerManager> customerManagerArrayAdapter;
    private ArrayAdapter<AdminUser> adminUserArrayAdapter;
    private ArrayAdapter<UserSuperAdmin> userSuperAdminArrayAdapter;
    AppCompatSpinner spnOfficeBranch;
    int selectedTellerIndex,selectedAdminIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_user_message);
        layoutCus = findViewById(R.id.layoutAllCus);
        layoutTeller = findViewById(R.id.layoutAllTeller);
        layoutAdmin = findViewById(R.id.layoutAllAdmin);
        layoutOffice = findViewById(R.id.layoutOffice);
        spnOfficeBranch = findViewById(R.id.SendeeOfficeBranch);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            userProfID = bundle.getInt("PROFILE_ID",0);
            firstName = bundle.getString("USER_FIRSTNAME","");
            surName = bundle.getString("USER_SURNAME","");
            profilePhone = bundle.getString("USER_PHONE","");
            email = bundle.getString("USER_EMAIL","");
            location = bundle.getString("USER_LOCATION","");
            iD = bundle.getInt("CUSTOMER_ID",0);
            customerFirstName = bundle.getString("CUSTOMER_FIRST_NAME","");
            cusSurname = bundle.getString("CUSTOMER_SURNAME","");
            cusEmail = bundle.getString("CUSTOMER_EMAIL_ADDRESS","");
            cusPhoneNo = bundle.getString("USER_PHONE","");
            cusLoc = bundle.getString("CUSTOMER_LATLONG","");
            name=surName+","+firstName;
            texName.setText(MessageFormat.format("Name:{0}", name));
            txtPhoneNo.setText(MessageFormat.format("Phone No:{0}", cusPhoneNo));
            txtCusID.setText(MessageFormat.format("ID:{0}", iD));
            //doBundleDialog();
        }
        random= new SecureRandom();
        superAdmin= new UserSuperAdmin();
        userProfile= new Profile();
        adminUser=new AdminUser();
        customersN = new ArrayList<Customer>();
        adminUsers = new ArrayList<AdminUser>();
        customerManagerArrayList = new ArrayList<CustomerManager>();
        customerManager= new CustomerManager();
        spnSendAdmin = findViewById(R.id.SenddeeAllAdmin);
        spnSendTeller = findViewById(R.id.SenddeeAllTeller);
        spnSendCustomer = findViewById(R.id.SenddeeAllCus);
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        machine = sharedpreferences.getString("Machine", "");
        gson0 = new Gson();
        json0 = sharedpreferences.getString("LastAdminUserProfileUsed", "");
        superAdmin = gson0.fromJson(json0, UserSuperAdmin.class);
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        gson1 = new Gson();
        json1 = sharedpreferences.getString("LastAdminUserProfileUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);

        if(userProfile !=null){
            profileID=userProfile.getPID();
            userBundle.putLong(PROFILE_ID,profileID);

        }


        gson2 = new Gson();
        json2 = sharedpreferences.getString("LastAdminUserProfileUsed", "");
        customerManager = gson2.fromJson(json2, CustomerManager.class);
        isInvisible=false;
        if(machine.equalsIgnoreCase("UserSuperAdmin")){
            customersN = dbHelper.getAllCustomerSpinner();
            sender="Skylight";
            customerManagerArrayList=dbHelper.getAllTellersSpinner();
            adminUsers=dbHelper.getAllAdminSpinner();
            dialogSuperAdmin(customersN,customerManagerArrayList,adminUsers, sender,bundle,profileID);

        }
        if(machine.equalsIgnoreCase("SuperAdmin")){
            customersN = dbHelper.getAllCustomerSpinner();
            sender="Skylight";
            customerManagerArrayList=dbHelper.getAllCustomersManagers();
            adminUsers=dbHelper.getAllAdmin();
            dialogSuperAdmin(customersN,customerManagerArrayList,adminUsers,sender,bundle, profileID);

        }
        if(machine.equalsIgnoreCase("Teller")){
            if(customerManager !=null){
                officeBranch=customerManager.getTOffice();

            }
            if(userProfile !=null){
                customersN = userProfile.getProfileCustomers();

            }


            customerManagerArrayList=dbHelper.getAllTellersSpinner();
            sender=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();
            doTellerDialog(customersN,officeBranch,customerManagerArrayList,sender,bundle,profileID);

        }
        if(machine.equalsIgnoreCase("Admin")){
            if(adminUser !=null){
                officeBranch=adminUser.getAdminOffice();

            }

            customersN = dbHelper.getAllCustomerBranchSpinner(officeBranch);
            customerManagerArrayList=dbHelper.getAllTellerBranchSpinner(officeBranch);
            sender=officeBranch;
            doAdminDialog(officeBranch,customersN,customerManagerArrayList,sender,bundle,profileID);

        }
        if(machine.equalsIgnoreCase("AdminUser")){
            if(adminUser !=null){
                officeBranch=adminUser.getAdminOffice();

            }

            customersN = dbHelper.getAllCustomerBranchSpinner(officeBranch);
            customerManagerArrayList=dbHelper.getAllTellerBranchSpinner(officeBranch);
            sender=officeBranch;
            doAdminDialog(officeBranch, customersN, customerManagerArrayList, sender,bundle, profileID);

        }

        spnSendCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                customer = (Customer) parent.getSelectedItem();
                Toast.makeText(SendUserMessageAct.this, "Customer's ID: " + customer.getCusUID() + ",  Customer's Name : " + customer.getCusFirstName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        texName = findViewById(R.id.SenddeeName);
        txtCusID = findViewById(R.id.SenddeeCusID);
        txtProfileID = findViewById(R.id.SenddeeProfileID);
        txtPhoneNo = findViewById(R.id.SenddeePhone);
        editMessage = findViewById(R.id.messageUser);
        btnSend = findViewById(R.id.SenddeeSubmit);

        userBundle=new Bundle();


        userBundle.putString("Machine",machine);

        profiles = new ArrayList<String>();
        /*profiles=dbHelper.getProfileName(machine);

        final ArrayAdapter<String> adapterAdmin = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, profiles);
        adapterAdmin.setDropDownViewResource(R.layout.spn_user_value);
        spnSendAdmin.setAdapter(adapterAdmin);

        final ArrayAdapter<String> adapterTeller = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, profiles);
        adapterTeller.setDropDownViewResource(R.layout.spn_user_value);
        spnSendTeller.setAdapter(adapterTeller);*/

        /*customersAdapter = new ArrayAdapter<Customer>(this, R.layout.support_simple_spinner_dropdown_item, customers);
        customersAdapter.setDropDownViewResource(R.layout.spn_user_value);
        spnSendCustomer.setAdapter(customersAdapter);
        customer=null;*/

        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doMessageSend();

            }
        });




    }
    protected void doTellerDialog(ArrayList<Customer> customersN, String officeBranch, ArrayList<CustomerManager> customerManagerArrayList, String sender, Bundle bundle, long profileID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send to:");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"My Branch Office","A Customer","A Colleague"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                layoutAdmin.setVisibility(View.GONE);
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);

                                break;
                            case 1:
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);

                                customersAdapter = new ArrayAdapter<Customer>(SendUserMessageAct.this, android.R.layout.simple_spinner_item, customersN);
                                customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendCustomer.setAdapter(customersAdapter);
                                spnSendCustomer.setSelection(0);

                                selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
                                try {
                                    customer = customersN.get(selectedCustomerIndex);
                                    try {
                                        if (customer != null) {
                                            SendUserMessageAct.this.bundle =null;
                                            iD =customer.getCusUID();
                                            cusPhoneNo=customer.getCusPhoneNumber();
                                            userProfID=customer.getCusProfile().getPID();
                                            firstName=customer.getCusFirstName();
                                            surName=customer.getCusSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);
                                            txtPhoneNo.setText("Phone No:"+cusPhoneNo);
                                            txtCusID.setText("ID:"+ iD);

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }

                                break;

                            case 2:

                                layoutCus.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);

                                customerManagerArrayAdapter = new ArrayAdapter<CustomerManager>(SendUserMessageAct.this, android.R.layout.simple_spinner_item, customerManagerArrayList);
                                customerManagerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendTeller.setAdapter(customersAdapter);
                                spnSendTeller.setSelection(0);

                                selectedTellerIndex = spnSendTeller.getSelectedItemPosition();
                                try {
                                    customerManager = customerManagerArrayList.get(selectedTellerIndex);
                                    try {
                                        if (customerManager != null) {
                                            SendUserMessageAct.this.bundle =null;
                                            iD =customerManager.getTID();
                                            cusPhoneNo=customerManager.getTPhoneNumber();
                                            userProfID=customerManager.getTellerProfile().getPID();
                                            firstName=customerManager.getTFirstName();
                                            surName=customerManager.getTSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);
                                            txtPhoneNo.setText("Phone No:"+cusPhoneNo);
                                            txtCusID.setText("ID:"+ iD);

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }

                                break;


                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        doMessageSend();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder.create().show();


    }
    protected void doAdminDialog(String officeBranch, ArrayList<Customer> customersN, ArrayList<CustomerManager> customerManagerArrayList, String sender, Bundle bundle, long profileID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send to:");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"A Teller","A Branch Office","A Customer"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                layoutCus.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);
                                customerManagerArrayAdapter = new ArrayAdapter<CustomerManager>(SendUserMessageAct.this, android.R.layout.simple_spinner_item, SendUserMessageAct.this.customerManagerArrayList);
                                customerManagerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendTeller.setAdapter(customersAdapter);
                                spnSendTeller.setSelection(0);

                                selectedTellerIndex = spnSendTeller.getSelectedItemPosition();
                                try {
                                    customerManager = SendUserMessageAct.this.customerManagerArrayList.get(selectedTellerIndex);
                                    try {
                                        if (customerManager != null) {
                                            SendUserMessageAct.this.bundle =null;
                                            iD =customerManager.getTID();
                                            cusPhoneNo=customerManager.getTPhoneNumber();
                                            userProfID=customerManager.getTellerProfile().getPID();
                                            firstName=customerManager.getTFirstName();
                                            surName=customerManager.getTSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);
                                            txtPhoneNo.setText("Phone No:"+cusPhoneNo);
                                            txtCusID.setText("ID:"+ iD);

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }


                                break;
                            case 1:
                                layoutCus.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);


                                spnOfficeBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        //selectedOffice = spnOfficeBranch.getSelectedItem().toString();
                                        selectedOffice = (String) parent.getSelectedItem();
                                        Toast.makeText(SendUserMessageAct.this, "Office Branch Selected: "+ selectedOffice,Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                texName.setText("Name:"+selectedOffice);

                                break;

                            case 2:
                                layoutOffice.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                customersAdapter = new ArrayAdapter<Customer>(SendUserMessageAct.this, android.R.layout.simple_spinner_item, customersN);
                                customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendCustomer.setAdapter(customersAdapter);
                                spnSendCustomer.setSelection(0);

                                selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
                                try {
                                    customer = customersN.get(selectedCustomerIndex);
                                    try {
                                        if (customer != null) {
                                            SendUserMessageAct.this.bundle =null;
                                            iD =customer.getCusUID();
                                            cusPhoneNo=customer.getCusPhoneNumber();
                                            userProfID=customer.getCusProfile().getPID();
                                            firstName=customer.getCusFirstName();
                                            surName=customer.getCusSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);
                                            txtPhoneNo.setText("Phone No:"+cusPhoneNo);
                                            txtCusID.setText("ID:"+ iD);

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }


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
    protected void doBundleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose User to Send a Message");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Selected User", "All Customers"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);

                                break;
                            case 1:
                                customers=dbHelper.getAllCustomers11();
                                customersAdapter = new ArrayAdapter<Customer>(SendUserMessageAct.this, android.R.layout.simple_spinner_item, customers);
                                customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendCustomer.setAdapter(customersAdapter);
                                spnSendCustomer.setSelection(0);

                                selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
                                try {
                                    customer = customers.get(selectedCustomerIndex);
                                    try {
                                        if (customer != null) {
                                            bundle=null;
                                            iD =customer.getCusUID();
                                            cusPhoneNo=customer.getCusPhoneNumber();
                                            userProfID=customer.getCusProfile().getPID();
                                            firstName=customer.getCusFirstName();
                                            surName=customer.getCusSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);
                                            txtPhoneNo.setText("Phone No:"+cusPhoneNo);
                                            txtCusID.setText("ID:"+ iD);

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }


                                layoutCus.setVisibility(View.VISIBLE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
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
    protected void dialogSuperAdmin(ArrayList<Customer> customersN, ArrayList<CustomerManager> customerManagerArrayList, ArrayList<AdminUser> adminUsers, String sender, Bundle bundle, long profileID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send to:");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"An Admin", "A Teller","A Branch Office","A Customer"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);

                                break;
                            case 1:
                                layoutCus.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);


                                customers=dbHelper.getAllCustomers11();
                                customersAdapter = new ArrayAdapter<Customer>(SendUserMessageAct.this, android.R.layout.simple_spinner_item, customers);
                                customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendCustomer.setAdapter(customersAdapter);
                                spnSendCustomer.setSelection(0);

                                selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
                                try {
                                    customer = customers.get(selectedCustomerIndex);
                                    try {
                                        if (customer != null) {
                                            SendUserMessageAct.this.bundle =null;
                                            iD =customer.getCusUID();
                                            cusPhoneNo=customer.getCusPhoneNumber();
                                            userProfID=customer.getCusProfile().getPID();
                                            firstName=customer.getCusFirstName();
                                            surName=customer.getCusSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);
                                            txtPhoneNo.setText("Phone No:"+cusPhoneNo);
                                            txtCusID.setText("ID:"+ iD);

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }

                                break;

                            case 2:
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);

                                break;


                            case 3:
                                layoutAdmin.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);
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
    public void SelectSpinnerValue(View view) {
        spnSendCustomer.setSelection(2);
    }

    public  void doMessageSend(){
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        machine = sharedpreferences.getString("Machine", "");
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            userName=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();


        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String meassageDate = mdformat.format(calendar.getTime());
        messageID = random.nextInt((int) (Math.random() * 129) + 11);
        try {
            dbHelper.insertMessage(userProfID, iD,messageID, messageDetails,sender,sendee,officeBranch,meassageDate);


        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        //Toast.makeText(SendUserMessageAct.this, "Send a message to the Customer", Toast.LENGTH_SHORT).show();
        /*Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        messageToCustomer=editMessage.getText().toString();
        message = Message.creator(
                new com.twilio.type.PhoneNumber(cusPhoneNo),
                new com.twilio.type.PhoneNumber("234"+cusPhoneNo),
                messageToCustomer)
                .create();

        messageToCustomer=editMessage.getText().toString();
        message = com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:cusPhoneNo"),
                new com.twilio.type.PhoneNumber("whatsapp:cusPhoneNo"),
                messageToCustomer)
                .create();*/

        Intent userIntent = new Intent(SendUserMessageAct.this, LoginDirectorActivity.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
}