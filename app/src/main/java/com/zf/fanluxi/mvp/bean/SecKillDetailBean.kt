package com.zf.fanluxi.mvp.bean

data class SecKillDetailBean(
    val info: SecKillInfo,
    val seller_info: Shop,
    val goods_content: String
)

data class SecKillInfo(
    val id: String,
    val title: String,
    val goods_id: String,
    val item_id: String,
    val price: String,
    val goods_name: String,
    val goods_images: List<String>,
    val cat_id: String,
    val start_time: Long,
    val end_time: Long,
    val shop_price: String,
    val store_count: String,
    val sales_sum: String,
    val comment_fr: Comment
)