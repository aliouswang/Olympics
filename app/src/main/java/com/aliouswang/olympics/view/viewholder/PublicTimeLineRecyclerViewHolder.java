package com.aliouswang.olympics.view.viewholder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliouswang.olympics.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hmzl.library.core.view.viewholder.BaseRecyclerViewHolder;
import com.hmzl.library.core.widget.SquareGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aliouswang on 16/3/14.
 */
public class PublicTimeLineRecyclerViewHolder extends BaseRecyclerViewHolder {

    @Bind(R.id.card_root_view)
    public CardView card_root_view;

    @Bind(R.id.img_avatar_head)
    public SimpleDraweeView img_avatar_head;

    @Bind(R.id.img_avatar_vip_tag)
    public ImageView img_avatar_vip_tag;

    @Bind(R.id.tv_avatar_name)
    public TextView tv_avatar_name;

    @Bind(R.id.tv_source)
    public TextView tv_source;

    @Bind(R.id.tv_timeline_content)
    public TextView tv_timeline_content;

    //    @Bind(R.id.img_timeline)
    public SimpleDraweeView img_timeline;

    //    @Bind(R.id.square_grid_view)
    public SquareGridView squareGridView;

    public PublicTimeLineRecyclerViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
        ButterKnife.bind(this, itemView);
        if (viewType == 0) {
            this.img_timeline = (SimpleDraweeView) itemView.findViewById(R.id.img_timeline);
        } else if (viewType == 1) {
            this.squareGridView = (SquareGridView) itemView.findViewById(R.id.square_grid_view);
        }

    }


}
