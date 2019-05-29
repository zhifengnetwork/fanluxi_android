package com.zf.fanluxi.mvp.bean

import java.io.Serializable

data class MyWalletBean(
    val user_money:String,
    val pay_points:String,
    val coupon_num:String,
    val alipay:String,
    val bank_name:String,
    val bank_card:String,
    val realname:String,
    val service_ratio:String,
    val min_service_money:String,
    val max_service_money:String
): Serializable