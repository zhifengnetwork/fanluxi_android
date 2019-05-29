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
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.CartGoodsList
import com.zf.fanluxi.mvp.bean.SpecCorrect
import com.zf.fanluxi.ui.adapter.CartSpecAdapter
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.pop_order_style.view.*

/**
 * 购物车的选择款式
 */
abstract class CartSpecPopupWindow(
        var context: Activity,
        layoutRes: Int,
        w: Int,
        h: Int,
        private val bean: CartGoodsList,
        private val specBean: List<SpecCorrect>
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


    var onNumberListener: ((num: Int) -> Unit)? = null
    var onSpecListener: ((String) -> Unit)? = null

    fun update() {
        initView()
    }

    private fun initView() {
        contentView.apply {

            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + bean.original_img, goodsIcon)

            val adapter = CartSpecAdapter(context, specBean, bean.spec_key)
            //设置规格
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter


            //每点击一个规格就刷新
            adapter.onItemClickListener = {
                var chooseSpec = ""
                var num = 0
                repeat(adapter.data.size) {
                    chooseSpec = (chooseSpec + "_" + adapter.data[it].chooseId)
                    if (adapter.data[it].chooseId.isNotEmpty()) {
                        num += 1
                    }
                }
                if (num == adapter.data.size) {
                    chooseSpec = chooseSpec.replaceFirst("_", "")
                    onSpecListener?.invoke(chooseSpec)
                }
            }


            confirm.setOnClickListener {
                //                var chooseSpec = ""
//                repeat(adapter.data.size) {
//                    chooseSpec = (chooseSpec + "_" + adapter.data[it].chooseId)
//                    if (adapter.data[it].chooseId.isEmpty()) {
//                        Toast.makeText(context, "请选择规格", Toast.LENGTH_SHORT).show()
//                        return@setOnClickListener
//                    }
//                }
//                chooseSpec = chooseSpec.replaceFirst("_", "")
//                onSpecListener?.invoke(chooseSpec)
                onDismiss()
            }

            /** 数量 */
            number.text = bean.goods_num.toString()

            goodsName.text = bean.goods_name
            goodsPrice.text = "¥ ${bean.goods_price}"

            reduce.isSelected = number.text.toString().toInt() < 2
            reduce.setOnClickListener {
                if (number.text.toString().toInt() > 1) {
                    onNumberListener?.invoke(number.text.toString().toInt() - 1)
                    number.text = (number.text.toString().toInt() - 1).toString()
                }
            }

            increase.setOnClickListener {
                onNumberListener?.invoke(number.text.toString().toInt() + 1)
                number.text = (number.text.toString().toInt() + 1).toString()
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