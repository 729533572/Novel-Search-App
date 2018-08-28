package com.smart.novel.adapter

import android.content.Context
import android.view.View
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.bean.UserEntity
import com.smart.novel.databinding.ItemSearchResultBinding

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description: 搜索结果列表Adapter
 */
class ADA_SearchList constructor(context: Context) : CommonAdapter<UserEntity, ItemSearchResultBinding>(context) {
    override fun convert(viewBinding: ItemSearchResultBinding?, holder: ViewHolder.BindingHolder?, userBean: UserEntity?, position: Int) {
        viewBinding!!.user = userBean
        holder!!.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                CommonUtils.makeEventToast(MyApplication.context, "postion=" + position, false)
            }
        })
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_search_result
    }

//    override fun convert(holder: ViewHolderNormal.BindingHolder?, userBean: UserEntity?, position: Int) {
//        var userBinding = holder!!.viewBinding as ItemSearchResultBinding
//        userBinding.user = userBean
//    }

}