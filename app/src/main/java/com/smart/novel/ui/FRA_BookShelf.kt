package com.smart.novel.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.interfaces.OnRefreshListener
import com.smart.framework.library.adapter.rv.normal.databinding.MultiItemTypeAdapter
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.framework.library.netstatus.NetUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ReadHistory
import com.smart.novel.adapter.ADA_ReadHistoryNodataBinding
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.dialog.DialogUtils
import com.smart.novel.mvp.contract.BookShelfContract
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter
import com.smart.novel.util.BroadCastConstant
import com.smart.novel.util.IntentUtil
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.fra_bookshelf.*
import kotlinx.android.synthetic.main.layout_common_recyclview.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseMVPFragment<BookShelfPresenter, BookShelfModel>(), BookShelfContract.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.tv_right) lateinit var tvRight: TextView
    var mAdapter: ADA_ReadHistory? = null
    val mColumnNum = 3//列表每行展示的个数
    val TYPE_READ = "read"
    val TYPE_LIKE = "like"
    var requestType = TYPE_READ
    var mCurrentPage = 1
    var isEdit = false//false 管理 true 完成(可编辑)
    var mData = ArrayList<NovelBean>()
    var deletePos = -1
    var deleteItem: NovelBean? = null
    var isRefreshing = true
    override fun onReceiveBroadcast(intent: Int, bundle: Bundle?) {
        when (intent) {
            BroadCastConstant.LOGOUT, BroadCastConstant.LOGIN_SUCCESS -> requestData(requestType, false)
        }
    }

    /**
     * companion object {}内：静态方法
     */
    companion object {
        fun getInstance(): FRA_BookShelf {
            val fragment = FRA_BookShelf()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_bookshelf
    }

    override fun startEvents() {
//        var actHome = activity as ACT_Home
//        actHome.lightModeStatusBar()
        tvRight.visibility = View.VISIBLE
        initRecyclerView()

        requestData(TYPE_READ, true)

        initListener()
    }

    /**
     * 处理列表
     */
    private fun initRecyclerView() {
        mAdapter = ADA_ReadHistory(activity)
        RecyclerViewHelper.initRecyclerView(activity, recyclerview, mAdapter!!, GridLayoutManager(activity, 3))
        recyclerview.setOnRefreshListener(this)
        recyclerview.setOnLoadMoreListener(this)
        recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView?) {
                //设计图item之间的间距为40 (header占了一个位置，故从位置1开始显示实际的item)
                if (itemPosition != 0 && itemPosition <= mColumnNum) {
                    outRect.top = 40
                }
                if (itemPosition > mColumnNum) {
                    outRect.top = 50
                }
                //设置item距离左边的距离
                outRect.left = 60
                if (itemPosition == mAdapter!!.dataList.size + 1 || itemPosition == 0) {
                    outRect.left = 0
                }
            }
        })
    }

    private fun initListener() {
        //item点击事件
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                if (isEdit) return
                var realPos = position - 1
                var bean = mAdapter!!.dataList.get(realPos)
                if (bean.type.equals(TYPE_READ)) {
                    var chapterBean = ChapterBean(bean.book_id, bean.name_cn, bean.chapter_number.toInt(), bean.chapter_name, bean.chapter_url, bean.origin_website, bean.covor_url)
                    IntentUtil.intentToReadNovel(activity, chapterBean)
                } else {
                    IntentUtil.intentToNovelDetail(activity, mAdapter!!.dataList.get(realPos))
                }
            }
        })
        //删除按钮
        mAdapter!!.setOnDeleteBtnClickListener(object : ADA_ReadHistory.OnDeleteBtnClickListener, ADA_ReadHistoryNodataBinding.OnDeleteBtnClickListener {
            override fun onDeleteBtnClick(position: Int, bean: NovelBean) {
                deleteItem = bean
                deletePos = position - 1
                Elog.e("TAG", "deletePos=" + deletePos)
                DialogUtils.showConfirmDialog(activity, "书架", "确定要移除吗?", object : DialogUtils.OnConfirmListener {
                    override fun onDialogConfirm() {
                        if (bean.type.equals(TYPE_READ)) mMvpPresenter.deleteReadRecord(bean.book_id) else mMvpPresenter.deleteCollect(bean.book_id)
                    }
                })

            }
        })

    }


    override fun onRefresh() {
//        Handler().postDelayed({ recyclerview.refreshComplete(1) }, 2000)
//        Elog.e("type", requestType).
        isRefreshing = true
        if (!NetUtils.isNetworkConnected()) {
            CommonUtils.makeShortToast(MyApplication.context.getString(R.string.network_error))
        } else {
            mCurrentPage = 1
            requestData(requestType, false)
        }
    }

    override fun onLoadMore() {
//        Handler().postDelayed({ recyclerview.setNoMore(true) }, 2000)
//        Elog.e("type", requestType)
        isRefreshing = false
        if (!NetUtils.isNetworkConnected()) {
            CommonUtils.makeShortToast(MyApplication.context.getString(R.string.network_error))
        } else {
            mCurrentPage++
            requestData(requestType, false)
        }
    }

    @OnClick(R.id.ll_read_history, R.id.ll_my_collected, R.id.tv_right)
    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_read_history -> {
                if (isEdit) return
                ll_read_history.getChildAt(0).visibility = View.VISIBLE
                ll_read_history.getChildAt(1).visibility = View.GONE
                ll_my_collected.getChildAt(0).visibility = View.GONE
                ll_my_collected.getChildAt(1).visibility = View.VISIBLE
                isRefreshing = true
                mCurrentPage = 1
                requestData(TYPE_READ, true)
            }
            R.id.ll_my_collected -> {
                if (isEdit) return
                ll_my_collected.getChildAt(0).visibility = View.VISIBLE
                ll_my_collected.getChildAt(1).visibility = View.GONE
                ll_read_history.getChildAt(0).visibility = View.GONE
                ll_read_history.getChildAt(1).visibility = View.VISIBLE
                isRefreshing = true
                mCurrentPage = 1
                requestData(TYPE_LIKE, true)
            }
            R.id.tv_right -> {
                //管理-完成
                if (!isEdit) {
                    tvRight.setText("完成")
                    isEdit = true
                } else {
                    tvRight.setText("管理")
                    isEdit = false
                }
                var data = ArrayList<NovelBean>()
                Elog.e("data", "list=" + mAdapter!!.dataList.size)
                for (i in 0..mAdapter!!.dataList.size - 1) {
                    val bean = mAdapter!!.dataList!!.get(i)
                    bean.isEdit = isEdit
                    data.add(bean)
                }
                mAdapter!!.update(data, true)
            }
        }
    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onUserInvisible() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {
        //本地阅读记录
//        if (requestType.equals(TYPE_READ)) {
//            val localList = DbManager.getInstance().queryAll(ChapterBean::class.java) as List<ChapterBean>
//            if (localList == null || localList.size == 0) {
//                showEmpty()
//                return
//            }
//            var mLocalList = ArrayList<NovelBean>()
//            for (i in 0..localList.size - 1) {
//                val chapterBean = localList.get(i)
//                var novelBean = NovelBean(chapterBean.book_id, chapterBean.name_cn, chapterBean.covor_url, "read", chapterBean.origin_website, chapterBean.chapter_url, chapterBean.chapter_number.toString(), chapterBean.chapter_name)
//                Elog.e("TAG", "yyy=" + novelBean.name_cn)
//                mLocalList.add(novelBean)
//            }
//            multipleStatusView.showContent()
//            mAdapter!!.update(mLocalList, true)
//        } else {
//            multipleStatusView.showError()
//        }
        showEmpty()
    }

    override fun showException(error: ErrorBean?) {

    }

    /**
     * 列表数据
     */
    override fun getBookShelfData(dataList: List<NovelBean>) {
        Elog.e("TAG", "isRefreshing=" + isRefreshing)
        //下拉刷新
        if (isRefreshing) {
            if (dataList != null && dataList!!.size > 0) {
                multipleStatusView.showContent()
                for (i in 0..dataList.size - 1) {
                    dataList!!.get(i).isEdit = isEdit
                }
                mAdapter!!.update(dataList!!, true)
                Elog.e("TAG", "dataList=" + dataList!!.size)
            } else {
                showEmpty()
            }
        } else {
            Elog.e("TAG", "loadmore")
            //loadmore
            if (dataList != null && dataList!!.size > 0) mAdapter!!.update(dataList!!, false) else recyclerview.setNoMore(true)
        }
        recyclerview.refreshComplete(1)
        tv_total.text = "共" + mAdapter!!.dataList.size + "本"
    }

    /**
     * 删除阅读记录
     */
    override fun deleteReadRecord(result: Any) {
//        isRefreshing = true
//        tvRight.setText("管理")
        isRefreshing = true
        mCurrentPage = 1
        CommonUtils.makeEventToast(MyApplication.context, "删除阅读记录成功", false)
//        mAdapter!!.remove(deletePos)
//        if (mAdapter!!.dataList.size == 0) showEmpty()
        requestData(requestType, false)
    }

    /**
     * 删除收藏
     */
    override fun deleteCollect(result: Any) {
//        tvRight.setText("管理")
//        isEdit = false
        isRefreshing = true
        mCurrentPage = 1
        CommonUtils.makeEventToast(MyApplication.context, "删除收藏成功", false)
//        mAdapter!!.remove(deletePos)
//        if (mAdapter!!.dataList.size == 0) showEmpty()
        requestData(requestType, false)
    }

    /**
     * 请求数据
     */
    private fun requestData(type: String, isShowLoading: Boolean) {
        if (isShowLoading) mMvpPresenter.getBookShelfData(type, mCurrentPage.toString(), multipleStatusView) else mMvpPresenter.getBookShelfData(type, mCurrentPage.toString(), null)
        requestType = type
    }

    /**
     * 展示空页面
     */
    private fun showEmpty() {
        multipleStatusView.showEmpty(R.drawable.ic_reading_no_data, MyApplication.context.getString(R.string.string_empty_bookshelf))
        isEdit = false
    }

    fun tabCheck() {

    }
}
