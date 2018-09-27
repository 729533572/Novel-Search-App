package com.smart.novel.ui

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.framework.library.common.utils.DP2PX
import com.smart.framework.library.common.utils.ScreenUtil
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.db.manager.DbManager
import com.smart.novel.dialog.DIA_ReadSetting
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.mvp.model.NovelDetailModel
import com.smart.novel.mvp.presenter.NovelDetailPresenter
import com.smart.novel.util.IntentUtil
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.read.ReadView
import com.smart.novel.wights.SonnyJackDragView
import de.greenrobot.event.Subscribe
import de.greenrobot.event.ThreadMode
import kotlinx.android.synthetic.main.act_read.*
import org.jsoup.Jsoup


/**
 * Created by JoJo on 2018/8/24.
 *wechat：18510829974
 *description：章节阅读页面-抓取解析网页：view-source:https://www.zhuishu.tw/id61823/534992.html
 */
class ACT_Read : BaseMVPActivity<NovelDetailPresenter, NovelDetailModel>(), NovelDetailContract.View {
    companion object {
        val FROM = "ACT_Read"
    }

    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    @BindView(R.id.iv_right_two) lateinit var ivRightTwo: ImageView
    @BindView(R.id.ll_common_title) lateinit var llCommonTitle: LinearLayout
    @BindView(R.id.title_divider) lateinit var titleDivider: View
    var chapterBean: ChapterBean? = null
    var mTotalPage = 1
    var mDiaSetting: DIA_ReadSetting? = null
    var novelDetailBean: NovelBean? = null
    var mTotalSize = 100
    var i = 3
    var listColor = listOf<Int>(R.color.color_f9f9f9, R.color.color_f8efde, R.color.color_f2e7e9, R.color.color_e7f4e9, R.color.color_101419)
    //接收全部章节页面
    @Subscribe(threadMode = ThreadMode.MainThread)
    fun onSwitchChapter(bean: ChapterBean) {
        chapterBean = bean
        requestChapters(false)
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_read
    }

    override fun getBundleExtras(extras: Bundle?) {
        chapterBean = extras!!.getSerializable(PageDataConstants.CHAPTER_BEAN) as ChapterBean?
    }

    override fun startEvents() {
        ivLeft.visibility = View.VISIBLE
        ivRightTwo.visibility = View.VISIBLE
        setHeaderTitle(chapterBean!!.name_cn)
        mDiaSetting = DIA_ReadSetting(this)

//        //添加本地阅读记录
//        DbManager.getInstance().insertOrReplace(ChapterBean::class.java,chapterBean)
//        //阅读记录上传服务器
//        mMvpPresenter.addReadRecord(chapterBean!!.book_id.toString(), chapterBean!!.chapter_name, chapterBean!!.chapter_number.toString())
        //添加阅读记录
        uploadReadRecord()

        requestNovelDetail()

        //请求当然章节内容
        requestChapters(true)

        //悬浮可拖动的设置按钮
//        initSettingButton()

        initListener()

    }

    //标记是否是手势滑动切换章节，如果是手势滑动切换，则左滑切换上一章节，需要切到上一章节最后一页而非第一页
    var isScroll = false

