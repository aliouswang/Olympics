package com.aliouswang.olympics.view.activity;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aliouswang.entities.images.TimeLineImage;
import com.aliouswang.olympics.R;
import com.aliouswang.olympics.view.adapter.ImageBrowseAdapter;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hmzl.library.core.manager.ImageManager;
import com.hmzl.library.core.utils.IntentUtil;
import com.hmzl.library.core.view.activity.BaseActivity;
import com.kogitune.activity_transition.ExitActivityTransition;

import java.util.ArrayList;

import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by aliouswang on 16/3/23.
 */
public class ImageBrowseActivity extends BaseActivity{

    public static final int ANIM_DURATION = 400;

    private ViewPager mViewPager;
    private ImageBrowseAdapter mImageBrowseAdapter;

    private ArrayList<TimeLineImage> mTimeLineImageList;
    private int mInitIndex;

    private ExitActivityTransition exitTransition;

    private ImageView mTragetImageView;

    private FrameLayout frame_root_view;

    private SimpleDraweeView mMirrorImageView;

    ColorDrawable colorDrawable;

    private int left;
    private int top;
    private int width;
    private int height;

    private int mLeftDelta;
    private int mTopDelta;

    private float mWidthScale;
    private float mHeightScale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mMirrorImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mMirrorImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    mMirrorImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = left - screenLocation[0];
                    mTopDelta = top - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) width / mMirrorImageView.getWidth();
                    mHeightScale = (float) height / mMirrorImageView.getHeight();

                    enterAnimation();
                    return true;
                }
            });
        }
    }

    public void enterAnimation() {
        mMirrorImageView.setPivotX(0);
        mMirrorImageView.setPivotY(0);
        mMirrorImageView.setScaleX(mWidthScale);
        mMirrorImageView.setScaleY(mHeightScale);
        mMirrorImageView.setTranslationX(mLeftDelta);
        mMirrorImageView.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        mMirrorImageView.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator)
        .withEndAction(new Runnable() {
            @Override
            public void run() {
                mViewPager.setVisibility(View.VISIBLE);
                mMirrorImageView.setVisibility(View.GONE);
            }
        });


        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    public void exitAnimation(final Runnable endAction) {
        mViewPager.setVisibility(View.GONE);
        mMirrorImageView.setVisibility(View.VISIBLE);
        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        mMirrorImageView.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.act_image_browse_layout;
    }

    @Override
    protected void parseIntent(Intent intent) {
        mTimeLineImageList = IntentUtil.parseObject(intent, IntentUtil.POJO_NAME);
        left = intent.getIntExtra("left", 0);
        top = intent.getIntExtra("top", 0);
        width = intent.getIntExtra("width", 0);
        height = intent.getIntExtra("height", 0);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView() {
        mViewPager = ButterKnife.findById(this, R.id.view_pager);
        frame_root_view = ButterKnife.findById(this, R.id.frame_root_view);
        mMirrorImageView = ButterKnife.findById(this, R.id.drawee_mirror_view);
//        Glide.with(this).load(mTimeLineImageList.get(0).bmiddle_pic).into(mMirrorImageView);
        ImageManager.loadImageWithFresco(mMirrorImageView, mTimeLineImageList.get(0).bmiddle_pic);
        setupImageBrowseViewPager();

        colorDrawable = new ColorDrawable(Color.BLACK);
        frame_root_view.setBackground(colorDrawable);
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

//            if (mTragetImageView == null) {
//                mTragetImageView = imageView;
//                if (Build.VERSION.SDK_INT >= 21) {
//                    imageView.setTransitionName("scene_transition");
//                }
//            }
        }
        mImageBrowseAdapter = new ImageBrowseAdapter();
        mImageBrowseAdapter.setImageUrlList(viewList);
        mViewPager.setAdapter(mImageBrowseAdapter);

//        exitTransition = ActivityTransition.with(getIntent()).
//                to(mTragetImageView).duration(400).start(mSaveInstanceState);
    }

    @Override
    public void onBackPressed() {
//        exitTransition.exit(this);
//        super.onBackPressed();
        exitAnimation(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}
