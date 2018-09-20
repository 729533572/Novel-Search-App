package com.smart.framework.library.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.smart.framework.library.common.log.Elog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 屏幕参数处理工具类
 *
 * @author joe
 * @version 2015.04.23
 */
public class ScreenUtil {

    /**
     * DIP转PX
     *
     * @param context
     * @param dipValue
     * @return int
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * float 转dp
     *
     * @param context
     * @param floatValue
     * @return
     */
    public static int floatToDP(Context context, float floatValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                floatValue, context.getResources().getDisplayMetrics());
    }

    /**
     * float 转px(像素)
     *
     * @param context
     * @param floatValue
     * @return
     */
    public static int floatToPX(Context context, float floatValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                floatValue, context.getResources().getDisplayMetrics());
    }

    /**
     * float 转SP
     *
     * @param context
     * @param floatValue
     * @return
     */
    public static int floatToSP(Context context, float floatValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                floatValue, context.getResources().getDisplayMetrics());
    }

    /**
     * PX转DIP
     *
     * @param context
     * @param pxValue
     * @return int
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 可以复用，但是每个item高度都要一致
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View listItem = listAdapter.getView(0, null, listView);
        if (null != listItem) {
            listItem.measure(0, 0);
            totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }

    }

    public static int getScreenWidth(Activity context) {
        // 获取屏幕分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        return widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        // 获取屏幕分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        return heightPixels + getNavigationBarHeight(context);
    }

    public static int getStatusBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Elog.v("status bar", "height:" + height);
        return height;
    }

    public static int getNavigationBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Elog.v("navigation bar", "height:" + height);
        return height;
    }
    /**
     * WebView加载网页图片显示太大的解决方案 图片尺寸超过屏幕宽度，最大显示屏幕的宽度，否则展示原图的大小 https://blog.csdn.net/qq_35008536/article/details/52281161
     * @param htmltext
     * @return
     */
    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("max-width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

}
