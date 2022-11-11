package com.skylightapp.Tellers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.CheckMailActivity;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.SavingsStandingOrderWeb;
import com.skylightapp.MyMessageFragment;
import com.skylightapp.PasswordRecovAct;
import com.skylightapp.PayNowActivity;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SignUpAct;
import com.skylightapp.TransactionFragment;
import com.skylightapp.UserTimeLineOverview;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Objects;

public class TellerDashboardAct extends AppCompatActivity {
    FragmentManager fragmentManager;
    public static final String USER_ID_EXTRA_KEY = "TellerDashboardAct.USER_ID_EXTRA_KEY";

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19;
    private ActionBar toolbar;
    private ImageView imgTime;
    private AppCompatTextView txtWelcome;
    private AppCompatTextView txtMessage, txtCus,txtPackages, txtStandingOrders;


    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private long profileID;
    Intent data;
    FloatingActionButton floatingActionButton;
    private static final String PREF_NAME = "skylight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_dashboard);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = findViewById(R.id.toolbar_teller);
        //setSupportActionBar(toolbar);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        Profile profile = gson.fromJson(json, Profile.class);
        txtWelcome = findViewById(R.id.welcome_teller90);
        txtStandingOrders = findViewById(R.id.No_of_SO90);
        txtCus = findViewById(R.id.cus909);
        txtPackages = findViewById(R.id.Groups90Savings);

        AppCompatImageView userpix = findViewById(R.id.profile_t);
        imgTime = findViewById(R.id.ic_teller_message);
        FrameLayout frameLayout = findViewById(R.id.frameCont_Teller);

        //userProfile.getTransactions().size();
        if(userProfile !=null){
            int noOfPacks= userProfile.getProfileSkylightPackages().size();
            int noOfStandingOrders= userProfile.getProfile_StandingOrders().size();
            int noOfCustomers= userProfile.getProfileCustomers().size();
            profileID = userProfile.getPID();

            txtStandingOrders.setText(MessageFormat.format("Standing Orders:{0}", String.valueOf(noOfStandingOrders)));
            txtPackages.setText(MessageFormat.format("Group Accounts:{0}", String.valueOf(noOfPacks)));
            txtCus.setText(MessageFormat.format("No. of Cus:{0}", String.valueOf(noOfCustomers)));

        }

//        userpix.setImageBitmap(BitmapFactory.decodeByteArray( userProfile.profilePix,
//                0,userProfile.profilePix.length));
        floatingActionButton = findViewById(R.id.fab_teller2);


        StringBuilder welcomeString = new StringBuilder();
        Calendar calendar = Calendar.getInstance();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            imgTime.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            welcomeString.append(getString(R.string.good_afternoon));
            imgTime.setImageResource(R.drawable.good_after1);
        } else {
            welcomeString.append(getString(R.string.good_evening));
            imgTime.setImageResource(R.drawable.good_even2);
        }

        welcomeString.append(", ")
                .append("Our Teller")
                .append(" ")
                .append("How is Business? ")
                .append(" ")
                .append(getString(R.string.happy))
                .append(" ");

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String[] days = getResources().getStringArray(R.array.days);
        String dow = "";

        switch (day) {
            case Calendar.SUNDAY:
                dow = days[0];
                break;
            case Calendar.MONDAY:
                dow = days[1];
                break;
            case Calendar.TUESDAY:
                dow = days[2];
                break;
            case Calendar.WEDNESDAY:
                dow = days[3];
                break;
            case Calendar.THURSDAY:
                dow = days[4];
                break;
            case Calendar.FRIDAY:
                dow = days[5];
                break;
            case Calendar.SATURDAY:
                dow = days[6];
                break;
            default:
                break;
        }

        welcomeString.append(dow)
                .append(".");

        txtWelcome.setText(welcomeString.toString());


    }


    public void viewTMessages(View view) {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.base_slide_right_in)
                .replace(R.id.frameCont_Teller, new MyMessageFragment(), "myMessageFragmentTag").addToBackStack(null)
                .commit();
    }
    public void viewTellerPayment(View view) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.base_slide_right_in)
                .replace(R.id.frameCont_Teller, new TransactionFragment(), "TransactionFragmentTag").addToBackStack(null)
                .commit();

    }

    public void viewTSavings(View view) {
    }

    public void tBorrowingOverviewFragment(View view) {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.base_slide_right_in)
                .replace(R.id.frameCont_Teller, new CMBorrowingOverviewFragment(), "loanFragmentTag").addToBackStack(null)
                .commit();
    }



    public void viewTPackages(View view) {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.base_slide_right_in)
                .replace(R.id.frameCont_Teller, new TPackListFragment(), "TellerPackageFragmentTag").addToBackStack(null)
                .commit();
    }



    public void viewCustomersT(View view) {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.base_slide_right_in)
                .replace(R.id.frameCont_Teller, new CustomerListFragment(), "customersFragmentTag").addToBackStack(null)
                .commit();
    }




    public void signUpAUserTeller(View view) {

        Intent intent = new Intent(this, SignUpAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }




    public void payNowT(View view) {
        Intent intent = new Intent(this, PayNowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }

    public void goToShopCM(View view) {
        Intent intent = new Intent(this, AwajimaSliderAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }

    public void webstoreTeller(View view) {
        Intent intent = new Intent(this, StoreManagerWeb.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }



    public void viewMyHistoryTeller(View view) {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.base_slide_right_in)
                .replace(R.id.frameCont_Teller, new UserTimeLineOverview(), "userTimelineFragmentTag").addToBackStack(null)
                .commit();
    }


    public void standingOrderT(View view) {
        Intent intent = new Intent(this, SavingsStandingOrderWeb.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }

    public void privacyPolicyTeller(View view) {
        Intent intent = new Intent(this, PrivacyPolicy_Web.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }

    public void getPasswordT(View view) {

        Intent intent = new Intent(this, PasswordRecovAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }

    public void checkTellerEmail(View view) {
        Intent intent = new Intent(this, CheckMailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }
    public void skylightC(View view) {
        Intent intent = new Intent(this, AwajimaSliderAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }
    public void packageAdderTellerAll(View view) {
        Intent intent = new Intent(this, PackageAllCusAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);
    }


    public void addSavingsPackageTel(View view) {

        Intent intent = new Intent(this, MyCusNewPackAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cm_main_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.privacy_policy:
                Toast.makeText(this, "Taking you to read our privacy policy", Toast.LENGTH_SHORT).show();
                Intent intentPolicy = new Intent(this, PrivacyPolicy_Web.class);
                intentPolicy.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentPolicy);
                break;
            case R.id.nav_web_shop1:
                Toast.makeText(this, "Taking you to the Awajima Web App", Toast.LENGTH_SHORT).show();
                Intent intentWebStore = new Intent(this, StoreManagerWeb.class);
                intentWebStore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentWebStore);

                break;
            case R.id.log1:
                Toast.makeText(this, "Logging you out of this profile...", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intentSL = new Intent(this, SignTabMainActivity.class);
                intentSL.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSL);
                finish();
                break;
            default:

        }


        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    public void sendCustomerCodeTeller(View view) {
        Intent usersIntent = new Intent(this,
                BusinessSMSAct.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }


}