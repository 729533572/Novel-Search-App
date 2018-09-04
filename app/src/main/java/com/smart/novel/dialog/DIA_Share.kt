package com.smart.novel.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.OnClick
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.bean.ChapterBean


/**
 * Created by JoJo on 2018/9/3.
 * wechat:18510829974
 * description: 分享弹窗
 */

class DIA_Share
(protected var mContext: Activity) : Dialog(mContext) {
    protected var mDialog: Dialog
    protected var mContentView: View
    var mAdapter: ADA_ChapterList? = null

    init {
        mDialog = Dialog(mContext, R.style.style_custom_dialog)
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_share, null)
        ButterKnife.bind(this, mContentView)
        mDialog.setContentView(mContentView)
    }

    val dialog: Dialog
        get() {
            mDialog.setCanceledOnTouchOutside(true)
            mDialog.window!!.setWindowAnimations(R.style.style_bottom_in_anim)
            mDialog.window!!.setGravity(Gravity.BOTTOM)
            val lp = mDialog.window!!.attributes
            lp.dimAmount = 0.4f
            lp.alpha = 1f
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT
//            lp.height = 1200
            // 设置点击屏幕Dialog消失
            // 背景灰度
            // 设置宽度
            //        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            return mDialog
        }

    @OnClick(R.id.btn_cancel)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_cancel -> mDialog.dismiss()
            R.id.btn_wechat -> mListener!!.onShareBoardClick(0)
            R.id.btn_qq -> mListener!!.onShareBoardClick(1)
            R.id.btn_weibo -> mListener!!.onShareBoardClick(2)
        }
    }

    /**
     * 传入数据
     *
     * @param dataList
     * @return
     */
    fun refreshData(dataList: List<ChapterBean>): DIA_Share {
        mAdapter!!.update(dataList, true)
        return this
    }

    //    @OnClick({R.id.tv_close, R.id.iv_clear_all, R.id.iv_switch_mode})
    //    public void onClick(View view) {
    //
    //    }
    interface OnShareBoardClickListener {
        fun onShareBoardClick(position: Int)
    }

    var mListener: OnShareBoardClickListener? = null
}

