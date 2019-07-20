package com.jgo.demos.listview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jgo.demos.R;
import com.jgo.demos.listview.data.OverlayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/07/20.
 *
 */
public class OverlayRecyclerViewAdapter extends RecyclerView.Adapter<OverlayRecyclerViewAdapter.RecyclerHolder> {
    private Context mContext;
    private List<OverlayData> mOverlayData = new ArrayList<>();

    public OverlayRecyclerViewAdapter(Context context, List<OverlayData> mOverlayData) {
        this.mContext = context;
        this.mOverlayData = mOverlayData;
    }

    /**
     * Set the image data.
     *
     * @param dataList image data
     */
    public void setData(List<OverlayData> dataList) {
        if (null != dataList) {
            mOverlayData.clear();
            mOverlayData.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_overlay_list_view_item, parent, false);
        return new RecyclerHolder(view);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.titleTv.setText(String.format(mContext.getString(R.string.overlay_list_sample_text, mOverlayData.get(position).getTitle())));
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext, mOverlayData.get(position).getMipmapId())).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mOverlayData.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTv;

        private RecyclerHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.overlay_view_img);
            titleTv = (TextView) itemView.findViewById(R.id.overlay_title_tv);
        }
    }
}

