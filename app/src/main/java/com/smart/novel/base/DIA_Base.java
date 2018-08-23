package com.smart.novel.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.smart.framework.library.common.utils.ScreenUtil;
import com.smart.novel.MyApplication;
import com.smart.novel.R;

import butterknife.ButterKnife;

/**
 * 对话框基类
 * Created by qyd on 2016/12/15.
 */
public abstract class DIA_Base {

    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    protected MyApplication application;


    public DIA_Base(Context context) {
        {
            if(null!=context){
                mContext = context;
                mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
                mContentView= LayoutInflater.from(context).inflate(getLayoutId(), null);
                ButterKnife.bind(this,mContentView);
                mDialog.setContentView(mContentView);
                //application = (MyApplication) context.getApplicationContext();
            }
        }
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }



    protected abstract int getLayoutId();

}

