package com.smart.novel.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.ui.ACT_NovelDetail
import com.smart.novel.ui.ACT_Read
import com.smart.novel.ui.ACT_WebView

/**
 * 跳转工具类
 * 作者：liushuofei on 2017/7/13 10:26
 */
class IntentUtil {
    companion object {
        //跳转到小说详情
        fun intentToNovelDetail(context: Context, bean: NovelBean) {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putSerializable(PageDataConstants.NOVEL_BEAN, bean)
            intent.setClass(context, ACT_NovelDetail::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

        //跳转到小说阅读页面
        fun intentToReadNovel(context: Context, chapterBean: ChapterBean) {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putSerializable(PageDataConstants.CHAPTER_BEAN, chapterBean)
            intent.setClass(context, ACT_Read::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

        /**
         * 跳转WebView
         */
        fun intentToWebView(context: Context, linkUrl: String) {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putString(PageDataConstants.WEB_URL, linkUrl)
            intent.setClass(context, ACT_WebView::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }
}
