package com.zf.fanluxi.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecDecoration(private val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //不是第一个的格子都设一个左边和底部的间距

//                outRect.bottom = space;
//        outRect.left = space
//        outRect.right = space
//        outRect.top = space
//        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
////        if (parent.getChildLayoutPosition(view) % 5 === 0) {
////            outRect.left = 0
////        }
//
//        //如果是一行两个，设置第二个的倍数的view左边距为0
//        if (parent.getChildLayoutPosition(view) % 2 != 0) {
//            outRect.left = 0
//        }


        val layoutManager = parent.layoutManager as GridLayoutManager
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanCount = layoutManager.spanCount
        val layoutPos = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        if (lp.spanSize != spanCount) {
            //左边距
            if (layoutPos % 2 == 1) {
                outRect.left = space / 2
                outRect.right = space / 2
            } else {
                outRect.left = space
                outRect.right = space / 2
            }
        }
        outRect.top = space

    }

}
