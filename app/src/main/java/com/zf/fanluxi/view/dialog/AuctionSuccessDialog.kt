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
import kotlinx.android.synthetic.main.dialog_auction_success.view.*

class AuctionSuccessDialog : DialogFragment() {

    interface OnItemClickListener {
        fun onItemClick()
    }

    private var mListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    companion object {

        fun showDialog(fragmentManager: FragmentManager): AuctionSuccessDialog {
            val receiveDialog = AuctionSuccessDialog()
            receiveDialog.show(fragmentManager, "")
            //点击空白处是否关闭dialog
            receiveDialog.isCancelable = true
            return receiveDialog
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val sp = window?.attributes
        sp?.width = DensityUtil.dp2px(300F)
        sp?.height = LinearLayout.LayoutParams.WRAP_CONTENT
        sp?.dimAmount = 0.3f
        window?.attributes = sp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_auction_success, container, false)
        view.confirm.setOnClickListener {
            mListener?.onItemClick()
            dismiss()
        }
        return view
    }

}
