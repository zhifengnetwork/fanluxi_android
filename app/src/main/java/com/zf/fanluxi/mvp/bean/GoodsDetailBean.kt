package com.zf.fanluxi.mvp.bean

data class GoodsDetailBean(
    val goods: GoodsInfo,
    val goods_content: String
)

data class GoodsInfo(
    val goods_id: String,
    val cat_id: String,
    val extend_cat_id: String,
    val goods_sn: String,
    val goods_name: String,
    val click_count: String,
    val brand_id: String,
    val store_count: String,
    val comment_count: String,
    val weight: String,
    val volume: String,
    val market_price: String,
    val shop_price: String,
    val price_ladder: String,
    val keywords: String,
    val goods_remark: String,
    val original_img: String,
    val is_virtual: String,
    val is_distribut: String,
    val is_agent: String,
    val virtual_indate: String,
    val virtual_limit: String,
    val virtual_refund: String,
    val virtual_sales_sum: String,
    val virtual_collect_sum: String,
    val collect_sum: String,
    val is_on_sale: String,
    val is_free_shipping: String,
    val sort: String,
    val is_recommend: String,
    val is_new: String,
    val is_hot: String,
    val last_update: String,
    val goods_type: String,
    val give_integral: String,
    val exchange_integral: String,
    val suppliers_id: String,
    val sales_sum: String,
    val prom_type: Int,
    val prom_id: String,
    val commission: String,
    val video: String,
    val sign_free_receive: Int,
    val buy_super_nsign: String,
    val seller_info: Shop,
    val is_collect: Int,
    val is_cart: Int,
    val comment_fr: Comment,
    val goods_images: List<String>,
    val prom_price: String,
    val start_time: Long,
    val end_time: Long

)

data class Shop(
    val store_id: String,
    val store_name: String,
    val avatar: String,
    val num: String,
    val goods: List<GoodsList>,
    val province_name: String,
    val city_name: String
)

data class Comment(
    val img_sum: String,
    val high_sum: String,
    val high_rate: String,
    val center_sum: String,
    val center_rate: String,
    val low_sum: String,
    val low_rate: String,
    val total_sum: String
)

data class GoodsList(
    val goods_id: String,
    val goods_name: String,
    val shop_price: String,
    val original_img: String
)