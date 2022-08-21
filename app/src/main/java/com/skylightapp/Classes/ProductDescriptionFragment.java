package com.skylightapp.Classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.skylightapp.R;

public class ProductDescriptionFragment extends Fragment {


    public ProductDescriptionFragment() {
    }
    private TextView descriptionBody;
    public String body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_product_desc, container, false);
        descriptionBody=view.findViewById(R.id.tv_product_description);
        descriptionBody.setText(body);
        return view;
    }
}
