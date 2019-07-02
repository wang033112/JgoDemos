package com.jgo.demos.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgo.demos.R;
import com.jgo.demos.listview.adapter.ImageRecyclerViewAdapter;
import com.jgo.demos.listview.adapter.VideoRecyclerViewAdapter;
import com.jgo.demos.listview.data.ImageData;
import com.jgo.demos.listview.data.VideoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/07/02.
 *
 */

public class VideoListViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private VideoRecyclerViewAdapter mAdapter;
    private int[] images = {R.mipmap.image_bench, R.mipmap.image_moutain,
            R.mipmap.image_sun, R.mipmap.image_tree, R.mipmap.image_wind};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list_view);
        mRecyclerView = findViewById(R.id.video_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mAdapter = new VideoRecyclerViewAdapter(getApplicationContext(), buildImageData());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * ダミーデータを作成する。
     *
     * @return ダミーデータ
     */
    private List<VideoData> buildImageData() {
        List<VideoData> videoData = new ArrayList<>();
        videoData.add(new VideoData("Image_Bench", images[0]));
        videoData.add(new VideoData("Image_Moutain", images[1]));
        videoData.add(new VideoData("Image_Sun", images[2]));
        videoData.add(new VideoData("Image_Tree", images[3]));
        videoData.add(new VideoData("Image_Wind", images[4]));

        return videoData;
    }
}
