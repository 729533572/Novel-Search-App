package com.smart.novel.util;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by zj on 2017/7/3.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class CountDownUtils extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.e("TAG", "millisUntilFinished="+millisUntilFinished);
    }

    @Override
    public void onFinish() {
        Log.e("TAG", "onFinish");
    }
}
