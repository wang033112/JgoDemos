package com.jgo.demos;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jgo.demos.adapter.ExpandableListViewAdapter;
import com.jgo.demos.data.SampleGroup;
import com.jgo.demos.util.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpandableListViewAdapter.OnItemClickListener {

    private static final String JSON_FILE = "home_list.json";
    private TextView mTextMessage;
    private ExpandableListView mListView;
    private ExpandableListViewAdapter mListAdapter;

    private HandlerThread mWorkThread;
    private Handler mUIHandler;
    private Handler mWorkHandler;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mListView = findViewById(R.id.main_expandable_listview);
        mListAdapter = new ExpandableListViewAdapter(getApplicationContext());
        mListAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mListAdapter);
        mListView.setGroupIndicator(null);

        showJsonToList();
    }

    /**
     * Jsonを読み込んで、リストに表示する。
     */
    private void showJsonToList() {
        mUIHandler = new Handler();
        mWorkThread = new HandlerThread("WorkThread");
        mWorkThread.start();
        mWorkHandler = new Handler(mWorkThread.getLooper());
        mWorkHandler.post(() -> {
            String jsonStr = Utils.parseJsonToString(getApplicationContext(), JSON_FILE);
            ArrayList<SampleGroup> groupArrayList = Utils.parseStringToGSON(jsonStr);
            mUIHandler.post(() -> mListAdapter.setSampleGroups(groupArrayList));
        });
    }

    @Override
    public void onChildItemtClick(String name) {
        Intent intent = new Intent();
        switch (name) {
            case "landscape" :
                intent.setComponent(new ComponentName("com.jgo.demos","com.jgo.demos.listview.ImageListViewActivity"));
                break;

            case "PersonList" :
                intent.setComponent(new ComponentName("com.jgo.demos","com.jgo.demos.listview.PersonListActivity"));
                break;

            case "VideoList" :
                intent.setComponent(new ComponentName("com.jgo.demos","com.jgo.demos.listview.VideoListViewActivity"));
                break;

            case "Activity_transition" :
                intent.setComponent(new ComponentName("com.jgo.demos","com.jgo.demos.transition.TransitionActivity"));
                break;

            case "ShareElementActivity" :
                intent.setComponent(new ComponentName("com.jgo.demos","com.jgo.demos.transition.ShareElementActivity"));
                break;
        }

        startActivity(intent);
    }
}
