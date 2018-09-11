package com.smart.novel.ui.mine

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.smart.novel.R
import com.smart.novel.base.ACT_Base
import kotlinx.android.synthetic.main.act_announcement.*

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:
 */
class ACT_AboutUs : ACT_Base() {
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_about_us
    }

    override fun initViewsAndEvents() {
        tvTile.setText("关于我们")

        ivLeft.visibility = View.VISIBLE
        tv_txt.setText(Html.fromHtml(content))
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
    val content = "<html>\n" +
            "<body>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "小说搜索神器是一个仅提供搜索服务的搜索引擎，根据用户发送的请求，以非人工检索的方式搜索网络上的内容列出索引，以方便用户查看网络上的文档图片等信息。查找结果将完全根据您输入的关键词返回位于其他网站上的相应内容的链接。本软件自身不存储，控制编辑或修改被链接的第三方网页上的信息内容或其表现形式。\n" +
            "</p>\n" +
            "<p>\n" +
            "如您有意见或建议，欢迎发送至我们的联系邮箱：yulekeji@qingqingshuwu.club\n" +
            "</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>"
}