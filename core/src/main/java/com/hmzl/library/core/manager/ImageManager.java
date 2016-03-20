package com.hmzl.library.core.manager;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hmzl.library.core.R;

/**
 * Load image from disk, cloud for you.
 * Created by aliouswang on 16/3/18.
 */
public class ImageManager {

    public static void loadImageWithFresco(SimpleDraweeView simpleDraweeView,
                                           String url) {
        simpleDraweeView.setImageURI(Uri.parse(url));
    }

    public static void loadImageWithGlide(Context context, ImageView imageView,
                                          String url) {
        loadImageWithGlide(context, imageView, url, R.mipmap.default_photo_ic);
    }

    public static void loadImageWithGlide(Context context, ImageView imageView,
                                          String url, int placeHolder) {
        Glide.with(context).load(url).placeholder(placeHolder).into(imageView);
    }
}
