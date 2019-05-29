package com.zf.fanluxi.mvp.bean

import java.io.Serializable

data class GroupBean(
    val team_id: String,
    val act_name: String,
    val goods_name: String,
    val group_price: String,
    val goods_id: String,
    val end_time: String,
    val group_number: String,
    val purchase_qty: String,
    val shop_price: String,
    val market_price: String,
    val goods_item_id:String,
    val original_img: String
):Serializable