package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.GsonBuilder;
import com.skylightapp.Bookings.BookingsMainTab;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.LoanDetailsActivity;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MoneyTxDetailAct;
import com.skylightapp.NotificationAct;
import com.skylightapp.PackageDetailsActivity;
import com.skylightapp.PlanPaymentActivity;
import com.skylightapp.R;
import com.skylightapp.Markets.CashOutDetailsActivity;
import com.skylightapp.Transactions.GOTVDetailsActivity;
import com.skylightapp.Transactions.StarTimeDetailsActivity;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.models.MarkerOption;
import com.teliver.sdk.models.NotificationData;
import com.teliver.sdk.models.TConstants;
import com.teliver.sdk.models.TrackingBuilder;

import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_ID;
import static com.skylightapp.Transactions.BillModel.BILL_ID;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class FirebaseMessS extends FirebaseMessagingService {

    private static final String TAG = FirebaseMessS.class.getSimpleName();

    private static int notificationId = 0;
    private PrefManager prefManager;

    private static final String POST_ID_KEY = "postId";
    private static final String MARKETER_ID_KEY = "marketerId";
    private static final String LOAN_ID_KEY = "loanId";
    private static final String MONEY_TRANSFER_ID_KEY = "moneyTransferId";
    private static final String CUSTOMER_ID_KEY = "customerId";
    private static final String NEW_SAVINGS_ID_KEY = "savingsId";
    private static final String PACKAGE_ID_KEY = "packageId";
    private static final String CASHOUT_ID_KEY = "cashOutId";
    private static final String NEW_PLAN_ID_KEY = "planId";
    private static final String GOTV_ID_KEY = "goTvId";
    private static final String DSTV_ID_KEY = "dstvId";
    private static final String SO_KEY = "so";

    private static final String SUPPORT_KEY = "messages";


    private static final String NEPA_BILL_ID_KEY = "nepaBillId";
    private static final String STARTIMES_ID_KEY = "starTimesId";
    private static final String DATA_ID_KEY = "dataSubId";
    private static final String AUTHOR_ID_KEY = "authorId";
    private static final String ACTION_TYPE_KEY = "actionType";
    private static final String TITLE_KEY = "title";
    private static final String BODY_KEY = "body";
    private static final String ICON_KEY = "icon";
    private static final String ACTION_TYPE_NEW_LIKE = "new_like";
    private static final String ACTION_TYPE_NEW_COMMENT = "new_comment";
    private static final String ACTION_TYPE_NEW_POST = "new_post";
    private static final String CRIME_ID_KEY = "crimeId";
    private static final String EMERG_ID_KEY = "EmergId";
    private static final String SPILLAGE_ID_KEY = "spillageId";
    private static final String NEW_USER_SIGNUP_KEY = "New User";
    private static final String NEW_USER_SIGNUP_CODE = "New User Code";
    private static final String NEW_BIZ_KEY = "New Biz Sign Up";
    private static final String NEW_CRIME_RES = "New Crime Resp";
    private static final String NEW_EMERG_RES = "New Emerg Resp";
    private static final String NEW_CLIMATE_CHANGE = "New Climate Change Report";
    Context context;

    //@Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData() != null && remoteMessage.getData().get(ACTION_TYPE_KEY) != null) {
            handleRemoteMessage(remoteMessage);
        } else {
            LogUtil.logError(TAG, "onMessageReceived()", new RuntimeException("FCM remoteMessage doesn't contains Action Type"));
        }
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            handleRemoteMessage(remoteMessage);
        }

    }

    private void showNotification(String title, String body) {

        Intent notificationIntent = new Intent(this, NewCustomerDrawer.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //notificationIntent.putExtra(TConstants.DOTS_OBJ, builder.build());
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this);
        notBuilder.setSmallIcon(R.mipmap.ls_logo_round);
        notBuilder.setContentTitle(title);
        notBuilder.setContentText(body);
        notBuilder.setAutoCancel(true);
        notBuilder.setOnlyAlertOnce(true);
        notBuilder.addAction(R.drawable.awajima_logo, this.getString(R.string.txt_start_tracking), pendingIntent);
        Notification notification = notBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(12, notification);

    }

    private void handleRemoteMessage(RemoteMessage remoteMessage) {
        String receivedActionType = remoteMessage.getData().get(ACTION_TYPE_KEY);
        LogUtil.logDebug(TAG, "Message Notification Action Type: " + receivedActionType);
        if(receivedActionType !=null){
            if(receivedActionType.equalsIgnoreCase("")){

                Map<String, String> pushData = remoteMessage.getData();
                final NotificationData data = new GsonBuilder().create().fromJson(pushData.get("description"), NotificationData.class);
                //prefManager = new PrefManager(this);
                //prefManager.saveTrackingId("welcome Message", data.getMessage());
                Intent intent = new Intent(this, NewCustomerDrawer.class);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data.getPayload());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String status = null;
                try {
                    if (jsonObject != null) {
                        status = jsonObject.getString("status");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("msg", status);
                intent.setAction("message");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            }

        }
        try {
            if (Teliver.isTeliverPush(remoteMessage)) {
                Map<String, String> pushData = remoteMessage.getData();
                final NotificationData data = new GsonBuilder().create().fromJson(pushData.get("description"), NotificationData.class);
                prefManager = new PrefManager(this);
                prefManager.saveTrackingId("TRACKING_ID", data.getTrackingID());
                Intent intent = new Intent(this, BookingsMainTab.class);
                JSONObject jsonObject = new JSONObject(data.getPayload());
                String status = jsonObject.getString("status");
                intent.putExtra("msg", status);
                intent.setAction("message");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                if (data.getMessage().equalsIgnoreCase("2"))
                    sendPushNotification(data, getString(R.string.txtDriverIsHere));
                else if (data.getMessage().equalsIgnoreCase("1"))
                    sendPushNotification(data, getString(R.string.youHaveACus));
                else if (data.getMessage().equalsIgnoreCase("3"))
                    sendPushNotification(data, getString(R.string.arrived));
                else sendPushNotification(data,data.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Teliver.isTeliverPush(remoteMessage)) {
                Map<String, String> pushData = remoteMessage.getData();
                NotificationData data = new GsonBuilder().create().fromJson(pushData.get("description"), NotificationData.class);
                if (data.getCommand().equals(TConstants.CMD_TRIP_START)) {

                    TrackingBuilder builder = new TrackingBuilder(new MarkerOption(data.getTrackingID()));
                    Intent notificationIntent = new Intent(this, Teliver.getMap());
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    notificationIntent.putExtra(TConstants.DOTS_OBJ, builder.build());
                    @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationManager notificationManager = (NotificationManager)
                            this.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this);
                    notBuilder.setSmallIcon(R.mipmap.ls_logo_round);
                    notBuilder.setContentTitle(this.getString(R.string.app_name));
                    notBuilder.setContentText(data.getMessage());
                    notBuilder.setAutoCancel(true);
                    notBuilder.setOnlyAlertOnce(true);
                    notBuilder.addAction(R.drawable.awajima_logo, this.getString(R.string.txt_start_tracking), pendingIntent);
                    Notification notification = notBuilder.build();
                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(12, notification);
                } else if (data.getCommand().equals(TConstants.CMD_EVENT_PUSH)) {
                    //The Event Push Area
                }
            } else {
                //The push is not for us, You can handle it.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (receivedActionType != null) {
            switch (receivedActionType) {
                case NEPA_BILL_ID_KEY:
                    //parseNEPABILL(remoteMessage);
                    break;
                case STARTIMES_ID_KEY:
                    //parseStarTimes(Channel.NEW_STARTTIMES_SUB, remoteMessage);
                    break;
                case DATA_ID_KEY:
                    //parseData(Channel.NEW_DATA_SUB, remoteMessage);
                    break;
                case LOAN_ID_KEY:
                    parseLoan(Channel.NEW_LOAN, remoteMessage);
                    break;
                case MONEY_TRANSFER_ID_KEY:
                    parseMoneyTransfer(Channel.NEW_AFRICA_MONEY_TRANSFER, remoteMessage);
                    break;
                case CUSTOMER_ID_KEY:
                    parseCashOut(Channel.NEW_STANDINGORDER, remoteMessage);
                    break;
                case NEW_SAVINGS_ID_KEY:
                    parseSavings(Channel.NEW_STANDINGORDER, remoteMessage);
                    break;
                case PACKAGE_ID_KEY:
                    parsePackage(Channel.NEW_PACKAGE, remoteMessage);
                    break;
                case CASHOUT_ID_KEY:
                    parseCashOut(Channel.NEW_CASHOUT, remoteMessage);
                    break;
                case NEW_PLAN_ID_KEY:
                    parsePlan(Channel.NEW_PLAN_REQUEST, remoteMessage);
                    break;
                case GOTV_ID_KEY:
                    parseGoTv(Channel.NEW_GOTV_SUB, remoteMessage);
                    break;
                case DSTV_ID_KEY:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;
                case SUPPORT_KEY:
                    //parseSupportM(Channel.NEW_SUPPORT_MESSAGE, remoteMessage);
                    break;
                case SO_KEY:
                    //parseSO(Channel.NEW_STANDINGORDER, remoteMessage);
                    break;
                case CRIME_ID_KEY:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;
                case EMERG_ID_KEY:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;
                case SPILLAGE_ID_KEY:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;
                case NEW_USER_SIGNUP_KEY:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;
                case NEW_USER_SIGNUP_CODE:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;
                case NEW_BIZ_KEY:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;

                case NEW_CRIME_RES:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;

                case NEW_EMERG_RES:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;

                case NEW_CLIMATE_CHANGE:
                    //parseDSTV(Channel.NEW_DSTV_SUB, remoteMessage);
                    break;

            }
        }
    }
    private void sendPushNotification(NotificationData data, String pushMessage) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle("Awajima App");
        notification.setContentText(pushMessage);
        notification.setSmallIcon(R.mipmap.ls_logo_round);

        notification.setStyle(new NotificationCompat.BigTextStyle().bigText(pushMessage).setBigContentTitle("Awajima Booking"));
        Intent intent = new Intent(this, BookingsMainTab.class);
        intent.putExtra("msg", data.getMessage());
        intent.putExtra("tracking_id", data.getTrackingID());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(pendingIntent);
        notification.setAutoCancel(true);
        notification.setPriority(Notification.PRIORITY_MAX);
        notification.setDefaults(Notification.DEFAULT_ALL);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
    }

    enum Channel {

        NEW_SPILLAGE_REPORT("new_marketer_id", R.string.spillage_channel_name),
        NEW_CRIME_REPORT("new_Crime_id", R.string.crime_channel_name),
        NEW_EMERG_REPORT("new_emerg_id", R.string.emerg_channel_name),
        NEW_BIZ_DEAL("new_Biz_Deal_id", R.string.bd_channel_name),
        NEW_SESSION_BOOKING("new_Session_B_id", R.string.session_channel_name),
        NEW_DEST_POINT_ALERT("new_dest_id", R.string.stop_point_channel),
        NEW_BOOKING_ALERT("new_booking_id", R.string.booking),
        NEW_MARKETER("new_marketer_id", R.string.marketer_channel_name),
        NEW_CUSTOMER("new_customer_id", R.string.customer_channel_name),
        NEW_SAVINGS("new_savings_id", R.string.savings_channel_name),
        NEW_PACKAGE("new_package_id", R.string.package_channel_name),
        NEW_CASHOUT("new_cashOut_id", R.string.cashout_channel_name),
        NEW_STANDINGORDER("new_so_id", R.string.standing_order),
        NEW_LOAN("new_loan_id", R.string.Loan_channel_name),
        NEW_SUPPORT_MESSAGE("new_support_message_id", R.string.messagechannel_name),
        NEW_PLAN_REQUEST("new_plan_id", R.string.Plan_channel_name),
        NEW_DSTV_SUB("new_dstv_sub_id", R.string.dstv_channel_name),
        NEW_GOTV_SUB("new_gotv_id", R.string.gotv_channel_name),
        NEW_DATA_SUB("new_data_id", R.string.data_channel_name),
        NEW_STARTTIMES_SUB("new_starTime_id", R.string.startTimes_channel_name),
        NEW_NEPABILL_SUB("new_nepaBill_id", R.string.Nepa_channel_name),
        NEW_AFRICA_MONEY_TRANSFER("new_moneyTransfer_id", R.string.money_tx_channel_name),
        NEW_COMMENT("new_comment_id", R.string.new_comment_channel_name);


        /*NEW_UER("new User", "New Awajima User Sign up");
        NEW_BIZ("new Biz. on Awajima", "New Biz Sign Up");
        NEW_OIL_SPILLAGE("new Oil spillage", "new Oil spillage");
        NEW_CRIME("new Crime Report", "New Crime");
        NEW_EMERG("new Emergency", "New Emergency Report");
        NEW_CRIME_RESP("new Crime Response", "New Crime Report Response");
        NEW_CLIMATE_CHANGE_STUFF("new Climate Change Report", "new Climate Change Report");
        NEW_BOAT_BOOKING("new Boat Booking", "new Boat Booking");
        NEW_TAXI_BOOKING("new Taxi Booking", "new Taxi Booking");
        NEW_JET_BOOKING("new Jet Booking", "new Jet Booking");*/

        String id;

        @StringRes
        int name;

        Channel(String id, @StringRes int name) {
            this.id = id;
            this.name = name;
        }
    }

    private void handleNewPackageCreatedAction(RemoteMessage remoteMessage) {

    }
    private void parseSavings(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String savingsId = remoteMessage.getData().get(NEW_SAVINGS_ID_KEY);

        Intent backIntent = new Intent(this, LoginDirAct.class);
        Intent intent = new Intent(this, NotificationAct.class);
        intent.putExtra(REPORT_ID, savingsId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
   /* private void parseNEPABILL(RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String nepaBillId = remoteMessage.getData().get(NEPA_BILL_ID_KEY);

        Intent backIntent = new Intent(this, AdminDashboardTab.class);
        Intent intent = new Intent(this, NEPABILLDetailsActivity.class);
        intent.putExtra(BillsPaymentActivity.NEPA_Bills_ID, nepaBillId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(Channel.NEW_NEPABILL_SUB, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }*/
    private void parsePlan(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String starTimesId = remoteMessage.getData().get(SO_KEY);

        Intent backIntent = new Intent(this, PlanPaymentActivity.class);
        Intent intent = new Intent(this, StarTimeDetailsActivity.class);
        intent.putExtra(BILL_ID, starTimesId);
        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    private void parseGoTv(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String starTimesId = remoteMessage.getData().get(GOTV_ID_KEY);
        Intent backIntent = new Intent(this, GOTVDetailsActivity.class);
        Intent intent = new Intent(this, GOTVDetailsActivity.class);
        intent.putExtra(BILL_ID, starTimesId);
        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    /*private void parseDSTV(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String starTimesId = remoteMessage.getData().get(DSTV_ID_KEY);
        Intent backIntent = new Intent(this, BillsPaymentActivity.class);
        Intent intent = new Intent(this, DSTVDetailsActivity.class);
        intent.putExtra(BILL_ID, starTimesId);
        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
       LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    private void parseSupportM(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String starTimesId = remoteMessage.getData().get(SUPPORT_KEY);
        Intent backIntent = new Intent(this, BillsPaymentActivity.class);
        Intent intent = new Intent(this, SupportMessageDetailsActivity.class);
        //intent.putExtra(NewCustomerDrawer.KEY, starTimesId);
        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    private void parseSO(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String starTimesId = remoteMessage.getData().get(SO_KEY);
        Intent backIntent = new Intent(this, BillsPaymentActivity.class);
        Intent intent = new Intent(this, StarTimeDetailsActivity.class);
        intent.putExtra(BILL_ID, starTimesId);
        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    private void parseStarTimes(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String starTimesId = remoteMessage.getData().get(STARTIMES_ID_KEY);
        Intent backIntent = new Intent(this, BillsPaymentActivity.class);
        Intent intent = new Intent(this, StarTimeDetailsActivity.class);
        intent.putExtra(BILL_ID, starTimesId);
        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }*/
    private void parsePackage(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String packageId = remoteMessage.getData().get(PACKAGE_ID_KEY);

        //Intent backIntent = new Intent(this, ProfileManager.class);
        Intent intent = new Intent(this, PackageDetailsActivity.class);
        intent.putExtra(PACKAGE_ID, packageId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        //sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    /*private void parseData(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String dataId = remoteMessage.getData().get(DATA_ID_KEY);
        Intent backIntent = new Intent(this, AdminDashboardTab.class);
        Intent intent = new Intent(this, DataDetailsActivity.class);
        intent.putExtra(DataSubActivity.DATA_SUB_ID, dataId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        //sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }*/
    private void parseCashOut(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String cashOutId = remoteMessage.getData().get(POST_ID_KEY);

        Intent backIntent = new Intent(this, ProfileManager.class);

        Intent intent = new Intent(this, CashOutDetailsActivity.class);
        //intent.putExtra(RequestPaymentActivity.CASHOUT_EXTRA_KEY, cashOutId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        //sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }
    private void parseLoan(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String postId = remoteMessage.getData().get(LOAN_ID_KEY);

        Intent backIntent = new Intent(this, LoginDirAct.class);
        Intent intent = new Intent(this, LoanDetailsActivity.class);
        //intent.putExtra(LOAN_ID_KEY, postId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }

    private void parseMoneyTransfer(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String postId = remoteMessage.getData().get(MONEY_TRANSFER_ID_KEY);

        Intent backIntent = new Intent(this, LoginDirAct.class);
        Intent intent = new Intent(this, MoneyTxDetailAct.class);
        //intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, postId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug(TAG, "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }

    @Nullable
    public Bitmap getBitmapFromUrl(String imageUrl) {
        return Utils.loadBitmap(Glide.with(context), imageUrl, Utils.PushNotification.LARGE_ICONE_SIZE, Utils.PushNotification.LARGE_ICONE_SIZE);
    }

    private void sendNotification(Channel channel, String notificationTitle, String notificationBody, Bitmap bitmap, Intent intent) {
        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, null);
    }

    private void sendNotification(Channel channel, String notificationTitle, String notificationBody, Bitmap bitmap, Intent intent, Intent backIntent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent;

        if (backIntent != null) {
            backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent[] intents = new Intent[]{backIntent, intent};
            pendingIntent = PendingIntent.getActivities(this, notificationId++, intents, PendingIntent.FLAG_ONE_SHOT);
        } else {
            pendingIntent = PendingIntent.getActivity(this, notificationId++, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel.id);
        notificationBuilder.setAutoCancel(true)   //Automatically delete the notification
                .setSmallIcon(R.drawable.lsgroup) //Notification icon
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setLargeIcon(bitmap)
                .setSound(defaultSoundUri);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channel.id, getString(channel.name), importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(this, R.color.colorPrimary));
            notificationChannel.enableVibration(true);
            notificationBuilder.setChannelId(channel.id);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(notificationId++, notificationBuilder.build());
    }


}
