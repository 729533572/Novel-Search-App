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
class ACT_Announcement : ACT_Base() {
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_announcement
    }

    override fun initViewsAndEvents() {
        tvTile.setText("声明条款")

        ivLeft.visibility = View.VISIBLE

        tv_txt.setText(Html.fromHtml(content))
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    val content = "<html>\n" +
            "<body>\n" +
            "\n" +
            "<p>\n" +
            "第一条 本免责及隐私保护声明（以下简称“隐私声明”或“本声明”）仅适用于小说搜索神器软件（以下简称“本软件”）。在使用小说搜索神器软件（以下简称本软件）前，请您务必仔细阅读并透彻理解本声明。在您阅读本声明后若不同意此声明中的任何条款，或对本声明存在质疑，请立刻停止使用我们的软件，但如果您使用本软件，您的使用行为将被视为对本声明全部内容的认可。\n" +
            "\n" +
            "</p><p>\n" +
            "第二条 本软件是一个仅提供搜索服务的搜索引擎。本软件自带的搜索引擎系统根据用户发送的请求，以非人工检索的方式搜索网络上的内容列出索引以方便用户查看网络上的文档图片等信息。查找结果将完全根据您输入的关键词返回位于其他网站上的相应内容的链接。本软件自身不存储，控制编辑或修改被链接的第三方网页上的信息内容或其表现形式。\n" +
            "\n" +
            "</p><p>\n" +
            "第三条 鉴于本软件以非人工检索方式、根据您输入的关键词返回位于其他网站上的相应内容的链接，因搜索结果产生风险，本软件不承担任何法律责任。\n" +
            "\n" +
            "</p><p>\n" +
            "第四条 在您使用本软件的搜索功能时，所得出的所有搜索结果完全由系统根据您的指令全自动生成，没有任何人工干预，内容不代表小说搜索神器赞成被搜索网站的内容或立场。\n" +
            "\n" +
            "</p><p>\n" +
            "第五条 本软件自带的净化转码功能可以将目标网站转码净化，屏蔽当中的弹窗广告等内容，以方便用户更舒适的阅览。该功能完全由用户客户端自动完成。净化目标网站是您自身需求自愿选择使用并完全知情且理解情况下的个人行为，因使用该功能所存在的风险和一切后果将完全由您自己承担，本软件无需承担任何责任。如果您不需要此功能，可通过搜索结果链接跳转到目标网站进行浏览。\n" +
            "\n" +
            "</p><p>\n" +
            "第六条 您应该对使用本软件的结果自行承担风险，我们不做任何形式的保证，并特别对以下事项进行声明：\n" +
            "<br>\n" +
            "（1）所有索引到的链接等信息仅供您个人浏览或欣赏，不得用于商业或者其他用途，否则，一切后果均由您自己承担，我们对此不承担任何法律责任。\n" +
            "<br>\n" +
            "（2）在使用搜索功能时输入的关键字将不被认为是您的个人隐私资料。\n" +
            "<br>\n" +
            "（3）作为搜索引擎，我们提供的是信息链接、来源网站的快速检索功能，不对链接所包含内容的安全性、正确性负责，跳转到目标链接后产生的任务后果，我们均不承担任何责任。\n" +
            "<br>\n" +
            "（4）我们尊重并保护所有使用本软件用户的个人隐私权，您通过本软件注册的用户名、电子邮件地址等个人资料，非经您亲自许可或根据相关法律、法规的强制性规定，我们不会主动地泄露给第三方。\n" +
            "<br>\n" +
            "（5）您应当对搜索到的所有信息资料的可靠性作出判断和评估，所有风险和责任由您独自承担。\n" +
            "<br>\n" +
            "（6）任何网站如果不想被本软件收录（即不被搜索到），应该及时向本软件反映，或者在其网站页面中根据拒绝蜘蛛协议加注拒绝收录的标记，否则，本软件将依照惯例视其为可收录网站。\n" +
            "<br>\n" +
            "（7） 任何单位或个人认为通过本软件链接到的第三方网页内容可能涉嫌侵犯其信息网络传播权，应该及时向本软件提出书面权利通知，并提供身份证明、权属证明及详细侵权情况证明，我们在收到上述法律文件后，将会依法尽快断开相关链接内容。\n" +
            "<br>\n" +
            "\n" +
            "</p><p>\n" +
            "第七条 权利人保护指引\n" +
            "<br>\n" +
            "（1）小说搜索神器是一个仅提供搜索服务的搜索引擎。小说搜索神器自带的搜索引擎系统根据用户发送的请求，以非人工检索的方式搜索网络上的内容列出索引以方便用户查看网络上的文档图片等信息。查找结果将完全根据您输入的关键词返回位于其他网站上的相应内容的链接。小说搜索神器自身不存储，控制编辑或修改被链接的第三方网页上的信息内容或其表现形式。\n" +
            "<br>\n" +
            "（2）作为权利人，当您发现小说搜索神器生成的链接所指向的第三方网页的内容侵犯了您的合法权益时，您应当首先向我们发送“权利通知”，我们将根据中国现行法律法规和政府规范性文件立即采取措施移除相关内容或屏蔽相关链接。\n" +
            "<br>\n" +
            "（3）请您务必以书面方式向我们提交权利通知（应含相应证明材料）。我们将在接到通知并审核无误之后合理的时间内依法采取措施移除或屏蔽相关链接。并且，我们可能会依法通知受此措施影响的网站或内容的拥有者或管理员，以便他们依法提出抗辩通知。\n" +
            "<br>\n" +
            "（4）请将电子版权利通知，以及加盖印章的纸面文件扫描或拍照发送至我们的联系邮箱：yulekeji@qingqingshuwu.club\n" +
            "<br>\n" +
            "</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n" +
            "\n"
}