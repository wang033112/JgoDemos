package com.jgo.demos.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by ke-oh on 2019/06/15.
 *
 */
public class SampleGroup {

    public String title;

    public List<Sample> samples;

    private boolean isExpanded;

    public SampleGroup(String title) {
        this.title = title;
        this.samples = new ArrayList<>();
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public void addSample(Sample sample) {
        samples.add(sample);
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

}
