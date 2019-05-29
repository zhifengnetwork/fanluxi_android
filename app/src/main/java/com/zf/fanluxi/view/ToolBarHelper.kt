package com.zf.fanluxi.view

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R

class ToolBarHelper {
    companion object {
        fun addMiddleTitle(context: Context, title: CharSequence?, toolBar: Toolbar) {
            val textView = TextView(context)
            textView.text = title

            val mTag = "centerTitle"
            val params = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER

            if (toolBar.findViewWithTag<TextView>(mTag) != null) {
                toolBar.removeView(toolBar.findViewWithTag<TextView>(mTag))
            }

            textView.tag = mTag
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText))
            textView.textSize = 16f
            toolBar.addView(textView, params)
        }
    }
}