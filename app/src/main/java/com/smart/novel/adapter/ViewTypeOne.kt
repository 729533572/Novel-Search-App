package com.smart.novel.adapter

import android.app.Activity
import com.smart.framework.library.adapter.rv.normal.databinding.ViewHolder
import com.smart.framework.library.adapter.rv.normal.ItemViewDelegateNormal
import com.smart.novel.R
import com.smart.novel.bean.UserBean
import com.smart.novel.databinding.ItemTypeOneBinding

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description:
 */
class ViewTypeOne constructor(context: Activity) : ItemViewDelegateNormal<UserBean> {
    override fun convert(holder: ViewHolder.BindingHolder, userBean: UserBean?, position: Int) {
        (holder.viewBinding as ItemTypeOneBinding).user = userBean
    }

    override fun getItemViewLayoutId(): Int {
        return R.layout.item_type_one
    }

    override fun isForViewType(item: UserBean, position: Int): Boolean {
        return item.id.equals("1")
    }


}