    //阅读的view的点击事件
    private fun initListener() {
        readView.setOnItemSelectListener(object : ReadView.OnItemSelectListener {
            override fun onTotalPage(totalPage: Int) {
                mTotalPage = totalPage
                tv_page_num.setText(readView.mCurrentPage.toString() + "/" + mTotalPage)
                Elog.e("page",readView.mCurrentPage.toString() + "/" + mTotalPage)
            }

            override fun onScrollRight() {
                isScroll = true
                if (readView.mCurrentPage == 1) {
                    mMvpPresenter.getLastChapter(chapterBean!!.book_id, chapterBean!!.chapter_number.toString())
//                    CommonUtils.makeShortToast("切换上一章")
                    return

                }
                readView.lastPage()
                tv_page_num.setText(readView.mCurrentPage.toString() + "/" + mTotalPage)
            }

            override fun onScrollLeft() {
                isScroll = false
                //如果是最后一页，广告页，滑动下一页，则切换下一章节
                if (readView.mCurrentPage == mTotalPage) {
                    mMvpPresenter.getNextChapter(chapterBean!!.book_id, chapterBean!!.chapter_number.toString())
//                    CommonUtils.makeShortToast("切换下一章")
                    return
                }
                readView.nextPage()
                tv_page_num.setText(readView.mCurrentPage.toString() + "/" + mTotalPage)
            }

        })
        //文本文字大小
        var defaultTextSize = 48
        var space = 5
        var listSize = listOf<Int>(defaultTextSize - space * 3, defaultTextSize - 2 * space, defaultTextSize - space, defaultTextSize, defaultTextSize + space, defaultTextSize + 2 * space, defaultTextSize + 3 * space)
        readView.setFontSize(listSize[3])
        //阅读设置弹窗事件处理
        mDiaSetting!!.setOnBoardClickListener(object : DIA_ReadSetting.OnBoardClickListener {
            override fun onClickLastChapter() {
                isScroll = false
                mMvpPresenter.getLastChapter(chapterBean!!.book_id, chapterBean!!.chapter_number.toString())
            }

            override fun onClickNextChapter() {
                isScroll = false
                mMvpPresenter.getNextChapter(chapterBean!!.book_id, chapterBean!!.chapter_number.toString())
            }

            override fun onClickCollect() {
                if (!MyApplication.isLogin) {
//                    CommonUtils.makeShortToast("请先登录~")
                    readyGo(ACT_Login::class.java)
                    mDiaSetting!!.dismiss()
                    return
                }
                if (novelDetailBean == null) {
                    mMvpPresenter.getNovelDetail(chapterBean!!.book_id)
                    return
                }
                if (TextUtils.isEmpty(novelDetailBean!!.like)) mMvpPresenter.doCollect(chapterBean!!.book_id) else mMvpPresenter.deleteCollect(chapterBean!!.book_id)
            }

            override fun onClickAllChapter() {
                if (chapterBean!!.totol_size <= 0) chapterBean!!.totol_size = 100
                IntentUtil.intentToAllChapters(this@ACT_Read, FROM, chapterBean!!.book_id, chapterBean!!.totol_size)
            }

            //            var default_size = DP2PX.dip2px(context, readView.fontSize.toFloat())
            //缩小文字
            override fun onClickDownSize(sbTextsize: SeekBar) {
                var default_size = DP2PX.dip2px(mContext, readView.fontSize.toFloat())
                if (sbTextsize.progress > 0) {
                    i--
                    sbTextsize.progress = i * 100
                    readView.setFontSize(listSize[i])
                }
            }

            //放大文字
            override fun onClickUpSize(sbTextsize: SeekBar) {
                var default_size = DP2PX.dip2px(mContext, readView.fontSize.toFloat())
                if (sbTextsize.progress < 600) {
                    i++
                    sbTextsize.progress = i * 100
                    readView.setFontSize(listSize[i])
                }
            }

        })
        mDiaSetting!!.sbTextsize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                var progress = seekBar!!.progress
                when (progress) {
                    in 0..49 -> seekBar.progress = 0
                    in 50..99 -> seekBar.progress = 100
                    in 100..199 -> seekBar.progress = 200
                    in 200..299 -> seekBar.progress = 300
                    in 300..399 -> seekBar.progress = 400
                    in 400..499 -> seekBar.progress = 500
                    in 500..600 -> seekBar.progress = 600
                }
                readView.setFontSize(listSize[seekBar.progress / 100])
            }

        })

        //阅读模式
        mDiaSetting!!.llReadMode.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                var position = 0
                when (checkedId) {
                    R.id.rb_grey -> {
                        position = 0
//                        readView.setTextColor(R.color.color_2E3439)
                        tv_chapter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999))
                        titleDivider.visibility = View.VISIBLE
                        titleDivider.setBackgroundResource(R.drawable.bg_shape_gradient_line_reverse)
                        llCommonTitle.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_ffffff))
                        rl_root.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_ffffff))
                    }
                    R.id.rb_yellow -> {
                        position = 1
//                        readView.setTextColor(R.color.color_2E3439)
                        tv_chapter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999))
                        titleDivider.visibility = View.INVISIBLE
                    }
                    R.id.rb_pink -> {
                        position = 2
//                        readView.setTextColor(R.color.color_2E3439)
                        tv_chapter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999))
                        titleDivider.visibility = View.INVISIBLE
                    }
                    R.id.rb_green -> {
                        position = 3
//                        readView.setTextColor(R.color.color_2E3439)
                        tv_chapter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999))
                        titleDivider.visibility = View.INVISIBLE
                    }
                    R.id.rb_night -> {
                        position = 4
//                        readView.setTextColor(R.color.color_3AC270)
                        tv_chapter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_2E3439))
                        titleDivider.visibility = View.INVISIBLE
                    }
                }
                ll_content_root.setBackgroundColor(ContextCompat.getColor(mContext, listColor[position]))
                if (position != 0) {
                    llCommonTitle.setBackgroundColor(ContextCompat.getColor(mContext, listColor[position]))
                    rl_root.setBackgroundColor(ContextCompat.getColor(mContext, listColor[position]))
                }
                (mDiaSetting!!.llReadMode.getChildAt(position) as RadioButton).isChecked = true
            }

        })

    }

    @OnClick(R.id.iv_setting, R.id.iv_right_two)
    fun onClick(view: View) {
        when (view.id) {
            R.id.iv_setting -> mDiaSetting!!.refreshData(chapterBean!!).dialog.show()
            R.id.iv_right_two -> {
//                CommonUtils.makeShortToast("功能正在开发中，请耐心等待~")
                novelDetailBean?.let {
                    chapterBean!!.author = novelDetailBean!!.author
                    IntentUtil.intentToOriginWebsite(this, chapterBean!!)
                }
            }
        }
    }

    /**
     * 创建悬浮可拖动的设置按钮
     */
    private fun initSettingButton() {
        val ivSetting = ImageView(this)
        ivSetting.scaleType = ImageView.ScaleType.CENTER_CROP
        ivSetting.setImageResource(R.drawable.ic_btn_fiction_details_set)
        //        IntentUtil.intentToAllChapters(mContext!!, mChapterBean!!.book_id, mChapterBean!!.totol_size)
        ivSetting.setOnClickListener { v ->
            mDiaSetting!!.refreshData(chapterBean!!).dialog.show()
        }
        val sonnyJackDragView = SonnyJackDragView.Builder()
                .setActivity(this)//当前Activity，不可为空
                .setDefaultLeft(ScreenUtil.getScreenWidth(this) - 140)//初始位置左边距
                .setDefaultTop(ScreenUtil.getScreenHeight(this) - ScreenUtil.getStatusBarHeight(this) - 400)//初始位置上边距
                .setNeedNearEdge(false)//拖动停止后，是否移到边沿
                .setSize(100)//DragView大小
                .setView(ivSetting)//设置自定义的DragView，切记不可为空
                .build()
    }

    /**
     * 请求小说详情获取收藏状态和章节总数total_size
     */
    private fun requestNovelDetail() {
        mMvpPresenter.getNovelDetail(chapterBean!!.book_id)
    }

    /**
     * 请求章节内容-解析chapterBean!!.chapter_url-----切换上下章节刷新页面时，使用了 multipleStatusView.showLoading()，会抛出窗口泄露异常
     */
    private fun requestChapters(isShowLoading: Boolean) {
        var handler = Handler()
        Thread(Runnable {
            if (isShowLoading) multipleStatusView.showLoading()
            try {
                val conn = Jsoup.connect(chapterBean!!.chapter_url).timeout(5000)
                conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                conn.header("Accept-Encoding", "gzip, deflate, sdch");
                conn.header("Accept-Language", "zh-CN,zh;q=0.8");
                conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                val content = conn.get().getElementById("content")
                Elog.e("content", content.text())
                handler.post({
                    if (isShowLoading) multipleStatusView.showContent()
                    tv_chapter_name.setText(chapterBean!!.chapter_name)
                    //从全部章节返回时切换上下章节设置内容时，从第一页开始，而非当前页开始
                    readView.init()
                    readView.mCurrentPage = 1
                    readView.setIsScroll(isScroll)
                    readView.setText(content.text())
//                    if (isScroll) {
//                        readView.mCurrentPage = mTotalPage
//                        readView.nextPage()
//                    }
                })
            } catch (e: Exception) {
                handler.post({
                    CommonUtils.makeShortToast("章节阅读失败")
                })
                Elog.e("TAG", e.toString())
            }
        }).start()
    }

    /**
     * 获取上一章
     */
    override fun getLastChapter(dataList: List<ChapterBean>) {
        if (dataList == null || dataList.size == 0) {
            CommonUtils.makeShortToast("没有上一章啦")
            return
        }
        switchChapter(dataList)
    }

    /**
     * 获取下一章
     */
    override fun getNextChapter(dataList: List<ChapterBean>) {
        if (dataList == null || dataList.size == 0) {
            CommonUtils.makeShortToast("已经是最后一章啦")
            return
        }
        switchChapter(dataList)
    }

    /**
     * 切换章节，刷新页面
     */
    private fun switchChapter(dataList: List<ChapterBean>) {
        //更新章节bean
        chapterBean = dataList.get(0)
        chapterBean!!.totol_size = mTotalSize
        mDiaSetting!!.refreshData(chapterBean!!)

        //切换上下章节设置内容时，从第一页开始，而非当前页开始
        readView.init()
        readView.mCurrentPage = 1
        requestChapters(false)

        //切换章节，重新添加阅读记录
        uploadReadRecord()
    }

    /**
     * 进入章节阅读、切换章节，添加阅读记录
     */
    private fun uploadReadRecord() {
        //添加本地阅读记录
        addLocalCache()
        //阅读记录上传服务器
        if (MyApplication.isLogin) mMvpPresenter.addReadRecord(chapterBean!!.book_id.toString(), chapterBean!!.chapter_name, chapterBean!!.chapter_number.toString())
    }

    /**
     * 添加本地阅读记录
     */
    private fun addLocalCache() {
        DbManager.getInstance().insertOrReplace(ChapterBean::class.java, chapterBean)
    }

    override fun showException(error: ErrorBean?) {

    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun isBindEventBusHere(): Boolean {
        return true
    }

    override fun getChapterList(dataList: List<ChapterBean>) {
    }

    override fun doCollect(result: Any) {
        mDiaSetting!!.setCollectStatus(true)
        CommonUtils.makeShortToast("收藏成功")
        requestNovelDetail()
    }

    override fun deleteCollect(result: Any) {
        mDiaSetting!!.setCollectStatus(false)
        CommonUtils.makeShortToast("取消收藏成功")
        requestNovelDetail()
    }

    override fun addReadRecord(result: Any) {
//        CommonUtils.makeShortToast("添加阅读记录成功")
    }

    override fun getNovelDetail(dataList: List<NovelBean>) {
        if (dataList == null || dataList.size == 0) return
        novelDetailBean = dataList.get(0)
        if (novelDetailBean == null) return

        mTotalSize = novelDetailBean!!.total_size

        if (!TextUtils.isEmpty(novelDetailBean!!.like)) mDiaSetting!!.setCollectStatus(true) else mDiaSetting!!.setCollectStatus(false)

        if (chapterBean != null) {
            chapterBean!!.totol_size = mTotalSize

        }
    }
}


