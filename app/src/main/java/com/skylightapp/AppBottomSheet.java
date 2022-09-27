package com.skylightapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.skylightapp.Classes.AppConstants.AWAJIMA_PRIVACY_POLICIES;

public class AppBottomSheet extends BottomSheetDialogFragment {
    String heading;
    View v;
    ImageView btnClose;
    TextView txtHeader;
    WebView webView;
    LinearLayout bottomSheetLinear;
    private CoordinatorLayout bottomSheetCoord;

    public AppBottomSheet(String heading) {
        this.heading = heading;
    }
    public AppBottomSheet() {
        super();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null) {

            getDialog().setOnShowListener(dialog -> {

                BottomSheetDialog d = (BottomSheetDialog) dialog;
                bottomSheetLinear = (LinearLayout) d.findViewById(R.id.bottomSheet_child);
                bottomSheetCoord = (CoordinatorLayout) d.findViewById(R.id.bottomSheet_Coord);
                if (bottomSheetLinear != null) {
                    BottomSheetBehavior.from(bottomSheetLinear)
                            .setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_app, container, false);
        txtHeader = v.findViewById(R.id.tv_headingT);
        btnClose = v.findViewById(R.id.btn_closeT);
        webView = v.findViewById(R.id.webViewT);
        bottomSheetLinear = v.findViewById(R.id.bottomSheet_child);

        txtHeader.setText(heading);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(AWAJIMA_PRIVACY_POLICIES);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), SignUpAct.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        });
        return v;
    }
}
