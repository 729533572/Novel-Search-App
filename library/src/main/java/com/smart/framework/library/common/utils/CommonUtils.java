package com.smart.framework.library.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.R;


/**
 * 作者：jojo on 17/12/15 15:01
 * 邮箱：zjuan@yonyou.com
 */
public class CommonUtils {

    private static Toast toast;

    /**
     * 字符串为空判断
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null")
                || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 显示toast
     *
     * @param context
     * @param text
     * @param isLongToast
     */
    public static void makeEventToast(Context context, String text,
                                      boolean isLongToast) {

        if (null == context || null == text)
            return;

        View v = LayoutInflater.from(context).inflate(R.layout.toast_view,
                null);
        TextView textView = (TextView) v.findViewById(R.id.text);
        textView.setText(text);

        //每次创建Toast时先做一下判断
        //如果前面已经有Toast在显示，则只是更新toast内容，而不再创建，提升用户体验
        if (toast != null) {
            textView.setText(text);
        } else {
            toast = Toast.makeText(context, text, isLongToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }

        toast.setView(v);
        toast.show();
    }

    /**
     * 显示toast
     *
     * @param text
     */
    public static void makeShortToast(String text) {
        if (null == text)
            return;

        View v = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.toast_view,
                null);
        TextView textView = (TextView) v.findViewById(R.id.text);
        textView.setText(text);

        //每次创建Toast时先做一下判断
        //如果前面已经有Toast在显示，则只是更新toast内容，而不再创建，提升用户体验
        if (toast != null) {
            textView.setText(text);
        } else {
            toast = Toast.makeText(BaseApplication.getInstance(), text,Toast.LENGTH_SHORT);
        }
        toast.setView(v);
        toast.show();
    }

    /**
     * 显示toast
     *
     * @param context
     * @param id
     * @param isLongToast
     */
    public static void makeEventToast(Context context, int id,
                                      boolean isLongToast) {

        if (null == context)
            return;

        String text = context.getResources().getString(id);

        View v = LayoutInflater.from(context).inflate(R.layout.toast_view,
                null);
        TextView textView = (TextView) v.findViewById(R.id.text);
        textView.setText(text);

        if (toast != null) {
            textView.setText(text);
        } else {
            toast = Toast.makeText(context, text, isLongToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }

        toast.setView(v);
        toast.show();

    }

    /**
     * 调用拨打电话
     *
     * @param context 上下文
     * @param number  电话号码
     */
    public static void callPhone(Context context, String number) {
        if (null != number) {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(String.format("tel:%s", number)));
                context.startActivity(intent);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            CommonUtils.makeEventToast(context, "电话号码不存在！", false);
        }
    }


}
