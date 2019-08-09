package com.jgo.demos.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jgo.demos.R;
import com.jgo.demos.customview.views.RadarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/08/08.
 *
 */

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    RadarView mRadarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mRadarView = findViewById(R.id.radar_view);

        findViewById(R.id.show_radar_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_radar_btn:
                List<RadarView.RadarData> radarDataList = new ArrayList();
                RadarView.RadarData radarData_1 = new RadarView.RadarData((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
                RadarView.RadarData radarData_2 = new RadarView.RadarData((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
                radarDataList.add(radarData_1);
                radarDataList.add(radarData_2);

                mRadarView.setRadarData(radarDataList);

        }
    }
}
