package com.aliouswang.olympics.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aliouswang.entities.feeds.TimeLine;
import com.aliouswang.olympics.R;
import com.aliouswang.olympics.view.viewholder.PublicTimeLineRecyclerViewHolder;
import com.hmzl.library.core.manager.ImageManager;
import com.hmzl.library.core.view.adapter.BaseRecyclerViewAdapter;
import com.hmzl.library.core.widget.SquareViewAdapter;

/**
 * Created by aliouswang on 16/3/15.
 */
public class TimeLineRecyclerViewAdapter
        extends BaseRecyclerViewAdapter<TimeLine, PublicTimeLineRecyclerViewHolder>{

    public TimeLineRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected PublicTimeLineRecyclerViewHolder createViewHolder(View view, int viewType) {
        return new PublicTimeLineRecyclerViewHolder(view, viewType);
    }

    @Override
    protected void bindViewHolder(final PublicTimeLineRecyclerViewHolder holder, final TimeLine data,
                                  int viewType) {
        ImageManager.loadImageWithFresco(holder.img_avatar_head, data.user.avatar_large);

        //set vartar vip tag
        if (data.user.verified) {
            holder.img_avatar_vip_tag.setVisibility(View.VISIBLE);
        }else {
            holder.img_avatar_vip_tag.setVisibility(View.GONE);
        }
        //set vartar name
        holder.tv_avatar_name.setText(data.user.name);

        holder.tv_source.setText("来自于   " + data.getSourceDesc());

        holder.tv_timeline_content.setText(data.text);

        if (viewType == 0) {
            if (!TextUtils.isEmpty(data.original_pic)) {
                holder.img_timeline.setVisibility(View.VISIBLE);
                ImageManager.loadImageWithGlide(context, holder.img_timeline, data.original_pic);
            }else {
                holder.img_timeline.setVisibility(View.GONE);
            }
        }else if (viewType == 1) {
            holder.squareGridView.setAdapter(new SquareViewAdapter() {
                @Override
                public int getCount() {
                    return data.pic_urls.size();
                }

                @Override
                public String getImageUrl(int position) {
                    return data.pic_urls.get(position).thumbnail_pic;
                }

                @Override
                public void onItemClick(View view, int index, Object o) {

                }
            });
        }

    }

    protected int [] getInflateLayoutArray() {
        return new int [] {R.layout.timeline_list_item_layout,
                R.layout.base_recycler_item_with_grid_images_layout};
    }

    @Override
    public int getItemViewType(int position) {
        TimeLine timeLine = getDataByPosition(position);
        if (timeLine != null && timeLine.pic_urls != null
                && timeLine.pic_urls.size() > 1) {
            return 1;
        }else return 0;
    }
}
