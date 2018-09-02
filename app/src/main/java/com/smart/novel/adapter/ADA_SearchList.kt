package com.smart.novel.adapter

import android.content.Context
import android.view.View
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ItemSearchResultBinding

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
                CommonUtils.makeEventToast(MyApplication.context, "postion=" + position, false)
            }
        })
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_search_result
    }

}