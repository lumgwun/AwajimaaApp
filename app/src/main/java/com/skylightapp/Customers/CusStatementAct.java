package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CusStatementAct extends AppCompatActivity {
    private static final String TAG = CusStatementAct.class.getSimpleName();
    private static String FILE ;
    String targetPdf = "/sdcard/skylight.pdf";
    File filePath;

    private Bundle updateBundle,typeBundle,walletBalance;
    private TellerReport tellerReport;
    private DBHelper dbHelper;
    private int customerID;
    private long accountID;
    private long soAccountID;
    private  int adminID,tellerReportID,txCount;
    private static final String PREF_NAME = "skylight";

    private Profile userProfile;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    int profileID, soAcctID, acctID;
    private double wBalance,soBalance;
    private String json,json1;
    private Customer customer;
    private Account account;
    private StandingOrderAcct standingOrderAcct;
    private Transaction transaction;
    private Bitmap bitmap;
    private Button btn;
    private LinearLayout llScroll;
    private int noOfSavings,monthPackCount,monthSOCount,monthTranxCount,monthSavingsCount;
    private double cashDeposits,monthTotalSavings,monthTotalTranx;
    String file_name_path ;
    private WebView mWebview;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    TextView txtName,txtWalletID,txtSOAcctNo,txtWalletBalance,txtSOAcctBalance,txtDate,txtNoOfSavings,txtCashDeposit,txtBankDeposit,txtGrpSavings,txtGrpSBalance,txtWithrawals,txtLoans;
    AppCompatImageView profilePix;
    boolean itIsMonthEnd=false;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String SharedPrefUserPassword,acctStatementDate, statementType, officeBranch,dateOfReport, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_statement);
        setTitle("Account Statement");
        dbHelper=new DBHelper(this);
        file_name_path = getString(R.string.file_path_name);
        FILE = Environment.getExternalStorageDirectory()
                + "/skylight.pdf";
        //ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);



        if (!hasPermissions(CusStatementAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(CusStatementAct.this, PERMISSIONS, PERMISSION_ALL);
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        filePath = new File(this.getExternalFilesDir(null).getAbsolutePath(), file_name_path);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        mWebview = new WebView(this);
        gson1 = new Gson();
        account= new Account();
        transaction = new Transaction();
        standingOrderAcct = new StandingOrderAcct();
        userProfile=new Profile();
        customer = new Customer();
        updateBundle= new Bundle();
        typeBundle= new Bundle();
        dbHelper= new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString(PROFILE_PASSWORD, "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerProfileUsed", "");
        customer = gson1.fromJson(json, Customer.class);
        typeBundle=getIntent().getBundleExtra("StatementType");
        updateBundle=getIntent().getExtras();

        if(updateBundle !=null){
            statementType=updateBundle.getString("PackageType");
            cmName=updateBundle.getString("customerName");
            customer=updateBundle.getParcelable("Customer");
            customerID=updateBundle.getInt("customerID");
            profileID=updateBundle.getInt("profileID");
            monthPackCount=updateBundle.getInt("monthPackCount");
            monthSOCount=updateBundle.getInt("monthSOCount");
            monthSavingsCount=updateBundle.getInt("monthSavingsCount");
            monthTranxCount=updateBundle.getInt("monthTranxCount");
            monthTotalSavings=updateBundle.getDouble("monthTotalSavings");
            monthTotalTranx=updateBundle.getDouble("monthTotalTranx");


        }
        if(customer !=null){
            standingOrderAcct= customer.getCusStandingOrderAcct();
            account=customer.getCusAccount();
            cmFirstName = customer.getCusFirstName();
            cmLastName = customer.getCusSurname();
            customerID= customer.getCusUID();
        }
        cmName=cmLastName+""+cmFirstName;
        if(account !=null){
            acctID=account.getSkyLightAcctNo();
            accountID=account.getSkyLightAcctNo();
            wBalance=account.getAccountBalance();
        }
        if(standingOrderAcct !=null){
            soAccountID=standingOrderAcct.getSoAcctNo();
            soBalance=standingOrderAcct.getSoAcctBalance();
        }
        if(updateBundle !=null){
            statementType=updateBundle.getString("Transactions");
            cmName=updateBundle.getString("customerName");
            customerID=updateBundle.getInt("customerID");
            profileID=updateBundle.getInt("profileID");
            monthPackCount=updateBundle.getInt("monthPackCount");
            monthSOCount=updateBundle.getInt("monthSOCount");
            monthSavingsCount=updateBundle.getInt("monthSavingsCount");
            monthTranxCount=updateBundle.getInt("monthTranxCount");
            monthTotalSavings=updateBundle.getDouble("monthTotalSavings");
            monthTotalTranx=updateBundle.getDouble("monthTotalTranx");


        }
        if(updateBundle !=null){
            statementType=updateBundle.getString("StandingOrders");
            cmName=updateBundle.getString("customerName");
            customerID=updateBundle.getInt("customerID");
            profileID=updateBundle.getInt("profileID");
            monthPackCount=updateBundle.getInt("monthPackCount");
            monthSOCount=updateBundle.getInt("monthSOCount");
            monthSavingsCount=updateBundle.getInt("monthSavingsCount");
            monthTranxCount=updateBundle.getInt("monthTranxCount");
            monthTotalSavings=updateBundle.getDouble("monthTotalSavings");
            monthTotalTranx=updateBundle.getDouble("monthTotalTranx");


        }
        if(updateBundle !=null){
            statementType=updateBundle.getString("Savings");
            cmName=updateBundle.getString("customerName");
            customerID=updateBundle.getInt("customerID");
            profileID=updateBundle.getInt("profileID");
            monthPackCount=updateBundle.getInt("monthPackCount");
            monthSOCount=updateBundle.getInt("monthSOCount");
            monthSavingsCount=updateBundle.getInt("monthSavingsCount");
            monthTranxCount=updateBundle.getInt("monthTranxCount");
            monthTotalSavings=updateBundle.getDouble("monthTotalSavings");
            monthTotalTranx=updateBundle.getDouble("monthTotalTranx");


        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        acctStatementDate = mdformat.format(calendar.getTime());

        txtName = findViewById(R.id.statement_name33);
        txtWalletID = findViewById(R.id.wallet_ID33);
        txtWalletBalance = findViewById(R.id.amtStatement32);
        txtSOAcctNo = findViewById(R.id.So_No3);
        txtSOAcctBalance = findViewById(R.id.SoBalance4);
        txtDate = findViewById(R.id.dateStatement3);
        txtNoOfSavings = findViewById(R.id.noOfSavingsStatement3);
        txtCashDeposit = findViewById(R.id.cashStatement4);
        txtBankDeposit = findViewById(R.id.bankDeposit4);
        txtGrpSavings = findViewById(R.id.statementGrpSavings4);
        txtGrpSBalance = findViewById(R.id.grpSavingsBalance4);
        txtWithrawals = findViewById(R.id.statementWithdrawals4);
        txtLoans = findViewById(R.id.loansStatement4);
        profilePix = findViewById(R.id.statement_image);


        try {
            ProfDAO profDAO= new ProfDAO(this);
            TranXDAO tranXDAO= new TranXDAO(this);
            txtName.setText("Customer:"+cmName);
            profilePix.setImageBitmap(profDAO.getProfilePicture(customerID));
            txtWalletID.setText("Wallet ID:"+accountID);
            txtWalletBalance.setText("Wallet Balance"+wBalance);
            txtSOAcctNo.setText("SO Acct ID:"+soAccountID);
            txtSOAcctBalance.setText("SO Acct Balance: N"+soBalance);
            txtDate.setText("Date:"+acctStatementDate);
            noOfSavings=dbHelper.getCustomerTotalSavingsCount(customerID);
            txCount=tranXDAO.getTxCountCustomer(customerID);
            txtBankDeposit.setText("Bank Tx:"+txCount);
            cashDeposits=dbHelper.getTotalSavingsForCustomer(customerID);
            txtCashDeposit.setText("deposits N"+cashDeposits);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        btn = findViewById(R.id.btnGo);
        llScroll = findViewById(R.id.llScroll);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
                bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
                createPdf();
            }
        });


    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        String targetPdf = "/sdcard/skylight.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }
    @SuppressLint("SetJavaScriptEnabled")
    private void doPrint() {
        if(customer !=null){
            standingOrderAcct= customer.getCusStandingOrderAcct();
            account=customer.getCusAccount();
            cmFirstName = customer.getCusFirstName();
            cmLastName = customer.getCusSurname();
            customerID= customer.getCusUID();
        }
        mWebview.getSettings().setJavaScriptEnabled(true);
        

        PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter;
        cmName=cmLastName+""+cmFirstName;
        String jobName = this.getString(R.string.app_name) +"/"+cmName+ " Doc";
        printAdapter = mWebview.createPrintDocumentAdapter(jobName);
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
        filePath = new File(targetPdf);
        printManager.print(filePath.getName(), printAdapter, builder.build());

    }

    public void viewPdfFile() {

        File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + file_name_path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void sharePdfFile() {

        File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + file_name_path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/skylightApp.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(CusStatementAct.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

}