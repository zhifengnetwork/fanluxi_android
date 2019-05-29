package com.zf.fanluxi.mvp.bean

data class MessageBean(
    val list: List<MessageList>
)

data class MessageList(
    val rec_id: String,
    val user_id: String,
    val message_id: String,
    val category: String,
    val is_see: String,
    val deleted: String,
    val message_title: String,
    val message_content:String,
    val send_time: Long
)