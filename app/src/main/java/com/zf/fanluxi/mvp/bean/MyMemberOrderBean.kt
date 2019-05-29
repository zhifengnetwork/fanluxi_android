package com.zf.fanluxi.mvp.bean

data class MyMemberOrderBean(
    val list:List<MemberOrderList>,
    val user:User
)
data class MemberOrderList(
    val order_id:String,
    val order_sn:String,
    val consignee:String,
    val add_time:Long,
    val total_amount:String
)
data class User(
    val user_id:String,
    val nickname:String,
    val mobile:String
)