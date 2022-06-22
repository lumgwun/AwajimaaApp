package com.skylightapp.Admins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.AdminCusAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusLoanRepaymentAct;
import com.skylightapp.Customers.CusPackageList;
import com.skylightapp.Customers.CustomerLoanListAct;
import com.skylightapp.Customers.MySavingsListAct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.SignUpAct.hasPermissions;

public class CustomerListActivity extends AppCompatActivity implements AdminCusAdapter.CustomerListener{
    ListView mCustomerList;
    Context context;
    Customer customer;
    int customerID, profileID;
    LatLng customerLatLng;
    String selectedStatus;
    String cusPhoneNo,cusEmail;
    double cusLat,cusLng;
    RecyclerView recyclerView,recyclerViewFirebase;
    AdminCusAdapter adminCusAdapter;

    ArrayAdapter<Customer> customerArrayAdapter;
    ArrayList<Customer> customers;
    //ArrayList<Customer> customersF;
    ArrayList<Customer> customersFireBase;
    DBHelper dbHelper;
    SearchView searchView;
    MenuItem searchItem;
    private static final String PREF_NAME = "skylight";
    long cusProfileID;
    SQLiteDatabase sqLiteDatabase;
    Profile cusProfile;
    Bundle bundle;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    public final static int PICK_PHOTO_CODE = 10436;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_CAMERA_CODE=22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_list);
        dbHelper= new DBHelper(this);
        //userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mCustomerList = findViewById(R.id.customerList);
        customers=new ArrayList<>();
        customersFireBase=new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_viewA);

        customers=dbHelper.getAllCustomers11();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adminCusAdapter = new AdminCusAdapter(CustomerListActivity.this,customers);
        recyclerView.setAdapter(adminCusAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        customer = new Customer();
        recyclerView.addOnItemTouchListener(new MyTouchListener(CustomerListActivity.this, recyclerView,new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {//code as per your need
            }

            @Override
            public void onRightSwipe(View view, int position) {//code as per your need
            }

            @Override
            public void onClick(View view, int position) {//code as per your need
            }
        }));

    }
    public void onPickProfilePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();
            dbHelper= new DBHelper(this);
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                //dbHelper = new DBHelper(this);
                sqLiteDatabase = dbHelper.getWritableDatabase();
                try {
                    dbHelper.insertProfilePicture(profileID,customerID,photoUri);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }


            }




        }
    }
    @Override protected void onStart()
    {
        super.onStart();
        //adminCusAdapterF.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
       // adminCusAdapterF.stopListening();
    }
    public void showDialog() {
        final AppCompatEditText edtEmailAddress,edtPhone,edtAddress,edtPassword;
        final CircleImageView profilePix;
        AppCompatButton btnSubmit;
        Spinner spnStatus;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.update_customer);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        edtEmailAddress = dialog.findViewById(R.id.email_addressUpdate);
        edtPhone = (AppCompatEditText) dialog.findViewById(R.id.phone_numberUpdate);
        edtAddress = (AppCompatEditText) dialog.findViewById(R.id.address_allUpdate);
        edtPassword = (AppCompatEditText) dialog.findViewById(R.id.passwordUpdate);
        profilePix = (CircleImageView) dialog.findViewById(R.id.picture_photo);
        spnStatus = (Spinner) dialog.findViewById(R.id.spnUpPix);

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedStatus = spnStatus.getSelectedItem().toString();
                selectedStatus = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSubmit = dialog.findViewById(R.id.submitUpdate);
        profilePix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(CustomerListActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(CustomerListActivity.this, PERMISSIONS, PERMISSION_ALL);
                }

                final PopupMenu popup = new PopupMenu(CustomerListActivity.this, profilePix);
                popup.getMenuInflater().inflate(R.menu.profile, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.Camera) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, RESULT_CAMERA_CODE);

                        }

                        if (item.getItemId() == R.id.Gallery) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                        if (item.getItemId() == R.id.cancel_action) {
                            // finish();
                        }

                        return true;
                    }
                });
                popup.show();



            }
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                if ((data != null) && requestCode == PICK_PHOTO_CODE) {
                    Uri photoUri = data.getData();
                    dbHelper= new DBHelper(CustomerListActivity.this);

                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        //dbHelper = new DBHelper(this);
                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        try {
                            dbHelper.updateProfilePix(profileID,customerID,photoUri);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }




                }
                if ((data != null) && requestCode == RESULT_LOAD_IMAGE) {
                    Uri photoUri = data.getData();
                    dbHelper= new DBHelper(CustomerListActivity.this);

                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        try {
                            dbHelper.updateProfilePix(profileID,customerID,photoUri);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }



                }



            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (edtEmailAddress.getText().toString().isEmpty()) {
                    edtEmailAddress.setError("Please Enter Email Address");
                }
                if (edtPhone.getText().toString().isEmpty()) {
                    edtPhone.setError("Please Enter Phone Number");
                }else if(edtAddress.getText().toString().isEmpty()) {
                    edtAddress.setError("Please Enter Address");
                }else {
                    String phoneNo=edtPhone.getText().toString();
                    String email=edtEmailAddress.getText().toString();
                    String address=edtAddress.getText().toString();
                    String password=edtPassword.getText().toString();
                    dbHelper= new DBHelper(CustomerListActivity.this);

                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        try {
                            dbHelper.updateCustomer(customerID,"","",phoneNo,email,"",address,"","",password,selectedStatus);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }

                    dialog.cancel();
                    displayCustomers();
                }
            }
        });
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void displayCustomers() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                customers = new ArrayList<>(dbHelper.getAllCustomers11());
            } catch (Exception e) {
                System.out.println("Oops!");
            }


        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adminCusAdapter  = new AdminCusAdapter(getApplicationContext(), this, customers);
        recyclerView.setAdapter(adminCusAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_cus_menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        try {
            if(searchItem !=null){
                searchView = (SearchView) searchItem.getActionView();
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adminCusAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void doPackage (){

    }
    public void  location(){

    }

    @Override
    public void onItemClick(Customer customer) {
        bundle = new Bundle();
        if (customer != null) {
            customerID = customer.getCusUID();
            cusProfile = customer.getCusProfile();
            customerLatLng = customer.getCusLocation();
            cusPhoneNo = customer.getCusPhoneNumber();
            cusEmail = customer.getCusEmailAddress();
            cusLat = customer.getCusLocation().latitude;
            cusLng = customer.getCusLocation().longitude;
            if(cusProfile !=null){
                cusProfileID = cusProfile.getPID();

            }
            bundle=new Bundle();
            bundle.putInt("customerID",customerID);
            bundle.putInt(CUSTOMER_ID,customerID);
            bundle.putInt("CUSTOMER_ID",customerID);
            bundle.putParcelable("Customer",customer);
            bundle.putParcelable("customer",customer);
            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerListActivity.this);
            builder.setTitle("Choose Action on Customer");
            Bundle finalBundle = bundle;
            builder.setItems(new CharSequence[]
                            {"Delete Customer", "Update Profile", "Loans", "Packages", "Savings", "Standing Orders", "Transactions", "Savings Codes", "Payment Docs", " Location", "Messages", " Send Customer message"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    Toast.makeText(CustomerListActivity.this, "delete option, selected ", Toast.LENGTH_SHORT).show();
                                    dbHelper.delete_Customer_byID(customerID);
                                    break;
                                case 1:
                                    Toast.makeText(CustomerListActivity.this, "update customer, selected", Toast.LENGTH_SHORT).show();
                                    showDialog();
                                    break;
                                case 2:
                                    Toast.makeText(CustomerListActivity.this, "Check Loan Overview", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(CustomerListActivity.this, CustomerLoanListAct.class);
                                    intent.putExtras(finalBundle);
                                    startActivity(intent);
                                    break;
                                case 3:
                                    Toast.makeText(CustomerListActivity.this, "Check package overview", Toast.LENGTH_SHORT).show();
                                    //dbHelper.getPackagesFromCustomer(customerID);

                                    Intent intent22 = new Intent(CustomerListActivity.this, CusPackageList.class);
                                    intent22.putExtras(finalBundle);
                                    startActivity(intent22);
                                    break;
                                case 4:
                                    Toast.makeText(CustomerListActivity.this, "Savings option, selected ", Toast.LENGTH_SHORT).show();
                                    //dbHelper.getSavingsFromCurrentCustomer(customerID);

                                    Intent cusIntent = new Intent(CustomerListActivity.this, MySavingsListAct.class);
                                    cusIntent.putExtras(finalBundle);
                                    startActivity(cusIntent);
                                    break;
                                case 5:
                                    Toast.makeText(CustomerListActivity.this, "View standing orders option, selected ", Toast.LENGTH_SHORT).show();
                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        //dbHelper = new DBHelper(this);
                                        sqLiteDatabase = dbHelper.getWritableDatabase();
                                        try {
                                            dbHelper.getAllStandingOrdersForCustomer(customerID);
                                        } catch (Exception e) {
                                            System.out.println("Oops!");
                                        }


                                    }

                                    break;
                                case 6:
                                    Toast.makeText(CustomerListActivity.this, "View transaction option, selected ", Toast.LENGTH_SHORT).show();
                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        sqLiteDatabase = dbHelper.getWritableDatabase();
                                        try {
                                            dbHelper.getAllTransactionCustomer(customerID);
                                        } catch (Exception e) {
                                            System.out.println("Oops!");
                                        }

                                    }

                                    break;
                                case 7:
                                    Toast.makeText(CustomerListActivity.this, "View savings codes option, selected ", Toast.LENGTH_SHORT).show();

                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        sqLiteDatabase = dbHelper.getWritableDatabase();
                                        try {
                                            dbHelper.getSavingsCodesCustomer(customerID);
                                        } catch (Exception e) {
                                            System.out.println("Oops!");
                                        }

                                    }

                                    break;
                                case 8:
                                    Toast.makeText(CustomerListActivity.this, "View payment documents option, selected ", Toast.LENGTH_SHORT).show();

                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        sqLiteDatabase = dbHelper.getWritableDatabase();
                                        try {
                                            dbHelper.getDocumentsFromCurrentCustomer(customerID);
                                        } catch (Exception e) {
                                            System.out.println("Oops!");
                                        }

                                    }

                                    break;

                                case 9:
                                    Toast.makeText(CustomerListActivity.this, "View Customer's Loc.", Toast.LENGTH_SHORT).show();
                                    Bundle bundle33 = new Bundle();
                                    Intent intent33 = new Intent(CustomerListActivity.this, TrackWorkersAct.class);
                                    intent33.putExtra("PreviousLongitude", cusLng);
                                    intent33.putExtra("PreviousLatitude", cusLat);
                                    intent33.putExtra("Customer", (Parcelable) customer);
                                    intent33.putExtra("Customer Profile", (Parcelable) cusProfile);
                                    intent33.putExtra("PreviousLocation", (Parcelable) customerLatLng);
                                    startActivity(intent33);
                                    break;
                                case 10:
                                    Toast.makeText(CustomerListActivity.this, "Customer messages, selected ", Toast.LENGTH_SHORT).show();
                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        sqLiteDatabase = dbHelper.getWritableDatabase();
                                        try {
                                            dbHelper.getMessagesForCurrentCustomer(customerID);
                                        } catch (Exception e) {
                                            System.out.println("Oops!");
                                        }

                                    }

                                    break;

                                case 11:
                                    Toast.makeText(CustomerListActivity.this, "Send a message to the Customer", Toast.LENGTH_SHORT).show();
                                    Bundle messageBundle = new Bundle();
                                    Intent messageIntent = new Intent(CustomerListActivity.this, SendUserMessageAct.class);
                                    messageIntent.putExtra("Customer", (Parcelable) customer);
                                    messageIntent.putExtra("Customer Profile", (Parcelable) cusProfile);
                                    messageIntent.putExtra("Customer ID", customerID);
                                    messageIntent.putExtra("Profile ID", cusProfileID);
                                    startActivity(messageIntent);
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


            /*Intent intent = new Intent(CustomerListActivity.this, AdminCusActionAct.class);
            intent.putExtra("Customer", (Parcelable) customer);
            startActivity(intent);*/


        }
    }
}