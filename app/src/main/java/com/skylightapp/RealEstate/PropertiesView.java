package com.skylightapp.RealEstate;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.skylightapp.R;

public class PropertiesView extends FrameLayout {
    public PropertiesView(Context context) {
        super(context);

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_prop, this);
    }
    public synchronized void setTitleId(int titleId) {
        ((AppCompatTextView) (findViewById(R.id.title482))).setText(titleId);
    }

    public synchronized void setDescriptionId(int descriptionId) {
        ((AppCompatTextView) (findViewById(R.id.description))).setText(descriptionId);
    }
}
