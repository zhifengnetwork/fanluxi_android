package com.zf.fanluxi.mvp.bean

data class AccountDetailBean(
    val list:List<AccountDetailList>
)
data class AccountDetailList(
    val log_id:String,
    val user_id:String,
    val user_money:String,
    val frozen_money:String,
    val pay_points:String,
    val change_time:String,
    val desc:String,
    val order_sn:String,
    val order_id:String,
    val states:String,
    val change_data:String
)