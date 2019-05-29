package com.zf.fanluxi.mvp.bean

data class CartSelectBean(
    val cart_price_info: CartPrice
)

data class CartPrice(
    val total_fee: String,
    val goods_fee: String,
    val goods_num: String
)