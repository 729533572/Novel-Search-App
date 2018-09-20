package com.smart.novel.adapter

import android.content.Context
import android.view.View
import com.smart.framework.library.adapter.rv.normal.databinding.CommonAdapter
import com.smart.framework.library.adapter.rv.normal.databinding.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.ChapterBean
import com.smart.novel.databinding.ItemNovelChapterBinding

/**
 * Created by JoJo on 2018/9/2.
wechat：18510829974
description：章节列表适配器
 */
class ADA_ChapterList constructor(context: Context, isShowLatest: Boolean) : CommonAdapter<ChapterBean, ItemNovelChapterBinding>(context) {
    //全部章节页面，不展示最新章节 showLatest = false;小说详情页，展示最新章节 showLatest = true
    var showLatest = true

    init {
        showLatest = isShowLatest
    }

    override fun convert(viewBinding: ItemNovelChapterBinding?, holder: ViewHolder.BindingHolder?, bean: ChapterBean, position: Int) {
        viewBinding!!.chapter = bean

        if (bean.latest) holder!!.setVisible(R.id.iv_newest_chapter, true) else holder!!.setVisible(R.id.iv_newest_chapter, false)

        if (!showLatest) holder!!.setVisible(R.id.iv_newest_chapter, false)

        if (!showLatest) {
            if (bean.latest) holder!!.convertView.visibility = View.GONE else holder!!.convertView.visibility = View.VISIBLE
        }
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_novel_chapter
    }
}