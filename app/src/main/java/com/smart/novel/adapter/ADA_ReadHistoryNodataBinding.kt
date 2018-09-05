package com.smart.novel.adapter

import android.content.Context
import android.widget.ImageView
import com.smart.framework.library.adapter.rv.normal.nodatabinding.CommonAdapter
import com.smart.framework.library.adapter.rv.normal.nodatabinding.ViewHolder
import com.smart.framework.library.glide.GlideHelper
import com.smart.novel.R
import com.smart.novel.bean.NovelBean

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description: 阅读历史
 */
class ADA_ReadHistoryNodataBinding constructor(context: Context) : CommonAdapter<NovelBean>(context) {
    override fun convert(holder: ViewHolder?, bean: NovelBean?, position: Int) {
        holder!!.setText(R.id.tv_name, bean!!.name_cn)
        val ivCover = holder.getView<ImageView>(R.id.iv_cover)
        GlideHelper.loadImage(bean.covor_url, ivCover, 0)
        if (bean!!.isEdit) holder!!.setVisible(R.id.btn_delete, true) else holder!!.setVisible(R.id.btn_delete, false)

        holder!!.setOnClickListener(R.id.btn_delete, { if (mListener != null) mListener!!.onDeleteBtnClick(position, bean!!) })
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_book_shelf
    }

    interface OnDeleteBtnClickListener {
        fun onDeleteBtnClick(position: Int, bean: NovelBean)
    }

    var mListener: OnDeleteBtnClickListener? = null

    fun setOnDeleteBtnClickListener(listener: OnDeleteBtnClickListener) {
        mListener = listener
    }
}