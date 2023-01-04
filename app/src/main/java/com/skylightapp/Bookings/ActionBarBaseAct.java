package com.skylightapp.Bookings;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.androidquery.callback.ImageOptions;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.R;

import org.apache.http.util.TextUtils;

import java.text.DecimalFormat;

public abstract class ActionBarBaseAct extends AppCompatActivity
        implements View.OnClickListener, AsyncListener, Response.ErrorListener {

    public ActionBar actionBar;
    private int mFragmentId = 0;
    private String mFragmentTag = null;
    public AppCompatImageButton btnNotification, btnActionMenu;
    public AppCompatTextView tvTitle;
    public AutoCompleteTextView etSource;
    public String currentFragment = null;
    public LinearLayout layoutDestination;
    public AppCompatImageButton imgClearDst;
    private LayoutInflater inflater=null;

    protected abstract boolean isValidate();
    private View customActionBarView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        // Custom Action Bar
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        try {
            inflater = (LayoutInflater) actionBar.getThemedContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(inflater !=null){
            customActionBarView = inflater.inflate(R.layout.custom_action_bar,
                    null);
        }
        if(customActionBarView !=null){
            layoutDestination = (LinearLayout) customActionBarView
                    .findViewById(R.id.layoutDestination);
            btnNotification = customActionBarView.findViewById(R.id.btnActionNotification);
            btnNotification.setOnClickListener(this);

            imgClearDst =  customActionBarView.findViewById(R.id.imgClearDst);

            tvTitle = (AppCompatTextView) customActionBarView
                    .findViewById(R.id.tvTitle);
            tvTitle.setOnClickListener(this);

            etSource = (AutoCompleteTextView) customActionBarView
                    .findViewById(R.id.etEnterSouce);

            btnActionMenu = customActionBarView.findViewById(R.id.btnActionMenu);

        }
        try {
            if(btnActionMenu !=null){
                btnActionMenu.setOnClickListener(this);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //setContentView(R.layout.custom_action_bar);



        try {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                            | ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setCustomView(customActionBarView,
                    new ActionBar.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent
                            .putExtra(
                                    Intent.EXTRA_TEXT,
                                    "I am using "
                                            + getString(R.string.app_name)
                                            + " App ! Why don't you try it out...\nInstall "
                                            + getString(R.string.app_name)
                                            + " now !\nhttps://play.google.com/store/apps/details?id="
                                            + getPackageName());
                    sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            getString(R.string.app_name) + " ");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent,
                            getString(R.string.share_this_app)));
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    public void setFbTag(String tag) {
        mFragmentId = 0;
        mFragmentTag = tag;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = null;

        if (mFragmentId > 0) {
            fragment = getSupportFragmentManager()
                    .findFragmentById(mFragmentId);
        } else if (mFragmentTag != null && !mFragmentTag.equalsIgnoreCase("")) {
            fragment = getSupportFragmentManager().findFragmentByTag(
                    mFragmentTag);
        }
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode,
                                       int fragmentId) {
        mFragmentId = fragmentId;
        mFragmentTag = null;
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Intent intent, int requestCode,
                                       String fragmentTag) {
        mFragmentTag = fragmentTag;
        mFragmentId = 0;
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Intent intent, int requestCode,
                                       int fragmentId, Bundle options) {

        mFragmentId = fragmentId;
        mFragmentTag = null;
        super.startActivityForResult(intent, requestCode, options);
    }

    public void startActivityForResult(Intent intent, int requestCode,
                                       String fragmentTag, Bundle options) {

        mFragmentTag = fragmentTag;
        mFragmentId = 0;
        super.startActivityForResult(intent, requestCode, options);
    }

    public void startIntentSenderForResult(Intent intent, int requestCode,
                                           String fragmentTag, Bundle options) {

        mFragmentTag = fragmentTag;
        mFragmentId = 0;
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    @Deprecated
    public void startIntentSenderForResult(IntentSender intent,
                                           int requestCode, Intent fillInIntent, int flagsMask,
                                           int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent,
                flagsMask, flagsValues, extraFlags);
    }

    public void startIntentSenderForResult(IntentSender intent,
                                           int requestCode, Intent fillInIntent, int flagsMask,
                                           int flagsValues, int extraFlags, String fragmentTag)
            throws IntentSender.SendIntentException {

        mFragmentTag = fragmentTag;
        mFragmentId = 0;
        super.startIntentSenderForResult(intent, requestCode, fillInIntent,
                flagsMask, flagsValues, extraFlags);
    }

    @Override
    @Deprecated
    public void startIntentSenderForResult(IntentSender intent,
                                           int requestCode, Intent fillInIntent, int flagsMask,
                                           int flagsValues, int extraFlags, Bundle options)
            throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent,
                flagsMask, flagsValues, extraFlags, options);
    }

    public void startIntentSenderForResult(IntentSender intent,
                                           int requestCode, Intent fillInIntent, int flagsMask,
                                           int flagsValues, int extraFlags, Bundle options, String fragmentTag)
            throws IntentSender.SendIntentException {
        mFragmentTag = fragmentTag;
        mFragmentId = 0;
        super.startIntentSenderForResult(intent, requestCode, fillInIntent,
                flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode,
                                       Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_left, R.anim.slide_out_right);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_frame, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            boolean isAnimate, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (isAnimate)
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.fab_slide_in_from_left,
                    R.anim.slide_out_right);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_frame, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void addFragmentWithStateLoss(Fragment fragment,
                                         boolean addToBackStack, String tag) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_frame, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void removeAllFragment(Fragment replaceFragment,
                                  boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        manager.popBackStackImmediate(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_frame, replaceFragment);
        ft.commit();
    }

    public void clearBackStackImmidiate() {

        FragmentManager manager = getSupportFragmentManager();

        manager.popBackStackImmediate(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager
                    .getBackStackEntryAt(0);
            manager.popBackStack(first.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.fab_slide_in_from_left, R.anim.slide_out_right);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    protected ImageOptions getAqueryOption() {
        ImageOptions options = new ImageOptions();
        options.targetWidth = 200;
        options.memCache = true;
        options.fallback = R.drawable.ic_admin_user;
        options.fileCache = true;
        return options;
    }

    public void openExitDialog() {
        final Dialog mDialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.exit_layout);
        mDialog.findViewById(R.id.tvExitOk).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                        finish();
                        overridePendingTransition(R.anim.fab_slide_in_from_left,
                                R.anim.slide_out_right);
                    }
                });
        mDialog.findViewById(R.id.tvExitCancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                    }
                });
        mDialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void showBillDialog(String timeCost, String total, String distCost,
                               String basePrice, String time, String distance, String promoBouns,
                               String referralBouns, String btnTitle) {
        final Dialog mDialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.bill_layout);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        DecimalFormat perHourFormat = new DecimalFormat("0.0");
        //
        String basePricetmp = String.valueOf(decimalFormat.format(Double
                .parseDouble(basePrice)));
        String totalTmp = String.valueOf(decimalFormat.format(Double
                .parseDouble(total)));
        String distCostTmp = String.valueOf(decimalFormat.format(Double
                .parseDouble(distCost)));
        String timeCostTmp = String.valueOf(decimalFormat.format(Double
                .parseDouble(timeCost)));

        AppLog.Log("Distacne", distance);
        AppLog.Log("Time", time);

        ((AppCompatTextView) mDialog.findViewById(R.id.tvBasePrice)).setText(basePrice);
        if (Double.parseDouble(distCost) != 0) {
            ((AppCompatTextView) mDialog.findViewById(R.id.tvBillDistancePerMile))
                    .setText(String.valueOf(perHourFormat.format((Double
                            .parseDouble(distCost) / Double
                            .parseDouble(distance))))
                            + getResources().getString(
                            R.string.cost_per_mile));
        } else {
            ((AppCompatTextView) mDialog.findViewById(R.id.tvBillDistancePerMile))
                    .setText(String.valueOf(perHourFormat.format(0.00))
                            + getResources().getString(
                            R.string.cost_per_mile));
        }
        if (Double.parseDouble(timeCost) != 0) {
            ((AppCompatTextView) mDialog.findViewById(R.id.tvBillTimePerHour))
                    .setText(String.valueOf(perHourFormat.format((Double
                            .parseDouble(timeCost) / Double.parseDouble(time))))
                            + getResources().getString(
                            R.string.cost_per_hour));
        } else {
            ((AppCompatTextView) mDialog.findViewById(R.id.tvBillTimePerHour))
                    .setText(String.valueOf(perHourFormat.format((0.00)))
                            + getResources().getString(
                            R.string.cost_per_hour));
        }
        ((AppCompatTextView) mDialog.findViewById(R.id.tvDis1)).setText(distCostTmp);

        ((AppCompatTextView) mDialog.findViewById(R.id.tvTime1)).setText(timeCostTmp);

        ((AppCompatTextView) mDialog.findViewById(R.id.tvTotal1)).setText(totalTmp);
        ((AppCompatTextView) mDialog.findViewById(R.id.tvPromoBonus))
                .setText(decimalFormat.format(Double.parseDouble(promoBouns)));
        ((AppCompatTextView) mDialog.findViewById(R.id.tvReferralBonus))
                .setText(decimalFormat.format(Double.parseDouble(referralBouns)));

        AppCompatButton btnConfirm = (AppCompatButton) mDialog
                .findViewById(R.id.btnBillDialogClose);
        if (!TextUtils.isEmpty(btnTitle)) {
            btnConfirm.setText(btnTitle);
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.setCancelable(true);
        mDialog.show();
    }

    public void setTitle(String str) {
        try {
            tvTitle.setText(str);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void setIconMenu(int img) {
        try {
            btnActionMenu.setImageResource(img);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void setIcon(int img) {
        try {
            btnNotification.setImageResource(img);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void goToMainActivity() {
        Intent i = new Intent(this, NewCustomerDrawer.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();
    }
    public void clearAll() {
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }
}