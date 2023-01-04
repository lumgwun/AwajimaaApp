package com.skylightapp.Bookings;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.skylightapp.Classes.Utils;
import com.skylightapp.R;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.models.MarkerOption;
import com.teliver.sdk.models.TrackingBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragCustomer extends Fragment {

    private Activity context;

    private View viewRoot;

    private AppCompatEditText edtId;

    private int[] icons = new int[]{R.drawable.ic__category, R.drawable.ic_bell, R.drawable.pickup};

    private Random random;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_cus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        viewRoot = view.findViewById(R.id.cus_view_root);
        edtId = context.findViewById(R.id.edt_tracking_id);
        random = new Random();
        view.findViewById(R.id.trip_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking();
            }
        });
    }


    private void startTracking() {
        try {
            startTracking(edtId.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTracking(String trackingId) {
        try {
            if (trackingId.isEmpty())
                Utils.showSnack(viewRoot, getString(R.string.enter_valid_id));
            else if (!Utils.isNetConnected(context))
                Utils.showSnack(viewRoot, getString(R.string.no_internet_connection));
            else {
                List<MarkerOption> markerOptionList = new ArrayList<>();
                String[] ids = trackingId.split(",");
                for (String id : ids) {
                    MarkerOption option = new MarkerOption(id);
                    option.setMarkerTitle(id);
                    option.setIconMarker(icons[random.nextInt(icons.length)]);
                    markerOptionList.add(option);
                }
                TrackingBuilder builder = new TrackingBuilder(markerOptionList);
                Teliver.startTracking(builder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
