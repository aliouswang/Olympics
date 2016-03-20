package com.hmzl.library.core.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hmzl.library.core.R;

/**
 * Created by aliouswang on 16/3/14.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_content;

    public BaseRecyclerViewHolder(View itemView , int viewType) {
        super(itemView);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
    }


}
