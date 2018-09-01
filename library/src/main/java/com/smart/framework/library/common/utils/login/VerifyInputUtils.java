package com.smart.framework.library.common.utils.login;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.common.utils.ToastUtil;


/**
 * Created by JoJo on 2017/10/24.
 * wechat:18510829974
 * description: 用户输入验证的工具类
 */

public class VerifyInputUtils {
    /**
     * 校验手机号和验证码
     *
     * @return
     */
    public static boolean doValidatePhoneAndCode(Context mContext, EditText etPhoneNumber,EditText etVerifyCode ) {
        Validator validator = new Validator();

        validator = new Validator();
        TextView[] widgets = new TextView[]{etPhoneNumber};
        validator.addRules(ValidateRule.IS_NOT_EMPTY);
        // 验证这些控件是不是都有内容了
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(BaseApplication.getInstance(), w.getHint().toString(), false);
                w.requestFocus();
                return false;
            }
        }

        validator = new Validator();
        validator.addRules(ValidateRule.IS_MOBILE_NUMBER);
        // 验证这些控件是不是都有内容了
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(mContext, ro.getErrorMessage(), false);
                w.requestFocus();
                return false;
            }
        }

        // 验证手机号为11位
        for (TextView w : widgets) {

            if (w.getText().toString().length() != 11) {
                CommonUtils.makeEventToast(BaseApplication.getInstance(), "请输入正确的手机号码", false);
                w.requestFocus();
                return false;
            }
        }

        widgets = new TextView[]{etPhoneNumber, etVerifyCode};
        validator = new Validator();
        validator.addRules(ValidateRule.IS_NOT_EMPTY);
        // 验证这些控件是不是都有内容了
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(BaseApplication.getInstance(), w.getHint().toString(), false);
                w.requestFocus();
                return false;
            }
        }
        widgets = new TextView[]{etVerifyCode};
        validator = new Validator();
        validator.addRules(ValidateRule.IS_CORRECT_VERIFYCODE);
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(BaseApplication.getInstance(), ro.getErrorMessage(), false);
                w.requestFocus();
                return false;
            }
        }
        return true;
    }

    /**
     * 验证手机号
     *
     * @return
     */
    public static boolean doValidatePhone(Context mContext,EditText etPhoneNumber) {
        Validator validator;

        validator = new Validator();
        TextView[] widgets = new TextView[]{etPhoneNumber};
        validator.addRules(ValidateRule.IS_NOT_EMPTY);
        // 验证这些控件是不是都有内容了
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                ToastUtil.makeText(BaseApplication.getInstance(), w.getHint().toString(), false);
                w.requestFocus();
                return false;
            }
        }
        validator = new Validator();
        validator.addRules(ValidateRule.IS_MOBILE_NUMBER);
        // 验证这些控件是不是都有内容了
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                ToastUtil.makeText(BaseApplication.getInstance(), ro.getErrorMessage(), false);
                w.requestFocus();
                return false;
            }
        }
        return true;
    }

}
