package com.zf.fanluxi.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    /** 重写方法 禁止左右滑动 */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        var mHheightMeasureSpec = heightMeasureSpec
//
//        var height = 0
//        for (i in 0 until childCount) {
//            val child = getChildAt(i)
//            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
//            val h = child.measuredHeight
//            if (h > height)
//                height = h
//        }
//
//        mHheightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
//
//        super.onMeasure(widthMeasureSpec, mHheightMeasureSpec)
//    }
}