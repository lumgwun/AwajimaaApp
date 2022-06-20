package com.skylightapp.Classes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Interfaces.AccountDao;
import com.skylightapp.Interfaces.AdminBankDepositDao;
import com.skylightapp.Interfaces.CustomerDao;
import com.skylightapp.Interfaces.EmergencyReportDao;
import com.skylightapp.Interfaces.PasswordHelpersDao;
import com.skylightapp.Interfaces.PaymentDocDao;
import com.skylightapp.Interfaces.ProfileDao;
import com.skylightapp.Interfaces.SkyLightPackageDao;
import com.skylightapp.Interfaces.SkylightCashDao;
import com.skylightapp.Interfaces.StandingOrderAcctDao;
import com.skylightapp.Interfaces.StandingOrderDao;
import com.skylightapp.Interfaces.TellerCashDao;
import com.skylightapp.Interfaces.TellerReportDao;
import com.skylightapp.Interfaces.TimeLineDao;
import com.skylightapp.Interfaces.TransactionDao;
import com.skylightapp.SuperAdmin.AdminBalance;
import com.skylightapp.SuperAdmin.SuperCash;
import com.skylightapp.Tellers.TellerCash;

import static com.skylightapp.Database.DBHelper.DATABASE_VERSION;

@Database(entities = {Profile.class}, version = DATABASE_VERSION)
@TypeConverters({SourceTypeConverter.class})
public abstract class RoomController extends RoomDatabase {
    private static RoomController instance;
    public abstract ProfileDao roomProfileTableDao();
    public ProfileDao profileDao;
    public CustomerDao customerDao;
    public TellerReportDao tellerReportDao;
    public AdminBankDepositDao abdDao;
    public TransactionDao txDao;
    public com.skylightapp.Interfaces.EmergencyReportDao EmergencyReportDao;
    public SkyLightPackageDao skyLightPackageDao;
    public SkylightCashDao skylightCashDao;
    public AccountDao accountDao;
    public StandingOrderDao standingOrderDao;
    public StandingOrderAcctDao standingOrderAcctDao;
    public PasswordHelpersDao passwordHelpersDao;
    public TimeLineDao timeLineDao;
    public TellerCashDao tellerCashDao;
    public PaymentDocDao paymentDocumentDao;
    /*public MessageDao messageDao;
    public Item_PurchaseDao item_purchaseDao;
    public CustomerDailyReportDao customerDailyReportDao;
    public CustomerManagerDao customerManagerDao;
    public AdminUserDao adminUserDao;
    public AdminBalanceDao adminBalanceDao;
    public SuperCashDao superCashDao;
    public PaymentDao paymentDao;
    public PaymentCodeDao paymentCodeDao;
    public LoanDao loanDao;
    public TransactionGrantingDao transactionGrantingDao;
    public PayeeDao payeeDao;*/




    public static synchronized RoomController getInstance(Context context) {

        if (instance == null) {
            instance =

                    Room.databaseBuilder(context.getApplicationContext(),
                            RoomController.class, "SkylightCoop")

                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)

                            .build();
        }

        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(RoomController instance) {
            ProfileDao profileDao = instance.ProfileDao();
            CustomerDao customerDao = instance.CustomerDao();
            TellerReportDao tellerReportDao = instance.TellerReportDao();
            AdminBankDepositDao abdDao = instance.AdminBankDepositDao();
            TransactionDao txDao = instance.TransactionDao();
            EmergencyReportDao EmergencyReportDao = instance.EmergencyReportDao();
            SkyLightPackageDao skyLightPackageDao = instance.SkyLightPackageDao();
            SkylightCashDao skylightCashDao = instance.SkylightCashDao();
            AccountDao accountDao = instance.AccountDao();
            StandingOrderDao standingOrderDao = instance.StandingOrderDao();
            StandingOrderAcctDao standingOrderAcctDao = instance.StandingOrderAcctDao();
            PasswordHelpersDao passwordHelpersDao = instance.PasswordHelpersDao();
            TimeLineDao timeLineDao = instance.TimeLineDao();
            PaymentDocDao paymentDocDao = instance.PaymentDocDao();
            TellerCashDao tellerCashDao = instance.TellerCashDao();
            /*AccountDao accountDao = instance.AccountDao();
            AccountDao accountDao = instance.AccountDao();
            AccountDao accountDao = instance.AccountDao();
            AccountDao accountDao = instance.AccountDao();*/
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    private SkylightCashDao SkylightCashDao() {
        return skylightCashDao;
    }

    protected SkyLightPackageDao SkyLightPackageDao() {
        return skyLightPackageDao;
    }

    protected com.skylightapp.Interfaces.EmergencyReportDao EmergencyReportDao() {
        return EmergencyReportDao;
    }

    protected TransactionDao TransactionDao() {
        return txDao;
    }

    protected AdminBankDepositDao AdminBankDepositDao() {
        return abdDao;
    }

    protected TellerReportDao TellerReportDao() {
        return tellerReportDao;
    }

    protected CustomerDao CustomerDao() {
        return customerDao;
    }


    ProfileDao ProfileDao() {
        return profileDao;
    }

    protected PaymentDocDao PaymentDocDao() {
        return paymentDocumentDao;
    }
    protected TellerCashDao TellerCashDao() {
        return tellerCashDao;
    }
    protected TimeLineDao TimeLineDao() {
        return timeLineDao;
    }
    private PasswordHelpersDao PasswordHelpersDao() {
        return passwordHelpersDao;
    }
    private StandingOrderAcctDao StandingOrderAcctDao() {
        return standingOrderAcctDao;
    }
    private StandingOrderDao StandingOrderDao() {
        return standingOrderDao;
    }
    private AccountDao AccountDao() {
        return accountDao;
    }

}
