package com.jgo.demos.listview.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jgo.demos.R;
import com.jgo.demos.listview.data.ImageData;
import com.jgo.demos.listview.data.PersonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/06/16.
 *
 */
public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.RecyclerHolder> {
    private Context mContext;
    private List<PersonData> mPersonDatas = new ArrayList<>();

    public PersonRecyclerViewAdapter(Context context, List<PersonData> personDatas) {
        this.mContext = context;
        this.mPersonDatas = personDatas;
    }

    /**
     * Set the image data.
     *
     * @param dataList image data
     */
    public void setData(List<PersonData> dataList) {
        if (null != dataList) {
            mPersonDatas.clear();
            mPersonDatas.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_person_list_view_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        holder.cardView.setVisibility(mPersonDatas.get(position).isHiddenImg() ? View.INVISIBLE : View.VISIBLE);
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext, mPersonDatas.get(position).getMipmapId())).into(holder.imageView);

        holder.titleTextView.setText(mPersonDatas.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mPersonDatas.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        CardView cardView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.person_list_view_img);
            titleTextView = itemView.findViewById(R.id.person_list_item_title);
            cardView = itemView.findViewById(R.id.person_list_view_img_parent);
        }
    }

    /**
     * whether The item at position is hidden.
     * @param position
     * @return
     */
    public boolean isImgHiddenAtPosition(int position) {
        return position < 0 || position >= mPersonDatas.size() || mPersonDatas.get(position).isHiddenImg();
    }

    /**
     * Set the image at postion hidden or not.
     *
     * @param position position at list
     * @param isHidden true:hidden
     */
    public void setImgHiddenAtPosition(int position, boolean isHidden) {
        if (position < 0 || position >= mPersonDatas.size()) {
            return;
        }
        mPersonDatas.get(position).setHiddenImg(isHidden);
        notifyDataSetChanged();
    }

    /**
     * Get the image src id at position
     *
     * @param position
     * @return
     */
    public int getImgSrcAtPosition(int position) {
        if (position < 0 || position >= mPersonDatas.size()) {
            return -1;
        }
        return mPersonDatas.get(position).getMipmapId();
    }
}

