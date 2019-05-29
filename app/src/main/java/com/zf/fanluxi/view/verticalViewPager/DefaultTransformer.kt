package com.zf.fanluxi.view.verticalViewPager


import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * introduce：这里写介绍  控件跳转动画
 * createBy：sunwentao
 * email：wentao.sun@yintech.cn
 * time: 9/10/18
 */
class DefaultTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        var alpha = 0f
        if (position in 0.0..1.0) {
            alpha = 1 - position
        } else if (-1 < position && position < 0) {
            alpha = position + 1
        }
        view.alpha = alpha
        view.translationX = view.width * -position
        val yPosition = position * view.height
        view.translationY = yPosition
    }
}
