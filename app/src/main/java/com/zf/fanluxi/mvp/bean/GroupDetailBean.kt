package com.zf.fanluxi.mvp.bean

data class GroupDetailBean(
        val team_found_num: String,
        val info: GroupDetailInfo,
        val goodsImg: List<GroupDetailImg>,
        val collect: Int,
        val team_found: List<GroupMemberList>
)

data class GroupMemberList(
        val found_id: String,
        val found_time: Long,
        val user_id: String,
        val nickname: String,
        val head_pic: String,
        val found_end_time: Long,
        val join: Int = 0,
        val order_id: String,
        val need: Int = 0
)

data class GroupDetailImg(
        val image_url: String,
        val img_id: String
)

data class GroupDetailInfo(
        val act_name: String,
        val team_id: String,
        val goods_name: String,
        val group_price: String,
        val start_time: Long,
        val goods_id: String,
        val cluster_type: String,
        val end_time: Long,
        val buy_limit: Int,
        val original_img: String,
        val goods_item_id: String,
        val shop_price: String,
        val sales_sum: String,
        val market_price: String,
        val comment_fr: GroupEva,
        val commentinfo: CommentInfo?

)

data class CommentInfo(
        val comment_id: String,
        val username: String,
        val content: String,
        val add_time: Long,
        val img: List<String>?,
        val deliver_rank: Int,
        val goods_rank: Int,
        val service_rank: Int
)

data class GroupEva(
        val img_sum: String,
        val high_sum: String,
        val high_rate: String,
        val center_sum: String,
        val center_rate: String,
        val low_sum: String,
        val low_rate: String,
        val total_sum: String
)