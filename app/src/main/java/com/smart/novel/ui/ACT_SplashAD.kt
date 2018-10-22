package com.smart.novel.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.view.KeyEvent
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
    var canJump = false
    /**
     * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
     * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
     * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值（单位ms）
     */
    private val minSplashTimeWhenNoAD = 2000

    val handler = Handler(Looper.getMainLooper())

    /**
     * 记录拉取广告的时间
     */
    private var fetchSplashADTime: Long = 0
    private var splashAD: SplashAD? = null

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_splash_advertisement
    }

    override fun onADPresent() {
        Elog.e("TAG", "AD-onADPresent()")
    }

    /**
     * 广告加载的回调
     */
    override fun onADExposure() {
        Elog.e("TAG", "AD-onADExposure()")
    }

    override fun onADDismissed() {
        Elog.e("TAG", "AD-onADDismissed()")
        next()
    }

    override fun onADClicked() {
        Elog.e("TAG", "AD-onADClicked()")
        //如果点击了，会先打开广告链接，再执行下面的代码
        startActivity(Intent(this, ACT_Home::class.java))
        finish()
    }

    override fun onADTick(p0: Long) {
        Elog.e("TAG", "AD-onADTick()")
    }

    override fun onPause() {
        super.onPause()
        canJump = false
        Elog.e("TAG", "AD-onPause()")
    }

    override fun onResume() {
        super.onResume()
        if (canJump) {
            next()
        }
        canJump = true
        Elog.e("TAG", "AD-onResume()")
    }

    override fun onNoAD(p0: AdError?) {
        Elog.e("TAG", "AD-onNoAD()")
        /**
         * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
         * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
         * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值
         **/
        val alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime//从拉广告开始到onNoAD已经消耗了多少时间
        val shouldDelayMills = if (alreadyDelayMills > minSplashTimeWhenNoAD)
            0
        else
            minSplashTimeWhenNoAD - alreadyDelayMills//为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD
        // 计算出还需要延时多久
        handler.postDelayed({
            startActivity(Intent(this, ACT_Home::class.java))
            finish()
        }, shouldDelayMills)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun next() {
        if (canJump) {
            startActivity(Intent(this, ACT_Home::class.java))
            finish()
        } else {
            canJump = true
        }
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

    override fun getBundleExtras(extras: Bundle?) {
    }

    /** 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费  */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            true
        } else super.onKeyDown(keyCode, event)
    }


}