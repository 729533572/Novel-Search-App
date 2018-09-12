package com.smart.novel.adapter

import android.content.Context
import android.view.View
import com.smart.framework.library.adapter.lv.normal.CommonAdapterListView
import com.smart.framework.library.adapter.lv.normal.ViewHolderListView
import com.smart.novel.R
import com.smart.novel.bean.SearchHistoryBean

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description:本地搜索历史
 */
class ADA_SearchHistory constructor(context: Context) : CommonAdapterListView<SearchHistoryBean>(context) {
    override fun itemLayoutId(): Int {
        return R.layout.item_search_history
    }

    override fun convert(holder: ViewHolderListView?, bean: SearchHistoryBean, position: Int) {
        holder!!.setText(R.id.tv_item_history, bean.searchKeyWords)
        holder.setOnClickListener(R.id.iv_delete_item, object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mListener != null) {
                    mListener!!.onItemDelete(bean)
                }
            }
        })
    }
    //CommonAdapterListView结合DataBinding使用时，数据有删除时，刷新有bug
//    override fun convert(viewDataBinding: ViewDataBinding?, holder: ViewHolderListView, bean: SearchHistoryBean, position: Int) {
//        var viewBind = viewDataBinding as ItemSearchHistoryBinding
//        viewBind.searchHistory = bean
//        holder.setOnClickListener(R.id.iv_delete_item, object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                if (mListener != null) {
//                    mListener!!.onItemDelete(bean)
//                }
//            }
//        })
//        Elog.e("TAG","ADA_SearchHistory")
//    }
//
//    override fun itemLayoutId(): Int {
//        return R.layout.item_search_history
//    }

    var mListener: OnItemDeleteListener? = null

    interface OnItemDeleteListener {
        fun onItemDelete(bean: SearchHistoryBean)
    }

    fun setOnItemDeleteListener(listener: OnItemDeleteListener) {
        mListener = listener
    }

}