package com.smart.novel.adapter

import android.content.Context
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
class ADA_ChapterList constructor(context: Context) : CommonAdapter<ChapterBean, ItemNovelChapterBinding>(context) {
    override fun convert(viewBinding: ItemNovelChapterBinding?, holder: ViewHolder.BindingHolder?, bean: ChapterBean?, position: Int) {
        viewBinding!!.chapter = bean
        if (bean!!.isLatest) holder!!.setVisible(R.id.iv_newest_chapter, true) else holder!!.setVisible(R.id.iv_newest_chapter, false)

    }

    override fun itemLayoutId(): Int {
        return R.layout.item_novel_chapter
    }
}