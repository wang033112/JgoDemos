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

public class CircularRevealWifiFragment extends Fragment implements View.OnClickListener {

    private View mMainContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mMainContainer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_circular_reveal_wifi, null);
        return mMainContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
