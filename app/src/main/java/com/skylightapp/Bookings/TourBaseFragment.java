package com.skylightapp.Bookings;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.androidquery.callback.ImageOptions;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.R;

public abstract class TourBaseFragment extends Fragment implements
        View.OnClickListener, AsyncListener {
    TourDetailsAct activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (TourDetailsAct) getActivity();
    }

    protected abstract boolean isValidate();

    @Override
    public void onTaskCompleted(final String response, int serviceCode) {

    }

    protected ImageOptions getAqueryOption() {
        ImageOptions options = new ImageOptions();
        options.targetWidth = 200;
        options.memCache = true;
        options.fallback = R.drawable.user_red;
        options.fileCache = true;
        return options;
    }
}
