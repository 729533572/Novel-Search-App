package com.smart.framework.library.glide;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.BaseApplication;

/**
 * Created by JoJo on 2018/8/29.
 * wechat：18510829974
 * description：loadImage辅助类
 */
public class GlideHelper {
    /**
     * 公共的加载网络图片的配置
     * @param imageUrl   图片地址
     * @param defaultImg 加载异常图
     * @param targetView 目标ImageView
     */
    public static final void loadImage(String imageUrl, ImageView targetView, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(defaultImg)
                .error(defaultImg)
                .priority(Priority.HIGH);
        Glide.with(BaseApplication.getInstance())
                .load(imageUrl)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(targetView);
    }
}
