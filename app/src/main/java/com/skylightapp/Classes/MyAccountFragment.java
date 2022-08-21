package com.skylightapp.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.skylightapp.MyAddressesAct;
import com.skylightapp.R;

public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
    }

    public static final int MANAGE_ADDRESS = 1;
    private AppCompatButton viewAllAddressBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        viewAllAddressBtn = view.findViewById(R.id.view_all_addresses_btn);
        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAddressesIntent = new Intent(getContext(), MyAddressesAct.class);
                MyAddressesIntent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(MyAddressesIntent);
            }
        });
        return view;
    }
}
