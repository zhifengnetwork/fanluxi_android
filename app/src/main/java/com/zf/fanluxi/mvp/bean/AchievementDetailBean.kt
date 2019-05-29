package com.zf.fanluxi.mvp.bean

data class AchievementDetailBean(
    val list:List<AchievementList>
)
data class AchievementList(
    val performance_id:String,
    val user_id:String,
    val money:String,
    val create_time:String,
    val note:String,
    val order_id:String
)