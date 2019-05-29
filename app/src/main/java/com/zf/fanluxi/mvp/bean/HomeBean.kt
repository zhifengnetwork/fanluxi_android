package com.zf.fanluxi.mvp.bean

data class HomeBean(
        val adlist: List<AdList>,
        val articlelist: List<ArticleList>,
        val flash_sale_goods: List<SecKillList>,
        val end_time: Long,
        val ad_top: ImageUrl,
        val ad_left1: ImageUrl,
        val ad_left2: ImageUrl,
        val ad_right: ImageUrl,
        val new_left: ImageUrl,
        val new_right: List<ImageUrl>?,
        val re_left: ImageUrl,
        val re_right: ImageUrl,
        val re_bottom: List<ImageUrl>?

)

data class ImageUrl(
        val ad_code: String,
        val goods_id: String
)

data class AdList(
        val ad_code: String,
        val ad_link: String,
        val ad_id: String,
        val goods_id: String?
)

data class ArticleList(
        val article_id: String,
        val title: String
)