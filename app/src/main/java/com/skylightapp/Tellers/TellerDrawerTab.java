package com.skylightapp.Tellers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.SimpleFantasyListener;
import com.github.mzule.fantasyslide.Transformer;
import com.skylightapp.AllCusPackTab;
import com.skylightapp.Customers.SOTab;
import com.skylightapp.LoginActivity;
import com.skylightapp.PasswordRecoveryActivity;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;
import com.skylightapp.SkylightSliderAct;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineOverview;

public class TellerDrawerTab extends AppCompatActivity {
    private FantasyDrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_drawer_tab);
        drawerLayout = (FantasyDrawerLayout) findViewById(R.id.drawerLayout);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.BLUE);
        //getSupportActionBar().setHomeAsUpIndicator(indicator);

        setTransformer();
        // setListener();

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });
    }

    private void setListener() {
        final TextView tipView = (TextView) findViewById(R.id.tipView);
        SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
        leftSideBar.setFantasyListener(new SimpleFantasyListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                tipView.setVisibility(View.VISIBLE);
                if (view instanceof TextView) {
                    tipView.setText(String.format("%s at %d", ((TextView) view).getText().toString(), index));
                } else if (view != null && view.getId() == R.id.userInfo) {
                    tipView.setText(String.format(" at %d", index));
                } else {
                    tipView.setText(null);
                }
                return false;

            }

            @Override
            public boolean onSelect(View view, int index) {
                tipView.setVisibility(View.INVISIBLE);
                Toast.makeText(TellerDrawerTab.this, String.format("%d selected", index), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onCancel() {
                tipView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setTransformer() {
        final float spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        SideBar rightSideBar = (SideBar) findViewById(R.id.rightSideBar);
        rightSideBar.setTransformer(new Transformer() {
            private View lastHoverView;

            @Override
            public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
                boolean hovered = itemView.isPressed();
                if (hovered && lastHoverView != itemView) {
                    animateIn(itemView);
                    animateOut(lastHoverView);
                    lastHoverView = itemView;
                }
            }

            private void animateOut(View view) {
                if (view == null) {
                    return;
                }
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -spacing, 0);
                translationX.setDuration(200);
                translationX.start();
            }

            private void animateIn(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -spacing);
                translationX.setDuration(200);
                translationX.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        if (id == R.id.nav_app_teller2) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        if (id == R.id.nav_app_teller2) {
            Intent mainIntent = new Intent(this, TellerDrawerTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        }
        if (id == R.id.teller_pack2) {

            Intent mainIntent = new Intent(this, SkylightSliderAct.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }

        if (id == R.id.my_cusP) {
            Intent mainIntent = new Intent(this, MyCustPackTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
        if (id == R.id.allCusP) {
            Intent mainIntent = new Intent(this, AllCusPackTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }


        if (id == R.id.myTimelines) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameCont_Teller, new UserTimeLineOverview())
                    .commit();

        }
        if (id == R.id.pp) {
            Intent mainIntent = new Intent(this, PrivacyPolicy_Web.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
        if (id == R.id.newCust) {
            Intent mainIntent = new Intent(this, SignUpAct.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
        if (id == R.id.standingOrders) {
            Intent mainIntent = new Intent(this, SOTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }

        if (id == R.id.teller_all_web3) {
            Intent mainIntent = new Intent(this, TellerWebTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }


        if (id == R.id.teller_pref) {
            Intent mainIntent = new Intent(this, UserPrefActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
        if (id == R.id.change_pswd) {
            Intent mainIntent = new Intent(this, PasswordRecoveryActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
        if (id == R.id.log11) {
            Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);

    }

    public void onClick(View view) {
        if (view instanceof TextView) {
            String title = ((TextView) view).getText().toString();
            if (title.startsWith("Teller")) {
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
            } else {
                startActivity(UniversalAct.newIntent(this, title));
            }
        } else if (view.getId() == R.id.userInfo) {
            startActivity(UniversalAct.newIntent(this, "Teller Dashboard"));
        }

    }

    public void cusPTab(View view) {
        Intent mainIntent = new Intent(this, MyCustPackTab.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void SkylightPacks(View view) {
        Intent mainIntent = new Intent(this, SkylightSliderAct.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void AllCusTab(View view) {
        Intent mainIntent = new Intent(this, AllCusPackTab.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void PayNow(View view) {
        Intent mainIntent = new Intent(this, PayForMyCusAct.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void goSO(View view) {
        Intent mainIntent = new Intent(this, SOTab.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void goLoanTab(View view) {
        Intent mainIntent = new Intent(this, LoanRepaymentTab.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void regNewCus(View view) {
        Intent mainIntent = new Intent(this, SignUpAct.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void tellerWeb(View view) {
        Intent mainIntent = new Intent(this, TellerWebTab.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }


    public void myPrefTeller(View view) {
        Intent prefIntent = new Intent(this, UserPrefActivity.class);
        prefIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(prefIntent);
    }

    public void skylightPrivacyPolicy(View view) {
        Intent pPIntent = new Intent(this, PrivacyPolicy_Web.class);
        pPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pPIntent);
    }
}