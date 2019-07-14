package com.jgo.demos.transition.adapter;

import com.jgo.demos.R;
public class ImageData {

  static final int[] IMAGE_DRAWABLES = {
          R.mipmap.picture3,
          R.mipmap.picture4,
          R.mipmap.picture5,
          R.mipmap.picture6,
          R.mipmap.picture7,
          R.mipmap.picture8,
          R.mipmap.picture9,
          R.mipmap.picture10,
          R.mipmap.picture11,
          R.mipmap.picture12,
          R.mipmap.picture13,
  };

  public static int getCurrentPosition() {
    return currentPosition;
  }

  public static void setCurrentPosition(int currentPosition) {
    ImageData.currentPosition = currentPosition;
  }

  public static int currentPosition;
}
