package com.jgo.demos.customview;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.jgo.demos.R;
import com.jgo.demos.customview.views.DashBoardView;
import com.jgo.demos.customview.views.ProgressCircleView;

/**
 * Created by ke-oh on 2019/07/23.
 *
 */

public class DrawViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressCircleView mProgressView;
    private DashBoardView mDashBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dotline);

        mProgressView = findViewById(R.id.progress_circle);
        findViewById(R.id.start_progress).setOnClickListener(this);

        mDashBoardView = findViewById(R.id.dash_board);
        findViewById(R.id.dash_board_random_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_progress :
                startProgress();
                break;
            case R.id.dash_board_random_btn :
                mDashBoardView.updateProgress((int) (Math.random() * 100));
                break;
        }
    }

    /**
     * Start progress
     */
    private void startProgress() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener((valueUpdate) -> {
            mProgressView.setProgressValue((int)valueUpdate.getAnimatedValue());
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(5 * 1000);
        animator.start();
    }
}
