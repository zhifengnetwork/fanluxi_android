package com.zf.fanluxi.mvp.bean

data class MyFollowShopBean(
    val list:List<FollowShopList>,
    val count:String
)
data class FollowShopList(
    val collect_id:String,
    val seller_id:String,
    val seller_name:String,
    val avatar:String
)