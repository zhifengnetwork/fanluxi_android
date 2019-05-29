package com.zf.fanluxi.view.popwindow

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.yanzhenjie.album.Album
import com.zf.fanluxi.R
import kotlinx.android.synthetic.main.pop_avatar.view.*

/**
 * 头像变更选项
 */
abstract class AvatarPopupWindow(var context: Activity, layoutRes: Int, w: Int, h: Int) {
    val contentView: View
    val popupWindow: PopupWindow
    private var isShowing = false

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }

    var onCamera: ((String) -> Unit)? = null
    var onPhoto: ((String) -> Unit)? = null

    private fun initView() {
        contentView.apply {
            /**
             * 拍照
             */
            camera.setOnClickListener { _ ->
                onDismiss()
                Album.camera(context)
                    .image()
                    .onResult { it ->
                        onCamera?.invoke(it)
                    }.start()
            }
            /**
             * 从相册选择
             */
            photo.setOnClickListener { _ ->
                onDismiss()
                Album.image(context)
                    .multipleChoice()
                    .camera(true)
                    .columnCount(3)
                    .selectCount(1)
                    .onResult { it ->
                        onPhoto?.invoke(it[0].path)
                    }
                    .start()
            }

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