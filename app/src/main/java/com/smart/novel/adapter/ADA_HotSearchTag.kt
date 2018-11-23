package com.smart.novel.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.smart.framework.library.widgets.tag.FlowLayout
import com.smart.framework.library.widgets.tag.TagAdapter
import com.smart.novel.MyApplication.Companion.context
import com.smart.novel.R
import com.smart.novel.bean.HotSearchBean

/**
 * Created by JoJo on 2018/8/30.
 * wechat:18510829974
 * description: 热门搜索标签
 */
class ADA_HotSearchTag constructor(hotList: List<HotSearchBean>) : TagAdapter<HotSearchBean>(hotList) {
    override fun getView(parent: FlowLayout?, position: Int, bean: HotSearchBean?): View {
        var itemTagView = LayoutInflater.from(context).inflate(R.layout.item_hot_search, null);
        var tvItemTxt = itemTagView.findViewById<TextView>(R.id.tv_item_name)
        tvItemTxt.setText(bean!!.search_keyword)
        return itemTagView
    }
}