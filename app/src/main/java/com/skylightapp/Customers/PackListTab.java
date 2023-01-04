package com.skylightapp.Customers;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

public class PackListTab extends TabActivity {
    FloatingActionButton floatingActionButton;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    Profile userProfile;
    long profileUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pack_list_tab);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_TabListPacks);
        if (userProfile != null) {
            profileUID = userProfile.getPID();

        }
        Resources resources = getResources();
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(getLocalActivityManager());

        TabHost.TabSpec oldPackTab = tabHost.newTabSpec("Packs by Cus");
        oldPackTab.setIndicator("Packs Overview", getResources().getDrawable(R.drawable.ic_admin_panel));
        Intent customerIntent = new Intent(this, CusPackageList.class);
        oldPackTab.setContent(customerIntent);

        TabHost.TabSpec newPackTab = tabHost.newTabSpec("Packs by Profile");
        newPackTab.setIndicator("Pack Details", getResources().getDrawable(R.drawable.ic_add_business));
        Intent profileIntent = new Intent(this, ProfilePackDetails.class);
        newPackTab.setContent(profileIntent);

        tabHost.addTab(newPackTab);
        tabHost.addTab(oldPackTab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });

    }
    public  void helpHome(){
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Bundle bundle = new Bundle();
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }

        bundle.putLong("ProfileID", profileUID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("PROFILE_ID", profileUID);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}