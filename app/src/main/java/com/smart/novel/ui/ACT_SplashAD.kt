package com.smart.novel.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.ViewGroup
import com.qq.e.ads.splash.SplashAD
import com.qq.e.ads.splash.SplashADListener
import com.qq.e.comm.util.AdError
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.framework.library.common.utils.PermissionUtils
import com.smart.framework.library.common.utils.PermissionUtils.REQUEST_ADVERTISE_NEED
import com.smart.novel.R
import com.smart.novel.base.ACT_Base
import com.smart.novel.util.Constants
import kotlinx.android.synthetic.main.act_splash_advertisement.*

/**
 * Created by JoJo on 2018/9/28.
 * wechat:18510829974
 * description: 开屏广告页面
 */
class ACT_SplashAD : ACT_Base(), SplashADListener {
    /**
     * 广告加载的回调
     */
    override fun onADExposure() {
    }

    override fun onADDismissed() {
    }

    override fun onADPresent() {
    }

    override fun onNoAD(p0: AdError?) {
    }

    override fun onADClicked() {
    }

    override fun onADTick(p0: Long) {
    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_splash_advertisement
    }


    override fun initViewsAndEvents() {
        //首次进入首页，申请所需要的权限
        // api16开始添加的权限
        var needPermissons = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
        var hasPermisson = PermissionUtils.getInstance(mContext).checkPermission(needPermissons, REQUEST_ADVERTISE_NEED)
        if (hasPermisson) {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            fetchSplashAD(this, splash_container, Constants.APPID, getPosId(), this, 0)
        }

//        Handler().postDelayed({
//            readyGo(ACT_Home::class.java)
//        }, 2000)
    }

    /**
     * 6.0以上动态申请权限的回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Elog.e("eee", "requestCode " + (requestCode == REQUEST_ADVERTISE_NEED) + "－－－grantResults =" + (hasAllPermissionsGranted(grantResults)))
        if (requestCode == REQUEST_ADVERTISE_NEED && hasAllPermissionsGranted(grantResults)) {
            fetchSplashAD(this, splash_container, Constants.APPID, getPosId(), this, 0)
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            CommonUtils.makeEventToast(mContext, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", true)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + packageName)
            startActivity(intent)
            finish()
        }
    }

    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 记录拉取广告的时间
     */
    private var fetchSplashADTime: Long = 0
    private var splashAD: SplashAD? = null
    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity        展示广告的activity
     * @param adsplash_container     展示广告的大容器
     * @param skipsplash_container   自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId           应用ID
     * @param posId           广告位ID
     * @param adListener      广告状态监听器
     * @param fetchDelay      拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private fun fetchSplashAD(activity: Activity, adsplash_container: ViewGroup, appId: String, posId: String, adListener: SplashADListener, fetchDelay: Int) {
        fetchSplashADTime = System.currentTimeMillis()
//        splashAD = SplashAD(activity, adsplash_container, skipsplash_container, appId, posId, adListener, fetchDelay)
        splashAD = SplashAD(activity, adsplash_container, appId, posId, adListener, fetchDelay)
    }

    private fun getPosId(): String {
        val posId = intent.getStringExtra("pos_id")
        return if (TextUtils.isEmpty(posId)) Constants.SplashPosID else posId
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun toggleOverridePendingTransition(isToggle: Boolean): Boolean {
        return super.toggleOverridePendingTransition(false)
    }
}