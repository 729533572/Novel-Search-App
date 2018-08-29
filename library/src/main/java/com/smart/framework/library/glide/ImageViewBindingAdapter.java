package com.smart.framework.library.glide;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by JoJo on 2018/8/29.
 * wechat：18510829974
 * description：Databinding中的ImageView与Glide结合使用,自定义ImageViewBindingAdapter
 */
public class ImageViewBindingAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        GlideHelper.loadImage(url, imageView, 0);
    }
}
