package com.smart.novel

import android.content.Context
import android.support.multidex.MultiDex
import com.smart.framework.library.BaseApplication
import kotlin.properties.Delegates

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
class MyApplication : BaseApplication() {
    ////用companion object包裹，实现java中static的效果,包裹的方法或者变量都是static的
    companion object {
          var context: Context by Delegates.notNull()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}