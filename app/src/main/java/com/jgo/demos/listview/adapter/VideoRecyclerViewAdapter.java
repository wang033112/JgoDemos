package com.jgo.demos.listview.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jgo.demos.R;
import com.jgo.demos.listview.data.ImageData;
import com.jgo.demos.listview.data.VideoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/07/02.
 *
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.RecyclerHolder> {
    private Context mContext;
    private List<VideoData> mImageDatas = new ArrayList<>();

    public VideoRecyclerViewAdapter(Context context, List<VideoData> imageDatas) {
        this.mContext = context;
        this.mImageDatas = imageDatas;
    }

    /**
     * Set the image data.
     *
     * @param dataList image data
     */
    public void setData(List<VideoData> dataList) {
        if (null != dataList) {
            mImageDatas.clear();
            mImageDatas.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_video_list_view_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        //holder.imageView.setText(dataList.get(position));
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext, mImageDatas.get(position).getMipmapId())).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImageDatas.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.video_list_view_img);
        }
    }
}

