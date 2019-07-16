package com.jgo.demos.listview.data;

import java.util.Date;

/**
 * Created by ke-oh on 2019/07/15.
 *
 */

public class CouponData {

    private String id;
    private String brandNameOmit;
    private String brandNameAll;
    private int countOff;
    private String date;
    private String description;
    private int cnt;
    private String content;
    private boolean isExpand;

    public CouponData(String id, String brandNameOmit) {
        this.id = id;
        this.brandNameOmit = brandNameOmit;
    }

    public String getBrandNameOmit() {
        return brandNameOmit;
    }

    public void setBrandNameOmit(String brandNameOmit) {
        this.brandNameOmit = brandNameOmit;
    }

    public String getBrandNameAll() {
        return brandNameAll;
    }

    public void setBrandNameAll(String brandNameAll) {
        this.brandNameAll = brandNameAll;
    }

    public int getCountOff() {
        return countOff;
    }

    public void setCountOff(int countOff) {
        this.countOff = countOff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
