package com.hmzl.library.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hmzl.library.core.R;

/**
 * Created by aliouswang on 16/3/20.
 */
public class ScaleImageView extends ImageView{

    private float scale = 1.0f;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ScaleImageView, 0, 0);
        scale = t.getFloat(R.styleable.ScaleImageView_siv_scale, 1.0f);
        t.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = View.MeasureSpec.getSize(widthMeasureSpec);
        float height = width * scale;
        setMeasuredDimension((int)width, (int)height);
    }


}
