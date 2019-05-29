package com.zf.fanluxi.mvp.bean

data class CommendBean(
        val goods_list: List<CommendList>
)

data class CommendList(
        val goods_id: String,
        val cat_id: String,
        val goods_name: String,
        val comment_count:String,
        val original_img: String,
        val shop_price: String,
        val commission_num:String
)