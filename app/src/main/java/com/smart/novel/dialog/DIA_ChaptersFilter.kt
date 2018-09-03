package com.smart.novel.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.smart.framework.library.common.utils.ScreenUtil
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.bean.ChapterBean
import com.smart.novel.util.RecyclerViewHelper


/**
 * Created by JoJo on 2018/9/3.
 * wechat:18510829974
 * description: 小说所有章节筛选弹窗
 */

class DIA_ChaptersFilter
(protected var mContext: Activity) : Dialog(mContext) {
    @BindView(R.id.recyclerviewFilter) lateinit var recyclerviewFilter: RecyclerView
    protected var mDialog: Dialog
    protected var mContentView: View
    var mAdapter: ADA_ChapterList? = null

    init {
        mDialog = Dialog(mContext, R.style.style_custom_dialog)
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_chapters_filter, null)
        ButterKnife.bind(this, mContentView)
        mDialog.setContentView(mContentView)

        mAdapter = ADA_ChapterList(mContext)
        RecyclerViewHelper.initNormalRecyclerView(mContext, recyclerviewFilter, mAdapter!!, LinearLayoutManager(mContext))
    }

    val dialog: Dialog
        get() {
            mDialog.setCanceledOnTouchOutside(true)
            mDialog.window!!.setWindowAnimations(R.style.style_bottom_in_anim)
            mDialog.window!!.setGravity(Gravity.TOP)
            val lp = mDialog.window!!.attributes
            lp.dimAmount = 0.2f
            lp.alpha = 1f
            lp.width = ScreenUtil.getScreenWidth(mContext)
            lp.height = 1200
            // 设置点击屏幕Dialog消失
            // 背景灰度
            // 设置宽度
            //        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            return mDialog
        }


    /**
     * 传入数据
     *
     * @param dataList
     * @return
     */
    fun refreshData(dataList: List<ChapterBean>): DIA_ChaptersFilter {
        mAdapter!!.update(dataList, true)
        return this
    }

    //    @OnClick({R.id.tv_close, R.id.iv_clear_all, R.id.iv_switch_mode})
    //    public void onClick(View view) {
    //
    //    }
}

