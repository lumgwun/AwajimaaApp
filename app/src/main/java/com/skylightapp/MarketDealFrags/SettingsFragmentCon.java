package com.skylightapp.MarketDealFrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragment;

import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.R;

public class SettingsFragmentCon extends PreferenceFragment {

    public SettingsFragmentCon() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isVideoSettings = this.getArguments().getBoolean(ConstsInterface.EXTRA_SETTINGS_TYPE);
        if (isVideoSettings) {
            addPreferencesFromResource(R.xml.preferences_video);
        } else {
            addPreferencesFromResource(R.xml.preferences_audio);
        }
    }

    @Override
    public void onCreatePreferences(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (v != null) {
            ListView lv = (ListView) v.findViewById(android.R.id.list);
            lv.setPadding(0, 0, 0, 0);
        }
        return v;
    }
}
