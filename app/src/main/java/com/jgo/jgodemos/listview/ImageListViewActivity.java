package com.jgo.jgodemos.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgo.jgodemos.R;
import com.jgo.jgodemos.listview.adapter.ImageRecyclerViewAdapter;
import com.jgo.jgodemos.listview.data.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/06/16.
 *
 */

public class ImageListViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageRecyclerViewAdapter mAdapter;
    private int[] images = {R.mipmap.image_bench, R.mipmap.image_moutain,
            R.mipmap.image_sun, R.mipmap.image_tree, R.mipmap.image_wind};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_view);
        mRecyclerView = findViewById(R.id.image_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mAdapter = new ImageRecyclerViewAdapter(getApplicationContext(), buildImageData());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * ダミーデータを作成する。
     *
     * @return ダミーデータ
     */
    private List<ImageData> buildImageData() {
        List<ImageData> imageData = new ArrayList<>();
        imageData.add(new ImageData("Image_Bench", images[0]));
        imageData.add(new ImageData("Image_Moutain", images[1]));
        imageData.add(new ImageData("Image_Sun", images[2]));
        imageData.add(new ImageData("Image_Tree", images[3]));
        imageData.add(new ImageData("Image_Wind", images[4]));

        return imageData;
    }
}
