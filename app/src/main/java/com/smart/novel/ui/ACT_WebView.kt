package com.smart.novel.ui

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.*
import com.smart.novel.R
import com.smart.novel.base.ACT_Base
import com.smart.novel.util.PageDataConstants
import kotlinx.android.synthetic.main.act_web_view.*

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description: webview
 */
class ACT_WebView : ACT_Base() {


//    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    var webUrl = ""
    override fun getBundleExtras(extras: Bundle?) {
        webUrl = extras!!.getString(PageDataConstants.WEB_URL, "")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_web_view
    }

    override fun initViewsAndEvents() {
//        ivLeft.visibility = View.VISIBLE
//        setHeaderTitle("小说原网页")
        initWebView()
    }

    private fun initWebView() {
        webView.loadUrl(webUrl)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
        webView.getSettings().setDomStorageEnabled(true)
        webView.setWebChromeClient(WebChromeClient())
        //设置加载网页时的进度
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                if (newProgress == 100) {
//                    pb_loading.setVisibility(View.GONE)//加载完网页进度条消失
//                } else {
//                    pb_loading.setVisibility(View.VISIBLE)//开始加载网页时显示进度条
//                    pb_loading.setProgress(newProgress)//设置进度值
//                }
            }
        })
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            // (1)解决android 6.0 webview加载https出现空白页问题
            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                //                super.onReceivedSslError(view, handler, error);
                handler.proceed() // 接受所有网站的证书
                //                if (error.getPrimaryError() == SslError.SSL_INVALID) {
                //                    handler.proceed();
                //                } else {
                //                    handler.cancel();
                //                }
            }
        })
        /**
         *  (1)解决android 6.0 webview加载https出现空白页问题
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
        }
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}