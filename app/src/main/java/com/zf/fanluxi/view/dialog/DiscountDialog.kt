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
import com.zf.fanluxi.mvp.bean.DiscountBean
import kotlinx.android.synthetic.main.dialog_discount.view.*

/**
 * 优惠券码
 */
class DiscountDialog : DialogFragment() {


    companion object {

        private var discountBean: DiscountBean? = null

        fun showDialog(fragmentManager: FragmentManager, bean: DiscountBean): DiscountDialog {

            val receiveDialog = DiscountDialog()

            discountBean = bean

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
        sp?.width = DensityUtil.dp2px(250f)
        sp?.height = DensityUtil.dp2px(90f)
        sp?.dimAmount = 0.3f
        window?.attributes = sp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_discount, container, false)
        view.apply {
            codeTxt.text = "券码:${discountBean?.coupon_code}"
        }
        return view
    }

}
