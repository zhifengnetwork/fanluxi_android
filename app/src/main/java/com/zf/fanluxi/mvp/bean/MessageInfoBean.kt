package com.zf.fanluxi.mvp.bean

data class MessageInfoBean(
    val info:MessageInfo
)
data class MessageInfo(
    val rec_id:String,
    val user_id:String,
    val message_id:String,
    val category:Int,
    val is_see:String,
    val deleted:String,
    val message_title:String,
    val send_time:Long,
    val message_content:String
)