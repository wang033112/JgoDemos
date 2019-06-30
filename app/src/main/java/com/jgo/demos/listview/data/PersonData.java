package com.jgo.demos.listview.data;

/**
 * Created by ke-oh on 2019/06/29.
 *
 */

public class PersonData {

    private String title;
    private int mipmapId;
    private String content;
    private boolean isHiddenImg;

    public PersonData(String title, int mipmapId) {
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


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHiddenImg() {
        return isHiddenImg;
    }

    public void setHiddenImg(boolean hiddenImg) {
        isHiddenImg = hiddenImg;
    }
}
