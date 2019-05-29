package com.zf.fanluxi.mvp.bean

data class RechargeRecordBean(
    val list:List<RechargeRecordList>
)
data class RechargeRecordList(
    val order_id:String,
    val user_id:String,
    val nickname:String,
    val order_sn:String,
    val account:String,
    val ctime:Long,
    val pay_time:String,
    val pay_code:String,
    val pay_name:String,
    val pay_status:Int,
    val buy_vip:String
)