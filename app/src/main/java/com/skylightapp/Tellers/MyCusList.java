package com.skylightapp.Tellers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.CustomerAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MyCusList extends AppCompatActivity  implements  CustomerAdapter.CustomerListener{
    Profile userProfile;
    long profileUID2;
    long id;
    String userName;
    String password;
    Customer customer;
    Bundle getBundle;
    String name;
    com.github.clans.fab.FloatingActionButton fab;
    String ManagerSurname,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json;
    private  DBHelper dbHelper;
    private RecyclerView recyclerView;

    private ArrayList<Customer> customerArrayList;
    private CustomerAdapter mAdapter;
    private TextView messageTxt;
    private  int cusCount;
    FloatingActionButton fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_cus_list);
        customer= new Customer();
        userProfile=new Profile();
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        checkInternetConnection();
        dbHelper = new DBHelper(this);
        messageTxt = findViewById(R.id.myCusT);
        fab2 = findViewById(R.id.cu_fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doHome();
            }
        });

        if(userProfile !=null){
            ManagerSurname = userProfile.getProfileLastName();
            managerFirstName = userProfile.getProfileFirstName();
            managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
            managerEmail = userProfile.getProfileEmail();
            managerNIN = userProfile.getProfileIdentity();
            managerUserName = userProfile.getProfileUserName();
            String userRole = userProfile.getProfileMachine();
            profileUID2= userProfile.getPID();
            try {

                cusCount=userProfile.getProfileCustomers().size();
                messageTxt.setText(MessageFormat.format("My Customers:{0}", cusCount));

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }


        }
        else {
            messageTxt.setText("No known Profile,please login to see your Customers");
        }
        recyclerView = findViewById(R.id.recycler_view_MyCus);
        customerArrayList = new ArrayList<Customer>();
        if(userProfile !=null){
            customerArrayList = userProfile.getProfileCustomers();

        }

        mAdapter = new CustomerAdapter(MyCusList.this, customerArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

    }

    private void doHome() {
        Intent intent = new Intent(MyCusList.this, TellerDrawerAct.class);
        startActivity(intent);

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
    public void onItemClick(Customer customer) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MyCusList.this, CustomerDetailAct.class);
        bundle.putParcelable("Customer", customer);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}