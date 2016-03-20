package com.hmzl.library.core.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmzl.library.core.R;
import com.hmzl.library.core.view.viewholder.BaseRecyclerViewHolder;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/3/14.
 */
public abstract class BaseRecyclerViewAdapter<T, H extends BaseRecyclerViewHolder> extends
        RecyclerView.Adapter<H> {

    protected Context context;
    private ArrayList<T> dataList;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<T> getDataList() {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        return dataList;
    }

    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void addDataList(ArrayList<T> newList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(newList);
        notifyDataSetChanged();
    }

    public T getDataByPosition(int position) {
        return getDataList().get(position);
    }

    protected abstract H createViewHolder(View view, int viewType);

    protected int [] getInflateLayoutArray() {
        return new int [] {R.layout.base_recycler_item_layout };
    }

    protected abstract void bindViewHolder(H holder, T data, int viewType);

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getInflateLayoutArray()[viewType], parent, false);
        return createViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        bindViewHolder(holder, getDataByPosition(position), getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }
}
