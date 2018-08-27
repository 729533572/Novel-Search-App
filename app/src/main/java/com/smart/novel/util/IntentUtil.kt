package com.smart.novel.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.smart.novel.ui.ACT_WebView

/**
 * 跳转工具类
 * 作者：liushuofei on 2017/7/13 10:26
 */
class IntentUtil{
    /**
     * 跳转WebView
     */
    fun intentToWebView(context: Context, linkUrl: String) {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putString(PageDataConstants.BANNER_URL, linkUrl)
        intent.setClass(context, ACT_WebView::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

}
