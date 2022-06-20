package com.skylightapp.FlutterWavePayments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skylightapp.R;

import java.util.Objects;

import static com.skylightapp.FlutterWavePayments.VerificationActivity.PUBLIC_KEY_EXTRA;
import static com.skylightapp.Transactions.OurConfig.RESULT_SUCCESS;

public class PinFragment extends Fragment {
    public static final String EXTRA_PIN = "extraPin";
    private String pin;

    public PinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pin, container, false);
        Button pinBtn = v.findViewById(R.id.rave_pinButton);
        final TextInputLayout pinTil = v.findViewById(R.id.rave_pinTil);
        final TextInputEditText pinEt = v.findViewById(R.id.rave_pinEv);

        injectComponents();
        pinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = Objects.requireNonNull(pinEt.getText()).toString();

                pinTil.setError(null);
                pinTil.setErrorEnabled(false);

                if (pin.length() != 4) {
                    pinTil.setError("Enter a valid pin");
                }
                else {
                    goBack();
                }
            }
        });

        pinEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                }
            }
        });

        return v;
    }

    private void injectComponents() {
        if (getActivity() != null) {
            ((VerificationActivity) getActivity()).getVerificationComponent()
                    .inject(this);
        }
    }


    public void goBack(){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PIN,pin);
        if (getActivity() != null) {
            getActivity().setResult(RESULT_SUCCESS, intent);
            getActivity().finish();
        }
    }
}
