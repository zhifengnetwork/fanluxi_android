package com.zf.fanluxi.mvp.bean

data class PostOrderBean(
        val order_sn: String,
        val user_money: String,
        val price: PostOrderPrice,
        val address: PostOrderAddress?,
        val goodsinfo: List<Goods>
)

data class PostOrderAddress(
        val address_id: String,
        val consignee: String,
        val province_name: String,
        val city_name: String,
        val district_name: String,
        val mobile: String,
        val address: String
)

data class PostOrderPrice(
        val shipping_price: String,
        val coupon_price: String,
        val sign_price: String,
        val deposit: String,
        val user_money: String,
        val integral_money: String,
        val pay_points: String,
        val order_amount: String,
        val total_amount: String,
        val goods_price: String,
        val total_num: String,
        val order_prom_amount: String
)