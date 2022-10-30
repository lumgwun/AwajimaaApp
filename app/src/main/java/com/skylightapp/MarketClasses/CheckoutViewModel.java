package com.skylightapp.MarketClasses;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.pay.Pay;
import com.google.android.gms.pay.PayApiAvailabilityStatus;
import com.google.android.gms.pay.PayClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.skylightapp.Classes.AppConstants;

import org.json.JSONObject;

public class CheckoutViewModel extends AndroidViewModel {
    //8ce82fbdcacbdc6e6a297d7886d4d800066843cb

    private final PaymentsClient paymentsClient;

    private final PayClient walletClient;

    private final MutableLiveData<Boolean> _canUseGooglePay = new MutableLiveData<>();

    private final MutableLiveData<Boolean> _canAddPasses = new MutableLiveData<>();

    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        paymentsClient = AppConstants.createPaymentsClient(application);
        walletClient = Pay.getClient(application);

        fetchCanUseGooglePay();
        fetchCanAddPassesToGoogleWallet();
    }

    public final LiveData<Boolean> canUseGooglePay = _canUseGooglePay;
    public final LiveData<Boolean> canAddPasses = _canAddPasses;

    private void fetchCanUseGooglePay() {
        final JSONObject isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (isReadyToPayJson == null) {
            _canUseGooglePay.setValue(false);
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                completedTask -> {
                    if (completedTask.isSuccessful()) {
                        _canUseGooglePay.setValue(completedTask.getResult());
                    } else {
                        Log.w("isReadyToPay failed", completedTask.getException());
                        _canUseGooglePay.setValue(false);
                    }
                });
    }


    public Task<PaymentData> getLoadPaymentDataTask(final long priceCents) {
        JSONObject paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (paymentDataRequestJson == null) {
            return null;
        }

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.toString());
        return paymentsClient.loadPaymentData(request);
    }

    /**
     * Determine whether the API to save passes to Google Pay is available on the device.
     */
    private void fetchCanAddPassesToGoogleWallet() {
        walletClient
                .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
                .addOnSuccessListener(
                        status -> _canAddPasses.setValue(status == PayApiAvailabilityStatus.AVAILABLE))

                .addOnFailureListener(exception -> _canAddPasses.setValue(false));
    }

    public void savePassesJwt(String jwtString, Activity activity, int requestCode) {
        walletClient.savePassesJwt(jwtString, activity, requestCode);
    }

    public void savePasses(String objectString, Activity activity, int requestCode) {
        walletClient.savePasses(objectString, activity, requestCode);
    }

    // Test generic object used to be created against the API
    // See https://developers.google.com/wallet/tickets/boarding-passes/web#json_web_token_jwt for more details

    //public final String genericObjectJwt = "8ce82fbdcacbdc6e6a297d7886d4d800066843cb";
    public final String genericObjectJwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJnb29nbGUiLCJwYXlsb2FkIjp7ImdlbmVyaWNPYmplY3RzIjpbeyJpZCI6IjMzODgwMDAwMDAwMjIwOTUxNzcuZjUyZDRhZjYtMjQxMS00ZDU5LWFlNDktNzg2ZDY3N2FkOTJiIn1dfSwiaXNzIjoid2FsbGV0LWxhYi10b29sc0BhcHBzcG90LmdzZXJ2aWNlYWNjb3VudC5jb20iLCJ0eXAiOiJzYXZldG93YWxsZXQiLCJpYXQiOjE2NTA1MzI2MjN9.ZURFHaSiVe3DfgXghYKBrkPhnQy21wMR9vNp84azBSjJxENxbRBjqh3F1D9agKLOhrrflNtIicShLkH4LrFOYdnP6bvHm6IMFjqpUur0JK17ZQ3KUwQpejCgzuH4u7VJOP_LcBEnRtzZm0PyIvL3j5-eMRyRAo5Z3thGOsKjqCPotCAk4Z622XHPq5iMNVTvcQJaBVhmpmjRLGJs7qRp87sLIpYOYOkK8BD7OxLmBw9geqDJX-Y1zwxmQbzNjd9z2fuwXX66zMm7pn6GAEBmJiqollFBussu-QFEopml51_5nf4JQgSdXmlfPrVrwa6zjksctIXmJSiVpxL7awKN2w";


}
