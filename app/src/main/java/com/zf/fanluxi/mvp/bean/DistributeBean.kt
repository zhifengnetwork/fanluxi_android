package com.zf.fanluxi.mvp.bean

data class DistributeBean(
    val money_total: DistributeMoney,
    val leader: DistributeLeader,
    val underling_number: String,
    val user_id: String,
    val statistical_time: String
)

data class DistributeLeader(
    val user_id: String,
    val nickname: String
)

data class DistributeMoney(
    val money_total: String,
    val max_moneys: String,
    val moneys: String,
    val oldPerformance: String
)