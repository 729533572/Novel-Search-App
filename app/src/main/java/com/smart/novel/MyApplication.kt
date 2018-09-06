package com.smart.novel

import android.content.Context
import android.support.multidex.MultiDex
import android.text.TextUtils
import com.smart.framework.library.BaseApplication
import com.smart.framework.library.common.utils.AppSharedPreferences
import com.smart.novel.util.SharePreConstants

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
class MyApplication : BaseApplication() {
    var sharePre: AppSharedPreferences? = null

    ////用companion object包裹，实现java中static的效果,包裹的方法或者变量都是static的
    companion object {
       lateinit var context: Context

        var isLogin: Boolean = false
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        sharePre = AppSharedPreferences(context)

        isLogin = !sharePre!!.getBoolean(SharePreConstants.LOGOUT) && (!TextUtils.isEmpty(sharePre!!.getString(SharePreConstants.USER_ID)));
    }
}