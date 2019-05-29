package com.zf.fanluxi.view.loadingDialog

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zf.fanluxi.R
import kotlinx.android.synthetic.main.loading_dialog_view.view.*

class LoadingDialogKt(context: Context, msg: String, val isBackDismiss: Boolean?) {

    internal var mLoadingView: LVCircularRing
    internal var mLoadingDialog: Dialog? = null


    init {
        // 首先得到整个View
        val view = LayoutInflater.from(context).inflate(
            R.layout.loading_dialog_view, null
        )
        // 获取整个布局
//        val layout = view.findViewById(R.id.dialog_view)
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.lv_circularring)
        // 页面中显示文本
//        val loadingText = R.id.loading_text
        // 显示文本
        view.loading_text.setText(msg)
        // 创建自定义样式的Dialog
        if (mLoadingDialog == null) {
            mLoadingDialog = Dialog(context, R.style.loading_dialog)
            // 设置返回键是否有效,并且点击消失
            mLoadingDialog!!.setCancelable(false)
            mLoadingDialog!!.setOnKeyListener { dialog, keyCode, event ->
                /**
                 * true,点击返回按钮可以关闭dialog
                 * false,点击返回键不能关闭dialog
                 * BaseActivity设置默认是true
                 */
                if (isBackDismiss == true) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mLoadingDialog != null) {
                            mLoadingView.stopAnim()
                            mLoadingDialog!!.dismiss()
                            mLoadingDialog = null
                        }
                    }
                }
                true
            }
            mLoadingDialog!!.setContentView(
                view.dialog_view, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
        }
    }


    fun show() {
        if (mLoadingDialog != null) {
            mLoadingDialog!!.show()
            mLoadingView.startAnim()
        }
    }

    fun close() {
        if (mLoadingDialog != null) {
            mLoadingView.stopAnim()
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
    }

}
