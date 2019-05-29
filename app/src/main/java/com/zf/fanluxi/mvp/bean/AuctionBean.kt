package com.zf.fanluxi.mvp.bean

data class AuctionBean(
    val list: List<AuctionList>
)

data class AuctionList(
    val id: String,
    val goods_id: String,
    val goods_name:String,
    val activity_name: String,
    val start_price: String,
    val start_time: Long,
    val original_img: String,
    val end_time:Long
)