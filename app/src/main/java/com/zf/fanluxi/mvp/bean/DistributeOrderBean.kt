package com.zf.fanluxi.mvp.bean

import java.io.Serializable

data class DistributeOrderBean(
    val list: List<DistributeOrderList>
) : Serializable

data class DistributeOrderList(
    val user_id: String,
    val order_id: String,
    val pay_time: Long,
    val nickname: String,
    val goods: List<DistributeGoodsList>
) : Serializable

data class DistributeGoodsList(
    val goods_name: String,
    val goods_num: String,
    val final_price: String
):Serializable
