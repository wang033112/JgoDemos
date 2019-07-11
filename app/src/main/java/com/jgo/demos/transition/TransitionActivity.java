package com.jgo.demos.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/08.
 *
 */

public class TransitionActivity extends AppCompatActivity implements View.OnClickListener, CircularRevealFragment.CircularItemListener {

    public static final int TRANS_EXPLODE = 1;
    public static final int TRANS_SLIDE   = 2;
    public static final int TRANS_FADE    = 3;

    public static final String TRANS_KEY = "trans_key";

    private ViewGroup mSceneContainer;
    private ImageView mRevertImg;
    private Scene mSceneFirst;
    private Scene mSceneSecond;
    private boolean mIsFirstScene;

    private FrameLayout mCircularLayout;
    private CircularRevealFragment mCircularRevealFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_transition);

        findViewById(R.id.transition_explode).setOnClickListener(this);
        findViewById(R.id.transition_slide).setOnClickListener(this);
        findViewById(R.id.transition_fade).setOnClickListener(this);

        mSceneContainer = findViewById(R.id.scene_container_layout);
        mSceneFirst = Scene.getSceneForLayout(mSceneContainer, R.layout.transition_scene_first, this);
        mSceneSecond = Scene.getSceneForLayout(mSceneContainer, R.layout.transition_scene_second, this);

        findViewById(R.id.scene_revert_img).setOnClickListener(this);
        mIsFirstScene = true;

        mCircularRevealFragment = new CircularRevealFragment();
        mCircularRevealFragment.setItemListener(this);
        mCircularLayout = findViewById(R.id.circular_reveal_layout);
        replaceCircularFragment(mCircularRevealFragment);
    }

    /**
     *
     */
    private void replaceCircularFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.circular_reveal_layout, fragment, "");
        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TransitionDesActivity.class);
        switch (v.getId()) {
            case R.id.transition_explode:
                Explode explode = new Explode();
                explode.setDuration(300);
                getWindow().setExitTransition(explode);

                intent.putExtra(TRANS_KEY, TRANS_EXPLODE);
                break;
            case R.id.transition_slide:
                Slide slide = new Slide(Gravity.LEFT);
                slide.setDuration(300);
                slide.setInterpolator(new FastOutSlowInInterpolator());
                getWindow().setExitTransition(slide);

                intent.putExtra(TRANS_KEY, TRANS_SLIDE);
                break;
            case R.id.transition_fade:
                Fade fade = new Fade();
                fade.setDuration(300);
                fade.setInterpolator(new FastOutSlowInInterpolator());
                getWindow().setExitTransition(new Fade());

                intent.putExtra(TRANS_KEY, TRANS_FADE);
                break;
            case R.id.scene_revert_img:
                TransitionManager.go(mIsFirstScene ? mSceneSecond : mSceneFirst);
                mIsFirstScene = !mIsFirstScene;
                findViewById(R.id.scene_revert_img).setOnClickListener(this);
                return;
        }

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onSelected(int circularType, float x, float y) {
        switch (circularType) {
            case CircularRevealFragment.CIRCULAR_TYPE_WIFI :
                CircularRevealWifiFragment wifiFragment = CircularRevealWifiFragment.getInstance(x, y);
                replaceCircularFragment(wifiFragment);
                break;
        }
    }
}
