package com.smart.novel.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import com.smart.framework.library.adapter.lv.CommonAdapterListView
import com.smart.framework.library.adapter.lv.ViewHolderListView
import com.smart.novel.R
import com.smart.novel.databinding.ItemMineListBinding
import com.smart.novel.bean.MineBean

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:我的页面列表
 */
class ADA_MineList constructor(context: Context) : CommonAdapterListView<MineBean,ItemMineListBinding>(context) {
    override fun convert(viewDataBinding: ItemMineListBinding, holder: ViewHolderListView?, bean: MineBean?, position: Int) {
        viewDataBinding.mineBean = bean
    }

//    override fun convert(viewDataBinding: ItemMineListBinding?, holder: ViewHolderListView?, bean: MineBean?, position: Int) {
////        var viewBinding = viewDataBinding as ItemMineListBinding
//        viewDataBinding.mineBean = bean
//    }

    override fun itemLayoutId(): Int {
        return R.layout.item_mine_list
    }

}