package com.jgo.demos.transition;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/10.
 *
 */

public class CircularRevealFragment extends Fragment implements View.OnClickListener {

    public static final int CIRCULAR_TYPE_WIFI = 1;
    private View mMainContainer;
    private ViewGroup mWifiLayout;

    private CircularItemListener mItemListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mMainContainer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_circular_reveal, null);
        return mMainContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWifiLayout = mMainContainer.findViewById(R.id.circular_reveal_wifi_layout);
        mWifiLayout.setOnClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circular_reveal_wifi_layout:
                if (mItemListener != null) {
                    mItemListener.onSelected(CIRCULAR_TYPE_WIFI);
                }
                break;
        }
    }

    public void setItemListener(CircularItemListener itemListener) {
        this.mItemListener = itemListener;
    }

    public interface CircularItemListener {
        void onSelected(int circularType);
    }
}