//(1)解析String类型的html格式的数据
//        val doc = Jsoup.parse("\n" +
//                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
//                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
//                "<head>\n" +
//                "<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\n" +
//                "<meta http-equiv=\"Cache-Control\" content=\"no-transform\" />\n" +
//                "<script type=\"text/javascript\" src=\"/js/wap.js\"></script>\n" +
//                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
//                "<title>超凡战尊 第一章 少年萧凡</title>\n" +
//                "<meta name=\"keywords\" content=\"超凡战尊 第一章 少年萧凡\" />\n" +
//                "<meta name=\"description\" content=\"超凡战尊新章节已经更新 第一章 少年萧凡 啦，如你喜欢小说超凡战尊，请将超凡战尊加入书架。专注于超凡战尊小说将第一时间更新超凡战尊最新章节\" />\n" +
//                "<meta http-equiv=\"mobile-agent\" content=\"format=html5; url=https://m.zhuishu.tw/id61823/534992.html\" />\n" +
//                "<meta http-equiv=\"mobile-agent\" content=\"format=xhtml; url=https://m.zhuishu.tw/id61823/534992.html\" />\n" +
//                "<script type=\"text/javascript\" src=\"/js/zepto.min.js\"></script>\n" +
//                "<script type=\"text/javascript\" src=\"/js/common.js\"></script>\n" +
//                "<script type=\"text/javascript\" src=\"/js/read.js\"></script>\n" +
//                "<link rel=\"stylesheet\" href=\"/css/style.css\" />\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<div id=\"wrapper\">\n" +
//                "\t<script>login();</script>\n" +
//                "\t<div class=\"header\">\n" +
//                "\t\t<div class=\"header_logo\">\n" +
//                "\t\t\t<a href=\"/\">追书网</a>\n" +
//                "\t\t</div>\n" +
//                "\t\t<script>panel();</script>\n" +
//                "\t</div>\n" +
//                "\t<div class=\"nav\">\n" +
//                "\t\t<ul>\n" +
//                "\t\t\t<li><a href=\"/\">追书网</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_1/\">玄幻奇幻</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_2/\">武侠仙侠</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_3/\">都市言情</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_4/\">历史军事</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_5/\">科幻灵异</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_6/\">网游竞技</a></li>\n" +
//                "\t\t\t<li><a href=\"/id_7/\">女频频道</a></li>\n" +
//                "\t\t\t<li><a href=\"/idph_2.html\">排行榜</a></li>\n" +
//                "\t\t\t<li><a href=\"/ClassicBook.html\">全本</a></li>\n" +
//                "\t\t\t<li><a href=\"/TempBookCase.aspx\">书架</a></li>\n" +
//                "\t\t</ul>\n" +
//                "\t</div>\n" +
//                "\t<div class=\"content_read\">\n" +
//                "\t\t<div class=\"box_con\">\n" +
//                "\t\t\t<div class=\"con_top\"><script>textselect();</script>\n" +
//                "\t\t\t\t<a href=\"/\">追书网</a> &gt; <a href=\"/id_1/\">玄幻小说</a> &gt; <a href=\"/id61823/\">超凡战尊</a> &gt; 第一章 少年萧凡\n" +
//                "\t\t\t</div>\n" +
//                "\t\t\t<div class=\"bookname\">\n" +
//                "\t\t\t\t<h1>第一章 少年萧凡</h1>\n" +
//                "\t\t\t\t<div class=\"bottem1\">\n" +
//                "\t\t\t\t\t<a href=\"/id61823/534991.html\">上一章</a> &larr; <a href=\"/id61823/\">章节列表</a> &rarr; <a href=\"/id61823/534993.html\">下一章</a> <a rel=\"nofollow\" href=\"javascript:;\" onclick=\"addBookMark(534992,61823);\">加入书签</a>\n" +
//                "\t\t\t\t</div>\n" +
//                "\t\t\t\t<div class=\"lm\">推荐阅读：<a href=\"/id61824/\">毁天屠帝</a><a href=\"/id61825/\">华年</a><a href=\"/id61822/\">灰烬神座</a><a href=\"/id61821/\">等我回家</a><a href=\"/id61820/\">浴血天都</a><a href=\"/id61819/\">天尊下凡</a><a href=\"/id61818/\">龙战少年</a><a href=\"/id61817/\">我的美女主播姐姐</a><a href=\"/id61816/\">最后一个大魔头</a><a href=\"/id61815/\">超级医道兵王</a></div>\n" +
//                "\t\t\t</div>\n" +
//                "\t\t\t<div style=\"text-align: center\"><script>read2();</script></div>\n" +
//                "\t\t\t<div id=\"content\">&nbsp;&nbsp;&nbsp;&nbsp;第一章&nbsp;少年萧凡<br />&nbsp;&nbsp;&nbsp;&nbsp;“不！”<br />&nbsp;&nbsp;&nbsp;&nbsp;“牧云谣别离开我，就算拼尽最后的神力，也要护你周全。不管几生几世,我龙敖天,都与你同生共死!”<br />&nbsp;&nbsp;&nbsp;&nbsp;萧凡惊恐一声，猛然睁开眼睛，看着四周陌生的环境，一脸茫然，突然双手往身下一拍，就坐了起来。<br />&nbsp;&nbsp;&nbsp;&nbsp;长长的吐出一口凉气，用衣袖将额头上的汗珠擦干，暗想道：“这剑帝东方白，平日里是个歉歉君子，孰不知连小人都不如，是个彻彻底底的伪君子？”<br />&nbsp;&nbsp;&nbsp;&nbsp;他越想越气愤！<br />&nbsp;&nbsp;&nbsp;&nbsp;君子报仇，十年未晚。<br />&nbsp;&nbsp;&nbsp;&nbsp;“东方白，竟敢夺走我的未婚妻，并一剑杀死我，待我重修十年，定报夺妻之很、一剑之仇。”<br />&nbsp;&nbsp;&nbsp;&nbsp;一千年的岁月，转瞬即逝，不知道那个可恶的东方白还活着么？若是他还活着，必取其性命，萧凡让他三更死，绝对不会留到五更。<br />&nbsp;&nbsp;&nbsp;&nbsp;……<br />&nbsp;&nbsp;&nbsp;&nbsp;千尘仙女牧云谣，在萧凡占居着重要位置，可以这样说，他能够在千年之后重生，全靠他对牧云谣的执念，一世轮回，一缕执念，思谣相遇。<br />&nbsp;&nbsp;&nbsp;&nbsp;牧云谣是苍玄大陆牧府的天之骄女，年仅十五岁，便以冰凤气运，修炼到玄极境九重天。<br />&nbsp;&nbsp;&nbsp;&nbsp;各路天才纷纷前往牧府提亲，可是，令他们失望的是，这位名动苍玄的女子竟然把他们都给拒绝了！<br />&nbsp;&nbsp;&nbsp;&nbsp;拒绝了他们不要紧，可是，她选择的未婚夫深深的刺痛了他们的神经，一股挫败感……<br />&nbsp;&nbsp;&nbsp;&nbsp;她选择的未婚夫，正是七绝门第一天才，少主萧凡。<br />&nbsp;&nbsp;&nbsp;&nbsp;七绝少主来到牧府，奉上九霄古剑，玄灵术《诸天神印》，作为求婚礼品，顿时轰到苍玄万界。<br />&nbsp;&nbsp;&nbsp;&nbsp;那牧府见此，立即就答应了他和牧云谣的婚事，而且牧云谣本人对前者的印象也是非常好<br />&nbsp;&nbsp;&nbsp;&nbsp;此刻，苍玄万界的天才们，对萧凡充满了敌意，早就忍不住了，要对萧凡发起挑战，趁机打败萧凡，把牧云谣抢到手，并和牧府联姻，这种想法不光是这些少年的想法，还代表着他们背后势力的想法，若不是他们忌惮萧凡父亲萧渊，他们又怎么会如此小心。<br />&nbsp;&nbsp;&nbsp;&nbsp;正在他成为苍玄大陆时空武帝的时候，可惜的是，他和剑帝东方白前往玄冥山寻找十方仙帝的传承，找到了“乾坤圣龙经”和“圣龙魂”，却不幸的是，剑帝东方白看到这卷绝世功法，眼中透着一股贪婪之色，似乎要杀人夺宝，最终他成功了，龙敖天却陨落了，但是“乾坤圣龙经”和“圣龙魂”随着萧凡的陨落而一同消失，剑帝最终是竹蓝打水一场空，这也算是恶有恶报。<br />&nbsp;&nbsp;&nbsp;&nbsp;剑帝，东方白，自封天外飞仙，是域外的主宰。<br />&nbsp;&nbsp;&nbsp;&nbsp;可笑的是，萧凡到死也想不到，自己曾经信任的兄弟，却成为夺妻之人，更令自己陨落。<br />&nbsp;&nbsp;&nbsp;&nbsp;当萧凡奇迹般醒过来，却发现自己己经在千年之后。<br />&nbsp;&nbsp;&nbsp;&nbsp;…………<br />&nbsp;&nbsp;&nbsp;&nbsp;“凡儿，你醒了。”<br />&nbsp;&nbsp;&nbsp;&nbsp;随即，耳边传来一阵温柔的女声。<br />&nbsp;&nbsp;&nbsp;&nbsp;他微微一眐，朝那女声望去，便看到一个身体柔弱的宫装美妇人，缓缓的向他走来，眼神里却是流露出一抹担忧。<br />&nbsp;&nbsp;&nbsp;&nbsp;萧凡看着眼前这个宫装美妇人，暗想道：“这美妇人是谁，为何这样看着我？”<br />&nbsp;&nbsp;&nbsp;&nbsp;随着记忆的融合，原来这美妇人不是别人，正是萧凡的娘亲，秦妃。<br />&nbsp;&nbsp;&nbsp;&nbsp;秦妃从十三岁嫁给大周郡王萧渊，幸福美满，像是一对人间眷侣，不过后来，却让诸位嫔妃羡慕恨的秦妃娘娘，因为她的独子萧凡，十五年未能开启“图腾气运”，而且她那侄子秦牧打伤了太子，虽然最终秦牧被废修为，但是秦妃还是遭到龙渊的疏远，从而导致她在后宫的地位，一落千丈。昔日的龙门少主没有想到自己竟然可以轮回转世到这具身体上，不过巧合的是，这具身体的原主人，和他同名同姓，也叫龙敖天。<br />&nbsp;&nbsp;&nbsp;&nbsp;萧凡，十五岁，大周郡国十四王子。其母秦伊，是郡王曾经喜爱的妃子。其父萧渊虽说是当世豪杰，但也被大秦皇国常常打压欺负，十年前氓山大战，被断一只手，修为一直停步不前，仿佛是大周气运被割断了一般。<br />&nbsp;&nbsp;&nbsp;&nbsp;……<br />&nbsp;&nbsp;&nbsp;&nbsp;三天前，七王子辱骂十四王子萧凡是“废物”，浪费王室修炼资源，至今还没有开启“图腾气运”。<br />&nbsp;&nbsp;&nbsp;&nbsp;萧凡忍无可忍，大怒……<br />&nbsp;&nbsp;&nbsp;&nbsp;他连气运都没有开启，又怎么会是炼体境四重七王子的对手，恐怕只能乖乖认输。<br />&nbsp;&nbsp;&nbsp;&nbsp;可是十四王子岂是会乖乖认输的人，既使是死，他也是要战死，也要拉着七王子一块死。<br />&nbsp;&nbsp;&nbsp;&nbsp;“战就战！废话少说……”萧凡怒道。<br />&nbsp;&nbsp;&nbsp;&nbsp;“呵呵。你找死！”&nbsp;七王子仗着自己的修为是炼体境四重，率先出手，一拳就把萧凡给打死，血肉模糊。<br />&nbsp;&nbsp;&nbsp;&nbsp;此时，萧凡只剩有最后半口气，一阵风吹过，他撒手而去，此刻，一千年前的七绝少主时空武帝“萧凡”却意外的进入他的身体，接收了他的一切，包含意识、记忆等等。<br />&nbsp;&nbsp;&nbsp;&nbsp;“哎，看你这活的窝囊，我真替你感到羞耻，不过，我既然借用你的身体，必会与那七王子再斗，为死去的你报仇血恨。”<br />&nbsp;&nbsp;&nbsp;&nbsp;“你安心去吧，以后我会帮你好好照顾你娘亲。”<br />&nbsp;&nbsp;&nbsp;&nbsp;一人之下，万人之上……<br />&nbsp;&nbsp;&nbsp;&nbsp;他看着这具肉身，思绪万千。<br />&nbsp;&nbsp;&nbsp;&nbsp;若是他有朝一日再见到剑帝，一定让把剑东方白挫骨扬灰。<br />&nbsp;&nbsp;&nbsp;&nbsp;“秦妃娘娘，王上让人把药送来了。”<br />&nbsp;&nbsp;&nbsp;&nbsp;一个宫女急匆匆从殿外走进，将手里刻有龙纹的精致盒子递给秦妃。<br />&nbsp;&nbsp;&nbsp;&nbsp;秦妃打开盒子。<br />&nbsp;&nbsp;&nbsp;&nbsp;双目注视着盒子，这……这是？<br />&nbsp;&nbsp;&nbsp;&nbsp;“王上好像说，这是龙血丹！”宫女眼睛微眨，缓缓的道。<br />&nbsp;&nbsp;&nbsp;&nbsp;秦妃沉思片刻，脸上露出了许久未出的笑颜，旋即从盒子里面取出一颗泛红的药丸，递到了萧凡嘴边：“凡儿，乖，服下龙血丹，你身体上的伤势就会好的”<br />&nbsp;&nbsp;&nbsp;&nbsp;“这是龙血丹?”少年看着他娘亲手上的丹药，又道：“听说龙血丹是父王疗伤的灵药，充斥着龙吟气息，有着起死回生之效。”<br />&nbsp;&nbsp;&nbsp;&nbsp;“&nbsp;嗯！”秦妃面含笑容。<br />&nbsp;&nbsp;&nbsp;&nbsp;随即，萧凡张开嘴把龙血丹药吞下。化作缓缓暖流，进入了他的体内，下一刻，药力化作一道道龙吟气猛烈冲击着他胸膛，那撕心裂肺的痛苦，随之而来……<br />&nbsp;&nbsp;&nbsp;&nbsp;紧接着，秦妃施展灵力，向他体内输送灵气，尝试着减少他的痛苦。<br />&nbsp;&nbsp;&nbsp;&nbsp;要知道，这一股灵气的气流，是至阴之气，才有可能将龙血丹缓缓的化开，归息到丹田神海处。<br />&nbsp;&nbsp;&nbsp;&nbsp;三个小时之后，秦妃额头上露出汗珠，缓缓收手，此时，她脸色苍白，显然是为萧凡疗伤而耗费了大量灵气。<br />&nbsp;&nbsp;&nbsp;&nbsp;就在此时，萧凡缓缓睁开眼睛，却发现，自己的身上酸痛消失了大半，坐起来根本没有任何负担。<br />&nbsp;&nbsp;&nbsp;&nbsp;同时，他的脸上，顿时露出惊骇之色……<br />&nbsp;&nbsp;&nbsp;&nbsp;半个小时后，少年少女再次走进房，少女轻声问道:“秦妃娘娘，听说十四王子被七王子一拳轰杀，不知道是不是真的？<br />&nbsp;&nbsp;&nbsp;&nbsp;紧接着少年又道：“若是十四弟还活着，那么秦家秦雅就和他解除婚约？”<br />&nbsp;&nbsp;&nbsp;&nbsp;堂堂十四王子竟然被家族之女解除婚约，大周皇室的脸面该往那里搁。<br />&nbsp;&nbsp;&nbsp;&nbsp;而秦家选择在这个时候，要十四王子解除婚的，绝对是落井下石。<br />&nbsp;&nbsp;&nbsp;&nbsp;“活……”<br />&nbsp;&nbsp;&nbsp;&nbsp;“当然活着。”<br />&nbsp;&nbsp;&nbsp;&nbsp;秦妃脸上的善意，瞬间就消失了，板着脸对着少女秦雅，怒道：“小雅你是本宫看着长大的，现在翅膀硬了，就敢这样退婚。”<br />&nbsp;&nbsp;&nbsp;&nbsp;“还有你七王子，也帮着秦雅欺负你十四弟。”<br />&nbsp;&nbsp;&nbsp;&nbsp;“呵呵！。”少女秦雅和七王子看了秦妃一眼，旋即笑道：“秦妃娘娘，你真是无知，以前我们秦家敬畏你，那是在王宫里面的地位很高，可是现在就不同了，七王子很喜欢我，你那废物儿子我早就看不上了，让他趁早死心。”<br />&nbsp;&nbsp;&nbsp;&nbsp;随即，七王子的双手把秦雅搂到怀里，望着床塌上的萧凡，笑道：“十四弟，秦雅从今日起，就是我萧战的女人，若是以后敢碰我的女人，我一定要了你的命。”<br />&nbsp;&nbsp;&nbsp;&nbsp;这时，秦妃挥了挥玉手，含恨道：“这秦雅是十四弟的未婚妻，你就是明抢。”<br />&nbsp;&nbsp;&nbsp;&nbsp;“秦妃娘娘，我就是明抢，你又能把我怎么样？”七王子戏谑道。<br />&nbsp;&nbsp;&nbsp;&nbsp;“滚！”<br />&nbsp;&nbsp;&nbsp;&nbsp;“三日后，我一定打败你，让她秦雅知道今日的选择是多么的愚蠢。”<br />&nbsp;&nbsp;&nbsp;&nbsp;一道稚嫩的怒声传到秦雅，令她眉头一皱，旋即，抬起头，望着那床塌上的少年，不怒反笑。<br />&nbsp;&nbsp;&nbsp;&nbsp;“废物表哥，还有脾气？”<br />&nbsp;&nbsp;&nbsp;&nbsp;“三日就想打败七王子，痴人说梦。”<br />&nbsp;&nbsp;&nbsp;&nbsp;七王子闻言，噗嗤一笑，望着萧凡，一脸不屑的道：“好！我等着，三日后你如何打败我。”<br />&nbsp;&nbsp;&nbsp;&nbsp;随即，萧凡把一纸休书扔到秦舒雅的面前，后者见状，微微一眐，显然是被前者的气势给吓倒了。<br />&nbsp;&nbsp;&nbsp;&nbsp;“别欺人太甚！那现在我就一纸休书，休了你秦雅。”<br />&nbsp;&nbsp;&nbsp;&nbsp;“一年之后，我将拜访秦家，洗刷今日之辱。”<br />&nbsp;&nbsp;&nbsp;&nbsp;随后，秦雅和七王子便离开了凤来宫。<br />&nbsp;&nbsp;&nbsp;&nbsp;此时此刻，秦妃心里有着无尽的愁苦，这么多年来，她无依无靠，而她所谓的背山的娘家，让选择这个时候，舍弃自己，投靠王后，这让秦妃很是心寒。<br />&nbsp;&nbsp;&nbsp;&nbsp;“娘亲，父王刚才送来的是什么品级丹药，我感到身体充满了力量。”<br />&nbsp;&nbsp;&nbsp;&nbsp;萧凡依旧坐在床榻上，敏锐的发现秦妃面容发生了细微的变化，旋即望着秦妃，缓缓的问道。<br />&nbsp;&nbsp;&nbsp;&nbsp;显然，这是萧凡故意这样，转移秦妃的注意力，使她从愁苦的情绪中走出来。<br />&nbsp;&nbsp;&nbsp;&nbsp;“凡儿，那是你父王御用‘九品龙血丹’。”<br />&nbsp;&nbsp;&nbsp;&nbsp;秦妃微微苦笑，似乎略有深意，道。<br />&nbsp;&nbsp;&nbsp;&nbsp;坐在冰冷的床榻上面，萧凡依然能够感受到潜在的危机，这种危机不仅仅是来自秦家以及秦雅，而且是大周皇室及大家族的压力。<br />&nbsp;&nbsp;&nbsp;&nbsp;秦雅此时选择退婚，无疑是将他推到风口浪尖上，不过他此时，却很平静，毕竟现在的萧凡已经不是原来那个萧凡，而是昔日的时空武帝，什么大风大浪没有历经过，岂会怕她带来的危机。<br />&nbsp;&nbsp;&nbsp;&nbsp;但是一口恶气，还是要出的，不然太对不起这具身体了。<br />&nbsp;&nbsp;&nbsp;&nbsp;“这具肉体太弱小了，而且都没有开启“气运”，难怪秦家那姑娘不喜欢原来那个萧凡，而且，我现在的肉体也不强大，若是踏上复仇，寻妻之旅，是自找死路，只有开启气运，才能获得举世无双的领悟能力，才能开脉修炼武道，才能报仇，所谓“君子报仇，十年未晚”，在强者为尊的世界，实力可以说明一切，既使王室成员也不例外。”萧凡的心中暗想。<br />&nbsp;&nbsp;&nbsp;&nbsp;“天生我材必有用”，萧凡可以轮回重生在这一具身体里面，那就说明他命不该绝，上天给了他一个报仇的机会，无论是与剑帝再斗，还是在大周郡国站住脚，都必须实力过硬，才能成为真正的强者。<br />&nbsp;&nbsp;&nbsp;&nbsp;今日所遭受的屈辱，他日必以十倍还回来，物竞天择，适者生存，只有真正的强者才能掌握自己的命运。<br />&nbsp;&nbsp;&nbsp;&nbsp;在苍玄大陆，想要掌握自己的命运，就必须开启“气运”，成为一名灵师，才能成为一位傲世苍生的强者，才能有资格指点江山。<br />&nbsp;&nbsp;&nbsp;&nbsp;所谓的“气运”，就是诸神护佑人族修炼武道的气运。<br />&nbsp;&nbsp;&nbsp;&nbsp;没有开启“气运”的人，就不能开脉，更加不能修炼通天彻地之法。<br />&nbsp;&nbsp;&nbsp;&nbsp;为何四王子可以抢走十四王子的未婚妻，原因是他可以开启“玄蟒气运”，可以开脉修武，现在已经是炼体境四重的境界，而十四王子还不能开启“圣龙气运”，不能开脉修武，更别说突破炼体境了。<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br /></div>\n" +
//                "\t\t\t<div style=\"text-align: center\"><script>read3();</script></div>\n" +
//                "\t\t\t<div class=\"bottem2\">\n" +
//                "\t\t\t\t<a href=\"/id61823/534991.html\">上一章</a> &larr; <a href=\"/id61823/\">章节目录</a> &rarr; <a href=\"/id61823/534993.html\">下一章</a> <a rel=\"nofollow\" href=\"javascript:;\" onclick=\"addBookMark(534992,61823);\">加入书签</a>\n" +
//                "\t\t\t</div>\n" +
//                "\t\t\t<div style=\"text-align: center\"><script>read4();</script></div>\n" +
//                "\t\t</div>\n" +
//                "\t</div>\n" +
//                "\t<div class=\"footer\">\n" +
//                "\t\t<div class=\"footer_link\">新书推荐：<a href=\"/id53148/\">玄界之门</a>\n" +
//                "<a href=\"/id12339/\">修罗武神</a>\n" +
//                "<a href=\"/id24226/\">超级兵王</a>\n" +
//                "<a href=\"/id17113/\">极品学生</a>\n" +
//                "<a href=\"/id44755/\">三国之召唤猛将</a>\n" +
//                "<a href=\"/id2954/\">大圣传</a>\n" +
//                "<a href=\"/id28111/\">极品透视</a>\n" +
//                "<a href=\"/id17523/\">修仙狂徒</a>\n" +
//                "<a href=\"/id52609/\">系统之乡土懒人</a>\n" +
//                "<a href=\"/id25300/\">功夫圣医</a></div>\n" +
//                "\t\t<div class=\"footer_cont\">\n" +
//                "\t\t\t<script>footer();dl();tan();</script>\n" +
//                "\t\t</div>\n" +
//                "\t</div>\n" +
//                "</div>\n" +
//                "<script language=\"javascript\">\n" +
//                "var lastread=new LastRead();\n" +
//                "document.onkeydown=keypage;\n" +
//                "var prevpage=\"/id61823/534991.html\"\n" +
//                "var nextpage=\"/id61823/534993.html\"\n" +
//                "var index_page = \"/id61823/\"\n" +
//                "function keypage() {\n" +
//                "\tif (event.keyCode==37) location=prevpage\n" +
//                "\tif (event.keyCode==39) location=nextpage\n" +
//                "\tif (event.keyCode == 13) document.location=index_page\n" +
//                "}\n" +
//                "lastread.set('61823', '534992', '超凡战尊', '第一章 少年萧凡', '百里庆之', '玄幻小说');\n" +
//                "function postErrorChapter(){\n" +
//                "\tpostError(61823,534992);\n" +
//                "}\n" +
//                "</script>\n" +
//                "</body>\n" +
//                "</html>")
//        val content = doc.getElementById("content")
//        tv_content.setText(content.text())

//学习网址：https://blog.csdn.net/roy_70/article/details/72453362
//报错：java.lang.RuntimeException: Unable to start activity ComponentInfo{com.smart.novel/com.smart.novel.ui.ACT_Read}: android.os.NetworkOnMainThreadException
//        val doc = Jsoup.connect("https://www.zhuishu.tw/id61823/534992.html").get()


/*：(2)
  报错 :org.jsoup.HttpStatusException: HTTP error fetching URL. Status=405, URL=https://www.zhuishu.tw/id61823/534992.html
 爬取网站的时候 conn = Jsoup.connect(url).timeout(5000).get();直接用get方法，有些网站可以正常爬取。
 但是有些网站报403错误，403是一种在网站访问的过程中，常见的错误提示。表示资源不可用，服务器理解客户对的请求，但是拒绝处理它，通常由服务器上文件或者目录的权限设置导致的web访问错误。

 解决方法无非就是从这几个角度：useragent,referer,token,cokkie
 所以我们给连接添加模拟浏览器的header：*/


//(3)【Android】快速切换到主线程更新UI的几种方法 https://blog.csdn.net/da_caoyuan/article/details/52931007