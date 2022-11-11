package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Classes.Utils;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketStock;
import com.skylightapp.MarketClasses.StockAttrException;
import com.skylightapp.Markets.StockSettingAct;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.ImportantDates;
import com.skylightapp.Classes.LoanInterest;
import com.skylightapp.Classes.PasswordHelpers;
import com.skylightapp.Classes.Payee;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.skylightapp.Admins.AdminBankDeposit.CREATE_ADMIN_DEPOSIT_TABLE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_TABLE;
import static com.skylightapp.Awards.Award.AWARD_TABLE;
import static com.skylightapp.Awards.Award.CREATE_AWARD_TABLE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TABLE;
import static com.skylightapp.Bookings.BoatTrip.CREATE_BOAT_TRIP_TABLE;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.BoatTripRoute.CREATE_BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.Driver.CREATE_DRIVER_TABLE;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.Bookings.TripBooking.CREATE_TRIP_BOOKING_TABLE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPES_TABLE;
import static com.skylightapp.Classes.Account.CREATE_ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.CREATE_ACCOUNT_TYPE_TABLE;
import static com.skylightapp.Classes.Birthday.B_DOB;
import static com.skylightapp.Classes.Birthday.B_PROF_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_CITY;
import static com.skylightapp.Classes.Customer.CUSTOMER_LATLONG;
import static com.skylightapp.Classes.Customer.CUSTOMER_MARKET_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_PROF_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_STATUS;
import static com.skylightapp.Classes.Customer.CUS_BIZ_ID1;
import static com.skylightapp.Classes.Customer.CUS_LOC_CUS_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ACCOUNT_NO_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_PACK_ID_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_CUS_ID_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_OFFICE_BRANCH;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_PROF_ID_FK;
import static com.skylightapp.Classes.CustomerManager.CREATE_WORKERS_TABLE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_PIX;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_PROF_ID;
import static com.skylightapp.Classes.CustomerManager.WORKER_TABLE;
import static com.skylightapp.Classes.DailyAccount.CREATE_DAILY_ACCOUNTING_TABLE;

import static com.skylightapp.Classes.DailyAccount.DAILY_ACCOUNTING_TABLE;

import static com.skylightapp.Classes.Profile.CREATE_USER_TYPE_TABLE;
import static com.skylightapp.Classes.Profile.USER_TYPE_TABLE;
import static com.skylightapp.Bookings.TripStopPoint.TRIP_STOP_POINT_TABLE;
import static com.skylightapp.Bookings.TripStopPoint.CREATE_TRIP_STOP_POINT_TABLE;
import static com.skylightapp.MapAndLoc.EmergReportNext.CREATE_EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergResponse.CREATE_RESPONSE_TABLE;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.CREATE_EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.Classes.Journey.CREATE_JOURNEY_TABLE;
import static com.skylightapp.Classes.Journey.JOURNEY_TABLE;
import static com.skylightapp.Classes.JourneyAccount.CREATE_JOURNEY_ACCOUNT_TABLE;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCOUNT_TABLE;
import static com.skylightapp.Classes.Loan.LOAN_PROF_ID;
import static com.skylightapp.Classes.Message.MESSAGE_PROF_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_NAME;
import static com.skylightapp.Classes.Payee.PAYEE_CUS_ID;
import static com.skylightapp.Classes.Payee.PAYEE_PROF_ID;
import static com.skylightapp.Classes.Payment.CREATE_PAYMENT_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENTS_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENT_AMOUNT;
import static com.skylightapp.Classes.Payment.PAYMENT_DATE;
import static com.skylightapp.Classes.Payment.PAYMENT_OFFICE;
import static com.skylightapp.Classes.Payment.PAYMENT_PROF_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_CUS_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_PROFILE_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_REPORT_NO;
import static com.skylightapp.Classes.Profile.CREATE_SPONSOR_TABLE;
import static com.skylightapp.Classes.Profile.CUS_ID_PASS_KEY;
import static com.skylightapp.Classes.Profile.CUS_ID_PIX_KEY;
import static com.skylightapp.Classes.Profile.PROFILE_CUS_ID_KEY;
import static com.skylightapp.Classes.Profile.PROFID_FOREIGN_KEY_PIX;
import static com.skylightapp.Classes.Profile.PROFILE_PIC_ID;
import static com.skylightapp.Classes.Profile.PROF_ID_FOREIGN_KEY_PASSWORD;
import static com.skylightapp.Classes.Profile.SPONSOR_TABLE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_CODE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_COLLECTION_STATUS;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_CUSTOMER_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ITEM;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_NAME;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_PROFILE_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_REPORT_ID;
import static com.skylightapp.Classes.AppCash.CREATE_SKYLIGHT_CASH_TABLE;
import static com.skylightapp.Classes.AppCash.APP_CASH_ADMIN_ID;
import static com.skylightapp.Classes.AppCash.APP_CASH_PROFILE_ID;
import static com.skylightapp.Classes.AppCash.APP_CASH_FROM;
import static com.skylightapp.Classes.AppCash.APP_CASH_TO;
import static com.skylightapp.Classes.StandingOrder.SO_CUS_ID;
import static com.skylightapp.Classes.AppCash.S_CASH_ADMIN;
import static com.skylightapp.Classes.AppCash.APP_CASH_AMOUNT;
import static com.skylightapp.Classes.AppCash.APP_CASH_CODE;
import static com.skylightapp.Classes.AppCash.APP_CASH_DATE;
import static com.skylightapp.Classes.AppCash.APP_CASH_ID;
import static com.skylightapp.Classes.AppCash.APP_CASH_PAYEE;
import static com.skylightapp.Classes.AppCash.APP_CASH_STATUS;
import static com.skylightapp.Classes.AppCash.APP_CASH_TABLE;
import static com.skylightapp.Classes.AppCash.APP_CASH_PAYER;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PROF_ID;
import static com.skylightapp.Classes.TransactionGranting.CREATE_TANSACTION_EXTRA_TABLE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_TABLE;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_MARKETBIZ_ID;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_PROFILE_ID;
import static com.skylightapp.Database.MarketTranXDAO.CREATE_MARKET_TX_TABLE_TABLE;
import static com.skylightapp.Database.MarketTranXDAO.MARKET_TX_TABLE;
import static com.skylightapp.Inventory.StockTransfer.CREATE_T_STOCKS_TABLE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.CREATE_STOCK_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.MapAndLoc.FenceEvent.CREATE_FENCE_EVENT_TABLE;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_TABLE;
import static com.skylightapp.MapAndLoc.PlaceData.CREATE_PLACES_TABLE;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_TABLE;
import static com.skylightapp.MapAndLoc.TaxiDriver.CREATE_TAXI_DRIVER_TABLE;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_TABLE;
import static com.skylightapp.MapAndLoc.TaxiTrip.CREATE_TAXI_TRIP_TABLE;
import static com.skylightapp.MapAndLoc.TaxiTrip.TAXI_TRIP_TABLE;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_TABLE;
import static com.skylightapp.MarketClasses.BizDealPartner.CREATE_BDEAL_PARTNER_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CODE_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.CREATE_BIZ_DEAL_CODE_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.CREATE_BIZ_DEAL_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.CREATE_DEAL_CHAT_TABLE;
import static com.skylightapp.MarketClasses.Market.CREATE_MARKET_TABLE;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_NAME;
import static com.skylightapp.MarketClasses.Market.MARKET_STATE;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSubScription.CREATE_MARKET_BIZ_SUB_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSupplier.CREATE_SUPPLIER_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.CREATE_BIZ_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static com.skylightapp.MarketClasses.MarketStock.CREATE_MARKET_STOCK_TABLE;
import static com.skylightapp.MarketClasses.MarketStock.KEYS_PROD;
import static com.skylightapp.MarketClasses.MarketStock.KEY_PROD_BENEFIT;
import static com.skylightapp.MarketClasses.MarketStock.KEY_PROD_NAME;
import static com.skylightapp.MarketClasses.MarketStock.KEY_PROD_SALES;
import static com.skylightapp.MarketClasses.MarketStock.MARKET_STOCK_TABLE;

