package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.PopupWindow
import butterknife.OnClick
import com.smart.framework.library.adapter.rv.MultiItemTypeAdapter
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterFilter
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.ChapterFilterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.dialog.PopupChapterFilter.Companion.initPopupWindow
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
    var mPopWindow: PopupWindow? = null
    var mCurrentPage = 1
    //总页数-每页默认返回100条数据
    var mTotalPage = 1
    var mTotalSize = 100
    var mAdapterFilter: ADA_ChapterFilter? = null
    var mFilterList = ArrayList<ChapterFilterBean>()

    var novelId: String? = null
    var mData: List<ChapterBean> = ArrayList()
    var mAdapter: ADA_ChapterList? = null
    override fun getBundleExtras(extras: Bundle?) {
        novelId = extras!!.getString(PageDataConstants.NOVEL_ID, "")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_all_chapters
    }

    override fun startEvents() {
        mAdapterFilter = ADA_ChapterFilter(this)
        //章节筛选弹窗
        mPopWindow = initPopupWindow(this, mAdapterFilter!!)

        //全部章节列表
        mAdapter = ADA_ChapterList(this)
        recyclerviewAllChapters.setPullRefreshEnabled(false)
        RecyclerViewHelper.initRecyclerView(this, recyclerviewAllChapters, mAdapter!!, LinearLayoutManager(this))

        mMvpPresenter.getChapterList(multipleStatusView, novelId!!, "n", mCurrentPage.toString())

        initListener()
    }

    private fun initListener() {
        mAdapterFilter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                mMvpPresenter.getChapterList(multipleStatusView, novelId!!, "n", (position + 1).toString())
                tv_chapter_filter.setText(mAdapterFilter!!.dataList.get(position).filterRange)
                mPopWindow!!.dismiss()
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                val chapterBean = mAdapter!!.dataList.get(position)
                mMvpPresenter.addReadRecord(chapterBean.book_id.toString(), chapterBean.chapter_name, chapterBean.chapter_number)
                CommonUtils.makeEventToast(MyApplication.context, chapterBean.chapter_name, false)
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })

    }

    @OnClick(R.id.ll_down_filter)
    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_down_filter -> {
                if (mPopWindow!!.isShowing) {
                    mPopWindow!!.dismiss()
                } else {
                    mAdapterFilter!!.update(mFilterList, true)
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


        //处理章节筛选数据
        handleFilterData(dataList)
    }

    /**
     * 处理章节筛选数据
     */
    private fun handleFilterData(dataList: List<ChapterBean>) {
        mTotalSize = dataList.size + 346
        mTotalPage = mTotalSize / 100 + 1

        mFilterList.clear()
        //章节筛选数据
        for (i in 0..mTotalPage - 1) {
            if (i == mTotalPage - 1) {
                var bean = ChapterFilterBean("第" + (i * 100 + 1) + "-" + mTotalSize + "章")
                mFilterList.add(bean)
                break
            }
            var bean = ChapterFilterBean("第" + (i * 100 + 1) + "-" + (i + 1) * 100 + "章")
            mFilterList.add(bean)
        }
    }

    /**
     * 收藏
     */
    override fun doCollect(result: Any) {

    }

    override fun deleteCollect(result: Any) {

    }

    /**
     * 获取小说详情信息
     */
    override fun getNovelDetail(novelBean: List<NovelBean>) {
    }

    override fun addReadRecord(result: Any) {

    }
}