package com.skylightapp.MarketClasses;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


public class MediaPickHelper {
    public static void pickAnImage(FragmentActivity activity, int requestCode) {
        MediaPickHelperFragCon mediaPickHelperFragment = MediaPickHelperFragCon.start(activity, requestCode);
        showImageSourcePickerDialog(activity.getSupportFragmentManager(), mediaPickHelperFragment);
    }

    private static void showImageSourcePickerDialog(FragmentManager fm, MediaPickHelperFragCon fragment) {
        MediaSourcePickDialogFragCon.show(fm,
                new MediaSourcePickDialogFragCon.LoggableActivityImageSourcePickedListener(fragment));
    }
}
