package com.smart.framework.library.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;

import com.smart.framework.library.R;


/**
 * Created by JoJo on 2017/10/23.
 * wechat：18510829974
 * description：获取验证码倒计时类
 */
public class TimeCount extends CountDownTimer {

    private RadioButton btnGetSecurityCode;
    private Activity context;
    //倒计时间隔时间
    private static final int countDownInterval = 1000;
    //验证码倒计时总时间
    private static final int msgTime = 60 * 1000;

    public TimeCount(RadioButton btnGetSecurityCode,
                     Activity context) {
        super(msgTime, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.btnGetSecurityCode = btnGetSecurityCode;
        this.context = context;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        // 设置倒计时结束后显示的提示文字
        btnGetSecurityCode.setChecked(true);
        btnGetSecurityCode.setTextColor(ContextCompat.getColor(context, R.color.color_3AC270));
        btnGetSecurityCode.setText(context.getString(R.string.string_get_verify_code));
        // 设置倒计时时不能点击
        btnGetSecurityCode.setClickable(true);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        btnGetSecurityCode.setChecked(false);
        btnGetSecurityCode.setClickable(false);
        btnGetSecurityCode.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
        btnGetSecurityCode.setText("剩余" + millisUntilFinished / 1000 + "S");
    }
}
