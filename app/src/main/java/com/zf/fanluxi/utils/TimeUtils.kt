package com.zf.fanluxi.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getHourMinute(time: Long): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(time * 1000)
    }

    fun getCountTime(time: Long): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(time)
    }

    //时间戳转时间
    fun myOrderTime(time: Long): String {
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        return format.format(time * 1000)
    }

    fun auctionTime(startTime: Long): String {
        //活动未开始，显示开始时间
        val format = SimpleDateFormat("MM月dd号 HH:mm", Locale.getDefault())
        return format.format(startTime * 1000)
    }

    //传入两个时间戳，计算两个时间戳的差得出秒差，具体到毫秒
    //start小 end大
    fun getCountTime2(time: Long): String {
        //一个小时60分钟 一分钟60秒

        val nd = (1000 * 24 * 60 * 60).toLong()// 一天的毫秒数
        val nh = (1000 * 60 * 60).toLong()// 一小时的毫秒数
        val nm = (1000 * 60).toLong()// 一分钟的毫秒数
        val ns: Long = 1000// 一秒钟的毫秒数

        val day = time / nd// 计算差多少天
        val hour = time % nd / nh + day * 24// 计算差多少小时
        val min = time % nd % nh / nm + day * 24 * 60// 计算差多少分钟
        val sec = time % nd % nh % nm / ns// 计算差多少秒
        /** 时分秒从下面打印语句取 */
//        LogUtils.e(">>>>>>时间相差：" + day + "天" + (hour - day * 24) + "小时"
//                + (min - day * 24 * 60) + "分钟" + sec + "秒。")
        return if (day == 0L) {
            (hour - day * 24).toString() + ":" + (min - day * 24 * 60).toString() + ":" + sec
        } else {
            day.toString() + "天" + (hour - day * 24).toString() + ":" + (min - day * 24 * 60).toString() + ":" + sec
        }
    }

    fun getYmd(time: Long): String {
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return format.format(time * 1000)
    }

}