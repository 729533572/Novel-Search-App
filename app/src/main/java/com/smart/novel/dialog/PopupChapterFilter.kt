package com.smart.novel.dialog

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterFilter
import com.smart.novel.util.RecyclerViewHelper
import com.smart.novel.wights.MyPopupWindow

/**
 * Created by JoJo on 2018/9/4.
 * wechat:18510829974
 * description:全部章节筛选弹窗
 */
class PopupChapterFilter {

    companion object {
        var mPopWindow: PopupWindow? = null
        fun initPopupWindow(mContext: Activity, mAdapterFilter: ADA_ChapterFilter): MyPopupWindow {
            //设置contentView，在布局中外层macthParent的布局中设置半透明的背景阴影
            var contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_chapters_filter, null)
            var recyclerviewFilter = contentView.findViewById(R.id.recyclerviewFilter) as RecyclerView
            var ll_popup_root = contentView.findViewById(R.id.ll_popup_root) as LinearLayout
            ll_popup_root.setOnClickListener { mPopWindow!!.dismiss() }
            //适配7.0版本
            mPopWindow = MyPopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//            mPopWindow = PopupWindow(this) //7.0以上,高度设置为matchParent会导致popupwindow覆盖全屏，showAsDropDown()方法无效
            //必须设置宽高才能显示出来
            mPopWindow!!.height = LinearLayout.LayoutParams.MATCH_PARENT
            mPopWindow!!.width = LinearLayout.LayoutParams.MATCH_PARENT
            mPopWindow!!.setContentView(contentView)
            mPopWindow!!.animationStyle = R.style.style_top_in_anim
            //解决5.0以下版本点击外部不消失问题
            mPopWindow!!.setOutsideTouchable(true)
            mPopWindow!!.setFocusable(true)
            mPopWindow!!.setBackgroundDrawable(BitmapDrawable())
            RecyclerViewHelper.initNormalRecyclerView(mContext, recyclerviewFilter!!, mAdapterFilter!!, LinearLayoutManager(mContext))

            return mPopWindow as MyPopupWindow
        }
    }

}