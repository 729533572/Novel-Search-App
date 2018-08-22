package com.smart.framework.library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 禁止滑动的gridview
 * 作者：liushuofei on 2016/12/7 15:38
 */
public class BanSlideGridView extends GridView {
    public BanSlideGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BanSlideGridView(Context context) {
        super(context);
    }

    public BanSlideGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
