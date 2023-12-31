package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.skylightapp.Accountant.AcctantBackOffice;
import com.skylightapp.Admins.AdminHomeChoices;
import com.skylightapp.Bookings.BookingHomeAct;
import com.skylightapp.Bookings.BookingsMainTab;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;

import com.skylightapp.Customers.CusLoanTab;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.MapAndLoc.ClimateCOffice;
import com.skylightapp.MapAndLoc.NOSDRAOffice;
import com.skylightapp.MapAndLoc.OilCompanyOffice;
import com.skylightapp.MapAndLoc.ResponseTeamOffice;
import com.skylightapp.Markets.BizAdminOffice;
import com.skylightapp.Markets.BizRegulOffice;
import com.skylightapp.Markets.MarketAdminOffice;
import com.skylightapp.Markets.MarketBizDonorOffice;
import com.skylightapp.Markets.MarketBizOffice;
import com.skylightapp.Markets.MarketBizPOffice;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.Markets.SupplierOffice;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.Tellers.TellerHomeChoices;
import com.skylightapp.StateDir.StateDashboard;

public class TestAct extends AppCompatActivity {
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    private Customer selectedCustomer;
    private Profile userProfile;
    private StandingOrderAcct standingOrderAcct;
    int profileID;
    private static final String PREF_NAME = "awajima";
    private GridLayout maingrid;
    private AppCompatButton btn_Customer,btnAdmin,btnShop,btnTeller,btnSuperAdmin,btnSign,btnAccountant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        gson = new Gson();
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        standingOrderAcct= new StandingOrderAcct();
        btn_Customer = findViewById(R.id.getCustomer);
        btnSign = findViewById(R.id.GetSign);
        btnAdmin = findViewById(R.id.getAdmin);
        btnShop = findViewById(R.id.getEShopping);
        btnAccountant = findViewById(R.id.getAccountant);
        btnSign.setOnClickListener(this::goSignTab);
        btnShop.setOnClickListener(this::goECommerce);
        btnAdmin.setOnClickListener(this::goAdmin);
        btnTeller = findViewById(R.id.GetTeller);
        btnSuperAdmin = findViewById(R.id.getSuperAdmin);
        btnSuperAdmin.setOnClickListener(this::goSuperAdmin);
        btnTeller.setOnClickListener(this::goTeller);
        btnAccountant.setOnClickListener(this::goAccountant);
        maingrid=(GridLayout) findViewById(R.id.grid_test);

        selectedCustomer= new Customer();
        btn_Customer.setOnClickListener(this::goCustomer);
        setSingleEvent(maingrid);
    }
    public void goAdmin(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, AdminHomeChoices.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goCustomer(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goTeller(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, TellerHomeChoices.class);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void goAccountant(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, AcctantBackOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goShop(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, MarketTab.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goSignTab(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, SignTabMainActivity.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void goSuperAdmin(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, SuperAdminOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void goECommerce(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, MarketTab.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void goDonor(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, MarketBizDonorOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goMarketAdminOffice(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, MarketAdminOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goBizOffice(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, MarketBizOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goPartnerOffice(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, MarketBizPOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goRegulator(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, BizRegulOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goLogistics(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, BizLogisticsOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goHQLogistics(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, HQLogisticsOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goInsurance(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, InsuranceOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void goCoach(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, CoachOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void getClimateOffice(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, ClimateCOffice.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    private void setSingleEvent(GridLayout maingrid)
    {
        for(int i=0;i<maingrid.getChildCount();i++)
        {
            CardView cardView=(CardView) maingrid.getChildAt(i);
            final int finalI=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent intent=new Intent(MainActivity.this,places.class);
                    String tosend="";
                    if(finalI==0)
                    {
                        tosend="HQ";
                        Intent intent=new Intent(TestAct.this, HQLogisticsOffice.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==1)
                    {
                        tosend="Insurance";
                        Intent intent=new Intent(TestAct.this, InsuranceOffice.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==2)
                    {
                        tosend="Coach Live";
                        Intent intent=new Intent(TestAct.this, CoachOffice.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==3) {
                        tosend = "Loans";
                        Intent intent=new Intent(TestAct.this, CusLoanTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==4) {
                        tosend = "Privacy";
                        Intent intent=new Intent(TestAct.this, PrivacyPolicy_Web.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==5) {
                        tosend = "Bookings";
                        Intent intent=new Intent(TestAct.this, BookingHomeAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    /*else if(finalI==6) {
                        tosend = "Biz Partner";
                        Intent intent=new Intent(TestAct.this, MarketBizPOffice.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }*/

                }
            });
        }
    }

    public void goBizPartner(View view) {
        Intent intent=new Intent(TestAct.this, MarketBizDonorOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void goRegulatorsTab(View view) {
        Intent intent=new Intent(TestAct.this, BizRegulOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void goResponseTTab(View view) {
        Intent intent=new Intent(TestAct.this, ResponseTeamOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void goSupplierTab(View view) {
        Intent intent=new Intent(TestAct.this, SupplierOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void goTripTab(View view) {
        Intent intent=new Intent(TestAct.this, BookingsMainTab.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }
    public void goStateOffice(View view) {
        Intent intent=new Intent(TestAct.this, StateDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void goOilComTab(View view) {
        Intent intent=new Intent(TestAct.this, OilCompanyOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void goNosdraTab(View view) {
        Intent intent=new Intent(TestAct.this, NOSDRAOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    public void GetBizBranchO(View view) {
        Intent intent = new Intent(TestAct.this, BizAdminOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }
}