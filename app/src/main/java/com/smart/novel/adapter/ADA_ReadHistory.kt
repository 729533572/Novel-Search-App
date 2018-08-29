package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.novel.R
import com.smart.novel.databinding.ItemBookShelfBinding
import com.smart.novel.db.bean.ReadHistoryEntity

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description:
 */
class ADA_ReadHistory constructor(context: Context) : CommonAdapter<ReadHistoryEntity, ItemBookShelfBinding>(context) {
    override fun convert(viewBinding: ItemBookShelfBinding?, holder: ViewHolder.BindingHolder?, t: ReadHistoryEntity?, position: Int) {

    }

    override fun itemLayoutId(): Int {
        return R.layout.item_book_shelf
    }

}