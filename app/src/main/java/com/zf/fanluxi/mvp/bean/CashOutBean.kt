package com.zf.fanluxi.mvp.bean

data class CashOutBean(
    val list:List<CashOutList>
)
data class CashOutList(
    val id:String,
    val money:String,
    val create_time:Long,
    val check_time:Long,
    val pay_time:Long,
    val refuse_time:Long,
    val bank_name:String,
    val bank_card:String,
    val realname:String,
    val remark:String,
    val taxfee:String,
    val status:Int,
    val pay_code:String,
    val error_code:String
)