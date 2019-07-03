package com.jgo.demos.listview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jgo.demos.R;
import com.jgo.demos.listview.data.VideoData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/07/02.
 *
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.RecyclerHolder>
                                         {
    private Context mContext;
    private List<VideoData> mImageDatas = new ArrayList<>();
    private Handler mHandler;

    public VideoRecyclerViewAdapter(Context context, List<VideoData> imageDatas) {
        this.mContext = context;
        this.mImageDatas = imageDatas;

        mHandler = new Handler();
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
        //Glide.with(mContext).load(ContextCompat.getDrawable(mContext, mImageDatas.get(position).getMipmapId())).into(holder.textureView);

        if (holder.textureView == null) {
            holder.textureView = new TextureView(mContext);
            holder.textureView.setSurfaceTextureListener(holder);

            holder.textureViewLayout.removeAllViews();
            holder.textureViewLayout.addView(holder.textureView);
        }


        if (holder.mediaPlayer == null) {
            holder.mediaPlayer = new MediaPlayer();
            holder.mediaPlayer.setOnCompletionListener(holder);
            holder.mediaPlayer.setVolume(0, 0);
            try {
                holder.mediaPlayer.setDataSource(mContext.getApplicationContext(), Uri.parse("android.resource://com.jgo.demos/" + R.raw.jgo_demo_video));
                holder.mediaPlayer.setOnPreparedListener(holder);
                holder.mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mImageDatas.size();
    }



    class RecyclerHolder extends RecyclerView.ViewHolder
            implements TextureView.SurfaceTextureListener,
            MediaPlayer.OnPreparedListener,View.OnClickListener,
            MediaPlayer.OnCompletionListener,
            View.OnTouchListener {
        FrameLayout textureViewLayout;
        TextureView textureView;
        MediaPlayer mediaPlayer;
        ImageView playerActionImg;
        FrameLayout playerActionLayout;

        boolean isMediaPlayerPrepared;
        boolean isTextureAvailable;
        SurfaceTexture surface;

        @SuppressLint("ClickableViewAccessibility")
        private RecyclerHolder(View itemView) {
            super(itemView);
            textureViewLayout = itemView.findViewById(R.id.video_texture_view_layout);
            playerActionImg = itemView.findViewById(R.id.video_list_view_action_img);
            playerActionImg.setOnClickListener(this);

            playerActionLayout = itemView.findViewById(R.id.video_list_view_action_layout);
            playerActionLayout.setOnTouchListener(this);
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            isTextureAvailable = true;
            this.surface = surface;
            if (isMediaPlayerPrepared) {
                startMediaPlayer();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            isMediaPlayerPrepared = true;
            if (isTextureAvailable) {
                startMediaPlayer();
            }
        }

        private void startMediaPlayer() {
            mediaPlayer.setSurface(new Surface(surface));
            playerActionImg.setImageDrawable(mContext.getDrawable(R.drawable.play_back_70dp));
            //mediaPlayer.start();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.video_list_view_action_img :
                    if (!isTextureAvailable || !isMediaPlayerPrepared) {
                        return;
                    }

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playerActionImg.setVisibility(View.VISIBLE);
                        playerActionImg.setImageDrawable(mContext.getDrawable(R.drawable.play_back_70dp));
                    } else {
                        mediaPlayer.start();
                        playerActionImg.setVisibility(View.INVISIBLE);
                        playerActionImg.setImageDrawable(mContext.getDrawable(R.drawable.pause_70dp));
                    }
                    break;
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            playerActionImg.setVisibility(View.VISIBLE);
            playerActionImg.setImageDrawable(mContext.getDrawable(R.drawable.play_back_70dp));
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mediaPlayer.isPlaying()) {
                playerActionImg.setVisibility(
                        playerActionImg.getVisibility() != View.VISIBLE ? View.VISIBLE : View.INVISIBLE);

                mHandler.postDelayed(() -> {
                    if (mediaPlayer.isPlaying()) {
                        playerActionImg.setVisibility(View.INVISIBLE);
                    }
                }, 2000);
            }
            
            return false;
        }




    }
}

