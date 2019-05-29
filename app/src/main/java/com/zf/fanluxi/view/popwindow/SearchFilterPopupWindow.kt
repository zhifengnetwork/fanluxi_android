package com.zf.fanluxi.view.popwindow

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.FilterPrice
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.pop_search_filter.view.*

/**
 * 搜索订单列表筛选
 */
abstract class SearchFilterPopupWindow(var context: Activity, layoutRes: Int, w: Int, h: Int,
                                       val chooseSel: String,
                                       val choosePrice: String,
                                       val filterPrice: List<FilterPrice>) {
    val contentView: View
    val popupWindow: PopupWindow
    private var isShowing = false

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }

    var onConfirmListener: ((String) -> Unit)? = null
    var onPriceListener: ((String) -> Unit)? = null

    private fun initView() {
        contentView.apply {
            priceView.visibility = if (filterPrice.isNotEmpty()) View.VISIBLE else View.GONE
            val priceList = ArrayList<String>()
            for (price in filterPrice) {
                priceList.add(price.value)
            }

            priceLayout.adapter = object : TagAdapter<String>(priceList) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val tv: TextView = LayoutInflater.from(context).inflate(
                            R.layout.layout_textview_style, hotLayout, false
                    ) as TextView
                    tv.text = t
                    return tv
                }
            }

            if (choosePrice.isNotEmpty()) {
                var mIndex: Int? = null
                if (filterPrice.isNotEmpty()) {
                    for (i in 0 until filterPrice.size) {
                        if (choosePrice == filterPrice[i].href) {
                            mIndex = i
                        }
                    }
                    if (mIndex != null) {
                        priceLayout.adapter.setSelectedList(setOf(mIndex))
                    }
                }
            }

            priceLayout.setOnSelectListener {
                if (it.toIntArray().isNotEmpty()) {
                    onPriceListener?.invoke(
                            filterPrice[it.toIntArray()[0]].href
                    )
                } else {
                    onPriceListener?.invoke("")
                }
            }


            val filterName = arrayOf("显示全部", "仅看包邮", "仅看有货", "促销商品")
            hotLayout.adapter = object : TagAdapter<String>(filterName) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val tv: TextView = LayoutInflater.from(context).inflate(
                            R.layout.layout_textview_style, hotLayout, false
                    ) as TextView
                    tv.text = t
                    return tv
                }
            }

            if (chooseSel.isNotEmpty()) {
                hotLayout.adapter.setSelectedList(setOf(when (chooseSel) {
                    "all" -> 0
                    "free_post" -> 1
                    "store_count" -> 2
                    "prom_type" -> 3
                    else -> 0
                }))
            }

            hotLayout.setOnSelectListener {
                if (it.toIntArray().isNotEmpty()) {
                    onConfirmListener?.invoke(
                            when (it.toIntArray()[0]) {
                                0 -> "all"
                                1 -> "free_post"
                                2 -> "store_count"
                                3 -> "prom_type"
                                else -> ""
                            }
                    )
                } else {
                    onConfirmListener?.invoke("")
                }
            }

            confirm.setOnClickListener {
                onDismiss()
            }

            back.setOnClickListener { onDismiss() }

        }
    }

    private fun initWindow() {
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        /** 设置出入动画  */
        popupWindow.animationStyle = R.style.pop_translate_around
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