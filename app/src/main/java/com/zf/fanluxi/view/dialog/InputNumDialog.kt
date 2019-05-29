package com.zf.fanluxi.view.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import kotlinx.android.synthetic.main.dialog_input_num.view.*

/**
 * 修改商品数量
 */
class InputNumDialog : DialogFragment() {

    var onNumListener: ((Int) -> Unit)? = null

    companion object {

        var mNum: Int = 0
        fun showDialog(fragmentManager: FragmentManager, num: Int): InputNumDialog {
            val receiveDialog = InputNumDialog()

            mNum = num

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
        sp?.width = DensityUtil.dp2px(300f)
        sp?.height = LinearLayout.LayoutParams.WRAP_CONTENT
        sp?.dimAmount = 0.3f
        window?.attributes = sp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_input_num, container, false)
        view.apply {


            numberInput.setText(mNum.toString())

            /** 让光标移到后面 */
            numberInput.setSelection(numberInput.length())

            decrease.isSelected = numberInput.text.toString().toInt() < 2

            //增加
            increase.setOnClickListener {
                val num = if (numberInput.text.isEmpty()) 0 else numberInput.text.toString().toInt()
                numberInput.setText((num + 1).toString())
                /** 让光标移到后面 */
                numberInput.setSelection(numberInput.length())
            }

            //减少
            decrease.setOnClickListener {
                if (numberInput.text.isNotEmpty() && numberInput.text.toString().toInt() > 1) {
                    val num = numberInput.text.toString().toInt()
                    numberInput.setText((num - 1).toString())
                    /** 让光标移到后面 */
                    numberInput.setSelection(numberInput.length())
                }
            }

            numberInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isEmpty()) {
                        decrease.isSelected = true
                    } else decrease.isSelected = s.toString().toInt() < 2
                }

                override fun afterTextChanged(s: Editable) {
                }
            })

            cancel.setOnClickListener {
                dismiss()
            }

            confirm.setOnClickListener {
                if (numberInput.text.isNotEmpty() && numberInput.text.toString().toInt() > 0) {
                    onNumListener?.invoke(numberInput.text.toString().toInt())
                    dismiss()
                }
            }
        }

        return view
    }

}
