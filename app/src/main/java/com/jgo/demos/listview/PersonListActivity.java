package com.jgo.demos.listview;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgo.demos.R;
import com.jgo.demos.listview.adapter.ImageRecyclerViewAdapter;
import com.jgo.demos.listview.adapter.PersonRecyclerViewAdapter;
import com.jgo.demos.listview.data.ImageData;
import com.jgo.demos.util.DataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/06/28.
 *
 */

public class PersonListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PersonRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list_view);
        mRecyclerView = findViewById(R.id.person_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mAdapter = new PersonRecyclerViewAdapter(getApplicationContext(), DataManager.getInstance().getPersonList(getApplication()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
