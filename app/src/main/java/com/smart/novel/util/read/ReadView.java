package com.smart.novel.util.read;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.smart.framework.library.common.log.Elog;
import com.smart.novel.R;
import com.smart.novel.util.read.modle.ChapterModel;
import com.smart.novel.util.read.modle.LineModel;
import com.smart.novel.util.read.modle.PageModel;

public class ReadView extends View {
    private Context mContext;
    int lineNum = 1;
    int lineHeight = 8;
    int viewWidth, viewHeight, textWidth, textHeight, readWidth, readHeight;
    public int fontSize;
    int textColor;
    int paddingLeft, paddingTop, paddingRight, paddingBottom;
    int background;
    public ReadTool readTool;
    public ChapterModel chapterModel;
    Paint mPaint;
    String eBook = "";
    //定义一个接口对象listerner
    private OnItemSelectListener listener;
    //    private Bitmap bitmap;
    private Matrix matrix;
    public int mCurrentPage = 1;


    public ReadView(Context context) {
        this(context, null);
    }

    public ReadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initCustomAttrs(context, attrs);

    }

    public ReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initCustomAttrs(context, attrs);
        init();
    }

    /**
     * 获取自定义属性
     */
    private void initCustomAttrs(Context context, AttributeSet attrs) {
        //获取自定义属性。
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ReadView);
        //获取字体大小,默认大小是16dp
        fontSize = (int) ta.getDimension(R.styleable.ReadView_fontSize, 30);
        //获取文字内容
        eBook = ta.getString(R.styleable.ReadView_text);
        //获取文字颜色，默认颜色是BLUE
//        textColor = ta.getColor(R.styleable.ReadView_color, ContextCompat.getColor(mContext,R.color.color_2E3439));
        textColor = ta.getColor(R.styleable.ReadView_color, Color.BLACK);
        //获取背景
        background = ta.getResourceId(R.styleable.ReadView_background, R.drawable.bg_reading);
        ta.recycle();
    }

    public void init() {
        textColor = ContextCompat.getColor(mContext, R.color.color_2E3439);
        // 创建画笔
        mPaint = new Paint();
        // 设置画笔颜色
        mPaint.setColor(textColor);
        // 设置画笔宽度
        mPaint.setTextSize(fontSize);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
//        bitmap = BitmapFactory.decodeResource(this.getResources(), background);
        chapterModel = new ChapterModel("走进科学", 1, null, 0);
        readTool = new ReadTool();
    }

    public void setText(String str) {
        eBook = str;
        requestLayout();
        invalidate();
    }

    public void PageUp() {
        if (chapterModel.getIndex() + 1 < chapterModel.getPageModels().size()) {
            chapterModel.setIndex(chapterModel.getIndex() + 1);
        }
        Elog.e("TAG", "PageUp-mCurrentPage=" + chapterModel.getIndex());
        mCurrentPage = chapterModel.getIndex() + 1;
    }

    public void PageDn() {
        if (chapterModel.getIndex() - 1 >= 0) {
            chapterModel.setIndex(chapterModel.getIndex() - 1);
            mCurrentPage = chapterModel.getIndex() - 1;
        }
        Elog.e("TAG", "pageDn-mCurrentPage=" + chapterModel.getIndex());
        mCurrentPage = chapterModel.getIndex() + 1;
    }

    private void setMatrix() {
//        float bitmapWidth = bitmap.getWidth();
//        float bitmapHeight = bitmap.getHeight();
//        float scaleX = viewWidth / bitmapWidth;
//        float scaleY = viewHeight / bitmapHeight;
        matrix = new Matrix();
        matrix.postTranslate(0, 0);
//        matrix.preScale(scaleX, scaleY);
    }

    private void getStrData(String str) {
        readTool.init();
        readTool.setStrCaptal(fontSize, textColor);
        int lineWidth = 2 * fontSize;
        if (TextUtils.isEmpty(str)) return;
        for (int i = 0; i < str.length(); i++) {
            String subStr;
            if (i < str.length() - 1) {
                subStr = str.substring(i, i + 1);
            } else {
                subStr = str.substring(i);
            }
            int fontWidth = (int) mPaint.measureText(subStr);
            lineWidth = lineWidth + fontWidth;
            if (subStr.equals("\n")) {
                readTool.addPage(readHeight, fontSize);
                readTool.addLine(0);
                readTool.setStrCaptal(fontSize, textColor);
                lineWidth = 2 * fontSize;
            } else if (lineWidth < readWidth) {
                readTool.addStrArr(subStr, fontWidth, lineWidth - fontWidth, textColor);
            } else {
                readTool.addPage(readHeight, fontSize);
                readTool.addLine(readWidth - lineWidth + fontWidth);
                lineWidth = fontWidth;
                readTool.addStrArr(subStr, lineWidth, 0, textColor);
            }
        }
        readTool.addEnd(readHeight, fontSize);
        lineWidth = 0;
        chapterModel.setPageModels(readTool.getPageModels());
        lineNum = readTool.getLineModels().size();
        if (lineNum > 1) {
            textWidth = getWidth();
        } else {
            textWidth = lineWidth;
        }
        textHeight = lineNum * (fontSize + lineHeight);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        viewWidth = widthSize;
        viewHeight = heightSize;
        readWidth = viewWidth - paddingLeft - paddingRight;
        readHeight = viewHeight - paddingTop - paddingBottom;
        setMatrix();
        getStrData(eBook);

        int width;
        int height;
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = textWidth;
        } else {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = textHeight;
        } else {
            height = heightSize;
        }

        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (listener != null && chapterModel.getPageModels() != null) {
            listener.onTotalPage(chapterModel.getPageModels().size());
            mCurrentPage = chapterModel.getIndex() + 1;
        }
        if (chapterModel.getPageModels() != null && chapterModel.getIndex() < chapterModel.getPageModels().size()) {
            PageModel page = chapterModel.getPageModels().get(chapterModel.getIndex());
            for (int i = 0; i < page.getLineModels().size(); i++) {
                LineModel line = page.getLineModels().get(i);
                int num = line.getStringList().size();
                float spacing;
                if (num == 0) {
                    spacing = 0;
                } else {
                    spacing = line.getStrDiff() / (float) (num - 1);
                }
                for (int j = 0; j < num; j++) {
//                mPaint.setColor(line.getStrColors().get(j));
                    canvas.drawText(line.getStringList().get(j), line.getStrX().get(j) + paddingLeft + j * spacing,
                            (i + 1) * fontSize * 1.5f + paddingTop - 4, mPaint);
                }
            }
        }
    }

    float downX = 0;
    float distance = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                int index = 0;
                //点击屏幕左边，上一页
                if (downX < viewWidth / 2) {
                    index = 0;
                    listener.onScrollLeft();
                } else {
                    index = 1;
                    listener.onScrollRight();
                }

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                distance = event.getX() - downX;
//                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG", "distance=" + distance);
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    //获得接口对象的方法。
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    //定义一个接口
    public interface OnItemSelectListener {
        //        void onItemSelect(int index);
        void onScrollLeft();

        void onScrollRight();

        void onTotalPage(int totalPage);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    /**
     * 设计文字颜色和大小
     *
     * @param fontSize
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        mPaint.setTextSize(fontSize);
        setText(eBook);
    }
}