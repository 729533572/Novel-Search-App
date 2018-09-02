package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.ChapterBean
import com.smart.novel.databinding.ItemNovelChapterBinding

/**
 * Created by JoJo on 2018/9/2.
wechat：18510829974
description：章节列表适配器
 */
class ADA_ChapterList constructor(context: Context):CommonAdapter<ChapterBean,ItemNovelChapterBinding>(context) {
    override fun convert(viewBinding: ItemNovelChapterBinding?, holder: ViewHolder.BindingHolder?, bean: ChapterBean?, position: Int) {
        viewBinding!!.chapter = bean
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_novel_chapter
    }
}