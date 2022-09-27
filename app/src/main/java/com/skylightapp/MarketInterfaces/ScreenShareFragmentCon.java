package com.skylightapp.MarketInterfaces;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.quickblox.videochat.webrtc.QBRTCMediaConfig;
import com.skylightapp.MarketDealFrags.BaseToolBarFragment;
import com.skylightapp.MarketDealFrags.PreviewFragment;
import com.skylightapp.MarketDealFrags.ScreenShareFragment;
import com.skylightapp.R;

public class ScreenShareFragmentCon extends BaseToolBarFragment {
    private static final String TAG = ScreenShareFragment.class.getSimpleName();

    private OnSharingEvents onSharingEvents;

    public static ScreenShareFragmentCon newInstance() {
        return new ScreenShareFragmentCon();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_screensharing;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            ToggleButton micToggle = view.findViewById(R.id.con_tb_switch_mic);
            ToggleButton cameraToggle = view.findViewById(R.id.con_tb_switch_cam);
            ToggleButton endCallToggle = view.findViewById(R.id.con_tb_end_call);
            ToggleButton shareScreenToggle = view.findViewById(R.id.con_tb_screen_share);
            ToggleButton swapCamToggle = view.findViewById(R.id.con_tb_swap_cam);

            micToggle.setVisibility(View.GONE);
            cameraToggle.setVisibility(View.GONE);
            endCallToggle.setVisibility(View.GONE);
            swapCamToggle.setVisibility(View.GONE);

            shareScreenToggle.setChecked(false);
            shareScreenToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d(TAG, "Stop Screen Sharing");
                    if (onSharingEvents != null) {
                        onSharingEvents.onStopPreview();
                    }
                }
            });
        }

        ImagesAdapter adapter = new ImagesAdapter(getChildFragmentManager());
        ViewPager pager = (ViewPager) view.findViewById(R.id.pagerTools);
        pager.setAdapter(adapter);

        QBRTCMediaConfig.setVideoWidth(QBRTCMediaConfig.VideoQuality.HD_VIDEO.width);
        QBRTCMediaConfig.setVideoHeight(QBRTCMediaConfig.VideoQuality.HD_VIDEO.height);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSharingEvents = (OnSharingEvents) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnSharingEvents");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSharingEvents = null;
    }

    public static class ImagesAdapter extends FragmentPagerAdapter {
        private static final int NUM_ITEMS = 1;

        private int[] images = {R.drawable.rect6b/*, R.drawable.p2p, R.drawable.group_call, R.drawable.opponents*/};

        ImagesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return PreviewFragment.newInstance(images[position]);
        }
    }

    public interface OnSharingEvents {

        void onStopPreview();
    }
}
