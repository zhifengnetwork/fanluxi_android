package com.zf.fanluxi.mvp.bean

data class GroupBuyBean(
    val list: List<GroupBuyList>
)

data class GroupBuyList(
    val goods_id: String,
    val goods_name: String,
    val original_img: String,
    val id: String,
    val title: String,
    val start_time: Long,
    val end_time: Long,
    val item_id: String,
    val price: String,
    val goods_num: String,
    val buy_num: String,
    val order_num: String,
    val goods_price: String,
    val comment_count:String,
    val rebate: String,
    val virtual_num: String
)