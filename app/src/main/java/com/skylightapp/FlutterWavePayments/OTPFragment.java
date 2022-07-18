package com.skylightapp.FlutterWavePayments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skylightapp.Interfaces.VerificationComponent;
import com.skylightapp.R;

import static com.skylightapp.Transactions.OurConfig.RESULT_SUCCESS;

public class OTPFragment extends Fragment implements View.OnClickListener{
    public static final String EXTRA_OTP = "extraOTP";
    public static final String IS_SAVED_CARD_CHARGE = "is_saved_card_charge";
    private Boolean isSavedCardCharge = false;
    TextInputEditText otpEt;
    TextInputLayout otpTil;
    TextView chargeMessage;
    Button otpButton;
    public static final String EXTRA_CHARGE_MESSAGE = "extraChargeMessage";
    View v;
    String otp;

    public OTPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ot, container, false);
        injectComponents();

        initializeViews();

        setChargeMessage();

        setIsSavedCardCharge();

        setListeners();

        return v;
    }

    private void setIsSavedCardCharge() {
        if (getArguments() != null) {
            if (getArguments().containsKey(IS_SAVED_CARD_CHARGE)) {
                isSavedCardCharge = getArguments().getBoolean(IS_SAVED_CARD_CHARGE);
            }
        }
    }

    private void setListeners() {
        otpButton.setOnClickListener(this);

        otpEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                }
            }
        });
    }

    private void initializeViews() {
        otpTil = v.findViewById(R.id.otpTil);
        otpEt = v.findViewById(R.id.otpEv);
        otpButton = v.findViewById(R.id.otpButton);
        chargeMessage = v.findViewById(R.id.otpChargeMessage);
    }

    private void injectComponents() {
        if (getActivity() != null) {
            ((VerificationActivity) getActivity()).getVerificationComponent()
                    .inject(this);
        }
    }



    @Override
    public void onClick(View view) {
        int i = view.getId();

        if (i == R.id.otpButton) {
            clearErrors();
            otp = otpEt.getText().toString();
            if (otp.length() < 1) {
                otpTil.setError("Enter a valid one time password");
            } else {
                goBack();
            }
        }
    }

    private void clearErrors() {
        otpTil.setErrorEnabled(false);
        otpTil.setError(null);
    }

    private void setChargeMessage() {
        if (getArguments() != null) {
            if (getArguments().containsKey(EXTRA_CHARGE_MESSAGE)) {
                chargeMessage.setText(getArguments().getString(EXTRA_CHARGE_MESSAGE));
            }
        }
    }

    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_OTP, otp);
        intent.putExtra(IS_SAVED_CARD_CHARGE, isSavedCardCharge);

        if (getActivity() != null) {
            getActivity().setResult(RESULT_SUCCESS, intent);
            getActivity().finish();
        }
    }
}
