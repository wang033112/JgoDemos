package com.jgo.demos.transition;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jgo.demos.R;
import com.jgo.demos.transition.fragment.RecyclerGridFragment;
import com.jgo.demos.transition.adapter.ImageData;

/**
 * Created by ke-oh on 2019/07/14.
 *
 */

@SuppressLint("Registered")
public class ShareElementActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_POSITION = "key_current_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_share_element_container, new RecyclerGridFragment(), "")
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, ImageData.getCurrentPosition());
    }
}
