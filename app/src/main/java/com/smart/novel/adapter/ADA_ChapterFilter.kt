package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.ChapterFilterBean
import com.smart.novel.databinding.ItemNovelChapterfilterBinding

/**
 * Created by JoJo on 2018/9/2.
wechat：18510829974
description：章节列表适配器
 */
class ADA_ChapterFilter constructor(context: Context): CommonAdapter<ChapterFilterBean, ItemNovelChapterfilterBinding>(context) {
    override fun convert(viewBinding: ItemNovelChapterfilterBinding?, holder: ViewHolder.BindingHolder?, bean: ChapterFilterBean?, position: Int) {
        viewBinding!!.chapterfilter = bean
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_novel_chapterfilter
    }
}