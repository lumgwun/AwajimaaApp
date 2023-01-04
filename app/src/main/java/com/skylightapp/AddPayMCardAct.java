package com.skylightapp;

import static com.skylightapp.Bookings.BookingConstant.URL;
import static com.skylightapp.Classes.AppConstants.VERVE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.skylightapp.Bookings.BookingConstant;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.Classes.Card;
import com.skylightapp.Classes.HttpRequester;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.TextUtils;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;


import java.util.HashMap;

public class AddPayMCardAct extends AppCompatActivity implements View.OnClickListener, AsyncListener, Response.ErrorListener {
    private static final String TAG = "AddPayMCardAct";
    private AppCompatButton btnAddPayment, btnPaymentSkip;
    private AppCompatImageView btnScan;
    private final int MY_SCAN_REQUEST_CODE = 111;
    private AppCompatEditText etCreditCardNum, etCvc, etYear, etMonth;
    // private String patternVisa = "^4[0-9]{12}(?:[0-9]{3})?$";
    // private String patternMasterCard = "^5[1-5][0-9]{14}$";
    // private String patternAmericanExpress = "^3[47][0-9]{13}$";
    public static final String[] PREFIXES_AMERICAN_EXPRESS = { "34", "37" };
    public static final String[] PREFIXES_DISCOVER = { "60", "62", "64", "65" };
    public static final String[] PREFIXES_JCB = { "35" };
    public static final String[] PREFIXES_DINERS_CLUB = { "300", "301", "302",
            "303", "304", "305", "309", "36", "38", "37", "39" };
    public static final String[] PREFIXES_VISA = { "4" };
    public static final String[] PREFIXES_MASTERCARD = { "50", "51", "52",
            "53", "54", "55" };
    public static final String AMERICAN_EXPRESS = "American Express";
    public static final String DISCOVER = "Discover";
    public static final String JCB = "JCB";
    public static final String DINERS_CLUB = "Diners Club";
    public static final String VISA = "Visa";
    public static final String MASTERCARD = "MasterCard";
    public static final String UNKNOWN = "Unknown";
    public static final int MAX_LENGTH_STANDARD = 16;
    public static final int MAX_LENGTH_AMERICAN_EXPRESS = 15;
    public static final int MAX_LENGTH_DINERS_CLUB = 14;
    private String type;
    private AppCompatEditText etHolder;
    private RequestQueue requestQueue;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_payment);
        setTitle("Add Payment Card");
        card= new Card();
        btnAddPayment = findViewById(R.id.btnAddPayment);
        btnPaymentSkip = findViewById(R.id.btnPaymentSkip);
        btnPaymentSkip.setVisibility(View.GONE);
        btnScan =  findViewById(R.id.btnScan);
        etCreditCardNum =  findViewById(R.id.edtRegisterCreditCardNumber);
        etCreditCardNum.addTextChangedListener(new TextWatcher() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (TextUtils.isBlank(s.toString())) {
                    etCreditCardNum.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, null, null);
                }
                type = getType(s.toString());

                if (type.equals(VISA)) {
                    etCreditCardNum.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(
                                    R.drawable.visa_logo_new), null,
                            null, null);

                } else if (type.equals(MASTERCARD)) {
                    etCreditCardNum.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(
                                    R.drawable.master_card_logo_svg),
                            null, null, null);

                } else if (type.equals(VERVE)) {
                    etCreditCardNum.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(
                                    R.drawable.verve), null,
                            null, null);

                }  else {
                    etCreditCardNum.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, null, null);
                }
                if (etCreditCardNum.getText().toString().length() == 16) {
                    etMonth.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etCvc =  findViewById(R.id.edtRegistercvc);
        etYear =  findViewById(R.id.edtRegisterexpYear);
        etMonth = findViewById(R.id.edtRegisterexpMonth);
        etYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (etYear.getText().toString().length() == 4) {
                    etCvc.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etMonth.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (etMonth.getText().toString().length() == 2) {
                    etYear.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etHolder =  findViewById(R.id.edtRegisterCreditCardHolder);
        btnScan.setOnClickListener(this);
        btnAddPayment.setOnClickListener(this);
        findViewById(R.id.btnPaymentSkip).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnAddPayment:
                if (isValidate()) {
                    saveCreditCard();
                }
                break;
            case R.id.btnScan:
                scan();
                break;
            case R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    //@Override
    protected boolean isValidate() {
        if (etCreditCardNum.getText().length() == 0
                || etCvc.getText().length() == 0
                || etMonth.getText().length() == 0
                || etYear.getText().length() == 0) {
            UtilsExtra.showToast("Enter Proper data", this);
            return false;
        }
        return true;
    }

    private void scan() {
        /*Intent scanIntent = new Intent(this, CardIOActivity.class);

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default:
        // true
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default:
        // false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default:
        // false

        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default:

        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_SCAN_REQUEST_CODE:
                /*if (resultCode == Activity.RESULT_OK) {
                    if (data != null
                            && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                        CreditCard scanResult = data
                                .getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                        etCreditCardNum.setText(scanResult.getRedactedCardNumber());

                        if (scanResult.isExpiryValid()) {
                            etMonth.setText(scanResult.expiryMonth + "");
                            etYear.setText(scanResult.expiryYear + "");
                        }

                        if (scanResult.cvv != null) {
                            // Never log or display a CVV
                            // resultStr += "CVV has " + scanResult.cvv.length()
                            // + " digits.\n";
                            etCvc.setText(scanResult.cvv);
                        }

                        // if (scanResult.postalCode != null) {
                        // resultStr += "Postal Code: " + scanResult.postalCode +
                        // "\n";
                        // }
                    } else {
                        // resultStr = "Scan was canceled.";
                        UtilsExtra.showToast("Scan was canceled.", this);
                    }
                } else {
                    UtilsExtra.showToast("Scan was uncessfull.", this);
                }
                break;*/

        }

    }

    public void saveCreditCard() {
        try {
            card = new Card(etCreditCardNum.getText().toString(),
                    Integer.parseInt(etMonth.getText().toString()),
                    Integer.parseInt(etYear.getText().toString()), Integer.parseInt(etCvc.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        boolean validation = card.validateCard();
        /*if (validation) {
            UtilsExtra.showCustomProgressDialog(this,
                    getString(R.string.adding_payment), false, null);
            new Stripe().createToken(car, Const.PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            // getTokenList().addToList(token);
                            // AndyUtils.showToast(token.getId(), activity);
                            String lastFour = etCreditCardNum.getText()
                                    .toString().toString();
                            lastFour = lastFour.substring(lastFour.length() - 4);
                            addCard(token.getId(), lastFour);
                            // finishProgress();
                        }

                        public void onError(Exception error) {
                            UtilsExtra.showToast("Error",
                                    UberAddPaymentActivity.this);
                            // finishProgress();
                            UtilsExtra.removeCustomProgressDialog();
                        }
                    });
        } else if (!car.validateNumber()) {
            // handleError("The car number that you entered is invalid");
            UtilsExtra.showToast("The car number that you entered is invalid",
                    this);
        } else if (!car.validateExpiryDate()) {
            // handleError("");
            UtilsExtra.showToast(
                    "The expiration date that you entered is invalid", this);
        } else if (!car.validateCVC()) {
            // handleError("");
            UtilsExtra.showToast("The CVC code that you entered is invalid",
                    this);

        } else {
            // handleError("");
            UtilsExtra.showToast(
                    "The car details that you entered are invalid", this);
        }*/


    }

    public String getType(String number) {
        if (!TextUtils.isBlank(number)) {
            if (TextUtils.hasAnyPrefix(number, PREFIXES_AMERICAN_EXPRESS)) {
                return AMERICAN_EXPRESS;
            } else if (TextUtils.hasAnyPrefix(number, PREFIXES_DISCOVER)) {
                return DISCOVER;
            } else if (TextUtils.hasAnyPrefix(number, PREFIXES_JCB)) {
                return JCB;
            } else if (TextUtils.hasAnyPrefix(number, PREFIXES_DINERS_CLUB)) {
                return DINERS_CLUB;
            } else if (TextUtils.hasAnyPrefix(number, PREFIXES_VISA)) {
                return VISA;
            } else if (TextUtils.hasAnyPrefix(number, PREFIXES_MASTERCARD)) {
                return MASTERCARD;
            } else {
                return UNKNOWN;
            }
        }
        return UNKNOWN;

    }

    private void addCard(String token, String lastFour) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.ADD_CARD);
        map.put(BookingConstant.Params.ID, String.valueOf(new PrefManager(this).getUserId()));
        map.put(BookingConstant.Params.TOKEN,
                new PrefManager(this).getSessionToken());
        map.put(BookingConstant.Params.CARD_TOKEN, token);
        map.put(BookingConstant.Params.LAST_FOUR, lastFour);
        map.put(BookingConstant.Params.CARD_TYPE, type);
        new HttpRequester(this, map, BookingConstant.ServiceCode.ADD_CARD, this);

        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.ADD_CARD, this, this));
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setStatusText(final String string) {
        //AppLog.Log(TAG, string);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}