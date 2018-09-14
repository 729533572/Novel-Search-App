package com.smart.novel.adapter

import android.content.Context
import android.view.View
import com.smart.framework.library.adapter.rv.normal.databinding.CommonAdapter
import com.smart.framework.library.adapter.rv.normal.databinding.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ItemSearchResultBinding
import com.smart.novel.util.IntentUtil

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description: 搜索结果列表Adapter
 */
class ADA_SearchList constructor(context: Context) : CommonAdapter<NovelBean, ItemSearchResultBinding>(context) {
    override fun convert(viewBinding: ItemSearchResultBinding?, holder: ViewHolder.BindingHolder?, bean: NovelBean?, position: Int) {
        viewBinding!!.searchBean = bean
        holder!!.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                IntentUtil.intentToNovelDetail(mContext, bean!!)
            }
        })
        holder!!.setOnClickListener(R.id.tv_comment, object : View.OnClickListener {
            override fun onClick(v: View?) {
                IntentUtil.intentToNovelDetail(mContext, bean!!)
            }
        })
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_search_result
    }

}