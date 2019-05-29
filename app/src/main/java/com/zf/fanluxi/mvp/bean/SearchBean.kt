package com.zf.fanluxi.mvp.bean

data class SearchBean(
    val goods_list: List<SearchList>?,
    val filter_price: List<FilterPrice>
)

data class FilterPrice(
    val value: String,
    val href: String
)

data class SearchList(
    val goods_id: String,
    val goods_name: String,
    val sales_sum: String,
    val comment_count: String,
    val goods_images: List<GoodsImagesList>?,
    val shop_price: String,
    val sale_total: String? = null,
    val seller_name: String,
    val cat_id: String,
    val original_img: String? = null,
    val commission_num: String

)

data class GoodsImagesList(
    val img_id: String,
    val image_url: String
)