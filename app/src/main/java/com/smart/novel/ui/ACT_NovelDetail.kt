package com.smart.novel.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.smart.framework.library.adapter.rv.normal.databinding.MultiItemTypeAdapter
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.AppDateUtil
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ActNovelDetailBinding
import com.smart.novel.dialog.DIA_Share
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.mvp.model.NovelDetailModel
import com.smart.novel.mvp.presenter.NovelDetailPresenter
import com.smart.novel.util.IntentUtil
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.share.ShareEntity
import com.smart.novel.util.share.UMShareUtils.WEIXIN_PAKAGENAME
import com.smart.novel.util.share.UMShareUtils.isPlatformInstalled
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.act_novel_detail.*
import kotlinx.android.synthetic.main.layout_novel_detail_footer.*

/**
 * Created by JoJo on 2018/9/2.
wechat：18510829974
description：小说详情页
 */
class ACT_NovelDetail : BaseMVPActivity<NovelDetailPresenter, NovelDetailModel>(), NovelDetailContract.View {
    companion object {
        val FROM = "ACT_NovelDetail"
    }

    var novelBean: NovelBean? = null
    var mAdapter: ADA_ChapterList? = null
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    var mShareDialog: DIA_Share? = null
    var total_size = 100
    var novelDetailBean: NovelBean? = null
    var dataRealShow = ArrayList<ChapterBean>()
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

        //分享弹窗
        mShareDialog = DIA_Share(this)

        mAdapter = ADA_ChapterList(this)

        val layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                // 直接禁止垂直滑动
                return false
            }
        }
        recyclerviewChapters.layoutManager = layoutManager
        recyclerviewChapters.adapter = mAdapter

        //章节列表数据
        mMvpPresenter.getChapterList(multipleStatusView, novelBean!!.book_id, "", "")
        //小说详情-(刷新小说是否收藏的状态)
        requestNovelDetail()

        initListener()
    }

    private fun initListener() {
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                val chapterBean = mAdapter!!.dataList.get(position)
//                mMvpPresenter.addReadRecord(chapterBean.book_id.toString(), chapterBean.chapter_name, chapterBean.chapter_number.toString())

                //跳转到阅读页面
                chapterBean.totol_size = novelDetailBean!!.total_size
                IntentUtil.intentToReadNovel(this@ACT_NovelDetail, chapterBean)
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })
        mShareDialog!!.setOnShareBoardClickListener(object : DIA_Share.OnShareBoardClickListener {
            override fun onShareBoardClick(position: Int) {
                when (position) {
                //微信
                    0 -> {
                        var shareBean = ShareEntity()
                        shareBean.shareUrl = "https://www.baidu.com/"
                        isPlatformInstalled(this@ACT_NovelDetail, SHARE_MEDIA.WEIXIN, WEIXIN_PAKAGENAME, shareBean)
                    }
                //QQ
                    1 -> {
                        var shareBean = ShareEntity()
                        shareBean.shareUrl = "https://www.baidu.com/"
                        isPlatformInstalled(this@ACT_NovelDetail, SHARE_MEDIA.QQ, WEIXIN_PAKAGENAME, shareBean)
                    }
                //微博
                    2 -> {
                        var shareBean = ShareEntity()
                        shareBean.shareUrl = "https://www.baidu.com/"
                        isPlatformInstalled(this@ACT_NovelDetail, SHARE_MEDIA.SINA, WEIXIN_PAKAGENAME, shareBean)
                    }
                }
            }

        })
    }

    @OnClick(R.id.btn_collect, R.id.btn_share, R.id.btn_all_chapters, R.id.btn_read)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_collect -> {
                if (!MyApplication.isLogin) {
                    CommonUtils.makeShortToast("请先登录~")
                    return
                }
                if (btn_collect.isSelected) {
                    mMvpPresenter.deleteCollect(novelBean!!.book_id)
                } else {
                    mMvpPresenter.doCollect(novelBean!!.book_id)
                }
            }
            R.id.btn_share -> {
                CommonUtils.makeShortToast("功能正在开发中，请耐心等待~")
//                mShareDialog!!.dialog.show()
            }
        //跳转到所有章节页面
            R.id.btn_all_chapters -> {
                IntentUtil.intentToAllChapters(this, FROM, novelBean!!.book_id, total_size)
            }
            R.id.btn_read -> {
                if (dataRealShow.size == 0) {
//                mMvpPresenter.addReadRecord(dataRealShow.get(0).book_id.toString(), dataRealShow.get(0).chapter_name, dataRealShow.get(0).chapter_number.toString())
                    CommonUtils.makeShortToast("没有可读的章节~")
                    return
                }
                IntentUtil.intentToReadNovel(this@ACT_NovelDetail, dataRealShow.get(0))
            }

        }
    }

    /**
     * 在分享所在的Activity里复写onActivityResult方法,如果不实现onActivityResult方法，会导致分享或回调无法正常进行
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(MyApplication.context).onActivityResult(requestCode, resultCode, data)
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {
    }

    override fun getLastChapter(dataList: List<ChapterBean>) {
    }

    override fun getNextChapter(dataList: List<ChapterBean>) {
    }

    override fun getChapterList(dataList: List<ChapterBean>) {
        if (dataList == null || dataList.size == 0) return
        //第一页的最后一个为最新章节
        val newestChapterBean = dataList.get(dataList.size - 1)
        dataRealShow.clear()

        if (newestChapterBean.latest) dataRealShow.add(newestChapterBean)
        dataRealShow.addAll(dataList)
        if (dataList.size >= 5) mAdapter!!.update(dataRealShow.subList(0, 5), true) else mAdapter!!.update(dataRealShow, true)
    }

    override fun doCollect(result: Any) {
        requestNovelDetail()
        CommonUtils.makeShortToast("添加收藏成功")
    }

    override fun deleteCollect(result: Any) {
        CommonUtils.makeShortToast("删除收藏成功")
        requestNovelDetail()
    }


    override fun addReadRecord(result: Any) {
        CommonUtils.makeShortToast("添加阅读记录成功")
    }

    /**
     * 请求小说详情接口
     */
    private fun requestNovelDetail() {
        mMvpPresenter.getNovelDetail(novelBean!!.book_id)
    }

    /**
     * 请求小说详情回调
     */
    override fun getNovelDetail(dataList: List<NovelBean>) {
        val bean = dataList!!.get(0)
        novelDetailBean = bean
        //设置是否收藏的状态
        btn_collect.isSelected = !TextUtils.isEmpty(bean.like)//like-是否已收藏
        //章节总数
        total_size = bean.total_size

        if (!TextUtils.isEmpty(novelDetailBean!!.content_update_time)) {
            //20:40
            val time = AppDateUtil.getTimeStamp(java.lang.Long.parseLong(novelDetailBean!!.content_update_time), AppDateUtil.HH_MM)
            val splitTime = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val hour = splitTime[0]
            val minute = splitTime[1]
            val hourTime = Integer.parseInt(hour)
            if (hourTime < 24) {
                tv_date.setText(hour + "小时" + minute + "分前更新")
            } else {
                tv_date.setText(AppDateUtil.getTimeStamp(java.lang.Long.parseLong(novelDetailBean!!.content_update_time), AppDateUtil.YYYY_MM_DD_HH_MM1) + "更新")
            }
        }
    }

}