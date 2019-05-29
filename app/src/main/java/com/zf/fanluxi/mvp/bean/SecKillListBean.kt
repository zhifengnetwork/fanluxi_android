package com.zf.fanluxi.mvp.bean

data class SecKillListBean(
        val flash_sale_goods: List<SecKillList>
)

data class SecKillList(
        val id: String,
        val title: String,
        val goods_id: String,
        val goods_name: String,
        val price: String,
        val shop_price: String,
        val original_img: String,
        val goods_num: Int,
        val order_num: Int,
        val disc: String
)