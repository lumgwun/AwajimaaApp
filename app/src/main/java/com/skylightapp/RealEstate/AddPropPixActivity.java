package com.skylightapp.RealEstate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class AddPropPixActivity extends AppCompatActivity {
    private static final String TAG = AddPropPixActivity.class.getSimpleName();
    public final static String PROPERTY_IMAGE_PATH = "ImageLink";
    public final static String PROPERTY_IMAGE_TITTLE = "ImageTittle";
    public final static String PROPERTY_IMAGE_ID = "ImageID";
    Bundle bundle;
    //DatabaseReference firebasePostPropPixRef,firebaseUser;
    //FirebaseDatabase firebaseDatabase;
    //AppController appController;
    private static boolean isPersistenceEnabled = false;
    String json,nIN;
    Profile userProfile;
    Gson gson;
    String acct;
    SQLiteDatabase sqLiteDatabase;
    long pictureID;
    String uPhoneNumber;
    long profileID;
    //private FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener mAuthListner;
    SecureRandom random;
    Context context;
    String dateOfBirth;
    SharedPreferences userPreferences;

    private DBHelper dbHelper;
    protected Profile managerProfile;
    String uFirstName, uEmail, uSurname, uAddress, uUserName;
    String ManagerSurname;
    Long propertyIDReceived;
    int propertyOwnerID;
    String managerFirstName, uPassword,uMachine,uProfileID,uPhoneNo,uDateJoined,uOffice,uState,propertyOwnerPhoneNo,pictureTittle;
    ArrayList<PropertyImage> sliderDataArrayList;
    AppCompatTextView propertyTittle;
    Location mCurrentLocation=null;
    AppCompatEditText edtPropertyTittle;
    AppCompatTextView txtPropertyID,txtPropertyAdminID;
    AppCompatImageView photoPrOP;
    Uri pictureUri;
    Bitmap bimage=null;
    private Properties properties;

    ActivityResultLauncher<Intent> mGetPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            if (data != null) {
                                pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(photoPrOP);
                            }
                            Toast.makeText(AddPropPixActivity.this, "Image uploading successful ", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(AddPropPixActivity.this, "Image uploading failed", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_prop_pix);
        sliderDataArrayList = new ArrayList<>();
        managerProfile=new Profile();
        //propertyTittle = findViewById(R.id.myPropertyTittle);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        uUserName = userPreferences.getString("USER_NAME", "");
        uPassword = userPreferences.getString("USER_PASSWORD", "");
        uMachine = userPreferences.getString("machine", "");
        uProfileID = userPreferences.getString("PROFILE_ID", "");
        uSurname = userPreferences.getString("USER_SURNAME", "");
        uFirstName = userPreferences.getString("USER_FIRSTNAME", "");
        uEmail = userPreferences.getString("USER_EMAIL", "");
        uPhoneNo = userPreferences.getString("USER_PHONE", "");
        uDateJoined = userPreferences.getString("USER_DATE_JOINED", "");
        uOffice = userPreferences.getString("CHOSEN_OFFICE", "");
        uState = userPreferences.getString("USER_STATE", "");
        gson = new Gson();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        SliderView sliderView = findViewById(R.id.sliderProperty);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        edtPropertyTittle = findViewById(R.id.propertyTittle);
        txtPropertyID = findViewById(R.id.idProp);
        txtPropertyAdminID = findViewById(R.id.idOwner);
        photoPrOP = findViewById(R.id.photoProp);

        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        pictureID = random.nextInt((int) (Math.random() * 900) + 101);
        bundle = getIntent().getExtras().getBundle("bundle") ;

        try {
            if(bundle !=null){
                properties=bundle.getParcelable("Properties");
                if(properties !=null){
                    propertyIDReceived=properties.propertyID;
                    managerProfile=properties.getProfile();


                }
                if(managerProfile !=null){
                    propertyOwnerID=managerProfile.getPID();
                    propertyOwnerPhoneNo=managerProfile.getProfilePhoneNumber();

                }
                txtPropertyAdminID.setText(MessageFormat.format("Admin Profile ID:{0}", propertyOwnerID));
                txtPropertyID.setText(MessageFormat.format("Property ID:{0}", propertyIDReceived));

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        sliderDataArrayList=dbHelper.getImagesForAProp(propertyIDReceived);

    }

    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }
    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));

    }
    protected void sendSMSMessage() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String timeLineTime = mdformat.format(calendar.getTime());
        String welcomeMessage = "A new Property Picture:" +pictureID +"" +"was added for "+propertyIDReceived+""+"@"+timeLineTime;
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(propertyOwnerPhoneNo),
                new com.twilio.type.PhoneNumber("234" + propertyOwnerPhoneNo),
                welcomeMessage)
                .create();

    }
    public void SubmitNewPicture(View view){
        try {
            pictureTittle = Objects.requireNonNull(edtPropertyTittle.getText()).toString().trim();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        String managerFullNamesT = ManagerSurname + "," + managerFirstName;
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String timeLineTime = mdformat.format(calendar.getTime());

        String timelineDetailsT = propertyIDReceived + "received a new Picture @" + "" + timeLineTime;
        String tittleT = "Property Picture Alert!";
        String timelineDetailsT1 = "You added" + uSurname + "," + uFirstName + "as a Teller" + "on" + timeLineTime;

        String managerFullNames = uSurname + "," + uFirstName;
        String timelineDetails = uSurname + "," + uFirstName + "added Picture:"  + pictureID + "@" + timeLineTime;

        String names = uSurname + uFirstName;
        //long customerId = customer.getId();
        //Customer c = new Customer();
        String name = uSurname + "," + uFirstName;


        /*try {
            firebasePostPropPixRef = FirebaseDatabase.getInstance().getReference("PropertyInfo").child("propertyPix");

            firebasePostPropPixRef.child("profileID").setValue(profileID);
            firebasePostPropPixRef.child(String.valueOf(propertyIDReceived)).setValue(PROPERTY_ID_KEY);
            firebasePostPropPixRef.child(String.valueOf(propertyOwnerID)).setValue(PROPERTY_OWNER_KEY);
            firebasePostPropPixRef.child(PROPERTY_IMAGE_PATH).setValue(uEmail);
            //firebasePostPropPixRef.child(PROPERTY_IMAGE_TITTLE).setValue("Tittle");
            firebasePostPropPixRef.child(PROPERTY_IMAGE_ID).setValue(pictureID);

        } catch (DatabaseException e) {
            System.out.println("Oops!");
        }*/
        try {

            dbHelper.insertTimeLine(tittleT, timelineDetails, timeLineTime, mCurrentLocation);
            dbHelper.saveNewPropertyPicture(profileID,propertyIDReceived,pictureID, pictureTittle ,pictureUri);


        } catch (SQLiteException e) {
            System.out.println("Oops!");
        }

        Toast.makeText(AddPropPixActivity.this, "Thank you" +
                "for adding a Property Pix  on Our Coop. App", Toast.LENGTH_LONG).show();

    }

    public void selectPropPicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            mGetPicture.launch(intent);
        } else {
            Toast.makeText(AddPropPixActivity.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }

    }
}