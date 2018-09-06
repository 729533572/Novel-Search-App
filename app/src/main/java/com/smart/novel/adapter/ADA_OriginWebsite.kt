package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.normal.databinding.CommonAdapter
import com.smart.framework.library.adapter.rv.normal.databinding.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.WebsiteBean
import com.smart.novel.databinding.ItemOriginWebsiteBinding
import com.smart.novel.util.IntentUtil

/**
 * Created by JoJo on 2018/9/5.
wechat：18510829974
description：原网页列表适配器
 */
class ADA_OriginWebsite constructor(context: Context) : CommonAdapter<WebsiteBean, ItemOriginWebsiteBinding>(context) {
    var mSelectPos = -1
    override fun convert(viewBinding: ItemOriginWebsiteBinding?, holder: ViewHolder.BindingHolder?, bean: WebsiteBean?, position: Int) {
        viewBinding!!.websiteBean = bean
        if (mSelectPos == position) holder!!.setChecked(R.id.iv_newest_chapter, true) else holder!!.setChecked(R.id.iv_newest_chapter, false)

        holder!!.setOnClickListener(R.id.tv_go_website, { IntentUtil.intentToWebView(mContext,bean!!.origin_website) })
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_origin_website
    }

    fun setSelectItem(position: Int) {
        mSelectPos = position
    }
}