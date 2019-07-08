package com.jgo.demos.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/08.
 *
 */

public class TransitionActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TRANS_EXPLODE = 1;
    public static final int TRANS_SLIDE   = 2;
    public static final int TRANS_FADE    = 3;

    public static final String TRANS_KEY = "trans_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_transition);

        findViewById(R.id.transition_explode).setOnClickListener(this);
        findViewById(R.id.transition_slide).setOnClickListener(this);
        findViewById(R.id.transition_fade).setOnClickListener(this);
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
        }

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
