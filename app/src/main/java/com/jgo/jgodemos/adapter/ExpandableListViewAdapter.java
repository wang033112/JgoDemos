package com.jgo.jgodemos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgo.jgodemos.R;
import com.jgo.jgodemos.data.Sample;
import com.jgo.jgodemos.data.SampleGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by wangjie on 2019/06/15.
 *
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter
        implements View.OnClickListener, ExpandableListView.OnGroupExpandListener,
                    ExpandableListView.OnGroupCollapseListener {

    private Context mContext;
    private List<SampleGroup> sampleGroups;

    public ExpandableListViewAdapter(Context context) {
        this.mContext = context;
        sampleGroups = Collections.emptyList();
    }

    public void setSampleGroups(List<SampleGroup> sampleGroups) {
        this.sampleGroups = sampleGroups;
        notifyDataSetChanged();
    }

    @Override
    public Sample getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).samples.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.sample_list_item, parent, false);
            View downloadButton = view.findViewById(R.id.download_button);
            downloadButton.setOnClickListener(this);
            downloadButton.setFocusable(false);
        }
        initializeChildView(view, getChild(groupPosition, childPosition));
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).samples.size();
    }

    @Override
    public SampleGroup getGroup(int groupPosition) {
        return sampleGroups.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, final View convertView,
                             ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext)
                            .inflate(R.layout.sample_list_title, parent, false);
        }
        TextView groupTitleView = view.findViewById(R.id.sample_group_title);
        groupTitleView.setText(getGroup(groupPosition).title);

        //
        ImageView expandImg = view.findViewById(R.id.sample_group_expand_img);
        view.setOnClickListener((v) -> {
            if (parent != null) {
                if (getGroup(groupPosition).isExpanded()) {
                    ((ExpandableListView)parent).collapseGroup(groupPosition);
                    getGroup(groupPosition).setExpanded(false);
                } else {
                    ((ExpandableListView)parent).expandGroup(groupPosition);
                    getGroup(groupPosition).setExpanded(true);
                }
            }
            expandImg.setImageDrawable(ContextCompat.getDrawable(mContext,
                    getGroup(groupPosition).isExpanded() ? R.drawable.expand_less : R.drawable.expand_more));
        });
        return view;
    }

    @Override
    public int getGroupCount() {
        return sampleGroups.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onClick(View view) {
        //onSampleDownloadButtonClicked((Sample) view.getTag());
    }

    private void initializeChildView(View view, Sample sample) {
        view.setTag(sample);
        TextView sampleTitle = view.findViewById(R.id.sample_title);
        sampleTitle.setText(sample.name);

        /*boolean canDownload = getDownloadUnsupportedStringId(sample) == 0;
        boolean isDownloaded = canDownload && downloadTracker.isDownloaded(((UriSample) sample).uri);
        ImageButton downloadButton = view.findViewById(R.id.download_button);
        downloadButton.setTag(sample);
        downloadButton.setColorFilter(
                canDownload ? (isDownloaded ? 0xFF42A5F5 : 0xFFBDBDBD) : 0xFFEEEEEE);
        downloadButton.setImageResource(
                isDownloaded ? R.drawable.ic_download_done : R.drawable.ic_download);*/
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        //
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        //
    }
}
