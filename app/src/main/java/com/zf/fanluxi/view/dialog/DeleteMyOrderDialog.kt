package com.zf.fanluxi.view.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import kotlinx.android.synthetic.main.dialog_delete_order.view.*

/**
 * 删除我的订单
 */
class DeleteMyOrderDialog : DialogFragment() {


    companion object {

        var mNum: Int = 0
        fun showDialog(fragmentManager: FragmentManager, num: Int): DeleteMyOrderDialog {
            val receiveDialog = DeleteMyOrderDialog()

            mNum = num

            receiveDialog.show(fragmentManager, "")
            //点击空白处是否关闭dialog
            receiveDialog.isCancelable = false
            return receiveDialog
        }

    }

    var onNumListener: ((String) -> Unit)? = null


    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val sp = window?.attributes
        sp?.width = DensityUtil.dp2px(250f)
        sp?.height = LinearLayout.LayoutParams.WRAP_CONTENT
        sp?.dimAmount = 0.3f
        window?.attributes = sp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete_order, container, false)
        view.apply {
            cancel.setOnClickListener {
                dismiss()
            }

            confirm.setOnClickListener {
                dismiss()
            }
        }

        return view
    }

}
