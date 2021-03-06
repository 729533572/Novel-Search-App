package com.smart.novel.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.smart.framework.library.adapter.rv.normal.databinding.CommonAdapter
import com.smart.framework.library.adapter.rv.normal.databinding.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.RankTitleBean
import com.smart.novel.databinding.ItemRankTitleBinding

/**
 * Created by JoJo on 2018/9/1.
wechat：18510829974
description： 排行榜-title adapter
 */
class ADA_RankingTitle constructor(context: Context) : CommonAdapter<RankTitleBean, ItemRankTitleBinding>(context) {
    override fun convert(viewBinding: ItemRankTitleBinding?, holder: ViewHolder.BindingHolder?, bean: RankTitleBean?, position: Int) {
        viewBinding!!.ranktitle = bean
        val convertView = holder!!.convertView as ViewGroup
        if (mSelectPos == position) {
            convertView.getChildAt(0).visibility = View.VISIBLE
            convertView.getChildAt(1).visibility = View.GONE
        } else {
            convertView.getChildAt(0).visibility = View.GONE
            convertView.getChildAt(1).visibility = View.VISIBLE
        }
    }

    var mSelectPos = 0
    override fun itemLayoutId(): Int {
        return R.layout.item_rank_title
    }

    fun setSelectItem(position: Int) {
        mSelectPos = position
    }
}