package com.skylightapp.GooglePayDir;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.skylightapp.GooglePayAct;
import com.skylightapp.MarketClasses.CheckoutViewModel;

public class ViewModelProvider extends ViewModel {
    private static final String TAG = ViewModelProvider.class.getSimpleName();
    private Context context;
    private GooglePayAct googlePayAct;

    public ViewModelProvider(GooglePayAct googlePayAct) {
        super();
        this.context=googlePayAct;
    }

    @NonNull
    //@Override
    public <T extends ViewModel> T get(@NonNull Class<T> checkoutViewModelClass) {
        if (checkoutViewModelClass.isAssignableFrom(ViewModelProvider.class)) {
            try {
                return (T) new ViewModelProvider(googlePayAct);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
    /*public static class GameViewModelFactory implements ViewModelProvider.Factory {
        private final ViewModelProvider trivialDriveRepository;

        public GameViewModelFactory(ViewModelProvider tdr) {
            trivialDriveRepository = tdr;
        }

        @NonNull
        //@Override
        public <T extends ViewModel> T get(@NonNull Class<T> checkoutViewModelClass) {
            if (checkoutViewModelClass.isAssignableFrom(ViewModelProvider.class)) {
                return (T) new ViewModelProvider(googlePayAct);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }*/
}
