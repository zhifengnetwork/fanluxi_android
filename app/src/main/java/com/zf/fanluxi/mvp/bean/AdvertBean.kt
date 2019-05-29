package com.zf.fanluxi.mvp.bean

data class AdvertBean(
    val list: List<AdvertList>
)

data class AdvertList(
    val ad_id: String,
    val ad_link: String,
    val ad_code: String,
    val bgcolor: String,
    val goods_id: String
)