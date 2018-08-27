package com.smart.novel.util

import android.os.CountDownTimer
import android.util.Log

/**
 * Created by zj on 2017/7/3.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
class CountDownUtils
/**
 * @param millisInFuture    The number of millis in the future from the call
 * to [.start] until the countdown is done and [.onFinish]
 * is called.
 * @param countDownInterval The interval along the way to receive
 * [.onTick] callbacks.
 */
(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        Log.e("TAG", "millisUntilFinished=" + millisUntilFinished)
    }

    override fun onFinish() {
        Log.e("TAG", "onFinish")
    }
}
