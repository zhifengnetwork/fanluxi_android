package com.zf.fanluxi.view.dialog

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.smarttop.library.utils.Dev
import com.smarttop.library.widget.BottomDialog

class CustomAddressDialog(context: Context ?) :
    BottomDialog(context) {

    init {
        val window = window
        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = Dev.dp2px(context, 456f)
        window.attributes = params

        window.setGravity(Gravity.BOTTOM)
    }

}