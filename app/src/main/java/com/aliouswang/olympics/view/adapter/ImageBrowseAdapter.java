package com.aliouswang.olympics.view.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/3/23.
 */
public class ImageBrowseAdapter extends PagerAdapter{

    private ArrayList<View> viewList = new ArrayList<>();

    public ImageBrowseAdapter() {

    }

    public void setImageUrlList(ArrayList<View> imageUrlList) {
        this.viewList = imageUrlList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        GestureImageView imageView =
//                (GestureImageView) viewList.get(position);
//        imageView.getController().getSettings()
//                .setFillViewport(true)
//                .setPanEnabled(true)
//                .setZoomEnabled(true)
//                .setDoubleTapEnabled(true)
//                .setOverzoomFactor(2.0f)
//                .setGravity(Gravity.CENTER);
        container.addView(viewList.get(position),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return viewList.get(position);
    }

}
