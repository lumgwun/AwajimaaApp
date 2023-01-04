package com.skylightapp.MapAndLoc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.card.MaterialCardView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class EmergReportArrayA extends ArrayAdapter<EmergencyReport> {

    private Context context;
    private int resource;
    private String town;
    private String currencyCode,currencySymbol;
    private List<EmergencyReport> accountList;

    public EmergReportArrayA(Context context, int resource, ArrayList<EmergencyReport> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.accountList = arrayList;
    }

    public EmergReportArrayA(FragmentActivity activity, int emerg_array, ArrayList<EmergencyReport> reportArrayList) {
        super(activity, emerg_array, reportArrayList);
        this.context = activity;
        this.resource = emerg_array;
        this.accountList = reportArrayList;
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        EmergencyReport report = getItem(position);
        if(report !=null){
            town =report.getEmergRTown();
        }
        MaterialCardView cardView = convertView.findViewById(R.id.card_emerg_Array);
        AppCompatTextView txtEmergID = convertView.findViewById(R.id.emerg_id_array);
        AppCompatTextView txtEmergType = convertView.findViewById(R.id.type_emerg_Array);
        AppCompatTextView txtEmergTown = convertView.findViewById(R.id.emerg_array_town);

        AppCompatTextView txtReport = convertView.findViewById(R.id.emerg_reportArray);
        AppCompatTextView txtEmergStatus = convertView.findViewById(R.id.emerg_Status_A);
        AppCompatTextView txtAddress = convertView.findViewById(R.id.emerg_Address);
        if (report != null) {
            txtEmergTown.setText(MessageFormat.format("Report Town:{0}", town));
            txtEmergType.setText(MessageFormat.format("Type:{0}", report.getEmergRType()));
            txtEmergID.setText(MessageFormat.format("{0}", report.getEmergReportID()));
            txtEmergStatus.setText(MessageFormat.format("{0}", report.getEmergRStatus()));
            txtReport.setText(MessageFormat.format("Report:{0}", report.getEmergReport()));
            txtAddress.setText(MessageFormat.format("Address{0}", report.getEmergR_BackgAddress()));

        }

        return convertView;
    }
}
