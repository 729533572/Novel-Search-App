package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.PopupWindow
import butterknife.OnClick
import com.smart.framework.library.adapter.rv.normal.databinding.MultiItemTypeAdapter
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
import com.smart.novel.util.IntentUtil
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.RecyclerViewHelper
import de.greenrobot.event.EventBus
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
    var mTotalSize = 100//默认100章节
    var mAdapterFilter: ADA_ChapterFilter? = null
    //筛选下拉菜单数据
    var mFilterList = ArrayList<ChapterFilterBean>()

    var novelId: String = ""
    var mData: List<ChapterBean> = ArrayList()
    var mAdapter: ADA_ChapterList? = null
    var fromType = ""
    override fun getBundleExtras(extras: Bundle?) {
        novelId = extras!!.getString(PageDataConstants.NOVEL_ID, "")
        mTotalSize = extras!!.getInt(PageDataConstants.TOTAL_SIZE, 0)
        fromType = extras!!.getString(PageDataConstants.FROM, ACT_NovelDetail.FROM)
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_all_chapters
    }

    override fun startEvents() {
        //处理章节筛选数据
        handleFilterData()

        mAdapterFilter = ADA_ChapterFilter(this)
        //章节筛选弹窗
        mPopWindow = initPopupWindow(this, mAdapterFilter!!)

        //全部章节列表
        mAdapter = ADA_ChapterList(this, false)
        recyclerviewAllChapters.setPullRefreshEnabled(false)
        RecyclerViewHelper.initRecyclerView(this, recyclerviewAllChapters, mAdapter!!, LinearLayoutManager(this))

        mMvpPresenter.getChapterList(multipleStatusView, novelId!!, "n", mCurrentPage.toString())

        initListener()
    }

    private fun initListener() {
        mAdapterFilter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                mMvpPresenter.getChapterList(multipleStatusView, novelId!!, "n", (position + 1).toString())
                mCurrentPage = (position + 1)
                tv_chapter_filter.setText(mAdapterFilter!!.dataList.get(position).filterRange)
                mPopWindow!!.dismiss()
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                var realPos = position - 1
                val chapterBean = mAdapter!!.dataList.get(realPos)
                chapterBean.totol_size = mTotalSize
//                mMvpPresenter.addReadRecord(chapterBean.book_id.toString(), chapterBean.chapter_name, chapterBean.chapter_number.toString())
                if (fromType.equals(ACT_NovelDetail.FROM)) {
                    //跳转到阅读页面
                    IntentUtil.intentToReadNovel(this@ACT_AllChapters, chapterBean)
                } else {
                    finish()
                    EventBus.getDefault().post(chapterBean)
                }
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })

        mPopWindow?.setOnDismissListener {
            view_shadow.visibility = View.GONE
        }
    }

    @OnClick(R.id.ll_down_filter)
    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_down_filter -> {
                if (mPopWindow!!.isShowing) {
                    view_shadow.visibility = View.GONE
                    mPopWindow!!.dismiss()
                } else {
                    mAdapterFilter!!.update(mFilterList, true)
                    mPopWindow!!.showAsDropDown(ll_down_filter)
                    view_shadow.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun showException(error: ErrorBean?) {

    }

    override fun getLastChapter(dataList: List<ChapterBean>) {
    }

    override fun getNextChapter(dataList: List<ChapterBean>) {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun getChapterList(dataList: List<ChapterBean>) {
//        mData = dataList
        dataList?.let {
            mAdapter!!.update(it, true)
        }
    }

    /**
     * 处理章节筛选数据
     */
    private fun handleFilterData() {
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
        tv_chapter_filter.setText(mFilterList.get(0).filterRange)
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
        CommonUtils.makeEventToast(MyApplication.context, "添加阅读记录成功", false)
    }
}