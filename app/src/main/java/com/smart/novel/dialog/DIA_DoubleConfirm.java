package com.smart.novel.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.framework.library.R;
import com.smart.framework.library.common.utils.StringUtil;

/**
 * 作者：liushuofei on 2016/12/9 09:06
 * 邮箱：lsf@yonyou.com
 */
public class DIA_DoubleConfirm extends Dialog {

    public DIA_DoubleConfirm(Activity context) {
        super(context);
    }

    public DIA_DoubleConfirm(Activity context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Activity context; // 上下文对象
        private String title; // 对话框标题
        private String confirm_btnText; // 按钮名称“确定”
        private String cancel_btnText; // 按钮名称“取消”

        /* 按钮监听事件 */
        private OnClickListener confirm_btnClickListener;
        private OnClickListener cancel_btnClickListener;

        public Builder(Activity context) {
            this.context = context;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(int confirm_btnText, OnClickListener listener) {
            this.confirm_btnText = (String) context.getText(confirm_btnText);
            this.confirm_btnClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String confirm_btnText, OnClickListener listener) {
            this.confirm_btnText = confirm_btnText;
            this.confirm_btnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int cancel_btnText, OnClickListener listener) {
            this.cancel_btnText = (String) context.getText(cancel_btnText);
            this.cancel_btnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String cancel_btnText, OnClickListener listener) {
            this.cancel_btnText = cancel_btnText;
            this.cancel_btnClickListener = listener;
            return this;
        }

        public DIA_DoubleConfirm create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final DIA_DoubleConfirm dialog = new DIA_DoubleConfirm(context, R.style.style_custom_dialog);
            View layout = inflater.inflate(R.layout.dialog_double_confirm, null);
            // 获取屏幕分辨率
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int widthPixels = metrics.widthPixels;
            dialog.addContentView(layout, new ViewGroup.LayoutParams(widthPixels / 10 * 6, ViewGroup.LayoutParams.MATCH_PARENT));

            // set the dialog title
            ((TextView) layout.findViewById(R.id.tv_double_confirm_title)).setText(title);
//            ((TextView) layout.findViewById(R.id.tv_double_confirm_title)).getPaint().setFakeBoldText(true);

            // set the cancel button
            if (cancel_btnText != null) {
                ((TextView) layout.findViewById(R.id.tv_double_confirm_left)).setText(cancel_btnText);
                if (cancel_btnClickListener != null) {
                    ((RelativeLayout) layout.findViewById(R.id.rl_double_confirm_left)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            cancel_btnClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            }

            // set the confirm button
            if (confirm_btnText != null) {
                ((TextView) layout.findViewById(R.id.tv_double_confirm_right)).setText(confirm_btnText);
                if (confirm_btnClickListener != null) {
                    ((RelativeLayout) layout.findViewById(R.id.rl_double_confirm_right)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            confirm_btnClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }

            // set the content message
            dialog.setContentView(layout);
//            dialog.getWindow().setWindowAnimations(R.style.style_custom_dialog);
            return dialog;
        }

    }

    public static void showDialog(Activity context, String title, String msg, final OnClickListener onClickListener) {
        DIA_DoubleConfirm.Builder assignBuilder = new DIA_DoubleConfirm.Builder(context);
        assignBuilder.setTitle(StringUtil.getString(title));
        assignBuilder.setPositiveButton(context.getString(R.string.accept), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != onClickListener) {
                    onClickListener.onClick(dialog, which);
                }
            }
        });
        assignBuilder.setNegativeButton(context.getString(R.string.reject), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        assignBuilder.create().show();
    }

    public static void showDialog(Activity context, String title, final OnClickListener onClickListener) {
        DIA_DoubleConfirm.Builder assignBuilder = new DIA_DoubleConfirm.Builder(context);
        assignBuilder.setTitle(StringUtil.getString(title));
        assignBuilder.setPositiveButton(context.getString(R.string.confirm), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != onClickListener) {
                    onClickListener.onClick(dialog, which);
                }
            }
        });
        assignBuilder.setNegativeButton(context.getString(R.string.cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        assignBuilder.create().show();
    }

    /**
     * @param context
     * @param title
     * @param rightText
     * @param onRightClickListener
     */
    public static void showDialog(Activity context, String title, String rightText, final View.OnClickListener onRightClickListener) {
        DIA_DoubleConfirm.Builder assignBuilder = new DIA_DoubleConfirm.Builder(context);
        assignBuilder.setTitle(StringUtil.getString(title));
        assignBuilder.setPositiveButton(rightText, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != onRightClickListener) {
                    onRightClickListener.onClick(null);
                }
                dialog.dismiss();
            }
        });
        assignBuilder.setNegativeButton(context.getString(R.string.cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        assignBuilder.create().show();
    }
}
