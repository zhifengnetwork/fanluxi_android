package com.zf.fanluxi.mvp.bean

data class AuctionPriceBean(
        val buy_num: String,
        val price_num: String,
        val max_price: List<PriceList>
)

data class PriceList(
        val id: String,
        val user_id: String,
        val offer_price: String,
        val offer_time: String,
        val user_name: String,
        val head_pic: String,
        val is_out:String, //0出局，1高价者，2成交
        val pay_status: String, //1竞拍成功 2未完成支付 还有个0的状态
        val isnowuser: String //1本人 0不是本人
)