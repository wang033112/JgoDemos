package com.jgo.demos.transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/08.
 *
 */

public class TransitionDesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //getWindow().setAllowEnterTransitionOverlap(false);
        //getWindow().setAllowReturnTransitionOverlap(false);

        Intent intent = getIntent();
        if (intent != null) {
            Transition transition = null;
            switch (intent.getIntExtra(TransitionActivity.TRANS_KEY, 1)) {
                case TransitionActivity.TRANS_EXPLODE:
                    transition = new Explode();
                    break;
                case TransitionActivity.TRANS_SLIDE:
                    transition = new Slide(Gravity.RIGHT);
                    break;
                case TransitionActivity.TRANS_FADE:
                    transition = new Fade();
                    break;
            }

            getWindow().setEnterTransition(transition);
        }

        setContentView(R.layout.activity_transition_des);
        TextView backTv = findViewById(R.id.transition_des_back);
        backTv.setOnClickListener((v) -> {
            finishAfterTransition();
        });
    }

}
