package com.smart.framework.library.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.framework.library.common.utils.AppDateUtil;
import com.smart.framework.library.glide.GlideHelper;

/**
 * Created by JoJo on 2018/8/29.
 * <p>
 * wechat：18510829974
 * description：Databinding中的ImageView与Glide结合使用,自定义ImageViewBindingAdapter
 */
public class ViewBindingAdapter {

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

    @BindingAdapter("bind:image")
    public static void loadLocalImage(ImageView image, int resId) {
        image.setImageResource(resId);
    }

    @BindingAdapter("bind:category")
    public static void setCategoryText(TextView tv, String text) {
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("bind:isFinished")
    public static void setUpdateStatus(TextView tv, int isFinished) {
        if (isFinished == 0) {
            tv.setText("更新中");
        } else {
            tv.setText("已完结");
        }
    }

    @BindingAdapter("bind:formatdate")
    public static void setFormatTime(TextView tv, String timesec) {
//        24内的展示 xx小时xx分更新，超过的直接显示日期时分秒
        if (!TextUtils.isEmpty(timesec)) {
            //20:40
            String time = AppDateUtil.getTimeStamp(Long.parseLong(timesec), AppDateUtil.HH_MM);
            String[] splitTime = time.split(":");
            String hour = splitTime[0];
            String minute = splitTime[1];
            int hourTime = Integer.parseInt(hour);
            if (hourTime < 24) {
                tv.setText(hour + "小时" + minute + "分前更新");
            } else {
                tv.setText(AppDateUtil.getTimeStamp(Long.parseLong(timesec), AppDateUtil.YYYY_MM_DD_HH_MM1) + "更新");
            }
        }
    }
}
