package com.smart.novel.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import com.smart.framework.library.adapter.lv.CommonAdapterListView
import com.smart.framework.library.adapter.lv.ViewHolderListView
import com.smart.novel.R
import com.smart.novel.databinding.ItemSearchHistoryBinding
import com.smart.novel.db.bean.SearchHistoryBean

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description:本地搜索历史
 */
class ADA_SearchHistory constructor(context: Context) : CommonAdapterListView<SearchHistoryBean>(context) {
    override fun convert(viewDataBinding: ViewDataBinding?, viewHolder: ViewHolderListView?, bean: SearchHistoryBean?, position: Int) {
        var viewBind = viewDataBinding as ItemSearchHistoryBinding
        viewBind.searchHistory = bean
    }
    override fun itemLayoutId(): Int {
        return R.layout.item_search_history
    }

}