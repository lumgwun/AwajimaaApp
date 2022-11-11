package com.skylightapp.MapAndLoc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.skylightapp.R;

public class CustomDialog {
    private final AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String message;
    private EditText dialogTitle,dialogMessage;
    private AppCompatButton negativeButton,positiveButton;
    private String btYesText;
    private ImageView dialogIcon;
    private String btNoText;
    private int icon=0;


    public CustomDialog(Context context) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        builder = new AlertDialog.Builder(context).setCancelable(false);

    }

    public CustomDialog setTitle(String title)  {
        dialogTitle.setVisibility(View.VISIBLE);
        dialogTitle.setText(title);

        return this;
    }

    public CustomDialog setMessage(String title){
        dialogMessage.setVisibility(View.VISIBLE);
        dialogMessage.setText(title);

        return this;
    }

    public CustomDialog setIcon(Drawable icon){
        dialogIcon.setVisibility(View.VISIBLE);
        dialogIcon.setImageDrawable(icon);

        return this;
    }

    public CustomDialog setCancelButton(String negativeText){
        negativeButton.setVisibility(View.VISIBLE);
        negativeButton.setText(negativeText);
        negativeButton.setOnClickListener(v -> dismissDialog());
        return this;
    }

    public CustomDialog setPositiveButton(String positiveText, ICustomDialogClickListener onClickListener){
        positiveButton.setVisibility(View.VISIBLE);
        positiveButton.setText(positiveText);
        positiveButton.setOnClickListener(v ->{
            onClickListener.onClick();
            dismissDialog();
        });

        return this;
    }

    public CustomDialog createDialog(){
        alertDialog.setContentView(R.layout.dialog_custom);
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        negativeButton = alertDialog.findViewById(R.id.cancel_textD);
        positiveButton = alertDialog.findViewById(R.id.okay_Dtext);
        dialogMessage = alertDialog.findViewById(R.id.cd_message_d);
        dialogTitle = alertDialog.findViewById(R.id.cd_dialog_T);
        return this;
    }

    public void showDialog() {
        if (this.alertDialog==null) {
            createDialog();
        }
        alertDialog.show();
    }

    public void dismissDialog(){ alertDialog.dismiss();}

    public interface ICustomDialogClickListener {
        void onClick();
    }
}
