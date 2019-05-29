package com.zf.fanluxi.mvp.bean

data class OrderBean(
        val monthList: List<MonthData>? = null
)

data class MonthData(
        val date: String?,
        val total:String?,
        val dayList: List<DayData>? = null
)

data class DayData(
        val date: String?,
        val total: String?
)