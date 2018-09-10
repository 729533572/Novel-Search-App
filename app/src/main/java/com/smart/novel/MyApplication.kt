package com.smart.novel

import android.content.Context
import android.support.multidex.MultiDex
import android.text.TextUtils
import com.smart.framework.library.BaseApplication
import com.smart.framework.library.common.utils.AppSharedPreferences
import com.smart.novel.util.AppConfig
import com.smart.novel.util.SharePreConstants
import com.umeng.analytics.MobclickAgent
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI

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

        //友盟分享-各个平台的配置，建议放在全局Application或者程序入口
        init {
            PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_APPSecret)
            PlatformConfig.setSinaWeibo(AppConfig.WEIBO_APP_ID, AppConfig.WEIBO_APP_SECRET, AppConfig.WEIBO_REDIRT_URL)
            PlatformConfig.setQQZone(AppConfig.QQ_APP_ID, AppConfig.QQ_APP_KEY)
        }
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
        initUMengSDK()
    }

    /**
     * 初始化UMengSDK
     */
    private fun initUMengSDK() {
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        //UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
        //注意：一定要在主进程进行该项操作
//        UMConfigure.init(this, AppConfig.UMENG_KEY, getChannelName(mContext), UMConfigure.DEVICE_TYPE_PHONE, "")
//        //普通统计场景类型
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
//        // 友盟统计 debug模式
//        //        MobclickAgent.setDebugMode(!RequestManager.getInstance().getBASE_URL().equals(IP));
        MobclickAgent.setDebugMode(true)
//        //正式上线设置是否对日志信息进行加密, 默认false(不加密).
//        if (!BuildConfig.DEBUG) {
//            UMConfigure.setEncryptEnabled(true)
//        }
        Config.DEBUG = true
        UMShareAPI.get(this)
    }
}