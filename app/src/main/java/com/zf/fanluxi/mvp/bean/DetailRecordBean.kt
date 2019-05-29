package com.zf.fanluxi.mvp.bean

data class DetailRecordBean(
    val list: List<DetailRecordList>
)

data class DetailRecordList(
    val log_id: String,
    val user_id: String,
    val user_money: String,
    val frozen_money: String,
    val pay_points: String,
    val change_time: Long,
    val desc: String,
    val order_id: String,
    val states: String
)