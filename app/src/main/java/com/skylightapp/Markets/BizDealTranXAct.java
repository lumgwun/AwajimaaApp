package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.MarketTranXDAO;
import com.skylightapp.MarketClasses.BusinessOthers;
import com.skylightapp.R;

public class BizDealTranXAct extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    LinearLayout share_layout;
    ImageView sidebar_icon;
    Intent intent;
    int cus_id;
    int biz_ID;
    ArrayList<String> transaction_amount, transaction_time, transaction_remarks, transaction_sender_id, transaction_id;
    Cursor cursor;
    RecyclerView transaction_chat_recycle;
    BDTranxChatAdapter chatAdapter;
    MaterialCardView transaction_debit, transaction_credit;
    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile, customerProfile, lastProfileUsed;
    String selectedCountry, selectedBank, bankName, bankNumber, officePref, userNamePref;
    private static final String PREF_NAME = "skylight";
    private BusinessOthers businessOthers;
    private Profile businessOwnerProfile;
    TextView transaction_date, save_debit, transaction_name, transaction_balance, save_credit, friend_name, error_msg_transaction_amount, error_msg_transaction_date, error_msg_transaction_name;
    String transaction_date_text, transaction_name_text, transaction_balance_text, Friend_name;
    SharedPreferences userPreferences;
    private Bundle cusBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_deal_tran_xact);
        setTitle("Business Transaction");
        cusBundle= new Bundle();
        businessOwnerProfile= new Profile();
        sidebar_icon = findViewById(R.id.sidebar_icon);
        transaction_chat_recycle = findViewById(R.id.transaction_chat_recycle);
        transaction_debit = findViewById(R.id.transaction_debit);
        transaction_credit = findViewById(R.id.transaction_credit);
        friend_name = findViewById(R.id.friend_name);
        share_layout = findViewById(R.id.share_layout);
        businessOthers = new BusinessOthers();
        gson= new Gson();
        gson1= new Gson();

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        businessOwnerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        businessOthers = gson1.fromJson(json1, BusinessOthers.class);
        biz_ID = userPreferences.getInt("BUSINESS_ID33",0);
        intent = getIntent();
        cusBundle=getIntent().getExtras();
        cus_id = intent.getIntExtra("Friend_id",0);
        if(cusBundle !=null){
            cus_id=cusBundle.getInt("MARKET_TX_RECEIVER_ID");

        }

        MarketTranXDAO myDB = new MarketTranXDAO(this);
        Cursor cursor = myDB.get_user_details(cus_id);
        while (cursor.moveToNext()) {
            Friend_name = cursor.getString(6);
        }

        friend_name.setText(Friend_name);

        transaction_sender_id = new ArrayList<>();
        transaction_amount = new ArrayList<>();
        transaction_remarks = new ArrayList<>();
        transaction_time = new ArrayList<>();
        transaction_id = new ArrayList<>();

        transaction_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BizDealTranXAct.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_debit_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                transaction_balance = bottomSheetDialog.findViewById(R.id.transaction_amount);
                transaction_name = bottomSheetDialog.findViewById(R.id.transaction_name);
                transaction_date = bottomSheetDialog.findViewById(R.id.transaction_date);
                error_msg_transaction_amount = bottomSheetDialog.findViewById(R.id.error_msg_transaction_amount);
                error_msg_transaction_date = bottomSheetDialog.findViewById(R.id.error_msg_transaction_date);
                error_msg_transaction_name = bottomSheetDialog.findViewById(R.id.error_msg_transaction_name);
                save_debit = bottomSheetDialog.findViewById(R.id.save_debit);

                transaction_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(BizDealTranXAct.this, BizDealTranXAct.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });

                save_debit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        transaction_balance_text = transaction_balance.getText().toString();
                        transaction_name_text = transaction_name.getText().toString();
                        transaction_date_text = transaction_date.getText().toString();

                        int count1 = 1, count2 = 1, count3 = 1;

                        error_msg_transaction_name.setVisibility(View.GONE);
                        error_msg_transaction_amount.setVisibility(View.GONE);
                        error_msg_transaction_date.setVisibility(View.GONE);

                        if (transaction_name_text.isEmpty()) {
                            count1 = 0;
                            error_msg_transaction_name.setVisibility(View.VISIBLE);
                            error_msg_transaction_name.setText("This is required");
                        }
                        if (transaction_balance_text.isEmpty()) {
                            count2 = 0;
                            error_msg_transaction_amount.setVisibility(View.VISIBLE);
                            error_msg_transaction_amount.setText("This is required");
                        }
                        if (transaction_date_text.isEmpty()) {
                            count3 = 0;
                            error_msg_transaction_date.setVisibility(View.VISIBLE);
                            error_msg_transaction_date.setText("This is required");
                        }
                        if (count1 == 1 && count2 == 1 && count3 == 1) {
                            MarketTranXDAO myDB = new MarketTranXDAO(BizDealTranXAct.this);
                            String tost_message = null;
                            if (myDB.storeNewDebitMarketTranx(biz_ID, cus_id, transaction_balance_text, transaction_name_text, transaction_date_text)) {
                                tost_message = "Transaction Added";
                            } else {
                                tost_message = "Something went wrong";
                            }
                            transaction_sender_id = new ArrayList<>();
                            transaction_amount = new ArrayList<>();
                            transaction_remarks = new ArrayList<>();
                            transaction_time = new ArrayList<>();
                            transaction_id = new ArrayList<>();
                            Fetch_Transaction(biz_ID, cus_id);
                            chatAdapter = new BDTranxChatAdapter(BizDealTranXAct.this, biz_ID, transaction_sender_id, transaction_amount, transaction_remarks, transaction_time, transaction_id);
                            transaction_chat_recycle.setAdapter(chatAdapter);
                            transaction_chat_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            transaction_chat_recycle.smoothScrollToPosition(transaction_amount.size());
                            Toast.makeText(BizDealTranXAct.this, tost_message, Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.hide();
                        }
                    }
                });
                bottomSheetDialog.show();
            }
        });


        transaction_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BizDealTranXAct.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_credit_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                transaction_balance = bottomSheetDialog.findViewById(R.id.transaction_amount);
                transaction_name = bottomSheetDialog.findViewById(R.id.transaction_name);
                transaction_date = bottomSheetDialog.findViewById(R.id.transaction_date);
                error_msg_transaction_amount = bottomSheetDialog.findViewById(R.id.error_msg_transaction_amount);
                error_msg_transaction_date = bottomSheetDialog.findViewById(R.id.error_msg_transaction_date);
                error_msg_transaction_name = bottomSheetDialog.findViewById(R.id.error_msg_transaction_name);
                save_credit = bottomSheetDialog.findViewById(R.id.save_credit);

                transaction_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(BizDealTranXAct.this, BizDealTranXAct.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });

                save_credit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        transaction_balance_text = transaction_balance.getText().toString();
                        transaction_name_text = transaction_name.getText().toString();
                        transaction_date_text = transaction_date.getText().toString();

                        int count1 = 1, count2 = 1, count3 = 1;

                        error_msg_transaction_name.setText("");
                        error_msg_transaction_amount.setText("");
                        error_msg_transaction_date.setText("");

                        if (transaction_name_text.isEmpty()) {
                            count1 = 0;
                            error_msg_transaction_name.setText("This is required");
                        }
                        if (transaction_balance_text.isEmpty()) {
                            count2 = 0;
                            error_msg_transaction_amount.setText("This is required");
                        }
                        if (transaction_date_text.isEmpty()) {
                            count3 = 0;
                            error_msg_transaction_date.setText("This is required");
                        }
                        if (count1 == 1 && count2 == 1 && count3 == 1) {
                            MarketTranXDAO myDB = new MarketTranXDAO(BizDealTranXAct.this);
                            String tost_message = null;
                            if (myDB.storeNewCreditTransaction(biz_ID, cus_id, transaction_balance_text, transaction_name_text, transaction_date_text)) {
                                tost_message = "Transaction Added";
                            } else {
                                tost_message = "Something went wrong";
                            }
                            transaction_sender_id = new ArrayList<>();
                            transaction_amount = new ArrayList<>();
                            transaction_remarks = new ArrayList<>();
                            transaction_time = new ArrayList<>();
                            transaction_id = new ArrayList<>();
                            Fetch_Transaction(biz_ID, cus_id);
                            chatAdapter = new BDTranxChatAdapter(BizDealTranXAct.this, biz_ID, transaction_sender_id, transaction_amount, transaction_remarks, transaction_time, transaction_id);
                            transaction_chat_recycle.setAdapter(chatAdapter);
                            transaction_chat_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            transaction_chat_recycle.smoothScrollToPosition(transaction_amount.size());
                            Toast.makeText(BizDealTranXAct.this, tost_message, Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.hide();
                        }
                    }
                });
                bottomSheetDialog.show();
            }
        });


        Fetch_Transaction(biz_ID, cus_id);
        chatAdapter = new BDTranxChatAdapter(getApplicationContext(), biz_ID, transaction_sender_id, transaction_amount, transaction_remarks, transaction_time, transaction_id);
        transaction_chat_recycle.setAdapter(chatAdapter);
        transaction_chat_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        transaction_chat_recycle.smoothScrollToPosition(transaction_amount.size());

    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }

    public void Fetch_Transaction(int userid, int friendid) {
        Date oneWayTripDate = null;
        MarketTranXDAO myDB = new MarketTranXDAO(this);
        cursor = myDB.user_friend_transaction(userid, friendid);
        while (cursor.moveToNext()) {
            transaction_sender_id.add(cursor.getString(0));
            transaction_amount.add(cursor.getString(1));
            transaction_remarks.add(cursor.getString(2));
            String date = cursor.getString(3);
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            try {
                oneWayTripDate = input.parse(date);  // parse input
            } catch (ParseException e) {
                e.printStackTrace();
            }
            transaction_time.add(output.format(oneWayTripDate));
            transaction_id.add(cursor.getString(4));
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String month_name = new DateFormatSymbols().getMonths()[month];
        String temp_date = String.valueOf(day) + " - " + month_name.substring(0, 3) + " - " + String.valueOf(year);
        transaction_date.setText(temp_date);

    }

    public void share_transaction(View view) {
        int transaction_id;
        String transaction_sender_id = null;
        String transaction_receiver_id = null;
        String transaction_amount_text = null;
        String transaction_remarks_text = null;
        String transaction_date_text = null;
        String sender_id;
        String customer_phone_number_alert_text = null;
        final String[] user_name = new String[1];
        final String[] user_business_name = new String[1];
        Bitmap customer_image_alert_text = null;
        final ImageView close_alert, transactionamountsymbol, customer_image_alert, share_icon;
        TextView transaction_amount, transaction_remarks, transaction_time, customer_phone_number_alert;
        final MaterialCardView alert_dialog;
        final LinearLayout share_layout;

        LinearLayout card = (LinearLayout) view;
        transaction_id = Integer.parseInt(card.getTag().toString());
        final MarketTranXDAO myDB = new MarketTranXDAO(this);
        Cursor cursor = myDB.get_transaction_details(transaction_id);

        while (cursor.moveToNext()) {
            transaction_sender_id = cursor.getString(1);
            transaction_receiver_id = cursor.getString(2);
            transaction_amount_text = cursor.getString(3);
            transaction_remarks_text = cursor.getString(5);
            transaction_date_text = cursor.getString(7);
        }

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_transaction_alert);

        alert_dialog = dialog.findViewById(R.id.alert_dialog);
        close_alert = dialog.findViewById(R.id.close_alert);
        customer_image_alert = dialog.findViewById(R.id.customer_image);
        customer_phone_number_alert = dialog.findViewById(R.id.customer_contact_number);
        transactionamountsymbol = dialog.findViewById(R.id.transactionamountsymbol);
        transaction_amount = dialog.findViewById(R.id.transaction_amount);
        transaction_remarks = dialog.findViewById(R.id.transaction_remarks);
        transaction_time = dialog.findViewById(R.id.transaction_time);
        share_icon = dialog.findViewById(R.id.share_icon);
        share_layout = dialog.findViewById(R.id.share_layout);

        if (transaction_sender_id.compareTo(String.valueOf(biz_ID)) == 0) {
            sender_id = transaction_receiver_id;
            transaction_amount.setText("- " + transaction_amount_text);
            transaction_amount.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
            transactionamountsymbol.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.debit_fg));
            close_alert.setImageResource(R.drawable.alertbox_cross_icon_debit);
        } else {
            sender_id = transaction_sender_id;
            transaction_amount.setText("+ " + transaction_amount_text);
            transaction_amount.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
            transactionamountsymbol.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.credit_fg));
            close_alert.setImageResource(R.drawable.alertbox_cross_icon_credit);
            share_icon.setImageResource(R.drawable.ic_share);
        }

        Cursor cursor1 = myDB.get_user_details(Integer.parseInt(sender_id));

        while (cursor1.moveToNext()) {
            customer_phone_number_alert_text = "+91-" + cursor1.getString(0).substring(2);
            customer_image_alert_text = BitmapFactory.decodeByteArray(cursor1.getBlob(4), 0, cursor1.getBlob(4).length);
        }

        transaction_remarks.setText(transaction_remarks_text);
        transaction_time.setText(transaction_date_text);
        customer_phone_number_alert.setText(customer_phone_number_alert_text);
        customer_image_alert.setImageBitmap(customer_image_alert_text);

        close_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                share_layout.setVisibility(View.INVISIBLE);
                close_alert.setVisibility(View.INVISIBLE);
                Bitmap bitmap = Bitmap.createBitmap(alert_dialog.getWidth(), alert_dialog.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                alert_dialog.draw(canvas);
                Cursor cursor2 = myDB.get_user_details(biz_ID);
                while (cursor2.moveToNext()) {
                    user_name[0] = cursor2.getString(1);
                    user_business_name[0] = cursor2.getString(2);
                }

                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + user_name[0] + "_" + user_business_name[0] + ".jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");
                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
