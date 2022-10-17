package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.blongho.country_data.Currency;
import com.bumptech.glide.Glide;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TellerSpinnerAdapter extends ArrayAdapter<CustomerManager> {

    private Context context;
    private int resource;
    private String tellerName,tellerFirstName,tellerSurname;
    private Uri tellerPix;
    private List<CustomerManager>customerManagers;

    public TellerSpinnerAdapter(Context context, int resource, ArrayList<CustomerManager> customerManagers) {
        super(context, resource, customerManagers);
        this.context = context;
        this.resource = resource;
        this.customerManagers = customerManagers;
    }

    public TellerSpinnerAdapter(FragmentActivity activity, int teller_item, ArrayList<CustomerManager> customerManagers) {
        super(activity, teller_item, customerManagers);
        this.context = activity;
        this.resource = teller_item;
        this.customerManagers = customerManagers;
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        CustomerManager customerManager = getItem(position);
        if(customerManager !=null){
            tellerFirstName=customerManager.getTFirstName();
            tellerSurname=customerManager.getTSurname();
            tellerName=tellerSurname+""+tellerFirstName;
            tellerPix=customerManager.getTPhoto();
        }


        AppCompatTextView txtTellerName = convertView.findViewById(R.id.teller_name_Item);
        CircleImageView bizLogo = convertView.findViewById(R.id.logo_Img_T);
        assert customerManager != null;
        txtTellerName.setText(tellerName);

        Glide.with(context)
                .load(tellerPix)
                .placeholder(R.drawable.user_red1)
                .error(R.drawable.ic_error)
                .into(bizLogo);
        return convertView;
    }
}
