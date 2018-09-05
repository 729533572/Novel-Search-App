package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.normal.databinding.CommonAdapter
import com.smart.framework.library.adapter.rv.normal.databinding.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ItemBookShelfDatabindingBinding

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description: 阅读历史
 */
class ADA_ReadHistory constructor(context: Context) : CommonAdapter<NovelBean, ItemBookShelfDatabindingBinding>(context) {
    override fun convert(viewBinding: ItemBookShelfDatabindingBinding?, holder: ViewHolder.BindingHolder?, bean: NovelBean?, position: Int) {
        viewBinding!!.history = bean

        if (bean!!.isEdit) holder!!.setVisible(R.id.btn_delete, true) else holder!!.setVisible(R.id.btn_delete, false)

        holder!!.setOnClickListener(R.id.btn_delete, { if (mListener != null) mListener!!.onDeleteBtnClick(position, bean!!) })
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_book_shelf_databinding
    }

    interface OnDeleteBtnClickListener {
        fun onDeleteBtnClick(position: Int, bean: NovelBean)
    }

    var mListener: OnDeleteBtnClickListener? = null

    fun setOnDeleteBtnClickListener(listener: OnDeleteBtnClickListener) {
        mListener = listener
    }
}