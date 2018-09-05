package com.smart.novel.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.R;
import com.smart.framework.library.common.utils.ScreenUtil;

import butterknife.ButterKnife;

/**
 * 对话框基类
 * Created by qyd on 2016/12/15.
 */

public abstract class DIA_Base {

    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    protected BaseApplication application;

    private boolean cancelable = true;

    public DIA_Base(Context context) {
        {
            if (null != context) {
                mContext = context;
                mDialog = new Dialog(context, getDialogAnimation());
                mContentView = LayoutInflater.from(context).inflate(getLayoutId(), null);
                ButterKnife.bind(this, mContentView);
                mDialog.setContentView(mContentView);
                application = BaseApplication.getInstance();
            }
        }
    }

    public Dialog getDialog() {
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().setGravity(Gravity.CENTER);
            mDialog.getWindow().setWindowAnimations(getDialogAnimation());
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            // lp.dimAmount = 0.0f; 背景灰度
            lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 8; // 设置宽度
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        return mDialog;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    /**
     * 外部是不是可以取消
     *
     * @param cancelable
     */
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }


    protected abstract int getLayoutId();

    /**
     * 对话框进出动画
     *
     * @return
     */
    protected int getDialogAnimation() {
        return R.style.style_custom_dialog_fade;
    }

}

