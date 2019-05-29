package com.zf.fanluxi.mvp.bean

data class GoodsSpecBean(
    val name: String,
    val id: String,
    val item: String,
    val info: GoodsSpecInfo
)

data class GoodsSpecInfo(
    val price: String,
    val store_count: String,
    val spec_img: String?,
    val key_name: String
)

