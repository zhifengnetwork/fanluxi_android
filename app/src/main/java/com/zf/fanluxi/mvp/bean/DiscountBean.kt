package com.zf.fanluxi.mvp.bean

data class DiscountBean(
    val cid: String,
    val name: String,
    val type: String,
    val money: String,
    val condition: String,
    val use_start_time: Long,
    val use_end_time: Long,
    val status: String,
    val use_type: String,
    val spacing_time: String,
    val use_scope: String,
    val coupon_code: String
)