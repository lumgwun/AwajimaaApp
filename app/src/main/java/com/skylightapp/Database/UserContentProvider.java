package com.skylightapp.Database;

import static com.skylightapp.Bookings.Bookings.BOOKING_TABLE;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.TripStopPoint.TRIP_STOP_POINT_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPES_TABLE;
import static com.skylightapp.Classes.AdminUser.ADMIN_TABLE;
import static com.skylightapp.Classes.AppCash.APP_CASH_TABLE;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_TABLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_LOCATION_TABLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_TABLE;
import static com.skylightapp.Classes.CustomerManager.WORKER_TABLE;
import static com.skylightapp.Classes.DailyAccount.DAILY_ACCOUNTING_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.ImportantDates.TABLE_REMINDERS;
import static com.skylightapp.Classes.Journey.JOURNEY_TABLE;
import static com.skylightapp.Classes.Loan.LOAN_TABLE;
import static com.skylightapp.Classes.LoanInterest.INTEREST_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_TABLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.Payee.PAYEES_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENTS_TABLE;
import static com.skylightapp.Classes.PaymentCode.CODE_TABLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_TABLE;
import static com.skylightapp.Classes.Profile.NIN_TABLE;
import static com.skylightapp.Classes.Profile.PASSWORD_TABLE;
import static com.skylightapp.Classes.Profile.PICTURE_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;
import static com.skylightapp.Classes.Profile.SPONSOR_TABLE;
import static com.skylightapp.Classes.StandingOrder.STANDING_ORDER_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCT_TABLE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_TABLE;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TABLE;
import static com.skylightapp.Classes.Transaction.GRP_TRANX_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_TABLE;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_TABLE;
import static com.skylightapp.Database.DBHelper.TAG;
import static com.skylightapp.Inventory.StockTransfer.T_STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.MapAndLoc.ERGeofenceResponse.GEOF_RESPONSE_TABLE;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CODE_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TABLE;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_TABLE22;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static com.skylightapp.MarketClasses.MarketStock.MARKET_STOCK_TABLE;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class UserContentProvider extends ContentProvider {
    public static final String TABLE_USER = "users";
    public static final String AUTHORITY = "com.awajima.UserContentProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int LOCATIONS = 1;
    private DBHelper dbHelper;
    private ProfDAO profDAO;
    private EmergReportDAO emergReportDAO;
    private EmergReportNextDAO emergReportNextDAO;
    private TaxiDriverDAO taxiDriverDAO;
    private TranXDAO tranXDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private WorkersDAO workersDAO;
    private BirthdayDAO birthdayDAO;
    private BizDealDAO bizDealDAO;
    private BizSubscriptionDAO subscriptionDAO;
    private TripDAO tripDAO;
    private BoatTripRouteDAO boatTripRouteDAO;
    private BoatTripSPDAO boatTripSPDAO;
    private BookingDAO bookingDAO;

    private MarketDAO marketDAO;
    private MarketBizDAO marketBizDAO;
    private MessageDAO messageDAO;
    private MarketStockDAO marketStockDAO;
    private MarketTranXDAO marketTranXDAO;
    private MBSupplierDAO mbSupplierDAO;
    private CusDAO cusDAO;
    private ChartDataDAO chartDataDAO;
    private CodeDAO codeDAO;
    private Customer_TellerDAO customerTellerDAO;
    private CustomerDailyReportDAO customerDailyReportDAO;
    private AcctDAO acctDAO;
    private AcctBookDAO acctBookDAO;
    private AdminUserDAO adminUserDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private AdminBDepositDAO adminBDepositDAO;
    private AppCashDAO appCashDAO;
    private AppPackageDAO appPackageDAO;
    private AwardDAO awardDAO;
    public static final int PROFILE_SURNAME_I = 51;
    public static final int PROFILE_FIRSTNAME_I = 52;
    public static final int PROFILE_EMAIL_I = 53;
    public static final int PROFILE_DOB_I = 54;
    public static final int PROFILE_ADDRESS_I = 55;
    public static final int PROFILE_GENDER_I = 56;
    public static final int PROFILE_PHONE_I = 57;
    public static final int PROFILE_ROLE_I = 58;
    public static final int PROFILE_DATE_JOINED_I = 59;
    public static final int PROFILE_NEXT_OF_KIN_I = 60;
    public static final int PROFILE_STATUS_I = 61;
    public static final int PROFILE_PASSWORD_I = 62;
    public static final int PROFILES_TABLE_INT = 63;
    public static final int PROFILE_UNIT_I = 64;
    public static final int PROFILE_WARD_I = 65;
    public static final int PROFILE_TOWN_I = 66;
    public static final int PROFILE_COUNTRY_I = 67;
    public static final int PROFILE_SPONSOR_ID_I = 68;
    public static final int PROFILE_ID_INT = 69;
    public static final int PICTURE_TABLE_INT = 70;
    public static final int PICTURE_URI_I = 71;
    public static final int PROFILE_USERNAME_I = 72;
    public static final int PASSWORD_I = 73;
    public static final int PASSWORD_ID_I = 74;
    public static final int PASSWORD_TABLE_I = 75;
    public static final int PROFILE_NIN_I = 76;
    public static final int PROFILE_STATE_I = 77;
    public static final int PROFILE_OFFICE_I = 78;
    public static final int PROFILE_PIC_ID_I = 79;
    public static final int PROFILE_CUS_ID_KEY_I = 80;
    public static final int CUS_ID_PIX_KEY_I = 81;
    public static final int CUS_ID_PASS_KEY_INT = 82;
    public static final int PROF_SPONSOR_ID_INT = 83;
    public static final int PROFID_FOREIGN_KEY_PIX_INT = 84;
    public static final int PROF_SPONSOR_KEY_INT = 85;
    public static final int PROF_REF_LINK_INT = 86;
    public static final int SPONSOR_TABLE_INT = 87;
    public static final int SPONSOR_TABLE_ID_INT = 88;
    public static final int SPONSOR_TABLE_CUS_ID_INT = 89;
    public static final int SPONSOR_TABLE_PROF_ID_INT = 90;
    public static final int SPONSOR_TABLE_PHONE_INT = 91;
    public static final int SPONSOR_TABLE_EMAIL_INT = 92;
    public static final int PICTURE_MARKET_ID_INT = 93;
    public static final int SPONSOR_REFERER_INT = 94;
    public static final int SPONSOR_REFERRER_CODE_INT = 95;
    public static final int SPONSOR_REFERRER_USER_INT = 96;
    public static final int SPONSOR_REF_COUNT_INT = 97;
    public static final int SPONSOR_REF_REWARD_COUNT_INT = 98;
    public static final int PROF_ID_FOREIGN_KEY_PASSWORD_INT = 99;
    public static final int PROF_BUSINESS_ID_INT = 100;
    public static final int PROF_MARKET_ID_INT = 101;
    public static final int PROF_ADMIN_TYPE_INT = 102;
    public static final int PROF_ROLE_TYPE_INT = 103;
    public static final int PROF_DB_ID_INT = 104;
    public static final int USER_TYPE_TABLE_INT = 105;
    public static final int USER_TYPE_ID_INT = 106;
    public static final int USER_TYPE_PROF_ID_INT = 107;
    public static final int USER_TYPE_CUS_ID_INT = 108;
    public static final int USER_TYPE_STRING_INT = 109;
    public static final int USER_TYPE_BIZ_ID_INT = 110;
    public static final int USER_TYPE_MARKET_ID_INT = 111;
    public static final int USER_TYPE_STATUS_INT = 112;


    public static final int ACCOUNTS_TABLE_INT = 113;
    public static final int ACCOUNT_BANK_INT = 114;
    public static final int ACCOUNT_NO_INT = 115;
    public static final int ACCOUNT_NAME_INT = 116;
    public static final int ACCOUNT_BALANCE_INT = 117;
    public static final int ACCOUNT_TYPE_INT = 118;
    public static final int ACCOUNT_EXPECTED_SAVINGS_INT = 119;
    public static final int ACCOUNT_SAVED_AMOUNT_INT = 120;
    public static final int BANK_ACCT_NO_INT = 121;
    public static final int BANK_ACCT_BALANCE_INT = 122;

    public static final int ACCOUNT_TYPES_TABLE_INT = 123;
    public static final int ACCOUNT_TYPE_INTEREST_INT = 124;
    public static final int ACCOUNT_TYPE_NAME_INT = 125;
    public static final int ACCOUNT_CURRENCY_INT = 126;
    public static final int ACCOUNT_TYPE_ID_INT = 127;
    public static final int ACCOUNT_CUS_ID_INT = 128;
    public static final int ACCOUNT_TYPE_NO_F_INT = 129;
    public static final int ACCOUNT_PROF_ID_INT = 130;
    public static final int ACCOUNT_TX_ID_INT = 131;
    public static final int ACCOUNT_MARKET_BUSINESS_ID_INT = 132;
    public static final int ACCOUNT_MARKET_ID_INT = 133;
    public static final int ACCOUNT_DB_ID_INT = 134;

    public static final int CUSTOMER_ID_INT = 135;
    public static final int CUSTOMER_SURNAME_INT = 136;
    public static final int CUSTOMER_FIRST_NAME_INT = 137;
    public static final int CUSTOMER_PHONE_NUMBER_INT = 138;
    public static final int CUSTOMER_EMAIL_ADDRESS_INT = 139;
    public static final int CUSTOMER_DOB_INT = 140;
    public static final int CUSTOMER_GENDER_INT = 141;
    public static final int CUSTOMER_ADDRESS_INT = 142;
    public static final int CUSTOMER_OFFICE_INT = 143;
    public static final int CUSTOMER_DATE_JOINED_INT = 144;
    public static final int CUSTOMER_USER_NAME_INT = 145;
    public static final int CUSTOMER_PASSWORD_INT = 146;
    public static final int CUSTOMER_NIN_INT = 147;
    public static final int CUSTOMER_STATUS_INT = 148;
    public static final int CUSTOMER_TABLE_INT = 149;

    public static final int CUSTOMER_LOCATION_TABLE_INT = 150;
    public static final int CUSTOMER_LOC_ID_INT = 151;
    public static final int CUSTOMER_PROF_ID_INT = 152;
    public static final int CUSTOMER_LONGITUDE_INT = 153;
    public static final int CUSTOMER_LATITUDE_INT = 154;
    public static final int CUSTOMER_LATLONG_INT = 155;
    public static final int CUSTOMER_CITY_INT = 156;
    public static final int CUSTOMER_WARD_INT = 157;
    public static final int CUS_LOC_CUS_ID_INT = 158;
    public static final int CUS_CUS_LOCID_INT = 159;
    public static final int CUS_BIZ_ID1_INT = 160;
    public static final int CUSTOMER_MARKET_ID_INT = 161;
    public static final int CUSTOMER_COUNTRY_INT = 162;
    public static final int CUS_DB_ID_INT = 163;
    public static final int CUSTOMER_ROLE_INT = 164;
    public static final int CUSTOMER_BIZ_STATUS_INT = 165;

    public static final int OFFICE_BRANCH_TABLE_INT = 166;
    public static final int OFFICE_BRANCH_ID_INT = 167;
    public static final int OFFICE_BRANCH_NAME_INT = 168;
    public static final int OFFICE_BRANCH_DATE_INT = 169;
    public static final int OFFICE_BRANCH_ADDRESS_INT = 170;
    public static final int OFFICE_BRANCH_APPROVER_INT = 171;
    public static final int OFFICE_BRANCH_STATUS_INT = 172;
    public static final int OFFICE_SUPERADMIN_ID_INT = 173;
    public static final int OFFICE_BRANCH_BIZ_ID_INT = 174;
    public static final int OFFICE_BRANCH_MARKET_ID_INT = 175;
    public static final int OFFICE_BRANCH_DB_ID_INT = 176;
    public static final int OFFICE_BRANCH_ROLE_INT = 177;

    public static final int NIN_TABLE_INT = 178;
    public static final int NIN_ID_INT = 179;
    public static final int NIN_PIX_INT = 180;
    public static final int NIN_NUMBER_INT = 181;
    public static final int NIN_TYPE_INT = 182;
    public static final int NIN_EXPIRYD_INT = 183;
    public static final int NIN_PROF_ID_INT = 184;
    public static final int NIN_CUS_ID_INT = 185;
    public static final int NIN_STATUS_INT = 186;
    public static final int NIN_APPROVER_INT = 187;
    public static final int NIN_APPROVING_OFFICE_INT = 188;
    public static final int NIN_QBID_INT = 189;

    public static final int MESSAGE_TABLE_INT = 190;
    public static final int MESSAGE_ID_INT = 191;
    public static final int MESSAGE_DETAILS_INT = 192;
    public static final int VIEWED_INT = 193;
    public static final int MESSAGE_TIME_INT = 194;
    public static final int MESSAGE_SENDER_INT = 195;
    public static final int MESSAGE_SENDEE_INT = 196;
    public static final int MESSAGE_PURPOSE_INT = 197;
    public static final int MESSAGE_ADMIN_NAME_INT = 198;
    public static final int MESSAGE_ADMIN_ID_INT = 199;
    public static final int MESSAGE_CUS_ID_INT = 200;
    public static final int MESSAGE_PROF_ID_INT = 201;
    public static final int MESSAGE_BRANCH_OFFICE_INT = 202;
    public static final int MESSAGE_PHONENO_INT = 203;
    public static final int MESSAGE_OTP_INT = 204;
    public static final int MESSAGE_BIZ_ID_INT = 205;
    public static final int MESSAGE_MARKET_ID_INT = 206;
    public static final int MESSAGE_DB_ID_INT = 207;

    public static final int CUSTOMER_TELLER_ID_INT = 208;

    public static final int CUSTOMER_TELLER_SURNAME_INT = 209;

    public static final int CUSTOMER_TELLER_FIRST_NAME_INT = 210;
    //@Ignore
    public static final int CUSTOMER_TELLER_PHONE_NUMBER_INT = 211;
    //@Ignore
    public static final int CUSTOMER_TELLER_EMAIL_ADDRESS_INT = 112;
    //@Ignore
    public static final int CUSTOMER_TELLER_DOB_INT = 213;
    //@Ignore
    public static final int CUSTOMER_TELLER_GENDER_INT = 214;
    //@Ignore
    public static final int CUSTOMER_TELLER_ADDRESS_INT = 215;
    //@Ignore
    public static final int CUSTOMER_TELLER_OFFICE_INT = 217;
    //@Ignore
    public static final int CUSTOMER_TELLER_DATE_JOINED_INT = 218;
    //@Ignore
    public static final int CUSTOMER_TELLER_USER_NAME_INT = 219;
    //@Ignore
    public static final int CUSTOMER_TELLER_PASSWORD_INT = 220;
    // @Ignore
    public static final int CUSTOMER_TELLER_NIN_INT = 221;
    //@Ignore
    public static final int CUSTOMER_TELLER_PIX_INT = 222;
    //@Ignore
    public static final int CUSTOMER_TELLER_STATUS_INT = 223;
    //@Ignore
    public static final int CUSTOMER_TELLER_PROF_ID_INT = 224;
    public static final int CUSTOMER_TELLER_BIZ_ID_INT = 225;
    public static final int CUSTOMER_TELLER_DB_ID_INT = 226;
    public static final int CUSTOMER_TELLER_MARKET_ID_INT = 227;

    public static final int TIMELINE_TABLE_INT = 228;
    public static final int TIMELINE_ID_INT = 229;
    public static final int TIMELINE_TITTLE_INT = 230;
    public static final int TIMELINE_DETAILS_INT = 231;
    public static final int TIMELINE_LOCATION_INT = 232;
    public static final int TIMELINE_TIME_INT = 233;
    public static final int TIMELINE_CUS_ID_INT = 234;
    public static final int TIMELINE_PROF_ID_INT = 235;
    public static final int TIMELINE_AMOUNT_INT = 236;
    public static final int TIMELINE_SENDING_ACCOUNT_INT = 237;
    public static final int TIMELINE_GETTING_ACCOUNT_INT = 238;
    public static final int TIMELINE_WORKER_NAME_INT = 239;
    public static final int TIMELINE_CLIENT_NAME_INT = 240;


    public static final int WORKER_TELLER_PROF_ID_INT = 241;
    public static final int CUSTOMER_TELLER_TABLE_INT = 242;
    public static final int WORKER_TABLE_INT = 243;
    public static final int WORKER_INT = 244;
    public static final int WORKER_ID_INT = 245;
    public static final int WORKER_DB_ID_INT = 246;
    public static final int WORKER_BIZ_ID_INT = 247;
    public static final int WORKER_BIZ_BRANCH_ID_INT = 248;


    public static final int ADMIN_ID_INT = 249;
    public static final int ADMIN_SURNAME_INT = 250;
    public static final int ADMIN_FIRST_NAME_INT = 251;
    public static final int ADMIN_PHONE_NUMBER_INT = 252;
    public static final int ADMIN_EMAIL_ADDRESS_INT = 253;
    public static final int ADMIN_DOB_INT = 254;
    public static final int ADMIN_GENDER_INT = 255;
    public static final int ADMIN_ADDRESS_INT = 256;
    public static final int ADMIN_OFFICE_INT = 257;
    public static final int ADMIN_DATE_JOINED_INT = 258;
    public static final int ADMIN_USER_NAME_INT = 259;
    public static final int ADMIN_PASSWORD_INT = 260;
    public static final int ADMIN_NIN_INT = 261;
    public static final int ADMIN_STATUS_INT = 262;
    public static final int ADMIN_PIX_INT = 263;
    public static final int ADMIN_PROFILE_ID_INT = 264;
    public static final int ADMIN_TABLE_INT = 265;
    public static final int ADMIN_BUSINESS_ID_INT = 266;
    public static final int ADMIN_MARKET_ID_INT = 267;
    public static final int ADMIN_MARKET_TYPE_INT = 268;
    public static final int ADMIN_DB_ID_INT = 269;



    public static final int DRIVER_TABLE_INT = 270;
    public static final int DRIVER_ID_INT = 271;
    public static final int DRIVER_DBID_INT = 272;
    public static final int DRIVER_NAME_INT = 273;
    public static final int DRIVER_PROF_ID_INT = 274;
    public static final int DRIVER_VEHICLE_INT = 275;
    public static final int DRIVER_TRIPS_INT = 276;
    public static final int DRIVER_REVENUE_INT = 277;
    public static final int DRIVER_STATUS_INT = 278;
    public static final int DRIVER_POSITION_INT = 279;
    public static final int DRIVER_JOINED_D_INT = 280;
    public static final int DRIVER_COMPLAINS_INT = 281;
    public static final int DRIVER_REPORTS_INT = 282;
    public static final int DRIVER_PICTURE_INT = 283;
    public static final int DRIVER_TYPE_INT = 284;


    public static final int TAXI_DRIVER_TABLE_INT = 285;
    public static final int TAXI_DRIVER_ID_INT = 286;
    public static final int TAXI_DRIVER_DBID_INT = 287;
    public static final int TAXI_DRIVER_AGE_INT = 288;
    public static final int TAXI_DRIVER_PROF_ID_INT = 289;
    public static final int TAXI_DRIVER_GENDER_INT = 290;
    public static final int TAXI_DRIVER_REVENUE_INT = 291;
    public static final int TAXI_DRIVER_CAR_TYPE_INT = 292;
    public static final int TAXI_DRIVER_STATUS_INT = 293;
    public static final int TAXI_DRIVER_PIX_INT = 294;
    public static final int TAXI_DRIVER_NIN_INT = 295;
    public static final int TAXI_DRIVER_LICENSE_INT = 296;
    public static final int TAXI_DRIVER_RATE_INT = 297;
    public static final int TAXI_DRIVER_JOINED_D_INT = 298;
    public static final int TAXI_DRIVER_PHONE_NO_INT = 299;
    public static final int TAXI_DRIVER_CURRENT_LATLNG_INT = 300;
    public static final int TAXI_DRIVER_CURRENT_TOWN_INT = 301;
    public static final int TAXI_DRIVER_NAME_INT = 302;
    public static final int TAXI_DRIVER_SURNAME_INT = 303;

    public static final int TAXI_TRIP_TABLE_INT = 304;
    public static final int TAXI_TRIP_ID_INT = 305;
    public static final int TAXI_TRIP_DBID_INT = 306;
    public static final int TAXI_TRIP_BOOKER_ID_INT = 307;
    public static final int TAXI_TRIP_DRIVER_ID_INT = 308;
    public static final int TAXI_TRIP_START_POINT_INT = 309;
    public static final int TAXI_TRIP_END_POINT_INT = 310;
    public static final int TAXI_TRIP_COST_INT = 311;
    public static final int TAXI_TRIP_DISTANCE_INT = 312;
    public static final int TAXI_TRIP_ADDRESS_INT = 313;
    public static final int TAXI_TRIP_COORDINATE_INT = 314;
    public static final int TAXI_TRIP_START_TIME_INT = 315;
    public static final int TAXI_TRIP_END_TIME_INT = 317;
    public static final int TAXI_TRIP_END_STATUS_INT = 318;
    public static final int TAXI_TRIP_PROMO_REWARD_INT = 319;


    public static final int A_TRIP_TABLE_INT = 320;
    public static final int A_TRIP_ID_INT = 321;
    public static final int A_TRIP_DBID_INT = 322;
    public static final int A_TRIP_PROF_ID_INT = 323;
    public static final int A_TRIP_STATE_INT = 324;
    public static final int A_TRIP_AMOUNT_ADULT_INT = 325;
    public static final int A_TRIP_AMT_CHILDREN_INT = 326;
    public static final int A_TRIP_STATUS_INT = 327;
    public static final int A_TRIP_NO_OF_SIT_INT = 328;
    public static final int A_TRIP_STATION_INT = 329;
    public static final int A_TRIP_DEST_NAME_INT = 330;
    public static final int A_TRIP_TOTAL_AMT_INT = 331;
    public static final int A_TRIP_START_TIME_INT = 333;
    public static final int A_TRIP_ENDT_INT = 334;
    public static final int A_TRIP_DATE_INT = 335;
    public static final int A_TRIP_TAKE_OFF_POINT_INT = 336;
    public static final int A_TRIP_TAKE_OFF_LATLNG_INT = 337;
    public static final int A_TRIP_NO_OF_BOATS_INT = 338;
    public static final int A_TRIP_TYPE_OF_BOAT_INT = 339;
    public static final int A_TRIP_TYPE_INT = 340;
    public static final int A_TRIP_CURRENCY_INT = 341;
    public static final int A_TRIP_BIZ_ID_INT = 342;
    public static final int A_TRIP_COUNTRY_INT = 343;
    public static final int A_TRIP_DRIVER_ID_INT = 344;


    public static final int TRIP_BOOKING_TABLE_INT = 345;
    public static final int TRIP_BOOKING_ID_INT = 346;
    public static final int TRIP_BOOKING_DBID_INT = 347;
    public static final int TRIP_BOOKING_PROF_ID_INT = 348;
    public static final int TRIP_BOOKING_TRIP_ID_INT = 349;
    public static final int TRIP_BOOKING_DEST_INT = 350;
    public static final int TRIP_BOOKING_DATE_INT = 351;
    public static final int TRIP_BOOKING_MOP_INT = 352;
    public static final int TRIP_BOOKING_AMT_INT = 353;
    public static final int TRIP_BOOKING_CHILDREN_INT = 354;
    public static final int TRIP_BOOKING_ADULT_INT = 355;
    public static final int TRIP_BOOKING_LUGGAGE_INT = 356;
    public static final int TRIP_BOOKING_NAME_INT = 357;
    public static final int TRIP_BOOKING_NIN_ID_INT = 358;
    public static final int TRIP_BOOKING_SLOTS_INT = 359;
    public static final int TRIP_BOOKING_LAMT_INT = 360;
    public static final int TRIP_BOOKING_TYPEOS_INT = 361;
    public static final int TRIP_BOOKING_STATUS_INT = 362;
    public static final int TRIP_BOOKING_TX_ID_INT = 363;
    public static final int TRIP_BOOKING_CURRENCY_INT = 364;
    public static final int TRIP_BOOKING_STATE_INT = 365;
    public static final int TRIP_BOOKING_COUNTRY_INT = 366;
    public static final int TRIP_BOOKING_FROM_INT = 367;
    public static final int TRIP_BOOKING_OFFICE_INT = 368;


    public static final int TRIP_B_NIN_TABLE_INT = 369;
    public static final int TRIP_B_NIN_ID_INT = 370;
    public static final int TRIP_B_NIN_INT = 371;
    public static final int TRIP_B_TB_ID_INT = 372;
    public static final int TRIP_B_TRIP_ID_INT = 373;
    public static final int TRIP_B_NIN_TYPE_INT = 374;
    public static final int TRIP_B_NIN_EXPIRYD_INT = 375;
    public static final int TRIP_B_NIN_STATUS_INT = 376;




    public static final int TRIP_STOP_POINT_TABLE_INT = 377;
    public static final int TRIP_STOP_POINT_ID_INT = 378;
    public static final int TRIP_STOP_POINT_DBID_INT = 379;
    public static final int TSP_TRIP_ID_INT = 380;
    public static final int TSP_AMOUNT_INT = 381;
    public static final int TSP_NAME_INT = 382;
    public static final int TSP_LATLNG_INT = 383;
    public static final int TSP_STATUS_INT = 384;
    public static final int TSP_BIZ_ID_INT = 385;
    public static final int TSP_STATE_INT = 386;
    public static final int TSP_LAT_INT = 387;
    public static final int TSP_LNG_INT = 388;

    public static final int MARKET_BIZ_TABLE_INT = 389;
    public static final int MARKET_BIZ_ID_INT = 390;
    public static final int MARKET_BIZ_NAME_INT = 391;
    public static final int MARKET_BIZ_REG_NO_INT = 392;
    public static final int MARKET_BIZ_PHONE_NO_INT = 393;
    public static final int MARKET_BIZ_EMAIL_INT = 394;
    public static final int MARKET_BIZ_ADDRESS_INT = 395;
    public static final int MARKET_BIZ_TYPE_INT = 396;
    public static final int MARKET_BIZ_BRANDNAME_INT = 397;
    public static final int MARKET_BIZ_STATE_INT = 398;
    public static final int MARKET_BIZ_PIX_INT = 399;
    public static final int MARKET_BIZ_STATUS_INT = 400;
    public static final int MARKET_BIZ_PROF_ID_INT = 401;
    public static final int MARKET_BIZ_COUNTRY_INT = 402;
    public static final int MARKET_BIZ_CONTINENT_INT = 403;
    public static final int MARKET_BIZ_MARKET_ID_INT = 404;
    public static final int MARKET_BIZ_CUSTOMER_ID_INT = 405;
    public static final int MARKET_BIZ_CAC_DATE_INT = 406;
    public static final int MARKET_BIZ_DESC_INT = 407;
    public static final int MARKET_BIZ_JOINED_D_INT = 408;


    public static final int MARKET_TABLE_INT = 409;
    public static final int MARKET_TOWN_INT = 410;
    public static final int MARKET_LGA_INT = 411;
    public static final int MARKET_NAME_INT = 412;
    public static final int MARKET_STATE_INT = 413;
    public static final int MARKET_TYPE_INT = 414;
    public static final int MARKET_ID_INT = 415;
    public static final int MARKET_COUNTRY_INT = 416;
    public static final int MARKET_COMMODITY_COUNT_INT = 417;
    public static final int MARKET_USER_COUNT_INT = 418;
    public static final int MARKET_LOGO_INT = 419;
    public static final int MARKET_STATUS_INT = 420;
    public static final int MARKET_ACCOUNT_ID_INT = 421;
    public static final int MARKET_PROF_ID_INT = 422;
    public static final int MARKET_REVENUE_INT = 423;
    public static final int MARKET_ADDRESS_INT = 424;
    public static final int MARKET_LAT_INT = 425;
    public static final int MARKET_LNG_INT = 426;
    public static final int MARKET_DBID_INT = 427;
    public static final int MARKET_CUS_ID_INT = 428;
    public static final int MARKET_DATEJOINED_INT = 429;
    public static final int MARKET_MAP_ZOOM_INT = 430;

    public static final int PAYMENTS_TABLE_INT = 431;
    public static final int PAYMENT_ID_INT = 432;
    public static final int PAYMENT_DATE_INT = 433;
    public static final int PAYMENTTYPE_INT = 434;
    public static final int PAYMENT_OFFICE_INT = 435;
    public static final int PAYMENT_AMOUNT_INT = 436;
    public static final int PAYMENT_APPROVAL_DATE_INT = 437;
    public static final int PAYMENT_APPROVER_INT = 438;
    public static final int PAYMENT_CODE_INT = 439;
    public static final int PAYMENT_ACCOUNT_INT = 440;
    public static final int PAYMENT_ACCOUNT_TYPE_INT = 441;
    public static final int PAYMENT_STATUS_INT = 442;
    public static final int PAYMENT_PROF_ID_INT = 443;
    public static final int PAYMENT_ADMIN_ID_INT = 444;
    public static final int PAYMENT_CUS_ID_INT = 445;
    public static final int PAYMENT_DB_ID_INT = 446;

    public static final int CODE_OWNER_INT = 447;
    public static final int CODE_TABLE_INT = 448;
    public static final int CODE_OWNER_PHONE_INT = 449;
    public static final int CODE_ID_INT = 450;
    public static final int CODE_PIN_INT = 451;
    public static final int CODE_DATE_INT = 452;
    public static final int CODE_STATUS_INT = 453;
    public static final int CODE_MANAGER_INT = 454;
    public static final int CODE_CUS_ID_INT = 455;
    public static final int CODE_PROFILE_ID_INT = 456;
    public static final int CODE_REPORT_NO_INT = 457;

    public static final int DOCUMENT_ID_INT = 458;
    public static final int DOCUMENT_TITLE_INT = 459;
    public static final int DOCUMENT_URI_INT = 460;
    public static final int DOCUMENT_STATUS_INT = 461;
    public static final int DOCUMENT_PAYMENT_METHOD_INT = 462;
    public static final int DOCUMENT_MANAGER_INT = 463;
    public static final int DOCUMENT_TABLE_INT = 464;
    public static final int DOCUMENT_PROF_ID_INT = 465;
    public static final int DOCUMENT_CUS_ID_INT = 466;
    public static final int DOCUMENT_REPORT_NO_INT = 467;


    public static final int STANDING_ORDER_TABLE_INT = 468;
    public static final int SO_ID_INT = 469;
    public static final int SO_DAILY_AMOUNT_INT = 470;
    public static final int SO_EXPECTED_AMOUNT_INT = 471;
    public static final int SO_RECEIVED_AMOUNT_INT = 472;
    public static final int SO_TOTAL_DAYS_INT = 473;
    public static final int SO_DAYS_REMAINING_INT = 474;
    public static final int SO_AMOUNT_DIFF_INT = 475;
    public static final int SO_STATUS_INT = 476;
    public static final int SO_ACCT_NO_INT = 477;
    public static final int SO_START_DATE_INT = 478;
    public static final int SO_END_DATE_INT = 479;
    public static final int SO_CUS_ID_INT = 480;
    public static final int SO_PROF_ID_INT = 481;
    public static final int SO_APPROF_DATE_INT = 482;


    public static final int SO_ACCT_TABLE_INT = 483;
    public static final int SO_ACCOUNT_NO_INT = 484;
    public static final int SO_ACCOUNT_NAME_INT = 485;
    public static final int SO_ACCOUNT_BALANCE_INT = 486;
    public static final int SO_ACCT_CUS_ID_INT = 487;
    public static final int SO_ACCT_PROF_ID_INT = 488;
    public static final int TRANSACTIONS_TABLE_INT = 489;
    public static final int TRANSACTION_ID_INT = 490;

    //public static final String TIMESTAMP = "timestamp";
    public static final int TRANSACTION_SENDING_ACCT_INT = 491;
    public static final int TRANSACTION_DEST_ACCT_INT = 492;
    public static final int TRANSACTION_PAYEE_INT = 493;
    public static final int TRANSACTION_PAYER_INT = 494;
    public static final int TRANSACTION_STATUS_INT = 495;
    public static final int TRANS_TYPE_INT = 496;
    public static final int TRANSACTIONS_TYPE_INT = 496;
    public static final int TRANSACTION_AMOUNT_INT = 497;
    public static final int TRANSACTION_DATE_INT = 498;
    //public static final String TABLE_USER_TRANSACTIONS = "users_transactions";
    public static final int TRANSACTION_OFFICE_BRANCH_INT = 499;
    public static final int TRANSACTION_APPROVER_INT = 500;
    public static final int TRANSACTION_APPROVAL_DATE_INT = 501;
    public static final int TRANSACTION_METHOD_OF_PAYMENT_INT = 502;
    public static final int TRANSACTION_PROF_ID_INT = 503;
    public static final int TRANSACTION_CUS_ID_INT = 504;
    public static final int TRANSACTION_ACCT_ID_INT = 505;
    public static final int TRANSACTION_BIZ_ID_INT = 506;
    public static final int TRANSACTION_MARKET_ID_INT = 507;
    public static final int TRANSACTION_DB_ID_INT = 508;

    public static final int GRP_TRANX_TYPE_INT = 509;
    public static final int GRP_TRANX_TABLE_INT = 510;
    public static final int GRP_TRANX_ID_INT = 511;
    public static final int GRP_TRANX_PROF_ID_INT = 512;
    public static final int GRP_TRANX_DB_ID_INT = 513;
    public static final int GRP_PAYMENT_METHOD_INT = 514;
    public static final int GRP_TRANX_AMT_INT = 515;
    public static final int GRP_TRANX_DATE_INT = 516;
    public static final int GRP_TRANX_SENDING_ACCT_INT = 517;
    public static final int GRP_TRANX_RECEIVING_ACCT_INT = 518;
    public static final int GRP_TRANX_STATUS_INT = 519;
    public static final int GRP_TRANX_ACCT_ID_INT = 520;



    public static final int CONTACTS_LOOKUP_PHOTO = 60;



    static {

        uriMatcher.addURI(AUTHORITY, PROFILES_TABLE, PROFILES_TABLE_INT);

        uriMatcher.addURI(AUTHORITY, PROFILES_TABLE + "/#",
                PROF_DB_ID_INT);
        uriMatcher.addURI(AUTHORITY, PASSWORD_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, PICTURE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, SPONSOR_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, NIN_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, DRIVER_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, BOAT_TRIP_ROUTE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, TRIP_STOP_POINT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, ACCOUNTS_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, ACCOUNT_TYPES_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, ADMIN_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, APP_CASH_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, BIRTHDAY_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, BOOKING_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, CUSTOMER_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, CUSTOMER_LOCATION_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, DAILY_REPORT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, CUSTOMER_TELLER_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, WORKER_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, DAILY_ACCOUNTING_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, GRP_ACCT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, GRP_PROFILE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, GROUP_SAVINGS_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, TABLE_REMINDERS, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, JOURNEY_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, LOAN_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, INTEREST_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, MESSAGE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, OFFICE_BRANCH_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, PAYEES_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, PAYMENTS_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, CODE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, DOCUMENT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, STANDING_ORDER_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, SO_ACCT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, TELLER_REPORT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, TIMELINE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, TRANSACTIONS_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, GRP_TRANX_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, TANSACTION_EXTRA_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, SUPER_ADMIN_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, STOCKS_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, T_STOCKS_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, EMERGENCY_REPORT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, EMERGENCY_NEXT_REPORT_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, RESPONSE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, GEOF_RESPONSE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, BIZ_DEAL_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, BIZ_DEAL_CODE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, BUSINESS_TABLE22, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, MARKET_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, PACKAGE_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, MARKET_BIZ_SUB_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, SUPPLIER_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, MARKET_BIZ_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, MARKET_STOCK_TABLE, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
        //uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH_SHORTCUT);

        //uriMatcher.addURI(AUTHORITY, "provider_status", PROVIDER_STATUS);

    }
    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);


    public UserContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PROFILES_TABLE_INT:
                return insertProfile(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertProfile(Uri uri, ContentValues values) {

        String userName = values.getAsString(PROFILE_USERNAME);
        if (userName == null) {
            throw new IllegalArgumentException("UserName is required");
        }
        String firstName = values.getAsString(PROFILE_FIRSTNAME);
        if (firstName == null) {
            throw new IllegalArgumentException("FirstName is required");
        }

        String surName = values.getAsString(PROFILE_SURNAME);
        if (surName == null) {
            throw new IllegalArgumentException("SurName is required");
        }
        String gender = values.getAsString(PROFILE_GENDER);
        if (gender == null) {
            throw new IllegalArgumentException("Gender is required");
        }

        Integer phoneNo = values.getAsInteger(PROFILE_PHONE);
        if (phoneNo != null && phoneNo < 10) {
            throw new IllegalArgumentException("Phone Number is required");
        }
        String dob = values.getAsString(PROFILE_DOB);
        if (dob == null) {
            throw new IllegalArgumentException("Date of Birth is required");
        }
        String password = values.getAsString(PROFILE_PASSWORD);
        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }
        String state = values.getAsString(PROFILE_STATE);
        if (state == null) {
            throw new IllegalArgumentException("State is required");
        }
        String office = values.getAsString(PROFILE_OFFICE);
        if (office == null) {
            throw new IllegalArgumentException("Office is required");
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = database.insert(PROFILES_TABLE, null, values);
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }



    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());

        messageDAO = new MessageDAO(getContext());
        awardDAO = new AwardDAO(getContext());
        adminBDepositDAO = new AdminBDepositDAO(getContext());
        adminBalanceDAO = new AdminBalanceDAO(getContext());
        adminUserDAO = new AdminUserDAO(getContext());
        acctBookDAO = new AcctBookDAO(getContext());
        acctDAO = new AcctDAO(getContext());
        customerTellerDAO = new Customer_TellerDAO(getContext());
        codeDAO = new CodeDAO(getContext());
        cusDAO = new CusDAO(getContext());
        mbSupplierDAO = new MBSupplierDAO(getContext());
        marketTranXDAO = new MarketTranXDAO(getContext());
        marketDAO = new MarketDAO(getContext());
        marketBizDAO = new MarketBizDAO(getContext());
        tranXDAO = new TranXDAO(getContext());
        timeLineClassDAO = new TimeLineClassDAO(getContext());
        workersDAO = new WorkersDAO(getContext());
        tripDAO = new TripDAO(getContext());
        profDAO = new ProfDAO(getContext());
        emergReportDAO = new EmergReportDAO(getContext());
        emergReportNextDAO = new EmergReportNextDAO(getContext());
        taxiDriverDAO = new TaxiDriverDAO(getContext());
        birthdayDAO = new BirthdayDAO(getContext());
        bizDealDAO = new BizDealDAO(getContext());
        subscriptionDAO = new BizSubscriptionDAO(getContext());
        boatTripRouteDAO = new BoatTripRouteDAO(getContext());
        boatTripSPDAO = new BoatTripSPDAO(getContext());
        bookingDAO = new BookingDAO(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case PROFILES_TABLE_INT:
                cursor = database.query(PROFILES_TABLE, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case PROF_DB_ID_INT:
                selection = PROFILE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PROFILES_TABLE, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor.
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /*

    public static final String REPORT_NUMBER_OF_DAYS_SO_FAR = "report_no_of_days_so_far";
    public static final String REPORT_AMOUNT_COLLECTED_SO_FAR = "report_amount_so_far";

    public static final String DAILY_REPORT_TABLE = "report_Cdaily_s";
    public static final String REPORT_PACKAGE = "report_Ppackage";
    public static final String REPORT_ID = "report_serial_number";
    public static final String REPORT_AMOUNT = "report_Aamount";
    public static final String REPORT_NUMBER_OF_DAYS = "report_Ddays";
    public static final String REPORT_DATE = "report_Ddate";
    public static final String REPORT_TOTAL = "report_Ttotal";
    public static final String REPORT_DAYS_REMAINING = "report_Ddays_remaining";
    public static final String REPORT_AMOUNT_REMAINING = "report_Aamount_remaining";
    public static final String REPORT_COUNT = "report_count";
    public static final String REPORT_OFFICE_BRANCH = "report_Office_Branch";
    public static final String REPORT_STATUS = "report_Sstatus";
    public static final String REPORT_CODE = "report_SsavingsCode";
    public static final String REPORT_PROF_ID_FK = "report_Pprofile_ID_Fk";
    public static final String REPORT_CUS_ID_FK = "report_Ccus_ID_FK";
    public static final String REPORT_PACK_ID_FK = "report_ID_FK";
    public static final String REPORT_ACCOUNT_NO_FK = "report_AcctNo_FK";





    public static final String BIRTHDAY_STATUS = "bbirthday_status";
    public static final String BIRTHDAY_ID = "bbirth_id";
    public static final String BIRTHDAY_DAYS_REMAINING = "bdays_remaining";
    public static final String BIRTHDAY_DAYS_BTWN = "bdays_between";

    public static final String BIRTHDAY_TABLE = "birthday_table";
    private static final String JSON_NAME = "name";
    public static final String B_FIRSTNAME = "b_FirstName";
    public static final String B_SURNAME = "b_Surname";
    public static final String B_EMAIL = "b_Email";
    public static final String B_PHONE = "b_Phone";
    public static final String B_DOB = "b_DOb";
    public static final String B_PROF_ID = "b_Prof_ID";
    private static final String JSON_DATE = "bdate";
    private static final String JSON_YEAR = "byear";
    private static final String JSON_REMIND = "bremind";
    private static final String JSON_UID = "buid";
    private static final String JSON_SHOW_YEAR = "bshow_year";
    public static final String B_DB_ID = "b_DB_ID";


    public static final String BOOKING_TABLE = "booking_table";
    public static final String BOOKING_STATUS = "booking_status";
    public static final String BOOKING_ID = "booking_id";
    public static final String BOOKING_TITTLE = "booking_tittle";
    public static final String BOOKING_CLIENT_NAME = "booking_name";
    public static final String BOOKING_DATE = "booking_date";
    public static final String BOOKING_LOCATION = "booking_location";
    public static final String BOOKING_PROF_ID = "booking_Prof_ID";
    public static final String BOOKING_CUS_ID = "booking_Cus_ID";
    public static final String BOOKING_OCCURENCE_NO = "booking_occuring_No";
    public static final Boolean ITISRECCURRING = false;




    public static final String DAILY_ACCOUNTING_TABLE = "daily_account_table";
    public static final String ID_DA = "id_DA";
    public static final String CREATE_TIME_DA = "create_timeDA";
    public static final String AMOUNT_DA = "amountDA";
    public static final String IS_INCOME_DA = "is_incomeDA";
    public static final String CONTENT_DA = "contentDA";
    public static final String CURRENCY_TYPE_DA = "currency_typeDA";
    public static final String STATUS_DA = "status_DA";
    public static final String DA_PROF_ID = "id_DA_Prof";
    public static final String DA_CUS_ID = "id_DA";
    public static final String DA_ACCT_ID = "id_DA_Acct_ID";



    public static final String KEY_ID77777 = "id";
    public static final String KEY_TITLE_R = "title";
    public static final String KEY_DATE_R = "date";
    public static final String KEY_TIME_R = "time";
    public static final String KEY_REPEAT = "repeat";
    public static final String KEY_REPEAT_NO = "repeat_no";
    public static final String KEY_REPEAT_TYPE = "repeat_type";
    public static final String KEY_ACTIVE = "active";

    public static final String IMPORTANT_DATE_TABLE = "dates_table";
    public static final String IMPORTANT_DATE_ID = "id";
    public static final String IMPORTANT_DATE_TITLE = "title";
    public static final String IMPORTANT_DATE = "date";
    private static final String NAME_33 = "name33";
    private static final String DATE_33 = "date33";
    private static final String YEAR_33 = "year33";
    private static final String REMIND_33 = "remind33";
    private static final String UID_44 = "uid44";
    private static final String SHOW_YEAR_44 = "show_year44";
    public static final String TABLE_REMINDERS = "ReminderTable";

    public static final String DESTINATION_JOURNEY = "destinationJ";
    public static final String JOURNEY_TABLE = "journey_table";
    public static final String START_DATE_JOURNEY = "start_dateJourney";
    public static final String END_DATE_JOURNEY = "end_dateJourney";
    public static final String TOTAL_AMOUNT_JOURNEY = "total_amountJourney";
    public static final String PERSON_JOURNEY = "personJourney";
    public static final String JOURNEY_ID = "journey_id_Journey";
    public static final String JOURNEY_PROF_ID = "journey_id_Prof";
    public static final String JOURNEY_JADCCT_ID = "journey_id_JAcct";
    public static final String JOURNEY_CUS_ID = "journey_id_Cus";
    public static final String STATUS_JOURNEY = "status_Journey";


    public static final String JOURNEY_ACCOUNT_TABLE = "journey_account_table";
    public static final String JOURNEY_ACCT_PERSON = "personJAcct";
    public static final String JOURNEY_ACCT_ID = "journey_Acct_id";
    public static final String JOURNEY_ACCT_CONTENT = "journey_Acct_id";
    public static final String JOURNEY_ACCT_AMOUNT = "journey_Acct_id";
    public static final String JOURNEY_ACCT_CURRENCY = "journey_Acct_id";
    public static final String JOURNEY_ACCT_CREATED_TIME = "journey_Acct_Time";
    public static final String JOURNEY_ACCT_DACCT_ID = "journey_Acct_Acct_ID";
    public static final String JOURNEY_ACCT_PROF_ID = "journey_Acct_Prof_ID";
    public static final String JOURNEY_ACCT_CUS_ID = "journey_Acct_Cus_ID";
    public static final String JOURNEY_ACCT_STATUS = "journey_Acct_Status";

    public static final String LOAN_ID = "loan_id";
    public static final String LOAN_TYPE = "loanType";
    public static final String LOAN_AMOUNT = "l_amount";
    public static final String LOAN_INTEREST = "l_interest";
    public static final String LOAN_FIXED_PAYMENT = "fixed_payment";
    public static final String LOAN_TOTAL_INTEREST = "l_total_interests";
    public static final String LOAN_DOWN_PAYMENT = "down_payment";
    public static final String LOAN_DISPOSABLE_COM = "disposable_commission";
    public static final String LOAN_MONTHLY_COM = "monthly_commission";
    public static final String LOAN_BALANCE = "residue_payment";
    public static final String LOAN_DATE = "l_date";
    public static final String LOAN_START_DATE = "loan_start_date";
    public static final String LOAN_END_DATE = "loan_end_date";
    public static final String LOAN_STATUS = "loan_status";
    public static final String LOAN_DURATION = "loan_duration";
    public static final String LOAN_ACCT_NAME = "loan_cus";
    public static final String LOAN_CODE = "loan_code";
    public static final String LOAN_TABLE = "loan_table";
    public static final String LOAN_CUS_ID = "loan_Cus_id";
    public static final String LOAN_PACK_ID = "loan_Package_id";
    public static final String LOAN_PROF_ID = "loan_Prof_id";
    public static final String LOAN_ACCT_NO = "loan_Acct_No";
    public static final String LOAN_BANK_ACCT_NO = "loan_BankAcct_No";
    public static final String LOAN_DB_NO = "loan_DB_No";


    public static final String INTEREST_ID = "int_id";
    public static final String INTEREST_RATE = "i_rate";
    public static final String INTEREST_TABLE = "interest_table";




    public static final String PAYEES_TABLE = "payees_table";

    public static final String PAYEE_ID = "payee_id";
    public static final String PAYEE_NAME = "payee_name";
    public static final String PAYEE_CUS_ID = "payee_Cus_ID";
    public static final String PAYEE_PROF_ID = "payee_Prof_ID";
    public static final String PAYEE_AMOUNT = "payee_Amount";
    public static final String PAYEE_DB_ID = "payee_DB_ID";



    public static final String TELLER_REPORT_TABLE = "tellerReportTable";
    public static final String TELLER_REPORT_ID = "teller_report_id";
    public static final String TELLER_REPORT_DATE = "t_R_Date";
    public static final String TELLER_REPORT_AMOUNT_SUBMITTED = "t_R_Amt_Submitted";
    public static final String TELLER_REPORT_NO_OF_SAVINGS = "t_R_No_Of_Savings";
    public static final String TELLER_REPORT_BALANCE = "t_R_Balance";
    public static final String TELLER_REPORT_AMT_PAID = "t_R_Amt_Paid";
    public static final String TELLER_REPORT_EXPECTED_AMT = "t_R_Expected_Amt";

    public static final String TELLER_REPORT_STATUS = "t_R_Status";
    public static final String TELLER_REPORT_BRANCH = "t_R__Branch";
    public static final String TELLER_REPORT_ADMIN = "t_R__Manager";
    public static final String TELLER_REPORT_MARKETER = "t_R__Marketer";
    public static final String TELLER_REPORT_COMMENT = "t_R__Comment";
    public static final String TELLER_REPORT_PROF_ID = "t_R__ProfID";
    public static final String TELLER_REPORT_APPROVAL_DATE = "t_R_ApprovalDate";
    public static final String TELLER_REPORT_ADMIN_ID = "t_R_AdminID";
    public static final String TELLER_REPORT_OFFICE_ID = "t_R_OfficeID";
    public static final String TELLER_REPORT_NO_OF_CUS = "t_R_No_Of_Cus";
    public static final String TELLER_REPORT_BIZ_NAME = "t_R_Biz_Name";




    public static final String BDEAL_PARTNER_TABLE = "biz_Deal_Partner_Table";
    public static final String BDEAL_PARTNER_DBID = "biz_Deal_Partner_DB_id";
    public static final String BDEAL_PARTNER_NAME = "biz_Deal_PartnerName";
    public static final String BDEAL_PARTNER_REG_NO = "biz_Deal_Partner_RegNo";
    public static final String BDEAL_PARTNER_BRAND_NAME = "biz_Deal_P__BrandName";
    public static final String BDEAL_PARTNER_MARKET_NAME = "biz_Deal_Partner_marketName";
    public static final String BDEAL_PARTNER_GENDER = "biz_Deal_Partner_Gender";
    public static final String BDEAL_PARTNER_STATUS = "biz_Deal_Partner_Status";
    public static final String BDEAL_PARTNER_COUNTRY = "biz_Deal_Partner_Country";
    public static final String BDEAL_PARTNER_PIX = "biz_Deal_Partner_Pix";
    public static final String BDEAL_PARTNER_PROF_ID = "biz_Deal_Partner_Prof_ID";
    public static final String BDEAL_PARTNER_QUSER_ID = "biz_Deal_Partner_Quser_ID";
    public static final String BDEAL_PARTNER_RATING = "biz_Deal_Partner_Rating";
    public static final String BDEAL_PARTNER_ID = "biz_Deal_Partner_ID";*/



}