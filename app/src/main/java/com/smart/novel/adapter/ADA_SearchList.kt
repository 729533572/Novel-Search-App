package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.UserEntity
import com.smart.novel.databinding.ItemSearchResultBinding

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description: 搜索结果列表Adapter
 */
class ADA_SearchList constructor(context: Context) : CommonAdapter<UserEntity>(context) {

    override fun itemLayoutId(): Int {
        return R.layout.item_search_result
    }

    override fun convert(holder: ViewHolder.BindingHolder?, userBean: UserEntity?, position: Int) {
        var userBinding = holder!!.viewBinding as ItemSearchResultBinding
        userBinding.user = userBean
    }

}