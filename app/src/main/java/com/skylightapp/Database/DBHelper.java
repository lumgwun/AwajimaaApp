package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.EmergReportNext;
import com.skylightapp.Classes.EmergencyReport;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.SkylightCash;
import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.Classes.TransactionGranting;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.RealEstate.Properties;
import com.skylightapp.RealEstate.PropertyImage;
import com.skylightapp.SuperAdmin.AdminBalance;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Bookings;
import com.skylightapp.Classes.Business;
import com.skylightapp.Classes.ConnectionFailedException;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.ImportantDates;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.LoanInterest;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PasswordHelpers;
import com.skylightapp.Classes.Payee;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Tellers.TellerCash;
import com.skylightapp.Transactions.BillModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.skylightapp.Admins.AdminBankDeposit.CREATE_ADMIN_DEPOSIT_TABLE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSITOR;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_ACCOUNT;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_ACCOUNT_NAME;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_AMOUNT;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_APPROVER;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_BANK;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_CONFIRMATION_DATE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_DATE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_DOC;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_ID;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_OFFICE_BRANCH;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_PROFILE_ID;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_TABLE;
import static com.skylightapp.Admins.AdminBankDeposit.DEPOSIT_TRANSACTION_STATUS;
import static com.skylightapp.Awards.Award.AWARD_ID;
import static com.skylightapp.Awards.Award.AWARD_TABLE;
import static com.skylightapp.Awards.Award.CREATE_AWARD_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_BALANCE;
import static com.skylightapp.Classes.Account.ACCOUNT_BANK;
import static com.skylightapp.Classes.Account.ACCOUNT_CUS_ID;
import static com.skylightapp.Classes.Account.ACCOUNT_NAME;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Account.ACCOUNT_PROF_ID;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPES_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE_INTEREST;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE_NAME;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE_ID;
import static com.skylightapp.Classes.Account.BANK_ACCT_BALANCE;
import static com.skylightapp.Classes.Account.BANK_ACCT_NO;
import static com.skylightapp.Classes.Account.CREATE_ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.CREATE_ACCOUNT_TYPE_TABLE;
import static com.skylightapp.Classes.AdminUser.ADMIN_PROFILE_ID;
import static com.skylightapp.Classes.Birthday.B_DOB;
import static com.skylightapp.Classes.Birthday.B_EMAIL;
import static com.skylightapp.Classes.Birthday.B_FIRSTNAME;
import static com.skylightapp.Classes.Birthday.B_PHONE;
import static com.skylightapp.Classes.Birthday.B_PROF_ID;
import static com.skylightapp.Classes.Birthday.B_SURNAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_LATLONG;
import static com.skylightapp.Classes.Customer.CUSTOMER_PROF_ID;
import static com.skylightapp.Classes.Customer.CUS_LOC_CUS_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ACCOUNT_NO_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_PACK_ID_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_CUS_ID_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_OFFICE_BRANCH;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_PROF_ID_FK;
import static com.skylightapp.Classes.CustomerManager.CREATE_WORKERS_TABLE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_PIX;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_PROF_ID;
import static com.skylightapp.Classes.CustomerManager.WORKER;
import static com.skylightapp.Classes.CustomerManager.WORKER_ID;
import static com.skylightapp.Classes.CustomerManager.WORKER_TABLE;
import static com.skylightapp.Classes.EmergReportNext.CREATE_EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LAT;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LATLNG;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LNG;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LOCID;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LOCTIME;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_REPORT_ID;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.Classes.EmergencyReport.CREATE_EMERGENCY_REPORT_TABLE;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_ADDRESS;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_LATLNG;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_PROF_ID;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_LOCID;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_LOCTIME;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_TYPE;
import static com.skylightapp.Classes.Loan.LOAN_ACCT_NO;
import static com.skylightapp.Classes.Loan.LOAN_CODE;
import static com.skylightapp.Classes.Loan.LOAN_CUS_ID;
import static com.skylightapp.Classes.Loan.LOAN_PACK_ID;
import static com.skylightapp.Classes.Loan.LOAN_PROF_ID;
import static com.skylightapp.Classes.Message.MESSAGE_BRANCH_OFFICE;
import static com.skylightapp.Classes.Message.MESSAGE_CUS_ID;
import static com.skylightapp.Classes.Message.MESSAGE_PROF_ID;
import static com.skylightapp.Classes.Message.MESSAGE_REPLY_MESSAGE_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_SUPERADMIN_ID;
import static com.skylightapp.Classes.Payee.PAYEE_CUS_ID;
import static com.skylightapp.Classes.Payee.PAYEE_PROF_ID;
import static com.skylightapp.Classes.Payment.CREATE_PAYMENT_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENTS_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENT_ACCOUNT;
import static com.skylightapp.Classes.Payment.PAYMENT_ACCOUNT_TYPE;
import static com.skylightapp.Classes.Payment.PAYMENT_ADMIN_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_AMOUNT;
import static com.skylightapp.Classes.Payment.PAYMENT_APPROVAL_DATE;
import static com.skylightapp.Classes.Payment.PAYMENT_APPROVER;
import static com.skylightapp.Classes.Payment.PAYMENT_CODE;
import static com.skylightapp.Classes.Payment.PAYMENT_CUS_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_DATE;
import static com.skylightapp.Classes.Payment.PAYMENT_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_OFFICE;
import static com.skylightapp.Classes.Payment.PAYMENT_PROF_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_STATUS;
import static com.skylightapp.Classes.Payment.PAYMENTTYPE;
import static com.skylightapp.Classes.PaymentCode.CODE_CUS_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_PROFILE_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_REPORT_NO;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_CUS_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_PROF_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_REPORT_NO;
import static com.skylightapp.Classes.Profile.CREATE_SPONSOR_TABLE;
import static com.skylightapp.Classes.Profile.CUS_ID_PIX_KEY;
import static com.skylightapp.Classes.Profile.PROFILE_CUS_ID_KEY;
import static com.skylightapp.Classes.Profile.PROFID_FOREIGN_KEY_PIX;
import static com.skylightapp.Classes.Profile.PROFILE_PIC_ID;
import static com.skylightapp.Classes.Profile.PROFILE_SPONSOR_ID;
import static com.skylightapp.Classes.Profile.PROF_ID_FOREIGN_KEY_PASSWORD;
import static com.skylightapp.Classes.Profile.SPONSOR_TABLE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_CODE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_CUSTOMER_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ITEM;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_NAME;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_PROFILE_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_REPORT_ID;
import static com.skylightapp.Classes.SkylightCash.CREATE_SKYLIGHT_CASH_TABLE;
import static com.skylightapp.Classes.SkylightCash.S_CASH_ADMIN_ID;
import static com.skylightapp.Classes.SkylightCash.S_CASH_PROFILE_ID;
import static com.skylightapp.Classes.SkylightCash.S_CASH_FROM;
import static com.skylightapp.Classes.SkylightCash.S_CASH_TO;
import static com.skylightapp.Classes.StandingOrder.SO_CUS_ID;
import static com.skylightapp.Classes.SkylightCash.S_CASH_ADMIN;
import static com.skylightapp.Classes.SkylightCash.S_CASH_AMOUNT;
import static com.skylightapp.Classes.SkylightCash.S_CASH_CODE;
import static com.skylightapp.Classes.SkylightCash.S_CASH_DATE;
import static com.skylightapp.Classes.SkylightCash.S_CASH_ID;
import static com.skylightapp.Classes.SkylightCash.S_CASH_PAYEE;
import static com.skylightapp.Classes.SkylightCash.S_CASH_STATUS;
import static com.skylightapp.Classes.SkylightCash.S_CASH_TABLE;
import static com.skylightapp.Classes.SkylightCash.S_CASH_PAYER;
import static com.skylightapp.Classes.StandingOrder.SO_PROF_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_ADMIN_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_OFFICE_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_PROF_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_CUS_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_PROF_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ACCT_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_CUS_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_METHOD_OF_PAYMENT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_OFFICE_BRANCH;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PAYER;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PROF_ID;
import static com.skylightapp.Classes.TransactionGranting.CREATE_TANSACTION_EXTRA_TABLE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_ACCTNAME;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_ACCTNO;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_AMOUNT;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_AUTHORIZER;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_BANK;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_CUSTOMER_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_C_NAME;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_DATE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_PAYMENT_METHOD;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_PROFILE_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_PURPOSE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_STATUS;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_TABLE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_TYPE;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_PROFILE_ID;
import static com.skylightapp.Inventory.StockTransfer.CREATE_T_STOCKS_TABLE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCKS_TABLE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_ACCEPTER;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_CODE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_DATE;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_FROM;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_ID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_ITEM_NAME;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_QTY;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_RECIPIENT_ID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_STATUS;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_STOCKID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_TRANSFERER;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_TRANSFERER_ID;
import static com.skylightapp.Inventory.StockTransfer.T_STOCK_To;
import static com.skylightapp.Inventory.Stocks.CREATE_STOCK_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_10_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_20_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_40_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_5_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTANCE_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_ACCEPTER;
import static com.skylightapp.Inventory.Stocks.STOCK_BRANCH_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_CODE;
import static com.skylightapp.Inventory.Stocks.STOCK_COLOR;
import static com.skylightapp.Inventory.Stocks.STOCK_DATE;
import static com.skylightapp.Inventory.Stocks.STOCK_DEFECTIVE;
import static com.skylightapp.Inventory.Stocks.STOCK_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_ITEM_NAME;
import static com.skylightapp.Inventory.Stocks.STOCK_MANAGER;
import static com.skylightapp.Inventory.Stocks.STOCK_MODEL;
import static com.skylightapp.Inventory.Stocks.STOCK_OFFICE;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_PROFILE_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_QTY;
import static com.skylightapp.Inventory.Stocks.STOCK_SIZE;
import static com.skylightapp.Inventory.Stocks.STOCK_STATUS;
import static com.skylightapp.Inventory.Stocks.STOCK_TYPE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_AGENT_FEE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_AMOUNT;
import static com.skylightapp.RealEstate.Properties.PROPERTY_CAPACITY;
import static com.skylightapp.RealEstate.Properties.PROPERTY_COUNTRY;
import static com.skylightapp.RealEstate.Properties.PROPERTY_DATE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_DESCRIPTION;
import static com.skylightapp.RealEstate.Properties.PROPERTY_ID;
import static com.skylightapp.RealEstate.Properties.PROPERTY_LGA;
import static com.skylightapp.RealEstate.Properties.PROPERTY_LINK;
import static com.skylightapp.RealEstate.Properties.PROPERTY_PRICE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_PRICE_DURATION;
import static com.skylightapp.RealEstate.Properties.PROPERTY_STATE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_STATUS;
import static com.skylightapp.RealEstate.Properties.PROPERTY_TABLE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_TITTLE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_TOWN;
import static com.skylightapp.RealEstate.Properties.PROPERTY_TYPE;
import static com.skylightapp.RealEstate.Properties.PROPERTY_TYPE_OF_LETTING;
import static com.skylightapp.RealEstate.Properties.PROPERTY_WARD;
import static com.skylightapp.RealEstate.PropertyImage.PROPERTY_PICTURE;
import static com.skylightapp.RealEstate.PropertyImage.PROPERTY_PICTURE_ID;
import static com.skylightapp.RealEstate.PropertyImage.PROPERTY_PICTURE_TABLE;
import static com.skylightapp.RealEstate.PropertyImage.PROPERTY_PICTURE_TITTLE;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_CUS_ID;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_NO;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_PACKID;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_STATUS;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_TABLE;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_EXPECTED_BALANCE;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_RECEIVED_AMOUNT;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_DATE;
import static com.skylightapp.SuperAdmin.AdminBalance.CREATE_ADMIN_BALANCE_TABLE;
import static com.skylightapp.Classes.AdminUser.ADMIN_ADDRESS;
import static com.skylightapp.Classes.AdminUser.ADMIN_DATE_JOINED;
import static com.skylightapp.Classes.AdminUser.ADMIN_DOB;
import static com.skylightapp.Classes.AdminUser.ADMIN_EMAIL_ADDRESS;
import static com.skylightapp.Classes.AdminUser.ADMIN_FIRST_NAME;
import static com.skylightapp.Classes.AdminUser.ADMIN_GENDER;
import static com.skylightapp.Classes.AdminUser.ADMIN_ID;
import static com.skylightapp.Classes.AdminUser.ADMIN_NIN;
import static com.skylightapp.Classes.AdminUser.ADMIN_OFFICE;
import static com.skylightapp.Classes.AdminUser.ADMIN_PASSWORD;
import static com.skylightapp.Classes.AdminUser.ADMIN_PHONE_NUMBER;
import static com.skylightapp.Classes.AdminUser.ADMIN_PIX;
import static com.skylightapp.Classes.AdminUser.ADMIN_STATUS;
import static com.skylightapp.Classes.AdminUser.ADMIN_SURNAME;
import static com.skylightapp.Classes.AdminUser.ADMIN_TABLE;
import static com.skylightapp.Classes.AdminUser.ADMIN_USER_NAME;
import static com.skylightapp.Classes.AdminUser.CREATE_ADMIN_TABLE;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_DAYS_BTWN;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_DAYS_REMAINING;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_TABLE;
import static com.skylightapp.Classes.Birthday.CREATE_BIRTHDAY_TABLE;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_ID;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_STATUS;
import static com.skylightapp.Classes.Bookings.BOOKING_CLIENT_NAME;
import static com.skylightapp.Classes.Bookings.BOOKING_DATE;
import static com.skylightapp.Classes.Bookings.BOOKING_ID;
import static com.skylightapp.Classes.Bookings.BOOKING_LOCATION;
import static com.skylightapp.Classes.Bookings.BOOKING_OCCURENCE_NO;
import static com.skylightapp.Classes.Bookings.BOOKING_STATUS;
import static com.skylightapp.Classes.Bookings.BOOKING_TABLE;
import static com.skylightapp.Classes.Bookings.BOOKING_TITTLE;
import static com.skylightapp.Classes.Bookings.CREATE_BOOKING_TABLE;
import static com.skylightapp.Classes.Bookings.ITISRECCURRING;
import static com.skylightapp.Classes.Business.BIZ_ADDRESS;
import static com.skylightapp.Classes.Business.BIZ_BRANDNAME;
import static com.skylightapp.Classes.Business.BIZ_EMAIL;
import static com.skylightapp.Classes.Business.BIZ_ID;
import static com.skylightapp.Classes.Business.BIZ_NAME;
import static com.skylightapp.Classes.Business.BIZ_PHONE_NO;
import static com.skylightapp.Classes.Business.BIZ_PIX;
import static com.skylightapp.Classes.Business.BIZ_REG_NO;
import static com.skylightapp.Classes.Business.BIZ_STATE;
import static com.skylightapp.Classes.Business.BIZ_STATUS;
import static com.skylightapp.Classes.Business.BIZ_TABLE;
import static com.skylightapp.Classes.Business.BIZ_TYPE;
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
import static com.skylightapp.Classes.GroupAccount.CREATE_GRP_ACCT_PROFILE_TABLE;
import static com.skylightapp.Classes.GroupAccount.CREATE_GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRPA_BALANCE;
import static com.skylightapp.Classes.GroupAccount.GRPA_EMAIL;
import static com.skylightapp.Classes.GroupAccount.GRPA_END_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_FIRSTNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_ID;
import static com.skylightapp.Classes.GroupAccount.GRPA_PHONE;
import static com.skylightapp.Classes.GroupAccount.GRPA_PURPOSE;
import static com.skylightapp.Classes.GroupAccount.GRPA_START_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRPA_SURNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_TITTLE;
import static com.skylightapp.Classes.GroupAccount.GRPP_ID;
import static com.skylightapp.Classes.GroupAccount.GRPP_JOINED_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPP_JOINED_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.GroupSavings.CREATE_GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_AMOUNT;
import static com.skylightapp.Classes.GroupSavings.GROUP_DATE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.GroupSavings.GS_ID;
import static com.skylightapp.Classes.GroupSavings.GS_STATUS;
import static com.skylightapp.Classes.ImportantDates.CREATE_REMINDER_TABLE;
import static com.skylightapp.Classes.ImportantDates.KEY_ACTIVE;
import static com.skylightapp.Classes.ImportantDates.KEY_DATE;
import static com.skylightapp.Classes.ImportantDates.KEY_ID;
import static com.skylightapp.Classes.ImportantDates.KEY_REPEAT;
import static com.skylightapp.Classes.ImportantDates.KEY_REPEAT_NO;
import static com.skylightapp.Classes.ImportantDates.KEY_REPEAT_TYPE;
import static com.skylightapp.Classes.ImportantDates.KEY_TIME;
import static com.skylightapp.Classes.ImportantDates.KEY_TITLE;
import static com.skylightapp.Classes.ImportantDates.TABLE_REMINDERS;
import static com.skylightapp.Classes.Loan.CREATE_LOAN_TABLE;
import static com.skylightapp.Classes.Loan.LOAN_AMOUNT;
import static com.skylightapp.Classes.Loan.LOAN_BALANCE;
import static com.skylightapp.Classes.Loan.LOAN_DATE;
import static com.skylightapp.Classes.Loan.LOAN_DISPOSABLE_COM;
import static com.skylightapp.Classes.Loan.LOAN_DOWN_PAYMENT;
import static com.skylightapp.Classes.Loan.LOAN_END_DATE;
import static com.skylightapp.Classes.Loan.LOAN_FIXED_PAYMENT;
import static com.skylightapp.Classes.Loan.LOAN_ID;
import static com.skylightapp.Classes.Loan.LOAN_INTEREST;
import static com.skylightapp.Classes.Loan.LOAN_START_DATE;
import static com.skylightapp.Classes.Loan.LOAN_STATUS;
import static com.skylightapp.Classes.Loan.LOAN_TABLE;
import static com.skylightapp.Classes.Loan.LOAN_TOTAL_INTEREST;
import static com.skylightapp.Classes.Loan.LOAN_TYPE;
import static com.skylightapp.Classes.LoanInterest.CREATE_INTEREST_TABLE;
import static com.skylightapp.Classes.LoanInterest.INTEREST_ID;
import static com.skylightapp.Classes.LoanInterest.INTEREST_RATE;
import static com.skylightapp.Classes.LoanInterest.INTEREST_TABLE;
import static com.skylightapp.Classes.Message.CREATE_MESSAGE_REPLY_TABLE;
import static com.skylightapp.Classes.Message.CREATE_MESSAGE_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_ADMIN_ID;
import static com.skylightapp.Classes.Message.MESSAGE_ADMIN_NAME;
import static com.skylightapp.Classes.Message.MESSAGE_DETAILS;
import static com.skylightapp.Classes.Message.MESSAGE_ID;
import static com.skylightapp.Classes.Message.MESSAGE_PURPOSE;
import static com.skylightapp.Classes.Message.MESSAGE_REPLY_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_SENDEE;
import static com.skylightapp.Classes.Message.MESSAGE_SENDER;
import static com.skylightapp.Classes.Message.MESSAGE_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_TIME;
import static com.skylightapp.Classes.OfficeBranch.CREATE_OFFICE_BRANCH;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ADDRESS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_APPROVER;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_DATE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_NAME;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_STATUS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.Payee.CREATE_PAYEES_TABLE;
import static com.skylightapp.Classes.Payee.PAYEES_TABLE;
import static com.skylightapp.Classes.Payee.PAYEE_ID;
import static com.skylightapp.Classes.Payee.PAYEE_NAME;
import static com.skylightapp.Classes.PaymentCode.CODE_DATE;
import static com.skylightapp.Classes.PaymentCode.CODE_MANAGER;
import static com.skylightapp.Classes.PaymentCode.CODE_PIN;
import static com.skylightapp.Classes.PaymentCode.CODE_STATUS;
import static com.skylightapp.Classes.PaymentCode.CODE_TABLE;
import static com.skylightapp.Classes.PaymentCode.CREATE_CODE_TABLE;
import static com.skylightapp.Classes.PaymentDoc.CREATE_DOCUMENT_TABLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_ID;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_PAYMENT_METHOD;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_STATUS;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_TABLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_TITLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_URI;
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
import static com.skylightapp.Classes.SavingsGroup.CREATE_SAVINGS_GROUP_TABLE;
import static com.skylightapp.Classes.SavingsGroup.SAVINGS_GROUP_TABLE;
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
import static com.skylightapp.Classes.StandingOrder.SO_ACCT_NO;
import static com.skylightapp.Classes.StandingOrder.SO_AMOUNT_DIFF;
import static com.skylightapp.Classes.StandingOrder.SO_DAILY_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.SO_DAYS_REMAINING;
import static com.skylightapp.Classes.StandingOrder.SO_END_DATE;
import static com.skylightapp.Classes.StandingOrder.SO_EXPECTED_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static com.skylightapp.Classes.StandingOrder.SO_RECEIVED_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.SO_START_DATE;
import static com.skylightapp.Classes.StandingOrder.SO_STATUS;
import static com.skylightapp.Classes.StandingOrder.STANDING_ORDER_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.CREATE_SO_ACCT_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_BALANCE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_NAME;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_NO;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCT_TABLE;
import static com.skylightapp.Classes.TellerReport.CREATE_TELLER_REPORT_TABLE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_AMOUNT_SUBMITTED;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_AMT_PAID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_APPROVAL_DATE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_BALANCE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_BRANCH;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_DATE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_EXPECTED_AMT;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_ADMIN;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_MARKETER;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_NO_OF_SAVINGS;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_STATUS;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_TABLE;
import static com.skylightapp.Classes.TimeLine.CREATE_TIMELINE_TABLE;
import static com.skylightapp.Classes.TimeLine.TIMELINE_AMOUNT;
import static com.skylightapp.Classes.TimeLine.TIMELINE_CLIENT_NAME;
import static com.skylightapp.Classes.TimeLine.TIMELINE_DETAILS;
import static com.skylightapp.Classes.TimeLine.TIMELINE_GETTING_ACCOUNT;
import static com.skylightapp.Classes.TimeLine.TIMELINE_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_LOCATION;
import static com.skylightapp.Classes.TimeLine.TIMELINE_SENDING_ACCOUNT;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TABLE;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TIME;
import static com.skylightapp.Classes.TimeLine.TIMELINE_WORKER_NAME;
import static com.skylightapp.Classes.Transaction.CREATE_GRP_TX_TABLE;
import static com.skylightapp.Classes.Transaction.CREATE_TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_DEST_ACCT;
import static com.skylightapp.Classes.Transaction.GRP_TRANX_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_SENDING_ACCT;
import static com.skylightapp.Classes.Transaction.TIMESTAMP;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TYPE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_AMOUNT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_DATE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PAYEE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_STATUS;
import static com.skylightapp.Classes.Transaction.TRANS_TYPE;
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
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_AMOUNT;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_BRANCH;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_CODE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_DATE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_ITEM_NAME;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_PACKAGE_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_PROFILE_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_STATUS;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_TABLE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_TELLER_NAME;
import static com.skylightapp.Transactions.BillModel.BILLER_CODE;
import static com.skylightapp.Transactions.BillModel.BILL_AMOUNT;
import static com.skylightapp.Transactions.BillModel.BILL_COUNTRY;
import static com.skylightapp.Transactions.BillModel.BILL_CURRENCY;
import static com.skylightapp.Transactions.BillModel.BILL_DATE;
import static com.skylightapp.Transactions.BillModel.BILL_ID;
import static com.skylightapp.Transactions.BillModel.BILL_ITEM_CODE;
import static com.skylightapp.Transactions.BillModel.BILL_NAME;
import static com.skylightapp.Transactions.BillModel.BILL_RECURRING_TYPE;
import static com.skylightapp.Transactions.BillModel.BILL_REF;
import static com.skylightapp.Transactions.BillModel.BILL_STATUS;
import static com.skylightapp.Transactions.BillModel.BILL_TABLE;
import static com.skylightapp.Transactions.BillModel.CREATE_BILLS_TABLE;
import static java.lang.String.valueOf;

public class DBHelper extends SQLiteOpenHelper {

    private static final String BILL_CUSTOMER_ID = "billCus";
    private ContentResolver myCR;
    private SQLiteDatabase myDB;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private HashMap hp;
    private ArrayList<Customer> customers;
    SharedPreferences userPref;
    private Context context;
    public static String  password;
    public String getPassword(int profileID) {

        /*SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {PROFILE_PASSWORD,PROFILE_ID};
        Cursor cursor =sqLiteDatabase.query(PROFILES_TABLE,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(1);
            password =cursor.getString(6);
        }*/

        return password;
    }
    public static String DB_PATH = "/data/D";

    public static final String DATABASE_NAME = "dBSkylight";
    private static final String LOG = DBHelper.class.getName();

    public static final String TABLE_MYTABLE = "mytable";
    public static final String COL_MYTABLE_ID = BaseColumns._ID;
    public static final String COl_MYTABLE_NAME = "name";

    public static final String BILL_ID_WITH_PREFIX = "bill.id";
    public static final int DATABASE_VERSION = 10;
    public static final int DATABASE_NEW_VERSION = 11;

    public static final int SO_ID_COLUMN = 0;
    public static final int SO_CUSTOMER_ID_COLUMN = 1;
    public static final int SO_ACCT_COLUMN = 2;
    public static final int SO_AMOUNT_COLUMN = 3;
    public static final int SO_EXPECTED_COLUMN = 4;
    public static final int SO_RECEIVED_COLUMN = 5;
    public static final int SO_DIFF_COLUMN = 6;
    public static final int SO_START_COLUMN = 7;
    public static final int SO_STATUS_COLUMN = 8;
    public static final int SO_END_DATE_COLUMN = 9;
    public static final int BOOKING_ID_COLUMN = 0;
    public static final int BOOKING_TITTLE_COLUMN = 1;
    public static final int BOOKING_NAME_COLUMN = 2;
    public static final int BOOKING_DATE_COLUMN = 3;
    public static final int BOOKING_LOCATION_COLUMN = 4;
    public static final int BOOKING_OCCUR_NO_COLUMN = 5;
    public static final int BOOKING_IS_COLUMN = 6;

    public static final int BIRTHDAY_COLUMN = 1;
    public static final int USER_BIRTHDAY_ID_COLUMN = 0;
    public static final int USER_BIRTHDAY_STATUS_COLUMN = 2;
    public static final int BIRTHDAY_DAYS_REMAINING_COLUMN = 3;
    public static final int BIRTHDAY_DAYS_BTWN_COLUMN = 4;

    public static final int REPORT_NUMBER_COLUMN = 1;
    public static final int REPORT_AMOUNT_COLUMN = 2;
    public static final int REPORT_NUMBER_OF_DAYS_COLUMN = 3;
    public static final int REPORT_DATE_COLUMN = 4;

    public static final int CUSTOMER_ID_COLUMN = 0;
    public static final int CUSTOMER_SURNAME_COLUMN = 1;
    public static final int CUSTOMER_FIRST_NAME_COLUMN = 2;
    public static final int PACKAGE_ID_COLUMN = 0;
    public static final int PACKAGE_VALUE_COLUMN = 4;
    public static final int PACKAGE_STATUS_COLUMN = 8;

    public static final int PROFILE_ID_COLUMN = 0;
    public static final int USERNAME_COLUMN = 4;

    public static final int ACCOUNT_BALANCE_COLUMN = 5;
    public static final int TIMESTAMP_COLUMN = 3;
    public static final int TRANSACTION_TYPE_COLUMN = 8;
    public static final int TIMELINE_TIME_COLUMN = 5;

    public static final int PROPERTY_ID_COLUMN = 0;
    public static final int PROPERTY_DESCRIPTION_COLUMN = 2;
    public static final int PROPERTY_TYPE_COLUMN = 3;
    public static final int PROPERTY_TOWN_COLUMN = 4;
    public static final int PROPERTY_PRICE_COLUMN = 5;
    public static final int PROPERTY_IMAGE_COLUMN = 7;


    public static final int MESSAGE_ID_COLUMN = 1;
    public static final int MESSAGE_DETAILS_COLUMN = 2;
    public static final int MESSAGE_VIEWED_COLUMN = 3;
    public static final int MESSAGE_SENDER_COLUMN = 4;
    public static final int MESSAGE_TIME_COLUMN = 5;

    public static final int Bill_ID_COLUMN = 1;
    public static final int BILL_NAME_COLUMN = 2;
    public static final int BILL_AMOUNT_COLUMN = 3;
    public static final int BILL_CURRENCY_COLUMN = 4;
    public static final int BILL_DATE_COLUMN = 5;
    public static final int BILL_RECURRING_TYPE_COLUMN = 6;
    public static final int BILL_STATUS_COLUMN = 7;

    public static final int ACCOUNT_INTEREST_COLUMN = 3;
    public static final int ACCOUNT_TYPE_NUMBER_COLUMN = 1;
    public static final int ACCOUNT_TYPES_NAME_COLUMN = 2;

