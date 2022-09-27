package com.skylightapp.MarketClasses;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.skylightapp.R;

public class CustomDialog {
    Activity activity;
    Dialog dialog;

    public CustomDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog(){
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_loader);

        ImageView imageView = dialog.findViewById(R.id.custom_loading_imageView);
        Glide.with(activity)
                .load(R.drawable.event_circle)
                .placeholder(R.drawable.message_circle)
                .centerCrop()
                .into(new DrawableImageViewTarget(imageView));

        dialog.show();
    }
    public void  hideDialog(){
        dialog.dismiss();
    }
}
