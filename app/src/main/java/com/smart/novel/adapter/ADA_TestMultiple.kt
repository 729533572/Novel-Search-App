package com.smart.novel.adapter

import android.app.Activity
import com.smart.framework.library.adapter.rv.normal.MultiTypeAdapterNormal
import com.smart.novel.bean.UserEntity

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description: RecyclerView多类型列表测试使用：使用MultiTypeAdapterNormal 和ItemViewDelegateNormal
 */
class ADA_TestMultiple constructor(context: Activity) : MultiTypeAdapterNormal<UserEntity>(context) {
    init {
        addItemViewDelegate(ViewTypeOne(context))
        addItemViewDelegate(ViewTypeTwo(context))
    }
}