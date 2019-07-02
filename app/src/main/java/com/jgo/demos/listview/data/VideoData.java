package com.jgo.demos.listview.data;

/**
 * Created by ke-oh on 2019/07/02.
 *
 */

public class VideoData {

    private String title;
    private int mipmapId;

    public VideoData(String title, int mipmapId) {
        this.title = title;
        this.mipmapId = mipmapId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMipmapId() {
        return mipmapId;
    }

    public void setMipmapId(int mipmapId) {
        this.mipmapId = mipmapId;
    }
}
