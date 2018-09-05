package com.smart.novel.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.smart.framework.library.common.utils.StringUtil;
import com.smart.novel.R;

import butterknife.BindView;


/**
 * Created by JoJo on 2017/10/25.
 * wechat:18510829974
 * description:只有一个按钮的提示框
 */
public class DIA_Alert extends DIA_Base implements DialogInterface.OnKeyListener {

    @BindView(R.id.tv_dia_title)
    TextView tvTitle;
    @BindView(R.id.tv__dia_confim)
    TextView tvConfim;

    public DIA_Alert(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_alert;
    }

    private String title; // 对话框标题
    private String confirm_btnText; // 按钮名称“确定”

    /* 按钮监听事件 */
    private DialogInterface.OnClickListener confirm_btnClickListener;

    private DialogInterface.OnDismissListener onDismissListener;

    public void setTitle(int title) {
        this.title = (String) mContext.getText(title);
        tvTitle.setText(this.title);
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(this.title);
    }

    public void setPositiveButton(int confirm_btnText, DialogInterface.OnClickListener listener) {
        this.confirm_btnText = (String) mContext.getText(confirm_btnText);
        this.confirm_btnClickListener = listener;
        tvConfim.setText(this.confirm_btnText);
        tvConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_btnClickListener.onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
            }
        });
    }

    public void setPositiveButton(String confirm_btnText, DialogInterface.OnClickListener listener) {
        this.confirm_btnText = confirm_btnText;
        this.confirm_btnClickListener = listener;
        tvConfim.setText(this.confirm_btnText);
        tvConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_btnClickListener.onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
            }
        });
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (null != onDismissListener) {
                onDismissListener.onDismiss(dialogInterface);
            }
            dialogInterface.dismiss();
        }
        return false;
    }

    public static void showDialog(Context context, String title, String msg, final DialogInterface.OnClickListener onClickListener,boolean isCancelable) {
        DIA_Alert assignBuilder = new DIA_Alert(context);
        assignBuilder.setCancelable(isCancelable);
        assignBuilder.setTitle(StringUtil.getString(title));
        assignBuilder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != onClickListener) {
                    onClickListener.onClick(dialog, which);
                }
            }
        });
        assignBuilder.getDialog().show();
    }

    /**
     * 带对话框监听
     * @param context
     * @param title
     * @param msg
     * @param onClickListener
     * @param onDismissListener
     */
    public static void showDialog(Context context, String title, String msg, final DialogInterface.OnClickListener onClickListener, final  DialogInterface.OnDismissListener onDismissListener) {
        DIA_Alert assignBuilder = new DIA_Alert(context);
        assignBuilder.setTitle(StringUtil.getString(title));
        assignBuilder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != onClickListener) {
                    onClickListener.onClick(dialog, which);
                }
            }
        });
        assignBuilder.getDialog().setCancelable(false);
        assignBuilder.getDialog().setCanceledOnTouchOutside(false);
        assignBuilder.setOnDismissListener(onDismissListener);
        assignBuilder.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (null != onDismissListener) {
                    onDismissListener.onDismiss(dialogInterface);
                }
            }
        });
        assignBuilder.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (null != onDismissListener) {
                    onDismissListener.onDismiss(dialogInterface);
                }
            }
        });
        assignBuilder.getDialog().show();
    }
//    /**
//     * 提示信息
//     *
//     * @param mContext
//     * @param message  错误提示信息
//     */
//    public void alertUser(Context mContext, String message) {
//        if (mContext == null) {
//            return;
//        }
//        DIA_Alert.showDialog(mContext, StringUtil.getString(message), getString(R.string.i_know), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                mDIAAlert = dialogInterface;
//            }
//
//        }, true);
//    }
}
