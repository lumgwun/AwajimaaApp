package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.R;

public class BizRegulOffice extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_biz_regul_office);
        chipNavigationBar = findViewById(R.id.reg_nav_bar);

        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.cHome:
                                Intent myIntent = new Intent(BizRegulOffice.this, BizRegulOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.cTimeLine:

                                Intent chat = new Intent(BizRegulOffice.this, MyTimelineAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.cGeneralShop:

                                Intent shop = new Intent(BizRegulOffice.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.cPackageT:

                                Intent pIntent = new Intent(BizRegulOffice.this, PackListTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.cSupport:
                                Intent helpIntent = new Intent(BizRegulOffice.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(helpIntent);
                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });

    }
}