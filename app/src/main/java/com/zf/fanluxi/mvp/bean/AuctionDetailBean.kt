package com.zf.fanluxi.mvp.bean

data class AuctionDetailBean(
        val auction: AuctionDetail,
        val bondCount: String,
        val bondUser: List<BondUser>
)

data class AuctionDetail(
        val id: String, //活动id
        val goods_id: String,
        val activity_name: String,
        val goods_name: String,
        val start_price: String,
        val start_time: Long,
        val deposit: String,
        val end_time: Long,
        val increase_price: String,
        val auction_status: String,
        val delay_time: String,
        val delay_num: String,
        val original_img: String,
        val delay_end_time: String,
        val isBond: String //1已交保证金 0未交保证金
)

data class BondUser(
        val id: String,
        val user_id: String,
        val offer_price: String,
        val offer_time: String,
        val user_name: String,
        val auction_id: String,
        val head_pic: String
)