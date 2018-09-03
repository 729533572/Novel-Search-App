package com.smart.novel.ui

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.mvp.model.NovelDetailModel
import com.smart.novel.mvp.presenter.NovelDetailPresenter
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.act_all_chapters.*

/**
 * Created by JoJo on 2018/9/3.
 * wechat:18510829974
 * description: 小说所有章节页面
 */
class ACT_AllChapters : BaseMVPActivity<NovelDetailPresenter, NovelDetailModel>(), NovelDetailContract.View {
    var mAdapter: ADA_ChapterList? = null
    var mAdapterFilter: ADA_ChapterList? = null
    var novelId: String? = null
    var mData: List<ChapterBean> = ArrayList()
    var mPopWindow: PopupWindow? = null
    var recyclerviewFilter: RecyclerView? = null
    override fun getBundleExtras(extras: Bundle?) {
        novelId = extras!!.getString(PageDataConstants.NOVEL_ID, "")
    }

    override fun startEvents() {
        initPopupWindow()


        mAdapter = ADA_ChapterList(this)
        recyclerviewAllChapters.setPullRefreshEnabled(false)
        RecyclerViewHelper.initRecyclerView(this, recyclerviewAllChapters, mAdapter!!, LinearLayoutManager(this))

        mMvpPresenter.getChapterList(multipleStatusView, novelId!!, "n", "1")


    }


    override fun getContentViewLayoutID(): Int {

        return R.layout.act_all_chapters
    }

    @OnClick(R.id.ll_down_filter)
    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_down_filter -> {
//                var mDialog = DIA_ChaptersFilter(this)
//                mDialog.refreshData(mData)
//                mDialog.dialog.show()
                if (mPopWindow!!.isShowing) {
                    mPopWindow!!.dismiss()
                } else {
                    mAdapterFilter!!.update(mData.subList(0, 5), true)
                    mPopWindow!!.showAsDropDown(ll_down_filter)
                }

            }
        }
    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun showException(error: ErrorBean?) {

    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun getChapterList(dataList: List<ChapterBean>) {
        mData = dataList
        if (dataList == null) return
        if (dataList.size > 0) mAdapter!!.update(dataList, true)
    }

    override fun doCollect(result: Any) {
    }

    override fun getNovelDetail(novelBean: NovelBean) {

    }

    fun initPopupWindow() {
        //设置contentView
        var contentView = LayoutInflater.from(this).inflate(R.layout.dia_chapters_filter, null)
        recyclerviewFilter = contentView.findViewById(R.id.recyclerviewFilter) as RecyclerView
        //适配7.0版本
//        mPopWindow = MyPopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopWindow = PopupWindow(this)
        //必须设置宽高才能显示出来
        mPopWindow!!.height = LinearLayout.LayoutParams.WRAP_CONTENT
        mPopWindow!!.width = LinearLayout.LayoutParams.MATCH_PARENT
        mPopWindow!!.setContentView(contentView)
        mPopWindow!!.animationStyle = R.style.style_fade_in_anim
        //解决5.0以下版本点击外部不消失问题
        mPopWindow!!.setOutsideTouchable(true);
        mPopWindow!!.setFocusable(true)
        mPopWindow!!.setBackgroundDrawable(BitmapDrawable());
        // 刷新状态
        mPopWindow!!.update()
        mAdapterFilter = ADA_ChapterList(mContext)
        RecyclerViewHelper.initNormalRecyclerView(mContext, recyclerviewFilter!!, mAdapterFilter!!, LinearLayoutManager(mContext))
    }
}