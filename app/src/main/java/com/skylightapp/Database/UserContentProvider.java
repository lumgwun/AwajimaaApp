package com.skylightapp.Database;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class UserContentProvider extends ContentProvider {
    public static final String TABLE_USER = "users";
    public static final String AUTHORITY =
            "com.awajima.UserContentProvider";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + PROFILES_TABLE);

    private static final UriMatcher uriMatcher ;
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
    private BoatTripDAO boatTripDAO;
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

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "locations", LOCATIONS);
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
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
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
        boatTripDAO = new BoatTripDAO(getContext());
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
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}