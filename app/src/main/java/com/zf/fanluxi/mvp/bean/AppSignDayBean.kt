package com.zf.fanluxi.mvp.bean

data class AppSignDayBean(
    val date:List<String>,
    val today_sign:Boolean,
    val points:String,
    val add_point:String,
    val continue_sign:String,
    val accumulate_day:String,
    val note:String,
    val auth:Int
)