    public static final int CODE_OWNER_COLUMN = 0;
    public static final int CODE_OWNER_PHONE_COLUMN = 1;
    public static final int CODE_PIN_COLUMN = 2;
    public static final int CODE_DATE_COLUMN = 3;
    public static final int CODE_STATUS_COLUMN = 4;
    public static final int CODE_MANAGER_COLUMN = 5;





    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        Log.d("table", CREATE_GRP_ACCT_PROFILE_TABLE);
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
        Log.d("table", CREATE_TELLER_REPORT_TABLE);


    }

    /*public DBHelper(Context context, String DATABASE_NAME,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 1);
        myCR = context.getContentResolver();
        this.context = context;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        db.execSQL(CREATE_SAVINGS_GROUP_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
        db.execSQL(CREATE_EMERGENCY_REPORT_TABLE);
        db.execSQL(CREATE_SO_ACCT_TABLE);
        db.execSQL(CREATE_GROUP_SAVINGS_TABLE);
        db.execSQL(CREATE_GRP_ACCT_TABLE);
        db.execSQL(CREATE_GRP_ACCT_PROFILE_TABLE);
        db.execSQL(CREATE_GRP_TX_TABLE);
        db.execSQL(CREATE_TELLER_REPORT_TABLE);
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
        db.execSQL(CREATE_TELLER_CASH_TABLE);
        db.execSQL(CREATE_SPONSOR_TABLE);
        db.execSQL(CREATE_EMERGENCY_NEXT_REPORT_TABLE);
        db.execSQL(CREATE_TELLER_REPORT_TABLE);
        db.execSQL(CREATE_SKYLIGHT_CASH_TABLE);
        db.execSQL("create table ROLES " + "(role_ID integer primary key, roleUserName text,rolePassword text,rolePhoneNo text,role text)");


        //db.execSQL("create table contacts " + "(id integer primary key, name text,phone text,email text, street text,place text)");

        //db.execSQL("CREATE TABLE ROLES " + "(ID INTEGER PRIMARY KEY NOT NULL," + "NAME TEXT NOT NULL)");
        /*try {


        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }*/


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
        db.execSQL("DROP TABLE IF EXISTS " + SAVINGS_GROUP_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + S_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPER_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + T_STOCKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EMERGENCY_REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TELLER_CASH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TANSACTION_EXTRA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TANSACTION_EXTRA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPONSOR_TABLE);
        onCreate(db);

    }

    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("Skylight App - check", e.getMessage());
        }
        if (tempDB != null)
            //tempDB.close();
            return tempDB != null ? true : false;
        return false;
    }

    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
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

    }
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
        }
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
    }
    public SQLiteDatabase openDataBase(SQLiteDatabase db) {
        if(db.isOpen()){
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
            return sqLiteDatabase;
        }
        //sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
        return sqLiteDatabase;
    }


    public void createDataBase() throws IOException {
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

    }


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
    public ArrayList<ChartData> getTranxChartByCusID(int cusID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = TRANSACTION_CUS_ID + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(cusID)};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection,
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
    public ArrayList<ChartData> getTranxChartByTellerID(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = TRANSACTION_PROF_ID + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(tellerID)};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection,
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
    public ArrayList<ChartData> getTranxChartByBranch(String branch){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = TRANSACTION_OFFICE_BRANCH + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(branch)};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection,
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
    public ArrayList<ChartData> getTranxChartAll(String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] selectionArgs = new String[]{valueOf(yearMonth)};
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, groupbyclause,
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
    public ArrayList<ChartData> getChartCustomers(String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"COUNT(" + CUSTOMER_ID + ") AS " + tmpcol_monthly_total, "substr(" + CUSTOMER_DATE_JOINED + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",4)";
        String orderbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",7,2)||substr(" + CUSTOMER_DATE_JOINED + ",4,2)";
        Cursor cursor = db.query(CUSTOMER_TABLE, columns, null,
                null, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
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
    public ArrayList<TellerCountData> getTellerMonthTReport(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> countData = new ArrayList<TellerCountData>();
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + TELLER_REPORT_AMT_PAID + ") AS " + tmpcol_monthly_total, "substr(" + TELLER_REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + TELLER_REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + TELLER_REPORT_DATE + ",7,2)||substr(" + TELLER_REPORT_DATE + ",4,2)";

        String selection = TELLER_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID)};

        Cursor cursor = db.query(TELLER_REPORT_TABLE, columns, selection, selectionArgs, groupbyclause,
                null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(PROFILE_ID));
                tellerCountData.setTellerName(cursor.getColumnName(12));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
                countData.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return countData;

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


    public long insertNewEmergNextLoc(int emergencyNextReportID,int emergencyReportID, String dateOfToday, String latLng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMERGENCY_NEXT_LOCID, emergencyNextReportID);
        values.put(EMERGENCY_NEXT_REPORT_ID, emergencyReportID);
        values.put(EMERGENCY_NEXT_LOCTIME, dateOfToday);
        values.put(EMERGENCY_NEXT_LATLNG, latLng);
        db.insert(EMERGENCY_NEXT_REPORT_TABLE, null, values);
        //emergencyNextReportID = db.insert(EMERGENCY_NEXT_REPORT_TABLE, EMERGENCY_NEXT_LOCID, values);

        return emergencyNextReportID;
    }
    private void getEmergNextCursor(ArrayList<EmergReportNext> emergReportNexts, Cursor cursor) {
        while (cursor.moveToNext()) {

            int emergNextID = cursor.getInt(0);
            int emergReportID = cursor.getInt(1);
            String time = cursor.getString(2);
            String latlng = cursor.getString(5);
            emergReportNexts.add(new EmergReportNext(emergNextID, emergReportID, time, latlng));
        }
    }
    public ArrayList<String> getAllLatLngForEmergReport(int emegReportID) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_LOCID + "=?";
        String[] columns = {EMERGENCY_NEXT_LATLNG};
        String[] selectionArgs = new String[]{valueOf(emegReportID)};
        Cursor res = db.query(EMERGENCY_NEXT_REPORT_TABLE,columns,selection,selectionArgs,null,null,null);


        if(res !=null && res.getCount()>0){

            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(5));
                    res.moveToNext();
                }
                res.close();
            }
        }


        return array_list;

    }
    public ArrayList<EmergReportNext> getAllEmergNextReportFprEmergReport(int reportID) {
        ArrayList<EmergReportNext> emergReportNexts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERGENCY_NEXT_REPORT_ID + "=? ";
        String[] selectionArgs = new String[]{valueOf(reportID)};

        String[] columns = {EMERGENCY_NEXT_LOCID,EMERGENCY_LOCID,EMERGENCY_NEXT_LOCTIME,EMERGENCY_NEXT_LAT,EMERGENCY_NEXT_LNG};
        Cursor cursor = db.query(EMERGENCY_NEXT_REPORT_TABLE, columns, selection, selectionArgs, null,
                null, null);
        getEmergNextCursor(emergReportNexts, cursor);
        cursor.close();
        db.close();
        return emergReportNexts;
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


    public int getTellerMonthCusCountNew(int profileID,String yearMonth) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String queryString="SELECT COUNT (*) FROM " + CUSTOMER_TABLE ;
        String selection = "substr(" + CUSTOMER_DATE_JOINED + ",4)" + "=? AND " + PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(yearMonth), valueOf(profileID)};

        String tmpcol_monthly_total = "Monthly_Count";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"COUNT(" + CUSTOMER_ID + ") AS " + tmpcol_monthly_total, "substr(" + CUSTOMER_DATE_JOINED + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",4)";
        String orderbyclause = "substr(" + CUSTOMER_DATE_JOINED + ",7,2)||substr(" + CUSTOMER_DATE_JOINED + ",4,2)";
        @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(CUSTOMER_PHONE_NUMBER);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;

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

    public long insertTransaction_Granting(int te_id, int profileID, int cusID, String cusName, double teAmount, String date, String bank, String acctName, String acctNo, String purpose, String method, String authorizer, String teType, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TANSACTION_EXTRA_ID, te_id);
        values.put(PROFILE_ID, profileID);
        values.put(CUSTOMER_ID, cusID);
        values.put(TANSACTION_EXTRA_C_NAME, cusName);
        values.put(TANSACTION_EXTRA_BANK, bank);
        values.put(TANSACTION_EXTRA_ACCTNAME, acctName);
        values.put(TANSACTION_EXTRA_ACCTNO, acctNo);
        values.put(TANSACTION_EXTRA_AMOUNT, teAmount);
        values.put(TANSACTION_EXTRA_TYPE, teType);
        values.put(TANSACTION_EXTRA_PURPOSE, purpose);
        values.put(TANSACTION_EXTRA_PAYMENT_METHOD, method);
        values.put(TANSACTION_EXTRA_DATE, date);
        values.put(TANSACTION_EXTRA_AUTHORIZER, authorizer);
        values.put(TANSACTION_EXTRA_STATUS, status);
        return db.insert(TANSACTION_EXTRA_TABLE, null, values);
    }

    public void updateTranxGrantingStatus(int teID,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = TANSACTION_EXTRA_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(teID)};
        stocksUpdateValues.put(TANSACTION_EXTRA_STATUS, status);
        db.update(TANSACTION_EXTRA_TABLE, stocksUpdateValues, selection, selectionArgs);


    }
    public void updateTranxGranting(int teID,String authorizer,String methodOfPayment,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues teUpdateValues = new ContentValues();
        String selection = TANSACTION_EXTRA_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(teID)};
        teUpdateValues.put(TANSACTION_EXTRA_PAYMENT_METHOD, methodOfPayment);
        teUpdateValues.put(TANSACTION_EXTRA_AUTHORIZER, authorizer);
        teUpdateValues.put(TANSACTION_EXTRA_STATUS, status);
        db.update(TANSACTION_EXTRA_TABLE, teUpdateValues, selection, selectionArgs);
        db.close();


    }
    public String getTransaction_GrantingType(int teID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor=null;
            String loanType=null;
            String selection = TANSACTION_EXTRA_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(teID)};
            cursor = sqLiteDatabase.query(TANSACTION_EXTRA_TABLE, null,  selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        loanType=cursor.getString(16);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return loanType;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public int getAPPROVER_TE_MonthCount(int profileID, String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String queryString="SELECT COUNT (*) FROM " + TANSACTION_EXTRA_TABLE ;

        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
        String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(profileID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(queryString,
                null,
                selection, selectionArgs,
                null, null, null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }

    public int getTE_MonthCount1(String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String query="SELECT COUNT (*) from "+TANSACTION_EXTRA_TABLE +" WHERE "+ TANSACTION_EXTRA_DATE +" >= date('now','localtime', '-31 day')";
        String queryString="SELECT COUNT (*) FROM " + TANSACTION_EXTRA_TABLE ;

        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=?";
        String selection22 = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
        String selection1 = "substr(" + TANSACTION_EXTRA_DATE + ",4)" + "=? AND " + PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear)};

        @SuppressLint("Recycle") Cursor cursor = db.query(queryString,
                null,
                selection, selectionArgs,
                null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }

    private void getTECursor(ArrayList<TransactionGranting> transactionGrantings, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                int teID = cursor.getInt(0);
                int profileID = cursor.getInt(1);
                int customerID = cursor.getInt(2);
                String teCusName = cursor.getString(3);
                String te_Bank = cursor.getString(4);
                String teBank_AcctName = cursor.getString(5);
                String te_Bank_AcctNo = cursor.getString(6);
                double te_Amount = cursor.getDouble(7);
                String te_Purpose = cursor.getString(8);
                String te_method = cursor.getString(9);
                String date = cursor.getString(10);
                String te_Authorizer = cursor.getString(11);
                String status = cursor.getString(12);
                transactionGrantings.add(new TransactionGranting(teID, profileID, customerID, teCusName, te_Bank, teBank_AcctName, te_Bank_AcctNo, te_Amount, te_Purpose, date, te_method, te_Authorizer, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TransactionGranting> getAllTransactionGranting() {
        try {
            ArrayList<TransactionGranting> transactionGrantings = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {TANSACTION_EXTRA_ID, TANSACTION_EXTRA_C_NAME, TANSACTION_EXTRA_BANK, TANSACTION_EXTRA_ACCTNO, TANSACTION_EXTRA_ACCTNAME, TANSACTION_EXTRA_AMOUNT, TANSACTION_EXTRA_PURPOSE, TANSACTION_EXTRA_DATE, TANSACTION_EXTRA_PAYMENT_METHOD, TANSACTION_EXTRA_AUTHORIZER, TANSACTION_EXTRA_STATUS};
            Cursor cursor = db.query(TANSACTION_EXTRA_TABLE, columns, null, null, TANSACTION_EXTRA_C_NAME,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTECursor(transactionGrantings, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return transactionGrantings;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<TransactionGranting> getTranxRequestAtDate(String currentDate) {
        try {
            ArrayList<TransactionGranting> grantingArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TANSACTION_EXTRA_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(currentDate)};

            Cursor cursor = db.query(TANSACTION_EXTRA_TABLE, null, selection, selectionArgs, null, null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTECursor(grantingArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return grantingArrayList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTranxRequestCountAtDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = TANSACTION_EXTRA_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TANSACTION_EXTRA_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }


    public double getTotalTranxExtraForTheMonth1(String monthYear) {
        String monthTotal2=null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "= monthYear";

            String query="SELECT COUNT (*) from "+TANSACTION_EXTRA_TABLE +" WHERE "+ TANSACTION_EXTRA_DATE +" >= date('now','localtime', '-31 day')";
            String queryString="select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection;


            String selection22 = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
            String selection1 = "substr(" + TANSACTION_EXTRA_DATE + ",4)" + "=? AND " + PROFILE_ID + "=?";

            String[] selectionArgs = new String[]{valueOf(monthYear)};

            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,null,

                    null);


            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getTotalTranxRequestAtDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;

            String selection = TANSACTION_EXTRA_DATE + "=?";

            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection,
                    selectionArgs);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }



    public double getTotalTEForCusTheMonth1(int customerID,String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;

        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";


        String query="SELECT COUNT (*) from "+TANSACTION_EXTRA_TABLE +" WHERE "+ TANSACTION_EXTRA_DATE +" >= date('now','localtime', '-31 day')";
        String queryString="select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection;


        String selection22 = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";
        String selection1 = "substr(" + TANSACTION_EXTRA_DATE + ",4)" + "=? AND " + TANSACTION_EXTRA_PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public double getTotalTEForProfileTheMonth(int profileID,String monthYear) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;


            String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";

            String queryString="select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection;


            String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(profileID)};

            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                    null);



            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }




    public int getCusMonthTransactionCount1(int customerID,String monthYear) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;
            String selection = "substr(" + TRANSACTION_DATE + ",4)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";

            String queryString="select COUNT ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection;

            String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                    null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(TRANSACTION_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public int getCusMonthSOCount1(int customerID,String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String selection = "substr(" + SO_START_DATE + ",4)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";

        String queryString="select COUNT ("+ SO_ID +") from " + STANDING_ORDER_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(SO_START_DATE);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;


    }







    public void deleteAward(long awardID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = AWARD_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(awardID)};

            db.delete(AWARD_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public int getTellerMonthCusCount1( int tellerID,String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;


        String selection = "substr(" + CUSTOMER_DATE_JOINED + ",4)" + "=? AND " + CUSTOMER_PROF_ID + "=?";

        String queryString="select COUNT ("+ CUSTOMER_ID +") from " + CUSTOMER_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(tellerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(CUSTOMER_ID);
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
    public long insertProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(PROFILE_ID, profile.getPID());
            values.put(PROFILE_SURNAME, profile.getProfileLastName());
            values.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
            values.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
            values.put(PROFILE_EMAIL, profile.getProfileEmail());
            values.put(PROFILE_DOB, profile.getProfileDob());
            values.put(PROFILE_GENDER, profile.getProfileGender());
            values.put(PROFILE_ADDRESS, profile.getProfileAddress());
            values.put(PROFILE_STATE, profile.getProfileState());
            values.put(PROFILE_OFFICE, profile.getProfileOffice());
            values.put(PROFILE_DATE_JOINED, profile.getProfileDateJoined());
            values.put(PROFILE_ROLE, profile.getProfileRole());
            values.put(PROFILE_USERNAME, profile.getProfileUserName());
            values.put(PROFILE_PASSWORD, profile.getProfilePassword());
            values.put(PROFILE_SPONSOR_ID, profile.getProfileSponsorID());
            values.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
            db.insert(PROFILES_TABLE,null,values);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profile.getPID();
    }
    public long insertTellerCash(int _id, int profileID, int packageID, String itemName, double packageAmount, String date, String teller, String branch, long code, String branchStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(TELLER_CASH_ID, _id);
            values.put(TELLER_CASH_PROFILE_ID, profileID);
            values.put(TELLER_CASH_ITEM_NAME, itemName);
            values.put(TELLER_CASH_AMOUNT, packageAmount);
            values.put(TELLER_CASH_DATE, date);
            values.put(TELLER_CASH_PACKAGE_ID, packageID);
            values.put(TELLER_CASH_TELLER_NAME, teller);
            values.put(TELLER_CASH_BRANCH, branch);
            values.put(TELLER_CASH_CODE, code);
            values.put(TELLER_CASH_STATUS, branchStatus);
            db.insert(OFFICE_BRANCH_TABLE,null,values);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return _id;
    }
    private void getTellerCashCursor(ArrayList<TellerCash> tellerCashes, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                int tellerCashID = cursor.getInt(0);
                int profileID = cursor.getInt(1);
                String tellerItemName = cursor.getString(2);
                double amount = cursor.getDouble(3);
                String date = cursor.getString(4);
                int packageID = cursor.getInt(5);
                String tellerName = cursor.getString(6);
                String branch = cursor.getString(7);
                long code = Long.parseLong(cursor.getString(8));
                String status = cursor.getString(9);
                tellerCashes.add(new TellerCash(tellerCashID, profileID,packageID,tellerItemName,amount,tellerName,branch,date,code,status));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<TellerCash> getAllTellerCash() {
        try {
            ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {TELLER_CASH_ITEM_NAME,TELLER_CASH_AMOUNT,TELLER_CASH_DATE,TELLER_CASH_PACKAGE_ID,TELLER_CASH_TELLER_NAME,TELLER_CASH_BRANCH,TELLER_CASH_CODE,TELLER_CASH_STATUS};
            Cursor cursor = db.query(TELLER_CASH_TABLE, columns, null, null, TELLER_CASH_TELLER_NAME,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTellerCashCursor(tellerCashArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return tellerCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }



    public double getTellerTotalTellerCashForTheMonth(String tellerName,String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;


        String selection = "substr(" + TELLER_CASH_DATE + ",4)" + "=? AND " + TELLER_CASH_PROFILE_ID + "=?";

        String queryString="select sum ("+ TELLER_CASH_AMOUNT +") from " + TELLER_CASH_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(tellerName)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TELLER_CASH_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }
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

    public double getMonthTotalTransactionForCus1(int customerID,String yearMonth) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;


        String selection = "substr(" + TRANSACTION_DATE + ",4)" + "=? AND " + TRANSACTION_CUS_ID + "=?";

        String queryString="select sum ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(yearMonth), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TRANSACTION_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }

    public double getTellerCashForTellerToday(String tellerName,String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = TELLER_CASH_TELLER_NAME + "=? AND " + TELLER_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerName), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ TELLER_CASH_AMOUNT +") from " + TELLER_CASH_TABLE + " WHERE " + selection,
                    selectionArgs);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(TELLER_CASH_AMOUNT);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    private void getTellerCashTellerCursor(ArrayList<String> tellerCashes, Cursor cursor) {
        try {
            String tellerName=null;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        tellerName = cursor.getString(6);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            tellerCashes.add(tellerName);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void getTellerCashBranchCursor(ArrayList<String> tellerCashes, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                String branch = cursor.getString(7);
                tellerCashes.add(branch);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getAllTellerCashBranchNames() {
        ArrayList<String> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {TELLER_CASH_BRANCH};
        Cursor cursor = db.query(TELLER_CASH_TABLE, columns, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerCashBranchCursor(tellerCashArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return tellerCashArrayList;
    }
    public ArrayList<String> getAllTellerCashTellerNames() {
        try {
            ArrayList<String> tellerCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {TELLER_CASH_TELLER_NAME};
            Cursor cursor = db.query(TELLER_CASH_TABLE, columns, null, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTellerCashBranchCursor(tellerCashArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return tellerCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TellerCash> getTellerCashForBranch(String branch) {
        try {
            ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TELLER_CASH_BRANCH + "=?";
            String[] selectionArgs = new String[]{valueOf(branch)};
            Cursor cursor = db.query(TELLER_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTellerCashCursor(tellerCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return tellerCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TellerCash> getTellerCashByTellerName(String tellerName) {
        try {
            ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TELLER_CASH_TELLER_NAME + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerName)};
            Cursor cursor = db.query(TELLER_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTellerCashCursor(tellerCashArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return tellerCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TellerCash> getTellerCashForTeller(int profileID) {
        try {
            ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TELLER_CASH_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(TELLER_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTellerCashCursor(tellerCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return tellerCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public void deleteTellerCash(int tellerCashID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TELLER_CASH_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerCashID)};
            db.delete(TELLER_CASH_TABLE,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public void updateTellerCashCode(int tcID,long code) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues stocksUpdateValues = new ContentValues();
            String selection = TELLER_CASH_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(tcID)};
            stocksUpdateValues.put(TELLER_CASH_CODE, code);
            db.update(TELLER_CASH_TABLE, stocksUpdateValues, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public long insertOfficeBranch(int officeBranchId, int superAdminID, String branchName, String branchRegDate, String branchAddress, String branchApprover, String branchStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(OFFICE_BRANCH_ID, officeBranchId);
            values.put(OFFICE_SUPERADMIN_ID, superAdminID);
            values.put(OFFICE_BRANCH_NAME, branchName);
            values.put(OFFICE_BRANCH_DATE, String.valueOf(branchRegDate));
            values.put(OFFICE_BRANCH_ADDRESS, branchAddress);
            values.put(OFFICE_BRANCH_APPROVER, branchApprover);
            values.put(OFFICE_BRANCH_STATUS, branchStatus);
            db.insert(OFFICE_BRANCH_TABLE,null,values);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return officeBranchId;
    }

    private void getOfficeBranchCursor(ArrayList<OfficeBranch> officeBranchArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                int officeID = cursor.getInt(0);
                String officeName = cursor.getString(2);
                String officeStatus = cursor.getString(6);
                officeBranchArrayList.add(new OfficeBranch(officeID, officeName,officeStatus));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<OfficeBranch> getAllBranchOffices() {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteStocksTransfer(int stockTransferID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockTransferID)};
            db.delete(T_STOCKS_TABLE,
                    selection, selectionArgs);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateStocksQty(int stockID,int qty) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues stocksUpdateValues = new ContentValues();
            String selection = STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockID)};
            stocksUpdateValues.put(STOCK_QTY, qty);
            db.update(STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateStocksTransferWithCode(int stockTransferID,long code) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues stocksUpdateValues = new ContentValues();
            String selection = T_STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockTransferID)};
            stocksUpdateValues.put(T_STOCK_CODE, code);
            db.update(T_STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<StockTransfer> getAllStocksTransfers() {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {T_STOCK_ID,T_STOCK_ITEM_NAME,T_STOCK_QTY,T_STOCK_DATE,T_STOCK_CODE,T_STOCK_TRANSFERER,T_STOCK_ACCEPTER,T_STOCK_STATUS};
            Cursor cursor = db.query(T_STOCKS_TABLE, columns, null, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferWithDate(String date) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {T_STOCK_ID,T_STOCK_ITEM_NAME,T_STOCK_QTY,T_STOCK_DATE,T_STOCK_CODE,T_STOCK_TRANSFERER,T_STOCK_ACCEPTER,T_STOCK_STATUS};

            String selection = T_STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(T_STOCKS_TABLE, columns, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferForSender(int profileID) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_TRANSFERER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferForReciever(int receiverProfileID) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_TRANSFERER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(receiverProfileID)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferFromTeller(String Teller) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_FROM + "=?";
            String[] selectionArgs = new String[]{valueOf(Teller)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferFromTellerWithDate(String Teller,String date) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_FROM + "=? AND " + T_STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(Teller), valueOf(date)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferFromBranchWithDate(String Branch,String date) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_FROM + "=? AND " + T_STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(Branch), valueOf(date)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<StockTransfer> getStocksTransferFromSkylightWithDate(String Skylight,String date) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_FROM + "=? AND " + T_STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(Skylight), valueOf(date)};
            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferFromBranch(String Branch) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_FROM + "=?";
            String[] selectionArgs = new String[]{valueOf(Branch)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferFromSkylight(String Skylight) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_FROM + "=?";
            String[] selectionArgs = new String[]{valueOf(Skylight)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksTransferAtDate(String date) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StockTransfer> getStocksToCustomer(String Customer) {
        try {
            ArrayList<StockTransfer> stockTransferArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = T_STOCK_To + "=?";
            String[] selectionArgs = new String[]{valueOf(Customer)};
            Cursor cursor = db.query(T_STOCKS_TABLE, null, selection, selectionArgs, T_STOCK_DATE,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferWithCodeCursor(stockTransferArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stockTransferArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public long saveNewStocksTransfer(int stID, int stockID,int senderID, int receiverID, String itemName,int qty, String sender,String receiver, String from,String to,String date, long code, String status) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(T_STOCK_ID, stID);
            contentValues.put(T_STOCK_STOCKID, stockID);
            contentValues.put(T_STOCK_TRANSFERER_ID, senderID);
            contentValues.put(T_STOCK_RECIPIENT_ID, receiverID);
            contentValues.put(T_STOCK_ITEM_NAME, itemName);
            contentValues.put(T_STOCK_QTY, qty);
            contentValues.put(T_STOCK_DATE, date);
            contentValues.put(T_STOCK_TRANSFERER, sender);
            contentValues.put(T_STOCK_ACCEPTER, receiver);
            contentValues.put(T_STOCK_FROM, from);
            contentValues.put(T_STOCK_To, to);
            contentValues.put(T_STOCK_CODE, code);
            contentValues.put(T_STOCK_STATUS, status);
            db.insert(T_STOCKS_TABLE,null,contentValues);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return stID;
    }
    public double getTotalStocksTransfersByBranch(String Branch,String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ S_CASH_AMOUNT +") from " + S_CASH_TABLE + " WHERE " + selection,
                    new String[]{valueOf(date)}
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    private void getStocksTransferWithCodeCursor(ArrayList<StockTransfer> stockTransfers, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                long stockTransferID = cursor.getLong(0);
                long stockTCode = cursor.getLong(10);
                String tItemName = cursor.getString(4);
                String recipient = cursor.getString(8);
                int qty = cursor.getInt(5);
                String transferer = cursor.getString(7);
                String date = cursor.getString(6);
                String from = cursor.getString(12);
                String to = cursor.getString(13);
                String status = cursor.getString(11);
                stockTransfers.add(new StockTransfer(stockTransferID, tItemName,transferer,qty,recipient,stockTCode,date,from,to,status));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void getStocksTransferWithoutCodeCursor(ArrayList<StockTransfer> stockTransfers, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                long stockTransferID = cursor.getLong(0);
                String tItemName = cursor.getString(4);
                String recipient = cursor.getString(8);
                int qty = cursor.getInt(5);
                String transferer = cursor.getString(7);
                String date = cursor.getString(6);
                String status = cursor.getString(11);
                stockTransfers.add(new StockTransfer(stockTransferID, tItemName,transferer,qty,recipient,date,status));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void getStocksTransferersCursor(ArrayList<String> strings, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                String itemName = cursor.getString(7);
                strings.add(itemName);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllStockTransferersName(String name) {
        try {
            ArrayList<String> strings = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {T_STOCK_TRANSFERER};
            String selection = T_STOCK_TRANSFERER + "=?";
            String[] selectionArgs = new String[]{valueOf(name)};
            Cursor cursor = db.query(T_STOCKS_TABLE, columns, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferersCursor(strings, cursor);
                    cursor.close();
                }

            db.close();

            return strings;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllStockTransferersNameWithDate(String date) {
        try {
            ArrayList<String> strings = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {T_STOCK_TRANSFERER};
            String selection = T_STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.query(T_STOCKS_TABLE, columns, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksTransferersCursor(strings, cursor);
                    cursor.close();
                }

            db.close();
            return strings;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }



    public int getAllStockTransferCountForDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = new String[]{date};
            String selection = T_STOCK_DATE + "=?";
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + T_STOCKS_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(STOCK_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getAllStockCountForDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = new String[]{date};
            String selection = STOCK_DATE + "=?";


            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(STOCK_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    private void getStocksNameSpnCursor(ArrayList<String> stringArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                String stocksName = cursor.getString(1);
                stringArrayList.add(stocksName);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public Stocks getRecipientAndStock(int recipientID, String stockItems) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;
        Stocks stocks = null;
        String selection = STOCK_PROFILE_ID + "=? AND " + STOCK_ITEM_NAME + "=?";
        String[] selectionArgs = new String[]{valueOf(recipientID), valueOf(stockItems)};
        String[] projection = {STOCK_ID, STOCK_ITEM_NAME,STOCK_TYPE,STOCK_OFFICE, STOCK_QTY,STOCK_DATE,STOCK_ACCEPTER,STOCK_ACCEPTANCE_DATE,STOCK_STATUS,STOCK_CODE};

        cursor = db.query(STOCKS_TABLE, projection, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            stocks= new Stocks();
            stocks.setStockID(cursor.getInt(0));
            stocks.setStockName(cursor.getString(1));
            stocks.setStockType(cursor.getString(2));
            stocks.setStockLocation(cursor.getString(6));
            stocks.setStockItemQty(cursor.getInt(7));
            stocks.setStockDate(cursor.getString(8));
            stocks.setStockAccepter(cursor.getString(15));
            stocks.setStockAcceptanceDate(cursor.getString(16));
            stocks.setStockStatus(cursor.getString(18));
            stocks.setStockCode(cursor.getLong(20));


        }
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.close();
            }
        db.close();

        return stocks;

    }


    public ArrayList<String> getStocksName() {
        try {
            ArrayList<String> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {STOCK_ITEM_NAME};
            Cursor cursor = db.query(STOCKS_TABLE, columns, STOCK_ITEM_NAME, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksNameSpnCursor(stocksArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getStocksNameStockCursor(ArrayList<Stocks> stringArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int stockID=cursor.getInt(0);
                String stocksName = cursor.getString(1);
                int qty=cursor.getInt(7);
                stringArrayList.add(new Stocks(stockID,stocksName,qty));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<Stocks> getStocksNameStock() {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {STOCK_ITEM_NAME};
            Cursor cursor = db.query(STOCKS_TABLE, columns, STOCK_ITEM_NAME, null, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksNameStockCursor(stocksArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Stocks> getAllStockForProfile(int profileID) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksNameStockCursor(stocksArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public long saveNewSuperCash(int tellerCashID, int recipientID, String date, double amount, String collectorName,String type, String officeBranch, String adminName, long code, String status) {

        try {
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
            db.insert(SUPER_CASH_TABLE,null,contentValues);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return tellerCashID;
    }
    public void saveNewWorker(int workerID,  String worker) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(WORKER_ID, workerID);
            contentValues.put(WORKER, worker);
            db.insert(WORKER_TABLE, null, contentValues);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    private void getWorkersCursor(ArrayList<String> workersArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                String worker = cursor.getString(1);
                workersArrayList.add(worker);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<String> getAllWorkers() {
        try {
            ArrayList<String> workersArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {WORKER};
            Cursor cursor = db.query(WORKER_TABLE, columns, WORKER, null, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTellerCashSpnCursor(workersArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return workersArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public int getSuperCashCountForBranchAndDate(String office,String date) {

        try {
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
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
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

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(S_CASH_ID, tellerCashID);
            contentValues.put(S_CASH_ADMIN_ID, tellerAdminID);
            contentValues.put(S_CASH_PROFILE_ID, profileId);
            contentValues.put(S_CASH_DATE, date);
            contentValues.put(S_CASH_AMOUNT, amount);
            contentValues.put(S_CASH_PAYER, payer);
            contentValues.put(S_CASH_PAYEE, payee);
            contentValues.put(S_CASH_FROM, from);
            contentValues.put(S_CASH_TO, to);
            contentValues.put(S_CASH_CODE, valueOf(code));
            contentValues.put(S_CASH_STATUS, status);
            db.insert(S_CASH_TABLE,null,contentValues);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return tellerCashID;
    }
    public int getAllSkylightCashCountForDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = new String[]{date};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + S_CASH_TABLE + " WHERE " + S_CASH_DATE + "=?",
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getSkylightCashCountForBranchAndDate(String office, String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = S_CASH_PAYEE + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    private void getSkylightCashCursorMy(ArrayList<SkylightCash> skylightCashArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int skylightCashID = cursor.getInt(0);
                String date = cursor.getString(4);
                double amount = cursor.getDouble(5);
                String payer = cursor.getString(6);
                String payee = cursor.getString(7);
                String status = cursor.getString(10);
                String from = cursor.getString(11);
                String to = cursor.getString(12);
                skylightCashArrayList.add(new SkylightCash(skylightCashID, date, amount, payer, payee, from, to,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    private void getSkylightCashCursorAll(ArrayList<SkylightCash> skylightCashArrayList, Cursor cursor) {
        try {
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
                skylightCashArrayList.add(new SkylightCash(skylightCashID, date, amount, payer, payee,code, from, to,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<SkylightCash> getAllSkylightCashForPayee(String payee) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_PAYEE + "=?";
            String[] selectionArgs = new String[]{valueOf(payee)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSkylightCashForFromCategory(String fromCategory) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_FROM + "=?";
            String[] selectionArgs = new String[]{valueOf(fromCategory)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSkylightCashForFromCategoryAndDate(String fromCategory, String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_FROM + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(fromCategory), valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSkylightCashForToCategory(String toCategory) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_TO + "=?";
            String[] selectionArgs = new String[]{valueOf(toCategory)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSkylightCashForToCategoryAndDate(String toCategory, String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_TO + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(toCategory), valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<SkylightCash> getAllSkylightCashForPayer(String payer) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_PAYER + "=?";
            String[] selectionArgs = new String[]{valueOf(payer)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
            ArrayList<String> tellerCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {S_CASH_PAYER};
            Cursor cursor = db.query(S_CASH_TABLE, columns, S_CASH_PAYER, null, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTellerCashSpnCursor(tellerCashArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return tellerCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<SkylightCash> getSCashForPayeeAtDate(String office, String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_PAYEE + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getSCashForPayerAtDate(String teller, String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_PAYER + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getSCashForAdminAtDate(String admin, String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_ADMIN + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(admin), valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getSkylightCashForProfileAtDate(int profileID, String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_PROFILE_ID + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorMy(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSkylightCashForProfile(int profileID) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorMy(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSCashAtDate(String date) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkylightCash> getAllSCashForAdmin(String adminName) {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = S_CASH_ADMIN + "=?";
            String[] selectionArgs = new String[]{valueOf(adminName)};

            Cursor cursor = db.query(S_CASH_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();
            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public double getSCashTotalForDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.rawQuery(
                    "select sum ("+ S_CASH_AMOUNT +") from " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getSCashTotalForPayerAndDate(String teller, String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = S_CASH_PAYER + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ S_CASH_AMOUNT +") from " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getSCashTotalForPayeeAndDate(String teller, String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = S_CASH_PAYEE + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ S_CASH_AMOUNT +") from " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public ArrayList<SkylightCash> getAllSkylightCash() {
        try {
            ArrayList<SkylightCash> skylightCashArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {S_CASH_ID, S_CASH_AMOUNT, S_CASH_CODE, S_CASH_ADMIN, S_CASH_PAYEE, S_CASH_DATE, S_CASH_PAYER, S_CASH_STATUS};
            Cursor cursor = db.query(S_CASH_TABLE, columns, null, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSkylightCashCursorAll(skylightCashArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return skylightCashArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public double getSkylightCashTotalForProfile(int profileID) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = S_CASH_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ S_CASH_AMOUNT +") from " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public double getSkylightCashTotalForProfileAndDate(int profileID, String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = S_CASH_PROFILE_ID + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ S_CASH_AMOUNT +") from " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getTellerCashCountForPayerWithDate(String teller, String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = S_CASH_PAYER + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(teller), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getAllTellerCashCountWithDate(String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getTellerCashCountForPayeeWithDate(String office, String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = S_CASH_PAYEE + "=? AND " + S_CASH_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + S_CASH_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(S_CASH_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public void updateTellerCashWithCode(String office,int tellerCashID,long code,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues stocksUpdateValues = new ContentValues();
            String selection = S_CASH_PROFILE_ID + "=? AND " + S_CASH_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(office), valueOf(tellerCashID)};
            stocksUpdateValues.put(S_CASH_CODE, code);
            stocksUpdateValues.put(S_CASH_STATUS, status);
            db.update(S_CASH_TABLE, stocksUpdateValues, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void deleteSkylightCash(int tellerCashID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TELLER_CASH_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerCashID)};

            db.delete(S_CASH_TABLE,
                    selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateBranchStockCount(String officeBranch,String itemName,int newItemCount) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues stocksUpdateValues = new ContentValues();
            String selection = STOCK_OFFICE + "=? AND " + STOCK_ITEM_NAME + "=?";
            String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(itemName)};
            stocksUpdateValues.put(STOCK_QTY, newItemCount);
            db.update(STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
            //db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public int getStockItemCountForBranch(String packageName, String officeBranch) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = STOCK_ITEM_NAME + "=? AND " + STOCK_OFFICE + "=?";
            String[] selectionArgs = new String[]{valueOf(packageName), valueOf(officeBranch)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(STOCK_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getStocksTotalForBranch(int branchID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + STOCK_QTY + ") as Total FROM " + STOCKS_TABLE, new String[]{" WHERE STOCK_BRANCH_ID=?",String.valueOf(branchID)})){

                if (cursor.moveToFirst()) {

                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public double getTotalStocksTodayForBranch1(int branchID, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0;
        String selection = STOCK_BRANCH_ID + "=? AND " + STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getDouble(7);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return count;


        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        //return 0;
    }
    public ArrayList<Stocks> getAllStocksForBranch(int branchID) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_BRANCH_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(branchID)};
            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getStocksNameFromCursor3(stocksArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Stocks> getStocksForBranchName(String branchName) {
        ArrayList<Stocks> stocksArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName)};

        Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksNameFromCursor3(stocksArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return stocksArrayList;

        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;*/
    }
    public ArrayList<Stocks> getStocksForBranchAtDate(int branchID, String date) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_BRANCH_ID + "=? AND " + STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(branchID), valueOf(date)};

            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getStocksFromCursorProfile(stocksArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int getStockCountAtDateForBranch(String officeBranch,String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = STOCK_OFFICE + "=? AND " + STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(STOCK_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public void deleteStocks(int stockID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockID)};

            db.delete(STOCKS_TABLE,
                    selection,
                    selectionArgs);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public int getStockItemCount(String stockItemName,String model) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = STOCK_ITEM_NAME + "=? AND " + STOCK_MODEL + "=?";
            String[] selectionArgs = new String[]{valueOf(stockItemName), valueOf(model)};
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STOCKS_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(STOCK_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public void updateStocksWithCode(String office,int profileID,long code,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues stocksUpdateValues = new ContentValues();
            String selection = STOCK_OFFICE + "=? AND " + STOCK_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(office), valueOf(profileID)};
            stocksUpdateValues.put(STOCK_CODE, code);
            stocksUpdateValues.put(STOCK_STATUS, status);
            db.update(STOCKS_TABLE, stocksUpdateValues, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }



    public long insertStock(int stockID, int profileID,String stockName,String stockType, String stockModel, String color,String size,int qty,String office,String date,String manager) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(STOCK_ID, stockID);
            values.put(STOCK_PROFILE_ID, profileID);
            values.put(STOCK_ITEM_NAME, stockName);
            values.put(STOCK_TYPE, stockType);
            values.put(STOCK_MODEL, stockModel);
            values.put(STOCK_COLOR, color);
            values.put(STOCK_SIZE, size);
            values.put(STOCK_OFFICE, office);
            values.put(STOCK_QTY, qty);
            values.put(STOCK_DATE, date);
            values.put(STOCK_MANAGER, manager);
            db.insert(STOCKS_TABLE,null,values);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return stockID;
    }

    public void updateStock(int stockID,String qty,String date40th,String date20th,String date10th,String date5th,int defective,String accepter,String accepterDate,String manager) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues paymentUpdateValues = new ContentValues();
            paymentUpdateValues.put(STOCK_QTY, qty);
            paymentUpdateValues.put(STOCK_40_DATE, date40th);
            paymentUpdateValues.put(STOCK_20_DATE, date20th);
            paymentUpdateValues.put(STOCK_10_DATE, date10th);
            paymentUpdateValues.put(STOCK_5_DATE, date5th);
            paymentUpdateValues.put(STOCK_DEFECTIVE, defective);
            paymentUpdateValues.put(STOCK_ACCEPTER, accepter);
            paymentUpdateValues.put(STOCK_ACCEPTANCE_DATE, accepterDate);
            paymentUpdateValues.put(STOCK_MANAGER, manager);
            String selection = STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockID)};
            db.update(STOCKS_TABLE,
                    paymentUpdateValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateStock5th(int stockID,String office,int qty,String date5th) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues paymentUpdateValues = new ContentValues();
            paymentUpdateValues.put(STOCK_OFFICE, office);
            paymentUpdateValues.put(STOCK_QTY, qty);
            paymentUpdateValues.put(STOCK_5_DATE, date5th);
            String selection = STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockID)};
            db.update(STOCKS_TABLE,
                    paymentUpdateValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateStock20th(int stockID,String office,int qty,String date20th) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues paymentUpdateValues = new ContentValues();
            paymentUpdateValues.put(STOCK_OFFICE, office);
            paymentUpdateValues.put(STOCK_QTY, qty);
            paymentUpdateValues.put(STOCK_20_DATE, date20th);
            String selection = STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockID)};
            db.update(STOCKS_TABLE,
                    paymentUpdateValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateStock4oth(int stockID,String office,int qty,String date40th) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues paymentUpdateValues = new ContentValues();
            paymentUpdateValues.put(STOCK_OFFICE, office);
            paymentUpdateValues.put(STOCK_QTY, qty);
            paymentUpdateValues.put(STOCK_40_DATE, date40th);
            String selection = STOCK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(stockID)};
            db.update(STOCKS_TABLE,
                    paymentUpdateValues, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    private void getStocksFromCursorSuper(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int stockID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                String office = cursor.getString(6);
                int qty = cursor.getInt(7);
                String date = cursor.getString(8);
                String date20percent = cursor.getString(10);
                String date5percent = cursor.getString(12);
                int defectiveNo = cursor.getInt(13);
                String stockOutDate = cursor.getString(14);
                String accepter = cursor.getString(15);
                String accepterDate = cursor.getString(16);
                String manager = cursor.getString(17);
                String status = cursor.getString(18);
                //Date date=formatter.parse(cursor.getString(6));

                stocksArrayList.add(new Stocks(stockID, itemName, office,qty,date,date20percent,date5percent,defectiveNo, stockOutDate,accepter,accepterDate,manager,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<Stocks> getAllStocksForDate(String date) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksFromCursorSuper(stocksArrayList, cursor);

                }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Stocks> getAllStocksForToday(String today) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(today)};
            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksFromCursorSuper(stocksArrayList, cursor);

                }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public double getTotalStocksForDate(String date) {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        cursor = db.rawQuery(
                "select Total ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getTotalStocks() {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(
                "select Total ("+ STOCK_QTY +") from " + STOCKS_TABLE ,null,
                null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public double getTotalStocksForToday(String today) {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        cursor = db.rawQuery(
                "select Total ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }

    public double getTotalStocksTodayForProfile(int profileID, String today) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0;
            Cursor cursor=null;
            String selection = STOCK_PROFILE_ID + "=? AND " + STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
            cursor = db.rawQuery(
                    "select sum ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                    selectionArgs);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(STOCK_QTY);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public ArrayList<Stocks> getStocksForProfile(int profileID) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getStocksFromCursorProfile(stocksArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Stocks> getStocksForProfileAtDate(int profileID, String date) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_PROFILE_ID + "=? AND " + STOCK_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};

            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getStocksFromCursorProfile(stocksArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Stocks> getAllStocksForProfile(int profileID) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksFromCursorProfile(stocksArrayList, cursor);

                }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getStocksFromCursorProfile(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int stockID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                String office = cursor.getString(6);
                String type = cursor.getString(2);
                int qty = cursor.getInt(7);
                String date = cursor.getString(8);
                String admin = cursor.getString(18);
                long code = cursor.getLong(20);
                String status = cursor.getString(18);
                stocksArrayList.add(new Stocks(stockID, itemName,type, qty,date,code,office,admin,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public double getStocksTotalForProfile(int profileID) {
        double count = 0;
        Cursor cursor=null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = STOCK_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        cursor = db.rawQuery(
                "select sum ("+ STOCK_QTY +") from " + STOCKS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(STOCK_QTY);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }







    private void getStocksNameFromCursor3(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                String itemName = cursor.getString(1);
                String itemType = cursor.getString(2);
                int qty = cursor.getInt(7);
                stocksArrayList.add(new Stocks(itemName,itemType,qty));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<Stocks> getStocksForTeller3(int profileID) {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = STOCK_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(STOCKS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getStocksNameFromCursor3(stocksArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getStocksNameAndMoreCursor(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int stockID = cursor.getInt(0);
                long stockCode = cursor.getLong(20);
                String itemName = cursor.getString(1);
                String itemType = cursor.getString(2);
                int qty = cursor.getInt(7);
                String color = cursor.getString(4);
                String status = cursor.getString(18);
                stocksArrayList.add(new Stocks(stockID, itemName,itemType,qty,color,stockCode,status));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void getStocksNameFromCursor(ArrayList<String> strings, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                String itemName = cursor.getString(1);
                strings.add(itemName);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllStocksName() {
        ArrayList<String> stockNameArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {STOCK_ITEM_NAME};
        Cursor cursor = db.query(STOCKS_TABLE, columns, STOCK_ITEM_NAME, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStocksNameFromCursor(stockNameArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return stockNameArrayList;
    }





    private void getStocksFromCursorFew(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int stockID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                String itemType = cursor.getString(2);
                int qty = cursor.getInt(7);
                String status = cursor.getString(18);
                stocksArrayList.add(new Stocks(stockID, itemName,itemType,qty,status));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    private void getStocksFromCursor(ArrayList<Stocks> stocksArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int stockID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                String itemType = cursor.getString(2);
                String model = cursor.getString(3);
                String color = cursor.getString(4);
                String size = cursor.getString(5);
                String office = cursor.getString(6);
                int qty = cursor.getInt(7);
                String date = cursor.getString(8);
                int defectiveNo = cursor.getInt(13);
                String status = cursor.getString(18);
                //Date date=formatter.parse(cursor.getString(6));

                stocksArrayList.add(new Stocks(stockID, itemName,itemType, model,color,size,office,qty,date,defectiveNo,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public ArrayList<Stocks> getALLStocksSuper() {
        try {
            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {STOCK_ID,STOCK_ITEM_NAME,STOCK_QTY,STOCK_OFFICE,STOCK_DATE,STOCK_20_DATE,STOCK_STATUS};
            Cursor cursor = db.query(STOCKS_TABLE, columns, null, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getStocksFromCursorSuper(stocksArrayList, cursor);
                    cursor.close();

                }
            db.close();

            return stocksArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public void deleteAdminDeposit(int depositID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = DEPOSIT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(depositID)};
            db.delete(DEPOSIT_TABLE,
                    selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateAdminDeposit( int depositID, String Status,String approver, String approvalDate) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DEPOSIT_ACCOUNT_NAME, Status);
            values.put(DEPOSIT_CONFIRMATION_DATE, approvalDate);
            values.put(DEPOSIT_APPROVER, approver);
            String selection = DEPOSIT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(depositID)};
            db.update(DEPOSIT_TABLE, values, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateAdminDeposit( int depositID, String bank, String acctName, String acctNo, double amount) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DEPOSIT_BANK, bank);
            values.put(DEPOSIT_ACCOUNT_NAME, acctName);
            values.put(DEPOSIT_ACCOUNT, acctNo);
            values.put(DEPOSIT_AMOUNT, amount);
            String selection = DEPOSIT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(depositID)};
            db.update(DEPOSIT_TABLE, values, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateAdminDeposit(int adminDepositID, Uri mImageUri) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DEPOSIT_DOC, String.valueOf(mImageUri));
            String selection = DEPOSIT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(adminDepositID)};
            db.update(DEPOSIT_TABLE, values, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<String> getAllAdminNamesForDeposit() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {DEPOSITOR};
            Cursor res = db.query(DEPOSIT_TABLE,columns,null,null,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(9));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public int getAllAdminDepositBranchCount(String branch) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = DEPOSIT_OFFICE_BRANCH + "=?";
            String[] selectionArgs = new String[]{valueOf(branch)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(DEPOSIT_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    private void getAdminBankDepositFull(ArrayList<AdminBankDeposit> adminBankDepositArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int depositID = cursor.getInt(0);
                int profileID = cursor.getInt(1);
                double depositAmt = Double.parseDouble(cursor.getString(2));
                String officeBranch = cursor.getString(3);
                String type = cursor.getString(4);
                String bank = cursor.getString(5);
                String acctName = cursor.getString(6);
                String acctNo = cursor.getString(7);
                String date = cursor.getString(8);
                String depositor = cursor.getString(9);
                String depositApprover = cursor.getString(10);
                String confirmationDate = cursor.getString(11);
                String status = cursor.getString(12);
                adminBankDepositArrayList.add(new AdminBankDeposit(depositID, profileID,depositAmt,officeBranch,type, bank,acctName, acctNo, date,depositor,depositApprover,confirmationDate,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void getAdminBankDepositImp(ArrayList<AdminBankDeposit> adminBankDepositArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                double depositAmt = Double.parseDouble(cursor.getString(2));
                String officeBranch = cursor.getString(3);
                String type = cursor.getString(4);
                String bank = cursor.getString(5);
                String acctName = cursor.getString(6);
                String acctNo = cursor.getString(7);
                String date = cursor.getString(8);
                String depositor = cursor.getString(9);
                String status = cursor.getString(12);
                adminBankDepositArrayList.add(new AdminBankDeposit(depositAmt,officeBranch,type, bank,acctName, acctNo, date,depositor,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<AdminBankDeposit> getAdminDepositsByDepositor(String depositor) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DEPOSITOR + "=?";
        String[] selectionArgs = new String[]{valueOf(depositor)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositsByProfileID(int profileID) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DEPOSIT_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositsForBranch(String officeBranch) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DEPOSIT_OFFICE_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }

    public ArrayList<AdminBankDeposit> getAdminDepositsAtDate(String date) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        //String selection = DEPOSITOR + "=? AND " + DEPOSIT_DATE + "=?";
        //String[] selectionArgs = new String[]{valueOf(depositor), valueOf(date)};
        String selection = DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, DEPOSIT_OFFICE_BRANCH,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositAtDateAndBranch(String branch,String date) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DEPOSIT_OFFICE_BRANCH + "=? AND " + DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branch), valueOf(date)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminDepositAtDateAndDepositor(String depositor,String date) {
        ArrayList<AdminBankDeposit> adminBankDeposits = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DEPOSITOR + "=? AND " + DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(depositor), valueOf(date)};

        Cursor cursor = db.query(DEPOSIT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDeposits, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAllAdminBankDepositImp() {
        ArrayList<AdminBankDeposit> adminBankDepositArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DEPOSIT_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositImp(adminBankDepositArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return adminBankDepositArrayList;
    }
    public ArrayList<AdminBankDeposit> getAllAdminBankDeposit() {
        ArrayList<AdminBankDeposit> adminBankDepositArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DEPOSIT_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAdminBankDepositFull(adminBankDepositArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return adminBankDepositArrayList;

    }
    public int getAllAdminDepositCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + null,
                null
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllAdminDepositCountDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

    }
    public int getAllAdminDepositCountForProfile(int profileID) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllAdminDepositCountForOffice(String officeBranch) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSIT_OFFICE_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public int getAllAdminDepositCountForAdmin(String adminOfficer) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DEPOSITOR + "=?";
        String[] selectionArgs = new String[]{valueOf(adminOfficer)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + DEPOSIT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(DEPOSIT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }
    public long saveNewAdminDeposit(int depositID,int profileID,String depositor, String date,String office ,String bank, double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DEPOSIT_ID, depositID);
        cv.put(DEPOSIT_PROFILE_ID, profileID);
        cv.put(DEPOSIT_AMOUNT, amount);
        cv.put(DEPOSIT_OFFICE_BRANCH, office);
        cv.put(DEPOSIT_BANK, bank);
        cv.put(DEPOSIT_DATE, date);
        cv.put(DEPOSIT_TRANSACTION_STATUS, "Unverified");
        cv.put(DEPOSITOR, depositor);
        return db.insert(DEPOSIT_TABLE, null, cv);

    }


    public long saveNewProperty(long propertyID, long profileId, String tittleOfProperty, String description, String propertyType, String town, String lga, Double price, String priceDuration, String propertyCapacity, String typeOfLetting,Date propertyDate,Uri propertylink,String status) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROPERTY_ID, propertyID);
            contentValues.put(PROFILE_ID, profileId);
            contentValues.put(PROPERTY_TITTLE, tittleOfProperty);
            contentValues.put(PROPERTY_DESCRIPTION, description);
            contentValues.put(PROPERTY_TYPE, propertyType);
            contentValues.put(PROPERTY_TOWN, town);
            contentValues.put(PROPERTY_LGA, lga);
            contentValues.put(PROPERTY_AMOUNT, valueOf(price));
            contentValues.put(PROPERTY_PRICE_DURATION, priceDuration);
            contentValues.put(PROPERTY_CAPACITY, propertyCapacity);
            contentValues.put(PROPERTY_TYPE_OF_LETTING, typeOfLetting);
            contentValues.put(PROPERTY_DATE, valueOf(propertyDate));
            contentValues.put(PROPERTY_LINK, valueOf(propertylink));
            db.insert(PROPERTY_TABLE,null,contentValues);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return propertyID;
    }

    public void updateProperty(long propertyID, long profileId, String tittleOfProperty, String description, String propertyType, String town, String lga, Double price, String priceDuration, String propertyCapacity, String typeOfLetting,Date propertyDate,Uri propertylink,String status) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            Customer customer = new Customer();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROPERTY_ID, propertyID);
            contentValues.put(PROFILE_ID, profileId);
            contentValues.put(PROPERTY_TITTLE, tittleOfProperty);
            contentValues.put(PROPERTY_DESCRIPTION, description);
            contentValues.put(PROPERTY_TYPE, propertyType);
            contentValues.put(PROPERTY_TOWN, town);
            contentValues.put(PROPERTY_LGA, lga);
            contentValues.put(PROPERTY_AMOUNT, valueOf(price));
            contentValues.put(PROPERTY_PRICE_DURATION, priceDuration);
            contentValues.put(PROPERTY_CAPACITY, propertyCapacity);
            contentValues.put(PROPERTY_TYPE_OF_LETTING, typeOfLetting);
            contentValues.put(PROPERTY_DATE, String.valueOf(propertyDate));
            contentValues.put(PROPERTY_LINK, String.valueOf(propertylink));
            String whereClause = "profileId=?";
            String[] whereArgs = {PROFILE_ID};
            db.update(PROPERTY_TABLE, contentValues, whereClause, whereArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public Uri getPhotoOfProperty(long photoNo) {

        try {
            PropertyImage propertyImage = new PropertyImage();
            Uri pictureUri = propertyImage.getImageUri();
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor cursor = db.query(PROPERTY_PICTURE_TABLE, new String[]{PROPERTY_PICTURE_TITTLE, PROPERTY_PICTURE}, PROPERTY_PICTURE_ID + "=?",
                    new String[]{String.valueOf(photoNo)}, null, PROPERTY_PICTURE_ID, null, null)) {
                if (cursor != null)
                    cursor.moveToFirst();

                return pictureUri;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public void deleteProperty(long propertyID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(PROPERTY_TABLE,
                    "PROPERTY_ID = ? ",
                    new String[]{Long.toString(propertyID)});

            db.delete(PROPERTY_PICTURE_TABLE,
                    "PROPERTY_ID = ? ",
                    new String[]{Long.toString(propertyID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }



    }

    public void deletePropertyPix(long propertyPixID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(PROPERTY_PICTURE_TABLE,
                    "PROPERTY_PICTURE_ID = ? ",
                    new String[]{Long.toString(propertyPixID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public void updateProperty(long profileID, long propertyID, String propertyTittle, String propertyDescription, Double amount, String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROPERTY_TITTLE, propertyTittle);
            values.put(PROPERTY_DESCRIPTION, propertyDescription);
            values.put(PROPERTY_AMOUNT, amount);
            //values.put(PROPERTY_PRICE_DURATION, priceDuration);
            values.put(PROPERTY_STATUS, status);

            // updating row
            db.update(PROPERTY_TABLE, values, PROPERTY_ID + " = ?",
                    new String[]{String.valueOf(propertyID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public long saveNewPropertyPicture(long profileID,long propertyID,long pictureID, String pictureTittle ,Uri pictureUri){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //PropertyImage propertyImage = new PropertyImage();
            cv.put(PROPERTY_ID, propertyID);
            cv.put(PROFILE_ID, profileID);
            cv.put(PROPERTY_PICTURE_ID, pictureID);
            cv.put(PROPERTY_PICTURE_TITTLE, pictureTittle);
            cv.put(PROPERTY_PICTURE, valueOf(pictureUri));
            db.insert(PROPERTY_PICTURE_TABLE, null, cv);
            //long id = db.insert(LOAN_TABLE, null, cv);
            //propertyImage.setPropertyImageID(id);

            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return profileID;
    }
    public Cursor getPropertyPictureCursor(long propertyID) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor;
            if (propertyID >0 ) {
                cursor = db.rawQuery("SELECT * FROM " + PROPERTY_TABLE + " WHERE PROFILE_ID = '" + propertyID + "'  ORDER BY PROPERTY_LGA;", null);
            } else {
                cursor = db.rawQuery("SELECT * FROM " + PROPERTY_TABLE + " WHERE PROFILE_ID = '" + propertyID + "'  ORDER BY PROPERTY_LGA;", null);
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getPropertyTownCursor(String propertyTown) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor;
            if (propertyTown !=null ) {

                cursor = db.rawQuery("SELECT * FROM " + PROPERTY_TABLE + " WHERE PROPERTY_TOWN = '" + propertyTown + "'  ORDER BY PROPERTY_TYPE;", null);
            } else {
                cursor = db.rawQuery("SELECT * FROM " + PROPERTY_TABLE + " WHERE PROPERTY_TOWN = '" + propertyTown + "'  ORDER BY PROPERTY_TYPE;", null);
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Long deleteProperty(String propertyID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            return (long) db.delete(PROPERTY_TABLE,
                    "PROPERTY_ID = ? ",
                    new String[]{(propertyID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Long deletePropertyPicture(String propertyPixID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            return (long) db.delete(PROPERTY_PICTURE_TABLE,
                    "PROPERTY_PICTURE_ID = ? ",
                    new String[]{(propertyPixID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllPropertyTowns() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROPERTY_TOWN + "=?";
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};
            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(7));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllPropertyLGAs() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};

            String selection = PROPERTY_LGA + "=?";
            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(13));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllPropertyCountry() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROPERTY_COUNTRY + "=?";
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};

            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_COUNTRY);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(21));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getPropertyTittleOfLetting() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};

            String selection = PROPERTY_TITTLE + "=?";
            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(3));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getPropertyType() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROPERTY_TYPE + "=?";
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};

            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(17));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllPropertyStates() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROPERTY_STATE + "=?";
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};

            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(8));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllPropertyCapacity() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROPERTY_CAPACITY + "=?";
            String[] columns = {PROPERTY_ID, PROPERTY_TITTLE, PROPERTY_DESCRIPTION,PROPERTY_LINK,PROPERTY_AMOUNT,PROPERTY_PRICE_DURATION};

            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(PROPERTY_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(9));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public int getPropertyCountForProfile(long profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + PROPERTY_TABLE + " WHERE " + PROFILE_ID + "=?",
                    new String[]{valueOf(profileID)}
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(PROPERTY_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getAllPropertyCount() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String countQuery = "SELECT  * FROM " + PROPERTY_TABLE;
            Cursor cursor = null;

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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public ArrayList<Properties> getAllProperties() {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(PROPERTY_TABLE, null, null, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getPropertyFromCursorPublic( propertiesArrayList, cursor);
                    cursor.close();

                }
            db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getPropertyFromCursorPublic(ArrayList<Properties> propertiesArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                long propID = (cursor.getLong(0));
                String title = cursor.getString(3);
                String typeOfLetting = cursor.getString(4);
                String town = cursor.getString(7);
                String state = cursor.getString(8);
                double amount = Double.parseDouble(cursor.getString(14));
                String priceDuration = cursor.getString(15);
                String type = cursor.getString(17);
                Uri propertyImage = Uri.parse(cursor.getString(18));
                String status = cursor.getString(19);
                String description = cursor.getString(20);
                propertiesArrayList.add(new Properties(title, typeOfLetting,town,type, description,state, amount, priceDuration,propertyImage,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    private void getPropertyFromCursorAdmin(ArrayList<Properties> propertiesArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                long propID = (cursor.getLong(0));
                String title = cursor.getString(3);
                String typeOfLetting = cursor.getString(4);
                String town = cursor.getString(7);
                String state = cursor.getString(8);
                double amount = Double.parseDouble(cursor.getString(14));
                String priceDuration = cursor.getString(15);
                String type = cursor.getString(17);
                Uri propertyImage = Uri.parse(cursor.getString(18));
                String status = cursor.getString(19);
                String description = cursor.getString(20);
                double agentFee = Double.parseDouble(cursor.getString(16));
                String ward = cursor.getString(6);
                String date = cursor.getString(5);
                propertiesArrayList.add(new Properties(title, typeOfLetting,town,type, description,state, amount, priceDuration,propertyImage,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public void updateProperty(long biZID,long profileID, String name,String brandName,String typeBiz,String bizEmail,String bizAddress, String ph_no,String state,String regNo) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(PROPERTY_TITTLE, biZID);
            cv.put(PROPERTY_TYPE_OF_LETTING, name);
            cv.put(PROPERTY_WARD, brandName);
            cv.put(PROPERTY_TOWN, typeBiz);
            cv.put(PROPERTY_STATE, bizEmail);
            cv.put(PROPERTY_CAPACITY, bizAddress);
            cv.put(PROPERTY_LGA, ph_no);
            cv.put(PROPERTY_AMOUNT, state);
            cv.put(PROPERTY_PRICE_DURATION, regNo);
            cv.put(PROPERTY_AGENT_FEE, state);
            cv.put(PROPERTY_TYPE, state);
            cv.put(PROPERTY_LINK, state);
            cv.put(PROPERTY_STATUS, state);
            cv.put(PROPERTY_DESCRIPTION, state);
            cv.put(PROPERTY_COUNTRY, state);

            db.update(PROPERTY_TABLE, cv, PROFILE_ID + "=?", new String[]{valueOf(profileID)});
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<Properties> getPropertyByType(String type) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            //String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(branch), valueOf(today)};
            String selection = PROPERTY_TYPE + "=?";
            String[] selectionArgs = new String[]{valueOf(type)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDate(String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            //String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(branch), valueOf(today)};
            String selection = PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndTown(String town,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = PROPERTY_TOWN + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(town), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndLGA(String lga,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = PROPERTY_LGA + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(lga), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndState(String state,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = PROPERTY_STATE + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(state), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndAmount(double price,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = PROPERTY_AMOUNT + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(price), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndCountry(String country,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = PROPERTY_COUNTRY + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(country), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndPriceDuration(String duration,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = PROPERTY_PRICE_DURATION + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(duration), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndPrice(String price, String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_PRICE + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(price), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndCapacity(String capacity,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_CAPACITY + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(capacity), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtDateAndType(String type,String date) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_TYPE + "=? AND " + PROPERTY_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(type), valueOf(date)};
            //String selection = PROPERTY_DATE + "=?";
            //String[] selectionArgs = new String[]{valueOf(date)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtPrice(String price) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_PRICE + "=?";
            String[] selectionArgs = new String[]{valueOf(price)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtType(String type) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_TYPE + "=?";
            String[] selectionArgs = new String[]{valueOf(type)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtTown(String town) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_TOWN + "=?";
            String[] selectionArgs = new String[]{valueOf(town)};
            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtCapacity(String capacity) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_CAPACITY + "=?";
            String[] selectionArgs = new String[]{valueOf(capacity)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtLGA(String lga) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_LGA + "=?";
            String[] selectionArgs = new String[]{valueOf(lga)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtState(String state) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_STATE + "=?";
            String[] selectionArgs = new String[]{valueOf(state)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtCountry(String country) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_COUNTRY + "=?";
            String[] selectionArgs = new String[]{valueOf(country)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Properties> getPropertyAtPriceDuration(String priceDuration) {
        try {
            ArrayList<Properties> propertiesArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROPERTY_PRICE_DURATION + "=?";
            String[] selectionArgs = new String[]{valueOf(priceDuration)};

            Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getPropertyFromCursorPublic(propertiesArrayList, cursor);
            cursor.close();
            //db.close();

            return propertiesArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public Cursor getPropertyProfileCursor(long profileID) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor;
            if (profileID >0 ) {
                cursor = db.rawQuery("SELECT * FROM " + PROPERTY_TABLE + " WHERE PROFILE_ID = '" + profileID + "'  ORDER BY PROPERTY_LGA;", null);
            } else {
                cursor = db.rawQuery("SELECT * FROM " + PROPERTY_TABLE + " WHERE PROFILE_ID = '" + profileID + "'  ORDER BY PROPERTY_LGA;", null);
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<PropertyImage> getImagesForAProp(long propertyID) {
        try {
            ArrayList<PropertyImage> propertyImages = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + PROPERTY_PICTURE_TABLE + " WHERE PROPERTY_ID = '" + propertyID + "'  ORDER BY PROPERTY_PICTURE_ID";

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        PropertyImage propertyImage = new PropertyImage();
                        propertyImage.setPropertyImageID(Long.parseLong(PROPERTY_PICTURE_ID));
                        propertyImage.setTittleOfPropImage(PROPERTY_PICTURE_TITTLE);
                        propertyImage.setImageUri(Uri.parse(PROPERTY_PICTURE));
                        propertyImages.add(propertyImage);
                    } while (cursor.moveToNext());
                }
            }
            return propertyImages;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteBusiness(long businessID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BIZ_TABLE,
                    "BIZ_ID = ? ",
                    new String[]{String.valueOf((businessID))});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public long saveBiz(Business business) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            business = new Business();
            Profile profile = new Profile();
            Values.put(BIZ_ID, business.getBusinessID());
            Values.put(PROFILE_ID, profile.getPID());
            Values.put(BIZ_NAME, valueOf(business.getProfileBusinessName()));
            Values.put(BIZ_BRANDNAME, business.getBizBrandname());
            Values.put(BIZ_TYPE, business.getBizType());
            Values.put(BIZ_REG_NO, business.getBizRegNo());
            Values.put(BIZ_EMAIL, business.getBizEmail());
            Values.put(BIZ_PHONE_NO, business.getBizPhoneNo());
            Values.put(BIZ_ADDRESS, business.getBizAddress());
            Values.put(BIZ_STATE, business.getBizState());
            Values.put(BIZ_PIX, String.valueOf(business.getBizPicture()));
            Values.put(BIZ_STATUS, business.getBizStatus());
            db.insert(BIZ_TABLE,null,Values);
            //long id = db.insert(BIZ_TABLE, null, codeValues);
            //paymentCode.setUID(String.valueOf(id));
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<Business> getBusinessesFromProfile(long profileID) {
        try {
            ArrayList<Business> businesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Profile profile = new Profile();
            if(profile !=null) {
                profileID = profile.getPID();


                Cursor cursor = db.query(BIZ_TABLE, null, PROFILE_ID + "=?",
                        new String[]{String.valueOf(profileID)}, null, null,
                        null, null);
                getBiZsFromCursor(businesses, cursor);
                cursor.close();
                //db.close();

                return businesses;
            }
            return businesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllBizNames() {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_BRANDNAME + "=?";
            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(BIZ_TABLE,null,selection,null,null,null,PROPERTY_TOWN);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(7));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }





    public ArrayList<Business> getAllBusinesses() {
        try {
            ArrayList<Business> businesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Profile profile = new Profile();
            long profileID = profile.getPID();
            Account account = new Account();
            //String accountNo = String.valueOf(account.getAcctID());
            Cursor cursor = db.query(BIZ_TABLE, null, null, null, BIZ_TYPE,
                    null, null);

            getBiZsFromCursor( businesses, cursor);

            cursor.close();
            //db.close();

            return businesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getBiZsFromCursor(ArrayList<Business> businesses, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                long bizID = (cursor.getLong(0));
                String name = cursor.getString(1);
                String brandName = cursor.getString(2);
                String type = cursor.getString(3);
                String regNo = cursor.getString(4);
                String email = cursor.getString(5);
                String phoneNo = cursor.getString(6);
                String address = cursor.getString(7);
                String state = cursor.getString(8);
                String status = cursor.getString(9);
                Uri logo = Uri.parse(cursor.getString(10));
                businesses.add(new Business(bizID, name,brandName,type, regNo, email, phoneNo,address,state,status,logo));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateBusiness(long biZID,long profileID, String name,String brandName,String typeBiz,String bizEmail,String bizAddress, String ph_no,String state,String regNo) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(PROFILE_ID, profileID);
            cv.put(BIZ_ID, biZID);
            cv.put(BIZ_NAME, name);
            cv.put(BIZ_BRANDNAME, brandName);
            cv.put(BIZ_TYPE, typeBiz);
            cv.put(BIZ_EMAIL, bizEmail);
            cv.put(BIZ_ADDRESS, bizAddress);
            cv.put(BIZ_PHONE_NO, ph_no);
            cv.put(BIZ_STATE, state);
            cv.put(BIZ_REG_NO, regNo);
            db.update(BIZ_TABLE, cv, PROFILE_ID + "=?", new String[]{valueOf(profileID)});
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public void updateSavingsStatus(int packageID,int reportId,Double packageBalance,String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        reportId = customerDailyReport.getRecordNo();
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
    public int getPaymentCountToday(String today) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = new String[]{today};
            String selection = PAYMENT_DATE + "=?";
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    public int getPaymentCountTodayForTeller(int profileID,String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PAYMENT_PROF_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

    }
    public int getPaymentCountTodayForCustomer(int customerID,String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = PAYMENT_CUS_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


    }
    public int getPaymentCountTodayForBranch(String branchName,String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }
    public double getTotalPaymentTodayForTeller1(int profileID, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        Cursor cursor=null;
        String selection = PAYMENT_PROF_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }


    public double getTotalPaymentTodayForCustomer(int customerID, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_CUS_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(today)};
        //Cursor cursor = db.rawQuery("select sum("+ REPORT_TOTAL + ") from " + DAILY_REPORT_TABLE, null);
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;


        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }

    public double getTotalPaymentTodayForBranch1(String branchName, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;


        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }
    public double getTotalPaymentForBranch(String branchName) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName)};
        //Cursor cursor = db.rawQuery("select sum("+ PAYMENT_AMOUNT + ") from " + PAYMENTS_TABLE, null);

        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {



        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }


    public double getTotalPaymentForTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection, selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;



        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }
    public double getTotalPaymentToday1(String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }

    public long saveNewAdminBalance(int id, int profileId, int customerID,int packageID,double amount, String date, String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ADMIN_BALANCE_NO, id);
        contentValues.put(ADMIN_EXPECTED_BALANCE, profileId);
        contentValues.put(ADMIN_RECEIVED_AMOUNT, amount);
        contentValues.put(ADMIN_BALANCE_DATE, date);
        contentValues.put(ADMIN_PROFILE_ID, profileId);
        contentValues.put(ADMIN_BALANCE_CUS_ID, customerID);
        contentValues.put(ADMIN_BALANCE_PACKID, packageID);
        contentValues.put(ADMIN_BALANCE_STATUS, packageID);
        db.insert(ADMIN_BALANCE_TABLE,null,contentValues);
        db.close();

        return id;
    }





    public long insertPayment(int paymentID, int  profileID, int customerID, String office, String paymentDate, Payment.PAYMENT_TYPE type, double paymentAmount, long paymentCode,long paymentAcct,String acctType,String approver,String approvalDate,String paymentStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAYMENT_ID, paymentID);
        values.put(PAYMENT_PROF_ID, profileID);
        values.put(PAYMENT_CUS_ID, customerID);
        values.put(PAYMENT_DATE, paymentDate);
        values.put(PAYMENTTYPE, String.valueOf(type));
        values.put(PAYMENT_AMOUNT, paymentAmount);
        values.put(PAYMENT_CODE, paymentCode);
        values.put(PAYMENT_ACCOUNT, paymentAcct);
        values.put(PAYMENT_ACCOUNT_TYPE, acctType);
        values.put(PAYMENT_APPROVER, approver);
        values.put(PAYMENT_APPROVAL_DATE, approvalDate);
        values.put(PAYMENT_ADMIN_ID, "");
        values.put(PAYMENT_OFFICE, office);
        values.put(PAYMENT_STATUS, paymentStatus);
        db.insert(PAYMENTS_TABLE,null,values);


        return paymentID;
    }
    public void updatePayment(int paymentID, long paymentCode, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(PAYMENT_CODE, paymentCode);
        paymentUpdateValues.put(PAYMENT_STATUS, status);
        String selection = PAYMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(paymentID)};
        db.update(PAYMENTS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();


    }
    public void updatePaymentAmount(int paymentID,double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(PAYMENT_AMOUNT, amount);
        String selection = PAYMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(paymentID)};
        db.update(PAYMENTS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();

    }

    public ArrayList<Payment> getALLPaymentsSuper() {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(PAYMENTS_TABLE, null, null, null, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        //db.close();
        return paymentArrayList;
    }
    public ArrayList<Payment> getALLPaymentsSuperToday(String today) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        //db.close();

        return paymentArrayList;
    }
    public ArrayList<Payment> getALLPaymentsBranchToday(String branch,String today) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branch), valueOf(today)};

        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        //db.close();

        return paymentArrayList;
    }
    public ArrayList<Payment> getALLPaymentsTeller(int profileID) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYMENT_PROF_ID + "=? ";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorTeller(paymentArrayList, cursor);
        cursor.close();
        //db.close();

        return paymentArrayList;
    }


    public ArrayList<Payment> getALLPaymentsTellerToday(int profileID,String today) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYMENT_PROF_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorTeller(paymentArrayList, cursor);
        cursor.close();
        db.close();

        return paymentArrayList;

    }
    public ArrayList<Payment> getALLPaymentsBranch(String branch) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYMENT_OFFICE + "=? ";
        String[] selectionArgs = new String[]{valueOf(branch)};

        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        db.close();
        return paymentArrayList;
    }


    private void getPaymentFromCursorAdmin(ArrayList<Payment> paymentArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int profileID = cursor.getInt(1);
                int customerID = cursor.getInt(2);
                String typeOfPayment = cursor.getString(4);
                double amountPaid = cursor.getDouble(5);
                Date date=formatter.parse(cursor.getString(6));
                String approver = cursor.getString(8);
                long code = cursor.getLong(9);
                String acctType = cursor.getString(11);
                String office = cursor.getString(12);
                String status = cursor.getString(13);
                paymentArrayList.add(new Payment(profileID, customerID, typeOfPayment,amountPaid,date,approver,code,acctType,office, status));
            }

        }catch (SQLException | ParseException e)
        {
            e.printStackTrace();
        }


    }
    private void getPaymentFromCursorTeller(ArrayList<Payment> paymentArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {
                int customerID = cursor.getInt(2);
                String typeOfPayment = cursor.getString(4);
                double amountPaid = cursor.getDouble(5);
                Date date=formatter.parse(cursor.getString(6));
                String status = cursor.getString(13);
                paymentArrayList.add(new Payment(customerID, typeOfPayment,amountPaid,date, status));
            }

        }catch (SQLException | ParseException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<Payment> getTellerPayments(int profileID) {
        ArrayList<Payment> payments = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorTeller(payments,cursor);

        cursor.close();
        db.close();

        return payments;
    }
    public void updateUserPassword(int userID,String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        ContentValues savingsUpdateValues2 = new ContentValues();
        savingsUpdateValues.put(PROFILE_PASSWORD, userPassword);
        savingsUpdateValues2.put(PASSWORD, userPassword);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(userID)};
        String selection1 = PROF_ID_FOREIGN_KEY_PASSWORD + "=?";
        String[] selectionArgs2 = new String[]{valueOf(userID)};
        db.update(PROFILES_TABLE,
                savingsUpdateValues, selection, selectionArgs);

        db.update(PASSWORD_TABLE,
                savingsUpdateValues, selection1, selectionArgs2);
        db.close();


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
    public void updateTellerReport(int tellerReportID, int tellerID,String admin, int noOfCustomers, double amountExpected, double amountEntered,String updateDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        double balance=amountExpected-amountEntered;
        savingsUpdateValues.put(TELLER_REPORT_AMOUNT_SUBMITTED, amountEntered);
        savingsUpdateValues.put(TELLER_REPORT_NO_OF_SAVINGS, noOfCustomers);
        savingsUpdateValues.put(TELLER_REPORT_EXPECTED_AMT, amountExpected);
        savingsUpdateValues.put(TELLER_REPORT_APPROVAL_DATE, updateDate);
        savingsUpdateValues.put(TELLER_REPORT_ADMIN, admin);
        savingsUpdateValues.put(TELLER_REPORT_BALANCE, balance);
        savingsUpdateValues.put(TELLER_REPORT_STATUS, status);
        String selection = TELLER_REPORT_ID + "=? AND " + TELLER_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerReportID), valueOf(tellerID)};
        db.update(TELLER_REPORT_TABLE,
                savingsUpdateValues, selection, selectionArgs);
        //db.update(TELLER_REPORT_TABLE, savingsUpdateValues, TELLER_REPORT_ID + " = ?", new String[]{valueOf(tellerReportID)});
        db.close();



    }

    public long insertTeller(int profileID2, String uSurname, String uFirstName, String uPhoneNumber, String dateOfBirth, String uEmail, String uAddress, String selectedGender, String office, String selectedState, Uri picture, String joinedDate, String stringNIN, String uUserName, String uPassword) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues values3 = new ContentValues();
        int superAdminID = 0;
        String name=uSurname+""+ uFirstName;
        String passwordCoded=PasswordHelpers.passwordHash(uPassword);
        String stringNIN2=PasswordHelpers.passwordHash(stringNIN);

        try {
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


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return profileID2;
    }
    public long insertSuperAdmin(int profileID,String surname,String firstName,String phoneNo,String email,String dob,String gender,String address, String office,String userName,String password,Uri profilePix) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues picValue = new ContentValues();
        //int superAdminID = 0;
        String name=surname+""+ firstName;
        String passwordCoded=PasswordHelpers.passwordHash(password);
        try {
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


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profileID;
    }

    public long insertTellerReport2(int tellerID,int officeBranchID, String dateOfReport, String officeBranch, double amountEntered, int noOfCustomers,String cmName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long tellerReportID=0;
        values.put(TELLER_REPORT_OFFICE_ID, officeBranchID);
        values.put(TELLER_REPORT_ID, tellerReportID);
        values.put(TELLER_REPORT_ADMIN_ID, "");
        values.put(TELLER_REPORT_PROF_ID, tellerID);
        values.put(TELLER_REPORT_DATE, dateOfReport);
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, amountEntered);
        values.put(TELLER_REPORT_NO_OF_SAVINGS, noOfCustomers);
        values.put(TELLER_REPORT_BALANCE, "");
        values.put(TELLER_REPORT_AMT_PAID, "");
        values.put(TELLER_REPORT_EXPECTED_AMT, "");
        values.put(TELLER_REPORT_BRANCH, officeBranch);
        values.put(TELLER_REPORT_ADMIN, cmName);
        values.put(TELLER_REPORT_MARKETER, cmName);
        values.put(TELLER_REPORT_STATUS, "");
        db.insert(TELLER_REPORT_TABLE,null,values);

        return tellerReportID;
    }

    public long insertTellerReport1(int officeBranchID,int adminID, int tellerID, String reportDate,double reportAmount,int noOfSavings,double reportBalance, double amtPaid,double expectedAmt,String branchName,String manager,String reportStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int tellerReportID=0;
        values.put(TELLER_REPORT_OFFICE_ID, officeBranchID);
        values.put(TELLER_REPORT_ID, tellerReportID);
        values.put(TELLER_REPORT_ADMIN_ID, adminID);
        values.put(TELLER_REPORT_PROF_ID, tellerID);
        values.put(TELLER_REPORT_DATE, String.valueOf(reportDate));
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, reportAmount);
        values.put(TELLER_REPORT_NO_OF_SAVINGS, noOfSavings);
        values.put(TELLER_REPORT_BALANCE, reportBalance);
        values.put(TELLER_REPORT_AMT_PAID, amtPaid);
        values.put(TELLER_REPORT_EXPECTED_AMT, expectedAmt);
        values.put(TELLER_REPORT_BRANCH, branchName);
        values.put(TELLER_REPORT_ADMIN, manager);
        values.put(TELLER_REPORT_MARKETER, manager);
        values.put(TELLER_REPORT_STATUS, reportStatus);
        db.insert(TELLER_REPORT_TABLE,null,values);

        return tellerReportID;
    }
    public int getTellerReportCountTodayForBranch(String branchName,String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TELLER_REPORT_BRANCH + "=? AND " + TELLER_REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TELLER_REPORT_TABLE + " WHERE " + selection,
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
        db.close();
        return count;

    }

    public Cursor readTellerReport() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                TELLER_REPORT_DATE,
                TELLER_REPORT_AMOUNT_SUBMITTED,
                TELLER_REPORT_NO_OF_SAVINGS,
                TELLER_REPORT_BALANCE,
                TELLER_REPORT_AMT_PAID,
                TELLER_REPORT_EXPECTED_AMT,
                TELLER_REPORT_BRANCH,
                TELLER_REPORT_ADMIN,
                TELLER_REPORT_MARKETER,
                TELLER_REPORT_STATUS
        };
        Cursor cursor = db.query(
                TELLER_REPORT_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }





    public long insertBirthDay3(Birthday birthday1) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String birthdayD = birthday1.getBirthDay();
            String surName =birthday1.getbSurName();
            String firstName =birthday1.getbFirstName();
            String phone =birthday1.getProfilePhoneNumber();
            String email =birthday1.getBEmail();
            String Status =birthday1.getProfileStatus();
            long birthdayId =birthday1.getPID();
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = mdformat.format(calendar.getTime());

            ContentValues contentValues = new ContentValues();
            contentValues.put(BIRTHDAY_ID, birthdayId);
            contentValues.put(B_DOB, birthdayD);
            contentValues.put(BIRTHDAY_STATUS, Status);
            contentValues.put(B_SURNAME, surName);
            contentValues.put(B_FIRSTNAME, firstName);
            contentValues.put(B_PHONE, phone);
            contentValues.put(B_EMAIL, email);
            contentValues.put(BIRTHDAY_DAYS_BTWN, birthday1.getDaysInBetween(currentDate,birthdayD));
            contentValues.put(BIRTHDAY_DAYS_REMAINING, birthday1.getFormattedDaysRemainingString(currentDate,birthdayD));
            sqLiteDatabase.insert(BIRTHDAY_TABLE,null,contentValues);
            sqLiteDatabase.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
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
    public void updateAParticularTellerReport(int tellerID, int currentReportID, TellerReport tellerReport,double amtSubmitted,String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        double expectedAmt=tellerReport.getAmtExpected();
        double amtPaid=tellerReport.getAmtPaid();
        double balance=expectedAmt-amtPaid-amtSubmitted;
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, amtSubmitted);
        values.put(TELLER_REPORT_BALANCE, balance);
        values.put(TELLER_REPORT_EXPECTED_AMT, expectedAmt);
        values.put(TELLER_REPORT_AMT_PAID, amtPaid);
        values.put(TELLER_REPORT_STATUS, status);
        String selection = TELLER_REPORT_ID + "=? AND " + TELLER_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(currentReportID), valueOf(tellerID)};
        db.update(TELLER_REPORT_TABLE,
                values, selection, selectionArgs);
    }


    public int getStandingOrderCountToday(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SO_START_DATE + "=?";
        String[] selectionArgs = new String[]{today};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
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
    public int getAllNewCusCountForToday(String joinedDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(joinedDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        return count;
    }
    public int getTellerTxForToday(int tellerID,String txDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_PROF_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(txDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public int getAllTxCountForToday(String txDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(txDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;


    }
    public int getCustomerTxForToday(int customerID,String txDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_CUS_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(txDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }

    public int getTxCountCustomer(int customerID) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
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
    public int getNewCustomersCountForTodayTeller(int profileID,String today) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CUSTOMER_PROF_ID + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getNewCustomersCountForTodayOffice(String  office,String today) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CUSTOMER_OFFICE + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getNewCustomersCountForToday(String today) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CUSTOMER_DATE_JOINED + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(CUSTOMER_ID);
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

    public int getAllProfileCount() {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = null;
            String[] selectionArgs = null;
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + PROFILES_TABLE + " WHERE " +  selection,
                    selectionArgs);
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(PROFILE_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCusCountTodayForTeller1(int tellerID, String today) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = CUSTOMER_PROF_ID + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(today)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(CUSTOMER_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public int getCusCountToday(String today) {

        try {
            int count = 0;
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = CUSTOMER_DATE_JOINED + "=?";
            String[] selectionArgs = new String[]{valueOf(today)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " +  selection,
                    selectionArgs
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(CUSTOMER_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public int getSavingsCusCountToday(int cusID,String today) {
        int count = 0;
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getTotalSavingsForTeller(int tellerID) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
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
    public int getTellerReportCountForDate(int profileID,String date) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = REPORT_PROF_ID_FK + "=? AND " + TELLER_REPORT_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + TELLER_REPORT_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(TELLER_REPORT_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getTellerReportCountAll(int profileID) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = REPORT_PROF_ID_FK + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + TELLER_REPORT_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count=cursor.getColumnIndex(TELLER_REPORT_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public double getTotalTellerReportAmountSubmittedTodayForBranch(String branchName, Date today) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = TELLER_REPORT_BRANCH + "=? AND " + TELLER_REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ TELLER_REPORT_AMOUNT_SUBMITTED +") from " + TELLER_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TELLER_REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return count;
    }

    public Cursor readAParticularTellerReport(int tellerReportID,int tellerID) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = TELLER_REPORT_ID + "=? AND " + REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerReportID), valueOf(tellerID)};

        try {
            String[] projection = {
                    TELLER_REPORT_DATE,
                    TELLER_REPORT_AMOUNT_SUBMITTED,
                    TELLER_REPORT_NO_OF_SAVINGS,
                    TELLER_REPORT_BALANCE,
                    TELLER_REPORT_AMT_PAID,
                    TELLER_REPORT_EXPECTED_AMT,
                    TELLER_REPORT_BRANCH,
                    TELLER_REPORT_ADMIN,
                    TELLER_REPORT_MARKETER,
                    TELLER_REPORT_STATUS
            };

            Cursor cursor = db.query(
                    TELLER_REPORT_TABLE,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            return cursor;
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<TellerReport> getTellerReportsAll() {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return tellerReportArrayList;
    }
    private void getTellerReportFromCursor(ArrayList<TellerReport> tellerReportArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                int tellerReportId = cursor.getInt(0);
                String date=cursor.getString(4);
                double amount = cursor.getDouble(5);
                int noOfSaving = cursor.getInt(6);
                double balance = cursor.getDouble(7);
                double amountPaid = cursor.getDouble(8);
                double expectedAmount = cursor.getDouble(9);
                String marketer = cursor.getString(11);
                String status = cursor.getString(12);
                tellerReportArrayList.add(new TellerReport(tellerReportId, date, amount,noOfSaving,balance,amountPaid,expectedAmount,marketer, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public ArrayList<TellerReport> getTellerReportForABranch(String branchName) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_REPORT_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName)};
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tellerReportArrayList;
    }
    public ArrayList<StandingOrder> getSOEndingCustomDay(String customEndDate) {
        ArrayList<StandingOrder> standingOrderArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SO_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customEndDate)};

        Cursor cursor = db.query(STANDING_ORDER_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrderArrayList,cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return standingOrderArrayList;
    }
    public void deleteTellerReport(int tellerReportID) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TELLER_REPORT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerReportID)};
            db.delete(TELLER_REPORT_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void deleteTeller(int tellerID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CUSTOMER_TELLER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerID)};

            db.delete(CUSTOMER_TELLER_TABLE,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    private void getTellerReportFromCursorSuper(ArrayList<TellerReport> tellerReportArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                int tellerReportId = cursor.getInt(0);
                String date=cursor.getString(4);
                double amount = cursor.getDouble(5);
                int noOfSaving = cursor.getInt(6);
                double balance = cursor.getDouble(7);
                double amountPaid = cursor.getDouble(8);
                double expectedAmount = cursor.getDouble(9);
                String marketer = cursor.getString(11);
                String status = cursor.getString(12);
                tellerReportArrayList.add(new TellerReport(tellerReportId, date, amount,noOfSaving,balance,amountPaid,expectedAmount,marketer, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<TellerReport> getTellerReportsForBranch(String todayDate,String officeBranch) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_REPORT_DATE + "=? AND " + TELLER_REPORT_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate), valueOf(officeBranch)};
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tellerReportArrayList;

    }
    public ArrayList<TellerReport> getTellerReportsForDate(int profileID,String date) {
        try {
            ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = REPORT_PROF_ID_FK + "=? AND " + TELLER_REPORT_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
            Cursor cursor = db.query(TELLER_REPORT_TABLE, null,  selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTellerReportFromCursor(tellerReportArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            db.close();

            return tellerReportArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TellerReport> getTellerReportForTeller(int tellerID) {
        try {
            ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = REPORT_PROF_ID_FK + "=?";
            String[] selectionArgs = new String[]{valueOf(tellerID)};

            Cursor cursor = db.query(TELLER_REPORT_TABLE, null,  selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTellerReportFromCursor(tellerReportArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return tellerReportArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public long insertAdminUser(int profileID, String surname, String firstName, String phoneNO, String emailAddress, String dob, String gender, String address, String officeBranch, String dateJoined, String userName, String password, String nin, String selectedState, Uri selectedImage, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(ADMIN_PROFILE_ID, profileID);
            values.put(ADMIN_SURNAME, surname);
            values.put(ADMIN_FIRST_NAME, firstName);
            values.put(ADMIN_PHONE_NUMBER, phoneNO);
            values.put(ADMIN_EMAIL_ADDRESS, emailAddress);
            values.put(ADMIN_DOB, dob);
            values.put(ADMIN_GENDER, gender);
            values.put(ADMIN_ADDRESS, address);
            values.put(ADMIN_OFFICE, officeBranch);
            values.put(ADMIN_DATE_JOINED, dateJoined);
            values.put(ADMIN_USER_NAME, userName);
            values.put(ADMIN_PASSWORD, password);
            values.put(ADMIN_NIN, nin);
            values.put(ADMIN_PIX, String.valueOf(selectedImage));
            values.put(ADMIN_STATUS, status);
            db.insert(ADMIN_TABLE,null,values);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profileID;
    }


    public long insertTeller(TellerReport tellerReport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(TELLER_REPORT_ID, tellerReport.getTellerReportID());
            values.put(TELLER_REPORT_AMOUNT_SUBMITTED, tellerReport.getAmtSubmitted());
            values.put(TELLER_REPORT_BRANCH, tellerReport.getReport_Office_Branch());
            values.put(TELLER_REPORT_ADMIN, tellerReport.getAdminName());
            values.put(TELLER_REPORT_DATE, tellerReport.getTellerReportDate());
            values.put(TELLER_REPORT_NO_OF_SAVINGS, tellerReport.getNoOfSavings());
            values.put(TELLER_REPORT_OFFICE_ID, tellerReport.getReport_Office_Branch());
            values.put(TELLER_REPORT_PROF_ID, tellerReport.getTellerReportID());
            values.put(TELLER_REPORT_BALANCE, tellerReport.getBalance());
            values.put(TELLER_REPORT_MARKETER, tellerReport.getReportMarketer());
            values.put(TELLER_REPORT_AMT_PAID, tellerReport.getAmtPaid());
            values.put(TELLER_REPORT_EXPECTED_AMT, tellerReport.getAmtExpected());
            values.put(TELLER_REPORT_STATUS, tellerReport.getReportStatus());
            db.insert(TELLER_REPORT_TABLE,null,values);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
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


    public void updateGrpAcctStatus(long grpAccountNo,long profileID,double accountBalance,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues savingsUpdateValues = new ContentValues();
            savingsUpdateValues.put(GRPA_BALANCE, accountBalance);
            savingsUpdateValues.put(GRPA_STATUS, status);
            db.update(PROFILES_TABLE, savingsUpdateValues, PROFILE_ID + " = ?", new String[]{valueOf(profileID)});
            db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRPA_ID + " = ?", new String[]{valueOf(grpAccountNo)});
            db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateGrpSavingsStatus(long grpSavingsAcctID,int grpSavingsID,double amount,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues savingsUpdateValues = new ContentValues();
            CustomerDailyReport customerDailyReport = new CustomerDailyReport();
            savingsUpdateValues.put(GS_STATUS, status);
            savingsUpdateValues.put(GROUP_AMOUNT, amount);
            db.update(GROUP_SAVINGS_TABLE, savingsUpdateValues, GS_ID + " = ?", new String[]{valueOf(grpSavingsID)});
            db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRPA_ID + " = ?", new String[]{valueOf(grpSavingsAcctID)});
            db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateGrpAcct(long grpAcctID, String tittle, String purpose, String surname, String firstName, String phoneNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        savingsUpdateValues.put(GRPA_TITTLE, tittle);
        savingsUpdateValues.put(GRPA_PURPOSE, purpose);
        savingsUpdateValues.put(GRPA_SURNAME, surname);
        savingsUpdateValues.put(GRPA_FIRSTNAME, firstName);
        savingsUpdateValues.put(GRPA_PHONE, phoneNo);
        //db.update(GROUP_SAVINGS_TABLE, savingsUpdateValues, GS_ID + " = ?", new String[]{valueOf(grpSavingsID)});
        db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRPA_ID + " = ?", new String[]{valueOf(grpAcctID)});
        db.close();

    }


    public long insertGroupAccount(long grpAccountNo, long profileID,String tittle, String purpose, String firstName, String lastName,String phoneNo,String emailAddress,Date startDate,double accountBalance,Date endDate,String status) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GRPA_ID, grpAccountNo);
            contentValues.put(PROFILE_ID, profileID);
            contentValues.put(GRPA_TITTLE, tittle);
            contentValues.put(GRPA_PURPOSE, purpose);
            contentValues.put(GRPA_SURNAME, lastName);
            contentValues.put(GRPA_FIRSTNAME, firstName);
            contentValues.put(GRPA_PHONE, phoneNo);
            contentValues.put(GRPA_EMAIL, emailAddress);
            contentValues.put(GRPA_START_DATE, String.valueOf(startDate));
            contentValues.put(GRPA_BALANCE, accountBalance);
            contentValues.put(GRPA_END_DATE, String.valueOf(endDate));
            contentValues.put(GRPA_STATUS, status);
            sqLiteDatabase.insert(GRP_ACCT_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return grpAccountNo;
    }

    public Cursor getAllGroupProfiles(long grpAcctID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM GRP_PROFILE_TABLE WHERE GRPA_ID = ?",
                    new String[]{valueOf(grpAcctID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getAllGroupSavingsForProfile(long profileID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM GROUP_SAVINGS_TABLE WHERE PROFILE_ID = ?",
                    new String[]{valueOf(profileID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public double getGrpSavingsTotal(long grpAcctID) {
        try {
            GroupAccount groupAccount = new GroupAccount();
            grpAcctID=groupAccount.getGrpAccountNo();
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + GROUP_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE GRPA_ID=?",String.valueOf(grpAcctID)})){

                if (cursor.moveToFirst()) {

                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public double getGrpSavingsForProfile(long profileID) {
        try {
            Profile profile = new Profile();
            profileID=profile.getPID();
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + GROUP_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE PROFILE_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public ArrayList<GroupSavings> getGrpSavingsForCurrentProfile(long profileID) {
        try {
            ArrayList<GroupSavings> groupSavings = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE PROFILE_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    do {
                        GroupSavings savings = new GroupSavings();
                        savings.setGrpSavingsID(Integer.parseInt(cursor.getString(0)));
                        savings.setGrpSavingsAmount(Double.parseDouble(cursor.getString(1)));
                        savings.seStatus(cursor.getString(3));
                        try {
                            savings.setSavingsDate(formatter.parse(cursor.getString(2)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        groupSavings.add(savings);
                    } while (cursor.moveToNext());


                }
                return groupSavings;
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<GroupAccount> getGrpAcctsForCurrentProfile(long profileID) {
        try {
            ArrayList<GroupAccount> groupAccounts = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GRP_ACCT_TABLE, new String[]{" WHERE PROFILE_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    do {
                        GroupAccount groupAccount = new GroupAccount();
                        groupAccount.setGrpAccountNo(Long.parseLong(cursor.getString(0)));
                        groupAccount.setTittle(cursor.getString(1));
                        groupAccount.setPurpose(cursor.getString(2));
                        groupAccount.setLastName(cursor.getString(3));
                        groupAccount.setFirstName(cursor.getString(4));
                        groupAccount.seGrpPhoneNo(cursor.getString(5));


                        try {
                            groupAccount.setStartDate(formatter.parse(cursor.getString(6)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        groupAccount.setEndDate(formatter.parse(cursor.getString(7)));
                        groupAccounts.add(groupAccount);
                    } while (cursor.moveToNext());


                }
                return groupAccounts;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Profile> getProfilesForCurrentGrpAccount(long grpAcctID) {
        try {
            ArrayList<Profile> profiles = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GRP_ACCT_TABLE, new String[]{" WHERE GRPA_ID=?",String.valueOf(grpAcctID)})){

                if (cursor.moveToFirst()) {

                    do {
                        Profile userProfile = new Profile();
                        userProfile.setPID(Integer.parseInt((cursor.getString(0))));
                        userProfile.setProfileLastName(cursor.getString(1));
                        userProfile.setProfileFirstName(cursor.getString(2));
                        userProfile.setProfileIdentity(cursor.getString(3));
                        userProfile.setProfilePicture(Uri.parse(cursor.getString(4)));
                        userProfile.setProfilePhoneNumber(cursor.getString(5));
                        profiles.add(userProfile);
                    } while (cursor.moveToNext());


                }
                return profiles;
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<GroupAccount> getAllGroupAcctList() {
        try {
            ArrayList<GroupAccount> groupAccountsList = new ArrayList<>();

            // Select all Query
            String selectQuery = "SELECT * FROM " + GRP_ACCT_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        GroupAccount groupAccount = new GroupAccount();
                        groupAccount.setGrpAccountNo(Long.parseLong(cursor.getString(0)));
                        groupAccount.setProfileID(Long.parseLong(cursor.getString(1)));
                        groupAccount.setPurpose(cursor.getString(3));
                        groupAccount.setGrpAcctBalance(Double.parseDouble(cursor.getString(10)));
                        try {
                            groupAccount.setStartDate(formatter.parse(cursor.getString(8)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            groupAccount.setEndDate(formatter.parse(cursor.getString(9)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        groupAccount.setIsComplete(Boolean.parseBoolean(cursor.getString(11)));


                        groupAccountsList.add(groupAccount);
                    } while (cursor.moveToNext());
                }
            }
            return groupAccountsList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteGroupAcct(long grpAcctID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(GRP_ACCT_TABLE, GRPA_ID + "=?",
                    new String[]{String.valueOf(grpAcctID)});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public GroupAccount getGrpAcct(String grpID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            GroupAccount groupAccount;
            try (Cursor cursor = db.query(GRP_ACCT_TABLE, new String[]
                            {
                                    GRPA_ID,
                                    PROFILE_ID,
                                    GRPA_TITTLE,
                                    GRPA_PURPOSE,
                                    GRPA_FIRSTNAME,
                                    GRPA_START_DATE,GRPA_BALANCE,GRPA_STATUS
                            }, BOOKING_ID + "=?",

                    new String[]{String.valueOf(grpID)}, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                groupAccount = null;
                if (cursor != null) {
                    try {
                        groupAccount = new GroupAccount(Integer.parseInt(cursor.getString(0)),Long.parseLong(cursor.getString(1)), cursor.getString(2),
                                cursor.getString(3), cursor.getString(4),Double.parseDouble(cursor.getString(5)),formatter.parse(cursor.getString(6)),
                                 cursor.getString(7));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            return groupAccount;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public long insertGroupSavings(int grpSavingsID,int grpSavingsAcctID,long profileID,double amount, Date savingsDate, String status) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ID, profileID);
            contentValues.put(GRPA_ID, grpSavingsAcctID);
            contentValues.put(GS_ID, grpSavingsID);
            contentValues.put(GROUP_AMOUNT, amount);
            contentValues.put(GROUP_DATE, String.valueOf(savingsDate));
            contentValues.put(GS_STATUS, status);
            sqLiteDatabase.insert(GROUP_SAVINGS_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return profileID;
    }

    public long insertAccountType(long number, AccountTypes name, BigDecimal interestRate) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_TYPE_ID, number);
            contentValues.put(ACCOUNT_TYPE_NAME, String.valueOf(name));
            contentValues.put(ACCOUNT_TYPE_INTEREST, interestRate.toPlainString());
            sqLiteDatabase.insert(ACCOUNT_TYPES_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return number;
    }
    public void updateMessage(int messageID,String messageUpdate) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues savingsUpdateValues = new ContentValues();
            CustomerDailyReport customerDailyReport = new CustomerDailyReport();
            savingsUpdateValues.put(MESSAGE_DETAILS, messageUpdate);
            String selection = MESSAGE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(messageID)};
            String selection3 = MESSAGE_REPLY_MESSAGE_ID + "=?";
            String[] selectionArgs3 = new String[]{valueOf(messageID)};
            db.update(MESSAGE_REPLY_TABLE, savingsUpdateValues, selection3, selectionArgs3);
            db.update(MESSAGE_TABLE, savingsUpdateValues, selection, selectionArgs);
            db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }



    public long insertUserEmergencyReport(int locID, int profileID, String time, String type,String report, String latLng) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(EMERGENCY_REPORT_PROF_ID, profileID);
            contentValues.put(EMERGENCY_LOCID, locID);
            contentValues.put(EMERGENCY_LOCTIME, time);
            contentValues.put(EMERGENCY_REPORT_TYPE, type);
            contentValues.put(EMERGENCY_REPORT, report);
            contentValues.put(EMERGENCY_REPORT_LATLNG, latLng);
            sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return locID;
    }
    public long insertCustomerLocation(int customerID, LatLng latLng) {
        try {

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUS_LOC_CUS_ID, customerID);
            contentValues.put(CUSTOMER_LATLONG, String.valueOf(latLng));
            sqLiteDatabase.insert(CUSTOMER_LOCATION_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customerID;
    }


    public long insertBooking(Bookings bookings) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BOOKING_ID, bookings.getBookingID());
            values.put(BOOKING_TITTLE, bookings.getTittle());
            values.put(BOOKING_CLIENT_NAME, bookings.getClientName());
            values.put(BOOKING_DATE, valueOf(bookings.getDateOfBooking()));
            values.put(BOOKING_LOCATION, bookings.getLocation());
            values.put(BOOKING_OCCURENCE_NO, bookings.getOccurrenceNo());
            values.put("ITISRECCURRING", bookings.getIsRecurring());
            db.insert(BOOKING_TABLE, null, values);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public List<Bookings> getAllBookingsAdmin() {
        try {
            List<Bookings> bookings = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + BOOKING_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        Bookings bookings1 = new Bookings();
                        bookings1.setBookingID(BOOKING_ID_COLUMN);
                        bookings1.setTittle(BOOKING_TITTLE);
                        bookings1.setClientName(BOOKING_CLIENT_NAME);
                        try {
                            bookings1.setBookingDate(formatter.parse(String.valueOf(BOOKING_DATE_COLUMN)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bookings1.setLocation(BOOKING_LOCATION);
                        bookings1.setStatus(BOOKING_STATUS);
                        bookings1.isRecurring(cursor.getString(6));
                        bookings1.setProfileID(Long.parseLong(cursor.getString(7)));
                        bookings1.setCustomerID(Long.parseLong(cursor.getString(8)));
                        bookings.add(bookings1);
                    } while (cursor.moveToNext());
                }
            }
            return bookings;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Bookings getBooking(String bookingID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Bookings bookings;
            try (Cursor cursor = db.query(BOOKING_TABLE, new String[]
                            {
                                    BOOKING_ID,
                                    BOOKING_TITTLE,
                                    BOOKING_CLIENT_NAME,
                                    BOOKING_DATE,
                                    BOOKING_LOCATION,
                                    BOOKING_OCCURENCE_NO,
                                    BOOKING_STATUS
                            }, BOOKING_ID + "=?",

                    new String[]{String.valueOf(bookingID)}, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                bookings = null;
                if (cursor != null) {
                    try {
                        bookings = new Bookings(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                                cursor.getString(2), formatter.parse(cursor.getString(3)), cursor.getString(4),
                                Boolean.parseBoolean(cursor.getString(5)), cursor.getString(6));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            return bookings;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    public int getBookingCount() {
        try {
            String countQuery = "SELECT * FROM " + BOOKING_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();
            return cursor.getCount();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public long insertBooking(int booking_ID,  long PROFILE_ID, long CUSTOMER_ID, String tittle, String ClientName, Date bookingDate, String location, Boolean isRecurring, String status) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //double packageCount =customerDailyReport.getTotalSavingsForPackage(investment_ID)% 31;
            contentValues.put(String.valueOf(BOOKING_ID), booking_ID);
            contentValues.put(String.valueOf(PROFILE_ID), PROFILE_ID);
            contentValues.put(String.valueOf(CUSTOMER_ID), CUSTOMER_ID);
            contentValues.put(BOOKING_TITTLE, tittle);
            contentValues.put(BOOKING_CLIENT_NAME, ClientName);
            contentValues.put(BOOKING_DATE, String.valueOf(bookingDate));
            contentValues.put(BOOKING_LOCATION, location);
            contentValues.put(String.valueOf(ITISRECCURRING), String.valueOf(isRecurring));
            contentValues.put(String.valueOf(BOOKING_STATUS), status);
            sqLiteDatabase.insertWithOnConflict(BOOKING_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return PROFILE_ID;
    }
    public void updateBooking(long bookingID) {
        try {
            Bookings bookings = new Bookings();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BOOKING_DATE, String.valueOf(bookings.getDateOfBooking()));
            values.put(BOOKING_LOCATION, bookings.getLocation());
            values.put(BOOKING_OCCURENCE_NO, bookings.getIsRecurring());
            values.put(BOOKING_STATUS, bookings.getStatus());

            // Updating row
            db.update(BOOKING_TABLE, values, BOOKING_ID + "=?",
                    new String[]{String.valueOf(bookingID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public void deleteBooking(long bookingID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BOOKING_TABLE, BOOKING_ID + "=?",
                    new String[]{String.valueOf(bookingID)});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public Cursor getBookingCursor(String tittle, Date bookingDate) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor;
            if (bookingDate !=null ) {
                cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE BOOKING_DATE = '" + bookingDate + "' AND NBRBEDROOMS = '" + tittle + "'  ORDER BY BOOKING_ID;", null);
            } else {
                cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE BOOKING_DATE = '" + bookingDate + "'  ORDER BY BOOKING_ID;", null);
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, reminder.getTitle());

            values.put(KEY_DATE, valueOf(reminder.getDate()));
            values.put(KEY_TIME, valueOf(reminder.getTime()));
            values.put(KEY_REPEAT, reminder.getRepeat());
            values.put(KEY_REPEAT_NO, reminder.getRepeatNo());
            values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
            values.put(KEY_ACTIVE, reminder.getActive());
            db.insert(TABLE_REMINDERS, null, values);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public List<AdminBalance> getAllAdminBalances() {
        try {
            List<AdminBalance> adminBalances = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        AdminBalance adminBalance = new AdminBalance();
                        adminBalance.setAdminBalanceID(Integer.parseInt(cursor.getString(0)));
                        adminBalance.setAdminExpectedBalance(Double.parseDouble(cursor.getString(1)));
                        adminBalance.setAdminExpectedBalance(Double.parseDouble(cursor.getString(1)));
                        adminBalance.setAdminReceivedBalance(Double.parseDouble(cursor.getString(2)));
                        adminBalance.setAdminBalanceDate(cursor.getString(4));

                        adminBalances.add(adminBalance);
                    } while (cursor.moveToNext());
                }
            }
            return adminBalances;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public double getAdminExpectedBalance() {
        try {
            AdminBalance adminBalance = new AdminBalance();
            String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
            SQLiteDatabase db = this.getWritableDatabase();
            //String[] whereArgs = new String[]{searchString};


            try (Cursor cursor = db.rawQuery("SELECT SUM(" + ADMIN_EXPECTED_BALANCE + ") as Total FROM " + ADMIN_BALANCE_TABLE, null)) {

                if (cursor.moveToFirst()) {

                    //int total = cursor.getInt(cursor.getColumnIndex(ADMIN_EXPECTED_BALANCE));
                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public double getAdminReceivedBalance() {
        try {
            String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + ADMIN_RECEIVED_AMOUNT + ") as Total FROM " + ADMIN_BALANCE_TABLE, null)) {

                if (cursor.moveToFirst()) {

                    //int total = cursor.getInt(cursor.getColumnIndex(ADMIN_EXPECTED_BALANCE));
                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        //AdminBalance adminBalance = new AdminBalance();

        return 0;
    }

    public double getAdminBalanceCount() {
        try {
            String countQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();
            return cursor.getCount();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public Cursor getUserDetailsFromUserName(String userName) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE WHERE USERNAME = ?",
                    new String[]{valueOf(userName)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @SuppressLint("Recycle")
    public Profile getUserDetailsFromPhoneNumber(String phoneNumber) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return (Profile) sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE WHERE USER_PHONE = ?",
                new String[]{valueOf(phoneNumber)});
    }
    public ImportantDates getReminder(String mReceivedID) {
        SQLiteDatabase db = this.getReadableDatabase();

        ImportantDates reminder;
        try (Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE
                        }, KEY_ID + "=?",

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


    public List<Birthday> getAllBirthdayReminder() {
        try {
            List<Birthday> birthdayReminderList = new ArrayList<>();

            // Select all Query
            String selectQuery = "SELECT * FROM " + BIRTHDAY_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        Birthday birthday = new Birthday();

                        birthday.setPID(Integer.parseInt(cursor.getString(0)));
                        birthday.setbSurName(cursor.getString(3));
                        birthday.setBFirstName(cursor.getString(2));
                        birthday.setbPhoneNumber(cursor.getString(5));
                        birthday.setBEmail(cursor.getString(4));
                        birthdayReminderList.add(birthday);
                    } while (cursor.moveToNext());
                }
            }
            return birthdayReminderList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
            String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            return cursor.getCount();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public double getTotalSavingsForPackage(int packageID) {
        try {
            String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
            String[] whereArgs = new String[]{String.valueOf(packageID)};
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE PACKAGE_ID=?",String.valueOf(packageID)})) {

                if (cursor.moveToFirst()) {
                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public double getTotalSavingsForCustomer(int customerID) {
        try {
            Customer customer = new Customer();
            customerID=customer.getCusUID();
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE CUSTOMER_ID=?",String.valueOf(customerID)})){

                if (cursor.moveToFirst()) {

                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public long insertBills(int bill_ID,  long PROFILE_ID, long CUSTOMER_ID, long accountNo,String billName, double amount, String currency, Date billDate, String billCountry, String recurringType,  String billRef, String itemCode,String billerCode) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //double packageCount =customerDailyReport.getTotalSavingsForPackage(investment_ID)% 31;
            contentValues.put(String.valueOf(PROFILE_ID), PROFILE_ID);
            contentValues.put(String.valueOf(BILL_ID), bill_ID);
            contentValues.put(String.valueOf(CUSTOMER_ID), CUSTOMER_ID);
            contentValues.put(String.valueOf(ACCOUNT_NO), accountNo);
            contentValues.put(String.valueOf(BILL_NAME), billName);
            contentValues.put(BILL_AMOUNT, amount);
            contentValues.put(String.valueOf(BILL_CURRENCY), valueOf(currency));
            contentValues.put(String.valueOf(BILL_DATE), String.valueOf(billDate));
            contentValues.put(String.valueOf(BILL_COUNTRY), billCountry);
            contentValues.put(String.valueOf(BILL_RECURRING_TYPE), recurringType);
            contentValues.put(String.valueOf(BILL_REF), billRef);
            contentValues.put(String.valueOf(BILL_ITEM_CODE), itemCode);
            contentValues.put(BILLER_CODE, billerCode);
            contentValues.put(BILL_STATUS, "Being processed");
            sqLiteDatabase.insert(BILL_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return PROFILE_ID;
    }

    public long insertSavingsCode(PaymentCode paymentCode) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CODE_CUS_ID, paymentCode.getCustomer_ID());
            contentValues.put(CODE_REPORT_NO, paymentCode.getSavingsID());
            contentValues.put(CODE_PIN, paymentCode.getCode());
            contentValues.put(CODE_DATE, paymentCode.getCodeDate());
            contentValues.put(CODE_STATUS, paymentCode.getCodeStatus());
            sqLiteDatabase.insert(CODE_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }



    public int updateSavingsCode(int savingsID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            PaymentCode paymentCode= new PaymentCode();
            ContentValues values = new ContentValues();
            values.put(CODE_PIN, paymentCode.getCode());
            String selection = CODE_REPORT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(savingsID)};
            return db.update(CODE_TABLE, values, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public void updateSavingsCodeStatus(int savingsId,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            PaymentCode paymentCode = new PaymentCode();
            status = paymentCode.getCodeStatus();
            int codeID = paymentCode.getCodeID();
            contentValues.put(CODE_PIN, codeID);
            contentValues.put(CODE_STATUS, status);
            String selection = CODE_REPORT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(savingsId)};
            db.update(CODE_TABLE, contentValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public void deleteSavingsCode(int codeID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = CODE_PIN + "=?";
            String[] selectionArgs = new String[]{valueOf(codeID)};
            db.delete(CODE_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public int getEmergencyReportCount() {
        try {
            String countQuery = "SELECT * FROM " + EMERGENCY_REPORT_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();
            return cursor.getCount();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public long insertUserEmergencyReport(int reportID, int profileID, String reportTime, String type,String report,String address, String latlong) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(EMERGENCY_REPORT_PROF_ID, profileID);
            contentValues.put(EMERGENCY_LOCID, reportID);
            contentValues.put(EMERGENCY_LOCTIME, reportTime);
            contentValues.put(EMERGENCY_REPORT_TYPE, type);
            contentValues.put(EMERGENCY_REPORT, report);
            contentValues.put(EMERGENCY_REPORT_ADDRESS, address);
            contentValues.put(EMERGENCY_REPORT_LATLNG, latlong);
            sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return reportID;
    }
    public void deleteLocationReport(int reportID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = EMERGENCY_REPORT_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(reportID)};
            db.delete(EMERGENCY_REPORT_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void getLocReportFromCursor(ArrayList<EmergencyReport> reportArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int reportId = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            String reportTime = cursor.getString(2);
            String type = cursor.getString(3);
            double lat = cursor.getDouble(4);
            double lng = cursor.getDouble(5);
            String status = cursor.getString(6);

            reportArrayList.add(new EmergencyReport(reportId, profileID,reportTime, type, lat,lng, status));
        }


    }
    public ArrayList<EmergencyReport> getEmergencyReportFromProfile(int profileID) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERGENCY_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLocReportFromCursor(emergencyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return emergencyReports;
    }
    public ArrayList<EmergencyReport> getAllEmergencyReports() {
        try {
            ArrayList<EmergencyReport> reportArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getLocReportFromCursor(reportArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return reportArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    private void getPaymentCodeFromCursor(ArrayList<PaymentCode> codeArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int ownerProfileId = cursor.getInt(CODE_OWNER_COLUMN);
            String phoneNumber = cursor.getString(CODE_OWNER_PHONE_COLUMN);
            long code = cursor.getLong(CODE_PIN_COLUMN);
            String pinGenerationDate = cursor.getString(CODE_DATE_COLUMN);
            String status = cursor.getString(CODE_STATUS_COLUMN);
            String approver = cursor.getString(CODE_MANAGER_COLUMN);

            codeArrayList.add(new PaymentCode(ownerProfileId, phoneNumber, code, pinGenerationDate, status, approver));
        }


    }


    public ArrayList<PaymentCode> getCodesFromCurrentCustomer(int customerID) {
        ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(codeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return codeArrayList;
    }




    public ArrayList<PaymentCode> getSavingsCodeForDate(String date) {
        ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(codeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return codeArrayList;
    }
    public ArrayList<PaymentCode> getCodesFromCurrentSavings(int savingsID) {
        try {
            ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = CODE_REPORT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(savingsID)};

            Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getPaymentCodeFromCursor(codeArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();


            return codeArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<PaymentCode> getCodesFromCurrentTeller(String teller) {
        try {
            ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = CODE_MANAGER + "=?";
            String[] selectionArgs = new String[]{valueOf(teller)};

            Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getPaymentCodeFromCursor(codeArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();


            return codeArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<PaymentCode> getAllSavingsCodes() {
        try {
            ArrayList<PaymentCode> paymentCodeArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(CODE_TABLE, null, null, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getPaymentCodeFromCursor(paymentCodeArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();

            return paymentCodeArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int saveNewSavingsCode(PaymentCode paymentCode) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues codeValues = new ContentValues();
        Customer customer = new Customer();
        CustomerDailyReport customerDailyReport= new CustomerDailyReport();
        //customerDailyReport=paymentCode.getCustomerDailyReport();
        int codeID= paymentCode.getCodeID();
        codeValues.put(CODE_REPORT_NO, paymentCode.getRecordNo());
        codeValues.put(CODE_CUS_ID, valueOf(customer.getCusUID()));
        codeValues.put(CODE_ID, codeID);
        codeValues.put(CODE_PIN, paymentCode.getCode());
        codeValues.put(CODE_DATE, paymentCode.getCodeDate());
        codeValues.put(CODE_STATUS, paymentCode.getCodeStatus());
        codeValues.put(CODE_MANAGER, paymentCode.getCodeManager());
        db.insert(CODE_TABLE, null, codeValues);
        db.close();
        return codeID;
    }

    // Deleting single Reminder
    public void deleteReminder(ImportantDates reminder) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                    new String[]{String.valueOf(reminder.getUID())});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    protected boolean updateUserPassword(String password, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PASSWORD, password);
            String selection = PROF_ID_FOREIGN_KEY_PASSWORD + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};

            return sqLiteDatabase.update(PASSWORD_TABLE, contentValues, selection, selectionArgs) > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProfile2(int profileId, int customerId, String lastName, String firstName, String phoneNumber, String email, String dob, String gender, String address, String state, String nextOfKin, String dateJoined, String userName, String password, String profilePicture, String status, int roleId) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            Profile profile = new Profile();
            Customer customer = new Customer();
            customerId = customer.getCusUID();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_USERNAME, customerId);
            contentValues.put(PROFILE_PHONE, phoneNumber);
            contentValues.put(PROFILE_ADDRESS, address);
            contentValues.put(PROFILE_EMAIL, email);
            contentValues.put(PROFILE_PASSWORD, password);
            contentValues.put(PROFILE_FIRSTNAME, firstName);
            contentValues.put(PROFILE_SURNAME, lastName);
            contentValues.put(PROFILE_STATUS, status);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileId)};
            db.update(PROFILES_TABLE, contentValues, selection, selectionArgs);
            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProfile3(Profile profile,int id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            Customer customer = new Customer();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ID, profile.getPID());
            contentValues.put(PROFILE_SURNAME, profile.getProfileLastName());
            contentValues.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
            contentValues.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
            contentValues.put(PROFILE_EMAIL, profile.getProfileEmail());
            contentValues.put(PROFILE_GENDER, profile.getProfileGender());
            contentValues.put(PROFILE_ADDRESS, valueOf(profile.getProfileAddress()));
            contentValues.put(PROFILE_STATE, profile.getProfileState());
            contentValues.put(PROFILE_USERNAME, profile.getProfileUserName());
            contentValues.put(PROFILE_PASSWORD, profile.getProfilePassword());
            contentValues.put(PROFILE_CUS_ID_KEY, profile.getProfCusID());
            contentValues.put(PROFILE_STATUS, profile.getProfileStatus());
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            db.update(PROFILES_TABLE, contentValues, selection, selectionArgs);
            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


    public long insertAccount(Account account, int profileId, int customerId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String accountName = account.getAccountName();
        String accountBank = account.getBankName();
        int accountNumber = account.getSkyLightAcctNo();
        String accountBalance = valueOf(account.getAccountBalance());
        contentValues.put(ACCOUNT_PROF_ID, profileId);
        contentValues.put(ACCOUNT_NAME, accountName);
        contentValues.put(ACCOUNT_CUS_ID, customerId);
        contentValues.put(ACCOUNT_BANK, accountBank);
        contentValues.put(ACCOUNT_NO, accountNumber);
        contentValues.put(ACCOUNT_BALANCE, accountBalance);
        sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);

        return accountNumber;
    }



    public ArrayList<CustomerDailyReport> getTellerSavingsForToday(int profileID,String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public int getSavingsCountForProfileToday(int profileID,String today) {

        try {
            String countQuery = "SELECT  * FROM " + DAILY_REPORT_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = REPORT_PROF_ID_FK + "=? AND " + REPORT_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};

            String[] columns = {REPORT_PACK_ID_FK, REPORT_AMOUNT, REPORT_DATE,REPORT_NUMBER_OF_DAYS,REPORT_TOTAL};

            Cursor cursor = db.query(countQuery, columns, selection, selectionArgs, "", null, null);
            int count = cursor.getCount();
            cursor.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public int getSavingsCount(int packageNo) {

        try {
            String countQuery = "SELECT  * FROM " + DAILY_REPORT_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {REPORT_PACK_ID_FK, REPORT_AMOUNT, REPORT_DATE,REPORT_NUMBER_OF_DAYS,REPORT_AMOUNT_REMAINING,REPORT_DAYS_REMAINING};

            String selection = REPORT_PACK_ID_FK + "=?";
            String[] selectionArgs = new String[]{valueOf(packageNo)};

            Cursor cursor = db.query(countQuery, columns, selection, selectionArgs, "", PACKAGE_ID, REPORT_DATE);
            int count = cursor.getCount();
            cursor.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
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
        try (Cursor cursor = db.query(TABLE_REMINDERS, new String[]{KEY_ID, KEY_TITLE, KEY_DATE, KEY_TIME, KEY_REPEAT, KEY_REPEAT_NO, KEY_REPEAT_TYPE, KEY_ACTIVE}, KEY_ID + "=?",
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
        int reportNo = customerDailyReport.getNumberOfDays();
        long reportCode =customerDailyReport.getSavingsCode();
        packageSavingsValue.put(REPORT_PROF_ID_FK, profile.getPID());
        packageSavingsValue.put(REPORT_PACK_ID_FK, skyLightPackage.getPackageId());
        packageSavingsValue.put(REPORT_CUS_ID_FK, customer.getCusUID());
        packageSavingsValue.put(REPORT_ACCOUNT_NO_FK, account.getSkyLightAcctNo());
        packageSavingsValue.put(REPORT_AMOUNT, valueOf(customerDailyReport.getAmount()));
        packageSavingsValue.put(REPORT_NUMBER_OF_DAYS, customerDailyReport.getNumberOfDays());
        packageSavingsValue.put(REPORT_TOTAL, customerDailyReport.getTotal());
        packageSavingsValue.put(REPORT_DATE, customerDailyReport.getRecordDate());
        packageSavingsValue.put(REPORT_DAYS_REMAINING, customerDailyReport.getRemainingDays());
        packageSavingsValue.put(REPORT_AMOUNT_REMAINING, customerDailyReport.getAmountRemaining());
        packageSavingsValue.put(REPORT_STATUS, customerDailyReport.getStatus());
        packageSavingsValue.put(REPORT_CODE, reportCode);
        sqLiteDatabase.insert(DAILY_REPORT_TABLE, null, packageSavingsValue);

        sqLiteDatabase.close();
        return reportNo;
    }

    public boolean updateReportWithCode(int reportID, int customerID,long reportCode, String confirmed) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(REPORT_CODE, reportCode);
            contentValues.put(REPORT_STATUS, confirmed);
            String selection = REPORT_ID + "=? AND " + REPORT_CUS_ID_FK + "=?";
            String[] selectionArgs = new String[]{valueOf(reportID), valueOf(customerID)};
            db.update(DAILY_REPORT_TABLE, contentValues, selection, selectionArgs);
            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public void updateReportVoidWithCode(int reportID, int customerID,long reportCode,String confirmed) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(REPORT_STATUS, confirmed);
            contentValues.put(REPORT_CODE, reportCode);
            String selection = REPORT_ID + "=? AND " + REPORT_CUS_ID_FK + "=?";
            String[] selectionArgs = new String[]{valueOf(reportID), valueOf(customerID)};
            db.update(DAILY_REPORT_TABLE, contentValues, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getProfileName(String machine) {
        try {
            ArrayList<String> array_list = new ArrayList<String>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROFILE_ROLE + "=?";
            String[] selectionArgs = new String[]{machine};
            String[] colums = {PROFILE_FIRSTNAME, PROFILE_SURNAME};

            try (Cursor res = db.query(PROFILES_TABLE, colums, selection, selectionArgs, null,
                    null, null)) {
                res.moveToFirst();

                while (!res.isAfterLast()) {
                    array_list.add(res.getString(16));
                    res.moveToNext();
                }
            }
            return array_list;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public List<String> getAllUsers(){
        List<String> listUsers = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME};
        Cursor c=null;

        try {
            c = db.rawQuery("SELECT * FROM " + PROFILES_TABLE , columns);
            if(c == null) return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(1);
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return listUsers;
    }
    public String getCustomerNames(int cusID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = CUSTOMER_ID + "=?";
        String name=null;
        String[] selectionArgs = new String[]{valueOf(cusID)};
        Cursor cursor = db.query(CUSTOMER_TABLE, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    name=cursor.getColumnName(4);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return name;
    }



    public String getUserPassword(String phoneNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_PHONE + "=?";
        String password=null;
        String[] selectionArgs = new String[]{valueOf(phoneNo)};
        Cursor cursor = db.query(PROFILES_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    password=cursor.getColumnName(19);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return password;
    }
    public String getUserName(String phoneNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PROFILE_PHONE + "=?";
        String userName=null;
        String[] selectionArgs = new String[]{valueOf(phoneNo)};
        Cursor cursor = db.query(PROFILES_TABLE, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    userName=cursor.getColumnName(18);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return userName;
    }
    public long insertProfilePicture(int profileID, int customerID, Uri profilePicture) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFID_FOREIGN_KEY_PIX, profileID);
            contentValues.put(CUS_ID_PIX_KEY, customerID);
            contentValues.put(PICTURE_URI, valueOf(profilePicture));
            sqLiteDatabase.insert(PICTURE_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profileID;
    }


    public Bitmap getDocPicture(int savingsId) {

        try {
            Uri picturePath = getDocPicturePath(savingsId);
            if (picturePath == null )
                return (null);

            return (BitmapFactory.decodeFile(String.valueOf(picturePath)));

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Uri getDocPicturePath(int savingsId) {

        try {
            SQLiteDatabase db = getReadableDatabase();
            Uri picturePath;
            try (Cursor reportCursor = db.query(DOCUMENT_TABLE,
                    null,
                    DOCUMENT_REPORT_NO + "=?",
                    new String[]{String.valueOf(savingsId)},
                    null,
                    null,
                    null)) {
                reportCursor.moveToNext();
                int column_index = reportCursor.getColumnIndexOrThrow(DOCUMENT_URI);
                return Uri.parse(reportCursor.getString(column_index));

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        //return (picturePath);
        return null;
    }


    public long saveNewMessage(int messageId, int profileID, Message message, LocalTime today) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String messageDetails = message.getText();
            String messageTimeStamp = message.getTimestamp();
            String sender = message.getSender();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MESSAGE_PROF_ID, profileID);
            contentValues.put(MESSAGE_SENDER, sender);
            contentValues.put(MESSAGE_DETAILS, messageDetails);
            contentValues.put(MESSAGE_TIME, messageTimeStamp);
            sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);
            sqLiteDatabase.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return messageId;
    }

    public ArrayList<Message> getAllMessages2() {
        try {
            ArrayList<Message> messages = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(MESSAGE_TABLE, null, null, null, MESSAGE_TIME,
                    null, null);
            getMessagesFromCursor(messages, cursor);
            cursor.close();
            db.close();

            return messages;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    protected void getMessagesFromCursor(ArrayList<Message> messageArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = (cursor.getInt(MESSAGE_ID_COLUMN));
                String messageDetails = cursor.getString(MESSAGE_DETAILS_COLUMN);
                String views = cursor.getString(MESSAGE_VIEWED_COLUMN);
                String sender = cursor.getString(MESSAGE_SENDER_COLUMN);
                String time = cursor.getString(MESSAGE_TIME_COLUMN);

                messageArrayList.add(new Message());
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        // returns true if pointed to a record

    }

    public long insertBirthDay3(Birthday birthday1, String birthdayD) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            birthdayD = birthday1.getBirthDay();
            String surName =birthday1.getbSurName();
            String firstName =birthday1.getbFirstName();
            String phone =birthday1.getbPhoneNumber();
            String Status =birthday1.getbStatus();
            int birthdayId =birthday1.getBirthdayID();
            birthdayD=birthday1.getBirthDay();
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = mdformat.format(calendar.getTime());

            ContentValues contentValues = new ContentValues();
            contentValues.put(BIRTHDAY_ID, birthdayId);
            contentValues.put(B_PROF_ID, birthday1.getbProfileID());
            contentValues.put(BIRTHDAY_STATUS, Status);
            contentValues.put(B_SURNAME, surName);
            contentValues.put(B_FIRSTNAME, firstName);
            contentValues.put(B_PHONE, phone);
            contentValues.put(BIRTHDAY_DAYS_BTWN, birthday1.getDaysInBetween(currentDate,birthdayD));
            contentValues.put(BIRTHDAY_DAYS_REMAINING, birthday1.getFormattedDaysRemainingString(currentDate,birthdayD));
            sqLiteDatabase.insert(BIRTHDAY_TABLE, null, contentValues);
            sqLiteDatabase.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public ArrayList<Birthday> getBirthdayFromTodayDate(String myDate) {
        try {

            ArrayList<Birthday> birthdayArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = B_DOB + "=?";
            String[] selectionArgs = new String[]{myDate};
            Cursor cursor = db.query(BIRTHDAY_TABLE, null, selection, selectionArgs, null, null, null);

            getBirthdaysFromCursorAdmin(birthdayArrayList, cursor);

            cursor.close();
            db.close();

            return birthdayArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Birthday> getAllBirthDays() {
        try {
            ArrayList<Birthday> birthdays = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(BIRTHDAY_TABLE, null, null, null, null,
                    null, null);
            getBirthdaysFromCursorAdmin(birthdays, cursor);

            cursor.close();
            db.close();

            return birthdays;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private void getBirthdaysFromCursorAdmin(ArrayList<Birthday> birthdays, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int profileID = cursor.getInt(0);
                int birthdayID = cursor.getInt(1);
                String name = cursor.getString(3) + ","+ cursor.getString(2);
                String phoneNumber = cursor.getString(5);
                String email = cursor.getString(4);
                String address = null;
                String gender = null;
                String date = cursor.getString(6);
                int daysBTWN = cursor.getInt(8);
                String daysRemaining = cursor.getString(7);
                String status = cursor.getString(9);

                birthdays.add(new Birthday(profileID, birthdayID, name, phoneNumber, email, gender, address, date,daysBTWN,daysRemaining, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    private void getStandingOrdersFromCursor(ArrayList<StandingOrder> standingOrders, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int uID = cursor.getInt(0);
                int customerID = cursor.getInt(2);
                int so_Acct_No = cursor.getInt(9);
                double soDailyAmount = Double.parseDouble(cursor.getString(1));
                double expectedAmount = Double.parseDouble(cursor.getString(3));
                double amountDiff = Double.parseDouble(cursor.getString(6));
                double receivedAmount = Double.parseDouble(cursor.getString(4));
                String soStatus = cursor.getString(8);
                String so_start_date = cursor.getString(10);
                String so_end_date = cursor.getString(11);

                standingOrders.add(new StandingOrder(uID, so_Acct_No, soDailyAmount,so_start_date,expectedAmount, receivedAmount,amountDiff,soStatus,so_end_date));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<StandingOrder> getAllSOEndingToday(String soEndDate) {
        try {
            ArrayList<StandingOrder> orderArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = SO_END_DATE + "=?";
            String[] selectionArgs = new String[]{(soEndDate)};
            String[] column = {SO_ID, CUSTOMER_ID, SO_DAILY_AMOUNT,SO_RECEIVED_AMOUNT,SO_AMOUNT_DIFF,SO_ACCT_NO,SO_STATUS,SO_START_DATE,SO_END_DATE};

            Cursor cursor = db.query(STANDING_ORDER_TABLE, column, selection, selectionArgs, null, null, null);

            getStandingOrdersFromCursor(orderArrayList, cursor);
            cursor.close();
            db.close();

            return orderArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }



    public ArrayList<StandingOrder> getAllStandingOrders11() {
        try {
            ArrayList<StandingOrder> orderArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            //String selection = "SO_END_DATE=?";
            //String[] selectionArgs = {SO_ID, CUSTOMER_ID, SO_DAILY_AMOUNT,SO_RECEIVED_AMOUNT,SO_AMOUNT_DIFF,SO_ACCT_NO,SO_STATUS,SO_START_DATE,SO_END_DATE};

            Cursor cursor = db.query(STANDING_ORDER_TABLE, null, null, null, null,null , null);

            getStandingOrdersFromCursor(orderArrayList, cursor);
            cursor.close();
            db.close();

            return orderArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<StandingOrder> getAllStandingOrdersForCustomer(int customerID) {
        try {
            ArrayList<StandingOrder> standingOrders = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = customerID + "=?";
            String[] selectionArgs = new String[]{String.valueOf((customerID))};

            Cursor cursor = db.query(STANDING_ORDER_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getStandingOrdersFromCursor(standingOrders, cursor);

            cursor.close();
            db.close();

            return standingOrders;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public void updateStandingOrder(int uID, String soStatus, double receivedAmount, double amountDiff) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SO_ID, uID);
            contentValues.put(SO_RECEIVED_AMOUNT, receivedAmount);
            contentValues.put(SO_AMOUNT_DIFF, amountDiff);
            contentValues.put(SO_STATUS, soStatus);
            String selection = SO_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(uID)};
            db.update(STANDING_ORDER_TABLE, contentValues, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public long insertStandingOrder(StandingOrder standingOrder, int customerID) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Customer customer = new Customer();
            customerID=customer.getCusUID();
            int uID=standingOrder.getUID();
            long so_Acct_No=standingOrder.getSo_Acct_No();
            String so_start_date =standingOrder.getSoStartDate();
            String soStatus =standingOrder.getSoStatus();
            double expectedAmount =standingOrder.getExpectedAmount();
            double receivedAmount =standingOrder.getReceivedAmount();
            double amountDiff =standingOrder.getAmountDiff();
            double soDailyAmount =standingOrder.getSoDailyAmount();
            String so_end_date =standingOrder.getSoEndDate();

            ContentValues contentValues = new ContentValues();
            contentValues.put(SO_ID, uID);
            contentValues.put(SO_CUS_ID, customerID);
            contentValues.put(SO_DAILY_AMOUNT, soDailyAmount);
            contentValues.put(SO_EXPECTED_AMOUNT, expectedAmount);
            contentValues.put(SO_RECEIVED_AMOUNT, receivedAmount);
            contentValues.put(SO_AMOUNT_DIFF, amountDiff);
            contentValues.put(SO_ACCT_NO, so_Acct_No);
            contentValues.put(SO_STATUS, soStatus);
            contentValues.put(SO_START_DATE, so_start_date);
            contentValues.put(SO_END_DATE, so_end_date);
            sqLiteDatabase.insert(STANDING_ORDER_TABLE, null, contentValues);

            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public long insertStandingOrder(int profileID, int customerID, int soID, long soAcctNo,double amountCarriedForward, String currentDate, double expectedAmount, double sONowAmount, double amtDiff, int totalDays, int daysRemaining, String endDate, String inProgress) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            StandingOrderAcct standingOrderAcct = new StandingOrderAcct();
            contentValues.put(SO_PROF_ID, profileID);
            contentValues.put(SO_CUS_ID, customerID);
            contentValues.put(SO_ID, soID);
            contentValues.put(SO_DAILY_AMOUNT, amountCarriedForward);
            contentValues.put(SO_EXPECTED_AMOUNT, expectedAmount);
            contentValues.put(SO_RECEIVED_AMOUNT, sONowAmount);
            contentValues.put(SO_AMOUNT_DIFF, amtDiff);
            contentValues.put(SO_ACCT_NO, soAcctNo);
            contentValues.put(SO_STATUS, inProgress);
            contentValues.put(SO_START_DATE, currentDate);
            contentValues.put(SO_END_DATE, endDate);
            sqLiteDatabase.insert(STANDING_ORDER_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return soID;
    }
    public long insertStandingOrderAcct(int profileID,int customerID ,int soAcctID, String soAcctName, Double soAcctBalance) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SO_PROF_ID, profileID);
            contentValues.put(SO_CUS_ID, customerID);
            contentValues.put(SO_ACCOUNT_NO, soAcctID);
            contentValues.put(SO_ACCOUNT_NAME, soAcctName);
            contentValues.put(SO_ACCOUNT_BALANCE, soAcctBalance);
            sqLiteDatabase.insert(SO_ACCT_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return soAcctID;
    }
    public boolean checkBirthdayExist(String birthday) {
        int count=0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = B_DOB + "=?";
            String[] selectionArgs = new String[]{String.valueOf((birthday))};

            Cursor cursor = db.query(BIRTHDAY_TABLE, null, selection, selectionArgs, null, null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getCount();
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }



    public ArrayList<Customer> getCustomersFromProfileWithDate(int profileID,String date) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {CUSTOMER_SURNAME,CUSTOMER_FIRST_NAME,CUSTOMER_OFFICE};
            String selection = CUSTOMER_PROF_ID + "=? AND " + CUSTOMER_DATE_JOINED + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
            Cursor cursor = db.query(CUSTOMER_TABLE, columns, selection, selectionArgs, null, null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getCustomerFromCursor(customers, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            db.close();
            return customers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getCustomersToday(String todayDate) {
        Customer customer = new Customer();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf.format(calendar.getTime().getDate());
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {CUSTOMER_SURNAME,CUSTOMER_FIRST_NAME,CUSTOMER_OFFICE};
            String selection = CUSTOMER_DATE_JOINED + "=?";
            String[] selectionArgs = new String[]{todayDate};
            Cursor cursor = db.query(CUSTOMER_TABLE, columns, selection, selectionArgs, null, null, null);
            getCustomerFromCursor(customers, cursor);
            cursor.close();
            db.close();
            return customers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getCustomerDailyReportForCustomer(int customerID) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<CustomerDailyReport> getCustomerDailyReportForCustomerToday(int customerID,String todayDate) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getPackageSavings(int packageID) {

        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = REPORT_PACK_ID_FK + "=?";
            String[] selectionArgs = new String[]{valueOf(packageID)};
            return sqLiteDatabase.rawQuery(
                    "SELECT * FROM " + DAILY_REPORT_TABLE + " WHERE " +  selection,
                    selectionArgs
            );


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getPackageLoans(int packageID) {

        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();

            String selection = LOAN_PACK_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(packageID)};
            return sqLiteDatabase.rawQuery(
                    "SELECT * FROM " + LOAN_TABLE + " WHERE " +  selection,
                    selectionArgs
            );


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int getNewPackageCountForTellerToday(int tellerID,String today) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getAllPackageCount() {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getNewPackageCountToday(String today) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
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
                        skyLightPackage.setPackageId(Integer.parseInt(cursor.getString(0)));
                        skyLightPackage.setCustomerName(cursor.getString(1));
                        skyLightPackage.setGrandTotal(Double.valueOf(cursor.getString(2)));
                        skyLightPackage.setAmount(Double.parseDouble(cursor.getString(3)));
                        skyLightPackage.setBalance(Double.parseDouble(cursor.getString(4)));
                        skyLightPackage.setDateStarted(cursor.getString(5));
                        skyLightPackage.setDateEnded(cursor.getString(6));
                        skyLightPackage.setPackageDuration(Integer.parseInt(cursor.getString(7)));
                        skyLightPackage.setPackageStatus(cursor.getString(8));
                        skyLightPackage.setCustomerId(Integer.parseInt(cursor.getString(9)));


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


    /*public long insertNewPackage2(long profileId, long customerId, String lastName, String firstName, String phoneNumber, String email, String dob, String gender, String address, String state, String nextOfKin, String dateJoined, String userName, String password, Uri profilePicture, int roleId, int packageId,String packageName, String packageType, int duration, double amount, String date, String startDate, long numberOfDays, double total, double ledgerBalance, String endDate, double grandTotal, int count, long remainingDays, String status) {
        try {
            //long id = insertPackage(profileId, customerId, packageId, packageType, valueOf(duration), amount, date, startDate, numberOfDays, total, "", "", endDate, count, 0, status);
            insertDailyReport(profileId, customerId, date, amount, numberOfDays, total, ledgerBalance, grandTotal, endDate, remainingDays, Long.parseLong(valueOf(count)), status, packageId);
            Account account = new Account();
            Profile profile = new Profile();
            Customer customer = new Customer();
            Birthday birthday = new Birthday();
            double amountContributedSoFar = account.getAccountBalance();
            //double amountRemaining = grandTotal - amountContributedSoFar;
            insertCustomer11(profileId, customerId, lastName, firstName, phoneNumber, email, dob, gender, address, state, nextOfKin, dateJoined, userName, password, profilePicture, "Customer");
            insertAccount(account, profileId, customerId);
            insertBirthDay3( birthday,dob);

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues packageValues = new ContentValues();
            SkyLightPackage skyLightPackage = new SkyLightPackage();
            CustomerDailyReport customerDailyReport = new CustomerDailyReport();

            packageValues.put(PROFILE_ID, profileId);
            packageValues.put(PACKAGE_ID, packageId);
            packageValues.put(CUSTOMER_ID, customerId);
            packageValues.put(PACKAGE_TYPE, packageType);
            packageValues.put(PACKAGE_VALUE, valueOf(skyLightPackage.getAmount()));
            packageValues.put(PACKAGE_END_DATE, skyLightPackage.getDateEnded());
            packageValues.put(PACKAGE_DURATION, valueOf(skyLightPackage.getPackageDuration()));
            packageValues.put(PACKAGE_BALANCE, skyLightPackage.getBalance());
            packageValues.put(PACKAGE_EXPECTED_VALUE, skyLightPackage.getPackageStatus());
            packageValues.put(PACKAGE_START_DATE, skyLightPackage.getDateStarted());
            packageValues.put(REPORT_DATE, skyLightPackage.getDate());
            packageValues.put(REPORT_NUMBER_OF_DAYS, customerDailyReport.getNumberOfDays());
            packageValues.put(REPORT_TOTAL, customerDailyReport.getTotal());
            packageValues.put(REPORT_STATUS, customerDailyReport.getStatus());
            packageValues.put("ReportCount", "");
            sqLiteDatabase.insert(PACKAGE_TABLE, null, packageValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profileId;
    }

    public long insertDailyReportForPackage(long packageID, CustomerDailyReport customerDailyReport) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues packageSavingsValue = new ContentValues();
            SkyLightPackage skyLightPackage=customerDailyReport.getSkyLightPackage();
            Profile profile=customerDailyReport.getProfile();
            //int count = skyLightPackage.getCount(packageID) % 31;
            int packNoOfDays = skyLightPackage.getNumberOfDays();
            int reportNoOfDays = customerDailyReport.getNumberOfDays();
            String reportCode =customerDailyReport.getSavingsCode();
            //count++;
            packageSavingsValue.put(PROFILE_ID, profile.getPID());
            packageSavingsValue.put(PACKAGE_ID, packageID);
            packageSavingsValue.put(CUSTOMER_ID, customerDailyReport.getCustomerId());
            packageSavingsValue.put(REPORT_NUMBER_OF_DAYS, customerDailyReport.getNumberOfDays());
            packageSavingsValue.put(REPORT_TOTAL, customerDailyReport.getTotal());
            packageSavingsValue.put(REPORT_DATE, customerDailyReport.getRecordDate());
            packageSavingsValue.put(REPORT_DAYS_REMAINING, customerDailyReport.getRemainingDays());
            packageSavingsValue.put(REPORT_AMOUNT_REMAINING, customerDailyReport.getAmountRemaining());
            packageSavingsValue.put(PACKAGE_STATUS, skyLightPackage.getPackageStatus());
            packageSavingsValue.put(REPORT_STATUS, customerDailyReport.getStatus());
            packageSavingsValue.put(REPORT_CODE, reportCode);
            sqLiteDatabase.insert(DAILY_REPORT_TABLE, null, packageSavingsValue);

            /*if (count == 1) {
                packageSavingsValue.put(REPORT_STATUS, "Just starting");
                packageSavingsValue.put(REPORT_DAYS_REMAINING, 30);
            } else if (count == 5) {
                packageSavingsValue.put(REPORT_DAYS_REMAINING, 26);
                packageSavingsValue.put(REPORT_STATUS, "in progress");
            } else if (count == 10) {
                packageSavingsValue.put(REPORT_DAYS_REMAINING, 21);
                packageSavingsValue.put(REPORT_STATUS, "in progress");
            } else if (count == 20) {
                packageSavingsValue.put(REPORT_STATUS, "in progress");
                packageSavingsValue.put(REPORT_DAYS_REMAINING, 11);
            } else if (count == 25) {
                packageSavingsValue.put(REPORT_STATUS, "in progress");
                packageSavingsValue.put(REPORT_DAYS_REMAINING, 6);
            } else if (count == 31) {
                packageSavingsValue.put(REPORT_STATUS, "Package completed");
                packageSavingsValue.put(REPORT_DAYS_REMAINING, 0);

            }
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return packageID;
    }*/

    public void updatePackageRecord(int profileId, int customerId, int packageId,int reportId, String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues packageValues = new ContentValues();
            String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_CUSTOMER_ID_FOREIGN + "=?AND " + PACKAGE_ID + "=?AND " + PACKAGE_REPORT_ID +"=?";
            String[] selectionArgs = new String[]{valueOf(profileId), valueOf(customerId),valueOf(packageId),valueOf(reportId)};
            packageValues.put(REPORT_STATUS, status);
            db.update(DAILY_REPORT_TABLE, packageValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updatePackageForCollection(int profileId, int customerId, int packageId, String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues packageValues = new ContentValues();
            String selection = PACKAGE_PROFILE_ID_FOREIGN + "=? AND " + PACKAGE_CUSTOMER_ID_FOREIGN + "=?AND " + PACKAGE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileId), valueOf(customerId),valueOf(packageId)};
            packageValues.put(PACKAGE_STATUS, status);
            db.update(PACKAGE_TABLE, packageValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


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
        cv.put(PACKAGE_REPORT_ID, customerDailyReport.getPackageId());
        cv.put(PACKAGE_BALANCE, valueOf(skyLightPackage.getBalance()));
        cv.put(PACKAGE_STATUS, skyLightPackage.getStatus());
        cv.put(PACKAGE_END_DATE, skyLightPackage.getDateEnded());
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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }
    private Cursor getPackagesFromCursorCustomer(int customerID, ArrayList<SkyLightPackage> packages) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getCusPackageEndingToday(int customerID, String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private Cursor getPackagesFromCursor(ArrayList<SkyLightPackage> packages,Cursor cursor) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<SkyLightPackage> getPackageByType(String type) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getPackageByTypeAndEndDate(String type,String date) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getPackageByTypeAndStartDate(String type,String date) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getTellerPackagesEndingToday(int profileID,String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getTellerPackagesEnding2DAYS(int profileID,String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getTellerPackagesEnding3DAYS(int profileID,String date) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getCustomerPackageEndingToday(int customerID,String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getCustomerSavingsCompletePackage(int customerID,String savings,String completed) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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


    public ArrayList<SkyLightPackage> getCustomerCompletePack(int customerId, String completed) {
        try {
            ArrayList<SkyLightPackage> packages = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String incomplete = null;
            //inProgress=incomplete;

            String selection = PACKAGE_CUSTOMER_ID_FOREIGN + "=? AND " + PACKAGE_STATUS + "=?";
            String[] selectionArgs = new String[]{valueOf(customerId), valueOf(completed)};

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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<SkyLightPackage> getCustomerItemPurchaseCompletePackage(int customerID,String itemPurchase,String completed) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SkyLightPackage> getPackagesSubscribedToday(String todayDate) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = sdf.format(calendar.getTime().getDate());
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public long insertNewPackage(Profile profile, Customer customer, SkyLightPackage skyLightPackage) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Calendar calendar = null;

            ContentValues packageValues = new ContentValues();
            packageValues.put(PACKAGE_PROFILE_ID_FOREIGN, profile.getPID());
            packageValues.put(PACKAGE_CUSTOMER_ID_FOREIGN, customer.getCusUID());
            packageValues.put(PACKAGE_ID, skyLightPackage.getPackageId());
            packageValues.put(PACKAGE_NAME, skyLightPackage.getPackageName());
            packageValues.put(PACKAGE_TYPE, skyLightPackage.getType());
            packageValues.put(PACKAGE_VALUE, skyLightPackage.getAmount());
            packageValues.put(PACKAGE_DURATION, skyLightPackage.getPackageDuration());
            packageValues.put(PACKAGE_START_DATE, skyLightPackage.getDateStarted());
            packageValues.put(PACKAGE_END_DATE, skyLightPackage.getDateEnded());
            packageValues.put(PACKAGE_BALANCE, skyLightPackage.getBalance());
            packageValues.put(REPORT_DAYS_REMAINING, skyLightPackage.getRemainingDays());
            packageValues.put(PACKAGE_STATUS, skyLightPackage.getStatus());
            sqLiteDatabase.insert(PACKAGE_TABLE, null, packageValues);

            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
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
        try {
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
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return reportID;
    }
    public long insertPackage(int profileId, int customerId, int packageId,String packageName, String packageType, String duration, double amount, String date, String startDate, double numberOfDays, double total, String ledgerBalance, String grandTotal, String endDate, int count, int remainingDays, String status) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return packageId;
    }

    public void updatePackage(SkyLightPackage skyLightPackage) {
        try {
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
            values.put(PACKAGE_BALANCE, skyLightPackage.getBalance());
            values.put(PACKAGE_END_DATE, skyLightPackage.getDateEnded());
            values.put(PACKAGE_STATUS, skyLightPackage.getPackageStatus());
            //values.put(PACKAGE_DURATION, skyLightPackage.getPackageDuration());
            values.put("ReportCount", "");
            long result = db.update(PACKAGE_TABLE, values,
                    PACKAGE_ID,
                    new String[]{valueOf(skyLightPackage.getUID())});
            Log.d("Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }





    public ArrayList<SkyLightPackage> getCustomerPromoCompletePackage(int customerID,String promo,String completed) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    protected int insertSavingsReport(int i, Profile profile, SkyLightPackage skyLightPackage, CustomerDailyReport customerDailyReport, Customer customer) {
        return 0;
    }


    public int getCustomerTotalSavingsCount(int customerID) {
        int count = 0;
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getSavingsDocCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String countQuery = "SELECT  * FROM " + DOCUMENT_TABLE;
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


        }catch (SQLException e)
        {
            e.printStackTrace();
        }



        return 0;



    }
    public int getPackagesCountAdmin() {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public int getTotalSavingsCountAdmin() {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    public int getSavingsCountTeller(int profileID) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public void updateReportPicture(int userId, Bitmap picture) {
        try {
            String picturePath = "";
            File internalStorage= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/skylightImage.jpg/");
            try {
                FileInputStream fis = new FileInputStream(internalStorage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File reportFilePath = new File(internalStorage, userId + ".png");
            picturePath = reportFilePath.toString();

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(reportFilePath);
                picture.compress(Bitmap.CompressFormat.PNG, 100 /*quality*/, fos);
                fos.close();
            }
            catch (Exception ex) {
                Log.i("SKYLIGHT DATABASE", "Problem updating picture", ex);
                picturePath = "";
            }

            SQLiteDatabase db = getWritableDatabase();
            String selection = PROFID_FOREIGN_KEY_PIX + "=?";
            String[] selectionArgs = new String[]{valueOf(userId)};

            ContentValues newPictureValue = new ContentValues();
            newPictureValue.put(PICTURE_URI,
                    picturePath);

            db.update(PICTURE_TABLE,
                    newPictureValue,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public PaymentDoc getDocumentFromSavings(int reportNo) {
        try {
            PaymentDoc paymentDoc = new PaymentDoc();
            Uri docUri = paymentDoc.getDocumentLink();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = DOCUMENT_REPORT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(reportNo)};
            try (Cursor cursor = db.query(DOCUMENT_TABLE, new String[]{DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_STATUS, REPORT_ID,
                            DOCUMENT_URI}, selection, selectionArgs, null, null, null, null)) {
                if (cursor != null)
                    cursor.moveToFirst();

                return paymentDoc;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    public ArrayList<CustomerDailyReport> getIncompleteSavingsForTellerToday(int profileID, String todayDate,String unPaid) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<CustomerDailyReport> getCompletedSavingsForCustomer(int customerID, String paid) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getAllIncompleteSavings(String unPaid) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getIncompleteSavingsForTeller(int profileID,String unPaid) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getCustomerDailyReportFromCurrentProfile(int profileID, String todayDate) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<CustomerDailyReport> getCustomerDailyReportToday(String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<CustomerDailyReport> getCustomerDailyReportFromCurrentCustomer(int customerID, String todayDate) {
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getAllCustomerDailyReports(int customerID) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getDailyReportForToday(String todayDate) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public Birthday getBirthdayReminder(String birthdayReceivedID) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Birthday birthday = null;
            String selection = BIRTHDAY_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(birthdayReceivedID)};

            try (Cursor cursor = db.query(BIRTHDAY_TABLE, new String[]{B_PROF_ID,BIRTHDAY_ID, B_DOB, BIRTHDAY_STATUS, B_SURNAME,
                                    B_FIRSTNAME,
                                    B_PHONE,
                                    B_EMAIL
                            }, selection, selectionArgs, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                if (cursor != null) {
                    birthday = new Birthday(cursor.getInt(0),cursor.getInt(1),
                            cursor.getString(2), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getInt(8));
                }
            }

            return birthday;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }





    public long insertPassword(String password, int profileID) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        password = PasswordHelpers.passwordHash(password);

        contentValues.put(PROF_ID_FOREIGN_KEY_PASSWORD, profileID);
        contentValues.put(PASSWORD, password);
        return sqLiteDatabase.insert(PASSWORD_TABLE, null, contentValues);


    }

    public long saveNewAccount1(String accountBank, String accountName, int accountNumber, double accountBalance, AccountTypes accountTypes) {


        SQLiteDatabase db = this.getWritableDatabase();
        Profile profile = new Profile();

        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_PROF_ID, profile.getPID());
        cv.put(ACCOUNT_BANK, accountBank);
        cv.put(ACCOUNT_NO, accountNumber);
        cv.put(ACCOUNT_NAME, accountName);
        cv.put(ACCOUNT_TYPE_NAME, String.valueOf(accountTypes));
        cv.put(ACCOUNT_BALANCE, accountBalance);
        db.insert(ACCOUNTS_TABLE, null, cv);

        //account.setId(id);

        //db.close();


        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/

        return accountNumber;
    }

    public long saveNewPayee(Profile profile, Payee payee) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(PAYEE_PROF_ID, profile.getPID());
            cv.put(PAYEE_ID, payee.getPayeeID());
            cv.put(PAYEE_NAME, payee.getPayeeName());
            db.insert(PAYEES_TABLE, null, cv);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTransactionCountForBranchAtDate(String branch,String date) {
        int count = 0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = TRANSACTION_OFFICE_BRANCH + "=? AND " + TRANSACTION_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(branch), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getSavingsCountForBranchAtDate(String branch,String date) {
        int count = 0;

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public double getTotalSavingsForBranchAtDate(String branchName, String theDate) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public double getTotalTransactionForBranchAtDate(String branchName, String theDate) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = TRANSACTION_OFFICE_BRANCH + "=? AND " + TRANSACTION_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(branchName), valueOf(theDate)};

            Cursor cursor = db.rawQuery(
                    "select sum ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getTransactionCountForCustomer(int customerID,String date) {
        int count = 0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = CUSTOMER_ID + "=? AND " + TRANSACTION_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID), valueOf(date)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getTotalTransactionForCustomer(int customerID, String theDate) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            double count = 0.00;
            String selection = CUSTOMER_ID + "=? AND " + TRANSACTION_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID), valueOf(theDate)};
            Cursor cursor = db.rawQuery(
                    "select sum ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection,
                    selectionArgs
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getDouble(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public ArrayList<Transaction> getAllTranxWithTypeForCustomer(int customerID,String typeOfTranX) {
        try {
            ArrayList<Transaction> transactionArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = CUSTOMER_ID + "=? AND " + TRANSACTIONS_TYPE + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID), valueOf(typeOfTranX)};

            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursor(transactionArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return transactionArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Transaction> getTransactionsForBranchAtDate(String officeBranch, String givenDate) {
        try {
            ArrayList<Transaction> transactionArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {TRANSACTION_ID, TRANSACTIONS_TYPE,TRANSACTION_AMOUNT,TRANSACTION_PAYEE,TRANSACTION_DATE,TRANSACTION_STATUS};

            String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(givenDate)};
            String selection = TRANSACTION_OFFICE_BRANCH + "=? AND " + TRANSACTION_DATE + "=?";

            Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection, selectionArgs, null, null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursor(transactionArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();


            return transactionArrayList;

        }catch (IllegalStateException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public long saveNewTransaction(int profileID, int customerId,Transaction transaction, long accountId,String payee,String payer,Transaction.TRANSACTION_TYPE type, double amount, long transactionId,  String officeBranch,String date) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
            ContentValues cv = new ContentValues();
            cv.put(TRANSACTION_PROF_ID, profileID);
            cv.put(TRANSACTION_CUS_ID, customerId);
            cv.put(TRANSACTION_ACCT_ID, accountId);
            cv.put(TRANSACTION_AMOUNT, amount);
            cv.put(TRANSACTION_ID, transactionId);
            cv.put(TRANSACTIONS_TYPE, String.valueOf(type));
            cv.put(TRANSACTION_PAYEE, payee);
            cv.put(TRANSACTION_PAYER, payer);
            cv.put(TRANSACTION_OFFICE_BRANCH, officeBranch);
            cv.put(TIMESTAMP, date);

            if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.MANUAL_WITHDRAWAL) {
                cv.put(TRANSACTION_SENDING_ACCT, "Skylight");
                cv.put(TRANSACTION_DEST_ACCT, transaction.getDestinationAccount());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.LOAN) {
                cv.put(TRANSACTION_SENDING_ACCT, "Skylight");
                cv.put(TRANSACTION_DEST_ACCT, transaction.getDestinationAccount());
                cv.put(TRANSACTION_PAYEE, transaction.getPayee());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.GROUP_SAVINGS_WITHDRAWAL) {
                cv.put(TRANSACTION_SENDING_ACCT, transaction.getSendingAccount());
                cv.put(TRANSACTION_DEST_ACCT, transaction.getDestinationAccount());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.STANDING_ORDER) {
                cv.put(TRANSACTION_PAYEE, transaction.getPayee());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.GROUP_SAVINGS_DEPOSIT) {
                cv.put(TRANSACTION_PAYEE, transaction.getPayee());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
                //cv.putNull(SENDING_ACCOUNT);
                //cv.putNull(DESTINATION_ACCOUNT);
                cv.put(TRANSACTION_PAYEE, transaction.getPayee());
            } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                cv.put(TRANSACTION_DEST_ACCT, transaction.getDestinationAccount());
                //cv.putNull(SENDING_ACCOUNT);
                //cv.putNull(DESTINATION_ACCOUNT);
                //cv.putNull(TRANSACTION_PAYEE);
            }
            cv.put(TRANSACTION_AMOUNT, transaction.getAmount());
            cv.put(TRANS_TYPE, transaction.getTransactionType().toString());

            db.insert(TRANSACTIONS_TABLE, null, cv);

            //db.close();

        }catch (IllegalStateException e)
        {
            e.printStackTrace();
        }

        return profileID;
    }
    public long savePaymentTransaction(String response) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TRANSACTION_STATUS, response);
            db.insert(TRANSACTIONS_TABLE, null, cv);

            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public long savePaymentTransactionP(int profileID4, int customerID, Transaction.TRANSACTION_TYPE type, String response) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            Customer customer = new Customer();
            Profile profile = new Profile();
            Transaction transaction = new Transaction();
            cv.put(TRANSACTION_PROF_ID, profile.getPID());
            cv.put(TRANSACTION_CUS_ID, customer.getCusUID());
            cv.put(TRANSACTIONS_TYPE, String.valueOf(transaction.getTransactionType()));
            cv.put(TRANSACTION_STATUS, response);
            db.insert(TRANSACTIONS_TABLE, null, cv);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profileID4;
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



    public long insertCustomer11(int profileID, int customerID, String lastName, String firstName, String phoneNumber, String email, String dob, String gender, String address, String state, String nextOfKin, String dateJoined, String userName, String password, Uri profilePicture, String machine) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //insertProfilePicture(profileID, customerID, profilePicture);
        ContentValues contentValues = new ContentValues();
        password = PasswordHelpers.passwordHash(password);

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
    public Cursor getCustomersFromPackageCursor(ArrayList<Customer> customers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int customerID = cursor.getInt((0));
                String surname = cursor.getString(3);
                String firstName = cursor.getString(4);
                String phoneNumber = cursor.getString(5);
                String emailAddress = cursor.getString(6);
                String nin = cursor.getString(7);
                String dob = cursor.getString(8);
                String gender = cursor.getString(9);
                String address = cursor.getString(10);
                String username = cursor.getString(11);
                String password = cursor.getString(12);
                String office = cursor.getString(13);
                String joinedDate = cursor.getString(14);
                String status = cursor.getString(15);
                String names = surname +","+firstName;





                customers.add(new Customer(customerID, names, phoneNumber, emailAddress, address, office));

            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }
    public ArrayList<Customer> getCusForItemsPurchase(String items) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                    + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                    + " WHERE " + PACKAGE_TYPE + " = " +  items;
            Cursor cursor = db.rawQuery(rawQuery,
                    null);
            getCustomersFromPackageCursor(customers, cursor);
            cursor.close();
            db.close();

            return customers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getCusForSavings(String savings) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                    + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                    + " WHERE " + PACKAGE_TYPE + " = " +  savings;
            Cursor cursor = db.rawQuery(rawQuery,
                    null);
            getCustomersFromPackageCursor(customers, cursor);
            cursor.close();
            db.close();

            return customers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getCusWithoutPackage() {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                    + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                    + " WHERE " + PACKAGE_TYPE + " = ?" +  "";

            Cursor cursor = db.rawQuery(rawQuery,
                    null);
            getCustomersFromPackageCursor(customers, cursor);
            cursor.close();
            db.close();

            return customers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getCusForInvestment(String investment) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                    + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                    + " WHERE " + PACKAGE_TYPE + " = " +  investment;
            Cursor cursor = db.rawQuery(rawQuery,
                    null);
            getCustomersFromPackageCursor(customers, cursor);
            cursor.close();
            db.close();

            return customers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getCusForPromo(String promo) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String rawQuery = "SELECT * FROM " + PACKAGE_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                    + " ON " + PACKAGE_CUSTOMER_ID_FOREIGN + " = " + CUSTOMER_ID
                    + " WHERE " + PACKAGE_TYPE + " = " +  promo;
            Cursor cursor = db.rawQuery(rawQuery,
                    null);
            getCustomersFromPackageCursor(customers, cursor);
            cursor.close();
            db.close();

            return customers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getAllCusForSo() {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String rawQuery = "SELECT * FROM " + STANDING_ORDER_TABLE + " INNER JOIN " + CUSTOMER_TABLE
                    + " ON " + SO_CUS_ID + " = " + CUSTOMER_ID;
            Cursor cursor = db.rawQuery(rawQuery,
                    null);
            getCustomerFromCursor(customers, cursor);
            cursor.close();
            db.close();

            return customers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public void blockCustomer(int cusID, String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROFILE_ROLE, status);
            String selection = PROFILE_CUS_ID_KEY + "=?";
            String[] selectionArgs = new String[]{valueOf(cusID)};
            db.update(PROFILES_TABLE, values, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public ArrayList<Message> getMessagesToday(String todayDate) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf.format(calendar.getTime().getDate());
        try {
            ArrayList<Message> messages = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {MESSAGE_SENDER, MESSAGE_PURPOSE,MESSAGE_DETAILS,MESSAGE_TIME};
            String selection = MESSAGE_TIME + "=?";
            String[] selectionArgs = new String[]{valueOf(todayDate)};
            Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, selectionArgs, null, null, null);
            getMessagesFromCursor(messages, cursor);
            cursor.close();
            //db.close();

            return messages;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        cv.put(PROFILE_OFFICE, profile.getProfileOffice());
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
    public Profile getProfileFromUserNameAndPassword(String userName,String password) {
        //long rv = -1;
        Profile profile = null;
        Cursor csr = null;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {
            String selection = PROFILE_USERNAME + "=? AND " + PROFILE_PASSWORD + "=?";
            String[] selectionArgs = new String[]{userName, password};
            String[] columns = {PROFILE_ID, PROFILE_FIRSTNAME, PROFILE_OFFICE,PROFILE_PHONE,PROFILE_ROLE,PROFILE_SURNAME};
            csr = sqLiteDatabase.query(PROFILES_TABLE,columns,selection,selectionArgs,null,null,null);
            if (csr.moveToFirst()) {
                profile = new Profile();

                profile.setPID(csr.getInt(0));
                profile.setProfileEmail(csr.getString(2));
                profile.setProfileOffice(csr.getString(6));
                profile.setProfileFirstName(csr.getString(7));
                profile.setProfileLastName(csr.getString(8));
                profile.setProfileRole(csr.getString(15));
                profile.setProfileDateJoined(csr.getString(16));

            }
            return profile;

        }
        finally{
            if (csr != null) {
                sqLiteDatabase.close();
                csr.close();
                close();
            }
        }

    }
    public long insertAccount(int profileID2, int customerID1, String skylightMFb, String accountName, long accountNumber, double accountBalance, AccountTypes accountTypeStr) {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_PROF_ID, profileID2);
        contentValues.put(ACCOUNT_CUS_ID, customerID1);
        contentValues.put(ACCOUNT_NAME, accountName);
        contentValues.put(ACCOUNT_NO, accountNumber);
        contentValues.put(ACCOUNT_BANK, skylightMFb);
        contentValues.put(BANK_ACCT_NO, "");
        contentValues.put(ACCOUNT_BALANCE, accountBalance);
        contentValues.put(ACCOUNT_TYPE_NAME, String.valueOf(accountTypeStr));
        sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);
        //profile.setDbId(accountNumber);

        //sqLiteDatabase.close();


        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        return accountNumber;
    }
    public long insertNewMessage(int messageID, int profileID, int customerID, int adminID, String adminName, String purposeOfMessage, String message, String sender, String sendee, String time) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGE_PROF_ID, profileID);
        contentValues.put(MESSAGE_ID, messageID);
        contentValues.put(MESSAGE_CUS_ID, customerID);
        contentValues.put(MESSAGE_ADMIN_ID, adminID);
        contentValues.put(MESSAGE_ADMIN_NAME, adminName);
        contentValues.put(MESSAGE_PURPOSE, purposeOfMessage);
        contentValues.put(MESSAGE_DETAILS, message);
        contentValues.put(MESSAGE_SENDER, sender);
        contentValues.put(MESSAGE_SENDEE, sendee);
        contentValues.put(MESSAGE_TIME, time);
        sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);
        sqLiteDatabase.close();



        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        return profileID;
    }

    public void deleteSavings(int savingsID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String selection = REPORT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(savingsID)};

            Cursor cursor = db.query(DAILY_REPORT_TABLE, null,  selection, selectionArgs, null, null, null);

            db.delete(DAILY_REPORT_TABLE,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }






    public long saveNewLoan(int profile_ID, int cus_ID, int loan_ID,double loanInterest, double loanAmt, String loanDate, int acctNo, String typeFromAcct, long loanCode,String loanStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(LOAN_PROF_ID, profile_ID);
            cv.put(LOAN_CUS_ID, cus_ID);
            cv.put(LOAN_ID, loan_ID);
            cv.put(LOAN_ACCT_NO, acctNo);
            cv.put(LOAN_AMOUNT, loanAmt);
            cv.put(LOAN_TYPE, typeFromAcct);
            cv.put(LOAN_INTEREST, loanInterest);
            cv.put(LOAN_CODE, loanCode);
            cv.put(LOAN_DATE, loanDate);
            cv.put(LOAN_STATUS, loanStatus);
            db.insert(LOAN_TABLE, null, cv);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public void updateSOAcctBalance(int soAcctID,double newBalance) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = SO_ACCT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(soAcctID)};
            ContentValues documentUpdateValues = new ContentValues();
            documentUpdateValues.put(SO_ACCOUNT_BALANCE, newBalance);
            db.update(SO_ACCT_TABLE, documentUpdateValues, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public long insertNewLoan(int profile_ID, int cus_ID, int loan_ID,double loanInterest, double loanAmt, String loanDate, int acctNo, String typeFromAcct, long loanCode,String loanStatus) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(LOAN_PROF_ID, profile_ID);
            cv.put(LOAN_CUS_ID, cus_ID);
            cv.put(LOAN_ID, loan_ID);
            cv.put(LOAN_ACCT_NO, acctNo);
            cv.put(LOAN_AMOUNT, loanAmt);
            cv.put(LOAN_TYPE, typeFromAcct);
            cv.put(LOAN_INTEREST, loanInterest);
            cv.put(LOAN_CODE, loanCode);
            cv.put(LOAN_DATE, loanDate);
            cv.put(LOAN_STATUS, loanStatus);
            db.insert(LOAN_TABLE, null, cv);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return loan_ID;
    }

    public long insertLoan13(int profileID, String address,String dob,int cusID, Loan loan) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(LOAN_PROF_ID, profileID);
            cv.put(LOAN_CUS_ID, cusID);
            cv.put(LOAN_ID, loan.getLoanId());
            cv.put(LOAN_AMOUNT, dob);
            cv.put(LOAN_INTEREST, address);
            cv.put(LOAN_FIXED_PAYMENT, valueOf(loan.getFixedPayment()));
            cv.put(LOAN_TOTAL_INTEREST, valueOf(loan.getTotalInterests()));
            cv.put(LOAN_DOWN_PAYMENT, valueOf(loan.getDownPayment()));
            cv.put(LOAN_DATE, loan.getDate());
            cv.put(LOAN_START_DATE, loan.getStartDate());
            cv.put(LOAN_END_DATE, loan.getEndDate());
            cv.put(LOAN_STATUS, loan.getStatus());
            sqLiteDatabase.insert(LOAN_TABLE, null, cv);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    public long insertInterestRate10() {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            LoanInterest interest = new LoanInterest();
            long interestId = interest.getLoanInterestId();
            double interestRate = interest.getLoanInterestId();
            contentValues.put(INTEREST_ID, interestId);
            contentValues.put(INTEREST_RATE, interestRate);
            sqLiteDatabase.insert(INTEREST_TABLE, null, contentValues);
            sqLiteDatabase.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<Customer> getCustomersFromCurrentProfile(int profileID) {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(CUSTOMER_TABLE, null,  selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getCustomerFromCursor(customerArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        cursor.close();

        return customerArrayList;
    }


    public ArrayList<TimeLine> getTimeLinesFromCurrentProfile(int profileID) {
        try {
            ArrayList<TimeLine> timeLines = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TIMELINE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(TIMELINE_TABLE, null,  selection, selectionArgs, null, null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTimeLinesFromCursorAdmin(timeLines, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return timeLines;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Customer> getAllCustomers11() {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getCustomerFromCursor(customers, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return customers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    private void getCustomerFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String lastName = cursor.getString(3);
                String firstName = cursor.getString(4);
                String phoneNumber = cursor.getString(5);
                String email = cursor.getString(6);
                String nin = cursor.getString(7);
                String dob = cursor.getString(8);
                String gender = cursor.getString(9);
                String address = cursor.getString(10);
                String username = cursor.getString(11);
                String password = cursor.getString(12);
                String office = cursor.getString(13);
                String joinedDate = cursor.getString(14);
                String status = cursor.getString(15);

                customers.add(new Customer(id, firstName, lastName, phoneNumber, email, address, nin,gender, dob, username, password, office, joinedDate));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }




    public ArrayList<StandingOrder> getAllStandingOrdersWithStatus(String completedStatus) {
        try {
            ArrayList<StandingOrder> standingOrders = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {SO_ID, SO_ACCOUNT_NAME, SO_START_DATE,SO_DAILY_AMOUNT,SO_EXPECTED_AMOUNT,SO_AMOUNT_DIFF,SO_ACCOUNT_BALANCE,SO_END_DATE};


            String selection = SO_STATUS + "=?";
            String[] selectionArgs = new String[]{valueOf(completedStatus)};

            Cursor cursor = db.query(SO_ACCT_TABLE, columns,  selection, selectionArgs, null, null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getStandingOrdersFromCursor(standingOrders, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return standingOrders;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }





    public long saveNewDocument1(PaymentDoc paymentDoc) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues documentValues = new ContentValues();
            documentValues.put(DOCUMENT_ID, valueOf(paymentDoc.getDocumentId()));
            documentValues.put(DOCUMENT_REPORT_NO, valueOf(paymentDoc.getRecordNo()));
            documentValues.put(DOCUMENT_CUS_ID, valueOf(paymentDoc.getDocCusID()));
            documentValues.put(DOCUMENT_TITLE, valueOf(paymentDoc.getTittle()));
            documentValues.put(DOCUMENT_URI, valueOf(paymentDoc.getDocumentLink()));
            documentValues.put(DOCUMENT_STATUS, valueOf(paymentDoc.getStatus()));
            db.insert(DOCUMENT_TABLE, null, documentValues);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public void updateDocumentStatus(int documentId,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues documentUpdateValues = new ContentValues();
            documentUpdateValues.put(DOCUMENT_STATUS, status);
            String selection = DOCUMENT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(documentId)};
            db.update(DOCUMENT_TABLE, documentUpdateValues, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<PaymentDoc> getDocumentsFromCurrentProfile1(int profileID) {
        try {
            ArrayList<PaymentDoc> documentArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_REPORT_NO,DOCUMENT_CUS_ID,DOCUMENT_STATUS};

            String selection = DOCUMENT_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(DOCUMENT_TABLE, columns,  selection, selectionArgs, null, null, null);


            getDocumentsFromCursor(documentArrayList, cursor);
            cursor.close();
            //db.close();

            return documentArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<PaymentDoc> getDocumentsFromCurrentCustomer(int customerID) {
        try {
            ArrayList<PaymentDoc> documentArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_REPORT_NO,DOCUMENT_STATUS};

            String selection = DOCUMENT_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            Cursor cursor = db.query(DOCUMENT_TABLE, columns,  selection, selectionArgs, null, null, null);

            getDocumentsFromCursor(documentArrayList, cursor);
            cursor.close();
            //db.close();

            return documentArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public PaymentDoc getDocumentsFromSavings(int savingsNo) {
        PaymentDoc paymentDoc = new PaymentDoc();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {DOCUMENT_ID, DOCUMENT_TITLE, DOCUMENT_REPORT_NO,DOCUMENT_CUS_ID,DOCUMENT_STATUS};
        String selection = DOCUMENT_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(savingsNo)};

        Cursor cursor = db.query(DOCUMENT_TABLE, columns,  selection, selectionArgs, null, null, null);
        getDocFromCursor(paymentDoc, cursor);
        cursor.close();
        return paymentDoc;
    }



    private void getDocumentsFromCursor(ArrayList<PaymentDoc> paymentDocs, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = (cursor.getInt(1));
                int report = cursor.getInt(2);
                String title = cursor.getString(3);
                Uri documentLink = Uri.parse(cursor.getString(5));
                String status = cursor.getString(6);
                int customerId = cursor.getInt(8);

                paymentDocs.add(new PaymentDoc(id, title, customerId, report, documentLink,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void getDocFromCursor(PaymentDoc paymentDoc, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = (cursor.getInt(1));
                int report = cursor.getInt(2);
                String title = cursor.getString(3);
                Uri documentLink = Uri.parse(cursor.getString(5));
                String status = cursor.getString(6);
                int customerId = cursor.getInt(8);
                paymentDoc =new PaymentDoc(id, title, customerId, report, documentLink,status);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        //return paymentDocument;
    }

    public ArrayList<PaymentDoc> getAllDocuments() {

        try {
            ArrayList<PaymentDoc> paymentDocs = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(DOCUMENT_TABLE, null, null, null, null,
                    null, null);
            getDocumentsFromCursor(paymentDocs, cursor);
            cursor.close();
            //db.close();

            return paymentDocs;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getProfileAcctFromCursor(ArrayList<Profile> profileArrayList , Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int profileID = (cursor.getInt(1));
                String surname = cursor.getString(4);
                String firstName = cursor.getString(5);
                String dateJoined = cursor.getString(6);
                String status = cursor.getString(7);
                Uri pix = Uri.parse(cursor.getString(3));
                profileArrayList.add(new Profile(profileID, surname,firstName,dateJoined,status, pix));

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        //return paymentDocument;
    }

    public ArrayList<Profile> getAllGrpAcctProfiles(long GRPA_ID) {

        try {
            ArrayList<Profile> profileArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(GRP_PROFILE_TABLE, null, GRPA_ID + "=?",
                    new String[]{String.valueOf(GRPA_ID)}, null, null,
                    null, null);
            getProfileAcctFromCursor(profileArrayList, cursor);
            cursor.close();
            //db.close();

            return profileArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public long insertGroupAccountProfile(long grpAccountNo, long grpAccountProfileNo,long profileID, String phoneNo,String dateJoined) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GRPP_ID, grpAccountProfileNo);
            contentValues.put(GRPA_ID, grpAccountNo);
            contentValues.put(PROFILE_ID, profileID);
            contentValues.put(GRPP_JOINED_DATE, dateJoined);
            contentValues.put(GRPP_JOINED_STATUS, "New");
            sqLiteDatabase.insert(GRP_PROFILE_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return grpAccountNo;
    }
    public void deletePaymentDoc(int docID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = DOCUMENT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(docID)};


            db.delete(DOCUMENT_TABLE,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public Uri getDocumentFromSavings9(int reportNo) {
        try {

            Uri docBitmap = null;
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = DOCUMENT_REPORT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(reportNo)};

            Cursor cursor = sqLiteDatabase.query(DOCUMENT_TABLE, new String[]{DOCUMENT_URI}, selection, selectionArgs, null, null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    docBitmap= Uri.parse(cursor.getColumnName(5));

                    cursor.close();
                }

            sqLiteDatabase.close();


            return docBitmap;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }





    public Uri getDocumentBitMap(int reportNo) {
        try {

            Uri docBitmap = null;
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = DOCUMENT_REPORT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(reportNo)};

            Cursor cursor = sqLiteDatabase.query(DOCUMENT_TABLE, new String[]{DOCUMENT_URI}, selection, selectionArgs, null, null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    docBitmap= Uri.parse(cursor.getColumnName(5));

                    cursor.close();
                }

            sqLiteDatabase.close();


            return docBitmap;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void getDailyReportsFromCursor(ArrayList<CustomerDailyReport> customerDailyReports, Cursor cursor) {

        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }




    public ArrayList<CustomerDailyReport> getPackageSavings1(int packageID) {
        try {
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

            //return sqLiteDatabase.rawQuery("SELECT * FROM DAILY_REPORT_TABLE WHERE PACKAGE_ID = ?",
            //new String[]{valueOf(packageID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<CustomerDailyReport> getPackageSavingsForCustomer(int customerID) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private void getReportsFromCursorAdmin(ArrayList<CustomerDailyReport> reports, Cursor cursor) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        while (cursor.moveToNext()) {


        }
    }


    protected long insertUserAccount(int profID, int accountId,int cusID,String bank,double skylightBalance,double bankBalance,String bankAcctNo) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_PROF_ID, profID);
            contentValues.put(ACCOUNT_CUS_ID, cusID);
            contentValues.put(ACCOUNT_NAME, accountId);
            contentValues.put(ACCOUNT_BANK, bank);
            contentValues.put(BANK_ACCT_NO, bankAcctNo);
            contentValues.put(BANK_ACCT_BALANCE, bankBalance);
            contentValues.put(ACCOUNT_NO, accountId);
            contentValues.put(ACCOUNT_BALANCE, skylightBalance);
            sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }





    public void updateInterestRate(int id) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public boolean checkUserExist(String strPhoneNumber) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME, PROFILE_PHONE};

            String selection = PROFILE_PHONE + "=?";
            String[] selectionArgs = new String[]{valueOf(strPhoneNumber)};

            Cursor cursor = db.query(PROFILES_TABLE, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();

            cursor.close();
            //close();

            if (count > 0) {
                return true;
            } else {
                return false;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public BigInteger getSavingsSumValue(){
        try {
            int sum=0;
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(" + REPORT_TOTAL + ") as Total FROM " + DAILY_REPORT_TABLE, null)) {
                if (cursor.moveToFirst())
                    sum = cursor.getColumnIndex(REPORT_TOTAL);
                //cursor.moveToFirst();
                //if(REPORT_TOTAL !=null)

                return BigInteger.valueOf(sum);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    public BigInteger getLoanSumValueAdmin(){
        try {
            int sum=0;
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(" + LOAN_AMOUNT + ") as Total FROM " + LOAN_TABLE, null)) {
                if (cursor.moveToFirst())
                    //cursor.moveToFirst();
                    sum = cursor.getColumnIndex(REPORT_TOTAL);
                return BigInteger.valueOf(sum);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    @SuppressLint("Recycle")
    public BigInteger getLoanSumValue() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int sum=0;
            //ArrayList<String> array_list = new ArrayList<String>();

            Cursor cursor = db.rawQuery("SELECT SUM(" + LOAN_AMOUNT + ") as Total FROM " + LOAN_TABLE, null);

            while (cursor.moveToNext()) {
                sum = cursor.getColumnIndex(LOAN_AMOUNT);
                return BigInteger.valueOf(sum);
            }
            return null;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
    public BigInteger getLoansSumValueForCustomer(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        //Cursor cur = db.rawQuery("SELECT SUM(" + (REPORT_TOTAL) + ") FROM " + DAILY_REPORT_TABLE, null);

        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ LOAN_AMOUNT +")) FROM " + LOAN_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(LOAN_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }
    public BigInteger getSOReceivedValue(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME, PROFILE_PHONE};

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_RECEIVED_AMOUNT +")) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(SO_RECEIVED_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }
    public BigInteger getSOExpectedValue(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_EXPECTED_AMOUNT +")) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(SO_EXPECTED_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }
    public BigInteger getSoValue(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_DAILY_AMOUNT +")) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs);
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


    public void deleteCustomer(String customerPhoneNumber) {
        try {
            boolean result = false;

            String selection = "customerPhoneNumber = \"" + customerPhoneNumber + "\"";

            int rowsDeleted = myCR.delete(UserContentProvider.CONTENT_URI,
                    selection, null);

            if (rowsDeleted > 0)
                result = true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public void deleteUSer(int id) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public void updateUser1(Profile profile,Customer customer) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



    public void overwriteUser(int PROFILE_ID,Profile profile) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(PROFILE_SURNAME, profile.getProfileLastName());
            cv.put(PROFILE_FIRSTNAME, profile.getProfileFirstName());
            cv.put(PROFILE_PHONE, profile.getProfilePhoneNumber());
            cv.put(PROFILE_EMAIL, profile.getProfileEmail());
            cv.put(PROFILE_DOB, valueOf(profile.getProfileDob()));
            cv.put(PROFILE_ADDRESS, valueOf(profile.getProfileAddress()));
            cv.put(PROFILE_OFFICE, profile.getProfileOffice());
            cv.put(PROFILE_NEXT_OF_KIN, profile.getNextOfKin());
            cv.put(PROFILE_USERNAME, profile.getProfileUserName());
            cv.put(PASSWORD, profile.getProfilePassword());
            cv.put(PROFILE_ROLE, profile.getProfileRole());
            cv.put(PROFILE_STATUS, profile.getProfileStatus());
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(PROFILE_ID)};


            db.update(PROFILES_TABLE, cv, selection,selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void overwriteCustomer1(int customerID,int profileID, Customer customer) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CUSTOMER_SURNAME, customer.getCusSurname());
            cv.put(CUSTOMER_FIRST_NAME, customer.getCusFirstName());
            cv.put(CUSTOMER_PHONE_NUMBER, customer.getCusPhoneNumber());
            cv.put(CUSTOMER_EMAIL_ADDRESS, customer.getCusEmail());
            cv.put(CUSTOMER_ADDRESS, valueOf(customer.getCusAddress()));
            cv.put(CUSTOMER_OFFICE, customer.getCusOfficeBranch());
            cv.put(CUSTOMER_USER_NAME, customer.getCusUserName());
            cv.put(CUSTOMER_PASSWORD, customer.getCusPassword());
            String selection = CUSTOMER_ID + "=? AND " + CUSTOMER_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID), valueOf(profileID)};
            db.update(CUSTOMER_TABLE, cv, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void UpdateUserStatus(String status, int profileID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            Profile profile = new Profile();
            profileID = profile.getPID();
            cVals.put(PROFILE_STATUS, status);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            long result = db.update(PROFILES_TABLE, cVals, selection, selectionArgs);
            Log.d("Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

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

    public void UpdateAccountBalanceStatus(String balance, int accountID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            cVals.put(ACCOUNT_BALANCE, balance);
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(accountID)};
            long result = db.update(ACCOUNTS_TABLE, cVals, selection, selectionArgs);
            Log.d("Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void UpdateLoanBalance(String amount, int loanID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            cVals.put(LOAN_BALANCE, amount);
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(loanID)};
            long result = db.update(LOAN_TABLE, cVals, selection, selectionArgs);
            Log.d("Update Result:", "=" + result);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



    public void overwriteLoan1(Profile profile, Customer customer, Loan loan) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(LOAN_PROF_ID, profile.getPID());
            cv.put(LOAN_CUS_ID, customer.getCusUID());
            cv.put(LOAN_AMOUNT, valueOf(profile.getProfileDob()));
            cv.put(LOAN_INTEREST, valueOf(profile.getProfileAddress()));
            cv.put(LOAN_FIXED_PAYMENT, valueOf(loan.getFixedPayment()));
            cv.put(LOAN_TOTAL_INTEREST, valueOf(loan.getTotalInterests()));
            cv.put(LOAN_DOWN_PAYMENT, valueOf(loan.getDownPayment()));
            cv.put(LOAN_DISPOSABLE_COM, valueOf(loan.getDisposableCommission()));
            cv.put(LOAN_BALANCE, valueOf(loan.getResiduePayment()));
            cv.put(LOAN_DATE, loan.getDate());
            cv.put(LOAN_START_DATE, loan.getStartDate());
            cv.put(LOAN_END_DATE, loan.getEndDate());
            cv.put(LOAN_STATUS, loan.getStatus());
            String selection = LOAN_CUS_ID + "=? AND " + LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customer.getCusUID()), valueOf(loan.getLoanId())};

            db.update(LOAN_TABLE, cv, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public void delete_Loan_byID(int LOAN_ID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(LOAN_ID)};

            db.delete(LOAN_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public void delete_Customer_byID(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};

            db.delete(CUSTOMER_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void delete_User_byID(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};

            db.delete(PROFILE_ID, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<Message> getMessagesForBranchToday(String officeBranch,String todayDate) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {CUSTOMER_ID,MESSAGE_PURPOSE, MESSAGE_DETAILS,MESSAGE_SENDER,MESSAGE_SENDEE,MESSAGE_TIME};
            String selection = MESSAGE_BRANCH_OFFICE + "=? AND " + MESSAGE_TIME + "=?";
            String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(todayDate)};

            Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, selectionArgs, null, null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getMessagesFromCursor(messageArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            //db.close();

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Message> getAllMessagesForBranch(String officeBranch) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {CUSTOMER_ID,MESSAGE_PURPOSE, MESSAGE_DETAILS,MESSAGE_SENDER,MESSAGE_SENDEE,MESSAGE_TIME};
            String selection = MESSAGE_BRANCH_OFFICE + "=?";
            String[] selectionArgs = new String[]{valueOf(officeBranch)};

            Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, selectionArgs, null, null, null);

            getMessagesFromCursor(messageArrayList, cursor);

            cursor.close();
            //db.close();

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Message> getMessagesFromCurrentProfile(int profileID) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MESSAGE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(MESSAGE_TABLE, null,  selection, selectionArgs, null,
                    null, null);

            getMessagesFromCursor(messageArrayList, cursor);

            cursor.close();
            //db.close();

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Message> getMessagesFromCurrentCustomer(int customerID) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MESSAGE_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            Cursor cursor = db.query(MESSAGE_TABLE, null,  selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getMessagesFromCursor(messageArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public long insertMessage(int profileID, int customerID ,int messageID , String message, String sender,String sendee,String officeBranch, String messageTime) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MESSAGE_PROF_ID, profileID);
            contentValues.put(MESSAGE_CUS_ID, customerID);
            contentValues.put(MESSAGE_DETAILS, message);
            contentValues.put(MESSAGE_ID, messageID);
            contentValues.put(MESSAGE_SENDER, sender);
            contentValues.put(MESSAGE_SENDEE, sendee);
            contentValues.put(MESSAGE_BRANCH_OFFICE, officeBranch);
            contentValues.put(MESSAGE_TIME, messageTime);
            sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageID;
    }
    public long insertMessageCustomer(int profID, int cusID, int messageID, String purpose,String message, String sender,String sendee,String office, String meassageDate) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Message message1 = new Message();
            contentValues.put(MESSAGE_PROF_ID, profID);
            contentValues.put(MESSAGE_CUS_ID, cusID);
            contentValues.put(MESSAGE_ID, messageID);
            contentValues.put(MESSAGE_PURPOSE, purpose);
            contentValues.put(MESSAGE_DETAILS, message);
            contentValues.put(MESSAGE_SENDER, sender);
            contentValues.put(MESSAGE_SENDEE, sendee);
            contentValues.put(MESSAGE_BRANCH_OFFICE, office);
            contentValues.put(MESSAGE_TIME, meassageDate);
            sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageID;
    }

    public Cursor getAllMessagesUser(int profileID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = MESSAGE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            return sqLiteDatabase.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                    null, null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getAllCustomerMessages(int profileID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = MESSAGE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        return sqLiteDatabase.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
    }

    public String getSpecificMessage(int messageId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String result=null;
        String selection = MESSAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(messageId)};

        Cursor cursor = sqLiteDatabase.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(1);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        sqLiteDatabase.close();

        return result;
    }
    public String getMessage() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {MESSAGE_ID,MESSAGE_DETAILS};
        Cursor cursor =sqLiteDatabase.query(MESSAGE_TABLE,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(1);
            String name =cursor.getString(6);
            buffer.append(cid+ "   " + name );
        }
        return buffer.toString();
    }


    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MESSAGE_TABLE, null, null, null, MESSAGE_SENDER,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getMessagesFromCursorAdmin(messageArrayList, cursor);
                cursor.close();
            }

        db.close();

        return messageArrayList;
    }

    private void getMessagesFromCursorAdmin(ArrayList<Message> messages, Cursor cursor) {
        while (cursor.moveToNext()) {
            try {
                int profileID = cursor.getInt(0);
                int customerID = cursor.getInt(2);
                int messageID = cursor.getInt(1);
                String purpose = cursor.getString(5);
                String messageDetails = cursor.getString(6);
                String messageSender = cursor.getString(7);
                String messageSendee = cursor.getString(8);
                String messageTime = cursor.getString(9);
                messages.add(new Message(profileID,customerID,messageID,purpose,messageDetails,messageSender,messageSendee,messageTime));

            }catch (SQLException e)
            {
                e.printStackTrace();
            }


        }
    }



    public ArrayList<Message> getMessagesForCurrentProfile(int profileID) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MESSAGE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getMessagesFromCursorAdmin(messageArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Message> getMessagesForCurrentCustomer(int customerID) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MESSAGE_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            Cursor cursor = db.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getMessagesFromCursorAdmin(messageArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public void deleteDocument(int docID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = DOCUMENT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(docID)};

            int count =sqLiteDatabase.delete(DOCUMENT_TABLE ,selection,selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



    public ArrayList<Transaction> getAllTransactionCustomer(int customerID) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TRANSACTION_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursorAdmin(transactions, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private void getTransactionsFromCursorGrp(ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String type = cursor.getString(3);
                double amount = cursor.getDouble(4);
                String endDate = cursor.getString(5);
                String methodOfPayment = cursor.getString(6);
                int sendingAcct = cursor.getInt(7);
                int receivingAcct = cursor.getInt(8);
                String status = cursor.getString(9);
                transactions.add(new Transaction(id,type,amount,endDate,methodOfPayment,sendingAcct,receivingAcct,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        // returns true if pointed to a record

    }
    public ArrayList<Transaction> getAllGrpAcctTranxs(long grpAcctID) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(GRP_TRANX_TABLE, null,  GRPA_ID
                            + " = " + grpAcctID, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTransactionsFromCursorGrp(transactions, cursor);
                    cursor.close();
                }

            db.close();

            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<Transaction> getAllTransactionProfile(int profileID) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TRANSACTION_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            Cursor cursor = db.query(TRANSACTIONS_TABLE, null,  selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursorAdmin(transactions, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<Transaction> getAllTransactionAdmin() {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursorAdmin(transactions, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    private void getTransactionsFromCursorAdmin(ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String date = cursor.getString(4);
                int sendingAcct = cursor.getInt(5);
                int destination = cursor.getInt(6);
                String payee = cursor.getString(7);
                String payer = cursor.getString(8);
                double amount = cursor.getDouble(9);
                String type = cursor.getString(10);
                String method = cursor.getString(15);
                String status = cursor.getString(15);
                transactions.add(new Transaction(id,type,method,date,sendingAcct,destination,amount,payee,payer,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public long insertTimeLineNew(int timelineID, int profileID,int cusID, String details, String date) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIMELINE_PROF_ID, profileID);
            contentValues.put(TIMELINE_CUS_ID, cusID);
            contentValues.put(TIMELINE_ID, timelineID);
            contentValues.put(TIMELINE_DETAILS, details);
            contentValues.put(TIMELINE_TIME, date);
            sqLiteDatabase.insert(TIMELINE_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public long insertTimeLine(Profile profile, Customer customer, TimeLine timeLine, Transaction transaction) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIMELINE_PROF_ID, profile.getPID());
            contentValues.put(TIMELINE_CUS_ID, customer.getCusUID());
            contentValues.put(TIMELINE_ID, timeLine.getTimelineID());
            contentValues.put(TIMELINE_DETAILS, timeLine.getTimelineDetails());
            contentValues.put(TIMELINE_TIME, timeLine.getTimestamp());
            contentValues.put(TIMELINE_AMOUNT, timeLine.getAmount());
            contentValues.put(TIMELINE_SENDING_ACCOUNT, transaction.getSendingAccount());
            contentValues.put(TIMELINE_GETTING_ACCOUNT, transaction.getDestinationAccount());
            contentValues.put(TIMELINE_WORKER_NAME, profile.getProfileLastName() + "," + profile.getProfileFirstName());
            contentValues.put(TIMELINE_CLIENT_NAME, customer.getCusSurname() + "," + customer.getCusFirstName());
            sqLiteDatabase.insert(TIMELINE_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public ArrayList<TimeLine> getAllTimeLinesCustomer(int customerID) {
        try {
            ArrayList<TimeLine> timeLines = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TIMELINE_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.query(TIMELINE_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTimeLinesFromCursorAdmin(timeLines, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            return timeLines;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<TimeLine> getAllTimeLinesProfile(int profileID) {
        try {
            ArrayList<TimeLine> timeLines = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TIMELINE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.query(TIMELINE_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTimeLinesFromCursorAdmin(timeLines, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return timeLines;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<TimeLine> getAllTimeLinesAdmin() {
        try {
            ArrayList<TimeLine> timeLines = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TIMELINE_TABLE, null, null, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTimeLinesFromCursorAdmin(timeLines, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return timeLines;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public long insertTimeLine(String tittle, String timelineDetails, String timeLineTime, Location mCurrentLocation) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMELINE_ID, tittle);
        contentValues.put(TIMELINE_DETAILS, timelineDetails);
        contentValues.put(TIMELINE_LOCATION, String.valueOf(mCurrentLocation));
        contentValues.put(TIMELINE_TIME, timeLineTime);
        sqLiteDatabase.insert(TIMELINE_TABLE, null, contentValues);
        sqLiteDatabase.close();

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        return 0;
    }

    private void getTimeLinesFromCursorAdmin(ArrayList<TimeLine> timeLines, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String tittle = cursor.getString(3);
                String details = cursor.getString(4);

                timeLines.add(new TimeLine(id,tittle, details));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    protected Cursor getCusAllLoans(int userId) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = LOAN_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(userId)};
            Cursor cursor = sqLiteDatabase.query(LOAN_TABLE, null,  selection, selectionArgs, null,
                    null, null);


            return sqLiteDatabase.rawQuery("SELECT * FROM LOAN_TABLE WHERE CUSTOMER_ID = ?",
                    new String[]{valueOf(userId)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public void updateProfilePix(int profileID, int customerID, Uri photoUri) {


    }
    public long getLoanCode(int loanID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor=null;
            long loanCode=0;
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(loanID)};
            cursor = sqLiteDatabase.query(LOAN_TABLE, null,  selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        loanCode=cursor.getColumnIndex(LOAN_CODE);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return loanCode;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return loanID;
    }

    public ArrayList<Loan> getAllLoansCustomer(int customerID) {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoanFromCursorAdmin(loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loanArrayList;
    }
    public ArrayList<Loan> getAllLoansProfile(int profileID) {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoanFromCursorAdmin(loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return loanArrayList;

    }
    public ArrayList<Loan> getAllLoansAdmin() {
        ArrayList<Loan> loanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(LOAN_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoanFromCursorAdmin(loanArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return loanArrayList;
    }

    private void getLoanFromCursorAdmin(ArrayList<Loan> loans, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int loanID = cursor.getInt(0);
                int customerID = cursor.getInt(1);
                int profileID = cursor.getInt(2);
                double loanAmount = cursor.getLong(5);
                double loanBalance = cursor.getLong(13);
                String loanDate = cursor.getString(14);
                String startDate = cursor.getString(15);
                String endDate = cursor.getString(16);
                String status = cursor.getString(17);
                loans.add(new Loan(profileID, customerID, loanID,loanAmount,loanDate, startDate, endDate, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public ArrayList<Profile> getAllProfileUsers() {
        try {
            ArrayList<Profile> userModelArrayList = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + PROFILES_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
            //db.close();
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Profile profile = new Profile();
                    Customer customer = new Customer();
                    profile.setPID(Integer.parseInt(c.getString(0)));
                    profile.setProfileLastName(c.getString(1));
                    profile.setProfileFirstName(c.getString(2));
                    profile.setProfilePhoneNumber(c.getString(3));
                    profile.setProfileEmail(c.getString(4));
                    profile.setProfileRole(c.getString(16));
                    try {
                        profile.setProfileUserName(c.getString(17));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    profile.setProfilePassword(c.getString(18));

                    userModelArrayList.add(profile);
                } while (c.moveToNext());
            }
            return userModelArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Customer> getAllCustomerUsers() {
        try {
            ArrayList<Customer> userModelArrayList = new ArrayList<>();

            String selectQuery = "SELECT  * FROM " + CUSTOMER_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
            //db.close();
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Customer customer = new Customer();
                    customer.setCusUID(Integer.parseInt(c.getString(0)));
                    customer.setCusSurname(c.getString(3));
                    customer.setCusFirstName(c.getString(4));
                    customer.setCusPhoneNumber(c.getString(5));
                    customer.setCusEmail(c.getString(6));
                    customer.setCusDateJoined(c.getString(14));
                    customer.setCusUserName(c.getString(11));
                    customer.setCusPin(c.getString(12));
                    customer.setCusOffice(c.getString(13));
                    customer.setCusGender(c.getString(9));

                    userModelArrayList.add(customer);
                } while (c.moveToNext());
            }
            return userModelArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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

    public Cursor getCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int customerID = cursor.getInt(0);
                String surname = cursor.getString(3);
                String firstName = cursor.getString(4);
                String phoneNumber = cursor.getString(5);
                String emailAddress = cursor.getString(6);
                String nin = cursor.getString(7);
                String dob = cursor.getString(8);
                String gender = cursor.getString(9);
                String address = cursor.getString(10);
                String userName = cursor.getString(11);
                String password = cursor.getString(12);
                String office = cursor.getString(13);
                String dateJoined = cursor.getString(14);

                customers.add(new Customer(customerID, surname, firstName, phoneNumber, emailAddress,nin, dob, gender, address, userName, password, office, dateJoined));

            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }
    public Cursor getSpinnerTellersFromCursor(ArrayList<CustomerManager> customerManagerArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int tellerID = cursor.getInt(0);
                String surname = cursor.getString(2);
                String firstName = cursor.getString(3);
                customerManagerArrayList.add(new CustomerManager(tellerID, surname, firstName));

            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<CustomerManager> getAllTellersSpinner() {
        ArrayList<CustomerManager> customerManagers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TELLER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSpinnerTellersFromCursor(customerManagers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customerManagers;

    }


    public Cursor getSpinnerAdminFromCursor(ArrayList<AdminUser> adminUserArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int adminID = cursor.getInt(0);
                String surname = cursor.getString(2);
                String firstName = cursor.getString(3);
                adminUserArrayList.add(new AdminUser(adminID, surname, firstName));

            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<AdminUser> getAllAdminSpinner() {
        try {
            ArrayList<AdminUser> adminUserArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query(ADMIN_TABLE, null, null, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSpinnerAdminFromCursor(adminUserArrayList, cursor);
                    cursor.close();
                }

            db.close();


            return adminUserArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<CustomerManager> getAllTellerBranchSpinner(String branchOffice) {
        try {
            ArrayList<CustomerManager> customerManagerArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CUSTOMER_TELLER_OFFICE + "=?";
            String[] selectionArgs = new String[]{valueOf(branchOffice)};

            @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TELLER_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSpinnerTellersFromCursor(customerManagerArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return customerManagerArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }



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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }




    public Cursor getSpinnerCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int customerID = cursor.getInt(0);
                String surname = cursor.getString(3);
                String firstName = cursor.getString(4);
                customers.add(new Customer(customerID, surname, firstName));

            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<Customer> getAllCustomerSpinner() {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getSpinnerCustomersFromCursor(customers, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return customers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public ArrayList<Customer> getAllCustomerBranchSpinner(String branchOffice) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CUSTOMER_OFFICE + "=?";
            String[] selectionArgs = new String[]{valueOf(branchOffice)};

            @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getSpinnerCustomersFromCursor(customers, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return customers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }



    public Cursor getAllCustomerManagersFromCursor(ArrayList<CustomerManager> customerManagers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int tellerID = cursor.getInt(0);
                String surname = cursor.getString(2);
                String firstName = cursor.getString(3);
                String phoneNumber = cursor.getString(4);
                String emailAddress = cursor.getString(5);
                String userName = cursor.getString(6);
                String dob = cursor.getString(7);
                String gender = cursor.getString(8);
                String address = cursor.getString(9);
                String office = cursor.getString(10);
                String dateJoined = cursor.getString(11);
                String password = cursor.getString(12);
                String nin = cursor.getString(13);
                Uri pix = Uri.parse(cursor.getString(14));
                String status = cursor.getString(15);

                try {
                    customerManagers.add(new CustomerManager(tellerID, surname, firstName, phoneNumber, emailAddress, nin,dob, gender, address, userName, password, office, dateJoined,pix,status));
                } catch (ConnectionFailedException e) {
                    e.printStackTrace();
                }
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }
    public Cursor getAllAdminFromCursor(ArrayList<AdminUser> adminUsers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int adminID = cursor.getInt(0);
                String surname = cursor.getString(2);
                String firstName = cursor.getString(3);
                String phoneNumber = cursor.getString(4);
                String emailAddress = cursor.getString(5);
                String dob = cursor.getString(6);
                String gender = cursor.getString(7);
                String address = cursor.getString(8);
                String office = cursor.getString(9);
                String dateJoined = cursor.getString(10);
                String userName = cursor.getString(11);
                String password = cursor.getString(12);
                String nin = cursor.getString(13);
                Uri pix = Uri.parse(cursor.getString(14));
                String status = cursor.getString(15);
                adminUsers.add(new AdminUser(adminID, surname, firstName, phoneNumber, emailAddress, nin,dob, gender, address, userName, password, office, dateJoined,pix,status));
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<AdminUser> getAllAdmin() {
        try {
            ArrayList<AdminUser> adminUserArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query(ADMIN_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getAllAdminFromCursor(adminUserArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return adminUserArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    public ArrayList<CustomerManager> getAllCustomersManagers() {
        ArrayList<CustomerManager> customerManagers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TELLER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAllCustomerManagersFromCursor(customerManagers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customerManagers;
    }
    public ArrayList<Profile> getTellersFromMachine(String Teller) {
        ArrayList<Profile> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(Teller)};
        Cursor cursor = db.query(PROFILES_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleProfileFromCursor(profileArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return profileArrayList;

    }
    public ArrayList<Profile> getBranchFromMachine(String Branch) {
        try {
            ArrayList<Profile> profileArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROFILE_ROLE + "=?";
            String[] selectionArgs = new String[]{Branch};
            Cursor cursor = db.query(PROFILES_TABLE, null,  selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSimpleProfileFromCursor(profileArrayList, cursor);
                    cursor.close();
                }

            db.close();

            return profileArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getSimpleProfileFromCursor(ArrayList<Profile> profileArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                String surname = cursor.getString(3);
                String firstName = cursor.getString(4);
                profileArrayList.add(new Profile(surname,firstName));
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<Customer> getCustomerFromMachine(String Customer) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{Customer};

        Cursor cursor = db.query(PROFILES_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return customers;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAllCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;
    }
    public ArrayList<Customer> getAllCustomersNames() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CUSTOMER_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;

    }
    public ArrayList<Customer> getCustomerFromCurrentProfile(int profileID) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PROFILE_ROLE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileID)};

        Cursor cursor = db.query(
                CUSTOMER_TABLE + " , " + PROFILES_TABLE,
                Utils.concat(new String[]{CUSTOMER_TABLE, PROFILES_TABLE}),
                CUSTOMER_PROF_ID + " = " + PROFILE_ID + " AND " + PROFILE_ID + " = " +  profileID ,
                null, PROFILE_ID, null, PROFILE_ID);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getCustomersFromCursor(customers, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return customers;
    }


    public Cursor getSimpleCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
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
    }

    public ArrayList<Customer> getCustomersFromCurrentCustomer(int customerID) {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(customerID)};

            Cursor cursor = db.query(CUSTOMER_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getAllCustomersFromCursor(customers, cursor);
                    cursor.close();
                }

            db.close();


            return customers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }



    public Cursor getAllCustomersFromCursor(ArrayList<Customer> customers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int customerID = cursor.getInt(0);
                String surname = cursor.getString(3);
                String firstName = cursor.getString(4);
                String phoneNumber = cursor.getString(5);
                String emailAddress = cursor.getString(6);
                String nin = cursor.getString(7);
                String dob = cursor.getString(8);
                String gender = cursor.getString(9);
                String address = cursor.getString(10);
                String userName = cursor.getString(11);
                String password = cursor.getString(12);
                String office = cursor.getString(13);
                String dateJoined = cursor.getString(14);
                customers.add(new Customer(customerID, surname, firstName, phoneNumber, emailAddress, nin,dob, gender, address, userName, password, office, dateJoined));
            }
            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }
    public ArrayList<String> getCustomer() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor res = db.rawQuery("select * from CUSTOMER_TABLE", null)) {
                res.moveToFirst();

                while (!res.isAfterLast()) {
                    array_list.add(res.getString(CUSTOMER_ID_COLUMN));
                    res.moveToNext();
                }
            }
            return array_list;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getCustomerName() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor res = db.rawQuery("select * from CUSTOMER_TABLE", null)) {
                res.moveToFirst();

                while (!res.isAfterLast()) {
                    array_list.add(res.getString(CUSTOMER_FIRST_NAME_COLUMN));
                    res.moveToNext();
                }
            }
            return array_list;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }




    public Customer findCustomer(String phoneNumber) {
        try {
            String[] projection = {CUSTOMER_ID,
                    CUSTOMER_FIRST_NAME, CUSTOMER_SURNAME, CUSTOMER_PHONE_NUMBER, CUSTOMER_USER_NAME, CUSTOMER_DATE_JOINED, CUSTOMER_ADDRESS};

            String selection = "CUSTOMER_PHONE_NUMBER = \"" + phoneNumber + "\"";

            Cursor cursor = myCR.query(UserContentProvider.CONTENT_URI,
                    projection, selection, null,
                    null);

            Customer customer = new Customer();

            assert cursor != null;
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                customer.setCusUID(Integer.parseInt(cursor.getString(0)));
                customer.setCusFirstName(cursor.getString(1));
                customer.setCusSurname(cursor.getString(2));
                customer.setCusPhoneNumber(cursor.getString(3));
                customer.setCusUserName(cursor.getString(4));
                customer.setCusDateJoined(cursor.getString(5));
                customer.setCusAddress(cursor.getString(6));
                cursor.close();
            } else {
                customer = null;
            }
            return customer;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String getUserRole(int userId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String result=null;
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};

        Cursor cursor = sqLiteDatabase.query(PROFILES_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(16);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return result;

    }

    public Cursor getUsersDetails() {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getAdminUserDetails(int adminID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM ADMIN_TABLE WHERE ADMIN_ID = ?",
                    new String[]{valueOf(adminID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getUserDetails(int userId) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(userId)};
            return sqLiteDatabase.rawQuery("SELECT * FROM PROFILES_TABLE WHERE PROFILE_ID = ?",
                    new String[]{valueOf(userId)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateRole(String role, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ROLE, role);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateCustomerUserName(String userName, int customerID) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUSTOMER_USER_NAME, userName);
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(customerID)};

            return sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProfileUserName(String name, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_USERNAME, name);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateCustomer(int id,String lastName, String firstName,String phoneNo,String email,String gender,String address,String office,String userName,String password,String role) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUSTOMER_SURNAME, lastName);
            contentValues.put(CUSTOMER_FIRST_NAME, firstName);
            contentValues.put(CUSTOMER_USER_NAME, userName);
            contentValues.put(CUSTOMER_PHONE_NUMBER, phoneNo);
            contentValues.put(CUSTOMER_EMAIL_ADDRESS, email);
            contentValues.put(CUSTOMER_ADDRESS, address);
            contentValues.put(CUSTOMER_PASSWORD, password);
            contentValues.put(CUSTOMER_GENDER, gender);
            contentValues.put(CUSTOMER_OFFICE, office);
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, "CUSTOMER_ID = ?", new String[]{valueOf(id)})
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public void updateCustomer1(int customerID,String lastName, String firstName,String phoneNo,String email,String gender,String address,String office,String userName,String password) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUSTOMER_SURNAME, lastName);
            contentValues.put(CUSTOMER_FIRST_NAME, firstName);
            contentValues.put(CUSTOMER_USER_NAME, userName);
            contentValues.put(CUSTOMER_PHONE_NUMBER, phoneNo);
            contentValues.put(CUSTOMER_EMAIL_ADDRESS, email);
            contentValues.put(CUSTOMER_ADDRESS, address);
            contentValues.put(CUSTOMER_PASSWORD, password);
            contentValues.put(CUSTOMER_GENDER, gender);
            contentValues.put(CUSTOMER_OFFICE, office);
            sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, "CUSTOMER_ID = ?", new String[]{valueOf(customerID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateProfile(int id,String lastName, String firstName,String phoneNo,String email,String gender,String address,String nextOfKin,String userName,String password) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_SURNAME, lastName);
            contentValues.put(PROFILE_FIRSTNAME, firstName);
            contentValues.put(PROFILE_USERNAME, userName);
            contentValues.put(PROFILE_PHONE, phoneNo);
            contentValues.put(PROFILE_EMAIL, email);
            contentValues.put(PROFILE_ADDRESS, address);
            contentValues.put(PROFILE_PASSWORD, password);
            contentValues.put(PROFILE_GENDER, gender);
            contentValues.put(PROFILE_NEXT_OF_KIN, nextOfKin);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public boolean updatePhoneNumber(String phoneNumber, int profileID) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_PHONE, phoneNumber);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(profileID)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUserRole(String role, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ROLE, role);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUserAddress(String address, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ADDRESS, address);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUserPicture(Uri profilePicture, int PROFILE_ID) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PICTURE_URI, valueOf(profilePicture));
            String selection = PROFID_FOREIGN_KEY_PIX + "=?";
            String[] selectionArgs = new String[]{String.valueOf(PROFILE_ID)};
            return sqLiteDatabase.update(PICTURE_TABLE, contentValues, selection,
                    selectionArgs) > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


    public boolean updateProfileStatus(String status, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_STATUS, status);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(PROFILE_ID)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateCustomerPassword(String password, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_PASSWORD, password);
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(id)};
            return sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public void updateCustomerUserNamePassword(int customerId, String userName, String password) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUSTOMER_PASSWORD, password);
            contentValues.put(CUSTOMER_USER_NAME, userName);
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(customerId)};
            sqLiteDatabase.update(CUSTOMER_TABLE, contentValues, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateProfile(int profileId, String phoneNo, String nextOfKin, String userEmail, String userAddress, String userName, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_PASSWORD, password);
        contentValues.put(PROFILE_USERNAME, userName);
        contentValues.put(PROFILE_ADDRESS, userAddress);
        contentValues.put(PROFILE_EMAIL, userEmail);
        contentValues.put(PROFILE_NEXT_OF_KIN, nextOfKin);
        contentValues.put(PROFILE_PHONE, phoneNo);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileId)};
        sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs);

    }
    public void repostAdmin(int adminID, String newOffice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ADMIN_OFFICE, newOffice);
            String selection = ADMIN_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(adminID)};
            sqLiteDatabase.update(ADMIN_TABLE, contentValues, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public boolean updateProfilePassword(String password, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_PASSWORD, password);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection, selectionArgs)
                > 0;

    }

    public boolean updateLoan(String status, int loanId,double loanBalance) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(LOAN_BALANCE, loanBalance);
            contentValues.put(LOAN_STATUS, status);
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(loanId)};
            return sqLiteDatabase.update(LOAN_TABLE, contentValues, selection, selectionArgs)
                    > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }






    public String getPassword1(int profileId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String result=null;
        String selection = PROF_ID_FOREIGN_KEY_PASSWORD + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profileId)};
        Cursor cursor = sqLiteDatabase.query(CUSTOMER_TABLE, null,
                selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(2);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return result;
    }



    public Cursor queueAll() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = new String[]{CUSTOMER_ID, CUSTOMER_USER_NAME, CUSTOMER_PHONE_NUMBER, CUSTOMER_FIRST_NAME, CUSTOMER_EMAIL_ADDRESS, CUSTOMER_SURNAME};
            Cursor cursor = db.query(CUSTOMER_TABLE, columns,
                    null, null, CUSTOMER_PHONE_NUMBER, null, null);

            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public Cursor queueAll_SortBy_PhoneNumber_And_Id() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = new String[]{PROFILE_ID, CUSTOMER_PHONE_NUMBER, CUSTOMER_ID};

            return db.query(CUSTOMER_TABLE, columns,
                    null, null, null, CUSTOMER_PHONE_NUMBER, CUSTOMER_ID);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor queueAll_SortBy_Office_And_Gender() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = new String[]{PROFILE_ID, CUSTOMER_OFFICE, CUSTOMER_GENDER};
            Cursor cursor = db.query(CUSTOMER_TABLE, columns,
                    null, null, null, CUSTOMER_OFFICE, CUSTOMER_OFFICE);

            return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
                case REPORT_NUMBER_COLUMN:
                    queryBuilder.appendWhere(REPORT_ID + "="
                            + uri.getLastPathSegment());
                    break;
                case REPORT_DATE_COLUMN:
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

    public void overwriteLoan(int profileID,int loanID,int cusID,double amount,double loanBalance,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOAN_AMOUNT, amount);
        cv.put(LOAN_STATUS, status);
        cv.put(LOAN_BALANCE, loanBalance);
        String selection = LOAN_PROF_ID + "=? AND " + LOAN_ACCT_NO + "=? AND " + LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(loanID),valueOf(cusID)};
        db.update(LOAN_TABLE, cv, selection, selectionArgs);
        db.close();


    }


    public void overwriteAccount(Profile profile, Customer customer, Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
        cv.put(BANK_ACCT_BALANCE, account.getBankAccountBalance());
        String selection = ACCOUNT_PROF_ID + "=? AND " + ACCOUNT_NO + "=? AND " + ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profile.getPID()), valueOf(account.getSkyLightAcctNo()),valueOf(customer.getCusUID())};
        db.update(ACCOUNTS_TABLE, cv, selection,
                selectionArgs);
        db.close();

    }

    public void overwriteAccount(Profile userProfile, Account account,int cusID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Customer customer = new Customer();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_NAME, account.getAccountName());
        cv.put(ACCOUNT_BALANCE, account.getAccountBalance());
        cv.put(BANK_ACCT_BALANCE, account.getBankAccountBalance());
        String selection = ACCOUNT_PROF_ID + "=? AND " + ACCOUNT_NO + "=? AND " + ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(userProfile.getPID()), valueOf(account.getSkyLightAcctNo()),valueOf(cusID)};

        db.update(ACCOUNTS_TABLE, cv, selection,
                selectionArgs);
        db.close();


    }
    public void overwriteAccount1(int acctID,int cusID,double balance,double bankAcctBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        Customer customer = new Customer();
        Account account = customer.getCusAccount();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_NO, acctID);
        cv.put(ACCOUNT_BALANCE, balance);
        cv.put(BANK_ACCT_BALANCE, bankAcctBalance);
        String selection = ACCOUNT_CUS_ID + "=? AND " + ACCOUNT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(cusID), valueOf(acctID)};

        db.update(ACCOUNTS_TABLE, cv, selection,
                selectionArgs);
        //db.close();



    }
    public long insertBillsTX(int profileID, String label, String billerName, double amount, String itemCode, String selectedCountry,String reference, String subDate) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String details= label + ""+ billerName +","+itemCode+","+ selectedCountry;
            ContentValues contentValues = new ContentValues();
            contentValues.put(TRANSACTION_ID, reference);
            contentValues.put(PROFILE_ID, profileID);
            //contentValues.put(TRANSACTIONS_TYPE, String.valueOf(Transaction.TRANSACTION_TYPE.BILLSUBCRIPTION));
            contentValues.put(TRANSACTION_AMOUNT, amount);
            contentValues.put(TRANSACTION_STATUS, details);
            contentValues.put(TRANSACTION_DATE, subDate);
            sqLiteDatabase.insert(TRANSACTIONS_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return Long.parseLong(reference);
    }
    public void updateRole(int profileID,int role) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ROLE_ID",profileID);
            contentValues.put("ROLE", role);
            String selection = "ROLE_ID" + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            sqLiteDatabase.update("ROLES", contentValues, selection, selectionArgs);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public ArrayList<Profile> getAllProfiles() {
        try {
            ArrayList<Profile> profiles = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(PROFILES_TABLE, null, null, null, null,
                    null, null);
            getProfilesFromCursor(profiles, cursor);

            cursor.close();
            //db.close();

            return profiles;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    public ArrayList<String> getPackage() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from PACKAGE_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(PACKAGE_ID_COLUMN));
                res.moveToNext();
            }
        }
        return array_list;

    }

    public ArrayList<String> getReportNOS() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor res = db.rawQuery("select * from DAILY_REPORT_TABLE", null)) {
                res.moveToFirst();

                while (!res.isAfterLast()) {
                    array_list.add(res.getString(REPORT_NUMBER_COLUMN));
                    res.moveToNext();
                }
            }
            return array_list;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getMessages() {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }




    public ArrayList<Loan> getLoansFromCurrentProfile(int profileID) {
        try {
            ArrayList<Loan> loans = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = LOAN_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                    null, null);

            getLoansFromCursorCustomer(profileID);

            cursor.close();
            //db.close();

            return loans;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public ArrayList<PaymentCode> getSavingsCodesCustomer(int customerID) {
        try {
            ArrayList<PaymentCode> codes = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CODE_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.query(CODE_TABLE, null, selection, selectionArgs, null,
                    null, null);
            /*Cursor cursor = db.query(CODE_TABLE, null, CUSTOMER_ID
                            + " = " + customerID, new String[]{CODE_DATE,CODE_MANAGER,CODE_PIN}, null,
                    null, null);*/
            getAllSavingsCodes();

            cursor.close();
            //db.close();

            return codes;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<PaymentCode> getSavingsCodesProfile(int profileID) {
        try {
            ArrayList<PaymentCode> codes = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CODE_PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.query(CODE_TABLE, null, selection, selectionArgs, null,
                    null, null);

            getAllSavingsCodes();

            cursor.close();
            //db.close();

            return codes;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<PaymentDoc> getSavingsDocsCustomer(int customerID) {
        try {
            ArrayList<PaymentDoc> documents = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = DOCUMENT_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.query(DOCUMENT_TABLE, null, selection, selectionArgs, null,
                    null, null);

            getAllDocuments();

            cursor.close();
            //db.close();

            return documents;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public void getSavingsDocsProfile(int profileID) {
        try {
            ArrayList<PaymentDoc> documents = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = DOCUMENT_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            String[] column = new String[]{DOCUMENT_ID,DOCUMENT_PAYMENT_METHOD,DOCUMENT_TITLE,DOCUMENT_URI};
            @SuppressLint("Recycle") Cursor cursor = db.query(DOCUMENT_TABLE, column, selection, selectionArgs, null,
                    null, null);

            getAllDocuments();

            cursor.close();
            //db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public ArrayList<StandingOrder> getSOFromCurrentCustomer(int customerID) {
        ArrayList<StandingOrder> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(STANDING_ORDER_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrders,cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return standingOrders;
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
    public ArrayList<Loan> getLoansFromCurrentCustomer(int customerID) {
        ArrayList<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoansFromCursorCustomer(customerID);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loans;
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
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<SkyLightPackage> getPackageEndingIn3Days(String dateOfTomorrow) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<SkyLightPackage> getPackageEnding7Days(String dateOfSevenDays) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
    public ArrayList<StandingOrder> getAllStandingOrdersForCustomerDate(int customerID, String date) {
        ArrayList<StandingOrder> standingOrders = new ArrayList<StandingOrder>();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {SO_DAILY_AMOUNT, SO_EXPECTED_AMOUNT,SO_RECEIVED_AMOUNT,SO_DAYS_REMAINING,SO_END_DATE,SO_STATUS};


        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(date)};

        Cursor cursor = db.query(
                STANDING_ORDER_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{STANDING_ORDER_TABLE, PACKAGE_TABLE}),
                SO_CUS_ID + " = " + PACKAGE_CUSTOMER_ID_FOREIGN + " AND " + SO_START_DATE + " = " +  date,
                null, null, null, null);

        while (cursor.moveToNext()) {
            StandingOrder standingOrder = new StandingOrder();
            Transaction transaction = new Transaction();
            standingOrder.setSoDailyAmount(cursor.getDouble(1));
            standingOrder.setSoExpectedAmount(cursor.getDouble(3));
            standingOrder.setSoReceivedAmount(cursor.getInt(4));
            standingOrder.setTotalDays(cursor.getInt(5));
            standingOrder.setDaysRemaining(cursor.getInt(7));
            standingOrder.setSoStatus(cursor.getString(8));
            standingOrder.setSoStartDate(cursor.getString(10));
            standingOrder.setSoEndDate(cursor.getString(11));
            transaction.setApprovalDate(cursor.getString(12));
            transaction.setTransactionStatus(cursor.getString(13));
            standingOrders.add(standingOrder);
        }
        return standingOrders;
    }
    public ArrayList<StandingOrder> getStandingOrdersToday(String todayDate) {

        ArrayList<StandingOrder> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {SO_CUS_ID,SO_DAILY_AMOUNT, SO_EXPECTED_AMOUNT,SO_RECEIVED_AMOUNT,SO_DAYS_REMAINING};
        String selection = SO_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};
        Cursor cursor = db.query(STANDING_ORDER_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrders, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return standingOrders;
    }
    public int getAllStandingOrdersWithStatusCount(String Completed) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;

            String selection = SO_STATUS + "=?";
            String[] selectionArgs = new String[]{valueOf(Completed)};

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs
            );
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public int getDueSOTodayCount(String todayDate) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;


            String selection = SO_END_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(todayDate)};

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public int getDueSOCustomCount(String customDate) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;


            String selection = SO_END_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(customDate)};

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs
            );
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }




    public int getMessageCountToday(String dateOfMessage) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;


            String selection = MESSAGE_TIME + "=?";
            String[] selectionArgs = new String[]{valueOf(dateOfMessage)};

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + MESSAGE_TABLE + " WHERE " + selection, selectionArgs
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }



    public ArrayList<Loan> getLoansFromCustomer(int customerID) {
        Customer customer= new Customer();
        if(customer !=null){
            customerID=customer.getCusUID();
        }
        ArrayList<Loan> loans1 = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = LOAN_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(LOAN_TABLE, null, selection,selectionArgs, null, null,
                null, null);
        getLoansFromCursorCustomer(customerID);

        cursor.close();
        db.close();

        return loans1;
    }
    public ArrayList<Account> getAccountsForCustomer(int customerID) {
        Customer customer= new Customer();
        if(customer !=null){
            customerID=customer.getCusUID();
        }

        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ACCOUNT_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(ACCOUNTS_TABLE, null, selection,selectionArgs, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAccountsFromCurrentCustomer(customerID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return accounts;
    }

    private void getLoansFromCursorCustomer(int customerID) {
        try {
            ArrayList<Loan> loans = new ArrayList<>();
            Cursor cursor = null;

            if (cursor != null) {
                while (cursor.moveToNext()) {

                    if (customerID == cursor.getInt(1)) {
                        int id = cursor.getInt(1);
                        int loanId = cursor.getInt(0);
                        int loanType = cursor.getInt(4);
                        BigDecimal amount = BigDecimal.valueOf(cursor.getFloat(5));
                        BigDecimal interest = BigDecimal.valueOf(cursor.getFloat(7));
                        BigDecimal fixedPayment = BigDecimal.valueOf(cursor.getFloat(8));
                        BigDecimal totalInterests = BigDecimal.valueOf(cursor.getFloat(9));

                        BigDecimal downPayment = BigDecimal.valueOf(cursor.getFloat(10));
                        BigDecimal disposableCommission = BigDecimal.valueOf(cursor.getFloat(11));
                        BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(12));
                        BigDecimal residuePayment = BigDecimal.valueOf(cursor.getFloat(13));
                        String loanDate = valueOf(BigDecimal.valueOf(cursor.getFloat(14)));

                        String loanStartDate = cursor.getString(15);
                        String loanEndDate = cursor.getString(16);
                        String loanStatus = cursor.getString(17);


                        loans.add(new Loan(customerID, loanId, loanType, amount, interest, fixedPayment, totalInterests, downPayment, disposableCommission, monthlyCommission, residuePayment, loanDate, loanStartDate, loanEndDate, loanStatus));
                    }
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<Loan> getLoanFromCurrentProfile(int profileID) {
        ArrayList<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LOAN_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(LOAN_TABLE, null, selection,selectionArgs, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLoansFromProfileCursor(profileID, loans, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return loans;

    }


    private Cursor getLoansFromProfileCursor(int profileID, ArrayList<Loan> loanArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                if (profileID == cursor.getInt(2)) {
                    int id = cursor.getInt(2);
                    int loanId = cursor.getInt(0);
                    int loanType = cursor.getInt(4);
                    BigDecimal amount = BigDecimal.valueOf(cursor.getFloat(5));
                    BigDecimal interest = BigDecimal.valueOf(cursor.getFloat(7));
                    BigDecimal fixedPayment = BigDecimal.valueOf(cursor.getFloat(8));
                    BigDecimal totalInterests = BigDecimal.valueOf(cursor.getFloat(9));

                    BigDecimal downPayment = BigDecimal.valueOf(cursor.getFloat(10));
                    BigDecimal disposableCommission = BigDecimal.valueOf(cursor.getFloat(11));
                    BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(12));
                    BigDecimal residuePayment = BigDecimal.valueOf(cursor.getFloat(13));
                    String loanDate = valueOf(BigDecimal.valueOf(cursor.getFloat(14)));

                    String loanStartDate = cursor.getString(15);
                    String loanEndDate = cursor.getString(16);
                    String loanStatus = cursor.getString(17);


                    loanArrayList.add(new Loan(profileID, loanId, loanType, amount, interest, fixedPayment, totalInterests, downPayment, disposableCommission, monthlyCommission, residuePayment, loanDate, loanStartDate, loanEndDate, loanStatus));
                }
            }return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }
    public Cursor getBillsFromCustomerCursor(long customerID, ArrayList<BillModel> bills, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                if (customerID == cursor.getLong(CUSTOMER_ID_COLUMN)) {
                    long billID = cursor.getLong(Bill_ID_COLUMN);
                    String billName = cursor.getString(BILL_NAME_COLUMN);
                    Integer billAmount = cursor.getInt(BILL_AMOUNT_COLUMN);
                    String billCurrency = cursor.getString(BILL_CURRENCY_COLUMN);
                    //BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(LOAN_MONTHLY_COLUMN));
                    String billDate = cursor.getString(BILL_DATE_COLUMN);
                    String billFreq = cursor.getString(BILL_RECURRING_TYPE_COLUMN);
                    String billStatus = cursor.getString(BILL_STATUS_COLUMN);

                    bills.add(new BillModel(billID,customerID, billName, billAmount,billCurrency,billDate,billFreq,billStatus));
                }
            }return  cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public void updateBill(long billID,String recurrenceType,String newStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Customer customer = new Customer();
            BillModel bill = new BillModel();
            ContentValues cv = new ContentValues();
            cv.put(BILL_ID, valueOf(billID));
            cv.put(BILL_STATUS, newStatus);
            cv.put(BILL_REF, bill.getReference());
            cv.put(BILL_RECURRING_TYPE, recurrenceType);
            String selection = BILL_ID + "=? AND " + BILL_REF + "=?";
            String[] selectionArgs = new String[]{valueOf(billID), valueOf(newStatus)};

            db.update(BILL_TABLE, cv, selection,
                    selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }



    public int getBillsCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String countQuery = "SELECT  * FROM " + BILL_TABLE;
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getBillsCountCustomer(long customerID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + BILL_TABLE + " WHERE " + selection, selectionArgs
            );

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(0);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public Cursor getBillsFromAdminCursor(ArrayList<BillModel> billModels, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                long billID = cursor.getLong(Bill_ID_COLUMN);
                long customerID = cursor.getLong(CUSTOMER_ID_COLUMN);
                String billName = cursor.getString(BILL_NAME_COLUMN);
                Integer billAmount = cursor.getInt(BILL_AMOUNT_COLUMN);
                String billCurrency = cursor.getString(BILL_CURRENCY_COLUMN);
                //BigDecimal monthlyCommission = BigDecimal.valueOf(cursor.getFloat(LOAN_MONTHLY_COLUMN));
                String billDate = cursor.getString(BILL_DATE_COLUMN);
                String billFreq = cursor.getString(BILL_RECURRING_TYPE_COLUMN);
                String billStatus = cursor.getString(BILL_STATUS_COLUMN);

                billModels.add(new BillModel(billID,customerID, billName, billAmount,billCurrency,billDate,billFreq,billStatus));

            }return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return cursor;
    }

    public ArrayList<BillModel> getAllBillsAdmin() {
        try {
            ArrayList<BillModel> bills = new ArrayList<>();
            Profile profile = new Profile();
            long profileID = profile.getPID();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(BILL_TABLE, null, null, null, null,
                    null, null);
            getBillsFromAdminCursor(bills, cursor);

            cursor.close();
            db.close();

            return bills;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<Loan> getAllLoansFromProfile1(int profileID) {
        try {
            ArrayList<Loan> loanArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = LOAN_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.query(LOAN_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getLoansFromProfileCursor(profileID, loanArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return loanArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private Cursor getProfilesFromCursor(ArrayList<Profile> profiles, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String lastName = cursor.getString(1);
                String firstName = cursor.getString(2);
                String state = cursor.getString(12);
                String username = cursor.getString(17);
                //String password = cursor.getString(18);
                profiles.add(new Profile(id,firstName, lastName, state, username ));
            }return  cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return cursor;
    }

    public ArrayList<Payee> getPayeesFromCurrentProfile(int profileID) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Payee> getPayeesFromCurrentCustomer(int customerID) {
        try {
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private Cursor getPayeesFromCustomerCursor(int customerID, ArrayList<Payee> payees, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                if (customerID == cursor.getLong(1)) {
                    int profID = cursor.getInt(2);
                    int payeeID = cursor.getInt(0);
                    String payeeName = cursor.getString(3);

                    payees.add(new Payee(payeeID,profID, payeeName));
                }
            }return cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
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

    public ArrayList<Transaction> getTransactionsFromCurrentAccount(String accountNo) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TRANSACTION_ACCT_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(accountNo)};
            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursor(transactions, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<Transaction> getTransactionsToday(String today) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = TRANSACTION_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(today)};
            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursor(transactions, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();

            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public ArrayList<Transaction> getAllTransactions() {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, null, null, null,
                    null, null);

            getTransactionsFromCursor( transactions, cursor);

            cursor.close();
            //db.close();

            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public ArrayList<Transaction> getAllTransactionsSimple() {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query(TRANSACTIONS_TABLE, new String[]{TRANSACTION_ID, TRANSACTION_DATE, TRANSACTION_PAYEE, TRANSACTION_PAYER,
                            TRANSACTION_AMOUNT,TRANSACTIONS_TYPE,TRANSACTION_METHOD_OF_PAYMENT,TRANSACTION_OFFICE_BRANCH,TRANSACTION_STATUS}, null, null, TRANSACTIONS_TYPE,
                    null, null);

            getTransactionsFromCursorSimple( transactions, cursor);

            cursor.close();
            //db.close();

            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    private Cursor getTransactionsFromCursorSimple( ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int transactionID = cursor.getInt(0);
                int profileID = cursor.getInt(1);
                int customerID = cursor.getInt(2);
                int accountNo=cursor.getInt(3);
                String timestamp = cursor.getString(4);
                String sendingAccount = cursor.getString(5);
                String destinationAccount = cursor.getString(6);
                String payee = cursor.getString(7);
                String payer = cursor.getString(8);
                double amount = cursor.getDouble(9);
                Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(10));
                String method = cursor.getString(11);
                String officeBranch = cursor.getString(12);
                String approver = cursor.getString(13);
                String approvalDate = cursor.getString(14);
                String status = cursor.getString(15);
                transactions.add(new Transaction(transactionID, timestamp,payee,payer, amount,transactionType, method,officeBranch,status));
            }return  cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }




    private Cursor getTransactionsFromCursor( ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int transactionID = cursor.getInt(0);
                int profileID = cursor.getInt(1);
                int customerID = cursor.getInt(2);
                int accountNo=cursor.getInt(3);
                String timestamp = cursor.getString(4);
                int sendingAccount = cursor.getInt(5);
                int destinationAccount = cursor.getInt(6);
                String payee = cursor.getString(7);
                String payer = cursor.getString(8);
                double amount = cursor.getDouble(9);
                Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(10));
                String method = cursor.getString(11);
                String officeBranch = cursor.getString(12);
                String approver = cursor.getString(13);
                String approvalDate = cursor.getString(14);
                String status = cursor.getString(15);

                transactions.add(new Transaction(transactionID, profileID,customerID,accountNo,timestamp,sendingAccount, destinationAccount,payee,payer, amount,transactionType, method,officeBranch,approver,approvalDate,status));
            }return  cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }


    private Cursor getTransactionsFromCustomerCursor(int customerID, ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                if (customerID == cursor.getInt(2)) {
                    int transactionID = cursor.getInt(0);
                    int accountNo = cursor.getInt(3);
                    String timestamp = cursor.getString(4);
                    int sendingAccount = cursor.getInt(5);
                    int destinationAccount = cursor.getInt(6);
                    double amount = cursor.getDouble(9);
                    Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(TRANSACTION_TYPE_COLUMN));

                    if (transactionType == Transaction.TRANSACTION_TYPE.PAYMENT) {
                        transactions.add(new Transaction(transactionID, timestamp, amount));
                    } else if (transactionType == Transaction.TRANSACTION_TYPE.TRANSFER) {
                        transactions.add(new Transaction(transactionID, timestamp, sendingAccount, destinationAccount, amount));
                    } else if (transactionType == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                        transactions.add(new Transaction(transactionID, timestamp, amount));
                    }

                }
            }return  cursor;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return cursor;
    }

    public ArrayList<String> getTransactions() {
        try {
            ArrayList<String> array_list = new ArrayList<String>();

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor res = db.rawQuery("select * from TRANSACTIONS_TABLE", null)) {
                res.moveToFirst();

                while (!res.isAfterLast()) {
                    array_list.add(res.getString(0));
                    res.moveToNext();
                }
            }
            return array_list;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Account> getAccountsFromCurrentCustomer(int customerID) {
        try {
            ArrayList<Account> accounts = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = ACCOUNT_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.query(ACCOUNTS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getAccountsFromCursor(accounts, cursor);

            cursor.close();
            //db.close();

            return accounts;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<Account> getAccountsFromCurrentProfile(int profileID) {
        try {
            ArrayList<Account> accounts = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = ACCOUNT_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            @SuppressLint("Recycle") Cursor cursor = db.query(ACCOUNTS_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getAccountsFromCursor(accounts, cursor);

            cursor.close();
            db.close();

            return accounts;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getAccountIds(int cusId) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();

            String selection = ACCOUNT_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(cusId)};
            return sqLiteDatabase.rawQuery("SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + selection, selectionArgs);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private void getAccountsFromCursor(ArrayList<Account> accounts, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String accountNo = cursor.getString(1);
                String accountType = cursor.getString(5);
                String accountBank = cursor.getString(6);
                String accountName = cursor.getString(7);
                double accountBalance = cursor.getDouble(8);
                accounts.add(new Account(id, accountBank, accountName, accountNo, accountBalance, AccountTypes.valueOf(accountType)));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public ArrayList<Account> getAllAccounts() {
        try {
            ArrayList<Account> accounts = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Profile profile = new Profile();
            long profileID = profile.getPID();
            Cursor cursor = db.query(ACCOUNTS_TABLE, null, null, null, null,
                    null, null);
            getAccountsFromCursor(accounts, cursor);
            cursor.close();
            //db.close();

            return accounts;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    private Profile getProfile(String phoneNumber) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Profile profile = new Profile();

            @SuppressLint("Recycle") Cursor cursor = db.query(PROFILES_TABLE,
                    null,
                    PROFILE_PHONE + "=?",
                    new String[]{String.valueOf(phoneNumber)},
                    null,
                    null,
                    null);
            if (cursor.moveToLast()) {
                try {
                    profile.setProfileUserName(cursor.getString(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                profile.setProfileLastName(cursor.getString(1));
                profile.setProfileFirstName(cursor.getString(2));
                profile.setProfilePhoneNumber(cursor.getString(3));
                profile.setProfileEmail(cursor.getString(4));
                profile.setProfileGender(cursor.getString(6));
                profile.setProfileOffice(cursor.getString(14));
                profile.setProfileDob(cursor.getString(4));
                profile.setProfileAddress(cursor.getString(7));
                return profile;

            }else {
                Log.e("error! not found", "We could not find that User ");
                return profile;

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private Customer getCustomer(String phoneNumber) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Customer u = new Customer();

            @SuppressLint("Recycle") Cursor cursor = db.query(CUSTOMER_TABLE,
                    null,
                    CUSTOMER_PHONE_NUMBER + "=?",
                    new String[]{String.valueOf(phoneNumber)},
                    null,
                    null,
                    null);
            if (cursor.moveToLast()) {
                u.setCusUserName(cursor.getString(1));
                u.setCusSurname(cursor.getString(2));
                u.setCusFirstName(cursor.getString(3));
                u.setCusPhoneNumber(cursor.getString(4));
                u.setCusEmail(cursor.getString(5));
                u.setCusGender(cursor.getString(6));
                u.setCusOffice(cursor.getString(7));
                u.setCusDob(cursor.getString(8));
                u.setCusAddress(cursor.getString(9));
                return u;

            }else {
                Log.e("error! not found", "We could not find that User ");
                return u;

            }


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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
    public long insertNewLoan(int profileID, int customerID, int borrowingID, double amountToBorrow, String borrowingDate, String bankName, String bankAcctNo, String borrower, int accountNo, String loanAcctType, int loanOTP, double v, String pending) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOAN_PROF_ID, profileID);
        contentValues.put(LOAN_CUS_ID, customerID);
        contentValues.put(LOAN_ID, borrowingID);
        contentValues.put(LOAN_ACCT_NO, accountNo);
        contentValues.put(LOAN_AMOUNT, amountToBorrow);
        contentValues.put(LOAN_DATE, borrowingDate);
        contentValues.put(LOAN_CODE, loanOTP);
        contentValues.put(LOAN_INTEREST, 0.00);
        contentValues.put(LOAN_STATUS, pending);
        return sqLiteDatabase.insert(LOAN_TABLE, null, contentValues);
    }
    public long insertRole(int profileID,String role,String rolePhoneNo) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("role_ID", profileID);
            //contentValues.put("roleUserName", profileID);
            //contentValues.put("rolePassword", role);
            contentValues.put("rolePhoneNo", rolePhoneNo);
            contentValues.put("role", role);
            return sqLiteDatabase.insert("ROLES", null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public boolean updateRole(int profileID,String role) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROFILE_ROLE, role);
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            return sqLiteDatabase.update(PROFILES_TABLE, contentValues, selection,
                    selectionArgs) > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }



    public long insertRole(String role,int id,String rolerPhoneNo) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ROLE", role);
            contentValues.put("role_ID", id);
            contentValues.put("rolePhoneNo", rolerPhoneNo);
            return sqLiteDatabase.insert("ROLES", null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }



    protected Cursor getAccountDetails(int accountId) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(accountId)};
            return sqLiteDatabase.rawQuery("SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + selection, selectionArgs);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }



    protected BigDecimal getBalance(int accountId) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            BigDecimal result = null;
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(accountId)};
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT BALANCE FROM " + ACCOUNTS_TABLE + " WHERE " + selection, selectionArgs);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        result = new BigDecimal(cursor.getString(0));
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            sqLiteDatabase.close();
            return result;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    protected Cursor getAccountTypesId() {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT ID FROM ACCOUNT_TYPES_TABLE", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public boolean updateAccountName(String name, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_NAME, name);
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            return sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                    selectionArgs) > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public void updateAccBalance(int eWalletID, double newBalance) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_BALANCE, newBalance);
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(eWalletID)};
            sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public boolean updateAccountBalance(BigDecimal balance, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_BALANCE, balance.toPlainString());
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            return sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                    selectionArgs) > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateAccountType(String typeId, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_TYPE, typeId);
            String selection = ACCOUNT_NO + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            return sqLiteDatabase.update(ACCOUNTS_TABLE, contentValues, selection,
                    selectionArgs) > 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


    public void updateTransactionStatus(String status, Transaction transaction) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            int transactionID = transaction.getTransactionID();
            contentValues.put(TRANSACTION_STATUS, status);
            String selection = TRANSACTION_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(transactionID)};
            sqLiteDatabase.update(TRANSACTIONS_TABLE, contentValues, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void updateTransactionStatus(String status, int transactionID) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TRANSACTION_STATUS, status);
            String selection = TRANSACTION_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(transactionID)};
            sqLiteDatabase.update(TRANSACTIONS_TABLE, contentValues, selection,
                    selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }


    public boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_TYPE_INTEREST, interestRate.toPlainString());
            String selection = ACCOUNT_TYPE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            return sqLiteDatabase.update(ACCOUNT_TYPES_TABLE, contentValues, selection,
                    selectionArgs)>0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


    //SELECT METHODS
    public Cursor getRoles() {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM ROLES;", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /*public String getRole(long id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT NAME FROM ROLES WHERE PROFILE_ID = ?",
                new String[]{valueOf(id)});
        cursor.moveToFirst();
        String value = cursor.getString(cursor.getColumnIndex("NAME"));
        cursor.close();
        return value;

    }


    public ArrayList<String> getPictures() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from PICTURE_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(res.getColumnIndex(PICTURE_URI)));
                res.moveToNext();
            }
        }
        return array_list;
    }*/

    public Bitmap getProfilePicture(int userId) {
        try {
            String picturePath = getPicturePath(userId);
            if (picturePath == null || picturePath.length() == 0)
                return (null);
            Bitmap profilePicture = BitmapFactory.decodeFile(picturePath);

            return (profilePicture);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public String getProfileRoleByEmailAndPassword(String email) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String role=null;

            @SuppressLint("Recycle") Cursor pictureCursor = db.query(PROFILES_TABLE,
                    null,
                    PROFILE_EMAIL + "=?",
                    new String[]{String.valueOf(email)},
                    null,
                    null,
                    null);
            pictureCursor.moveToNext();

            role = pictureCursor.getString(16);

            return (role);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public String getProfileRoleByUserNameAndPassword(String userName,String password) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String role = null;
            String[] columns = {PROFILE_ROLE};
            String selection = PROFILE_USERNAME + "=? AND " + PROFILE_PASSWORD + "=?";
            String[] selectionArgs = new String[]{valueOf(userName), valueOf(password)};

            @SuppressLint("Recycle") Cursor profileCursor = db.query(PROFILES_TABLE,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            if (profileCursor != null && profileCursor.getCount() > 0) {
                profileCursor.moveToFirst();
                role = profileCursor.getString((16));
                profileCursor.close();
            }

            /*if(profileCursor != null && profileCursor.moveToFirst()){
                //role = profileCursor.getString(16);
                role = profileCursor.getString(profileCursor.getColumnIndex(PROFILE_ROLE));
                //profileCursor.close();
            }*/

            return (role);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    public int getProfileIDByUserNameAndPassword(String userName, String email) {
        int picturePath=0;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String[] columns = {PROFILE_ID};
            String selection = PROFILE_USERNAME + "=? AND " + PROFILE_PASSWORD + "=?";
            String[] selectionArgs = new String[]{userName, email};

            @SuppressLint("Recycle") Cursor pictureCursor = db.query(PROFILES_TABLE,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            while (pictureCursor.moveToNext()) {
                picturePath = pictureCursor.getInt(0);
            }
            if (pictureCursor != null)
                if (pictureCursor.getCount() > 0) {
                    pictureCursor.close();
                }


            return picturePath;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public long getProfileIDByEmailAndPassword(String email) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String selection = PROFILE_EMAIL + "=?";
            String[] selectionArgs = new String[]{valueOf(email)};

            @SuppressLint("Recycle") Cursor pictureCursor = db.query(PROFILES_TABLE,
                    null,
                    selection, selectionArgs,
                    null,
                    null,
                    null);
            pictureCursor.moveToNext();

            String picturePath = pictureCursor.getString(0);

            return Long.parseLong((picturePath));

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    private String getPicturePath(int profileId) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String selection = PROFID_FOREIGN_KEY_PIX + "=?";
            String[] selectionArgs = new String[]{valueOf(profileId)};

            @SuppressLint("Recycle") Cursor pictureCursor = db.query(PICTURE_TABLE,
                    null,
                    selection, selectionArgs,
                    null,
                    null,
                    null);
            pictureCursor.moveToNext();

            String picturePath = pictureCursor.getString(0);

            return (picturePath);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public void deletePicture(int userId) {
        try {
            String picturePath = getPicturePath(userId); // See above
            if (picturePath != null && picturePath.length() != 0) {
                File reportFilePath = new File(picturePath);
                reportFilePath.delete();
            }

            SQLiteDatabase db = getWritableDatabase();
            String selection = PROFID_FOREIGN_KEY_PIX + "=?";
            String[] selectionArgs = new String[]{valueOf(userId)};

            db.delete(PICTURE_TABLE,
                    selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public Cursor getData(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};

            return db.rawQuery(
                    "SELECT * FROM " + PROFILES_TABLE + " WHERE " + selection,
                    selectionArgs
            );

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getCustomerCursor(int customerID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            return db.rawQuery(
                    "SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + selection,
                    selectionArgs
            );


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getProfileCursor(int profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};

            return db.rawQuery(
                    "SELECT * FROM " + PROFILES_TABLE + " WHERE " + selection,
                    selectionArgs
            );

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getGroupSavingsCursor(int groupSavingsID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            return db.rawQuery("select * from GROUP_SAVINGS_TABLE where PROFILE_ID=" + groupSavingsID + "", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getLoanCursor(int loanID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = LOAN_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(loanID)};

            return db.rawQuery(
                    "SELECT * FROM " + LOAN_TABLE + " WHERE " + selection,
                    selectionArgs
            );

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getProfileLoanCursor(int profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = LOAN_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            return db.rawQuery(
                    "SELECT * FROM " + LOAN_TABLE + " WHERE " + selection,
                    selectionArgs
            );


            //return db.rawQuery("select * from LOAN_TABLE where PROFILE_ID=" + loanID + "", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }





    public boolean updateBillStatus(long billID, String billStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BILL_STATUS, billStatus);
            db.update(BILL_TABLE, contentValues, "BILL_ID = ? ", new String[]{Long.toString(billID)});
            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public ArrayList<BillModel> getBills() {
        try {
            ArrayList<BillModel> bills = new ArrayList<BillModel>();
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            SQLiteDatabase db = this.getReadableDatabase();
            queryBuilder
                    .setTables(BILL_TABLE
                            + " INNER JOIN "
                            + CUSTOMER_TABLE
                            + " ON "
                            + DBHelper.BILL_CUSTOMER_ID
                            + " = "
                            + (CUSTOMER_TABLE + "." + DBHelper.CUSTOMER_ID_COLUMN));

            // Get cursor

            Cursor cursor = queryBuilder.query(db, new String[] {
                            BILL_ID_WITH_PREFIX,
                            BILL_TABLE + "."
                                    + BILL_NAME,
                            BILL_AMOUNT,
                            BILL_CURRENCY,
                            BILL_DATE,
                            DBHelper.BILL_CUSTOMER_ID,
                            CUSTOMER_TABLE + "."
                                    + CUSTOMER_PHONE_NUMBER }, null, null, null, null,
                    null);

            while (cursor.moveToNext()) {
                BillModel bill = new BillModel();
                bill.setId(cursor.getInt(0));
                bill.setName(cursor.getString(1));
                bill.setAmount(cursor.getInt(2));
                bill.setCurrency(cursor.getString(3));
                bill.setName(cursor.getString(4));
                //bill.setBillDate(cursor.getString(5));
                bill.setBillStatus(cursor.getString(6));
                try {
                    bill.setBillDate(formatter.parse(cursor.getString(5)));
                } catch (ParseException e) {
                    bill.setBillDate((Date) null);
                }
                Customer customer = new Customer();
                customer.setCusUID(cursor.getInt(4));
                customer.setCusPhoneNumber(cursor.getString(5));
                bill.setCustomer(customer);

                bills.add(bill);
            }
            return bills;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public int getCustomerBillCount(long customerId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + BILL_TABLE + " WHERE " + CUSTOMER_ID + "=?",
                    new String[]{valueOf(customerId)}
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getProfileBillCount(long profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + BILL_TABLE + " WHERE " + PROFILE_ID + "=?",
                    new String[]{valueOf(profileID)}
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getBillCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String countQuery = "SELECT  * FROM " + BILL_TABLE;
            Cursor cursor = null;

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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public void deleteProfile(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            db.delete(PROFILES_TABLE, selection,selectionArgs);

            String selectionCus = CUSTOMER_PROF_ID + "=?";
            String[] selectionArgsCus = new String[]{valueOf(id)};
            db.delete(CUSTOMER_TABLE, selectionCus,selectionArgsCus);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void deleteBill(BillModel billModel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            if(billModel !=null){
                long billId= billModel.getId();
                db.delete(BILL_TABLE,
                        "BILL_ID = ? ",
                        new String[]{Long.toString(billId)});
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }




    }


    public int deleteCustomer(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = CUSTOMER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(id)};
            return db.delete(CUSTOMER_TABLE, selection,selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return id;
    }


    public int getCustomerCount1() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = CUSTOMER_ID + "=?";
            //String[] selectionArgs = new String[]{valueOf(customerId)};
            int count = 0;
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + null,
                    null
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    public int getLoansCountTeller(int profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;
            String selection = LOAN_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + LOAN_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public int getLoansCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String countQuery = "SELECT  * FROM " + LOAN_TABLE;
            Cursor cursor = null;

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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public int getUsersCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String countQuery = "SELECT  * FROM " + PROFILES_TABLE;
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCustomersCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor=null;

            String countQuery = "SELECT  * FROM " + CUSTOMER_TABLE;
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }



    @SuppressLint("Recycle")
    public int getSOCountAdmin() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor=null;

            String countQuery = "SELECT  * FROM " + STANDING_ORDER_TABLE;

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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCustomersCountTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = CUSTOMER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + CUSTOMER_TABLE + " WHERE " + selection,
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
    public int getCustomerSOCountForDate(int customerID, String date) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = SO_CUS_ID + "=? AND " + SO_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
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
    public int getSOCountCustomer(int customerID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;

            String selection = SO_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public int getTxCountTeller(int profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int count = 0;
            String selection = TRANSACTION_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public int getCustomerMessagesCount(int customerID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MESSAGE_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + MESSAGE_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(MESSAGE_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    public int getProfileMessagesCount(int profileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MESSAGE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + MESSAGE_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(MESSAGE_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCustomerTotalPackageCount(int customerID) {
        try {
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


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCustomerTotalTXCount(int customerID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = TRANSACTION_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(TRANSACTION_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCustomerSOCount(int customerId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = SO_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerId)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(SO_START_DATE);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public int getCustomerLoanCount(int customerId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = LOAN_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerId)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + LOAN_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(LOAN_CUS_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    public int getProfileCount(int profileId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = PROFILE_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileId)};
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT (*) FROM " + PROFILES_TABLE + " WHERE " + selection,
                    selectionArgs
            );
            int count = 0;

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getColumnIndex(PROFILE_ID);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            db.close();
            return count;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return profileId;
    }


    protected boolean updateUserMessageState(int id) {
        return true;
    }


    public String getSpecificLoan(int loanId) {
        return null;
    }

    protected long insertUserAccount(String name, BigDecimal balance, int typeId) {
        return 0;
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


}
