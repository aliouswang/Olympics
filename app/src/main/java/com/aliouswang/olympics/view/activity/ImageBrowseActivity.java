package com.aliouswang.olympics.view.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.aliouswang.entities.images.TimeLineImage;
import com.aliouswang.olympics.R;
import com.aliouswang.olympics.view.adapter.ImageBrowseAdapter;
import com.bumptech.glide.Glide;
import com.hmzl.library.core.utils.IntentUtil;
import com.hmzl.library.core.view.activity.BaseActivity;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

import java.util.ArrayList;

import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by aliouswang on 16/3/23.
 */
public class ImageBrowseActivity extends BaseActivity{

    private ViewPager mViewPager;
    private ImageBrowseAdapter mImageBrowseAdapter;

    private ArrayList<TimeLineImage> mTimeLineImageList;
    private int mInitIndex;

    private ExitActivityTransition exitTransition;

    private ImageView mTragetImageView;

    @Override
    protected int getInflateLayout() {
        return R.layout.act_image_browse_layout;
    }

    @Override
    protected void parseIntent(Intent intent) {
        mTimeLineImageList = IntentUtil.parseObject(intent, IntentUtil.POJO_NAME);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView() {
        mViewPager = ButterKnife.findById(this, R.id.view_pager);
        setupImageBrowseViewPager();
    }

    @Override
    protected void loadData() {

    }

    private void setupImageBrowseViewPager() {
        ArrayList<View> viewList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (TimeLineImage diaryImage : mTimeLineImageList) {
            View view = inflater.inflate(R.layout.image_browse_item_layout, null);
            viewList.add(view);
            PhotoView imageView =
                    (PhotoView) view;
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
            Glide.with(this).load(diaryImage.bmiddle_pic).into(imageView);

            if (mTragetImageView == null) {
                mTragetImageView = imageView;
            }
        }
        mImageBrowseAdapter = new ImageBrowseAdapter();
        mImageBrowseAdapter.setImageUrlList(viewList);
        mViewPager.setAdapter(mImageBrowseAdapter);

        exitTransition = ActivityTransition.with(getIntent()).
                to(mTragetImageView).duration(400).start(mSaveInstanceState);
    }
}
