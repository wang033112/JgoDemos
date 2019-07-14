package com.jgo.demos.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgo.demos.R;
import com.jgo.demos.transition.adapter.ImageData;
import com.jgo.demos.transition.adapter.ImagePagerAdapter;

import java.util.List;
import java.util.Map;

public class ImagePagerFragment extends Fragment {

  private ViewPager viewPager;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    viewPager = (ViewPager) inflater.inflate(R.layout.fragment_share_element_pager, container, false);
    viewPager.setAdapter(new ImagePagerAdapter(this));

    viewPager.setCurrentItem(ImageData.getCurrentPosition());
    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        ImageData.setCurrentPosition(position);
      }
    });

    setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.image_shared_element_transition));

    setEnterSharedElementCallback(
            new SharedElementCallback() {
              @Override
              public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Fragment currentFragment = (Fragment) viewPager.getAdapter()
                        .instantiateItem(viewPager, ImageData.getCurrentPosition());
                View view = currentFragment.getView();
                if (view == null) {
                  return;
                }

                // Map the first shared element name to the child ImageView.
                sharedElements.put(names.get(0), view.findViewById(R.id.image));
              }
            });

    // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
    if (savedInstanceState == null) {
      postponeEnterTransition();
    }

    return viewPager;
  }
}
