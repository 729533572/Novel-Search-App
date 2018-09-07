package com.smart.novel.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.bean.ChapterBean

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description: 阅读设置弹窗
 */

class DIA_ReadSetting(protected var context: Activity) : Dialog(context as Context?) {
    protected var mDialog: Dialog
    protected var mContentView: View
    var mAdapter: ADA_ChapterList? = null
    var mContext: Activity? = null
    var mChapterBean: ChapterBean? = null
    @BindView(R.id.rb_collect) lateinit var rbCollect: RadioButton
    @BindView(R.id.sb_progress) lateinit var sbProgress: SeekBar
    @BindView(R.id.sb_textsize) lateinit var sbTextsize: SeekBar
    @BindView(R.id.ll_read_mode) lateinit var llReadMode: RadioGroup

    init {
        mContext = context
        mDialog = Dialog(mContext, R.style.style_custom_dialog)
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_read_setting, null)
        ButterKnife.bind(this, mContentView)
        mDialog.setContentView(mContentView)
        sbProgress.isEnabled = false
        sbTextsize.isEnabled = false
    }

    val dialog: Dialog
        get() {
            mDialog.setCanceledOnTouchOutside(true)
            mDialog.window!!.setWindowAnimations(R.style.style_bottom_in_anim)
            mDialog.window!!.setGravity(Gravity.BOTTOM)
            val lp = mDialog.window!!.attributes
            lp.dimAmount = 0.2f
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

    @OnClick(R.id.ll_collect, R.id.rb_collect, R.id.tv_collect, R.id.ll_all_chapter, R.id.tv_last_chapter, R.id.tv_next_chapter, R.id.tv_down_size, R.id.tv_up_size)
    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_collect -> if (mListener != null) mListener!!.onClickCollect()
            R.id.rb_collect -> if (mListener != null) mListener!!.onClickCollect()
            R.id.tv_collect -> if (mListener != null) mListener!!.onClickCollect()
            R.id.tv_last_chapter -> if (mListener != null) mListener!!.onClickLastChapter()
            R.id.tv_next_chapter -> if (mListener != null) mListener!!.onClickNextChapter()
            R.id.tv_down_size -> if (mListener != null) mListener!!.onClickDownSize(sbTextsize)
            R.id.tv_up_size -> if (mListener != null) mListener!!.onClickUpSize(sbTextsize)
            R.id.ll_all_chapter -> {
                if (mListener != null) mListener!!.onClickAllChapter()
                mDialog.dismiss()
            }
        }
    }

    /**
     * 传入数据
     *
     * @param dataList
     * @return
     */
    fun refreshData(chapterBean: ChapterBean): DIA_ReadSetting {
        mChapterBean = chapterBean
        sbProgress.progress = ((chapterBean.chapter_number * 1.0f / chapterBean.totol_size) * 100).toInt()
        return this
    }

    interface OnBoardClickListener {
        fun onClickCollect()
        fun onClickAllChapter()
        fun onClickLastChapter()
        fun onClickNextChapter()
        fun onClickDownSize(sbTextsize: SeekBar)
        fun onClickUpSize(sbTextsize: SeekBar)
    }

    var mListener: OnBoardClickListener? = null

    fun setOnBoardClickListener(listener: OnBoardClickListener) {
        mListener = listener
    }

    fun setCollectStatus(isCollected: Boolean) {
        if (isCollected) rbCollect.isSelected = true else rbCollect.isSelected = false
    }

}