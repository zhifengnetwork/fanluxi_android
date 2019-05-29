package com.zf.fanluxi.view.popwindow

//import com.zf.mart.mvp.bean.SpecList
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.Toast
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.GroupDetailBean
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.pop_order_style.view.*

/**
 * 活动 商品规格
 */
abstract class ActiveSpecPopupWindow(
        var context: Activity,
        layoutRes: Int,
        w: Int,
        h: Int,
        private val bean: GroupDetailBean?,
        val promType: Int

) {
    val contentView: View
    val popupWindow: PopupWindow
    private var isShowing = false

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }

    //    var onNumberListener: ((num: Int) -> Unit)? = null
//    var onSpecListener: ((String) -> Unit)? = null
    var onConfirmListener: ((String, String) -> Unit)? = null

    private fun initView() {
        contentView.apply {

            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + bean?.info?.original_img, goodsIcon)

//            val adapter = CartSpecAdapter(context, specBean, specId)
            //设置规格
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = adapter

            confirm.setOnClickListener {
                var chooseSpec = ""
//                repeat(adapter.data.size) {
//                    chooseSpec = (chooseSpec + "_" + adapter.data[it].chooseId)
//                    if (adapter.data[it].chooseId.isEmpty()) {
//                        Toast.makeText(context, "请选择规格", Toast.LENGTH_SHORT).show()
//                        return@setOnClickListener
//                    }
//                }
                chooseSpec = chooseSpec.replaceFirst("_", "")
                onConfirmListener?.invoke(chooseSpec, number.text.toString())

                onDismiss()
            }

            /** 数量 */
            number.text = "1"

            goodsName.text = bean?.info?.goods_name
            //0单独买 6拼单
            goodsPrice.text = if (promType == 6) "¥ ${bean?.info?.group_price}" else "¥ ${bean?.info?.shop_price}"

            reduce.isSelected = number.text.toString().toInt() < 2


            reduce.setOnClickListener {
                if (number.text.toString().toInt() > 1) {
                    number.text = (number.text.toString().toInt() - 1).toString()
                }
            }

            increase.setOnClickListener {
                if (number.text.toString().toInt() < bean?.info?.buy_limit ?: Int.MAX_VALUE) {
                    number.text = (number.text.toString().toInt() + 1).toString()
                } else {
                    Toast.makeText(context, "限购数量:${bean?.info?.buy_limit}", Toast.LENGTH_SHORT).show()
                }
            }

            number.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    reduce.isSelected = s.toString().toInt() < 2
                }
            })

        }
    }

    private fun initWindow() {
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        /** 设置出入动画  */
        popupWindow.animationStyle = R.style.pop_translate
    }

    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        popupWindow.showAtLocation(parent, gravity, x, y)
        isShowing = true
        popupWindow.setOnDismissListener {
            //隐藏后显示背景为透明
            val lp = context.window.attributes
            lp.alpha = 1.0f
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            context.window.attributes = lp
        }

        //显示时候设置背景为灰色
        val lp = context.window.attributes
        lp.alpha = 0.7f
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        context.window.attributes = lp
    }

    fun onDismiss() {
        popupWindow.dismiss()
        isShowing = false

    }

}