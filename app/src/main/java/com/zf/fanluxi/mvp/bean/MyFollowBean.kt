package com.zf.fanluxi.mvp.bean

import java.io.Serializable

data class MyFollowBean(
    val list:List<MyFollowList>,
    val count:String
)
data class MyFollowList(
    val collect_id:String,
    val add_time:String,
    val goods_id:String,
    val goods_name:String,
    val shop_price:String,
    val is_on_sale:String,
    val store_count:String,
    val cat_id:String,
    val is_virtual:String,
    val original_img:String,
    val market_price:String
): Serializable