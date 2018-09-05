package com.smart.novel.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.smart.novel.MyApplication
import com.smart.novel.R

/**
 * Created by JoJo on 2018/9/5.
 * wechat:18510829974
 * description:
 */
class DialogUtils {
    companion object {
        /**
         * 显示加入书架对话框
         *
         * @param bean
         */
        fun showConfirmDialog(mContext: Activity, title: String, msg: String, listenter: OnConfirmListener) {
            AlertDialog.Builder(mContext)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(MyApplication.context.getString(R.string.confirm)) { dialog, which ->
                        if (listenter!=null) {
                            listenter.onDialogConfirm()
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton(MyApplication.context.getString(R.string.cancel)) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }

    }

    interface OnConfirmListener {
        fun onDialogConfirm()
    }
}