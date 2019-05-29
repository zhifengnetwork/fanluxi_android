package com.zf.fanluxi.mvp.bean

data class SecKillTimeBean(
        val time_space_past: List<PastTimeList>,
        val time_space_future: List<FutureTimeList>
)

data class PastTimeList(
        val font: String,
        val start_time: Long,
        val end_time: Long
)

data class FutureTimeList(
        val font: String,
        val start_time: Long,
        val end_time: Long
)

data class SecKillData(
        val font: String,
        val start_time: Long,
        val end_time: Long,
        val status: String
)