import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_TABLE;
import static com.skylightapp.SuperAdmin.AppCommission.CREATE_ADMIN_BALANCE_TABLE;
import static com.skylightapp.Classes.AdminUser.ADMIN_TABLE;
import static com.skylightapp.Classes.AdminUser.CREATE_ADMIN_TABLE;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_DAYS_BTWN;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_DAYS_REMAINING;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_TABLE;
import static com.skylightapp.Classes.Birthday.CREATE_BIRTHDAY_TABLE;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_ID;
import static com.skylightapp.Classes.Bookings.BOOKING_TABLE;
import static com.skylightapp.Classes.Bookings.CREATE_BOOKING_TABLE;
import static com.skylightapp.Classes.Customer.CREATE_CUSTOMERS_TABLE;
import static com.skylightapp.Classes.Customer.CREATE_CUSTOMER_LOCATION_TABLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_ADDRESS;
import static com.skylightapp.Classes.Customer.CUSTOMER_DATE_JOINED;
import static com.skylightapp.Classes.Customer.CUSTOMER_DOB;
import static com.skylightapp.Classes.Customer.CUSTOMER_EMAIL_ADDRESS;
import static com.skylightapp.Classes.Customer.CUSTOMER_FIRST_NAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_GENDER;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_LOCATION_TABLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_OFFICE;
import static com.skylightapp.Classes.Customer.CUSTOMER_PASSWORD;
import static com.skylightapp.Classes.Customer.CUSTOMER_PHONE_NUMBER;
import static com.skylightapp.Classes.Customer.CUSTOMER_SURNAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_USER_NAME;
import static com.skylightapp.Classes.CustomerDailyReport.CREATE_DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_COLLECTED_SO_FAR;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_REMAINING;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_CODE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DAYS_REMAINING;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_NUMBER_OF_DAYS;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_STATUS;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_TOTAL;
import static com.skylightapp.Classes.CustomerManager.CREATE_CUSTOMERS_TELLER_TABLE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_ADDRESS;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_DATE_JOINED;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_DOB;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_EMAIL_ADDRESS;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_FIRST_NAME;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_GENDER;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_ID;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_NIN;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_OFFICE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_PASSWORD;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_PHONE_NUMBER;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_SURNAME;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_TABLE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_USER_NAME;
import static com.skylightapp.Classes.GroupAccount.CREATE_GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.GroupAccount.CREATE_GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.GroupSavings.CREATE_GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.ImportantDates.CREATE_REMINDER_TABLE;
import static com.skylightapp.Classes.ImportantDates.KEY_ACTIVE;
import static com.skylightapp.Classes.ImportantDates.KEY_DATE_R;
import static com.skylightapp.Classes.ImportantDates.KEY_ID77777;
import static com.skylightapp.Classes.ImportantDates.KEY_REPEAT;
import static com.skylightapp.Classes.ImportantDates.KEY_REPEAT_NO;
import static com.skylightapp.Classes.ImportantDates.KEY_REPEAT_TYPE;
import static com.skylightapp.Classes.ImportantDates.KEY_TIME_R;
import static com.skylightapp.Classes.ImportantDates.KEY_TITLE_R;
import static com.skylightapp.Classes.ImportantDates.TABLE_REMINDERS;
import static com.skylightapp.Classes.Loan.CREATE_LOAN_TABLE;
import static com.skylightapp.Classes.Loan.LOAN_AMOUNT;
import static com.skylightapp.Classes.Loan.LOAN_TABLE;
import static com.skylightapp.Classes.LoanInterest.CREATE_INTEREST_TABLE;
import static com.skylightapp.Classes.LoanInterest.INTEREST_ID;
import static com.skylightapp.Classes.LoanInterest.INTEREST_RATE;
import static com.skylightapp.Classes.LoanInterest.INTEREST_TABLE;
import static com.skylightapp.Classes.Message.CREATE_MESSAGE_REPLY_TABLE;
import static com.skylightapp.Classes.Message.CREATE_MESSAGE_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_REPLY_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_TABLE;
import static com.skylightapp.Classes.OfficeBranch.CREATE_OFFICE_BRANCH;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.Payee.CREATE_PAYEES_TABLE;
import static com.skylightapp.Classes.Payee.PAYEES_TABLE;
import static com.skylightapp.Classes.Payee.PAYEE_ID;
import static com.skylightapp.Classes.Payee.PAYEE_NAME;
import static com.skylightapp.Classes.PaymentCode.CODE_TABLE;
import static com.skylightapp.Classes.PaymentCode.CREATE_CODE_TABLE;
import static com.skylightapp.Classes.PaymentDoc.CREATE_DOCUMENT_TABLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_TABLE;
import static com.skylightapp.Classes.Profile.CREATE_PASSWORD_TABLE;
import static com.skylightapp.Classes.Profile.CREATE_PIXTURE_TABLE;
import static com.skylightapp.Classes.Profile.CREATE_PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PASSWORD;
import static com.skylightapp.Classes.Profile.PASSWORD_TABLE;
import static com.skylightapp.Classes.Profile.PICTURE_TABLE;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_NIN;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;
import static com.skylightapp.Classes.Profile.PROFILE_ADDRESS;
import static com.skylightapp.Classes.Profile.PROFILE_DATE_JOINED;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_NEXT_OF_KIN;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_STATUS;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Classes.ProjectSavingsGroup.CREATE_PROJECT_SAVINGS_GROUP_TABLE;
import static com.skylightapp.Classes.ProjectSavingsGroup.PROJECT_SAVINGS_GROUP_TABLE;
import static com.skylightapp.Classes.SkyLightPackage.CREATE_PACKAGE_TABLE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_BALANCE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_DURATION;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_END_DATE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_EXPECTED_VALUE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_START_DATE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_STATUS;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TOTAL_VALUE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TYPE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_VALUE;
import static com.skylightapp.Classes.StandingOrder.CREATE_SO_TABLE;
import static com.skylightapp.Classes.StandingOrder.SO_DAILY_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.STANDING_ORDER_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.CREATE_SO_ACCT_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCT_TABLE;
import static com.skylightapp.Classes.TellerReport.CREATE_TELLER_REPORT_TABLE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_TABLE;
import static com.skylightapp.Classes.TimeLine.CREATE_TIMELINE_TABLE;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TABLE;
import static com.skylightapp.Classes.Transaction.CREATE_GRP_TX_TABLE;
import static com.skylightapp.Classes.Transaction.CREATE_TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.GRP_TRANX_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.UserSuperAdmin.CREATE_SUPER_ADMIN_TABLE;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_ADDRESS;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_DOB;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_EMAIL_ADDRESS;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_FIRST_NAME;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_GENDER;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_ID;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_NIN;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_OFFICE;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_PASSWORD;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_PHONE_NUMBER;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_SURNAME;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_TABLE;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_USER_NAME;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_AMOUNT;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_APPROVER;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_BRANCH;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_CODE;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_COLLECTOR;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_COLLECTOR_TYPE;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_DATE;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_ID;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_PROFILE_ID;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_TABLE;
import static com.skylightapp.SuperAdmin.SuperCash.SUPER_CASH_TRANX_STATUS;
import static com.skylightapp.Tellers.TellerCash.CREATE_TELLER_CASH_TABLE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_TABLE;
import static com.skylightapp.Transactions.BillModel.CREATE_BILLS_TABLE;
import static java.lang.String.valueOf;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = DBHelper.class.getSimpleName();
    public static int flag;
    String outFileName = "";

    private static final String BILL_CUSTOMER_ID = "billCus";
    private ContentResolver myCR;
    private SQLiteDatabase myDB;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private HashMap hp;
    private ArrayList<Customer> customers;
    SharedPreferences userPref;
    private Context mContext;

    private SQLiteDatabase db;
    private static String TICK = "`";
    private File dbFile;

    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    private Bitmap missingPhoto;

    public static String DB_PATH = "dA/";
    public static final String DATABASE_NAME = "A.DBase";
    private static final String LOG = DBHelper.class.getName();

    public static final String TABLE_MYTABLE = "mytable";
    public static final String COL_MYTABLE_ID = BaseColumns._ID;
    public static final String COl_MYTABLE_NAME = "name";

    public static final String BILL_ID_WITH_PREFIX = "bill.id";
    public static final int DATABASE_VERSION = 37;
    public static final int DATABASE_NEW_VERSION = 39;
    private static final String TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZZZZZ";
    private SimpleDateFormat timeStamp;
    private DateTimeFormatter timeFormat;
    private static final String PREF_NAME = "awajima";
    private static String sqlite_ext_shm = "-shm";
    private static String sqlite_ext_wal = "-wal";
    private static int copy_buffer_size = 1024 * 8;
    private static String stck_trc_msg = " (see stack-trace above)";
    private static String sqlite_ext_journal = "-journal";
    private static int db_user_version, asset_user_version, user_version_offset = 60, user_version_length = 4;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //super(context, DATABASE_NAME, null, setUserVersionFromAsset(context,DATABASE_NAME));
        this.mContext = context;
        if (!ifDbExists(context,DATABASE_NAME)) {
            try {
                copyDBFromAssets(context, DATABASE_NAME,DATABASE_NAME);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        } else {
            setUserVersionFromAsset(context,DATABASE_NAME);
            setUserVersionFromDB(context,DATABASE_NAME);
            if (asset_user_version > db_user_version) {
                copyDBFromAssets(context,DATABASE_NAME,DATABASE_NAME);
            }
        }
        // Force open (and hence copy attempt) when constructing helper
        myDB = this.getWritableDatabase();


        outFileName = DB_PATH + DATABASE_NAME;
        File file = new File(DB_PATH);
        Log.e(TAG, "Awajima DB: " + file.exists());
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

             // CHANGED you don't want the database to be a directory
        }
        //getDatabase(context);
        Log.d("table", CREATE_PROFILES_TABLE);
        Log.d("table", CREATE_PAYEES_TABLE);
        Log.d("table", CREATE_ACCOUNTS_TABLE);
        Log.d("table", CREATE_TRANSACTIONS_TABLE);
        Log.d("table", CREATE_DAILY_REPORT_TABLE);
        Log.d("table", CREATE_CUSTOMERS_TABLE);
        Log.d("table", CREATE_PACKAGE_TABLE);
        Log.d("table", CREATE_PIXTURE_TABLE);
        Log.d("table", CREATE_LOAN_TABLE);
        Log.d("table", CREATE_INTEREST_TABLE);
        Log.d("table", CREATE_BIRTHDAY_TABLE);
        Log.d("table", CREATE_MESSAGE_TABLE);
        Log.d("table", CREATE_TIMELINE_TABLE);
        Log.d("table", CREATE_PASSWORD_TABLE);
        Log.d("table", CREATE_ACCOUNT_TYPE_TABLE);
        Log.d("table", CREATE_REMINDER_TABLE);
        Log.d("table", CREATE_CODE_TABLE);
        Log.d("table", CREATE_REMINDER_TABLE);
        Log.d("table", CREATE_CODE_TABLE);
        Log.d("table", CREATE_DOCUMENT_TABLE);
        Log.d("table", CREATE_SO_TABLE);
        Log.d("table", CREATE_ADMIN_BALANCE_TABLE);
        Log.d("table", CREATE_BILLS_TABLE);
        Log.d("table", CREATE_BOOKING_TABLE);
        Log.d("table", CREATE_EMERGENCY_REPORT_TABLE);
        Log.d("table", CREATE_GRP_ACCT_TABLE);
        Log.d("table", CREATE_GROUP_SAVINGS_TABLE);
        Log.d("table", CREATE_GRP_PROFILE_TABLE);
        Log.d("table", CREATE_GRP_TX_TABLE);
        Log.d("table", CREATE_TELLER_REPORT_TABLE);
        Log.d("table", CREATE_ADMIN_TABLE);
        Log.d("table", CREATE_SUPER_ADMIN_TABLE);
        Log.d("table", CREATE_CUSTOMERS_TELLER_TABLE);
        Log.d("table", CREATE_OFFICE_BRANCH);
        Log.d("table", CREATE_MESSAGE_REPLY_TABLE);
        //Log.d("table", CREATE_ACCOUNTS_STATEMENT_TABLE);
        Log.d("table", CREATE_PAYMENT_TABLE);
        Log.d("table", CREATE_STOCK_TABLE);
        Log.d("table", CREATE_AWARD_TABLE);
        Log.d("table", CREATE_ADMIN_DEPOSIT_TABLE);
        Log.d("table", CREATE_TELLER_CASH_TABLE);
        Log.d("table", CREATE_WORKERS_TABLE);
        Log.d("table", CREATE_T_STOCKS_TABLE);
        Log.d("table", CREATE_CUSTOMER_LOCATION_TABLE);
        Log.d("table", CREATE_TANSACTION_EXTRA_TABLE);
        Log.d("table", CREATE_SPONSOR_TABLE);
        Log.d("table", CREATE_EMERGENCY_NEXT_REPORT_TABLE);
        Log.d("table", CREATE_MARKET_TX_TABLE_TABLE);
        Log.d("table", CREATE_BIZ_TABLE);
        Log.d("table", CREATE_PLACES_TABLE);
        Log.d("table", CREATE_RESPONSE_TABLE);
        Log.d("table", CREATE_SUPPLIER_TABLE);
        Log.d("table", CREATE_MARKET_BIZ_SUB_TABLE);
        Log.d("table", CREATE_USER_TYPE_TABLE);
        Log.d("table", CREATE_BOAT_TRIP_ROUTE_TABLE);
        Log.d("table", CREATE_BOAT_TRIP_TABLE);
        Log.d("table", CREATE_TRIP_STOP_POINT_TABLE);
        Log.d("table", CREATE_TRIP_BOOKING_TABLE);
        Log.d("table", CREATE_TAXI_TRIP_TABLE);
        Log.d("table", CREATE_TAXI_DRIVER_TABLE);
        Log.d("table", CREATE_MARKET_TABLE);
        Log.d("table", CREATE_FENCE_EVENT_TABLE);
        Log.d("table", CREATE_BIZ_DEAL_CODE_TABLE);
        Log.d("table", CREATE_BIZ_DEAL_TABLE);
        Log.d("table", CREATE_BDEAL_PARTNER_TABLE);
        Log.d("table", CREATE_DEAL_CHAT_TABLE);

        try {
            sharedPreferences = mContext.getSharedPreferences(PREF_NAME, 0);


            timeStamp = new SimpleDateFormat(TIME_FORMAT_PATTERN,
                    Locale.getDefault());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                timeFormat = DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN, Locale.getDefault());
            }

            missingPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.user3);

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("FieldBook", "Unable to create or open database");
        }


    }
    private static int setUserVersionFromAsset(Context context, String assetname) {
        InputStream is = null;
        try {
            try {
                is = context.getAssets().open(assetname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("IOError Getting asset " + assetname + " as an InputStream" + stck_trc_msg);
        }
        if (is != null) {
            asset_user_version = getUserVersion(is);
        }
        Log.d("ASSETUSERVERSION","Obtained user_version from asset, it is " + String.valueOf(asset_user_version)); //TODO remove for Live App
        return asset_user_version;
    }
    private static boolean ifDbExists(Context context, String dbname) {
        File db = context.getDatabasePath(dbname);
        if (db.exists()) return true;
        if (!db.getParentFile().exists()) {
            db.getParentFile().mkdirs();
        }
        return false;
    }
    private static int setUserVersionFromDB(Context context, String dbname) {
        File db = context.getDatabasePath(dbname);
        InputStream is;
        try {
            is = new FileInputStream(db);
        } catch (IOException e) {
            throw new RuntimeException("IOError Opening " + db.getPath() + " as an InputStream" + stck_trc_msg);
        }
        db_user_version = getUserVersion(is);
        Log.d("DATABASEUSERVERSION","Obtained user_version from current DB, it is " + String.valueOf(db_user_version)); //TODO remove for live App
        return db_user_version;
    }
    private void getDatabase(Context context) {
        dbFile = new File(context.getDatabasePath((DATABASE_NAME)).getPath());
        if (dbFile.exists()) return; // Database found so all done
        // Otherwise ensure that the database directory exists (does not by default until later versions)
        if (!dbFile.getParentFile().exists()) {
            dbFile.getParentFile().mkdirs();
        }
        if (!copyDataBase()) {
            throw new RuntimeException("Unable to copy database from the asset (check the stack-trace).");
        }
    }

    private static int getUserVersion(InputStream is) {
        String ioerrmsg = "Reading DB header bytes(60-63) ";
        int rv;
        byte[] buffer = new byte[user_version_length];
        byte[] header = new byte[64];
        try {
            is.skip(user_version_offset);
            is.read(buffer,0,user_version_length);
            ByteBuffer bb = ByteBuffer.wrap(buffer);
            rv = ByteBuffer.wrap(buffer).getInt();
            ioerrmsg = "Closing DB ";
            is.close();
            return rv;
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("IOError " + ioerrmsg + stck_trc_msg);
        }
    }
    private static void copyDBFromAssets(Context context, String dbname, String assetname) {
        String tag = "COPYDBFROMASSETS";
        Log.d(tag,"Copying Database from assets folder");
        String backup_base = "bkp_" + String.valueOf(System.currentTimeMillis());
        String ioerrmsg = "Opening Asset " + assetname;

        // Prepare Files that could be used
        File db = context.getDatabasePath(dbname);
        File dbjrn = new File(db.getPath() + sqlite_ext_journal);
        File dbwal = new File(db.getPath() + sqlite_ext_wal);
        File dbshm = new File(db.getPath() + sqlite_ext_shm);
        File dbbkp = new File(db.getPath() + backup_base);
        File dbjrnbkp = new File(db.getPath() + backup_base);
        File dbwalbkp = new File(db.getPath() + backup_base);
        File dbshmbkp = new File(db.getPath() + backup_base);
        byte[] buffer = new byte[copy_buffer_size];
        int bytes_read = 0;
        int total_bytes_read = 0;
        int total_bytes_written = 0;

        // Backup existing sqlite files
        if (db.exists()) {
            db.renameTo(dbbkp);
            dbjrn.renameTo(dbjrnbkp);
            dbwal.renameTo(dbwalbkp);
            dbshm.renameTo(dbshmbkp);
        }
        // ALWAYS delete the additional sqlite log files
        dbjrn.delete();
        dbwal.delete();
        dbshm.delete();

        //Attempt the copy
        try {
            ioerrmsg = "Open InputStream for Asset " + assetname;
            InputStream is = context.getAssets().open(assetname);
            ioerrmsg = "Open OutputStream for Databse " + db.getPath();
            OutputStream os = new FileOutputStream(db);
            ioerrmsg = "Read/Write Data";
            while((bytes_read = is.read(buffer)) > 0) {
                total_bytes_read = total_bytes_read + bytes_read;
                os.write(buffer,0,bytes_read);
                total_bytes_written = total_bytes_written + bytes_read;
            }
            ioerrmsg = "Flush Written data";
            os.flush();
            ioerrmsg = "Close DB OutputStream";
            os.close();
            ioerrmsg = "Close Asset InputStream";
            is.close();
            Log.d(tag,"Databsse copied from the assets folder. " + String.valueOf(total_bytes_written) + " bytes were copied.");
            // Delete the backups
            dbbkp.delete();
            dbjrnbkp.delete();
            dbwalbkp.delete();
            dbshmbkp.delete();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOError attempting to " + ioerrmsg + stck_trc_msg);
        }
    }
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //
        } else {
            this.getReadableDatabase();
            copyDataBase();
        }
    }
    /*public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            openDataBase();
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("App - create", e.getMessage());
            }
        }

    }*/


    /*public DBHelper(Context context, String DATABASE_NAME,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 1);
        myCR = context.getContentResolver();
        this.context = context;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.beginTransaction();
            db.execSQL(CREATE_PROFILES_TABLE);
            db.execSQL(CREATE_LOAN_TABLE);
            db.execSQL(CREATE_INTEREST_TABLE);
            //db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_TANSACTION_EXTRA_TABLE);
            db.execSQL(CREATE_BIRTHDAY_TABLE);
            db.execSQL(CREATE_MESSAGE_TABLE);
            db.execSQL(CREATE_TIMELINE_TABLE);
            db.execSQL(CREATE_PASSWORD_TABLE);
            db.execSQL(CREATE_ACCOUNT_TYPE_TABLE);
            db.execSQL(CREATE_REMINDER_TABLE);
            db.execSQL(CREATE_CODE_TABLE);
            db.execSQL(CREATE_DOCUMENT_TABLE);
            db.execSQL(CREATE_SO_TABLE);
            db.execSQL(CREATE_PAYEES_TABLE);
            db.execSQL(CREATE_ACCOUNTS_TABLE);
            db.execSQL(CREATE_TRANSACTIONS_TABLE);
            db.execSQL(CREATE_DAILY_REPORT_TABLE);
            db.execSQL(CREATE_CUSTOMERS_TABLE);
            db.execSQL(CREATE_PACKAGE_TABLE);
            db.execSQL(CREATE_PIXTURE_TABLE);

            db.execSQL(CREATE_ADMIN_BALANCE_TABLE);
            db.execSQL(CREATE_PROJECT_SAVINGS_GROUP_TABLE);
            db.execSQL(CREATE_BOOKING_TABLE);
            db.execSQL(CREATE_EMERGENCY_REPORT_TABLE);
            db.execSQL(CREATE_SO_ACCT_TABLE);
            db.execSQL(CREATE_GROUP_SAVINGS_TABLE);
            db.execSQL(CREATE_GRP_ACCT_TABLE);
            db.execSQL(CREATE_GRP_PROFILE_TABLE);
            db.execSQL(CREATE_GRP_TX_TABLE);
            db.execSQL(CREATE_ADMIN_TABLE);
            db.execSQL(CREATE_SUPER_ADMIN_TABLE);
            db.execSQL(CREATE_CUSTOMERS_TELLER_TABLE);
            db.execSQL(CREATE_CUSTOMER_LOCATION_TABLE);
            db.execSQL(CREATE_OFFICE_BRANCH);
            db.execSQL(CREATE_MESSAGE_REPLY_TABLE);
            //db.execSQL(CREATE_ACCOUNTS_STATEMENT_TABLE);
            db.execSQL(CREATE_PAYMENT_TABLE);
            db.execSQL(CREATE_STOCK_TABLE);
            db.execSQL(CREATE_AWARD_TABLE);
            db.execSQL(CREATE_ADMIN_DEPOSIT_TABLE);
            db.execSQL(CREATE_TELLER_CASH_TABLE);
            db.execSQL(CREATE_WORKERS_TABLE);
            db.execSQL(CREATE_T_STOCKS_TABLE);
            db.execSQL(CREATE_SPONSOR_TABLE);
            db.execSQL(CREATE_TELLER_REPORT_TABLE);
            db.execSQL(CREATE_SKYLIGHT_CASH_TABLE);
            db.execSQL(CREATE_MARKET_STOCK_TABLE);
            db.execSQL(CREATE_MARKET_TABLE);
            db.execSQL(CREATE_EMERGENCY_NEXT_REPORT_TABLE);
            db.execSQL(CREATE_DAILY_ACCOUNTING_TABLE);
            db.execSQL(CREATE_JOURNEY_TABLE);
            db.execSQL(CREATE_JOURNEY_ACCOUNT_TABLE);
            db.execSQL(CREATE_MARKET_TX_TABLE_TABLE);
            db.execSQL(CREATE_BIZ_TABLE);
            db.execSQL(CREATE_PLACES_TABLE);
            db.execSQL(CREATE_RESPONSE_TABLE);
            db.execSQL(CREATE_SUPPLIER_TABLE);
            db.execSQL(CREATE_MARKET_BIZ_SUB_TABLE);
            db.execSQL(CREATE_USER_TYPE_TABLE);
            db.execSQL(CREATE_BOAT_TRIP_ROUTE_TABLE);
            db.execSQL(CREATE_BOAT_TRIP_TABLE);
            db.execSQL(CREATE_DRIVER_TABLE);
            db.execSQL(CREATE_TRIP_STOP_POINT_TABLE);
            db.execSQL(CREATE_TRIP_BOOKING_TABLE);
            db.execSQL(CREATE_TAXI_TRIP_TABLE);
            db.execSQL(CREATE_TAXI_DRIVER_TABLE);
            db.execSQL(CREATE_FENCE_EVENT_TABLE);
            db.execSQL(CREATE_BIZ_DEAL_CODE_TABLE);
            db.execSQL(CREATE_BIZ_DEAL_TABLE);
            db.execSQL(CREATE_BDEAL_PARTNER_TABLE);
            db.execSQL(CREATE_DEAL_CHAT_TABLE);

            db.execSQL("create table ROLES " + "(role_ID integer primary key, roleUserName text,rolePassword text,rolePhoneNo text,role text)");
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
            android.util.Log.e("DBHelper Table error", e.getMessage());
        }
        finally{

            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS ROLES");
        db.execSQL("DROP TABLE IF EXISTS " + PROFILES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PAYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PACKAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PICTURE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOAN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTEREST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIRTHDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TIMELINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PASSWORD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TYPES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DOCUMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CODE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STANDING_ORDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_BALANCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_SAVINGS_GROUP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EMERGENCY_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SO_ACCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRP_ACCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GROUP_SAVINGS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRP_PROFILE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRP_TRANX_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TELLER_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPER_ADMIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TELLER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OFFICE_BRANCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_REPLY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + "ROLES");
        db.execSQL("DROP TABLE IF EXISTS " + PAYMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STOCKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AWARD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DEPOSIT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + APP_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPER_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + T_STOCKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TELLER_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TANSACTION_EXTRA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPONSOR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_STOCK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_ACCOUNTING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOURNEY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOURNEY_ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_TX_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_BIZ_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PLACE_ENTRY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESPONSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPPLIER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_BIZ_SUB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TYPE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOAT_TRIP_ROUTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOAT_TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DRIVER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_STOP_POINT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TAXI_TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TAXI_DRIVER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FENCE_EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIZ_DEAL_CODE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIZ_DEAL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BDEAL_PARTNER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIZ_DEAL_CHAT_TABLE);

        onCreate(db);

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldV, int newV ){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS ROLES");
        db.execSQL("DROP TABLE IF EXISTS " + PROFILES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PAYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PACKAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PICTURE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOAN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTEREST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIRTHDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TIMELINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PASSWORD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TYPES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DOCUMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CODE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STANDING_ORDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_BALANCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_SAVINGS_GROUP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EMERGENCY_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SO_ACCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRP_ACCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GROUP_SAVINGS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRP_PROFILE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRP_TRANX_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TELLER_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPER_ADMIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TELLER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OFFICE_BRANCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_REPLY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + "ROLES");
        db.execSQL("DROP TABLE IF EXISTS " + PAYMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STOCKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AWARD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DEPOSIT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + APP_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPER_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + T_STOCKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EMERGENCY_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TELLER_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TANSACTION_EXTRA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TANSACTION_EXTRA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPONSOR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_STOCK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_ACCOUNTING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOURNEY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOURNEY_ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_TX_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_BIZ_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESPONSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPPLIER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_BIZ_SUB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TYPE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOAT_TRIP_ROUTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOAT_TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DRIVER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_STOP_POINT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TAXI_TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TAXI_DRIVER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FENCE_EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIZ_DEAL_CODE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIZ_DEAL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BDEAL_PARTNER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIZ_DEAL_CHAT_TABLE);
        onCreate(db);
    }

    private String arrayToString(String table, String[] s) {
        String value = "";

        for (int i = 0; i < s.length; i++) {
            if (table.length() > 0)
                value += table + "." + TICK + s[i] + TICK;
            else
                value += s[i];

            if (i < s.length - 1)
                value += ",";
        }

        return value;
    }
    public static String replaceIdentifiers(String s) {

        return s.replaceAll("'", "''");
    }
    public static String replaceSpecialChars(String s) {

        final Pattern p = Pattern.compile("[\\[\\]`\"\']");

        int lastIndex = 0;

        StringBuilder output = new StringBuilder();

        Matcher matcher = p.matcher(s);

        while (matcher.find()) {

            output.append(s, lastIndex, matcher.start());

            lastIndex = matcher.end();

        }

        if (lastIndex < s.length()) {

            output.append(s, lastIndex, s.length());

        }

        return output.toString();
    }

    /**
     * V2 - Check if a string has any special characters
     */
    public static boolean hasSpecialChars(String s) {
//        final Pattern p = Pattern.compile("[()<>/;\\*%$`\"\']");
        final Pattern p = Pattern.compile("[\\[\\]`\"\']");

        final Matcher m = p.matcher(s);

        return m.find();
    }
    /**
     * Helper function to convert array to csv format
     */
    private static String convertToCommaDelimited(String[] list) {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }
    private void copyFile(File oldFile, File newFile) throws IOException {
        if (oldFile.exists()) {
            try {
                copyFileCall(new FileInputStream(oldFile), new FileOutputStream(newFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void copyFileCall(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;

        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }
    private void copyFile(String fullPath, String filename) {
        AssetManager assetManager = mContext.getAssets();

        InputStream in;
        OutputStream out;

        try {
            in = assetManager.open(filename);
            out = new FileOutputStream(fullPath + "/" + filename);

            byte[] buffer = new byte[1024];
            int read;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("Sample Data", e.getMessage());
        }

    }

    public static String getDatabasePath(Context context) {
        try {
            return context.getDatabasePath(DATABASE_NAME).getPath();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isTableExists(String tableName) {

        open();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public boolean isTableEmpty(String tableName) {
        boolean empty = true;

        if (!isTableExists(tableName)) {
            return empty;
        }

        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        if (cur != null) {
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt(0) == 0);
            }
            cur.close();
        }
        return empty;
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        db.rawQuery("PRAGMA foreign_keys=ON;", null).close();

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(outFileName, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            copyDataBase();
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }



    /*private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("Awajima App - check", e.getMessage());
        }
        if (tempDB != null)
            //tempDB.close();
            return tempDB != null ? true : false;
        return false;
    }*/

    private boolean  copyDataBase() {
        Log.i("Database",
                "Awajima database is being copied to device!");
        byte[] buffer = new byte[4096]; //Probably more efficient as default page size will be 4k
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = mContext.getAssets().open(DATABASE_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput = new FileOutputStream(DB_PATH);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*public boolean copyDataBase() throws IOException {
        try {
            InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
            String outputFileName = DB_PATH + DATABASE_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }

        return false;
    }*/
    private synchronized SQLiteDatabase openWrite(SQLiteOpenHelper handler) {
        if (handler != null) {
            return handler.getWritableDatabase();
        }
        return null;
    }

    private synchronized SQLiteDatabase openRead(SQLiteOpenHelper handler) {
        if (handler != null) {
            return handler.getReadableDatabase();
        }
        return null;
    }

    private synchronized void close(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            db.close();
            super.close();
        }
    }
    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        Log.e(TAG, "openDataBase: Open " + db.isOpen());
    }
    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
        Log.i(DBHelper.class.getName(), "Database is closed");
    }

    /*public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.e(TAG, "openDataBase: Open " + db.isOpen());
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");

    }*/
    public SQLiteDatabase openDataBase(SQLiteDatabase db) {
        if(db.isOpen()){
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
            return sqLiteDatabase;
        }
        //sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
        return sqLiteDatabase;
    }



    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    /*public void openDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && (db.close(){
            db.beginTransaction();

        }

    }*/


    @SuppressLint("StaticFieldLeak")
    private static DBHelper instance;

    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }



    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }




    private String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        return dateFormat.format(date);
    }

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);


    //String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
    //String[] selectionArgs = new String[]{valueOf(branch), valueOf(today)};

    /*public String getPostDesc(String Name) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String desc = "";

        try {
            c = db.rawQuery("Select USER_FIRSTNAME from PROFILES_TABLE INNER JOIN CUSTOMER_TABLE ON PROFILE_ID=PROFILE_ID WHERE NAME='" + Name + "'", null);

            if (c == null) return null;

            if (c.moveToFirst()) {
                desc = c.getString(0);
            }


            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return desc;
    }*/

    public int numberOfRows() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, PROFILES_TABLE);
            return numRows;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    //String P_first_name ,String p_role,int cus_id ,String p_username , String P_surname , String p_state, String p_password, String P_email, String p_sponsor , String P_gender , String P_street , String p_office, String p_phone , String p_join_date , String P_dob ,String p_next_of_kin , String picture_uri , String profile_NIN , String p_status , int profile_id
   /* public long insertProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_ID, profile.getPID());
        cv.put(PROFILE_SURNAME, profile.getProfileLastName());
        cv.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        cv.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        cv.put(PROFILE_EMAIL, profile.getProfileEmail());
        cv.put(PROFILE_DOB, profile.getProfileDob());
        cv.put(PROFILE_GENDER, profile.getProfileGender());
        cv.put(PROFILE_ADDRESS, profile.getProfileAddress());
        cv.put(PROFILE_NIN, profile.getProfileState());
        cv.put(PROFILE_STATE, profile.getProfileIdentity());
        cv.put(PROFILE_OFFICE, profile.getProfileOffice());
        cv.put(PROFILE_DATE_JOINED, profile.getProfileDateJoined());
        cv.put(PROFILE_ROLE, profile.getProfileRole());
        cv.put(PROFILE_USERNAME, profile.getProfileUserName());
        cv.put(PROFILE_PASSWORD, profile.getProfilePassword());
        cv.put(PROFILE_STATUS, profile.getProfileStatus());
        cv.put(PROFILE_NEXT_OF_KIN, profile.getNextOfKin());
        cv.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
        return db.insert("RoomProfileTable",null,cv);
    }

    public Cursor getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query("RoomProfileTable",null,null,null,null,null,null);
    }
    public static long insertPostRoom(SupportSQLiteDatabase db, Profile profile) {
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_ID, profile.getPID());
        cv.put(PROFILE_SURNAME, profile.getProfileLastName());
        cv.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        cv.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        cv.put(PROFILE_EMAIL, profile.getProfileEmail());
        cv.put(PROFILE_DOB, profile.getProfileDob());
        cv.put(PROFILE_GENDER, profile.getProfileGender());
        cv.put(PROFILE_ADDRESS, profile.getProfileAddress());
        cv.put(PROFILE_NIN, profile.getProfileState());
        cv.put(PROFILE_STATE, profile.getProfileIdentity());
        cv.put(PROFILE_OFFICE, profile.getProfileOffice());
        cv.put(PROFILE_DATE_JOINED, profile.getProfileDateJoined());
        cv.put(PROFILE_ROLE, profile.getProfileRole());
        cv.put(PROFILE_USERNAME, profile.getProfileUserName());
        cv.put(PROFILE_PASSWORD, profile.getProfilePassword());
        cv.put(PROFILE_STATUS, profile.getProfileStatus());
        cv.put(PROFILE_NEXT_OF_KIN, profile.getNextOfKin());
        cv.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
        return db.insert("RoomProfileTable", OnConflictStrategy.IGNORE,cv);
    }*/



    @SuppressLint("Range")
    public ArrayList<ChartData> getSavingsChartByCusID(int cusID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = REPORT_CUS_ID_FK + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(cusID)};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {

                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getSavingsChartByTellerID(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = REPORT_PROF_ID_FK + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(tellerID)};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }

    @SuppressLint("Range")
    public ArrayList<ChartData> getChartPaymentByBranch(String branch,String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = PAYMENT_OFFICE + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + PAYMENT_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + PAYMENT_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(branch)};
        String groupbyclause = "substr(" + PAYMENT_DATE + ",4)";
        String orderbyclause = "substr(" + PAYMENT_DATE + ",7,2)||substr(" + PAYMENT_DATE + ",4,2)";
        Cursor cursor = db.query(PAYMENTS_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getChartPaymentByTeller(int tellerID,String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = PAYMENT_PROF_ID + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + PAYMENT_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + PAYMENT_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(tellerID)};
        String groupbyclause = "substr(" + PAYMENT_DATE + ",4)";
        String orderbyclause = "substr(" + PAYMENT_DATE + ",7,2)||substr(" + PAYMENT_DATE + ",4,2)";
        Cursor cursor = db.query(PAYMENTS_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getChartPaymentAll(String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + PAYMENT_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + PAYMENT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + PAYMENT_DATE + ",4)";
        String orderbyclause = "substr(" + PAYMENT_DATE + ",7,2)||substr(" + PAYMENT_DATE + ",4,2)";
        Cursor cursor = db.query(PAYMENTS_TABLE, columns, null,
                null, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }

    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthInvs(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> dataList = new ArrayList<TellerCountData>();
        String strgInv="Investment";
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                REPORT_PACK_ID_FK + " = " + PACKAGE_ID + " AND " + PACKAGE_TYPE + " = " +  strgInv +" AND " + REPORT_PACK_ID_FK + " = " +  tellerID,
                null, groupbyclause, null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(REPORT_PROF_ID_FK));
                tellerCountData.setTellerName(cursor.getColumnName(2));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
                //Log.e("DATABASE",cursor.getString(5));
                dataList.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthItems(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> dataList = new ArrayList<TellerCountData>();
        String strgInv="Items Purchase";
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                REPORT_PACK_ID_FK + " = " + PACKAGE_ID + " AND " + PACKAGE_TYPE + " = " +  strgInv +" AND " + PROFILE_ID + " = " +  tellerID,
                null, groupbyclause, null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(REPORT_PROF_ID_FK));
                tellerCountData.setTellerName(cursor.getColumnName(2));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
               // Log.e("DATABASE",cursor.getString(5));
                dataList.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthSavings(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> dataList = new ArrayList<TellerCountData>();
        String strgInv="Savings";
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                REPORT_PACK_ID_FK + " = " + PACKAGE_ID + " AND " + PACKAGE_TYPE + " = " +  strgInv +" AND " + PROFILE_ID + " = " +  tellerID,
                null, groupbyclause, null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(REPORT_PROF_ID_FK));
                tellerCountData.setTellerName(cursor.getColumnName(2));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
                // Log.e("DATABASE",cursor.getString(5));
                dataList.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthPromo(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> dataList = new ArrayList<TellerCountData>();
        String strgInv="Promo";
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                REPORT_PACK_ID_FK + " = " + PACKAGE_ID + " AND " + PACKAGE_TYPE + " = " +  strgInv +" AND " + PROFILE_ID + " = " +  tellerID,
                null, groupbyclause, null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(REPORT_PROF_ID_FK));
                tellerCountData.setTellerName(cursor.getColumnName(2));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
                // Log.e("DATABASE",cursor.getString(5));
                dataList.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return dataList;

    }




    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthPayment(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> dataList = new ArrayList<TellerCountData>();
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + PAYMENT_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + PAYMENT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + PAYMENT_DATE + ",4)";
        String orderbyclause = "substr(" + PAYMENT_DATE + ",7,2)||substr(" + PAYMENT_DATE + ",4,2)";

        String selection = PAYMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID)};

        Cursor cursor = db.query(PAYMENTS_TABLE, columns, selection, selectionArgs, groupbyclause,
                null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(PAYMENT_PROF_ID));
                tellerCountData.setTellerName(cursor.getColumnName(12));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
                dataList.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return dataList;

    }



    @SuppressLint("Range")
    public ArrayList<ChartData> getChartSavingsAll(String Savings, String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        String selection = PACKAGE_TYPE + "=?";
        String[] selectionArgs = new String[]{valueOf(Savings)};

        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                selection,
                selectionArgs, groupbyclause, null, orderbyclause);


        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getChartItemsValue(String ItemsPurchase,String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        ItemsPurchase = "Item Purchase";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        String selection = PACKAGE_TYPE + "=?";
        String[] selectionArgs = new String[]{ItemsPurchase};

        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                selection,
                selectionArgs, groupbyclause, null, orderbyclause);

        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getChartPromoValue(String Promo,String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";
        String selection = PACKAGE_TYPE + "=?";
        String[] selectionArgs = new String[]{valueOf(Promo)};
        Cursor cursor = db.query(
                DAILY_REPORT_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{DAILY_REPORT_TABLE, PACKAGE_TABLE}),
                selection,
                selectionArgs, groupbyclause, null, orderbyclause);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getChartInvestmentAll(String Investment, String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + REPORT_TOTAL + ") AS " + tmpcol_monthly_total, "substr(" + REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + REPORT_DATE + ",7,2)||substr(" + REPORT_DATE + ",4,2)";

        String selection = PACKAGE_TYPE + "=? AND " + groupbyclause + "=?";
        String[] selectionArgs = new String[]{valueOf(Investment), valueOf(yearMonth)};

        Cursor cursor = db.query(
                PACKAGE_TABLE + " , " + DAILY_REPORT_TABLE,
                Utils.concat(new String[]{PACKAGE_TABLE, DAILY_REPORT_TABLE}),
                selection,
                selectionArgs, groupbyclause, null, orderbyclause);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }

    @SuppressLint("Range")
    public ArrayList<ChartData> getPackageChart(String monthYear){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String selection = "substr(" + PACKAGE_START_DATE + ",4)" + "=?";
        String[] selectionArgs = new String[]{valueOf(monthYear)};

        String[] columns = new String[]{"sum(" + PACKAGE_VALUE + ") AS " + tmpcol_monthly_total, "substr(" + PACKAGE_START_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + PACKAGE_START_DATE + ",4)";
        String orderbyclause = "substr(" + PACKAGE_START_DATE + ",7,2)||substr(" + PACKAGE_START_DATE + ",4,2)";
        @SuppressLint("Recycle") Cursor cursor = db.query(PACKAGE_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }







    private void getTellerCountDataCursor(ArrayList<TellerCountData> countDataArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int tellerCountDID=cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
                @SuppressLint("Range") String tellerName = cursor.getString(cursor.getColumnIndex(PROFILE_FIRSTNAME));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex("Month_and_Year"));
                @SuppressLint("Range") int size=cursor.getInt(cursor.getColumnIndex("Monthly_Count"));
                countDataArrayList.add(new TellerCountData(tellerCountDID,tellerName,size,duration));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthCuSs(int profileID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> dataList = new ArrayList<TellerCountData>();
        String selection = CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        String tmpcol_monthly_total = "Monthly_Count";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"COUNT(" + CUSTOMER_ID + ") AS " + tmpcol_monthly_total, "substr(" + CUSTOMER_DATE_JOINED + ",4) AS " + tmpcol_month_year, PROFILE_ID};
        String groupbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",4)";
        String orderbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",7,2)||substr(" + CUSTOMER_DATE_JOINED + ",4,2)";
        Cursor cursor = db.query(CUSTOMER_TABLE, null, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerCountDataCursor(dataList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return dataList;

    }




    public int getCusMonthSavingsCount1(int customerID, String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};
        String queryString="SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE ;

        @SuppressLint("Recycle") Cursor cursor = db.query(queryString,
                null,
                selection, selectionArgs,
                REPORT_ID, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;



    }

    public int getCusMonthPackagesCount1(int customerID, String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String queryString="SELECT COUNT (*) FROM " + PACKAGE_TABLE ;


        String selection = "STRFTIME('%Y-%m',PACKAGE_START_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
        String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + REPORT_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(queryString,
                null,
                selection, selectionArgs,
                REPORT_ID, grpBy, null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(PACKAGE_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;


    }





    /*public Cursor getSimpleCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                String surname = cursor.getString(3);
                String firstName = cursor.getString(4);
                customers.add(new Customer(surname, firstName));
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }*/


    public double getMonthTotalSavingsForCustomer(int customerID,String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;

        String selection = "substr(" + REPORT_DATE + ",4)" + "=? AND " + REPORT_CUS_ID_FK + "=?";

        String queryString="select sum ("+ REPORT_TOTAL +") from " + DAILY_REPORT_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(REPORT_TOTAL);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }





    public double getTotalStocksTransfersByBranch(String Branch,String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ APP_CASH_AMOUNT +") from " + APP_CASH_TABLE + " WHERE " + selection,
                new String[]{valueOf(date)}
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }



    public long saveNewSuperCash(int tellerCashID, int recipientID, String date, double amount, String collectorName,String type, String officeBranch, String adminName, long code, String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUPER_CASH_ID, tellerCashID);
        contentValues.put(SUPER_CASH_PROFILE_ID, recipientID);
        contentValues.put(SUPER_CASH_DATE, date);
        contentValues.put(SUPER_CASH_AMOUNT, amount);
        contentValues.put(SUPER_CASH_COLLECTOR, collectorName);
        contentValues.put(SUPER_CASH_BRANCH, officeBranch);
        contentValues.put(SUPER_CASH_COLLECTOR_TYPE, type);
        contentValues.put(SUPER_CASH_CODE, valueOf(code));
        contentValues.put(SUPER_CASH_APPROVER, adminName);
        contentValues.put(SUPER_CASH_TRANX_STATUS, status);
        return db.insert(SUPER_CASH_TABLE,null,contentValues);
    }

    public int getSuperCashCountForBranchAndDate(String office,String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SUPER_CASH_BRANCH + "=? AND " + SUPER_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + SUPER_CASH_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(SUPER_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }

    /*private void getSuperCashCursor(ArrayList<SuperCash> superCashArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                long superCashID = cursor.getLong(0);
                //formatter.format(date)
                String date = cursor.getString(4);
                double amount = cursor.getDouble(5);
                String collectorName = cursor.getString(6);
                String office = cursor.getString(7);
                String adminName = cursor.getString(8);
                long code = cursor.getLong(9);
                String status = cursor.getString(10);
                superCashArrayList.add(new SuperCash(superCashID, date, amount, collectorName, office, adminName, code,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }*/
    public long saveNewSkylightCash(int tellerCashID,int tellerAdminID, int profileId, String date, double amount, String from, String to, String payee, String payer,long code, String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_CASH_ID, tellerCashID);
        contentValues.put(APP_CASH_ADMIN_ID, tellerAdminID);
        contentValues.put(APP_CASH_PROFILE_ID, profileId);
        contentValues.put(APP_CASH_DATE, date);
        contentValues.put(APP_CASH_AMOUNT, amount);
        contentValues.put(APP_CASH_PAYER, payer);
        contentValues.put(APP_CASH_PAYEE, payee);
        contentValues.put(APP_CASH_FROM, from);
        contentValues.put(APP_CASH_TO, to);
        contentValues.put(APP_CASH_CODE, valueOf(code));
        contentValues.put(APP_CASH_STATUS, status);
        return db.insert(APP_CASH_TABLE,null,contentValues);

    }
    public int getAllSkylightCashCountForDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[]{date};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + APP_CASH_TABLE + " WHERE " + APP_CASH_DATE + "=?",
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getSkylightCashCountForBranchAndDate(String office, String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = APP_CASH_PAYEE + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }

    private void getSkylightCashCursorMy(ArrayList<AppCash> appCashArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int skylightCashID = cursor.getInt(0);
            String date = cursor.getString(4);
            double amount = cursor.getDouble(5);
            String payer = cursor.getString(6);
            String payee = cursor.getString(7);
            String status = cursor.getString(10);
            String from = cursor.getString(11);
            String to = cursor.getString(12);
            appCashArrayList.add(new AppCash(skylightCashID, date, amount, payer, payee, from, to,status));
        }

    }
    private void getSkylightCashCursorAll(ArrayList<AppCash> appCashArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int skylightCashID = cursor.getInt(0);
            String date = cursor.getString(4);
            double amount = cursor.getDouble(5);
            String payer = cursor.getString(6);
            String payee = cursor.getString(7);
            long code = cursor.getLong(9);
            String status = cursor.getString(10);
            String from = cursor.getString(11);
            String to = cursor.getString(12);
            appCashArrayList.add(new AppCash(skylightCashID, date, amount, payer, payee,code, from, to,status));
        }


    }
    public ArrayList<AppCash> getAllSkylightCashForPayee(String payee) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_PAYEE + "=?";
        String[] selectionArgs = new String[]{valueOf(payee)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSkylightCashForFromCategory(String fromCategory) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_FROM + "=?";
        String[] selectionArgs = new String[]{valueOf(fromCategory)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();

        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSkylightCashForFromCategoryAndDate(String fromCategory, String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_FROM + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(fromCategory), valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSkylightCashForToCategory(String toCategory) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_TO + "=?";
        String[] selectionArgs = new String[]{valueOf(toCategory)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSkylightCashForToCategoryAndDate(String toCategory, String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_TO + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(toCategory), valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }

    public ArrayList<AppCash> getAllSkylightCashForPayer(String payer) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_PAYER + "=?";
        String[] selectionArgs = new String[]{valueOf(payer)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    private void getTellerCashSpnCursor(ArrayList<String> tellerCashArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                String tellerName = cursor.getString(6);
                tellerCashArrayList.add(tellerName);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllSCashPayers() {
        ArrayList<String> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {APP_CASH_PAYER};
        Cursor cursor = db.query(APP_CASH_TABLE, columns, APP_CASH_PAYER, null, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getTellerCashSpnCursor(tellerCashArrayList, cursor);
                cursor.close();
            }

        db.close();

        return tellerCashArrayList;
    }

    public ArrayList<AppCash> getSCashForPayeeAtDate(String office, String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_PAYEE + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getSCashForPayerAtDate(String teller, String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_PAYER + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getSCashForAdminAtDate(String admin, String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = S_CASH_ADMIN + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(admin), valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getSkylightCashForProfileAtDate(int profileID, String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_PROFILE_ID + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorMy(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();

        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSkylightCashForProfile(int profileID) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorMy(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSCashAtDate(String date) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }
    public ArrayList<AppCash> getAllSCashForAdmin(String adminName) {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = S_CASH_ADMIN + "=?";
        String[] selectionArgs = new String[]{valueOf(adminName)};

        Cursor cursor = db.query(APP_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return appCashArrayList;
    }


    public double getSCashTotalForDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.rawQuery(
                "select sum ("+ APP_CASH_AMOUNT +") from " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getSCashTotalForPayerAndDate(String teller, String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = APP_CASH_PAYER + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ APP_CASH_AMOUNT +") from " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getSCashTotalForPayeeAndDate(String teller, String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = APP_CASH_PAYEE + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ APP_CASH_AMOUNT +") from " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public ArrayList<AppCash> getAllSkylightCash() {
        ArrayList<AppCash> appCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {APP_CASH_ID, APP_CASH_AMOUNT, APP_CASH_CODE, S_CASH_ADMIN, APP_CASH_PAYEE, APP_CASH_DATE, APP_CASH_PAYER, APP_CASH_STATUS};
        Cursor cursor = db.query(APP_CASH_TABLE, columns, null, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSkylightCashCursorAll(appCashArrayList, cursor);
                cursor.close();
            }

        db.close();

        return appCashArrayList;

    }
    public double getSkylightCashTotalForProfile(int profileID) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = APP_CASH_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ APP_CASH_AMOUNT +") from " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }

    public double getSkylightCashTotalForProfileAndDate(int profileID, String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = APP_CASH_PROFILE_ID + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ APP_CASH_AMOUNT +") from " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getTellerCashCountForPayerWithDate(String teller, String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = APP_CASH_PAYER + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllTellerCashCountWithDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getTellerCashCountForPayeeWithDate(String office, String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = APP_CASH_PAYEE + "=? AND " + APP_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + APP_CASH_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(APP_CASH_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public void updateTellerCashWithCode(String office,int tellerCashID,long code,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = APP_CASH_PROFILE_ID + "=? AND " + APP_CASH_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(tellerCashID)};
        stocksUpdateValues.put(APP_CASH_CODE, code);
        stocksUpdateValues.put(APP_CASH_STATUS, status);
        db.update(APP_CASH_TABLE, stocksUpdateValues, selection, selectionArgs);


    }
    public void deleteSkylightCash(int tellerCashID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_CASH_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerCashID)};

        db.delete(APP_CASH_TABLE,
                selection,
                selectionArgs);


    }







    public void updateSavingsStatus(int packageID,int reportId,Double packageBalance,String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        reportId = customerDailyReport.getRecordID();
        savingsUpdateValues.put(PACKAGE_BALANCE, packageBalance);
        savingsUpdateValues.put(REPORT_STATUS, status);
        db.update(DAILY_REPORT_TABLE, savingsUpdateValues, REPORT_ID + " = ?", new String[]{valueOf(reportId)});
        db.update(PACKAGE_TABLE, savingsUpdateValues, PACKAGE_ID + " = ?", new String[]{valueOf(packageID)});
        db.close();


        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


    }

    public void updateSavingsCode(int customerID,int reportId,long savingsCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        ContentValues savingsUpdateValues2 = new ContentValues();
        savingsUpdateValues.put(REPORT_CODE, savingsCode);
        savingsUpdateValues2.put(CODE_REPORT_NO, savingsCode);
        String selection = REPORT_CUS_ID_FK + "=? AND " + REPORT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(reportId)};
        String selection3 = CODE_CUS_ID + "=? AND " + CODE_REPORT_NO + "=?";
        String[] selectionArgs3 = new String[]{valueOf(customerID), valueOf(reportId)};
        db.update(DAILY_REPORT_TABLE,
                savingsUpdateValues, selection, selectionArgs);
        db.update(CODE_TABLE,
                savingsUpdateValues2, selection3, selectionArgs3);
        db.close();


    }

    public long insertTellerReport(int profileID2, String uSurname, String uFirstName, String uPhoneNumber, String dateOfBirth, String uEmail, String uAddress, String selectedGender, String office, String selectedState, Uri picture, String joinedDate, String stringNIN, String uUserName, String uPassword) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues values3 = new ContentValues();
        int superAdminID = 0;
        String name=uSurname+""+ uFirstName;
        String passwordCoded=PasswordHelpers.passwordHash(uPassword);
        String stringNIN2=PasswordHelpers.passwordHash(stringNIN);

        values.put(CUSTOMER_TELLER_ID, profileID2);
        values.put(CUSTOMER_TELLER_SURNAME, uSurname);
        values.put(CUSTOMER_TELLER_PROF_ID, profileID2);
        values.put(CUSTOMER_TELLER_FIRST_NAME, uFirstName);
        values.put(CUSTOMER_TELLER_PHONE_NUMBER, uPhoneNumber);
        values.put(CUSTOMER_TELLER_EMAIL_ADDRESS, uEmail);
        values.put(CUSTOMER_TELLER_DOB, dateOfBirth);
        values.put(CUSTOMER_TELLER_GENDER, selectedGender);
        values.put(CUSTOMER_TELLER_ADDRESS, uAddress);
        values.put(CUSTOMER_TELLER_OFFICE, office);
        values.put(CUSTOMER_TELLER_USER_NAME, uUserName);
        values.put(CUSTOMER_TELLER_DATE_JOINED, joinedDate);
        values.put(CUSTOMER_TELLER_PIX, String.valueOf(picture));
        values.put(CUSTOMER_TELLER_PASSWORD, passwordCoded);
        values.put(CUSTOMER_TELLER_NIN, stringNIN2);


        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());
        int daysBTWN=0;
        int daysRemaining=0;
        Birthday birthday=new Birthday(profileID2, 0,name, uPhoneNumber, uEmail,selectedGender,uAddress,dateOfBirth,daysBTWN, String.valueOf(daysRemaining), "");

        values1.put(B_DOB, dateOfBirth);
        values1.put(B_PROF_ID, profileID2);
        values1.put(BIRTHDAY_ID, profileID2);
        values1.put(BIRTHDAY_DAYS_BTWN, birthday.getDaysInBetween(currentDate,dateOfBirth));
        values1.put(BIRTHDAY_DAYS_REMAINING, birthday.getFormattedDaysRemainingString(currentDate,dateOfBirth));

        values2.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID2);
        values2.put(PASSWORD, passwordCoded);

        values3.put(PROFILE_PIC_ID, profileID2);
        values3.put(PICTURE_URI, String.valueOf(picture));

        db.insert(CUSTOMER_TELLER_TABLE, null, values);
        db.insert(BIRTHDAY_TABLE, null, values1);
        db.insert(PASSWORD_TABLE, null, values2);
        db.insert(PICTURE_TABLE, null, values3);
        //db.close();
        return profileID2;
    }
    public long insertSuperAdmin(int profileID,String surname,String firstName,String phoneNo,String email,String dob,String gender,String address, String office,String userName,String password,Uri profilePix,int marketBizID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues picValue = new ContentValues();
        //int superAdminID = 0;
        String name=surname+""+ firstName;
        String passwordCoded=PasswordHelpers.passwordHash(password);
        values.put(SUPER_ADMIN_ID, profileID);
        values.put(SUPER_ADMIN_SURNAME, surname);
        values.put(SUPER_ADMIN_PROFILE_ID, profileID);
        values.put(SUPER_ADMIN_FIRST_NAME, firstName);
        values.put(SUPER_ADMIN_PHONE_NUMBER, phoneNo);
        values.put(SUPER_ADMIN_EMAIL_ADDRESS, email);
        values.put(SUPER_ADMIN_DOB, dob);
        values.put(SUPER_ADMIN_GENDER, gender);
        values.put(SUPER_ADMIN_ADDRESS, address);
        values.put(SUPER_ADMIN_OFFICE, office);
        values.put(SUPER_ADMIN_USER_NAME, userName);
        values.put(SUPER_ADMIN_MARKETBIZ_ID, marketBizID);
        values.put(SUPER_ADMIN_PASSWORD, passwordCoded);
        values.put(SUPER_ADMIN_NIN, "");

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());
        int daysBTWN=0;
        int daysRemaining=0;
        Birthday birthday=new Birthday(profileID, profileID,name, phoneNo, email,gender,address,dob,daysBTWN, String.valueOf(daysRemaining), "");

        values1.put(PROFILE_DOB, dob);
        values1.put(PROFILE_ID, profileID);
        values1.put(PROFILE_SURNAME, surname);
        values1.put(PROFILE_FIRSTNAME, firstName);
        values1.put(PROFILE_PHONE, phoneNo);
        values1.put(PROFILE_EMAIL, email);
        values1.put(PROFILE_SURNAME, surname);
        values1.put(PROFILE_FIRSTNAME, firstName);
        values1.put(PROFILE_PHONE, phoneNo);
        values1.put(PROFILE_EMAIL, email);


        values1.put(B_DOB, dob);
        values1.put(B_PROF_ID, profileID);
        values1.put(BIRTHDAY_ID, profileID);
        values1.put(BIRTHDAY_DAYS_BTWN, birthday.getDaysInBetween(currentDate,dob));
        values1.put(BIRTHDAY_DAYS_REMAINING, birthday.getFormattedDaysRemainingString(currentDate,dob));

        values2.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID);
        values2.put(PASSWORD, passwordCoded);

        picValue.put(PROFILE_PIC_ID, profileID);
        picValue.put(PICTURE_URI, String.valueOf(profilePix));

        db.insert(CUSTOMER_TELLER_TABLE, null, values);
        db.insert(BIRTHDAY_TABLE, null, values1);
        db.insert(PASSWORD_TABLE, null, values2);
        db.insert(PICTURE_TABLE, null, picValue);
        //db.close();



        return profileID;
    }




    public void updatePackage(int packageID,int customerID,double packageBalance,String status,String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PACKAGE_BALANCE, valueOf(packageBalance));
        cv.put(PACKAGE_STATUS, status);
        cv.put(PACKAGE_END_DATE, endDate);
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(packageID)};
        db.update(PACKAGE_TABLE,
                cv, selection, selectionArgs);
        db.close();


    }


    public int getSavingsCountToday(String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_DATE + "=?";
        int count = 0;
        String[] selectionArgs = new String[]{today};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        return count;
    }




    public int getFirstSavingsCountForToday(String today,String status) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = REPORT_DATE + "=? AND " + REPORT_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(today), valueOf(status)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }

    public int getSavingsCusCountTodayForTeller(int tellerID, Date today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public double getTotalSavingsToday(String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ REPORT_TOTAL +") from " + DAILY_REPORT_TABLE + " WHERE " + selection, selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }
    public int getSavingsCountTodayForCustomer(int customerID, Date today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_CUS_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }



    public int getSavingsCusCountToday(int cusID,String today) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_CUS_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[] {valueOf(cusID),valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public double getTotalSavingsForTeller(int tellerID) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ REPORT_TOTAL +") from " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_TOTAL);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public double getTotalSavingsTodayForTeller(int tellerID, Date today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ REPORT_TOTAL +") from " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(REPORT_TOTAL);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public ArrayList<CustomerDailyReport> getSavingsForBranchAtDate(String officeBranch, String dateOfToday) {
        ArrayList<CustomerDailyReport> customerDailyReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_OFFICE_BRANCH + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(dateOfToday)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getDailyReportsFromCursor(customerDailyReportArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return customerDailyReportArrayList;
    }


    /*public void insertOfficeBranch(OfficeBranch officeBranch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(OFFICE_BRANCH_NAME, officeBranch.getOfficeBranchName());
            values.put(OFFICE_BRANCH_DATE, String.valueOf(officeBranch.getOfficeBranchDate()));
            values.put(OFFICE_BRANCH_ADDRESS, officeBranch.getOfficeBranchAddress());
            values.put(OFFICE_BRANCH_APPROVER, officeBranch.getOfficeBranchApprover());
            values.put(OFFICE_BRANCH_STATUS, officeBranch.getOfficeBranchAddressStatus());
            int officeBranchID = (int) db.insert(OFFICE_BRANCH_TABLE, null, values);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }*/




    public long insertCustomerLocation(int customerID, LatLng latLng) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUS_LOC_CUS_ID, customerID);
        contentValues.put(CUSTOMER_LATLONG, String.valueOf(latLng));
        return sqLiteDatabase.insert(CUSTOMER_LOCATION_TABLE, null, contentValues);
    }
    /*public Cursor getBillCursor(String tittle, Date bookingDate) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (bookingDate !=null ) {
            cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE BOOKING_DATE = '" + bookingDate + "' AND NBRBEDROOMS = '" + tittle + "'  ORDER BY BOOKING_ID;", null);
        } else {
            cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE BOOKING_DATE = '" + bookingDate + "'  ORDER BY BOOKING_ID;", null);
        }
        return cursor;
    }*/


    public long addReminder(ImportantDates reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_R, reminder.getTitle());

        values.put(KEY_DATE_R, valueOf(reminder.getDate()));
        values.put(KEY_TIME_R, valueOf(reminder.getTime()));
        values.put(KEY_REPEAT, reminder.getRepeat());
        values.put(KEY_REPEAT_NO, reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());
        return db.insert(TABLE_REMINDERS, null, values);

    }



    public ImportantDates getReminder(String mReceivedID) {
        SQLiteDatabase db = this.getReadableDatabase();

        ImportantDates reminder;
        try (Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID77777,
                                KEY_TITLE_R,
                                KEY_DATE_R,
                                KEY_TIME_R,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE
                        }, KEY_ID77777 + "=?",

                new String[]{String.valueOf(mReceivedID)}, null, null, null, null)) {

            if (cursor != null)
                cursor.moveToFirst();

            reminder = null;
            if (cursor != null) {
                reminder = new ImportantDates(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
            }
        }

        return reminder;

    }





    public List<ImportantDates> getAllReminders() {
        try {
            List<ImportantDates> reminderList = new ArrayList<>();

            // Select all Query
            String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        ImportantDates reminder = new ImportantDates();
                        reminder.setID(Integer.parseInt(cursor.getString(0)));
                        reminder.setTitle(cursor.getString(1));
                        reminder.setDate(cursor.getString(2));
                        reminder.setTime(cursor.getString(3));
                        reminder.setRepeat(cursor.getString(4));
                        reminder.setRepeatNo(cursor.getString(5));
                        reminder.setRepeatType(cursor.getString(6));
                        reminder.setActive(cursor.getString(7));

                        reminderList.add(reminder);
                    } while (cursor.moveToNext());
                }
            }
            return reminderList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public int getRemindersCount() {
        String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
    public double getTotalSavingsForPackage(int packageID) {
        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        String[] whereArgs = new String[]{String.valueOf(packageID)};
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE PACKAGE_ID=?",String.valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }
        return 0;
    }
    public double getTotalSavingsForCustomer(int customerID) {
        Customer customer = new Customer();
        customerID=customer.getCusUID();
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE CUSTOMER_ID=?",String.valueOf(customerID)})){

            if (cursor.moveToFirst()) {

                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }

        return 0;
    }





    // Deleting single Reminder
    public void deleteReminder(ImportantDates reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID77777 + "=?",
                new String[]{String.valueOf(reminder.getUID())});
        db.close();
    }




    public ArrayList<CustomerDailyReport> getTellerSavingsForToday(int profileID,String todayDate) {
        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(todayDate)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        getReportsFromCursorAdmin(customerDailyReports, cursor);

        cursor.close();
        db.close();

        return customerDailyReports;
    }
    public int getSavingsCountForProfileToday(int profileID,String today) {

        String countQuery = "SELECT  * FROM " + DAILY_REPORT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};

        String[] columns = {REPORT_PACK_ID_FK, REPORT_AMOUNT, REPORT_DATE,REPORT_NUMBER_OF_DAYS,REPORT_TOTAL};

        Cursor cursor = db.query(countQuery, columns, selection, selectionArgs, "", null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getSavingsCount(int packageNo) {

        String countQuery = "SELECT  * FROM " + DAILY_REPORT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {REPORT_PACK_ID_FK, REPORT_AMOUNT, REPORT_DATE,REPORT_NUMBER_OF_DAYS,REPORT_AMOUNT_REMAINING,REPORT_DAYS_REMAINING};

        String selection = REPORT_PACK_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(packageNo)};

        Cursor cursor = db.query(countQuery, columns, selection, selectionArgs, "", PACKAGE_ID, REPORT_DATE);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int insertDailyReport(int packageID, int reportID, int profileID, int customerID, String reportDate, double reportAmt, int report_No_Of_Days, double total, double amtCollected, double reportAmtRemaining,  int daysRem, String reportStatus) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPORT_PROF_ID_FK, profileID);
        contentValues.put(REPORT_PACK_ID_FK, packageID);
        contentValues.put(REPORT_ID, reportID);
        contentValues.put(REPORT_CUS_ID_FK, customerID);
        contentValues.put(REPORT_DATE, reportDate);
        contentValues.put(REPORT_AMOUNT, reportAmt);
        contentValues.put(REPORT_NUMBER_OF_DAYS, report_No_Of_Days);
        contentValues.put(REPORT_TOTAL, total);
        contentValues.put(REPORT_AMOUNT_COLLECTED_SO_FAR, amtCollected);
        contentValues.put(REPORT_DAYS_REMAINING, daysRem);
        contentValues.put(REPORT_AMOUNT_REMAINING, reportAmtRemaining);
        contentValues.put(REPORT_STATUS, reportStatus);
        sqLiteDatabase.insert(DAILY_REPORT_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return reportID;
    }

    public ImportantDates getReminder(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        ImportantDates reminder;
        try (Cursor cursor = db.query(TABLE_REMINDERS, new String[]{KEY_ID77777, KEY_TITLE_R, KEY_DATE_R, KEY_TIME_R, KEY_REPEAT, KEY_REPEAT_NO, KEY_REPEAT_TYPE, KEY_ACTIVE}, KEY_ID77777 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null)) {

            if (cursor != null)
                cursor.moveToFirst();

            reminder = null;
            if (cursor != null) {
                reminder = new ImportantDates(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
            }
        }


        return reminder;
    }



    public int insertDailyReport4(Profile profile, Customer customer, SkyLightPackage skyLightPackage, CustomerDailyReport customerDailyReport, Account account) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues packageSavingsValue = new ContentValues();
        int reportNo = customerDailyReport.getRecordNumberOfDays();
        long reportCode =customerDailyReport.getRecordSavingsCode();
        packageSavingsValue.put(REPORT_PROF_ID_FK, profile.getPID());
        packageSavingsValue.put(REPORT_PACK_ID_FK, skyLightPackage.getPackID());
        packageSavingsValue.put(REPORT_CUS_ID_FK, customer.getCusUID());
        packageSavingsValue.put(REPORT_ACCOUNT_NO_FK, account.getAwajimaAcctNo());
        packageSavingsValue.put(REPORT_AMOUNT, valueOf(customerDailyReport.getRecordAmount()));
        packageSavingsValue.put(REPORT_NUMBER_OF_DAYS, customerDailyReport.getRecordNumberOfDays());
        packageSavingsValue.put(REPORT_TOTAL, customerDailyReport.getRecordAmount());
        packageSavingsValue.put(REPORT_DATE, customerDailyReport.getRecordDate());
        packageSavingsValue.put(REPORT_DAYS_REMAINING, customerDailyReport.getRecordRemainingDays());
        packageSavingsValue.put(REPORT_AMOUNT_REMAINING, customerDailyReport.getRecordAmountRemaining());
        packageSavingsValue.put(REPORT_STATUS, customerDailyReport.getRecordStatus());
        packageSavingsValue.put(REPORT_CODE, reportCode);
        sqLiteDatabase.insert(DAILY_REPORT_TABLE, null, packageSavingsValue);

        sqLiteDatabase.close();
        return reportNo;
    }

    public boolean updateReportWithCode(int reportID, int customerID,long reportCode, String confirmed) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPORT_CODE, reportCode);
        contentValues.put(REPORT_STATUS, confirmed);
        String selection = REPORT_ID + "=? AND " + REPORT_CUS_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID), valueOf(customerID)};
        db.update(DAILY_REPORT_TABLE, contentValues, selection, selectionArgs);
        return true;
    }
    public void updateReportVoidWithCode(int reportID, int customerID,long reportCode,String confirmed) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPORT_STATUS, confirmed);
        contentValues.put(REPORT_CODE, reportCode);
        String selection = REPORT_ID + "=? AND " + REPORT_CUS_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID), valueOf(customerID)};
        db.update(DAILY_REPORT_TABLE, contentValues, selection, selectionArgs);


    }


    public ArrayList<CustomerDailyReport> getCustomerDailyReportForCustomerToday(int customerID,String todayDate) {

        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_CUS_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(todayDate)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return dailyReports;
    }

    public Cursor getPackageSavings(int packageID) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = REPORT_PACK_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(packageID)};
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + DAILY_REPORT_TABLE + " WHERE " +  selection,
                selectionArgs
        );


    }

    public int getNewPackageCountForTellerToday(int tellerID,String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " +  selection,
                selectionArgs
        );
        int count = 0;
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        if (cursor != null) {
            cursor.close();
        }
        return count;

    }
    public int getAllPackageCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " +  null,
                null
        );
        int count = 0;
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }
    public int getNewPackageCountToday(String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PACKAGE_START_DATE + "=? ";
        String[] selectionArgs = new String[]{(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " +  selection,
                selectionArgs
        );
        int count = 0;
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }

    public List<SkyLightPackage> getAllPackageReminders() {
        try {
            List<SkyLightPackage> packageReminderList = new ArrayList<>();

            // Select all Query
            String selectQuery = "SELECT * FROM " + PACKAGE_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                // Looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        SkyLightPackage skyLightPackage = new SkyLightPackage();
                        skyLightPackage.setRecordPackageId(Integer.parseInt(cursor.getString(0)));
                        skyLightPackage.setPackageCustomerName(cursor.getString(1));
                        skyLightPackage.setPackageTotalAmount(Double.valueOf(cursor.getString(2)));
                        skyLightPackage.setPackageDailyAmount(Double.parseDouble(cursor.getString(3)));
                        skyLightPackage.setPackageBalance(Double.parseDouble(cursor.getString(4)));
                        skyLightPackage.setPackageDateStarted(cursor.getString(5));
                        skyLightPackage.setDateEnded(cursor.getString(6));
                        skyLightPackage.setPackageDuration(Integer.parseInt(cursor.getString(7)));
                        skyLightPackage.setPackageStatus(cursor.getString(8));
                        skyLightPackage.setPackageCustomerId(Integer.parseInt(cursor.getString(9)));


                        // Adding Reminders to list
                        packageReminderList.add(skyLightPackage);
                    } while (cursor.moveToNext());
                }
            }
            return packageReminderList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void updatePackageRecord(int profileId, int customerId, int packageId,int reportId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues packageValues = new ContentValues();
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_CUSTOMER_ID_FOREIGN + "=?AND " + PACKAGE_ID + "=?AND " + PACKAGE_REPORT_ID +"=?";
        String[] selectionArgs = new String[]{valueOf(profileId), valueOf(customerId),valueOf(packageId),valueOf(reportId)};
        packageValues.put(REPORT_STATUS, status);
        db.update(DAILY_REPORT_TABLE, packageValues, selection, selectionArgs);
        db.close();


    }
    public void updatePackageForCollection(int profileId, int customerId, int packageId, int marketBizID,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues packageValues = new ContentValues();
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + SUPER_ADMIN_MARKETBIZ_ID + "=?AND "+ PACKAGE_CUSTOMER_ID_FOREIGN + "=?AND " + PACKAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileId), valueOf(marketBizID),valueOf(customerId),valueOf(packageId)};
        packageValues.put(PACKAGE_STATUS, status);
        db.update(PACKAGE_TABLE, packageValues, selection, selectionArgs);
        db.close();


    }




    public ArrayList<SkyLightPackage> getAllPackagesCustomer(int customerID) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return packages;

    }


    public ArrayList<SkyLightPackage> getAllPackagesAdmin() {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(PACKAGE_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return packages;
    }

    private void getPackagesFromCursorAdmin(ArrayList<SkyLightPackage> packages, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int packageID = cursor.getInt(0);
                String name = cursor.getString(4);
                String type = cursor.getString(5);
                String startDate = cursor.getString(6);
                int duration = cursor.getInt(7);
                String endDate = cursor.getString(8);
                double amount = cursor.getDouble(9);
                double total = cursor.getDouble(10);
                double balance = cursor.getDouble(11);
                String status = cursor.getString(12);
                //ArrayList<Account> accounts = new ArrayList<>();
                //ArrayList<Payee> payees = new ArrayList<>();

                packages.add(new SkyLightPackage(packageID, name,type, amount, startDate, duration, total, balance, endDate, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        // returns true if pointed to a record

    }




    public void overwriteCustomerPackage(SkyLightPackage skyLightPackage, Customer customer, CustomerDailyReport customerDailyReport, Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PACKAGE_CUSTOMER_ID_FOREIGN, customer.getCusUID());
        cv.put(PACKAGE_REPORT_ID, customerDailyReport.getRecordPackageId());
        cv.put(PACKAGE_BALANCE, valueOf(skyLightPackage.getPackageBalance()));
        cv.put(PACKAGE_STATUS, skyLightPackage.getPackageStatus());
        cv.put(PACKAGE_END_DATE, skyLightPackage.getPackageDateEnded());
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=?AND " + PACKAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customer.getCusUID()), valueOf(skyLightPackage.getPackID())};
        db.update(PACKAGE_TABLE, cv, selection, selectionArgs);
        db.close();


    }

    public void updatePackage(int customerID,int packageId,double packageBalance,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PACKAGE_BALANCE, valueOf(packageBalance));
        cv.put(PACKAGE_STATUS, status);
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=?AND " + PACKAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(packageId)};
        db.update(PACKAGE_TABLE, cv, selection, selectionArgs);
        db.close();

    }
    /*public void overwriteCustomerReport(int customerID,int packageID,int reportID, long acctID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Account account = new Account();
            SkyLightPackage skyLightPackage = new SkyLightPackage();
            Customer customer = new Customer();
            CustomerDailyReport customerDailyReport = new CustomerDailyReport();
            ContentValues cv = new ContentValues();
            cv.put(CUSTOMER_ID, customerID);
            cv.put(REPORT_NUMBER, reportID);
            cv.put(PACKAGE_BALANCE, valueOf(skyLightPackage.getBalance()));
            cv.put(PACKAGE_STATUS, skyLightPackage.getStatus());
            cv.put(PACKAGE_END_DATE, skyLightPackage.getDateEnded());
            cv.put(REPORT_AMOUNT_REMAINING, customerDailyReport.getAmountRemaining());
            cv.put(REPORT_DAYS_REMAINING, customerDailyReport.getRemainingDays());
            cv.put(REPORT_STATUS, customerDailyReport.getStatus());
            cv.put("Round Completed", customerDailyReport.getCompleted());

            db.update(DAILY_REPORT_TABLE, cv, CUSTOMER_ID + "=? , " + PACKAGE_ID + "=? , " + REPORT_NUMBER + "=?",
                    new String[]{valueOf(customer.getCusUID()), valueOf(skyLightPackage.getPackageId()),valueOf(customerDailyReport.getRecordNo())});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }*/



    public ArrayList<SkyLightPackage> getPackagesFromCurrentProfile(int profileID) {
        ArrayList<SkyLightPackage> skyLightPackages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(PACKAGE_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorCustomer(profileID, skyLightPackages);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return skyLightPackages;
    }
    protected String getSpecificPackage(int packageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String skPackage=null;
        String selection = PACKAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(packageId)};
        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    skPackage = cursor.getColumnName(4);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        cursor.close();
        db.close();

        return skPackage;
    }



    public void deletePackage(int packageId) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PACKAGE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(packageId)};
            db.delete(PACKAGE_TABLE,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<SkyLightPackage> getPackagesFromCustomer(int customerID) {
        ArrayList<SkyLightPackage> skyLightPackages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? ";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorCustomer1(customerID, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return skyLightPackages;
    }
    private Cursor getPackagesFromCursorCustomer1(int customerID, Cursor cursor) {
        while (cursor.moveToNext()) {
            ArrayList<SkyLightPackage> packages=new ArrayList<SkyLightPackage>();

            if (customerID == cursor.getLong(2)) {
                int id = cursor.getInt(2);
                int packageId = cursor.getInt(0);
                String packageType = cursor.getString(5);
                String startDate = cursor.getString(6);
                String endDate = cursor.getString(8);
                String packageValue = cursor.getString(9);
                String packageDuration = cursor.getString(7);
                String packageStatus = cursor.getString(12);

                packages.add(new SkyLightPackage(packageId, packageType, packageValue, packageStatus));
            }
        }return cursor;
    }
    private Cursor getPackagesFromCursorCustomer(int customerID, ArrayList<SkyLightPackage> packages) {
        Cursor cursor = null;

        if (cursor != null) {
            while (cursor.moveToNext()) {

                if (customerID == cursor.getInt(2)) {
                    int packageId = cursor.getInt(0);
                    String packageType = cursor.getString(5);
                    String startDate = cursor.getString(6);
                    String endDate = cursor.getString(8);
                    String packageValue = cursor.getString(9);
                    String packageDuration = cursor.getString(7);
                    String packageStatus = cursor.getString(12);

                    packages.add(new SkyLightPackage(packageId, packageType, packageValue, packageStatus));
                }
            }
        }return  cursor;
    }

    public int getCustomerPackageCount(int cusId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? ";
        String[] selectionArgs = new String[]{valueOf(cusId)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return count;

    }

    public int getPackageCountTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        int count = 0;
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;

    }

    public ArrayList<SkyLightPackage> getPackagesForTellerProfileWithDate(int tellerID, String todayDate) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE,PACKAGE_TYPE,PACKAGE_NAME,PACKAGE_CODE,PACKAGE_EXPECTED_VALUE,PACKAGE_START_DATE,PACKAGE_END_DATE,PACKAGE_DURATION,PACKAGE_TOTAL_VALUE,PACKAGE_ITEM,PACKAGE_NAME,PACKAGE_STATUS};
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(todayDate)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    public ArrayList<SkyLightPackage> getCusPackageEndingToday(int customerID, String todayDate) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE,PACKAGE_TYPE,PACKAGE_NAME,PACKAGE_CODE,PACKAGE_EXPECTED_VALUE,PACKAGE_START_DATE,PACKAGE_END_DATE,PACKAGE_DURATION,PACKAGE_TOTAL_VALUE,PACKAGE_STATUS};
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(todayDate)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    private Cursor getPackagesFromCursor(ArrayList<SkyLightPackage> packages,Cursor cursor) {

        while (cursor.moveToNext()) {
            int packageId = cursor.getInt(0);
            String packageName = cursor.getString(4);
            String packageType = cursor.getString(5);

            String startDate = cursor.getString(6);
            int packageDuration = cursor.getInt(7);
            String endDate = cursor.getString(8);
            double packageValue = cursor.getDouble(9);
            double expectedValue = cursor.getDouble(10);
            long code = cursor.getLong(11);
            String packageStatus = cursor.getString(12);

            packages.add(new SkyLightPackage(packageId, packageName,packageType, packageValue,expectedValue,packageDuration,startDate,endDate, code,packageStatus));
        }
        return cursor;
    }
    public ArrayList<SkyLightPackage> getPackageByType(String type) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE,PACKAGE_TYPE,PACKAGE_NAME,PACKAGE_CODE,PACKAGE_EXPECTED_VALUE,PACKAGE_START_DATE,PACKAGE_END_DATE,PACKAGE_DURATION,PACKAGE_TOTAL_VALUE,PACKAGE_STATUS};
        String selection = PACKAGE_TYPE + "=?";
        String[] selectionArgs = new String[]{valueOf(type)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursor(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;

    }
    public ArrayList<SkyLightPackage> getPackageByTypeAndEndDate(String type,String date) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE,PACKAGE_TYPE,PACKAGE_NAME,PACKAGE_CODE,PACKAGE_EXPECTED_VALUE,PACKAGE_START_DATE,PACKAGE_END_DATE,PACKAGE_DURATION,PACKAGE_TOTAL_VALUE,PACKAGE_STATUS};
        String selection = PACKAGE_TYPE + "=? AND " + PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(type), valueOf(date)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursor(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    public ArrayList<SkyLightPackage> getPackageByTypeAndStartDate(String type,String date) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE,PACKAGE_TYPE,PACKAGE_NAME,PACKAGE_CODE,PACKAGE_EXPECTED_VALUE,PACKAGE_START_DATE,PACKAGE_END_DATE,PACKAGE_DURATION,PACKAGE_TOTAL_VALUE,PACKAGE_STATUS};
        String selection = PACKAGE_TYPE + "=? AND " + PACKAGE_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(type), valueOf(date)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursor(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    public ArrayList<SkyLightPackage> getTellerPackagesEndingToday(int profileID,String todayDate) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(todayDate)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    public ArrayList<SkyLightPackage> getTellerPackagesEnding2DAYS(int profileID,String todayDate) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(todayDate)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    public ArrayList<SkyLightPackage> getTellerPackagesEnding3DAYS(int profileID,String date) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;

    }
    public ArrayList<SkyLightPackage> getCustomerPackageEndingToday(int customerID,String todayDate) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(todayDate)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }

    public ArrayList<SkyLightPackage> getCustomerCompleteUnCollectedPack(int customerId, String completed,String collectionStatus) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String incomplete = null;
        //inProgress=incomplete;

        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_COLLECTION_STATUS + "=?AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerId), collectionStatus,completed};

        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return packages;
    }


    public ArrayList<SkyLightPackage> getCustomerSavingsCompletePackage(int customerID,String savings,String completed) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_TYPE + "=?AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(savings),valueOf(completed)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);

        cursor.close();
        db.close();

        return skyLightPackageArrayList;
    }
    public int getPackageCountCustomDay(String dateOfCustomDays) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        ArrayList<SkyLightPackage> packages = new ArrayList<>();

        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfCustomDays)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;
    }

    public ArrayList<SkyLightPackage> getPackEndingCustomDay(String dateOfCustomDays) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfCustomDays)};

        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return packages;

    }



    public ArrayList<SkyLightPackage> getProfileIncompletePack(int profileID, String inProgress) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(inProgress)};

        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return packages;
    }
    public int getPackEndingTomoroCount(String dateOfTomorrow) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfTomorrow)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;

    }

    public int getPackEnding7DaysCount(String dateOfSevenDays) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfSevenDays)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;
    }

    public ArrayList<SkyLightPackage> getCustomerItemPurchaseCompletePackage(int customerID,String itemPurchase,String completed) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_TYPE + "=?AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(itemPurchase),valueOf(completed)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return skyLightPackageArrayList;
    }
    public ArrayList<SkyLightPackage> getPackagesSubscribedToday(String todayDate) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = sdf.format(calendar.getTime().getDate());
        ArrayList<SkyLightPackage> skyLightPackages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_TYPE, PACKAGE_VALUE,PACKAGE_DURATION,PACKAGE_TOTAL_VALUE,PACKAGE_STATUS};
        String selection = PACKAGE_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(skyLightPackages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return skyLightPackages;
    }

    public long insertNewPackage(Profile profile, Customer customer, SkyLightPackage skyLightPackage) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Calendar calendar = null;

        ContentValues packageValues = new ContentValues();
        packageValues.put(PACKAGE_PROFILE_ID_FOREIGN, profile.getPID());
        packageValues.put(PACKAGE_CUSTOMER_ID_FOREIGN, customer.getCusUID());
        packageValues.put(PACKAGE_ID, skyLightPackage.getPackID());
        packageValues.put(PACKAGE_NAME, skyLightPackage.getPackageName());
        packageValues.put(PACKAGE_TYPE, skyLightPackage.getPackageType());
        packageValues.put(PACKAGE_VALUE, skyLightPackage.getPackageDailyAmount());
        packageValues.put(PACKAGE_DURATION, skyLightPackage.getPackageDuration());
        packageValues.put(PACKAGE_START_DATE, skyLightPackage.getPackageDateStarted());
        packageValues.put(PACKAGE_END_DATE, skyLightPackage.getPackageDateEnded());
        packageValues.put(PACKAGE_BALANCE, skyLightPackage.getPackageBalance());
        packageValues.put(PACKAGE_STATUS, skyLightPackage.getPackageStatus());
        return sqLiteDatabase.insert(PACKAGE_TABLE, null, packageValues);

    }

    public SkyLightPackage getPackageReminder(String mReceivedID) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            @SuppressLint("Recycle") Cursor cursor = db.query(PACKAGE_TABLE, new String[]
                            {
                                    PACKAGE_CUSTOMER_ID_FOREIGN,
                                    PACKAGE_ID,
                                    PACKAGE_TYPE,
                                    PACKAGE_VALUE,
                                    PACKAGE_DURATION,
                                    PACKAGE_START_DATE,
                                    PACKAGE_END_DATE,
                                    PACKAGE_BALANCE,
                                    REPORT_DAYS_REMAINING,
                                    PACKAGE_STATUS
                            }, PACKAGE_ID + "=?",

                    new String[]{String.valueOf(mReceivedID)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            SkyLightPackage skyLightPackage = null;
            /*if (cursor != null) {
                skyLightPackage = new SkyLightPackage(cursor.getString(0)), cursor.getString(1),
                        Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)),
                        cursor.getString(4), Integer.parseInt(cursor.getString(5)), cursor.getString(6),Double.parseDouble(cursor.getString(7)),cursor.getString(6);
            }*/

            return skyLightPackage;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public SkyLightPackage getPackageReminder2(long mReceivedID) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            SkyLightPackage skyLightPackage;
            try (Cursor cursor = db.query(PACKAGE_TABLE, new String[]
                            {
                                    PACKAGE_CUSTOMER_ID_FOREIGN,
                                    PACKAGE_ID,
                                    PACKAGE_TYPE,
                                    PACKAGE_VALUE,
                                    PACKAGE_DURATION,
                                    PACKAGE_START_DATE,
                                    PACKAGE_END_DATE,
                                    PACKAGE_BALANCE,
                                    REPORT_DAYS_REMAINING,
                                    PACKAGE_STATUS
                            }, PACKAGE_ID + "=?",

                    new String[]{String.valueOf(mReceivedID)}, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                skyLightPackage = null;
                /*if (cursor != null) {
                    skyLightPackage = new SkyLightPackage(cursor.getString(0)), cursor.getString(1),
                            Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)),
                            cursor.getString(4), Integer.parseInt(cursor.getString(5)), cursor.getString(6), Double.parseDouble(cursor.getString(7)), cursor.getString(6);
                }*/
            }

            return skyLightPackage;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public BigInteger getPackagesSumValue(){
        try {
            int sum=0;
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(" + PACKAGE_VALUE + ") as Total FROM " + PACKAGE_TABLE, null)) {
                if (cursor.moveToFirst())
                    //cursor.moveToFirst();
                    sum = cursor.getColumnIndex(PACKAGE_VALUE);
                return BigInteger.valueOf(sum);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public long insertNewPackage(int profileId, int customerId, int packageId,long packageCode,String packageName, String packageType, int duration, double amount, String startDate, double grandTotal, String endDate, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (sqLiteDatabase != null) {
            Calendar calendar = null;
            ContentValues packageValues = new ContentValues();
            packageValues.put(PACKAGE_PROFILE_ID_FOREIGN, profileId);
            packageValues.put(PACKAGE_CUSTOMER_ID_FOREIGN, customerId);
            packageValues.put(PACKAGE_ID, packageId);
            packageValues.put(PACKAGE_NAME, packageName);
            packageValues.put(PACKAGE_TYPE, packageType);
            packageValues.put(PACKAGE_VALUE, amount);
            packageValues.put(PACKAGE_DURATION, duration);
            packageValues.put(PACKAGE_START_DATE, startDate);
            packageValues.put(PACKAGE_END_DATE, endDate);
            packageValues.put(PACKAGE_TOTAL_VALUE, grandTotal);
            packageValues.put(PACKAGE_STATUS, status);
            packageValues.put(PACKAGE_CODE, packageCode);
            return sqLiteDatabase.insert(PACKAGE_TABLE, null, packageValues);
        }


        return packageId;
    }
    public long insertNewDailyReport(int profileId, int customerId, int packageId,int reportID,long reportCode,int noOfDays, double total, String date, int remainingDays,double amountRem, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues reportValue = new ContentValues();
        reportValue.put(PACKAGE_PROFILE_ID_FOREIGN, profileId);
        reportValue.put(PACKAGE_CUSTOMER_ID_FOREIGN, customerId);
        reportValue.put(PACKAGE_ID, packageId);
        reportValue.put(PACKAGE_REPORT_ID, reportID);
        reportValue.put(REPORT_CODE, reportCode);
        reportValue.put(REPORT_DAYS_REMAINING, remainingDays);
        reportValue.put(REPORT_AMOUNT_REMAINING, amountRem);
        reportValue.put(REPORT_DATE, date);
        reportValue.put(REPORT_NUMBER_OF_DAYS, noOfDays);
        reportValue.put(REPORT_TOTAL, total);
        reportValue.put(REPORT_STATUS, status);
        sqLiteDatabase.insert(DAILY_REPORT_TABLE, null, reportValue);
        return reportID;
    }
    public long insertPackage(int profileId, int customerId, int packageId,String packageName, String packageType, String duration, double amount, String date, String startDate, double numberOfDays, double total, String ledgerBalance, String grandTotal, String endDate, int count, int remainingDays, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Calendar calendar = null;
        ContentValues packageValues = new ContentValues();
        packageValues.put(PACKAGE_PROFILE_ID_FOREIGN, profileId);
        packageValues.put(PACKAGE_CUSTOMER_ID_FOREIGN, customerId);
        packageValues.put(PACKAGE_ID, packageId);
        packageValues.put(PACKAGE_NAME, packageName);
        packageValues.put(PACKAGE_TYPE, packageType);
        packageValues.put(PACKAGE_VALUE, amount);
        packageValues.put(PACKAGE_DURATION, duration);
        packageValues.put(PACKAGE_START_DATE, startDate);
        packageValues.put(PACKAGE_END_DATE, endDate);
        packageValues.put(PACKAGE_BALANCE, ledgerBalance);
        packageValues.put(PACKAGE_STATUS, status);
        packageValues.put(PACKAGE_TOTAL_VALUE, grandTotal);
        //sqLiteDatabase.insertWithOnConflict(DAILY_REPORT_TABLE, null, reportValue, SQLiteDatabase.CONFLICT_IGNORE);
        return sqLiteDatabase.insertWithOnConflict(PACKAGE_TABLE, null, packageValues, SQLiteDatabase.CONFLICT_IGNORE);

    }

    public void updatePackage(SkyLightPackage skyLightPackage) {
        SQLiteDatabase db = this.getWritableDatabase();
        Customer customer = new Customer();
        Profile profile = new Profile();
        Account account = new Account();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        ContentValues values = new ContentValues();
        //values.put(PROFILE_ID, profile.getPID());
        //values.put(PACKAGE_ID, skyLightPackage.getPackageId());
        //values.put(CUSTOMER_ID, customer.getCusUID());
        //values.put(REPORT_ID, customer.getCusUID());
        //values.put(ACCOUNT_NO, account.getSkyLightAcctNo());
        //values.put(ACCOUNT_BALANCE, account.getAccountBalance());
        //values.put(PACKAGE_VALUE, skyLightPackage.getDailyAmount());
        //values.put(PACKAGE_START_DATE, skyLightPackage.getDateStarted());
        values.put(PACKAGE_BALANCE, skyLightPackage.getPackageBalance());
        values.put(PACKAGE_END_DATE, skyLightPackage.getPackageDateEnded());
        values.put(PACKAGE_STATUS, skyLightPackage.getPackageStatus());
        //values.put(PACKAGE_DURATION, skyLightPackage.getPackageDuration());
        values.put("ReportCount", "");
        long result = db.update(PACKAGE_TABLE, values,
                PACKAGE_ID,
                new String[]{valueOf(skyLightPackage.getPackID())});
        Log.d("Update Result:", "=" + result);


    }




    public ArrayList<SkyLightPackage> getCustomerPromoCompletePackage(int customerID,String promo,String completed) {
        ArrayList<SkyLightPackage> skyLightPackageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PACKAGE_VALUE, PACKAGE_TYPE,PACKAGE_START_DATE,PACKAGE_TOTAL_VALUE,PACKAGE_EXPECTED_VALUE,PACKAGE_BALANCE};
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_TYPE + "=?AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(promo),valueOf(completed)};

        Cursor cursor = db.query(PACKAGE_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(skyLightPackageArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return skyLightPackageArrayList;
    }
    public ArrayList<CustomerDailyReport> getCustomerDailyReportForCustomer(int customerID) {

        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_CUS_ID_FK + "=?" ;
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);

        getDailyReportsFromCursor(dailyReports, cursor);

        cursor.close();
        db.close();

        return dailyReports;
    }
    protected int insertSavingsReport(int i, Profile profile, SkyLightPackage skyLightPackage, CustomerDailyReport customerDailyReport, Customer customer) {
        return 0;
    }


    public int getCustomerTotalSavingsCount(int customerID) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection, selectionArgs
        );


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;
    }

    public int getPackagesCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + PACKAGE_TABLE;
        Cursor cursor=null;

        try {
            cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }


        return 0;
    }

    public int getTotalSavingsCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + DAILY_REPORT_TABLE;
        Cursor cursor=null;

        try {
            cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }


        return 0;
    }


    public int getSavingsCountTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection, selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return count;
    }

    public ArrayList<CustomerDailyReport> getIncompleteSavingsForTellerToday(int profileID, String todayDate,String unPaid) {

        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=? AND " + REPORT_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(todayDate),valueOf(unPaid)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }

    public ArrayList<CustomerDailyReport> getCompletedSavingsForCustomer(int customerID, String paid) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_CUS_ID_FK + "=? AND " + REPORT_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(paid)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<CustomerDailyReport> getAllIncompleteSavings(String unPaid) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(unPaid)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<CustomerDailyReport> getIncompleteSavingsForTeller(int profileID,String unPaid) {

        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(unPaid)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<CustomerDailyReport> getCustomerDailyReportFromCurrentProfile(int profileID, String todayDate) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(todayDate)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;

    }

    public ArrayList<CustomerDailyReport> getCustomerDailyReportToday(String todayDate) {
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_CUS_ID_FK,REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }


    public ArrayList<CustomerDailyReport> getCustomerDailyReportFromCurrentCustomer(int customerID, String todayDate) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};

        String selection = REPORT_CUS_ID_FK + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(todayDate)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);

        getDailyReportsFromCursor(dailyReports, cursor);

        cursor.close();
        db.close();

        return dailyReports;
    }
    public ArrayList<CustomerDailyReport> getAllCustomerDailyReports(int customerID) {
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,REPORT_AMOUNT_REMAINING};
        String selection = REPORT_CUS_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);

        getDailyReportsFromCursor(dailyReports, cursor);

        cursor.close();
        db.close();

        return dailyReports;

    }
    public ArrayList<CustomerDailyReport> getDailyReportForToday(String todayDate) {
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {REPORT_AMOUNT, REPORT_NUMBER_OF_DAYS,REPORT_TOTAL,REPORT_DAYS_REMAINING,REPORT_DATE,PROFILE_ID};

        String selection = REPORT_DATE + "=?";

        String[] selectionArgs = new String[]{valueOf(todayDate)};
        @SuppressLint("Recycle") Cursor cursor = db.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, PROFILE_ID);

        getDailyReportsFromCursor(dailyReports, cursor);

        cursor.close();
        //db.close();

        return dailyReports;
    }






    public long saveNewPayee(Profile profile, Payee payee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PAYEE_PROF_ID, profile.getPID());
        cv.put(PAYEE_ID, payee.getPayeeID());
        cv.put(PAYEE_NAME, payee.getPayeeName());
        db.insert(PAYEES_TABLE, null, cv);
        //db.close();
        return 0;
    }


    public int getSavingsCountForBranchAtDate(String branch,String date) {
        int count = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REPORT_OFFICE_BRANCH + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branch), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );



        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return count;
    }

    public double getTotalSavingsForBranchAtDate(String branchName, String theDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = REPORT_OFFICE_BRANCH + "=? AND " + REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(theDate)};

        Cursor cursor = db.rawQuery(
                "select sum ("+ REPORT_AMOUNT +") from " + DAILY_REPORT_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }


    /*public long saveNewTransaction11(int profileID, int customerId, long skylightNo, Transaction.TRANSACTION_TYPE transactionType, double amount,  double skylightBalance, String bank, long bankNo, String bankName, String transactionId, String paystackRefId, String status,String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        Transaction transaction = new Transaction();
        co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
        Customer customer = new Customer();
        Profile profile = new Profile();
        Account account = new Account();
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_ID, profile.getPID());
        cv.put(CUSTOMER_ID, customer.getCusUID());
        cv.put(TRANSACTIONS_TYPE, String.valueOf(transaction.getTransactionType()));
        cv.put(ACCOUNT_NAME, account.getAccountName());
        cv.put(ACCOUNT_BANK, account.getBankName());
        cv.put(ACCOUNT_NO, account.getSkyLightAcctNo());
        cv.put(BANK_ACCT_NO, account.getBankAcct_No());
        cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
        cv.put("Paystack Ref", transaction2.getReference());
        cv.put(TRANSACTION_AMOUNT, amount);
        cv.put(TRANSACTION_ID, transaction.getTransactionID());
        cv.put(TIMESTAMP, date);
        cv.put(TRANSACTION_STATUS, status);

        if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
            cv.put(SENDING_ACCOUNT, transaction.getSendingAccount());
            cv.put(DESTINATION_ACCOUNT, transaction.getDestinationAccount());
            cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
            cv.put(TRANSACTION_PAYEE,transaction.getPayee());
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            cv.putNull(DESTINATION_ACCOUNT);
            cv.put(ACCOUNT_NO, account.getSkyLightAcctNo());
            cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
            cv.put(TRANSACTION_PAYEE, transaction.getPayee());
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            cv.put(ACCOUNT_NAME, account.getAccountName());
            cv.put(ACCOUNT_BANK, account.getBankName());
            cv.put(ACCOUNT_NO, account.getSkyLightAcctNo());
            cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
            cv.put(BANK_ACCT_NO, account.getBankAcct_No());
            cv.putNull(TRANSACTION_PAYEE);
        }
        else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.BORROWING) {
            cv.put(ACCOUNT_NAME, account.getAccountName());
            cv.put(ACCOUNT_BANK, account.getBankName());
            cv.put(ACCOUNT_NO, account.getSkyLightAcctNo());
            cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
            cv.put(BANK_ACCT_NO, account.getBankAcct_No());
            db.insert(TRANSACTIONS_TABLE, null, cv);
            //db.close();
        }
        return Long.parseLong(transactionId);
    }*/

    /*public long saveNewTransaction2(Profile userProfile, String accountNo, Transaction transaction) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
            Customer customer = new Customer();
            Account account = new Account();
            double amount = transaction.getAmount();
            String date = transaction.getDate();

            ContentValues cv = new ContentValues();
            cv.put(TRANSACTION_PROF_ID, userProfile.getPID());
            cv.put(TRANSACTION_CUS_ID, customer.getCusUID());
            cv.put(ACCOUNT_NAME, account.getAccountName());
            cv.put(ACCOUNT_NO, account.getSkyLightAcctNo());
            cv.put("Paystack Ref", transaction2.getReference());
            cv.put(TRANSACTION_AMOUNT, amount);
            cv.put(TRANSACTION_ID, transaction.getTransactionID());
            cv.put(TIMESTAMP, date);
            cv.put(TRANS_TYPE, transaction.getTransactionType().toString());

            if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                cv.put(SENDING_ACCOUNT, transaction.getSendingAccount());
                cv.put(DESTINATION_ACCOUNT, transaction.getDestinationAccount());
                cv.putNull(TRANSACTION_PAYEE);
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
                cv.putNull(SENDING_ACCOUNT);
                cv.putNull(DESTINATION_ACCOUNT);
                cv.put(TRANSACTION_PAYEE, transaction.getPayee());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                cv.putNull(SENDING_ACCOUNT);
                cv.putNull(DESTINATION_ACCOUNT);
                cv.putNull(TRANSACTION_PAYEE);
            }
            db.insert(TRANSACTIONS_TABLE, null, cv);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }*/

    public void insertCustomer11(int profileID1, int customerID, long bizID, int marketID, String uSurname, String uFirstName, String uPhoneNumber, String uEmail, String dateOfBirth, String selectedGender, String uAddress, String selectedState, String s, String joinedDate, String uUserName, String uPassword, Uri mImageUri, String status) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //insertProfilePicture(profileID, customerID, profilePicture);
        ContentValues contentValues = new ContentValues();


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_PROF_ID, profileID1);
        cv.put(CUSTOMER_ID, customerID);
        cv.put(CUS_BIZ_ID1, bizID);
        cv.put(CUSTOMER_MARKET_ID, marketID);
        cv.put(CUSTOMER_SURNAME, uSurname);
        cv.put(CUSTOMER_FIRST_NAME, uFirstName);
        cv.put(CUSTOMER_PHONE_NUMBER, uPhoneNumber);
        cv.put(CUSTOMER_EMAIL_ADDRESS, uEmail);
        cv.put(CUSTOMER_DOB, dateOfBirth);
        cv.put(CUSTOMER_ADDRESS, uAddress);
        cv.put(CUSTOMER_CITY, selectedState);
        cv.put(CUSTOMER_GENDER, selectedGender);
        cv.put(CUSTOMER_DATE_JOINED, joinedDate);
        cv.put(CUSTOMER_STATUS, status);
        cv.put(CUSTOMER_USER_NAME, uUserName);
        cv.put(CUSTOMER_PASSWORD, uPassword);

        ContentValues pictureValues = new ContentValues();
        pictureValues.put(CUS_ID_PIX_KEY, customerID);
        pictureValues.put(PROFID_FOREIGN_KEY_PIX, profileID1);
        pictureValues.put(PICTURE_URI, String.valueOf(mImageUri));
        db.insertOrThrow(PICTURE_TABLE, null, pictureValues);

        ContentValues doBValue = new ContentValues();
        doBValue.put(B_PROF_ID, profileID1);
        doBValue.put(B_DOB, dateOfBirth);
        db.insertOrThrow(BIRTHDAY_TABLE, null, doBValue);
        uPassword = PasswordHelpers.passwordHash(uPassword);

        ContentValues passwordValue = new ContentValues();
        passwordValue.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID1);
        passwordValue.put(CUS_ID_PASS_KEY, customerID);
        passwordValue.put(PASSWORD, uPassword);
        db.insertOrThrow(PASSWORD_TABLE, null, passwordValue);
        db.insert(CUSTOMER_TABLE, null, cv);

    }



    public long insertCustomer11(int profileID, int customerID, String lastName, String firstName, String phoneNumber, String email, String dob, String gender, String address, String state, String nextOfKin, String dateJoined, String userName, String password, Uri profilePicture, String machine) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //insertProfilePicture(profileID, customerID, profilePicture);
        ContentValues contentValues = new ContentValues();
        //password = PasswordHelpers.passwordHash(password);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_PROF_ID, profileID);
        cv.put(CUSTOMER_ID, customerID);
        cv.put(CUSTOMER_SURNAME, lastName);
        cv.put(CUSTOMER_FIRST_NAME, firstName);
        cv.put(CUSTOMER_PHONE_NUMBER, phoneNumber);
        cv.put(CUSTOMER_EMAIL_ADDRESS, email);
        cv.put(CUSTOMER_DOB, dob);
        cv.put(CUSTOMER_ADDRESS, address);
        cv.put(CUSTOMER_GENDER, gender);
        cv.put(CUSTOMER_DATE_JOINED, dateJoined);
        cv.put(CUSTOMER_USER_NAME, userName);
        cv.put(CUSTOMER_PASSWORD, password);

        ContentValues pictureValues = new ContentValues();
        pictureValues.put(PROFID_FOREIGN_KEY_PIX, profileID);
        pictureValues.put(PICTURE_URI, String.valueOf(profilePicture));
        db.insertOrThrow(PICTURE_TABLE, null, pictureValues);

        ContentValues doBValue = new ContentValues();
        doBValue.put(B_PROF_ID, profileID);
        doBValue.put(B_DOB, dob);
        db.insertOrThrow(BIRTHDAY_TABLE, null, doBValue);

        ContentValues passwordValue = new ContentValues();
        passwordValue.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID);
        passwordValue.put(PASSWORD, password);
        db.insertOrThrow(PASSWORD_TABLE, null, passwordValue);
        db.insert(CUSTOMER_TABLE, null, cv);

        return customerID;
    }





    public long saveNewCustomer(int profileID, int customerId, String lastName, String firstName, String phoneNumber, String email, String dob, String gender, String address, String state, String nextOfKin, String dateJoined, String userName, String password, String profilePicture, int roleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_PROF_ID, profileID);
        cv.put(CUSTOMER_ID, customerId);
        cv.put(CUSTOMER_SURNAME, lastName);
        cv.put(CUSTOMER_FIRST_NAME, firstName);
        cv.put(CUSTOMER_PHONE_NUMBER, phoneNumber);
        cv.put(CUSTOMER_EMAIL_ADDRESS, email);
        cv.put(CUSTOMER_DOB, dob);
        cv.put(CUSTOMER_ADDRESS, address);
        cv.put(CUSTOMER_GENDER, gender);
        cv.put(CUSTOMER_DATE_JOINED, dateJoined);
        cv.put(CUSTOMER_USER_NAME, userName);
        cv.put(CUSTOMER_PASSWORD, password);

        ContentValues pictureValues = new ContentValues();
        pictureValues.put(PROFID_FOREIGN_KEY_PIX, profileID);
        pictureValues.put(PICTURE_URI, profilePicture);
        db.insertOrThrow(PICTURE_TABLE, null, pictureValues);

        ContentValues doBValue = new ContentValues();
        doBValue.put(B_PROF_ID, profileID);
        doBValue.put(B_DOB, dob);
        db.insertOrThrow(BIRTHDAY_TABLE, null, doBValue);

        ContentValues passwordValue = new ContentValues();
        passwordValue.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID);
        passwordValue.put(PASSWORD, password);
        db.insertOrThrow(PASSWORD_TABLE, null, passwordValue);
        db.insert(CUSTOMER_TABLE, null, cv);
        //db.close();

        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/

        return profileID;
    }



    public long saveNewProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_ID, profile.getPID());
        cv.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
        cv.put(PROFILE_SURNAME, profile.getProfileLastName());
        cv.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        cv.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        cv.put(PROFILE_EMAIL, profile.getProfileEmail());
        cv.put(PROFILE_DOB, profile.getProfileDob());
        cv.put(PROFILE_GENDER, profile.getProfileGender());
        cv.put(PROFILE_ADDRESS, profile.getProfileAddress());
        cv.put(PROFILE_NIN, profile.getProfileState());
        cv.put(PROFILE_STATE, profile.getProfileIdentity());
        cv.put(PROFILE_OFFICE, profile.getProfOfficeName());
        cv.put(PROFILE_DATE_JOINED, profile.getProfileDateJoined());
        cv.put(PROFILE_ROLE, profile.getProfileRole());
        cv.put(PROFILE_USERNAME, profile.getProfileUserName());
        cv.put(PROFILE_PASSWORD, profile.getProfilePassword());
        cv.put(PROFILE_STATUS, profile.getProfileStatus());
        cv.put(PROFILE_NEXT_OF_KIN, profile.getNextOfKin());
        ContentValues pictureValues = new ContentValues();

        pictureValues.put(PROFILE_PIC_ID, profile.getPID());
        pictureValues.put(PROFID_FOREIGN_KEY_PIX, profile.getPID());
        pictureValues.put(PICTURE_URI, String.valueOf(profile.getProfilePicture()));
        db.insertOrThrow(PICTURE_TABLE, null, pictureValues);

        ContentValues passwordValue = new ContentValues();
        passwordValue.put(PROF_ID_FOREIGN_KEY_PASSWORD, profile.getPID());
        passwordValue.put(PASSWORD, profile.getProfilePassword());
        db.insertOrThrow(PASSWORD_TABLE, null, passwordValue);

        ContentValues birthdayValue = new ContentValues();
        birthdayValue.put(B_PROF_ID, profile.getPID());
        birthdayValue.put(B_DOB, profile.getProfileDob());
        db.insertOrThrow(BIRTHDAY_TABLE, null, birthdayValue);
        db.close();

        return 0;
    }

    public void deleteSavings(int savingsID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = REPORT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(savingsID)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, null,  selection, selectionArgs, null, null, null);

        db.delete(DAILY_REPORT_TABLE,
                selection, selectionArgs);

    }

    public long insertInterestRate10() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        LoanInterest interest = new LoanInterest();
        long interestId = interest.getLoanInterestId();
        double interestRate = interest.getLoanInterestId();
        contentValues.put(INTEREST_ID, interestId);
        contentValues.put(INTEREST_RATE, interestRate);
        return sqLiteDatabase.insert(INTEREST_TABLE, null, contentValues);
    }






    private void getDailyReportsFromCursor(ArrayList<CustomerDailyReport> customerDailyReports, Cursor cursor) {

        while (cursor.moveToNext()) {

            int profileId = cursor.getInt(0);
            int customerId = cursor.getInt(1);
            int packageId = cursor.getInt(3);
            double amount = cursor.getDouble(6);
            int numberOfDay = cursor.getInt(9);
            double total = cursor.getDouble(10);
            int daysRemaining = cursor.getInt(11);
            double amountRemaining = cursor.getDouble(12);
            String date = cursor.getString(13);
            String status = cursor.getString(14);


            customerDailyReports.add(new CustomerDailyReport(profileId, customerId, packageId, amount, numberOfDay, total, daysRemaining, amountRemaining, date, status));
        }

    }




    public ArrayList<CustomerDailyReport> getPackageSavings1(int packageID) {
        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {REPORT_DATE, REPORT_NUMBER_OF_DAYS, REPORT_TOTAL,REPORT_CODE,REPORT_DAYS_REMAINING,REPORT_AMOUNT_COLLECTED_SO_FAR};
        String selection = REPORT_PACK_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(packageID)};

        Cursor cursor = sqLiteDatabase.query(DAILY_REPORT_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getDailyReportsFromCursor(customerDailyReports, cursor);

                cursor.close();
            }

        sqLiteDatabase.close();

        return customerDailyReports;
    }

    public ArrayList<CustomerDailyReport> getPackageSavings2(int packageID) {

        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_PACK_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(packageID)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, null,  selection, selectionArgs, null,
                PACKAGE_ID, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getDailyReportsFromCursor(customerDailyReports, cursor);

                cursor.close();
            }

        db.close();

        return customerDailyReports;
        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;*/
    }
    public ArrayList<CustomerDailyReport> getPackageSavingsForProfile(int profileID) {
        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getDailyReportsFromCursor(customerDailyReports, cursor);

                cursor.close();
            }

        db.close();

        return customerDailyReports;
    }
    public ArrayList<CustomerDailyReport> getPackageSavingsForCustomer(int customerID) {
        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_CUS_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(DAILY_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getDailyReportsFromCursor(customerDailyReports, cursor);

                cursor.close();
            }

        db.close();

        return customerDailyReports;
    }



    public ArrayList<CustomerDailyReport> getAllReportsAdmin() {
        ArrayList<CustomerDailyReport> reports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DAILY_REPORT_TABLE, null, null, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getReportsFromCursorAdmin(reports, cursor);

                cursor.close();
            }

        db.close();

        return reports;
    }
    public ArrayList<CustomerDailyReport> getSavingsFromCurrentProfile(int profileID) {
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, null, selection
                        , selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getReportsFromCursorAdmin(dailyReports, cursor);
                //getTotalSavingsForCustomer(profileID);

                cursor.close();
            }

        db.close();

        return dailyReports;

    }
    public ArrayList<CustomerDailyReport> getSavingsFromCurrentCustomer(int customerID) {
        ArrayList<CustomerDailyReport> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_CUS_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(DAILY_REPORT_TABLE, null, selection,selectionArgs , null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getReportsFromCursorAdmin(dailyReports, cursor);
                //getTotalSavingsForCustomer(profileID);

                cursor.close();
            }

        db.close();

        return dailyReports;
    }

    private void getReportsFromCursorAdmin(ArrayList<CustomerDailyReport> reports, Cursor cursor) {
        int id = cursor.getInt(2);
        int profileId = cursor.getInt(0);
        int customerId = cursor.getInt(1);
        double amount = cursor.getDouble(6);
        int numberOfDay = cursor.getInt(9);
        double total = cursor.getDouble(10);
        int daysRemaining = cursor.getInt(11);
        double amountRemaining = cursor.getDouble(12);
        String date = cursor.getString(13);
        String status = cursor.getString(14);

        reports.add(new CustomerDailyReport(profileId, customerId, id, amount, numberOfDay, total, daysRemaining, amountRemaining, date, status));

    }
    public void updateInterestRate(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues interestValues = new ContentValues();
        LoanInterest interest = new LoanInterest();
        long interestId = interest.getLoanInterestId();
        double interestRate = interest.getLoanInterestId();
        LocalDate today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }
        interestValues.put(INTEREST_ID, interestId);
        interestValues.put(INTEREST_RATE, interestRate);
        //interestValues.put("update date", valueOf(today));
        String selection = INTEREST_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        db.update(INTEREST_TABLE, interestValues, selection, selectionArgs);
        //db.close();

    }



    public BigInteger getSavingsSumValue(){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(" + REPORT_TOTAL + ") as Total FROM " + DAILY_REPORT_TABLE, null)) {
            if (cursor.moveToFirst())
                sum = cursor.getColumnIndex(REPORT_TOTAL);
            //cursor.moveToFirst();
            //if(REPORT_TOTAL !=null)

            return BigInteger.valueOf(sum);
        }
    }


    public BigInteger getLoanSumValueAdmin(){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(" + LOAN_AMOUNT + ") as Total FROM " + LOAN_TABLE, null)) {
            if (cursor.moveToFirst())
                //cursor.moveToFirst();
                sum = cursor.getColumnIndex(REPORT_TOTAL);
            return BigInteger.valueOf(sum);
        }
    }

    public BigInteger getSavingsSumValueOfCustomer(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Cursor cur = db.rawQuery("SELECT SUM(" + (REPORT_TOTAL) + ") FROM " + DAILY_REPORT_TABLE, null);
        String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME, PROFILE_PHONE};

        String selection = REPORT_CUS_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ REPORT_TOTAL +")) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(REPORT_TOTAL);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);
    }


    /*public String fetchAllReportChart(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //ArrayList<String> array_list = new ArrayList<String>();
        String monthQuery="SELECT strftime('%m',REPORT_DATE) as Month FROM "+  DAILY_REPORT_TABLE ;
        try (Cursor cursor = sqLiteDatabase.rawQuery(monthQuery, new String []{ REPORT_DATE})) {
            if (cursor.moveToFirst()) {
                    monthQuery = cursor.getColumnIndexOrThrow(PACKAGE_TOTAL_VALUE);
                    return monthQuery;
            }
        }catch (Exception ex) {
            return null;
        }
        return null;
    }*/


    @SuppressLint("Recycle")
    public BigInteger getTotalOfSavingsForCustomer(int customerID) {
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_DAILY_AMOUNT +")) FROM " + DAILY_REPORT_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(SO_DAILY_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }

    public void deleteUSer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = new String[]{valueOf(id)};
        String selection = PROFILE_ID + "=?";
        String selectionC = CUSTOMER_PROF_ID + "=?";
        String selectionD = REPORT_PROF_ID_FK + "=?";
        String selectionT = TRANSACTION_PROF_ID + "=?";
        String selectionM = MESSAGE_PROF_ID + "=?";
        String selectionL = LOAN_PROF_ID + "=?";
        String selectionP = PACKAGE_PROFILE_ID_FOREIGN + "=?";
        String selectionCode = CODE_PROFILE_ID + "=?";
        db.delete(CUSTOMER_TABLE, selectionC, selectionArgs);
        db.delete(PACKAGE_TABLE, selectionP, selectionArgs);
        db.delete(MESSAGE_TABLE, selectionM, selectionArgs);
        db.delete(CODE_TABLE, selectionCode, selectionArgs);
        db.delete(PROFILES_TABLE, selection, selectionArgs);
        db.delete(DAILY_REPORT_TABLE, selectionD, selectionArgs);
        db.delete(LOAN_TABLE, selectionL, selectionArgs);
        db.delete(TRANSACTIONS_TABLE, selectionT, selectionArgs);
        db.close();

    }

    public void updateUser1(Profile profile,Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = profile.getPID();
        ContentValues values = new ContentValues();
        values.put(PROFILE_SURNAME, profile.getProfileLastName());
        values.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
        values.put(PROFILE_EMAIL, profile.getProfileEmail());
        values.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
        values.put(PROFILE_ADDRESS, valueOf(profile.getProfileAddress()));
        values.put(PROFILE_DOB, profile.getProfileDob());
        values.put(PROFILE_GENDER, profile.getProfileGender());
        values.put(PROFILE_NEXT_OF_KIN, profile.getNextOfKin());
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        db.update(PROFILES_TABLE, values, selection, selectionArgs);
        String selection2 = CUSTOMER_ID + "=? AND " + CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs2 = new String[]{valueOf(customer.getCusUID()), valueOf(profile.getPID())};

        ContentValues valuesus = new ContentValues();
        valuesus.put(CUSTOMER_ADDRESS, valueOf(profile.getProfileAddress()));
        valuesus.put(CUSTOMER_SURNAME, customer.getCusSurname());
        valuesus.put(CUSTOMER_FIRST_NAME, customer.getCusFirstName());
        valuesus.put(CUSTOMER_PHONE_NUMBER, customer.getCusPhoneNumber());
        valuesus.put(CUSTOMER_EMAIL_ADDRESS, customer.getCusEmail());
        valuesus.put(CUSTOMER_ADDRESS, valueOf(customer.getCusAddress()));
        valuesus.put(CUSTOMER_OFFICE, customer.getCusOfficeBranch());
        valuesus.put(CUSTOMER_USER_NAME, customer.getCusUserName());
        valuesus.put(CUSTOMER_PASSWORD, customer.getCusPassword());
        db.update(CUSTOMER_TABLE, valuesus, selection2, selectionArgs2);
        db.close();

    }


    public void UpdateSavingsStatus(String status, int savingsID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            cVals.put(REPORT_STATUS, status);
            String selection = REPORT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(savingsID)};
            long result = db.update(DAILY_REPORT_TABLE, cVals, selection, selectionArgs);
            Log.d("Savings Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    /*public ArrayList<Profile> getAllUsers() {
        ArrayList<Profile> userModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + PROFILES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        //db.close();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Profile userModel = new Profile();
                userModel.setId(Long.parseLong(c.getString(PROFILE_ID_COLUMN)));
                userModel.setSurname(c.getString(USER_SURNAME_COLUMN));
                userModel.setFirstName(c.getString(USER_FIRSTNAME_COLUMN));
                userModel.setPhoneNumber(c.getString(USER_PHONE_COLUMN));
                userModel.setEmail(c.getString(USER_EMAIL_COLUMN));
                userModel.setRole(c.getString(USER_ROLE_COLUMN));
                userModel.setUserName(c.getString(USERNAME_COLUMN));
                userModel.setPin(c.getString(USER_PASSWORD_COLUMN));

                //getting user savings where id = id from savings table
                String selectRecordQuery = null;
                selectRecordQuery = "SELECT  * FROM " + PROFILES_TABLE + " WHERE " + PROFILE_ID + " = " + userModel.getUId();
                Log.d("oppp", Objects.requireNonNull(selectRecordQuery));
                //SQLiteDatabase dbhobby = this.getReadableDatabase();
                @SuppressLint("Recycle") Cursor cRecords = db.rawQuery(selectRecordQuery, null);

                if (cRecords.moveToFirst()) {
                    do {
                        userModel.setCustomDailyReport1(cRecords.getString(REPORT_NUMBER_COLUMN));
                    } while (cRecords.moveToNext());
                }

                String selectTransactionQuery = null;
                selectTransactionQuery = "SELECT  * FROM " + TABLE_USER_TRANSACTIONS + " WHERE " + PROFILE_ID + " = " + userModel.getUId();
                ;
                //SQLiteDatabase dbCity = this.getReadableDatabase();
                @SuppressLint("Recycle") Cursor transactions = db.rawQuery(selectTransactionQuery, null);

                if (transactions.moveToFirst()) {
                    do {
                        userModel.setTransaction(transactions.getString((TRANSACTION_ID_COLUMN)));
                    } while (transactions.moveToNext());
                }
                String selectLoanQuery = null;
                selectLoanQuery = "SELECT  * FROM " + LOAN_TABLE + " WHERE " + PROFILE_ID + " = " + userModel.getUId();
                ;
                //SQLiteDatabase dbCity = this.getReadableDatabase();
                @SuppressLint("Recycle") Cursor loans = db.rawQuery(selectLoanQuery, null);

                if (loans.moveToFirst()) {
                    do {
                        userModel.setLoan(loans.getString((LOAN_AMOUNT_COLUMN)));
                    } while (loans.moveToNext());
                }
                String selectPackagesQuery = null;
                selectPackagesQuery = "SELECT  * FROM " + PACKAGE_TABLE + " WHERE " + PROFILE_ID + " = " + userModel.getuID();
                ;
                //SQLiteDatabase dbCity = this.getReadableDatabase();
                @SuppressLint("Recycle") Cursor packages = db.rawQuery(selectPackagesQuery, null);

                if (transactions.moveToFirst()) {
                    do {
                        userModel.setTransaction(transactions.getString(transactions.getColumnIndex(TRANSACTION_AMOUNT)));
                    } while (transactions.moveToNext());
                }

                // adding to Students list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }*/
    /*CUSTOMER_ID
                + " = " + profileID

    db.update(INTEREST_TABLE, interestValues, INTEREST_ID + " = ?", new String[]{valueOf(id)});*/

    public Cursor getSpinnerSuperFromCursor(ArrayList<UserSuperAdmin> superAdminArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int superID = cursor.getInt(0);
                String surname = cursor.getString(1);
                String firstName = cursor.getString(3);
                superAdminArrayList.add(new UserSuperAdmin(superID, surname, firstName));

            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<UserSuperAdmin> getAllSuperSpinner() {
        ArrayList<UserSuperAdmin> superAdminArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(SUPER_ADMIN_TABLE, null, null, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSpinnerSuperFromCursor(superAdminArrayList, cursor);
                cursor.close();
            }

        db.close();


        return superAdminArrayList;
    }

    //@Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(DAILY_REPORT_TABLE);
            UriMatcher sURIMatcher =
                    new UriMatcher(UriMatcher.NO_MATCH);

            int uriType = sURIMatcher.match(uri);

            switch (uriType) {
                case 2:
                    queryBuilder.appendWhere(REPORT_ID + "="
                            + uri.getLastPathSegment());
                    break;
                case 13:
                    queryBuilder.appendWhere(REPORT_DATE + "="
                            + uri.getLastPathSegment());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI");
            }

            Cursor cursor = queryBuilder.query(this.getReadableDatabase(),
                    projection, selection, selectionArgs, null, null,
                    sortOrder);
            //cursor.setNotificationUri(getContext().getContentResolver(),
            //       uri);
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public void updateRole(int profileID,int role) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ROLE_ID",profileID);
        contentValues.put("ROLE", role);
        String selection = "ROLE_ID" + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        sqLiteDatabase.update("ROLES", contentValues, selection, selectionArgs);
        sqLiteDatabase.close();

    }




    public ArrayList<String> getPackage() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from PACKAGE_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(0));
                res.moveToNext();
            }
        }
        return array_list;

    }

    public ArrayList<String> getReportNOS() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from DAILY_REPORT_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(2));
                res.moveToNext();
            }
        }
        return array_list;
    }

    public ArrayList<String> getMessages() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor res = db.rawQuery("select * from MESSAGE_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(2));
                res.moveToNext();
            }
        }
        return array_list;
    }










    private void getPackCustomerSpinner(ArrayList<SkyLightPackage> packages, Cursor cursor) {
        try {

            try {
                if (cursor != null) {
                    while (cursor.moveToNext()) {

                        int id = cursor.getInt(0);
                        String type = cursor.getString(5);
                        double balance = cursor.getDouble(11);

                        packages.add(new SkyLightPackage(id, type, balance));
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SkyLightPackage> getPacksForCustomerSpinner(int customerID) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackCustomerSpinner(packages,cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return packages;
    }
    public ArrayList<SkyLightPackage> getPacksFromCurrentCustomer(int customerID) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages,cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return packages;
    }

    public ArrayList<SkyLightPackage> getAllPackagesProfile(int profileID) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(PACKAGE_TABLE, null, PACKAGE_PROFILE_ID_FOREIGN
                        + " = " + profileID, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return packages;

    }
    public ArrayList<SkyLightPackage> getCustomerIncompletePack(int customerID, String inProgress) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String incomplete = null;
        inProgress=incomplete;
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(inProgress)};
        @SuppressLint("Recycle") Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return packages;

    }
    public boolean updateSavingsWithCode(int reportID, String code, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPORT_CODE, code);
        contentValues.put(REPORT_STATUS, status);
        String selection = REPORT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID)};
         db.update(DAILY_REPORT_TABLE, contentValues, selection, selectionArgs);
        return true;
    }
    public ArrayList<CustomerDailyReport> getCustomerIncompleteSavings(int customerID, String unconfirmed) {
        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String incomplete = null;
        String selection = REPORT_CUS_ID_FK + "=? AND " + PACKAGE_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(unconfirmed)};
        @SuppressLint("Recycle") Cursor cursor = db.query(DAILY_REPORT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDailyReportsFromCursor(customerDailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customerDailyReports;

    }
    public ArrayList<SkyLightPackage> getPackageEndingToday1(String dateOfToday) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfToday)};
        @SuppressLint("Recycle") Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return packages;
    }

    public ArrayList<SkyLightPackage> getPackageEndingIn3Days(String dateOfTomorrow) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfTomorrow)};
        @SuppressLint("Recycle") Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return packages;
    }

    public ArrayList<SkyLightPackage> getPackageEnding7Days(String dateOfSevenDays) {
        ArrayList<SkyLightPackage> packages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfSevenDays)};
        @SuppressLint("Recycle") Cursor cursor = db.query(PACKAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPackagesFromCursorAdmin(packages, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return packages;
    }

    public int getPackEndingToDayCount(String dateOfToday) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = PACKAGE_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfToday)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;
    }
    public ArrayList<Payee> getPayeesFromCurrentProfile(int profileID) {
        ArrayList<Payee> payees = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYEE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(PAYEES_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPayeesFromProfileCursor(profileID, payees, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return payees;
    }

    public ArrayList<Payee> getPayeesFromCurrentCustomer(int customerID) {
        ArrayList<Payee> payees = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYEE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(PAYEES_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPayeesFromCustomerCursor(customerID, payees, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return payees;
    }

    private Cursor getPayeesFromCustomerCursor(int customerID, ArrayList<Payee> payees, Cursor cursor) {
        while (cursor.moveToNext()) {

            if (customerID == cursor.getLong(1)) {
                int profID = cursor.getInt(2);
                int payeeID = cursor.getInt(0);
                String payeeName = cursor.getString(3);

                payees.add(new Payee(payeeID,profID, payeeName));
            }
        }return cursor;
    }

    private Cursor getPayeesFromProfileCursor(int profileID, ArrayList<Payee> payees, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                if (profileID == cursor.getLong(2)) {
                    int cusID = cursor.getInt(1);
                    int payeeID = cursor.getInt(0);
                    String payeeName = cursor.getString(3);

                    payees.add(new Payee(payeeID, payeeName, cusID));
                }
            }return  cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }









    /*public ArrayList<String> getAllContacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor res = db.rawQuery("select * from PROFILES_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(res.getColumnIndex(USER_SURNAME)));
                res.moveToNext();
            }
        }
        return array_list;
    }*/

    public long insertRole(int profileID,String role,String rolePhoneNo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("role_ID", profileID);
        //contentValues.put("roleUserName", profileID);
        //contentValues.put("rolePassword", role);
        contentValues.put("rolePhoneNo", rolePhoneNo);
        contentValues.put("role", role);
        return sqLiteDatabase.insert("ROLES", null, contentValues);

    }

    public long insertRole(String role,int id,String rolerPhoneNo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ROLE", role);
        contentValues.put("role_ID", id);
        contentValues.put("rolePhoneNo", rolerPhoneNo);
        return sqLiteDatabase.insert("ROLES", null, contentValues);
    }




    //SELECT METHODS
    public Cursor getRoles() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM ROLES;", null);

    }

    public void deleteProfile(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(id)};
        db.delete(PROFILES_TABLE, selection,selectionArgs);

        String selectionCus = CUSTOMER_PROF_ID + "=?";
        String[] selectionArgsCus = new String[]{valueOf(id)};
        db.delete(CUSTOMER_TABLE, selectionCus,selectionArgsCus);

    }

    public int getCustomerTotalPackageCount(int customerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PACKAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(PACKAGE_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;


    }

    protected long insertUserAccount(String name, BigDecimal balance, int typeId) {
        return 0;
    }
    public boolean addProduct(MarketStock prod) {
        try{
            this.getMarketStock(prod.getName());
        }catch (SQLException e){
            return (db.insert(MARKET_STOCK_TABLE, null, contentValuesFrom(prod)) >= 0);
        }

        return false;

    }



    private ContentValues contentValuesFrom(MarketStock p) {
        ContentValues content = new ContentValues();
        content.put(MarketStock.KEY_PROD_NAME, p.getName());
        content.put(MarketStock.KEY_PROD_STOCK, p.getStock());
        content.put(MarketStock.KEY_PROD_COST, p.getCost());
        content.put(MarketStock.KEY_PROD_PRICE, p.getPrice());
        content.put(MarketStock.KEY_PROD_BOUGHT, p.getBoughtUnits());
        content.put(MarketStock.KEY_PROD_PHOTO, p.getPhotoAsByteArray());

        return content;
    }

    public boolean deleteMarketStock(MarketStock prod) {
        return db.delete(MARKET_STOCK_TABLE,
                KEY_PROD_NAME + " = '" + prod.getName() + "'", null) > 0;
    }

    private String getOrderBy(){
        String sortBy = PreferenceManager.getDefaultSharedPreferences(mContext).getString(StockSettingAct.PREF_SORT_BY, "Name");

        if (sortBy.equals("Name")) {
            return KEY_PROD_NAME + " ASC";
        } else if (sortBy.equals("Benefits")) {
            return KEY_PROD_BENEFIT + " DESC";
        } else if (sortBy.equals("Sales")) {
            return KEY_PROD_SALES + " DESC";
        }

        return KEY_PROD_NAME + " ASC";
    }
    public MarketStock getMarketStock(String name) throws SQLException {
        Cursor cursor = db.query(MARKET_STOCK_TABLE, KEYS_PROD, KEY_PROD_NAME
                + " = '" + name + "'", null, null, null, null);

        if (cursor.getCount() == 0 || !cursor.moveToFirst())
            throw new SQLException("No Product found for condition: "
                    + KEY_PROD_NAME + " = '" + name + "'");

        return productFrom(cursor);
    }

    public List<MarketStock> getProductsList(String filter) {
        ArrayList<MarketStock> products = new ArrayList<MarketStock>();
        Cursor cursor;

        if(filter != null){
            cursor = db.query(MARKET_STOCK_TABLE, KEYS_PROD, KEY_PROD_NAME+" MATCH ?", processFilter(filter), null, null, getOrderBy());
        }else{
            cursor = db.query(MARKET_STOCK_TABLE, KEYS_PROD, null, null, null, null, getOrderBy());
        }

        if (cursor.moveToFirst())
            do {
                products.add(productFrom(cursor));
            } while (cursor.moveToNext());

        return products;
    }

    public ArrayList<MarketStock> getTopNBenefits (int n){
        ArrayList<MarketStock> products = new ArrayList<MarketStock>();
        Cursor cursor;

        cursor = db.query(MARKET_STOCK_TABLE, KEYS_PROD, null, null, null, null, KEY_PROD_BENEFIT+" DESC LIMIT "+n);

        if (cursor.moveToFirst())
            do {
                products.add(productFrom(cursor));
            } while (cursor.moveToNext());

        return products;
    }


    public ArrayList<MarketStock> getTopNSales(int n){
        ArrayList<MarketStock> marketStocks = new ArrayList<MarketStock>();
        Cursor cursor;

        cursor = db.query(MARKET_STOCK_TABLE, KEYS_PROD, null, null, null, null, KEY_PROD_SALES+" DESC LIMIT "+n);

        if (cursor.moveToFirst())
            do {
                marketStocks.add(productFrom(cursor));
            } while (cursor.moveToNext());

        return marketStocks;
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
            Log.i(DBHelper.class.getName(),
                    "Database is open in rw-mode mode :)");
        } catch (SQLException ex) {
            db = dbHelper.getReadableDatabase();
            Log.i(DBHelper.class.getName(),
                    "Database is open in read-only mode!!!");
        }
    }

    private String[] processFilter(String filter){
        String[] filters = filter.split("\\s+");
        filter = "";
        for (String f : filters) {
            filter += " *"+f+"*";
        }

        Log.i(MarketStock.class.getName(), "Filter on query: "+filter);
        return new String[] {filter};
    }

    private MarketStock productFrom(Cursor cursor) {
        String name = cursor.getString(MarketStock.PROD_NAME_COL);
        int stock = cursor.getInt(MarketStock.PROD_STOCK_COL);
        double cost = cursor.getDouble(MarketStock.PROD_COST_COL);
        double price = cursor.getDouble(MarketStock.PROD_PRICE_COL);
        int boughtUnits = cursor.getInt(MarketStock.PROD_BOUGHT_COL);

        byte[] imageBlob = cursor.getBlob(MarketStock.PROD_PHOTO_COL);
        Bitmap photo = BitmapFactory.decodeByteArray(imageBlob, 0,
                imageBlob.length);

        MarketStock ret = null;
        try {
            ret = new MarketStock(name, stock, cost, price, photo, boughtUnits);
        } catch (StockAttrException e) {}

        return ret;
    }

    public boolean updateProduct(MarketStock prod) {
        return db.update(MARKET_STOCK_TABLE, contentValuesFrom(prod), KEY_PROD_NAME
                + " = '" + prod.getName() + "'", null) > 0;
    }
    private void getMarketsFromCursor(ArrayList<Market> marketArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int marketUserCount = cursor.getInt(11);
            int marketComCount = cursor.getInt(10);
            String marketName = cursor.getString(1);
            String marketType = cursor.getString(4);
            String marketTown = cursor.getString(5);
            String marketLGA = cursor.getString(6);
            String marketState = cursor.getString(7);
            double marketRevenue = cursor.getDouble(13);
            String marketStatus = cursor.getString(12);
            int marketLogo = cursor.getInt(9);
            marketArrayList.add(new Market(id, marketName, marketType, marketLGA, marketState, marketLogo,marketUserCount,marketComCount,marketRevenue,marketStatus));
        }


    }

    public ArrayList<Market> getAllMarketsForState(String currentState) {
        ArrayList<Market> stateMarkets = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MARKET_STATE + "=?";
        String[] selectionArgs = new String[]{valueOf(currentState)};

        Cursor cursor = db.query(MARKET_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getMarketsFromCursor(stateMarkets, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return stateMarkets;
    }
    public boolean deleteMarket(Market market) {
        return db.delete(MARKET_TABLE,
                MARKET_NAME + " = '" + market.getMarketName() + "'", null) > 0;
    }
    public int deleteMarketByID(int marketID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MARKET_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(marketID)};
        return db.delete(MARKET_TABLE, selection,selectionArgs);

    }
    private void getOfficeBranchCursor(ArrayList<OfficeBranch> officeBranchArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int officeID = cursor.getInt(0);
            String officeName = cursor.getString(2);
            String officeStatus = cursor.getString(6);
            officeBranchArrayList.add(new OfficeBranch(officeID, officeName,officeStatus));
        }
    }
    public ArrayList<OfficeBranch> getAllBranchOffices() {
        ArrayList<OfficeBranch> officeBranchArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {OFFICE_BRANCH_NAME};
        Cursor cursor = db.query(OFFICE_BRANCH_TABLE, columns, OFFICE_BRANCH_NAME, null, null,
                null, null);

        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getOfficeBranchCursor(officeBranchArrayList, cursor);
                cursor.close();
            }

        db.close();
        return officeBranchArrayList;
    }



}
