package com.smart.novel.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.bean.ChapterBean
import com.smart.novel.util.IntentUtil

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description: 阅读设置弹窗
 */

class DIA_ReadSetting(protected var context: Activity) : Dialog(context) {
    protected var mDialog: Dialog
    protected var mContentView: View
    var mAdapter: ADA_ChapterList? = null
    var mContext: Activity? = null
    var mChapterBean: ChapterBean? = null
    @BindView(R.id.tv_chapter_name) lateinit var tvChapterName:TextView

    init {
        mContext = context
        mDialog = Dialog(mContext, R.style.style_custom_dialog)
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_read_setting, null)
        ButterKnife.bind(this, mContentView)
        mDialog.setContentView(mContentView)
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

    @OnClick(R.id.rb_collect, R.id.ll_all_chapter)
    fun onClick(view: View) {
        when (view.id) {
            R.id.rb_collect -> CommonUtils.makeEventToast(MyApplication.context, "收藏", false)
            R.id.ll_all_chapter -> {
                IntentUtil.intentToAllChapters(mContext!!, mChapterBean!!.book_id, mChapterBean!!.totol_size)

            }
        }
        mDialog.dismiss()
    }

    /**
     * 传入数据
     *
     * @param dataList
     * @return
     */
    fun refreshData(chapterBean: ChapterBean): DIA_ReadSetting {
        mChapterBean = chapterBean
        tvChapterName.setText(mChapterBean!!.chapter_name)
        return this
    }

    //    }
    interface OnShareBoardClickListener {
        fun onShareBoardClick(position: Int)
    }

    var mListener: OnShareBoardClickListener? = null
}