package com.jgo.demos.listview.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jgo.demos.R;
import com.jgo.demos.listview.data.CouponData;
import com.jgo.demos.listview.data.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/07/14.
 *
 */
public class CouponRecyclerViewAdapter extends RecyclerView.Adapter<CouponRecyclerViewAdapter.RecyclerHolder> {
    private Context mContext;
    private List<CouponData> mCouponDatas = new ArrayList<>();

    public CouponRecyclerViewAdapter(Context context, List<CouponData> mCouponDatas) {
        this.mContext = context;
        this.mCouponDatas = mCouponDatas;
    }

    /**
     * Set the coupon data.
     *
     * @param dataList coupon data
     */
    public void setData(List<CouponData> dataList) {
        if (null != dataList) {
            mCouponDatas.clear();
            mCouponDatas.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_coupon_list_view_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.expandImg.setOnClickListener((view) -> {
            boolean isExpanding = mCouponDatas.get(position).isExpand();
            expandView(holder.detailInfoTv, !isExpanding);
            mCouponDatas.get(position).setExpand(!isExpanding);

            holder.expandImg.setImageDrawable(mContext.getDrawable(isExpanding ? R.drawable.expand_more : R.drawable.expand_less));
        });

        holder.brandOmitTv.setText(mCouponDatas.get(position).getBrandNameOmit());
    }

    /**
     *
     */
    private void expandView(View view, boolean isExpand) {

        float startValue = 0.0f;
        float endValue = 0.0f;

        if (isExpand) {
            endValue = 1.0f;
        } else {
            startValue = 1.0f;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(startValue, endValue);
        animator.addUpdateListener((updateListener) -> {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
                layoutParams.height = (int)(400 * (float)updateListener.getAnimatedValue());
                view.setLayoutParams(layoutParams);

                view.setAlpha((float)updateListener.getAnimatedValue());
                });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setAlpha(isExpand ? 0.0f : 1.0f);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    @Override
    public int getItemCount() {
        return mCouponDatas.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView expandImg;
        TextView detailInfoTv;
        TextView brandOmitTv;

        private RecyclerHolder(View itemView) {
            super(itemView);
            expandImg = itemView.findViewById(R.id.coupon_expand_img);
            detailInfoTv = itemView.findViewById(R.id.coupon_detail_info_tv);
            brandOmitTv = itemView.findViewById(R.id.coupon_brand_name_omit_tv);
        }
    }
}

