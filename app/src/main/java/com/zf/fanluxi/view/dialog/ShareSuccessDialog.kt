package com.zf.fanluxi.view.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import kotlinx.android.synthetic.main.dialog_share_success.view.*


/**
 * 分享成功
 */
class ShareSuccessDialog : DialogFragment() {

    interface OnItemClickListener {
        fun onItemClick()
    }

    private var mListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    companion object {

        fun showDialog(fragmentManager: FragmentManager): ShareSuccessDialog {
            val receiveDialog = ShareSuccessDialog()
            receiveDialog.show(fragmentManager, "")
            //点击空白处是否关闭dialog
            receiveDialog.isCancelable = false
            return receiveDialog
        }

    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val sp = window?.attributes
        sp?.width = DensityUtil.dp2px(250f)
        sp?.height = DensityUtil.dp2px(280f)
        sp?.dimAmount = 0.3f
        window?.attributes = sp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_share_success, container, false)
        view.confirm.setOnClickListener {
            dismiss()
        }

        return view
    }

}
