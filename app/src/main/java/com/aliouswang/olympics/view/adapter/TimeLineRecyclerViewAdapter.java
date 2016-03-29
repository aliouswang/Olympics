package com.aliouswang.olympics.view.adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.aliouswang.entities.feeds.TimeLine;
import com.aliouswang.olympics.R;
import com.aliouswang.olympics.interfaces.OnTimeLineItemClickListener;
import com.aliouswang.olympics.view.viewholder.PublicTimeLineRecyclerViewHolder;
import com.hmzl.library.core.manager.ImageManager;
import com.hmzl.library.core.utils.PopupMenuUtil;
import com.hmzl.library.core.utils.SnackBarUtil;
import com.hmzl.library.core.view.adapter.BaseRecyclerViewAdapter;
import com.hmzl.library.core.widget.SquareViewAdapter;

/**
 * Created by aliouswang on 16/3/15.
 */
public class TimeLineRecyclerViewAdapter
        extends BaseRecyclerViewAdapter<TimeLine, PublicTimeLineRecyclerViewHolder> {

    private OnTimeLineItemClickListener onTimeLineItemClickListener;

    public TimeLineRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void setOnTimeLineItemClickListener(OnTimeLineItemClickListener onTimeLineItemClickListener) {
        this.onTimeLineItemClickListener = onTimeLineItemClickListener;
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
        } else {
            holder.img_avatar_vip_tag.setVisibility(View.GONE);
        }
        //set vartar name
        holder.tv_avatar_name.setText(data.user.name);

        holder.tv_source.setText("来自于   " + data.getSourceDesc());

        holder.tv_timeline_content.setText(data.text);

        if (viewType == 0) {
            if (!TextUtils.isEmpty(data.original_pic)) {
                holder.img_timeline.setVisibility(View.VISIBLE);
//                ImageManager.loadImageWithGlide(context, holder.img_timeline, data.original_pic);
                ImageManager.loadImageWithFresco(holder.img_timeline, data.original_pic);
            } else {
                holder.img_timeline.setVisibility(View.GONE);
            }
        } else if (viewType == 1) {
            holder.squareGridView.setAdapter(new SquareViewAdapter() {
                @Override
                public int getCount() {
                    return data.pic_urls.size();
                }

                @Override
                public String getImageUrl(int position) {
                    return data.pic_urls.get(position).bmiddle_pic;
                }

                @Override
                public void onItemClick(View view, int index, Object o) {
                    if (onTimeLineItemClickListener != null) {
                        onTimeLineItemClickListener.onClick(view, data);
                    }
                }
            });
        }

//        holder.card_root_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onTimeLineItemClickListener != null) {
//                    onTimeLineItemClickListener.onClick(v, data);
//                }
//            }
//        });


        holder.img_menu_overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenuUtil.showPopupMenu(context,
                        holder.img_menu_overflow,
                        R.menu.menu_home_timeline,
                        new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.copy_timeline) {
                                    ClipboardManager cm = (ClipboardManager)
                                            context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cm.setText(data.text);
                                    SnackBarUtil.showSnackBar((Activity) context, "拷贝成功");
                                }
                                return false;
                            }
                        });
            }
        });

    }

    protected int[] getInflateLayoutArray() {
        return new int[]{R.layout.timeline_list_item_layout,
                R.layout.base_recycler_item_with_grid_images_layout};
    }

    @Override
    public int getItemViewType(int position) {
        TimeLine timeLine = getDataByPosition(position);
        if (timeLine != null && timeLine.pic_urls != null
                && timeLine.pic_urls.size() > 1) {
            return 1;
        } else return 0;
    }
}
