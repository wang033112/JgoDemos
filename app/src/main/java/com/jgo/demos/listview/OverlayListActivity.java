package com.jgo.demos.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.jgo.demos.R;
import com.jgo.demos.listview.adapter.OverlayRecyclerViewAdapter;
import com.jgo.demos.listview.data.OverlayData;
import com.jgo.demos.listview.views.OverlayLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/07/20.
 *
 */

public class OverlayListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private OverlayRecyclerViewAdapter mAdapter;
    private int[] images = {
            R.mipmap.image_bench, R.mipmap.image_moutain, R.mipmap.image_sun,
            R.mipmap.image_tree, R.mipmap.image_wind,R.mipmap.picture5,
            R.mipmap.picture6, R.mipmap.picture7, R.mipmap.picture8,
            R.mipmap.picture9, R.mipmap.picture10, R.mipmap.picture11,
            R.mipmap.picture12, R.mipmap.picture13};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay_list_view);
        mRecyclerView = findViewById(R.id.overlay_list_view);
        mRecyclerView.setLayoutManager(new OverlayLayoutManager());

        mAdapter = new OverlayRecyclerViewAdapter(getApplicationContext(), buildImageData());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * ダミーデータを作成する。
     *
     * @return ダミーデータ
     */
    private List<OverlayData> buildImageData() {
        List<OverlayData> overlayData = new ArrayList<>();

        //for (int i = 0; i < 10; i++) {
            /*overlayData.add(new OverlayData("Image_Bench_" + i, images[0]));
            overlayData.add(new OverlayData("Image_Moutain_" + i, images[1]));
            overlayData.add(new OverlayData("Image_Sun_" + i, images[2]));
            overlayData.add(new OverlayData("Image_Tree_" + i, images[3]));
            overlayData.add(new OverlayData("Image_Wind_" + i, images[4]));*/
        //}

            for (int i = 0; i < 14; i++) {
               overlayData.add(new OverlayData("Index_" + i, images[i]));
            }
            /*overlayData.add(new OverlayData("Image_Bench_", images[0]));
            overlayData.add(new OverlayData("Image_Moutain_", images[1]));
            overlayData.add(new OverlayData("Image_Sun_", images[2]));
            overlayData.add(new OverlayData("Image_Tree_", images[3]));
            overlayData.add(new OverlayData("Image_Wind_", images[4]));

            overlayData.add(new OverlayData("Image_Bench_1", images[5]));
            overlayData.add(new OverlayData("Image_Moutain_1", images[6]));
            overlayData.add(new OverlayData("Image_Sun_1", images[7]));
            overlayData.add(new OverlayData("Image_Tree_1", images[8]));
            overlayData.add(new OverlayData("Image_Wind_1", images[9]));

            overlayData.add(new OverlayData("Image_Bench_1", images[10]));
            overlayData.add(new OverlayData("Image_Moutain_1", images[11]));
            overlayData.add(new OverlayData("Image_Sun_1", images[12]));
            overlayData.add(new OverlayData("Image_Sun_1", images[13]));*/

        return overlayData;
    }
}
