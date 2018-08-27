package com.smart.novel.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.smart.framework.library.common.BroadcastConstants
import com.smart.novel.ui.ACT_Login

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description: 退出登录
 */

class LogoutUtils{
    fun logout(context: Context){
        //关闭所有页面，进入到登录页面
        sendBroadcast(context, BroadcastConstants.LOGOUT)
        val intent = Intent(context, ACT_Login::class.java)
//        val bundle = Bundle()
//        bundle.putString(SendDataConstants.LOGOUT_INFO, logoutInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    /**
     * 发送一个广播
     *
     * @param value
     */
    fun sendBroadcast(context: Context, value: Int) {
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            val intent = Intent()
            intent.action = info.packageName + com.smart.framework.library.common.Constants.BROADCASE_ADDRESS
            intent.putExtra(com.smart.framework.library.common.Constants.BROADCASE_INTENT, value)
            context.sendBroadcast(intent)
        } catch (e: PackageManager.NameNotFoundException) {
        }

    }
}
