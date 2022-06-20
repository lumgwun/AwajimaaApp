package com.skylightapp.RealEstate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class EstatePropAct extends AppCompatActivity implements SearchView.OnQueryTextListener,PropertyListAdapter.PropertyListener {
    //ListView list;
    PropertyListAdapter adapter;
    PropertyListAdapter propertyAdapterDateOnly;
    PropertyListAdapter propertyListAdapterJoint;
    SearchView editsearch;
    ArrayList<Properties> arraylist;
    ArrayList<Properties> arraylistByDateOnly;
    ArrayList<Properties> arraylistByDateAndAnother;
    ArrayList<Properties> propertiesArrayList;
    DBHelper dbHelper;
    Spinner spnPropSearchByDBType,spnPropSearchOthers;
    AppCompatButton btnSearchByOthers, btnSearchByType,btnSearchByDate;
    RecyclerView recyclerViewAllProp;
    SharedPreferences userPreferences;
    Gson gson;
    String json,profileFirstName,profileSurname;
    long profileID;
    private FloatingActionButton fab;
    private  Profile userProfile;
    String selectedPropType,dateOfProperty,selectedOthers;
    long reportTime;
    int propertyCount;
    RecyclerView recyclerViewByType,recyclerViewByTypeAndDate;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayList<String> stringArrayList;
    DatePicker picker;
    protected DatePickerDialog datePickerDialog;
    private TextView txtPropCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_estate_prop);
        arraylist = new ArrayList<Properties>();
        dbHelper= new DBHelper(this);
        propertiesArrayList = new ArrayList<Properties>();
        arraylistByDateOnly = new ArrayList<Properties>();
        arraylistByDateAndAnother = new ArrayList<Properties>();
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        //showSearchView = new ShowSearchView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddProperty);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        txtPropCount = findViewById(R.id.countProp);
        stringArrayList = new ArrayList<String>();
        spnPropSearchByDBType = (Spinner) findViewById(R.id.spnSearchByDBType);
        spnPropSearchOthers = (Spinner) findViewById(R.id.spnSearchByOthers);
        recyclerViewByType = findViewById(R.id.recyclerViewProp23);
        recyclerViewByTypeAndDate = findViewById(R.id.recyclerViewTypeAndDate);
        recyclerViewAllProp = (RecyclerView) findViewById(R.id.recyclerViewEstateProp);
        btnSearchByType = findViewById(R.id.btnSearchByDBType);
        btnSearchByDate = findViewById(R.id.btnSearchByDate);
        btnSearchByOthers = findViewById(R.id.btnSearchPropOthers);
        btnSearchByDate.setOnClickListener(this::searchByPropDate);
        btnSearchByOthers.setOnClickListener(this::searchByOthers);
        btnSearchByType.setOnClickListener(this::searchByPropType);

        picker=(DatePicker)findViewById(R.id._date_Prop);
        try {

            stringArrayList=dbHelper.getPropertyType();
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        propertiesArrayList = dbHelper.getAllProperties();
        propertyCount=dbHelper.getAllPropertyCount();

        spnPropSearchOthers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedOthers = spnPropSearchOthers.getSelectedItem().toString();
                selectedOthers = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(EstatePropAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAllProp.setLayoutManager(layoutManager);
        adapter = new PropertyListAdapter(EstatePropAct.this, propertiesArrayList);
        recyclerViewAllProp.setAdapter(adapter);
        //recyclerViewAllProp.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAllProp.getContext(),
                layoutManager.getOrientation());
        recyclerViewAllProp.addItemDecoration(dividerItemDecoration);
        try {
            if(propertiesArrayList.size() > 0){
                recyclerViewAllProp.setVisibility(View.VISIBLE);
                adapter = new PropertyListAdapter(EstatePropAct.this, propertiesArrayList);
                recyclerViewAllProp.setAdapter(adapter);

            }else {
                recyclerViewAllProp.setVisibility(View.GONE);
                Toast.makeText(this, "There is no Property for now.", Toast.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        if(propertyCount >0){
            txtPropCount.setText("Total Properties:"+ propertyCount);

        }else if(propertyCount ==0){
            txtPropCount.setText("No Property, yet!");

        }
        try {

            ArrayAdapter <String> stringArrayAdapter=new ArrayAdapter<String>(this, R.layout.spn_prop_layout, R.id.spnPropText, stringArrayList);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnPropSearchByDBType.setAdapter(stringArrayAdapter);

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        selectedPropType = spnPropSearchByDBType.getSelectedItem().toString();

        arraylist=dbHelper.getPropertyByType(selectedPropType);

        LinearLayoutManager layoutManagerType
                = new LinearLayoutManager(EstatePropAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewByType.setLayoutManager(layoutManagerType);
        adapter = new PropertyListAdapter(EstatePropAct.this, arraylist);
        recyclerViewByType.setAdapter(adapter);
        //recyclerViewByType.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecorationType = new DividerItemDecoration(recyclerViewByType.getContext(),
                layoutManager.getOrientation());
        recyclerViewByType.addItemDecoration(dividerItemDecorationType);


        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        String value_via_position =  stringArrayList.get(spnPropSearchByDBType.getSelectedItemPosition());
        String value_from_selected_item = (String) spnPropSearchByDBType.getSelectedItem();
        dateOfProperty = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

        editsearch = (SearchView) findViewById(R.id.search1);
        btnSearchByOthers = (AppCompatButton) findViewById(R.id.btnSearchPropOthers);
        editsearch.setOnQueryTextListener(this);

        arraylistByDateOnly=dbHelper.getPropertyAtDate(dateOfProperty);


        btnSearchByOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(dateOfProperty !=null){
                    if(selectedOthers.equalsIgnoreCase("Price")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndPrice(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Type")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndType(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Town")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndTown(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Capacity")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndCapacity(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("LGA")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndLGA(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("State")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndState(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Country")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndCountry(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Duration")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndPriceDuration(selectedOthers,dateOfProperty);

                    }

                    arraylistByDateOnly = dbHelper.getPropertyAtDate(dateOfProperty);


                }else {
                    if(selectedOthers.equalsIgnoreCase("Price")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtPrice(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Type")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtType(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Town")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtTown(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Capacity")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtCapacity(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("LGA")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtLGA(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("State")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtState(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Country")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtCountry(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Duration")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtPriceDuration(selectedOthers);

                    }
                }
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(EstatePropAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewByTypeAndDate.setLayoutManager(layoutManager);
                adapter = new PropertyListAdapter(EstatePropAct.this, arraylistByDateAndAnother);
                recyclerViewByTypeAndDate.setAdapter(adapter);
                //recyclerViewByTypeAndDate.setHasFixedSize(true);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewByTypeAndDate.getContext(),
                        layoutManager.getOrientation());
                recyclerViewByTypeAndDate.addItemDecoration(dividerItemDecoration);


            }
        });
        btnSearchByType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateOfProperty !=null){
                    if(selectedOthers.equalsIgnoreCase("Price")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndPrice(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Type")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndType(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Town")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndTown(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Capacity")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndCapacity(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("LGA")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndLGA(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("State")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndState(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Country")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndCountry(selectedOthers,dateOfProperty);

                    }
                    if(selectedOthers.equalsIgnoreCase("Duration")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtDateAndPriceDuration(selectedOthers,dateOfProperty);

                    }

                    arraylistByDateOnly = dbHelper.getPropertyAtDate(dateOfProperty);


                }else {
                    if(selectedOthers.equalsIgnoreCase("Price")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtPrice(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Type")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtType(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Town")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtTown(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Capacity")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtCapacity(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("LGA")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtLGA(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("State")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtState(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Country")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtCountry(selectedOthers);

                    }
                    if(selectedOthers.equalsIgnoreCase("Duration")){
                        arraylistByDateAndAnother = dbHelper.getPropertyAtPriceDuration(selectedOthers);

                    }
                }
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(EstatePropAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewByTypeAndDate.setLayoutManager(layoutManager);
                adapter = new PropertyListAdapter(EstatePropAct.this, arraylistByDateAndAnother);
                recyclerViewByTypeAndDate.setAdapter(adapter);
                //recyclerViewByTypeAndDate.setHasFixedSize(true);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewByTypeAndDate.getContext(),
                        layoutManager.getOrientation());
                recyclerViewByTypeAndDate.addItemDecoration(dividerItemDecoration);

            }
        });
        btnSearchByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(EstatePropAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewByTypeAndDate.setLayoutManager(layoutManagerC);
                //recyclerViewByTypeAndDate.setHasFixedSize(true);
                propertyAdapterDateOnly = new PropertyListAdapter(EstatePropAct.this, arraylistByDateOnly);
                recyclerViewByTypeAndDate.setAdapter(propertyAdapterDateOnly);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewByTypeAndDate.getContext(),
                        layoutManagerC.getOrientation());
                recyclerViewByTypeAndDate.addItemDecoration(dividerItemDecoration7);


            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addNewAward();
                Intent intent = new Intent(EstatePropAct.this, PropertyRegAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("ProfileID", profileID);
                startActivity(intent);
            }
        });
    }
    private void chooseDate() {
        dateOfProperty = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        //adapter.filter(text);
        return false;


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }

    public void searchByPropDate(View view) {
    }

    public void addNewProperty(View view) {
    }

    public void searchByOthers(View view) {
    }

    public void searchByPropType(View view) {
    }

    @Override
    public void onItemClick(Properties properties) {

    }
}