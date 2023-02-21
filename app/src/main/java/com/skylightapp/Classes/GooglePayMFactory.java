package com.skylightapp.Classes;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.skylightapp.MarketClasses.CheckoutViewModel;

import javax.inject.Inject;

public class GooglePayMFactory implements ViewModelProvider.Factory {
    private CheckoutViewModel mViewModel;

    @Inject
    public GooglePayMFactory(CheckoutViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            return (T) mViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
