package com.smart.novel.adapter

import android.app.Activity
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.framework.library.adapter.rv.normal.ItemViewDelegateNormal
import com.smart.novel.R
import com.smart.novel.bean.UserBean

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description:
 */
class ViewTypeTwo constructor(context: Activity) : ItemViewDelegateNormal<UserBean> {

    override fun convert(holder: ViewHolder.BindingHolder, t: UserBean?, position: Int) {
//        (holder.viewBinding as ItemSearchResultBinding).user = t
    }

    override fun getItemViewLayoutId(): Int {
        return R.layout.item_search_result
    }

    override fun isForViewType(item: UserBean, position: Int): Boolean {
        return item.id.equals("2")
    }


}