package com.skylightapp.Customers;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.skylightapp.R;

import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_COLLECTED_SO_FAR;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_REMAINING;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_DURATION;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_END_DATE;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_ID;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_START_DATE;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_TOTAL_VALUE;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_TYPE;
import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_VALUE;

public class SavingsPackAdapt extends CursorAdapter {
    public SavingsPackAdapt(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.package_row, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView packageAmount = view.findViewById(R.id.package_amount1);
        TextView grandTotalAmount = view.findViewById(R.id.package_total4);
        TextView  savedAmount = view.findViewById(R.id.package_saved7);
        TextView amountRemaining = view.findViewById(R.id.package_amount_rem11);
        TextView duration = view.findViewById(R.id.package_duration11);
        TextView startDate = view.findViewById(R.id.package_start_date11);
        TextView daysRemaining = view.findViewById(R.id.package_days_remaining11);
        TextView status = view.findViewById(R.id.package_status_31);
        TextView profileManager = view.findViewById(R.id.package_manager4);
        TextView packageID = view.findViewById(R.id.package_ID11);
        TextView packageType = view.findViewById(R.id.package_type31);
        TextView endDate = view.findViewById(R.id.endDate2);
        //AppCompatButton btnMore = view.findViewById(R.id.package_more);
        long packageId = cursor.getLong(cursor.getColumnIndexOrThrow(PACKAGE_ID));
        String startDate3 = cursor.getString(cursor.getColumnIndexOrThrow(PACKAGE_START_DATE));
        String endDate5 = cursor.getString(cursor.getColumnIndexOrThrow(PACKAGE_END_DATE));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(PACKAGE_TYPE));
        int packageDuration = cursor.getInt(cursor.getColumnIndexOrThrow(PACKAGE_DURATION));
        Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(PACKAGE_VALUE));
        Double amountSaved = cursor.getDouble(cursor.getColumnIndexOrThrow(REPORT_AMOUNT_COLLECTED_SO_FAR));
        Double grandTotal = cursor.getDouble(cursor.getColumnIndexOrThrow(PACKAGE_TOTAL_VALUE));
        Double amountRem = cursor.getDouble(cursor.getColumnIndexOrThrow(REPORT_AMOUNT_REMAINING));

    }
}
