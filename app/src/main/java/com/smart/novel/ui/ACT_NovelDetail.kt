package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ActNovelDetailBinding
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.mvp.model.NovelDetailModel
import com.smart.novel.mvp.presenter.NovelDetailPresenter
import com.smart.novel.util.PageDataConstants
import kotlinx.android.synthetic.main.act_novel_detail.*

/**
 * Created by JoJo on 2018/9/2.
wechat：18510829974
description：小说详情页
 */
class ACT_NovelDetail : BaseMVPActivity<NovelDetailPresenter, NovelDetailModel>(), NovelDetailContract.View {

    var novelBean: NovelBean? = null
    var mAdapter: ADA_ChapterList? = null
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    override fun getContentViewLayoutID(): Int {
        return R.layout.act_novel_detail
    }

    override fun getBundleExtras(extras: Bundle?) {
        novelBean = extras!!.getSerializable(PageDataConstants.NOVEL_BEAN) as NovelBean
    }

    override fun startEvents() {
        ivLeft.visibility = View.VISIBLE
        tvTile.setText(novelBean!!.name_cn)

        //展示小说详情信息
        (viewDataBinding as ActNovelDetailBinding).novelBean = novelBean


        mAdapter = ADA_ChapterList(this)

        val layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                // 直接禁止垂直滑动
                return false
            }
        }
        recyclerviewChapters.layoutManager = layoutManager
        recyclerviewChapters.adapter = mAdapter

        mMvpPresenter.getChapterList(multipleStatusView, novelBean!!.id, "n", "1")

    }

    @OnClick(R.id.btn_collect, R.id.btn_share, R.id.btn_all_chapters, R.id.btn_read)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_collect -> {
            }
            R.id.btn_share -> {
            }
            R.id.btn_all_chapters -> {
                var bundle = Bundle()
                bundle.putString(PageDataConstants.NOVEL_ID, novelBean!!.id)
                readyGo(ACT_AllChapters::class.java, bundle)
            }
            R.id.btn_read -> {
            }
        }
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {
    }

    override fun getChapterList(dataList: List<ChapterBean>) {
        if (dataList == null) return
        if (dataList.size >= 5) mAdapter!!.update(dataList.subList(0, 5), true) else mAdapter!!.update(dataList, true)

    }

    override fun doCollect(result: Any) {
    }

    //是否已收藏
    override fun getNovelDetail(novelBean: NovelBean) {

    }
}