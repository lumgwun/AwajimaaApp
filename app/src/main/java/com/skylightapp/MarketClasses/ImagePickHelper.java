package com.skylightapp.MarketClasses;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.skylightapp.MarketDealFrags.MediaPickHelperFragment;
import com.skylightapp.MarketDealFrags.MediaSourcePickDialogFragment;


public class ImagePickHelper {
    public void pickAnImage(FragmentActivity activity, int requestCode) {
        MediaPickHelperFragment mediaPickHelperFragment = MediaPickHelperFragment.start(activity, requestCode);
        showImageSourcePickerDialog(activity.getSupportFragmentManager(), mediaPickHelperFragment);
    }

    private void showImageSourcePickerDialog(FragmentManager fm, MediaPickHelperFragment fragment) {
        MediaSourcePickDialogFragment.show(fm,
                new MediaSourcePickDialogFragment.ImageSourcePickedListener(fragment));
    }
}
