package com.zf.fanluxi.utils

import android.content.Context
import android.widget.ImageView
import com.zf.fanluxi.GlideApp


class GlideUtils {

    companion object {
        fun loadUrlImage(context: Context?, url: String?, imageView: ImageView) {
            context?.let {
                url?.let { url ->
                    GlideApp.with(it)
                        .asBitmap()
                        .load(url)
                        .into(imageView)
                }

            }
        }
    }

}
