package com.jgo.demos.transition.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;

import com.jgo.demos.R;
import com.jgo.demos.transition.adapter.ImageData;
import com.jgo.demos.transition.adapter.RecyclerGridAdapter;

import java.util.List;
import java.util.Map;


public class RecyclerGridFragment extends Fragment {

  private RecyclerView recyclerView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_grid_for_recycler_view, container, false);
    recyclerView.setAdapter(new RecyclerGridAdapter(this));

    setExitTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.exit_transition));

    setExitSharedElementCallback(
            new SharedElementCallback() {
              @Override
              public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                RecyclerView.ViewHolder selectedViewHolder = recyclerView
                        .findViewHolderForAdapterPosition(ImageData.getCurrentPosition());
                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                  return;
                }

                sharedElements.put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
              }
            });
    postponeEnterTransition();

    return recyclerView;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    scrollToPosition();
  }

  /**
   * Scrolls the recycler view to show the last viewed item in the grid.
   * This is important when navigating back from the grid.
   */
  private void scrollToPosition() {
    recyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v,
          int left,
          int top,
          int right,
          int bottom,
          int oldLeft,
          int oldTop,
          int oldRight,
          int oldBottom) {
        recyclerView.removeOnLayoutChangeListener(this);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View viewAtPosition = layoutManager.findViewByPosition(ImageData.getCurrentPosition());
        if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
          recyclerView.post(() -> layoutManager.scrollToPosition(ImageData.getCurrentPosition()));
        }
      }
    });
  }

}
