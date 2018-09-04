package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ItemBookShelfBinding

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description: 阅读历史
 */
class ADA_ReadHistory constructor(context: Context) : CommonAdapter<NovelBean, ItemBookShelfBinding>(context) {
    override fun convert(viewBinding: ItemBookShelfBinding?, holder: ViewHolder.BindingHolder?, bean: NovelBean?, position: Int) {
        viewBinding!!.history = bean

//        if (bean!!.is_finished.equals("1")) holder!!.setVisible(R.id.btn_delete, false) else holder!!.setVisible(R.id.btn_delete, true)

        holder!!.setOnClickListener(R.id.btn_delete,{ if (mListener != null) mListener!!.onDeleteBtnClick(bean!!) })

    }

    override fun itemLayoutId(): Int {
        return R.layout.item_book_shelf
    }

    interface OnDeleteBtnClickListener {
        fun onDeleteBtnClick(bean: NovelBean)
    }

    var mListener: OnDeleteBtnClickListener? = null

    fun setOnDeleteBtnClickListener(listener: OnDeleteBtnClickListener) {
        mListener = listener
    }
}