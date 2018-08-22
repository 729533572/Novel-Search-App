package com.smart.novel;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.AppSharedPreferences;
import com.smart.novel.util.SharePreConstants;
import com.smart.novel.util.Constants;


/**
 * 作者：jojo on 2017/6/22 10:00
 * 邮箱：zjuan@yonyou.com
 */
public class MyApplication extends BaseApplication {

    private static Context mContext;

    public static MyApplication mInstance = null;
    /**
     * 用户是否登录
     */
    public static boolean isLogin;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        //内存中保存用户信息
        AppSharedPreferences sharePre = new AppSharedPreferences(this);
        boolean token = !TextUtils.isEmpty(sharePre.getString(SharePreConstants.USER_SIGN));
        isLogin = !sharePre.getBoolean(SharePreConstants.LOGOUT) && token;
        if (isLogin) {
            Constants.USER_ID = sharePre.getString(SharePreConstants.USER_ID);
            Constants.USER_SIGN = sharePre.getString(SharePreConstants.USER_SIGN);
            Constants.TYPE = sharePre.getString(SharePreConstants.TYPE);
            Constants.BRANCH_NAME = sharePre.getString(SharePreConstants.BRANCH_NAME);
            Elog.e("TAG", "USER_ID=" + Constants.USER_ID + "USER_SIGN=" + Constants.USER_SIGN);
        }
    }
}
