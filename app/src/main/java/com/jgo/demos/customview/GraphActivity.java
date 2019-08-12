package com.jgo.demos.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jgo.demos.R;
import com.jgo.demos.customview.views.PieView;
import com.jgo.demos.customview.views.RadarView;
import com.jgo.demos.customview.views.StackAreaView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ke-oh on 2019/08/08.
 *
 */

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    StackAreaView mStackAreaView;
    RadarView mRadarView;
    PieView mPieView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mRadarView = findViewById(R.id.radar_view);
        findViewById(R.id.show_radar_btn).setOnClickListener(this);

        mStackAreaView = findViewById(R.id.stack_area_view);
        findViewById(R.id.start_stack).setOnClickListener(this);

        mPieView = findViewById(R.id.pie_view);
        findViewById(R.id.start_pie).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_radar_btn :
                List<RadarView.RadarData> radarDataList = new ArrayList();
                RadarView.RadarData radarData_1 = new RadarView.RadarData((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
                RadarView.RadarData radarData_2 = new RadarView.RadarData((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
                radarDataList.add(radarData_1);
                radarDataList.add(radarData_2);

                mRadarView.setRadarData(radarDataList);
                break;

            case R.id.start_stack :
                mStackAreaView.clearDatas();

                String[] axis = new String[] {"Mon", "Tus", "Wed", "Thu", "Fri", "Sat", "Sun"};
                mStackAreaView.setXAxis(axis);

                Map<String, Integer> values_1 = new HashMap<>();
                values_1.put("Mon", (int) (Math.random() * 1000));
                values_1.put("Tus", (int) (Math.random() * 1000));
                values_1.put("Wed", (int) (Math.random() * 1000));
                values_1.put("Thu", (int) (Math.random() * 1000));
                values_1.put("Fri", (int) (Math.random() * 1000));
                values_1.put("Sat", (int) (Math.random() * 1000));
                values_1.put("Sun", (int) (Math.random() * 1000));
                mStackAreaView.addCategory(new StackAreaView.Category(values_1));

                Map<String, Integer> values_2 = new HashMap<>();
                values_2.put("Mon", (int) (Math.random() * 1000));
                values_2.put("Tus", (int) (Math.random() * 1000));
                values_2.put("Wed", (int) (Math.random() * 1000));
                values_2.put("Thu", (int) (Math.random() * 1000));
                values_2.put("Fri", (int) (Math.random() * 1000));
                values_2.put("Sat", (int) (Math.random() * 1000));
                values_2.put("Sun", (int) (Math.random() * 1000));
                mStackAreaView.addCategory(new StackAreaView.Category(values_2));
                mStackAreaView.showData();
                break;

            case R.id.start_pie :

                List<PieView.PieFruit> dataList = new ArrayList<>();

                int appleValue = (int) (Math.random() * 20);
                if (appleValue == 0) appleValue = 10;
                dataList.add(new PieView.PieFruit("Apple", appleValue));
                dataList.add(new PieView.PieFruit("Banana", 40 - appleValue));

                int pearValue = (int) (Math.random() * 30);
                if (pearValue == 0) pearValue = 10;
                dataList.add(new PieView.PieFruit("Pear", pearValue));
                dataList.add(new PieView.PieFruit("Orange", 50 - pearValue));
                dataList.add(new PieView.PieFruit("Grape", 10));

                mPieView.setData(dataList);

        }
    }
}
