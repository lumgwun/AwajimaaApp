package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.CusAdaptSuper;
import com.skylightapp.BlockedUserAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.SendCustomerMessage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.UnBlockUserAct;

import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFILE_ADDRESS;
import static com.skylightapp.Classes.Profile.PROFILE_DATE_JOINED;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;

public class CusByPackAct extends AppCompatActivity implements  CusAdaptSuper.CustomerListener{
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView TxtPaymentCounts;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerViewNoPacks, recyclerViewItemsP,recyclerViewInv,recyclerPromo,recyclerSo,recyclerSavings;

    private ArrayList<Customer> noPackCus;
    private ArrayList<Customer> savingsCus;
    private ArrayList<Customer> itemCus;
    private ArrayList<Customer> invCus;
    private ArrayList<Customer> promoCus;
    private ArrayList<Customer> sOCus;
    private CusAdaptSuper cusNoPackAdapt;
    private CusAdaptSuper cusInvAdapt;
    private CusAdaptSuper cusItemsAdapter;
    private CusAdaptSuper cusPromoAdapter;
    private CusAdaptSuper cusSavingsAdapter;
    private CusAdaptSuper cusSOAdapter;
    DBHelper dbHelper;
    private Profile profile;
    private Customer customer;
    String cusUserName,cusUserPassword,userRole;
    String json,itemPurchase,savings,investment,promo,selectedOffice;
    long profileID;
    private AppCompatButton btnSearch;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_by_pack);
        customer= new Customer();
        profile= new Profile();
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.AllPayments);
        recyclerViewNoPacks = findViewById(R.id.recycler_cusNoPack);
        recyclerViewItemsP = findViewById(R.id.recycler_ItemsCus);
        recyclerViewInv = findViewById(R.id.recycler_InvCus);
        recyclerPromo = findViewById(R.id.recycler_PromoCus);
        recyclerSo = findViewById(R.id.recycler_SoCus);
        recyclerSavings = findViewById(R.id.recycler_SavingsCus);
        noPackCus = new ArrayList<Customer>();
        savingsCus = new ArrayList<Customer>();
        itemCus = new ArrayList<Customer>();
        invCus = new ArrayList<Customer>();
        promoCus = new ArrayList<Customer>();
        sOCus = new ArrayList<Customer>();
        dbHelper=new DBHelper(this);
        itemPurchase="Item Purchase";
        savings="Savings";
        investment="Investment";
        promo="Promo";

        noPackCus = dbHelper.getCusWithoutPackage();
        savingsCus = dbHelper.getCusForSavings(savings);
        itemCus = dbHelper.getCusForItemsPurchase(itemPurchase);
        invCus =dbHelper.getCusForInvestment(investment);
        promoCus =dbHelper.getCusForPromo(promo);
        sOCus =dbHelper.getAllCusForSo();

        cusNoPackAdapt = new CusAdaptSuper(CusByPackAct.this,noPackCus);
        cusInvAdapt = new CusAdaptSuper(CusByPackAct.this,invCus);
        cusItemsAdapter = new CusAdaptSuper(CusByPackAct.this,itemCus);
        cusPromoAdapter = new CusAdaptSuper(CusByPackAct.this,promoCus);
        cusSavingsAdapter = new CusAdaptSuper(CusByPackAct.this,savingsCus);
        cusSOAdapter = new CusAdaptSuper(CusByPackAct.this,sOCus);


        LinearLayoutManager layoutManagerNo = new LinearLayoutManager(CusByPackAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNoPacks.setLayoutManager(layoutManagerNo);
        recyclerViewNoPacks.setAdapter(cusNoPackAdapt);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewNoPacks.getContext(),
                layoutManagerNo.getOrientation());
        recyclerViewNoPacks.addItemDecoration(dividerItemDecoration7);




        LinearLayoutManager layoutManagerItems
                = new LinearLayoutManager(CusByPackAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewItemsP.setLayoutManager(layoutManagerItems);
        recyclerViewItemsP.setItemAnimator(new DefaultItemAnimator());
        recyclerViewItemsP.setAdapter(cusItemsAdapter);
        DividerItemDecoration dividerItemDecorationItems = new DividerItemDecoration(recyclerViewItemsP.getContext(),
                layoutManagerItems.getOrientation());
        recyclerViewItemsP.addItemDecoration(dividerItemDecorationItems);
        recyclerViewNoPacks.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewItemsP);


        LinearLayoutManager layoutManagerSavings
                = new LinearLayoutManager(CusByPackAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSavings.setLayoutManager(layoutManagerSavings);
        recyclerSavings.setItemAnimator(new DefaultItemAnimator());
        recyclerSavings.setAdapter(cusSavingsAdapter);
        DividerItemDecoration dividerItemDecorationSavings = new DividerItemDecoration(recyclerSavings.getContext(),
                layoutManagerSavings.getOrientation());
        recyclerSavings.addItemDecoration(dividerItemDecorationSavings);
        recyclerViewItemsP.setNestedScrollingEnabled(false);


        LinearLayoutManager layoutManagerInv
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewInv.setLayoutManager(layoutManagerInv);
        recyclerViewInv.setAdapter(cusInvAdapt);
        DividerItemDecoration dividerItemDecorationInv = new DividerItemDecoration(recyclerViewInv.getContext(),
                layoutManagerInv.getOrientation());
        recyclerViewInv.addItemDecoration(dividerItemDecorationInv);



        LinearLayoutManager layoutManagerPromo
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPromo.setLayoutManager(layoutManagerPromo);
        recyclerPromo.setAdapter(cusPromoAdapter);
        DividerItemDecoration dividerItemDecorationPromo = new DividerItemDecoration(recyclerPromo.getContext(),
                layoutManagerPromo.getOrientation());
        recyclerPromo.addItemDecoration(dividerItemDecorationPromo);


        LinearLayoutManager layoutManagerSO
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSo.setLayoutManager(layoutManagerSO);
        recyclerSo.setAdapter(cusSOAdapter);
        DividerItemDecoration dividerItemDecorationSO = new DividerItemDecoration(recyclerSo.getContext(),
                layoutManagerSO.getOrientation());
        recyclerSo.addItemDecoration(dividerItemDecorationSO);



    }

    @Override
    public void onItemClick(Customer customer) {
        dbHelper=new DBHelper(this);
        if(customer !=null){
            cusUserName=customer.getCusUserName();
            cusUserPassword=customer.getCusPassword();
            profile=customer.getCusProfile();

        }
        userRole=dbHelper.getProfileRoleByUserNameAndPassword(cusUserName,cusUserPassword);

        if(userRole !=null) {
            if (userRole.equalsIgnoreCase("BlockedUser")) {
                Intent tellerIntent = new Intent(this, UnBlockUserAct.class);
                tellerIntent.putExtra("PROFILE_ID", profileID);
                tellerIntent.putExtra("PROFILE_USERNAME", cusUserName);
                tellerIntent.putExtra("PROFILE_PASSWORD", cusUserPassword);
            } else {

            }
        }


        Bundle messageBundle= new Bundle();
        messageBundle.putParcelable("Customer", customer);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Next Action");
        String[] options = {"Send Customer Message", "Block Customer"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(CusByPackAct.this, SendCustomerMessage.class);
                        intent.putExtras(messageBundle);
                        startActivity(intent);
                    case 1:
                        Intent blockIntent = new Intent(CusByPackAct.this, BlockedUserAct.class);
                        blockIntent.putExtras(messageBundle);
                        startActivity(blockIntent);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.super_cus_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchCus)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cusItemsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                cusItemsAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchCus) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}