package com.smart.novel.adapter

import android.app.Activity
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.framework.library.adapter.rv.normal.ItemViewDelegateNormal
import com.smart.novel.R
import com.smart.novel.bean.UserEntity
import com.smart.novel.databinding.ItemTypeOneBinding

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description:
 */
class ViewTypeOne constructor(context: Activity) : ItemViewDelegateNormal<UserEntity> {
    override fun convert(holder: ViewHolder.BindingHolder, userBean: UserEntity?, position: Int) {
        (holder.viewBinding as ItemTypeOneBinding).user = userBean
    }

    override fun getItemViewLayoutId(): Int {
        return R.layout.item_type_one
    }

    override fun isForViewType(item: UserEntity, position: Int): Boolean {
        return item.id == 1
    }


}