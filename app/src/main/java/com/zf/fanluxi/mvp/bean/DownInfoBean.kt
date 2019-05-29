package com.zf.fanluxi.mvp.bean

import java.io.Serializable
import java.util.*

/**
 * 需要实现 Serializable
 */
data class DownInfoBean(

        val name: String?,
        val url: String?,
        //下面这个是设置优先级，数字越大，优先下载，可以不设置
        var priority: Int = 0
) : Serializable {

    companion object {

        private const val serialVersionUID = 2072893447591548402L
    }

    init {
        val random = Random()
        priority = random.nextInt(100)
    }

}