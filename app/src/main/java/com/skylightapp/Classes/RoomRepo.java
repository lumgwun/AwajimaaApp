package com.skylightapp.Classes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Interfaces.AccountDao;
import com.skylightapp.Interfaces.AdminBankDepositDao;
import com.skylightapp.Interfaces.CustomerDao;
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
import com.skylightapp.Tellers.TellerCash;

import java.util.List;

public class RoomRepo {

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

    private LiveData<List<Profile>> allProfiles;
    private LiveData<List<Customer>> allCustomers;
    private LiveData<List<TellerReport>> allTellerReports;
    private LiveData<List<AdminBankDeposit>> allAdminBankDeposits;

    private LiveData<List<Transaction>> allTransactions;
    private LiveData<List<EmergencyReport>> allEmergencyReports;
    private LiveData<List<EmergReportNext>> allEmergRepNexts;
    private LiveData<List<SkyLightPackage>> allSkylightPacks;
    private LiveData<List<SkylightCash>> allSkylightCashes;
    private LiveData<List<StandingOrder>> allSOs;
    private LiveData<List<Account>> allAccounts;
    private LiveData<List<StandingOrderAcct>> allSOAccts;
    private LiveData<List<String>> allPassW;
    private LiveData<List<TimeLine>> allTimeLines;
    private LiveData<List<TellerCash>> allTellerCash;
    private LiveData<List<Payment>> allPayments;
    private LiveData<List<PaymentCode>> allPaymentCodes;
    private LiveData<List<PaymentDoc>> allPaymentDocs;
    private LiveData<List<TransactionGranting>> allTranxGrantings;
    private LiveData<List<AdminBalance>> allAdminBalances;
    private LiveData<List<CustomerManager>> allTellers;

    public RoomRepo(Application application) {
       /* RoomController database = RoomController.getInstance(application);
        timeLineDao = database.TimeLineDao();
        allTimeLines = timeLineDao.getAllTimeLine();
        tellerCashDao = database.TellerCashDao();
        allTellerCash = tellerCashDao.getAllTellerCash();
        paymentDocumentDao = database.PaymentDocDao();
        allPaymentDocs = paymentDocumentDao.getAllPaymentDoc();
        txDao = database.TransactionDao();
        allTransactions = txDao.getAllTransaction();
        profileDao = database.ProfileDao();
        allProfiles = profileDao.getAllProfiles();
        customerDao = database.CustomerDao();
        allCustomers = customerDao.getAllCustomers();
        tellerReportDao = database.TellerReportDao();
        allTellerReports = tellerReportDao.getAllTC();
        abdDao = database.AdminBankDepositDao();
        allAdminBankDeposits = abdDao.getAllAdminBankDeposit();
        txDao = database.TransactionDao();
        allTransactions = txDao.getAllTransaction();
        EmergencyReportDao = database.EmergencyReportDao();
        allEmergencyReports = EmergencyReportDao.getAllEmergencyReport();
        skyLightPackageDao = database.SkyLightPackageDao();
        allSkylightPacks = skyLightPackageDao.getAllSkyLightPackage();
    }

    public void insertProfile(Profile profile) {
        new InsertProfileAsyncTask(profileDao).execute(profile);
    }

    public void update(Profile profile) {
        new UpdateProfileAsyncTask(profileDao).execute(profile);
    }

    public void delete(Profile profile) {
        new DeleteProfileAsyncTask(profileDao).execute(profile);
    }

    public void deleteAllProfiles() {
        new DeleteAllProfilesAsyncTask(profileDao).execute();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return allProfiles;
    }

    private static class InsertProfileAsyncTask extends AsyncTask<Profile, Void, Void> {
        public ProfileDao profileDao;

        private InsertProfileAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profile) {
            profileDao.insertProfile(profile[0]);
            return null;
        }
    }

    private static class UpdateProfileAsyncTask extends AsyncTask<Profile, Void, Void> {
        public ProfileDao profileDao;

        private UpdateProfileAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... models) {
            profileDao.updateProfile(models[0]);
            return null;
        }
    }

    private static class DeleteProfileAsyncTask extends AsyncTask<Profile, Void, Void> {
        public ProfileDao profileDao;

        private DeleteProfileAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profile) {

            profileDao.deleteProfile(profile[0]);
            return null;
        }
    }

    private static class DeleteAllProfilesAsyncTask extends AsyncTask<Void, Void, Void> {
        public ProfileDao profileDao;
        private DeleteAllProfilesAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            profileDao.deleteAllProfiles();
            return null;
        }*/
    }
}
