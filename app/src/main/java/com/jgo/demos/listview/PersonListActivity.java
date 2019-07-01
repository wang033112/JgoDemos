package com.jgo.demos.listview;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jgo.demos.R;
import com.jgo.demos.listview.adapter.PersonRecyclerViewAdapter;
import com.jgo.demos.util.DataManager;

/**
 * Created by ke-oh on 2019/06/28.
 *
 */

public class PersonListActivity extends AppCompatActivity {

    private static final String TAG = PersonListActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private PersonRecyclerViewAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private LinearLayout mTopLayout;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list_view);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView = findViewById(R.id.person_list_view);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new PersonRecyclerViewAdapter(getApplicationContext(), DataManager.getInstance().getPersonList(getApplication()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new PersonScrollListener());

        mTopLayout = findViewById(R.id.person_list_top_layout);

        mHandler = new Handler();
    }

    /**
     * Recycler Scroller Listener
     */
    public class PersonScrollListener extends RecyclerView.OnScrollListener {

        private int mCurrentFirstVisiblePosition;

        private int mLastAddOrRemoveViewPosition;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState){
            //SCROLL_STATE_IDLE == 0, SCROLL_STATE_DRAGGING == 1, SCROLL_STATE_SETTLING == 2
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy){

            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            View firstVisibleView = layoutManager.findViewByPosition(firstVisiblePosition);
            if (firstVisibleView != null) {
                ImageView img = firstVisibleView.findViewById(R.id.person_list_view_img);

                Rect visibleRF = new Rect();
                img.getLocalVisibleRect(visibleRF);

                //Scroll up
                if (dy > 0 && (visibleRF.top != 0) && !mAdapter.isImgHiddenAtPosition(firstVisiblePosition)) {
                    mHandler.post(() -> addViewTopLayout(firstVisiblePosition));
                    mLastAddOrRemoveViewPosition = firstVisiblePosition;
                }

                //Scroll down
                if (dy < 0 && mAdapter.isImgHiddenAtPosition(firstVisiblePosition)
                        && ((firstVisiblePosition == mCurrentFirstVisiblePosition && (visibleRF.top > 0 && visibleRF.top < img.getHeight() / 3))
                        || firstVisiblePosition < mLastAddOrRemoveViewPosition - 1)) {

                    int tempPosition = mCurrentFirstVisiblePosition;
                    while (tempPosition >= firstVisiblePosition) {
                        final int removePosition = tempPosition;
                        mHandler.post(() -> removeViewTopLayout(removePosition));
                        tempPosition--;
                        mLastAddOrRemoveViewPosition = firstVisiblePosition;
                    }
                }
                mCurrentFirstVisiblePosition = firstVisiblePosition;
            }
        }


        /**
         * Add imageview and cardView to toplayout.
         *
         * @param position the position of imageview at listview.
         */
        private void addViewTopLayout(int position) {

            if (position <0 || position >= mAdapter.getItemCount()) {
                return;
            }

            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageDrawable(getDrawable(mAdapter.getImgSrcAtPosition(position)));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            CardView cardView = new CardView(getApplicationContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int)getResources().getDimension(R.dimen.person_list_view_img_size), (int)getResources().getDimension(R.dimen.person_list_view_img_size));
            layoutParams.rightMargin = (int)getResources().getDimension(R.dimen.person_list_view_img_top_layout_right_margin);
            cardView.setRadius(getResources().getDimension(R.dimen.person_list_view_img_size) / 2);
            cardView.addView(imageView);

            if (!mAdapter.isImgHiddenAtPosition(position)) {
                mTopLayout.addView(cardView, 0, layoutParams);
                mAdapter.setImgHiddenAtPosition(position, true);
            }
        }

        /**
         * Remove the view at toplayout.
         *
         * @param position the position of imageview at listview.
         */
        private void removeViewTopLayout(int position) {
            if (position <0 || position >= mAdapter.getItemCount()) {
                return;
            }

            try {
                if (mAdapter.isImgHiddenAtPosition(position)) {
                    mTopLayout.removeViewAt(0);
                    mAdapter.setImgHiddenAtPosition(position, false);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error at toplayout when remove view");
            }
        }
    }
}
