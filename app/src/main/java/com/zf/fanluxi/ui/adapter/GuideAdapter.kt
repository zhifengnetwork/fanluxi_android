package com.zf.fanluxi.ui.adapter

import android.view.View
import android.widget.ImageView
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter


class GuideAdapter(private val imageViews: List<ImageView>) : PagerAdapter() {

    //获取当前要显示对象的数量
    override fun getCount(): Int {
        return imageViews.size
    }

    //判断是否用对象生成界面
    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    //从ViewGroup中移除当前对象（图片）
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(imageViews[position])
    }

    //当前要显示的对象（图片）
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(imageViews[position])
        return imageViews[position]
    }

}