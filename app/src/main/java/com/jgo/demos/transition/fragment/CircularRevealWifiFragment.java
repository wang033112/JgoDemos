package com.jgo.demos.transition.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/10.
 *
 */

public class CircularRevealWifiFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_ANIM_START_X = "anim_start_x";
    public static final String KEY_ANIM_START_Y = "anim_start_y";
    private static final float START_RADIUS = 10.0f;
    private static final float END_RADIUS = 30.0f;

    private View mMainContainer;
    private ImageView mCloseImg;
    private float mStartX;
    private float mStartY;

    /**
     * CircularRevealWifiFragment
     *
     * @param startX animation starts x
     * @param startY animation starts x
     * @return
     */
    public static CircularRevealWifiFragment getInstance(float startX, float startY) {
        CircularRevealWifiFragment fragment = new CircularRevealWifiFragment();
        Bundle bundle = new Bundle();
        bundle.putFloat(KEY_ANIM_START_X, startX);
        bundle.putFloat(KEY_ANIM_START_Y, startY);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mStartX = bundle.getFloat(KEY_ANIM_START_X);
            mStartY = bundle.getFloat(KEY_ANIM_START_Y);
        }
        mMainContainer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_circular_reveal_wifi, null);
        return mMainContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCloseImg = mMainContainer.findViewById(R.id.close_img);
        mCloseImg.setOnClickListener(this);
        Animator animator = ViewAnimationUtils.createCircularReveal(
                mMainContainer, (int)mStartX, (int)mStartY, 50, 1000);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img :
                closeWithAnimation();
                break;
        }
    }

    private void closeWithAnimation() {
        Animator animator = ViewAnimationUtils.createCircularReveal(
                mMainContainer, (int)mStartX, (int)mStartY, 1000, 50);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isAdded()) {
                    return;
                }
                mMainContainer.setVisibility(View.INVISIBLE);
                if (getActivity() != null && getActivity().getSupportFragmentManager() != null){
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            }
        });
        animator.start();
    }
}
